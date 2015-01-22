package spell;

import java.io.*;
import java.util.*;

public class ISpellCorrector implements ISpellCorrector{
	
	public ISpellCorrector( ){
		
	}
	
	private ITrie dict;
	
	public static class NoSimilarWordFoundException extends Exception {
	}
	
	/**
	 * Tells this <code>ISpellCorrector</code> to use the given file as its ITrie
	 * for generating suggestions. 
	 * @param ITrieFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useITrie(String ITrieFileName) 
			throws IOException {
		File in = new File(ITrieFileName);
		Scanner src = new Scanner(in);
		dict = new ITrie();
		while(src.hasNext()){
			String s = src.next();
			s = s.toLowerCase();
			dict.add(s);
		}
	}
		
	/**
	 * Suggest a word from the ITrie that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion
	 * @throws NoSimilarWordFoundException If no similar word is in the ITrie
	 */
	public String suggestSimilarWord(String inputWord) 
			throws NoSimilarWordFoundException {
		// generate Set of dist-1 words as described
		// search for each, return the highest count
		HashSet<String> morphed = morph(inputWord);
		ITrie.INode result; 
		String found;
		for(String word : morphed){
			ITrie.INode intermediate = dict.find(word);
			if(intermediate.getValue() > result.getValue() ||
					result == null){
				result = intermediate;
				found = word;
			}
		}
		
		if (result == null || result.getValue() == 0) {
			HashSet<String> morphedAgain;
			for(String word : morphed){
				morphedAgain.addAll(morph(word));
			}
			for(String word : morphedAgain){
				ITrie.INode intermediate = dict.find(word);
				if(intermediate.getValue() > result.getValue() ||
						result == null){
					result = intermediate;
					found = word;
				}
			}
		}
		
		if (result == null || result.getValue() == 0) {
			throw NoSimilarWordFoundException;
		}
		
		else {
			return found;
		}
	
		// if each is 0, repeat with dist-2
		// if still 0, throw exception
					
	}
	
	private HashSet<String> morph(String root){
		HashSet<String> morphs = new HashSet();
		// add deletion words:
		for (int i = 0; i < root.length(); i++){
			StringBuilder s;
			morphs.add(s.append(root.substring(0,i))
				.append(root.substring(i + 1)).toString());
		}
		// add transposition words:
		for (int i = 1; i < root.length(); i++){
			// if string is size one, nothing is done.
			// else swap each i with i-1
			StringBuilder s;
			char before = root.charAt(i - 1);
			char after = root.charAt(i);
			morphs.add(s.append(root.substring(0,i - 1))
				.append(after).append(before)
				.append(root.substring(i + 1)).toString());
		}
		// add alteration words:
		for (int i = 0; i < root.length(); i++){
			for (int c = 0; c < 25; c++){
				StringBuilder s;
				char put = (char) (c + 'a');
				morphs.add(s.append(root.substring(0,i))
					.append(put)
					.append(root.substring(i + 1)).toString());
			}
		}
		// add insertion words:
		for (int i = 0; i < root.length(); i++){
			for (int c = 0; c < 25; c++){
				StringBuilder s;
				char put = (char) (c + 'a');
				morphs.add(s.append(root.substring(0,i))
					.append(put)
					.append(root.substring(i)).toString());
				morphs.add(s.append(root).append(put).toString());
				//making sure to add at end too!
			}
		}
	}
	
	public class NoSimilarWordFoundException extends Exception{
		
		public void NoSimilarWordFoundException(){
		}
	}
}
