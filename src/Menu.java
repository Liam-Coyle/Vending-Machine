package src;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a console-based menu.
 * @author LiamCoyle 40270954
 * @version V1.0
 */
public class Menu 
{
	private String options[];	// Array of strings representing user options.
	private String title;		// Menu title.
	private Scanner input;		// For Keyboard input.
	
	/**
	 * Constructor for Menu.
	 * @param title - the menu title.
	 * @param String Array options - the options for user selection.
	 * @throws IllegalArgumentException if title or options are null.
	 */
	public Menu(String title, String options[]) throws IllegalArgumentException
	{
		//If title or options are null, throw exception. (Throwing exception here means that I don't have to check for null values again)
		if ((title == null) || (options == null))
		{
			throw new IllegalArgumentException("parameters cannot be null");
		}
		
		this.title = title; //Sets the menu title.
		copyOptions(options); //Copy input array to options array.
		input = new Scanner(System.in); //Initialise a scanner which will be used for user input.
	}
	
	/**
	 * Requests and read a user choice.
	 * @param String message prompting the user to pick an option.
	 * @return - the user choice.
	 */
	public int getChoice(String message) 
	{
		if (message == null || message.equals("")) //If the input message is null or an empty string:
		{
			throw new IllegalArgumentException("message must have a value."); //Throw an exception.
		}
		
		int choice;
		boolean valid;
		do //While the input message is invalid:
		{
			valid = true; //Set valid flag to true at first.
			choice = getPositiveInt(message); //Get a positive integer from the user (Does not include 0).
			
			if (choice > this.options.length) //If that number is not an option in the menu:
			{
				System.out.println("\nSorry, that's not a valid option.\n"); // Print error message
				valid = false; //Set valid flag to false.
			}
		}
		while(!valid);
		return choice; //When you get a valid choice, return it.
	}
	
	/**
	 * Requests and read a user choice. (Overloaded to allow a secret option)
	 * @param String message prompting the user to pick an option.
	 * @param secretOption - a secret option which the user can choose. (Note: Must be a positive integer)
	 * @return - the user choice.
	 */
	public int getChoice(String message, int secretOption) 
	{
		if (message == null || message.equals("")) //If the input message is null or an empty string:
		{
			throw new IllegalArgumentException("message must have a value."); //Throw an exception.
		}
		
		if (secretOption < 1) //If the input secretOption < 1:
		{
			throw new IllegalArgumentException("secretOption must be < 1."); //Throw an exception.
		}
		
		int choice;
		boolean valid;
		do //While the input message is invalid:
		{
			valid = true; //Set valid flag to true at first.
			choice = getPositiveInt(message); //Get a positive integer from the user (Does not include 0).
			
			if (choice == secretOption) //If the user chooses the secret option:
			{
				return choice; //return it.
			}
			
			if (choice > this.options.length) //If that number is not an option in the menu:
			{
				System.out.println("\nSorry, that's not a valid option.\n"); // Print error message
				valid = false; //Set valid flag to false.
			}
		}
		while(!valid);
		return choice; //When you get a valid choice, return it.
	}
	
	/**
	 * Displays the menu title and menu options available, if properly defined.
	 */
	public void display()
	{
			printTitle(); //Print the menu options.
			printOptions(); //Print the menu options.
	}
	
	/**
	 * Prints the title of the menu, underlined by '+' characters.
	 */
	public void printTitle()
	{
		if (this.title.length() == 0) //If the menu title is blank:
		{
			System.out.println("NO TITLE");
			System.out.println("--------");
		}
		else //Else if the menu title is not blank.
		{
			System.out.println(this.title); //Print the title
			for (int i = 0; i < this.title.length(); i++) //Underline title with '+' characters.
			{
				System.out.print('+');
			}
		}
		System.out.println(); //Skip a line.
	}
	
	/**
	 * Prints the menu options available for selection, prefixed with an int starting at 1.
	 */
	public void printOptions()
	{
		if (options.length == 0) //If there are no options:
		{
			System.out.println("-No options to display.");
		}
		else //Else if there are options:
		{
			int count = 1; //Stores current index of menu.
			for(String str : this.options) //For every option: 
			{
				System.out.println(count + ". " + str); //Print the correct index, followed by a '.', followed by the option.
				count++; //Increment the index.
			}
		}
	}
	
	/**
	 * Initialises the options array by copying contents of data.
	 * @param data - array of Strings - Options for user to select from.
	 */
	private void copyOptions(String data[]) 
	{
		this.options = new String[data.length]; //Initialise an array of strings the same length as the input array.
		for (int index = 0; index < data.length; index++) //For every number in the 'data' array:
		{
			this.options[index] = data[index]; //Put it in the options array at the same index.
		}
	}
	
	/**
	 * Gets a positive int from the user. (Does not include 0)
	 * @param message String prompting the user to enter a positive int.
	 * @return a positive integer from user.
	 * @throws IllegalArgumentException if message is null or message is an empty string.
	 */
	private int getPositiveInt(String message) throws IllegalArgumentException
	{
		if (message == null || message.equals("")) //If the input message is null or an empty string:
		{
			throw new IllegalArgumentException("message must have a value."); //Throw an exception.
		}
		
		boolean valid;
		int number = -1; //Initialise initial number (This will be overwritten anyway)
		do //While the input integer is invalid:
		{
			valid = true; //Set valid flag to initially be true.
			System.out.print(message); //Print the message prompting the user to enter a positive int.
			
			try
			{
				number = input.nextInt(); //Read input from user.
			}
			catch (InputMismatchException e) //If user does not input an integer:
			{
				System.out.println("\nSorry, that's not a valid option\n"); //Print error message.
				input.nextLine(); //Clear the scanner buffer.
				valid = false; //Set valid flag to false.
			}
			
			if (valid) //If they entered an integer:
			{
				if (number < 1) //If the number is less than 1:
				{
					System.out.println("\nSorry, that's not a valid option\n"); //Print error message.
					input.nextLine(); //Clear the scanner buffer.
					valid = false; //Set valid flag to false.
				}
			}
		}
		while(!valid);
		return number; //When you get a valid integer, return it.
	}
}
