package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		spellcheck corrector = new spellcheck();
		
		corrector.useDictionary(dictionaryFileName);
		try{
		String suggestion = corrector.suggestSimilarWord(inputWord);
		System.out.println("Suggestion is: " + suggestion);}
		catch(NoSimilarWordFoundException e){
			// do nothing, essentially
			System.out.println("NOPE TRY AGAIN");
		}
	//	System.out.println(corrector.toString());
	}

}
