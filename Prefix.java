//Name: Yanxiang Wang
//USC loginid: yanxianw
//CS 455 PA4
//Fall 2015

import java.util.ArrayList;

/**
 * This Class is used to save the prefix words, and it should be a key type of
 * a hash map in another class, so it has hashcode() method and equals() method  
 */

public class Prefix {
	/**
	 * Instance Variable: HASH_MULTIPIER is used to calculate hash code it should
	 * be larger than 0. prefixWords is the prefix words of the object.
	 */
	final int HASH_MUTIPLIER = 20;
	private ArrayList<String> prefixWords;	
	
	/**
	 * Build an empty object with no prefix
	 */
	public Prefix(){}	
	
	/**
	 * Build an object with words as its prefix words.
	 *  @param words prefix words
	 */
	public Prefix(ArrayList<String> words) {
		prefixWords = new ArrayList<String> (words);
	}	
	
	/**
	 * @return return the prefix words
	 */
	public ArrayList<String> getPrefix(){
		return prefixWords;
	}	
	
	/**
	 * used to calculate hash code
	 *  @return return the hash code
	 */
	public int hashCode(){
		int h = 0;
		if(!prefixWords.isEmpty()){
			for(int i=0; i<prefixWords.size();i++){
				h = h * HASH_MUTIPLIER + prefixWords.get(i).hashCode();
			}
		}
		return h;
	}
	
	/**
	 * used to compare two object
	 *  @return return true if the two object are the same
	 */
	public boolean equals(Object obj){
		Prefix otherOne = (Prefix)obj;
		if(!prefixWords.isEmpty()){
			for(int i=0; i<prefixWords.size();i++){
				if(!prefixWords.get(i).equals(otherOne.getPrefix().get(i))){
					return false;
				}
			}
		}
		return true;
	}
	


}
