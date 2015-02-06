package hangman;

import java.io.*;
import java.util.*;

public class evil implements IEvilHangmanGame {
	
	public void evil(){
		// do nothing;
	}
	
	@SuppressWarnings("serial")
	public static class GuessAlreadyMadeException extends Exception {
		public GuessAlreadyMadeException(){
			// do nothing;
		}
	}
	
	public TreeSet<Character> guesses;
	public HashSet<String> src;
	

	public void startGame(File dictionary, int wordLength){
		//reset / init all the vars to be ready for a new game
		
		guesses = new TreeSet<Character>();
		src = new HashSet<String>();
		try{
			Scanner read = new Scanner(dictionary);
			while(read.hasNext()){
				String hold = read.next().toLowerCase();
				if (hold.length() == wordLength){
					src.add(hold);
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Bad dictionary file");
		}
	}
	
	public HashSet<String> makeGuess(char guess)
			throws IEvilHangmanGame.GuessAlreadyMadeException {
		HashSet<String> ret = new HashSet<String>();
			// have to do this to avoid NPE
		if (guesses.contains(guess)){
			throw new IEvilHangmanGame.GuessAlreadyMadeException();
		}
		else {
			guesses.add(guess);
			HashMap<String, HashSet<String>> parts =
				new HashMap<String,HashSet<String>>();
			for(String item : src){
				String key = makePattern(item, guess);
				if(parts.containsKey(key)){
					HashSet<String> temp = parts.get(key);
					temp.add(item);
					parts.remove(key);
					parts.put(key, temp);
				}
				else {
					HashSet<String> temp = new HashSet<String>();
					temp.add(item);
					parts.put(key, temp);
				}
			}
			int marker = 0;
			String line = "";
			for(String key : parts.keySet()){
				if (parts.get(key).size() > marker){
					marker = parts.get(key).size();
					line = key;
				}
				else if (parts.get(key).size() == marker){
					line = compare(line, key);
					// do nothing to marker: it has not changed.
				}
				else {
					// do nothing.  we are throwing this data away.
				}
			}
			ret = parts.get(line);
			if(ret.size() > 0){
				// there was a misunderstanding here...
				// basically i never selected a group with ----
				// for some dumb reason.  but if i did select that
				// group, it would not get "saved," and blah blah blah
				src = ret;
			//	System.out.println(src.toString());
			//	System.out.println(ret.toString());
			}
		}
		return ret; 
	}
			
	public String makePattern(String word, char pivot){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == pivot){
				sb.append(pivot);
			}
			else {
				sb.append('-');
			}
		}
	//	System.out.println("Made pattern: " + sb.toString());
	//	System.out.println("from word: " + word);
	//	System.out.println("Pivot was: " + pivot);
		return sb.toString();
	}
	
	private String compare(String first, String second){
		int one = 0;
		int two = 0;
		int a = 0;
		int b = 0;
		for(int i = 0; i < first.length(); i++){
			// count -'s and note final non-dash
			if(first.charAt(i) == '-'){
				one++;
			}
			else {
				a = i;
			}
			if(second.charAt(i) == '-'){
				two++;
			}
			else {
				b = i;
			}
		}
		if(one > two){
			// first has more dashes
		//	System.out.println("compare 1 wins: " + first);
			return first;
		}
		if(two > one){
			// second has more dashes
			// effing forgot to swap the less than for a greater than
		//	System.out.println("compare 2 wins: " + second);
			return second;
		}
		// the above takes care of the case where one string has
		// zero characters (it is returned because it is always 
		// less.  both cannot be zero because we cannot have 
		// identical keys).  this leaves us with the case where
		// both keys have the same number of letters, but they have 
		// them in different spots:
		if(a > b){
			return first;
		}
		return second;
	}	
}
