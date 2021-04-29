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

		Scanner input = new Scanner(System.in);													//initialize scanner for user input
		Random generator = new Random();														//initialize random number generator
		
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
		while (playGame){
		
			boolean wordGuessed = false;
			int missed_guesses = 0;
			int random_index = generator.nextInt(words.length);									//randomly select index from 'words' array
			char[] selected_word = words[random_index].toCharArray();							//convert randomly selected string to character array
			int guesses_remaining = selected_word.length *3;
			/*
			System.out.println("------------------testing------------");
			for (char letter : selected_word)
				System.out.print(letter);														//outputs the word to be guessed to make testing easier for the programmer
			System.out.println("-------------------------------------");

			*/
			
			char[] output_array = new char[selected_word.length];								//creates a new array for purposes of outputting guessing progress
			char[] copy_array = new char [selected_word.length];								//creates a second array to receive the array returned by the search_for_letter method

			for (int i = 0; i < output_array.length; i++)
			{
				output_array[i] = '*';															//fills output array with stars
			}
			System.out.println();

			for (char character : output_array)													//outputs word to be guessed with letters replaced by stars
				System.out.print(character + " ");

			while ((guess_limit == true && guesses_remaining > 0) || guess_limit == false ){	//allows game to proceed as long as player has finite number of guesses left or if player has selected the unlimited guess option
			
				while (wordGuessed == false && guesses_remaining > 0){	
					//main while loop to allows infinite iterations of hangman game
				
					int number_letters_unguessed = 0;
					if (guess_limit)
						System.out.println("\nYou have " + guesses_remaining + " guesses left");
					
					System.out.println("\nplease type a letter to guess if "					//prompt user to guess a letter
						+ "it is contained in this word");	
					
					guess = input.next().charAt(0);												//input guessed character
					guesses_remaining--;
					
					copy_array = search_for_letter(selected_word, guess);						//invoke search for letter method to check if the guessed letter is contained in the word
					fail_tracker = miss_counter(selected_word, guess);							//invokes method to count the number of incorrect guesses 

					if (fail_tracker){
						missed_guesses++;														//increments the number of incorrect guesses
					}

					for (int i = 0; i < output_array.length; i++){	
						if (copy_array[i] != '*')												//if returned array contains a letter, place that letter in the same index in the output array
							output_array[i] = copy_array[i];
						else
							number_letters_unguessed++;
						}
					
					for (int j = 0; j < output_array.length; j++)
						System.out.print(output_array[j] + " ");								//outputs array after method call so users can modify their guesses
				
					number_letters_unguessed = count_letters_left(output_array);				//calls method to determine if there are any unguessed letters remaining in output array
					if (number_letters_unguessed == 0)											//registers that word has been successfully guessed and exits inner while loop 
						wordGuessed = true;
				}
			
				//if guess limit > 0 output the message below
				if (guesses_remaining > 0) {
					System.out.println();
					System.out.println("\nCongratulations you guessed the word!!! \n");				//tells user word has been correctly guessed
					System.out.println("\n\n you had " + missed_guesses + " missed guesses \n");	//outputs number of missed guesses
					playGame = false;																

					System.out.println("would you like to play again?");							//asks user if they want to continue the game and attempt to guess a new word
					System.out.println("Please press the 'y' key for yes or the 'n' key for no");	
					answer = input.next().charAt(0);												

					if (answer == 'y')																//if answer is yes, initiate new iteration of the game with a new unknown word
						playGame = true;
					else {
						System.out.println("==================================");
						System.out.println("Thanks for playing. Goodbye!\n==================================");																
						input.close();
					}
				}		
			}		
			System.out.println("\nI'm sorry, you have run out of guesses, "
					+ "better luck next time \n");													//output game loss message and output actual word
			System.out.println("The word was:");
			for (char letter : selected_word)
				System.out.print(letter);
				
			playGame = false;																

			System.out.println("\n\nWould you like to play again?");								//asks user if they want to continue the game and attempt to guess a new word
			System.out.println("Please press the 'y' key for yes or any other key for no");	
			answer = input.next().charAt(0);												

			if (answer == 'y')																		//if answer is yes, initiate new iteration of the game with a new unknown word
				playGame = true;	
			else {
				System.out.println("==================================");
				System.out.println("Thanks for playing. Goodbye!\n==================================");																
				input.close();
			}
		}
	}
	public static char[] search_for_letter(char[] list, char letter){								//method to determine if the letter guessed is contained in the unknown word 
		int count = 0;
		char[] return_array = new char[list.length];							

		for (int i = 0; i < list.length; i++){
			return_array[i] = '*';																	//initially fills return array with stars
		}

		for (int j = 0; j < list.length; j++){
			if (list[j] == letter) {			
				return_array[j] = letter;
				count++;																			//increment count to record that the the guess was correct and a match was found
			}
		}
		if (count == 0) 																			//if count is zero, no match was found
			System.out.println("the word does not contain the letter you guessed");
			
		return return_array;																		//return resulting array with all '*' characters except possibly the correctly guessed letter
	}
	public static boolean miss_counter(char[] list, char letter){									 //method to 
		boolean incorrectGuess = true;

		for (char guessedLetter : list) {
			if (guessedLetter == letter)
				incorrectGuess = false;
		}
		return incorrectGuess;
	}
	public static int count_letters_left(char[] list) {
		
		int num_letters_left = 0;
		for (char character: list)
			if(character == '*')
				num_letters_left++;
		
		return num_letters_left;
	}
}
