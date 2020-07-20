package src;

import java.io.File;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class QUBVendingMachineSystem
{
	
	/**
	 * This is the application class for the QUB Vending machine.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */
	
	//Declare vending machine (Allows users to change easily).
	private static final String VENDING_MACHINE_OWNER = "QUB";
	private static final int VENDING_MACHINE_MAX_ITEMS = 10; //WARNING: You should reset the vending machine before changing this value, as it could result in errors.
	private static VendingMachine queensVendingMachine;
	
	//Declare menus/options (Allows users to change easily)
	private static final String CONFIG_TITLE = "QUB VendingMachine - System Configuration";
	private static final String[] CONFIG_OPTIONS = {"User view", "View data", "Switch mode","Load coins", "Restock item", "Add item", "Remove item", "Reset to factory default", "Turn off"}; //WARNING: Changing length of array will break the program. CHANGE NAMES AT OWN RISK.
	private static Menu configMenu;
	
	//Declare menus/options (Allows users to change easily)
	private static final String MAIN_TITLE = "QUB VendingMachine"; //Main menu title
	private static final String[] MAIN_OPTIONS = {"Purchase item", "Insert coin"}; //Main menu options. WARNING: Changing length of array will break the program. CHANGE NAMES AT OWN RISK.
	private static Menu mainMenu;
	
	private static final String PURCHASE_TITLE = "QUB VendingMachine"; //Purchase menu title.
	private static Menu purchaseMenu;
	
	private static final String COIN_TITLE = "QUB VendingMachine"; //Insert coin menu title.
	private static final String[] COIN_LIST = {"�2", "�1", "�0.50", "�0.20", "�0.10", "�0.05", "Back"}; //Insert coin menu options. WARNING: Changing length of array will break the program. CHANGE NAMES AT OWN RISK.
	private static Menu insertCoinMenu;
	
	private static final String SELECT_ITEM_TITLE = "QUB VendingMachine"; //Select item menu title.
	private static Menu selectItemMenu;
	
	private static final String CONFIRMATION_TITLE = "QUB VendingMachine"; //Confirmation menu title.
	private static final String[] CONFIRMATION_OPTIONS = {"Yes", "No"}; //Confirmation menu options. WARNING: Changing length of array will break the program. CHANGE NAMES AT OWN RISK.
	private static Menu confirmationMenu;
	
	private static final String REMOVE_ITEM_TITLE = "QUB VendingMachine"; //Remove Item menu title.
	private static Menu removeItemMenu;
	
	//Initialises password for config menu.
	private static final int PASSWORD = 987;
	
	//Initialise scanner for keyboard input
	private static Scanner input = new Scanner(System.in);
	
	//Specify directory which state will be saved to.
	private static final String CSV_FILE_PATH = "VendingMachineState.csv";

	public static void main(String[] args)
	{
		//Initialise the menus and the vending machine, and load state from CSV file (If it exists)
		setup();
			
		//Main program
		configView(); //Starts the machine in system configuration menu.
		exitRoutine(0); //Exits the program when turned off.
	}
	
	/**
	 * Shows the config menu and gets user choice.
	 */
	public static void configView()
	{	
		//Show the config menu.
		configMenu.display();
		System.out.println();
		
		int choice = configMenu.getChoice("Enter choice: "); //Get the choice of the user.
		while (choice != CONFIG_OPTIONS.length) //While the user doesn't turn off the machine
		{
			processConfigChoice(choice); //Process their choice.
			
			printConfigMenu();
			choice = configMenu.getChoice("Enter choice: "); //Get their choice again.
		}
	}
	
	/**
	 * Shows the main menu and gets user choice.
	 */
	private static void mainView()
	{
		//Show the main menu
		printMainMenu();
		
		int choice = mainMenu.getChoice("Enter choice: ", PASSWORD); //Get the choice of the user.
		while (choice != PASSWORD) //While the user doesn't turn off the machine
		{
			processMainChoice(choice); //Process their choice.
			
			//When they return to this menu: (By selecting back/purchasing an item/ inserting a coin)
			printMainMenu(); //Show the main menu.
			choice = mainMenu.getChoice("Enter choice: ", PASSWORD); //Get their choice again.
		}
	}
	
	/**
	 * Shows the purchase menu and gets user choice.
	 */
	private static void purchaseView()
	{
		String[] itemList = getItemListWithBackOption(); //Gets the item list with a 'back' option appended.
		purchaseMenu = null; //Initialises menu to null temporarily, until it is validated.
		try
		{
			purchaseMenu = new Menu(PURCHASE_TITLE, itemList); //Initialise the purchase menu.			
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Show the purchase menu.
		printPurchaseMenu();
		
		int choice = purchaseMenu.getChoice("Choose an item to purchase: "); //Get the choice of the user.
		if (choice != itemList.length) //If the user doesn't select 'back.'
		{
			processPurchaseChoice(choice); //Process their choice before returning to main menu.
		}
		System.out.println(); //Skip a line before returning to main menu.
	}
	
	/**
	 * Shows the coin menu and gets user choice.
	 */
	private static void insertCoinView()
	{
		//Show the coin menu.
		printCoinMenu();
		
		int choice = insertCoinMenu.getChoice("Choose a coin to insert: "); //Get the choice of the user.
		if (choice != COIN_LIST.length) //If the user doesn't select 'back'
		{
			processCoinChoice(choice); //Process their choice.
		}
		System.out.println(); //Skip a line before returning to main menu.
	}
	
	/**
	 * Shows the re-stock item menu and gets user choice.
	 */
	private static void restockItemView()
	{
		String[] itemList = getItemListWithBackOption(); //Gets the item list with a 'back' option appended.
	
		try
		{
			selectItemMenu = new Menu(SELECT_ITEM_TITLE, itemList); //Define the restock menu.			
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Show the re-stock menu.
		printRestockMenu();
		
		int choice = selectItemMenu.getChoice("Choose an item to restock: "); //Get the choice of the user.
		if (choice != itemList.length) //If the user doesn't select 'back.'
		{
			processRestockChoice(choice); //Process their choice before returning to the main menu.
		}
	}
	
	/**
	 * Asks the user if they are sure they want to reset to factory default.
	 */
	private static void resetToFactoryDefaultView()
	{
		//Show the resetToFactoryDefault menu.
		confirmationMenu.display();
		
		int choice = confirmationMenu.getChoice("\nAre you sure you want to reset to factory default?\nAll items and money will be lost: ");
		if (choice != CONFIRMATION_OPTIONS.length) //If the user does not select no (ie: Selects yes)
		{
			//Reset the machine to default
			processResetToFactoryDefaultChoice();
		}
	}
	
	/**
	 * Shows remove item menu and gets choice.
	 */
	private static void removeItemView()
	{
		String[] itemList = getItemListWithBackOption(); //Gets the item list with a 'back' option appended.
		
		try
		{
			removeItemMenu = new Menu(REMOVE_ITEM_TITLE, itemList); //Define the remove item menu.			
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Show the remove item menu.
		printRemoveItemMenu();
		
		int choice = removeItemMenu.getChoice("Choose an item to remove: "); //Get the choice of the user.
		if (choice != itemList.length) //If the user doesn't select 'back.'
		{
			processRemoveChoice(choice); //Process their choice before returning to the main menu.
		}
	}

	/**
	 * Processes the chosen option which was chosen in the config menu.
	 * @param choice the option chosen in the config menu.
	 */
	public static void processConfigChoice(int choice)
	{
		System.out.println(); //Skip a line.
		switch(choice) //Run the correct method based on which option the user has chosen.
		{
			case 1: mainView(); //1. User view
					break;
			case 2: System.out.print(queensVendingMachine.getSystemInfo()); //2. View data
					break;
			case 3: switchMode(); //3. Switch mode
					break;
			case 4: loadFloatCoins(); //4. Load coins
					break;
			case 5: restockItemView(); //5. Restock item
					break;
			case 6: addItem(); //6. Add item
					break;
			case 7: removeItemView(); //7. Remove item
					break;
			case 8: resetToFactoryDefaultView(); //8. Reset to factory default
					break;
		}
	}

	/**
	 * Processes the chosen option which was chosen in the main menu.
	 * @param choice the option chosen in the main menu.
	 */
	private static void processMainChoice(int choice)
	{
		System.out.println(); //Skip a line.
		switch(choice) //Run the correct method based on which option the user has chosen.
		{
			case 1: purchaseView();   //1. Purchase item
					break;
			case 2: insertCoinView(); //2. Insert coin
					break;
		}
	}
	
	/**
	 * Processes the chosen option which was chosen in the purchase menu.
	 * @param choice the option chosen in the purchase menu.
	 */
	private static void processPurchaseChoice(int choice)
	{
		System.out.println('\n' + queensVendingMachine.purchaseItem(choice - 1) + '\n'); //Print the result of the purchase.
	}
	
	/**
	 * Processes the chosen option which was chosen in the coin menu.
	 * @param choice the option chosen in the coin menu.
	 */
	private static void processCoinChoice(int choice)
	{
		System.out.println(); //Skip a line.
		switch(choice) //Run the correct method based on which option the user has chosen.
		{
			case 1: queensVendingMachine.insertCoin(2);   //1. �2
					break;
			case 2: queensVendingMachine.insertCoin(1);   //2. �1
					break;
			case 3: queensVendingMachine.insertCoin(50);  //3. �0.50
					break;
			case 4: queensVendingMachine.insertCoin(20);  //4. �0.20
					break;
			case 5: queensVendingMachine.insertCoin(10);  //5. �0.10 
					break;
			case 6: queensVendingMachine.insertCoin(5);   //6. �0.05
					break;
		}
	}
	
	/**
	 * Processes the chosen option which was chosen in the re-stock item menu.
	 * @param choice the option chosen in the re-stock item menu.
	 */
	private static void processRestockChoice(int choice)
	{
		System.out.println(); //Skip a line.
		int amount = getPositiveInt("Enter amount to restock by (or 0 to cancel): "); //Get a positive integer to re-stock by.
			
		if (amount == 0) //If user re-stocked by 0, return to the main menu.
		{
			return;
		}
		
		//If re-stocking by that amount would put the quantity over 10:
		if (!queensVendingMachine.getVendItem(choice - 1).restock(amount))
		{
			System.out.println("\nSorry, item quantity cannot exceed 10."); //Print error message and return to menu.
			input.nextLine(); //Clear enter character from scanner buffer.
		}

		else //Else, re-stock the item by that amount and print string confirming the amount re-stocked.
		{
			System.out.println("\nRestocked " + queensVendingMachine.getVendItem(choice - 1).getName() + " by " + amount + '.');			
		}
	}
	
	/**
	 * Processes the chosen option which was chosen in the resetToFactoryDefault menu.
	 */
	private static void processResetToFactoryDefaultChoice()
	{
		System.out.println("\nRestoring vending machine to factory default...");
		queensVendingMachine.reset(); //Reset the vending machine to default.
		System.out.println("Reset complete.");
	}
	
	/**
	 * Processes the chosen option which was chosen in the removeItem menu.
	 * @param choice the option chosen in the removeItem menu.
	 */
	private static void processRemoveChoice(int choice)
	{
		System.out.println(); //Skip a line.
		confirmationMenu.display(); //Show the confirmation menu.
		
		int decision = confirmationMenu.getChoice("\nAre you sure you want to to remove " + queensVendingMachine.getVendItem(choice - 1).getName() + "?\nThis cannot be undone: ");
		if (decision != CONFIRMATION_OPTIONS.length) //If the user does not select no (ie: Selects yes)
		{
			//Remove the item.
			System.out.println("\nRemoving...");
			queensVendingMachine.removeItem(choice - 1);
			System.out.println("Successfully removed item.");
		}
	}
	
	/**
	 * Switches mode between SERVICE_MODE and VENDING_MODE.
	 */
	private static void switchMode()
	{
		//If the current status of the machine is SERVICE_MODE:
		if (queensVendingMachine.getVmStatus() == Status.SERVICE_MODE)
		{	
			//For every item in the vending machine:
			for (int index = 0; index < queensVendingMachine.getItemCount(); index++)
			{
				//If you find an item which has a qty > 0:
				if (queensVendingMachine.getVendItem(index).getQty() != 0)
				{
					queensVendingMachine.setStatus(Status.VENDING_MODE); //Set the status of the machine to VENDING_MODE.
					System.out.println("Mode changed to VENDING_MODE.");
					return; //Stop the method.
				}
			}
			
			//Else the machine must be empty.
			System.out.println("Sorry, failed to switch modes. Vending machine is empty."); //Notify the user of invalid switch.
		}
		
		else //If the current status of the machine is in VENDING_MODE:
		{
			queensVendingMachine.setStatus(Status.SERVICE_MODE); //Set the status of the machine to SERVICE_MODE.
			System.out.println("Mode changed to SERVICE_MODE.");
		}
	}

	/**
	 * Uses user input to add a new item to the vending machine.
	 */
	private static void addItem()
	{
		//If the vending machine is at it's maximum capacity:
		if (queensVendingMachine.getItemCount() == queensVendingMachine.getMaxItems())
		{
			System.out.println("Sorry, this machine is at full capacity."); //Print error message.
			return; //Stop the method.
		}
		
		//Else, ask the user to input a name, unit price, and quantity available for their new item -
		//and add it to the vending machine.
		boolean success = queensVendingMachine.addNewItem(new VendItem(getNewItemName(), getNewItemUnitPrice(), getNewItemQtyAvailable()));
		
		//If the user tried to add a VendItem which was already in the vending machine
		if (!success)
		{
			System.out.println("Sorry, this item is already in the vending machine.");
			return; //Stop the method.
		}
	}
	
	/**
	 * Allows the engineer to load machine with coins (as a float) without changing userMoney.
	 */
	private static void loadFloatCoins()
	{
		final String[] DENOMINATIONS = {"�2", "�1", "50p", "20p", "10p", "5p"};
		int[] coinsAvailable = {0, 0, 0, 0, 0, 0};
		
		//For every denomination:
		for (int index = 0; index < DENOMINATIONS.length; index++)
		{
			//Set up request string.
			String request = "Number of " + DENOMINATIONS[index] + " coins to load: ";
			
			//If the denomination is �2, �1, or 5p, add an extra space so that it lines up neatly.
			if (DENOMINATIONS[index].length() == 2)
			{
				request += " ";
			}
			
			//Get the qty to load and save to coinsAvailable array.
			coinsAvailable[index] = getPositiveInt(request);
		}
		System.out.println("Loading coins...");
		queensVendingMachine.loadFloatCoins(coinsAvailable); //Update state of vending machine to contain correct number of each coin.
		System.out.println("Coins successfully loaded.");
	}
	
	/**
	 * Prints the items in the vending machine prefixed with '-'.
	 * @param itemList String array containing VendItem info.
	 */
	private static void printItemList(String[] itemList)
	{
		if (itemList.length == 0) //If there are no items:
		{
			System.out.println("-No items to display"); //Print this string.
		}
		
		for (String info: itemList) //Else
		{
			System.out.println('-' + info); //Print the info prefixed with '-'.
		}
	}
	
	/**
	 * Prints the config menu.
	 */
	private static void printConfigMenu()
	{
		System.out.println(); //Skip a line.
		configMenu.display(); //Print the title and options.
		System.out.println(); //Skip a line.
	}
	
	/**
	 * Prints the main menu.
	 */
	private static void printMainMenu()
	{
		mainMenu.printTitle(); //Print the title.
		printItemList(queensVendingMachine.listItems()); //Print the list of items available.
		System.out.println("\nBalance: �" + String.format("%.2f", queensVendingMachine.getUserMoney()) + '\n'); //Print current balance.
		mainMenu.printOptions(); //Print the meny options.
		System.out.println(); //Skip a line.
	}
	
	/**
	 * Prints the purchase menu.
	 */
	private static void printPurchaseMenu()
	{
		purchaseMenu.display(); //Prints the title and options of the menu.
		System.out.println("\nBalance: �" + String.format("%.2f", queensVendingMachine.getUserMoney()) + '\n'); //Prints the current balance.
	}
	
	/**
	 * Prints the coin menu.
	 */
	private static void printCoinMenu()
	{
		insertCoinMenu.display(); //Prints the title and options of the menu.
		System.out.println("\nBalance: �" + String.format("%.2f", queensVendingMachine.getUserMoney()) + '\n'); //Prints the current balance.
	}
	
	/**
	 * Prints the re-stock menu.
	 */
	private static void printRestockMenu()
	{
		selectItemMenu.display(); //Prints the title and options of the menu.
		System.out.println(); //Skips a line.
	}
	
	private static void printRemoveItemMenu()
	{
		removeItemMenu.display(); //Prints the title and options of the menu.
		System.out.println(); //Skips a line.
	}
	
	/**
	 * Appends a 'back' option to the list of items.
	 * @return String array - the list of items with a 'back' option appended. 
	 */
	private static String[] getItemListWithBackOption()
	{
		String[] itemList = queensVendingMachine.listItems(); //Get the list of items available for sale.
		itemList = Arrays.copyOf(itemList, itemList.length + 1); //Add 1 slot to itemList, to make room for 'back' option.
		itemList[itemList.length - 1] = "Back"; //Add "back" option to options.
		return itemList; //Return the string array.
	}
	
	/**
	 * Gets a positive int from the user. (INCLUDES 0)
	 * @param message String prompting the user to enter a positive int.
	 * @return a positive integer from user.
	 * @throws IllegalArgumentException if message is null or message is an empty string.
	 */
	private static int getPositiveInt(String message) throws IllegalArgumentException
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
				if (number < 0) //If the number is less than 1:
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
	
	/**
	 * Gets a valid name for a VendItem from the user.
	 * @return String name which is valid for a VendItem.
	 */
	private static String getNewItemName()
	{
		String name;
		boolean valid; 
		
		//While the inputed string is INVALID:
		do
		{
			valid = true; //Set the valid flag to true.
			System.out.print("Item name: ");
			name = input.nextLine(); //Get the required name.
			
			if (name.contentEquals("")) //If it's an empty string, it is invalid.
			{
				valid = false; //Set the valid flag to false.
				System.out.println("\nSorry, that name is invalid. Please try again\n"); //Print error message.
			}
		}
		while(!valid);
		System.out.println(); //Skip a line.
		return name; //Return the valid string.
	}
	
	/**
	 * Gets a valid unit price for a VendItem from the user.
	 * @return double unitprice which is valid for a VendItem.
	 */
	private static double getNewItemUnitPrice()
	{
		double unitPrice = 0;
		double unitPricePence = 0;
		boolean valid;
		
		//While the inputed double is INVALID:
		do
		{
			valid = true; //Set the valid flag to true.
			System.out.print("Unit price: �");
			
			try
			{
				unitPrice = input.nextDouble(); //Get the input double.
			}
			catch (InputMismatchException e) //If the user did not input a double:
			{
				System.out.println("\nSorry, unit price must be a number. Please try again\n"); //Print error message.
				input.nextLine(); //Flush the scanner buffer.
				valid = false; //Set the valid flag to false.
			}
			
			//If the user DID input a double
			if (valid)
			{
				unitPricePence = (int) (Math.round(unitPrice * 100)); //Convert the double to pence.
				
				if (unitPricePence <= 0 || unitPricePence > 200) //If the unit price is not within the range of allowed prices:
				{
					System.out.println("\nSorry, unitPrice must be >�0 and <=�2. Please try again\n"); //Print error message.
					valid = false; //Set valid flag to false.
				}
			}
			
			//If the user DID input a double AND the unit price IS within the range of allowed prices
			//ie: If the input is still valid by this point
			if (valid)
			{
				if (unitPricePence % 5 != 0) //If the unit price is not divisible by 5p:
				{
					System.out.println("\nSorry, unit price must be divisible by 5p.\n"); //Print error message.
					valid = false; //Set the valid flag to false.
				}
			}
		}
		while(!valid);
		unitPrice = unitPricePence / 100.0; //Calculate the unit price as a double.
		System.out.println(); //Skip a line.
		return unitPrice; //Return the valid double.
	}
	
	/**
	 * Gets a valid quantity available for a VendItem from the user.
	 * @return valid quantity available for a VendItem.
	 */
	private static int getNewItemQtyAvailable()
	{
		int qtyAvailable = 0;
		boolean valid;
		
		//While the inputed int is INVALID:
		do
		{
			valid = true; //Set the valid flag to true.
			System.out.print("Quantitiy available: ");
			
			try
			{
				qtyAvailable = input.nextInt(); //Get the input int.
				input.nextLine(); //Clear the scanner buffer of the return character.
			}
			catch(InputMismatchException e) //If the user did not input an int:
			{
				System.out.println("\nSorry, quantitiy available must be a number. Please try again\n"); //Print error message.
				input.nextLine(); //Clear the buffer of whatever the user entered.
				valid = false; //Set valid flag to false.
			}
			
			//If the user DID input an int:
			if (valid)
			{
				if (qtyAvailable < 0 || qtyAvailable > 10) //If the qtyAvailable is not within the range of allowed quantities:
				{
					System.out.println("\nSorry, quantity available must be between 0 and 10 (inclusive). Please try again\n"); //Print error message.
					valid = false; //Set valid flag to false.
				}
			}
		}
		while(!valid);
		return qtyAvailable; //Return the valid int.
	}
	
	/**
	 * Initialises the config, main, and coin menu.
	 * NOTE: Does not initialise the purchase menu because it must be constantly changed based on state of vending machine.
	 */
	private static void initialiseMenus()
	{
		//Initialise the config menu.
		try
		{
			configMenu = new Menu(CONFIG_TITLE, CONFIG_OPTIONS);			
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Initialise the main menu.
		try
		{
			mainMenu = new Menu(MAIN_TITLE, MAIN_OPTIONS);
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Initialise the coin menu.
		try
		{
			insertCoinMenu = new Menu(COIN_TITLE, COIN_LIST); 
		}
		catch (IllegalArgumentException e) //If the menu was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
		
		//Initialise the confirmationMenu
		try
		{
			confirmationMenu = new Menu(CONFIRMATION_TITLE, CONFIRMATION_OPTIONS);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
	}
	
	/**
	 * Initialises the vending machine.
	 */
	private static void initialiseVendingMachine()
	{
		try
		{
			queensVendingMachine = new VendingMachine(VENDING_MACHINE_OWNER, VENDING_MACHINE_MAX_ITEMS);			
		}
		catch (IllegalArgumentException e) //If the vending machine was attempted to be initialised with invalid arguments:
		{
			e.printStackTrace(); //Print the stack trace.
			exitRoutine(1); //Exit the program with a value of 1 to indicate an error.
		}
	}
	
	/**
	 * Initialises menus and the vending machine.
	 */
	private static void setup()
	{
		initialiseVendingMachine(); //Initialises the vending machine.
		initialiseMenus(); //Initialises the menus.
		
		//If a file exists at the specified path:
		File specifiedDirectory = new File(CSV_FILE_PATH);
		if (specifiedDirectory.exists())
		{
			queensVendingMachine.loadState(CSV_FILE_PATH); //Load state of the vending machine from memory.			
		}
		//Else, machine will be in it's default state.
	}
	
	/**
	 * Shuts down the system properly (Closes scanner, saves state, exits)
	 * @param errorCode error code to exit program with (0 if no problems)
	 */
	private static void exitRoutine(int errorCode)
	{
		System.out.println("Shutting down..."); //When the machine is turned off, this will be printed.
		input.close(); //Close the scanner.
		queensVendingMachine.saveState(CSV_FILE_PATH); //Save the state of the machine.
		System.exit(errorCode); //Shuts down the program.
	}
}
