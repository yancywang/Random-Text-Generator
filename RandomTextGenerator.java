//Name: Yanxiang Wang
//USC loginid: yanxianw
//CS 455 PA4
//Fall 2015

import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.io.FileNotFoundException;

/**
 * This class is used to generate random text 
 */

public class RandomTextGenerator {
	/**
	 * Instance variable: LINELENGTH is the length of every line in the output file.
	 * lineLength should be less than LINELENGTH.	
	 * dataBase is the hash map that to match prefix (key type) with ArrayList<String>
	 * which saves the choice list in which the next words can be chosen from. 
	 * prefixLength should be less than sourceWords.length 
	 * totalWords is the number of words the output file consists
	 * debugMode is the  flag of debug model
	 */
	final int LINELENGTH = 80;
	int lineLength=0;
	private Map<Prefix,ArrayList<String>> dataBase = new HashMap<Prefix,ArrayList<String>>();
	int prefixLength;
	int totalWords;
	Random generator;
	ArrayList<String> sourceWords; 
	boolean debugMod;
	
	/**
	 * construct a object with 
	 * @param words source words
	 * @param length prefix length 
	 * @param outWords number of words in output file
	 * @param debug flag of debug model 
	 */
	public RandomTextGenerator(ArrayList<String> words, int length, int outWords,boolean debug) {
		sourceWords = words;
		prefixLength = length;	
		totalWords = outWords;
		debugMod = debug;
		if(debug){
			generator = new Random(1);// set seed as 1
		}
		else{
			generator = new Random();
		}
		ArrayList<String> key = new ArrayList<String>();
		ArrayList<String> value = new ArrayList<String>();
		for(int i=0;i<prefixLength;i++){
			key.add(words.get(i));
		}
		for(int index=0;index+prefixLength<words.size();index++){
			Prefix preWords = new Prefix(key);
			if(dataBase.get(preWords)==null){
				value = new ArrayList<String>();
				value.add(words.get(index + prefixLength));
				dataBase.put(preWords,value);
			}
			else{
				dataBase.get(preWords).add(words.get(index + prefixLength));
			}
			key.remove(0);
			key.add(words.get(index + prefixLength));
		}
	}
	
	/**
	 *generate an output file
	 *@param outfile the name of the output file 
	 */
	public void generateText(String outFile){
		ArrayList<String> preWords=new ArrayList<String>();
		try{
			preWords = randomPreWords();
			Prefix preFixWords= new Prefix(preWords);
			if(debugMod){
				System.out.println("DEBUG: chose a new initial prefix:" + preWords);
			}
			PrintWriter out = new PrintWriter(outFile);
			for(int i=0;i<totalWords;i++){
				while(dataBase.get(preFixWords)==null){
					preWords = randomPreWords();
					preFixWords = new Prefix(preWords);
					if(debugMod){
						System.out.println("DEBUG: successors: <END OF FILE>");
						System.out.println("DEBUG: chose a new initial prefix:" + preWords);
					}
				}
				preWords = printWord(out,preFixWords);
				preFixWords = new Prefix(preWords);
			}
			out.close();
		}
		catch(FileNotFoundException exception){			
		}
	}

	/**
	 * print a word, if the length of this line is larger than LINELENGTH, then 
	 * print a return
	 * @param out output stream
	 * @param preFix the key type object to find a value from the hash map
	 * @return return the next prefix words than to find a match value 
	 */
	private ArrayList<String> printWord(PrintWriter out,Prefix preFix){
		ArrayList<String> preWordss=new ArrayList<String>(preFix.getPrefix());
		ArrayList<String> choiceList = new ArrayList<String>(dataBase.get(preFix));
		String nextWord = "";
		nextWord = choiceList.get(generator.nextInt(choiceList.size()));
		if(lineLength + nextWord.length()>LINELENGTH){
			out.println();
			lineLength = 0;
		}
		out.printf(nextWord+" ");
		lineLength = lineLength + nextWord.length() +1;
		if(debugMod){
			System.out.println("DEBUG: prefix:" + preWordss);
			System.out.println("DEBUG: successors:" + choiceList);
			System.out.println("DEBUG: word generated:" + nextWord);
		}
		preWordss.remove(0);
		preWordss.add(nextWord);
		return preWordss;
	}

	/**
	 * generate a random chosen prefix words
	 * @return return the random chosen prefix words.
	 */
	private ArrayList<String> randomPreWords(){
		ArrayList<String> preWords = new ArrayList<String>();
		int index = generator.nextInt(sourceWords.size()-prefixLength-1);
		for(int i=index;i<index + prefixLength;i++){
			preWords.add(sourceWords.get(i));
		}
		return preWords;
	}
}
