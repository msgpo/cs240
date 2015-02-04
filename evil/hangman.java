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
			System.out.printf("Turn: %d Turns remaining: %d/n",
				turns + 1, guesses);
			System.out.println("Guess a letter: ");
			boolean repeat = true;
			while(repeat){
				char guess = term.nextChar();
				guess = Character.toLowerCase(guess);
				if(guess.isLetter()){
					try{
						HashSet<String> res = game.makeGuess(guess);
						repeat = false;
						if(res.size() == 0){
							System.out.print("Sorry, there are");
							System.out.printf(" no %c's\n", guess);
							guesses--;
						}
						else {
							game.src = res;
							// somehow figure out a ton of crap,
							// like the letter count and exact pattern
							// need a persistent SB with the intermediate
							// results or w/e.  other than that, looks
							// done.
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
