//Name: Yanxiang Wang
//USC loginid: yanxianw
//CS 455 PA4
//Fall 2015

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

/**
 *This class is used to read words in the source file and output the a sequence of 
 * words to the out file where the source file name and out file name is given by 
 * the conductor. The sequence of the words is based on the source file.
 *
 */
public class GenText {
	final static String REMINDER = "command should be like 'java GenText [-d] prefixLength numWords \n"
			+ "sourceFile outFile' where -d is optional, prefixLength should larger than 1\nnumWords "
			+ "should larger than 0, prefixLength and numWords should be integer";
	/**
	 * @param args command should be like 'java GenText [-d] prefixLength numWords 
	 * sourceFile outFile' where -d is optional, prefixLength should larger than 1
	 * numWords should larger than 0, prefixLength and numWords should be integer
	 */
	public static void main(String[] args) {
		boolean debug = false;
		int index = 0;
		ArrayList<String> sourceWords = new ArrayList<String>();
		try{
			if(args.length==0){
				throw new BadDataException("missing command-line arguments");
			}
			if(args[0].equals("-d")){
				debug = true;
				index++; //set to next argument
			}		
			int prefixLength = Integer.parseInt(args[index]);
			int numWords = Integer.parseInt(args[index + 1]);
			String sourceFileName = args[index + 2];
			String outFileName = args[index + 3];
			if(prefixLength<1||numWords<0){
				throw new BadDataException(REMINDER); 
			}
			sourceWords= readFile(sourceFileName);
			if(sourceWords.size()<=prefixLength){
				throw new BadDataException("prefixLength should be less than the words in source file");
			}
			RandomTextGenerator text = new RandomTextGenerator(sourceWords, prefixLength,numWords,debug);
			text.generateText(outFileName);
		}
		catch(FileNotFoundException exception){
			System.out.println("File not found.");
		}
		catch(BadDataException exception){
			System.out.println("Bad data: " + exception.getMessage());
		}
		catch(IOException exception){
			exception.printStackTrace();
		}	
	}
	
	   /**
    Reads all the words in the source file.
    @param filename the name of the file holding the source words
    @return the words in the file as an ArrayList<String>
 */
	private static ArrayList<String> readFile(String filename) throws IOException{
		ArrayList<String> words = new ArrayList<String>();
		File inFile = new File(filename);
		Scanner in = new Scanner(inFile);
		try
		{
			words = readData(in);
			return words;
		}
		finally
		{
			in.close();//inFile.exists();
		}
	}

 /**
    Reads all words in the scanner.
    @param in the scanner that scans the words
    @return the words in the scanner
 */
	private static ArrayList<String> readData(Scanner in) throws BadDataException
	{
		ArrayList<String> readString = new ArrayList<String>();
		if (!in.hasNext()) 
		{
			throw new BadDataException("Words expected:");
		}

		while(in.hasNext()) 
		{
			readString.add(in.next());
		}
		return readString;
	}
}

