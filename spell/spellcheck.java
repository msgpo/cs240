package spell;

import java.io.*;
import java.util.*;

public spellcheck implements ISpellCorrector{
	
	public spellcheck( ){
		
	}
	
	private dictionary dict;
	
	public static class NoSimilarWordFoundException extends Exception {
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
	public String suggestSimilarWord(String inputWord) 
			throws NoSimilarWordFoundException {
		// generate Set of dist-1 words as described
		// search for each, return the highest count
		HashSet distOne = new HashSet();
		// add deletion words:
		for (int i = 0; i < inputWord.length; i++){
			distOne.add(inputWord.substring(0,i)
				.concat(inputWord.substring(i + 1)));
		}
		// add transposition words:
		for (int i = 1; i < inputWord.length; i++){
			// if string is size one, nothing is done.
			// else swap each i with i-1
			char before = inputWord.charAt(i - 1);
			char after = inputWord.charAt(i);
			distOne.add(inputWord.substring(0,i - 1)
				.concat(after).concat(before)
				.concat(inputWord.substring(i + 1)));
		}
		// add alteration words:
		for (int i = 0; i < inputWord.length; i++){
			for (int c = 0; c < 25; c++){
				char put = (c + 'a');
				distOne.add(inputWord.substring(0,i)
					.concat(put)
					.concat(inputWord.substring(i + 1)));
			}
		}
		// add insertion words:
		for (int i = 0; i < inputWord.length; i++){
			for (int c = 0; c < 25; c++){
					char put = (c + 'a');
					distOne.add(inputWord.substring(0,i)
						.concat(put)
						.concat(inputWord.substring(i)));
					distOne.add(inputWord.concat(put);
					//making sure to add at end too!
				}
			}
		}
		// if each is 0, repeat with dist-2
		// if still 0, throw exception
					
	}
	
}
