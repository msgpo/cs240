package spell;

import java.io.*;
import java.util.*;

public class spellcheck implements ISpellCorrector{
	
	public spellcheck(){
		
	}
	
	private dictionary dict;
	
	public static class NoSimilarWordFoundException extends Exception {
		public void NoSimilarWordFoundException(){
		}
	}
	
	/**
	 * Tells this <code>ISpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions. 
	 * @param dictionaryFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useDictionary(String dictionaryFileName) 
			throws IOException {
		File in = new File(dictionaryFileName);
		Scanner src = new Scanner(in);
		dict = new dictionary();
		while(src.hasNext()){
			String s = src.next();
			s = s.toLowerCase();
			dict.add(s);
		}
	}
	


	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion
	 * @throws NoSimilarWordFoundException If no similar word is in the dictionary
	 */
	public String suggestSimilarWord(String inputWord) throws
		spell.ISpellCorrector.NoSimilarWordFoundException {
		// generate Set of dist-1 words as described
		// search for each, return the highest count

		HashSet<String> morphed = morph(inputWord);
		int result = 0;	
		String found = new String();
		if (dict.find(inputWord) != null){
			return inputWord;
		}
		for(String word : morphed){
			dictionary.INode intermediate = dict.find(word);
			if(intermediate != null &&
					intermediate.getValue() >= result &&
					(found.length() == 0 || 
					word.compareTo(found) < 0)){
				result = intermediate.getValue();
				found = word;
			}
		}
		
		if (found.length() == 0) {
			HashSet<String> morphedAgain = new HashSet<String>();
			for(String word : morphed){
				morphedAgain.addAll(morph(word));
			}
			for(String word : morphedAgain){
				dictionary.INode intermediate = dict.find(word);
				if(intermediate != null &&
						intermediate.getValue() >= result &&
						(found.length() == 0 || 
						word.compareTo(found) < 0)){
					result = intermediate.getValue();
					found = word;
				}
			}
		}
		if(found.length() > 0){
			return found;
		}
			
		throw new spell.ISpellCorrector.NoSimilarWordFoundException();

	}
	
	private HashSet<String> morph(String root){
		HashSet<String> morphs = new HashSet<String>();
		// add deletion words:
		for (int i = 0; i < root.length(); i++){
			StringBuilder s = new StringBuilder();
			morphs.add(s.append(root.substring(0,i))
				.append(root.substring(i + 1)).toString());
		}
		// add transposition words:
		for (int i = 1; i < root.length(); i++){
			// if string is size one, nothing is done.
			// else swap each i with i-1
			StringBuilder s = new StringBuilder();
			char before = root.charAt(i - 1);
			char after = root.charAt(i);
			morphs.add(s.append(root.substring(0,i - 1))
				.append(after).append(before)
				.append(root.substring(i + 1)).toString());
		}
		// add alteration words:
		for (int i = 0; i < root.length(); i++){
			for (int c = 0; c < 25; c++){
				StringBuilder s = new StringBuilder();
				char put = (char) (c + 'a');
				morphs.add(s.append(root.substring(0,i))
					.append(put)
					.append(root.substring(i + 1)).toString());
			}
		}
		// add insertion words:
		for (int i = 0; i < root.length(); i++){
			for (int c = 0; c < 25; c++){
				StringBuilder s = new StringBuilder();
				char put = (char) (c + 'a');
				morphs.add(s.append(root.substring(0,i))
					.append(put)
					.append(root.substring(i)).toString());
				morphs.add(s.append(root).append(put).toString());
				//making sure to add at end too!
			}
		}

		return morphs;
	}
}

