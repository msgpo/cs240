package hangman;

import java.io.*;
import java.util.*;

public class hangman {
	public static void main(String[] args){
		File dict = new File(args[0]);
		int length = Integer.parseInt(args[1]);
		int guesses = Integer.parseInt(args[2]);
		if(!dict.canRead()){
			System.out.println("Bad dictionary");
			return;
		}
		if(length < 2){
			System.out.println("Bad length");
			return;
		}
		if(guesses < 1){
			System.out.println("Too few guesses");
			return;
		}
		
		evil game = new evil();
		game.startGame(dict,length);
		
		int turns = 0;
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < length; i++){
			word.append('-');
		}
		Scanner term = new Scanner(System.in);
		while(guesses > 0){
			System.out.printf("Turn: %d Turns remaining: %d\n",
				turns + 1, guesses);
			turns++;
			System.out.println(game.guesses.toString());
			System.out.print("WORD: ");
			System.out.println(word.toString());
			System.out.print("Guess a letter:  ");
			boolean repeat = true;
			while(repeat){
				String guesss = term.nextLine();
				System.out.println();
				guesss = guesss.toLowerCase();
				if(Character.isLetter(guesss.charAt(0)) && guesss.length() == 1){
					char guess = guesss.charAt(0);
					try{
						HashSet<String> res = game.makeGuess(guess);
						repeat = false;
						if(res.isEmpty()){
							System.out.print("Sorry, there are");
							System.out.printf(" no %c's\n", guess);
							guesses--;
						}
						else {
							//game.src = res;
							Iterator<String> e = res.iterator();
							String tem =
									game.makePattern(e.next(),guess);
							if(tem.indexOf(guess) >= 0){
								int count = 0;
								for(char c : tem.toCharArray()){
									if(c != '-'){
										count++;
									}
								}	
								System.out.printf("Yes, there is %d",count);
								System.out.printf(" %c's\n", guess);
								for(int i = 0; i < word.length(); i++){
									if(tem.charAt(i) == guess){
										word.setCharAt(i, guess);
									}
								}							
							// somehow figure out a ton of crap,
							// like the letter count and exact pattern
							// need a persistent SB with the intermediate
							// results or w/e.  other than that, looks
							// done.
							}
							else{
							System.out.print("Sorry, there are");
							System.out.printf(" no %c's\n", guess);
							guesses--;
							}
						}	
						if(res.contains(word.toString())){
							//we're done!  you win!
							System.out.println("You win!");
							System.out.println(word.toString());
							return;
						}
						if(guesses == 0
								&& !res.contains(word.toString())){
							//we're out of guesses, don't even close the
							//loop really.  just quit.
							Iterator<String> e = game.src.iterator();
							System.out.println("You lose.  Computer chose:");
							System.out.println(e.next());
							return;
						}
							
					}
					catch(IEvilHangmanGame.GuessAlreadyMadeException e){
						System.out.print("You already guessed that le");
						System.out.println("tter");
						repeat = true;
					}
				}
				else{
					System.out.println("Invalid input");
				}
			}
		}
	
		
		
	}
}
