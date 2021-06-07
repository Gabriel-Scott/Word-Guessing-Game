import java.util.Scanner;
import java.util.Random;
/**
 * 
 * @author Gabriel Scott
 * This program is a fun little project from my data structures course.  
 * This was a 2nd year level course from my degree program.
 * 
 * This program is a simple simulation of the common word guessing game 
 * where a player (user) is given the number of letters of an unknown 
 * word and is instructed to guess, one letter at a time, the letters 
 * that comprise the mystery word.  In this implementation, the unknown 
 * word is randomly selected from an array of 30 arbitrary words that 
 * have been hard-coded.   
 * 
 * This implementation offers the user the option to play the game either
 * with a finite or an infinite number of allowed guesses.  It also outputs 
 * the number of incorrect guesses if the player successfully guesses all
 * letters in the word, or it outputs the word to be guessed if the player
 * runs out of guesses without correctly guessing all the letters.  The 
 * program also asks the player if he/she would like to play again, and 
 * if the player indicates in the affirmative it selects a new word and 
 * repeats the game.         
 *
 */
public class WordGuess														
{
	public static void main(String[] args)										
	{
		boolean playGame = true;
		char guess;
		char answer;
		boolean fail_tracker = true;
		boolean guess_limit = false;
		int selection = 0;
		boolean valid_selection = false;

		String[] words = {"falls", "battle", "kitten", "sassy", "member", "cartoon", "kettle", "towell", "window", "train", "tumble", "remember", "artist", "number", "photo", 
				"umbrella", "computer", "feather", "justice", "europe", "saxophone", "asteroid", "lumenous", "volume", "vitriol", "bumper", "xylophone", "zebra", "quaint", "quark"};
		
		//instantiate and initialize scanner for user input
		Scanner input = new Scanner(System.in);	
		//instantiate and initialize random number generator										
		Random generator = new Random();														
		
		//output the game instructions to the user
		System.out.println("==================================");
		System.out.println("Welcome to my word guessing game! \n==================================");
		System.out.println("The stars below represent the letters of the word you must guess");
		System.out.println("You will be prompted to guess one letter at a time");
		System.out.println("-------------------------------------------------------------------------"
				+ "--------------------------------------------");
		System.out.println("There are two options: option 1 gives you a limited number of guesses, "
				+ "option 2 givees you an unlimited number of guesses");
		System.out.println("---------------------------------------------------------------------------"
				+ "-------------------------------------------");
		
		//while loop to allow user to select whether they want to play the game with a finite or an infinite number of guesses allowed
		while (valid_selection == false) {
			
			System.out.println("if you would like option 1 please press the number 1 key, if you "
					+ "would like option 2 please press the number 2 key");
			
			selection = input.nextInt(); 
			
			if (selection == 1) {
				guess_limit = true;
				valid_selection = true;
			}
			else if (selection == 2)
				valid_selection = true;
			else
				System.out.println("You have made an invalid entry, please try again");
		}
		//while loop to allow user to continue playing an infinite number of new sessions of the game
		while (playGame){
		
			boolean wordGuessed = false;
			int missed_guesses = 0;

			//randomly select index from 'words' array
			int random_index = generator.nextInt(words.length);	
			//convert randomly selected string to character array
					
			char[] selected_word = words[random_index].toCharArray();
			//creates a maximum number of guesses allowed... 
			//this maximum number is directly proportional to the length of the word to be guessed 							
			int guesses_remaining = selected_word.length *3;

			//outputs the word to be guessed strictly for debugging purposes and is not necessary for the program to function correctly
			/*
			System.out.println("------------------testing------------");
			for (char letter : selected_word)
				System.out.print(letter);														
			System.out.println("-------------------------------------");

			*/
			
			//creates a new array for purposes of outputting guessing progress
			char[] output_array = new char[selected_word.length];	
			//creates a second array to receive the array returned by the search_for_letter method							
			char[] copy_array = new char [selected_word.length];								

			//fills output array with stars
			for (int i = 0; i < output_array.length; i++)
			{
				output_array[i] = '*';															
			}
			System.out.println();

			//outputs word to be guessed with letters replaced by stars
			for (char character : output_array)													
				System.out.print(character + " ");

			//allows game to proceed as long as player has finite number of guesses left or if player has selected the unlimited guess option
			while ((guess_limit == true && guesses_remaining > 0) || guess_limit == false ){	
			
				while (wordGuessed == false && guesses_remaining > 0){	
					//main while loop to allows infinite iterations of hangman game
				
					int number_letters_unguessed = 0;
					if (guess_limit)
						System.out.println("\nYou have " + guesses_remaining + " guesses left");
					
					//prompt user to guess a letter
					System.out.println("\nplease type a letter to guess if "					
						+ "it is contained in this word");	
					
					//input guessed character and decrement the number of guesses remaining
					guess = input.next().charAt(0);												
					guesses_remaining--;
					
					//invoke search for letter method to check if the guessed letter is contained in the word
					copy_array = search_for_letter(selected_word, guess);	
					//invoke method to count the number of incorrect guesses 					
					fail_tracker = miss_counter(selected_word, guess);							

					//increments the number of incorrect guesses
					if (fail_tracker){
						missed_guesses++;														
					}

					//if returned array contains a letter, place that letter in the same index in the output array
					for (int i = 0; i < output_array.length; i++){	
						if (copy_array[i] != '*')												
							output_array[i] = copy_array[i];
						else
							number_letters_unguessed++;
						}
					
					//outputs array after method call so users can modify their guesses
					for (int j = 0; j < output_array.length; j++)
						System.out.print(output_array[j] + " ");								
				
					//calls method to determine if there are any unguessed letters remaining in output array
					number_letters_unguessed = count_letters_left(output_array);	
					//registers that word has been successfully guessed and exits inner while loop			
					if (number_letters_unguessed == 0)											 
						wordGuessed = true;
				}
			
				//if guess limit > 0 output the message below
				if (guesses_remaining > 0) {
					System.out.println();
					//tells user word has been correctly guessed
					System.out.println("\nCongratulations you guessed the word!!! \n");	
					
					//outputs number of missed guesses
					System.out.println("\n\n you had " + missed_guesses + " missed guesses \n");	
					playGame = false;																

					//asks user if they want to continue the game and attempt to guess a new word
					System.out.println("would you like to play again?");							
					System.out.println("Please press the 'y' key for yes or the 'n' key for no");	
					answer = input.next().charAt(0);												

					//if answer is yes, initiate new iteration of the game with a new unknown word
					if (answer == 'y')																
						playGame = true;
					else {
						System.out.println("==================================");
						System.out.println("Thanks for playing. Goodbye!\n==================================");																
						input.close();
					}
				}		
			}	
			//output game loss message and output actual word
			System.out.println("\nI'm sorry, you have run out of guesses, "
					+ "better luck next time \n");													
			System.out.println("The word was:");
			for (char letter : selected_word)
				System.out.print(letter);
				
			playGame = false;																

			//asks user if they want to continue the game and attempt to guess a new word
			System.out.println("\n\nWould you like to play again?");								
			System.out.println("Please press the 'y' key for yes or any other key for no");	
			answer = input.next().charAt(0);												

			//if answer is yes, initiate new iteration of the game with a new unknown word
			if (answer == 'y')																		
				playGame = true;	
			else {
				System.out.println("==================================");
				System.out.println("Thanks for playing. Goodbye!\n==================================");																
				input.close();
			}
		}
	}
	//method to determine if the letter guessed is contained in the unknown word 
	public static char[] search_for_letter(char[] list, char letter){								
		int count = 0;
		char[] return_array = new char[list.length];							

		//initially fills return array with stars
		for (int i = 0; i < list.length; i++){
			return_array[i] = '*';																	
		}

		//increment count to record that the the guess was correct and a match was found
		for (int j = 0; j < list.length; j++){
			if (list[j] == letter) {			
				return_array[j] = letter;
				count++;																			
			}
		}
		//if count is zero, no match was found
		if (count == 0) 																			
			System.out.println("the word does not contain the letter you guessed");
		
		//return resulting array with all '*' characters except possibly the correctly guessed letter	
		return return_array;
	}

	//method to track whether a guessed letter was not contained in the word
	public static boolean miss_counter(char[] list, char letter){	
								
		boolean incorrectGuess = true;

		for (char guessedLetter : list) {
			if (guessedLetter == letter)
				incorrectGuess = false;
		}
		return incorrectGuess;
	}

	//method to count the number of unguessed letters remaining in the word
	public static int count_letters_left(char[] list) {
		
		int num_letters_left = 0;
		for (char character: list)
			if(character == '*')
				num_letters_left++;
		
		return num_letters_left;
	}
}
