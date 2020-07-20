package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class VendingMachine
{
	
	/**
	 * This class represents a vending machine.
	 * @author 40270954
	 * @version V1.0
	 */
	
	/**
	 * Rounds a number to 2 decimal places (Reduces floating point imprecision)
	 * @param amount double amount to round.
	 * @return double amount rounded to 2dp.
	 */
	private static double roundToTwoDP(double amount)
	{
		int temp = (int) (Math.round(amount * 100)); //Multiply amount by 100 and cast it to an int (Gets rid of everything after 2dp).
		return (temp / 100.0); //Returns the temp value to 2dp.
	}
	
	private String owner; //The owner of the vending machine.
	private int maxItems; //The maximum number of VendItem objects which can be stored.
	private int itemCount; //The actual number of items for sale.
	private VendItem[] stock; //Holds references to the VendItem objects which are for sale.
	private double totalMoney; //The total amount of cash in the machine.
	private double userMoney; //The money entered by a user for a purchase.
	private Status vmStatus; //The status of the vending machine (VENDING_MODE or SERVICE_MODE)
	private int[] coinsAvailable; //Holds how many of each type of coin are available. (�2, �1, 50p, 20p, 10p, 5p)
	
	/**
	 * The constructor method for VendingMachine.
	 * The vending machine's stock array is set to all null values by default.
	 * The default value for totalMoney is 0.
	 * The default value for userMoney is 0.
	 * The default value for vmStatus is SERVICE_MODE.
	 * @param owner String owner of the vending machine.
	 * @param maxItems the maximum number of VendItem objects the vending machine can store.
	 */
	public VendingMachine(String owner, int maxItems)
	{
		this.setOwner(owner); //Set the owner of the vending machine using a setter for validation.
		this.setMaxItems(maxItems); //Set the max items using a setter for validation.
		stock = new VendItem[this.getMaxItems()]; //Creates stock array of size max items.
		this.itemCount = 0; //Sets the number of items for sale to 0 by default.
		this.totalMoney = 0; //Sets the total money in the machine to 0 by default.
		this.userMoney = 0; //Sets the money entered by a user to 0.
		this.vmStatus = Status.SERVICE_MODE; //Sets the status to SERVICE_MODE by default.
		this.coinsAvailable = new int[6]; //Holds how many of each type of coin are available. (�2, �1, 50p, 20p, 10p, 5p)
	}

	/**
	 * Re-initialises a vending machine instance; Empties machine of items and cash.
	 */
	public void reset()
	{
		this.emptyStockArray(); //Sets all slots in stock array to null.
		this.emptyCoinsAvailableArray(); //Empty the machine of coins.
		this.setUserMoney(0); //Sets user money to 0.
		this.setTotalMoney(0); //Sets total money to 0.
		this.itemCount = 0; //Sets itemcount to 0.
		this.setStatus(Status.SERVICE_MODE); //Set status to service mode.
	}

	/**
	 * Request the purchase of an item.
	 * @parameter the index of the item to be purchased in the stock array.
	 * @return String "Thanks for purchasing (item-name)" if item is available.
	 * 		   String "Sorry, this machine is out of order." if VendingMachine is in SERVICE_MODE.
	 * 		   String "Sorry, that item is out of stock." if the selected VendItem is out of stock.
	 * 		   String "Sorry, you have insufficient funds to purchase that." if userMoney is less than 
	 * 			       the unit price of the selected VendItem.
	 * @throws IndexOutOfBoundsException if given index is out of range of stock array.
	 */
	public String purchaseItem(int index) throws IndexOutOfBoundsException
	{
		//If index is out of range of the stock array, throw an exception.
		if ((index < 0 || index > this.getMaxItems() - 1))
		{
			throw new IndexOutOfBoundsException("index out of range of stock array");
		}
				
		//If the current status of the vending machine is SERVICE_MODE:
		if (this.getVmStatus() == Status.SERVICE_MODE)
		{
			return ("Sorry, this machine is out of order."); //Return this string.
		}
		
		//If the item is out of stock:
		if (this.stock[index].getQty() < 1)
		{
			return ("Sorry, that item is out of stock."); //Return this string.
		}
		
		//If the user doesn't have enough money:
		if (this.getUserMoney() < this.stock[index].getPrice())
		{
			return ("Sorry, you have insufficient funds to purchase that."); //Return this string.
		}
		
		//Else, everything must be OK.
		double changeDue = this.getUserMoney() - this.stock[index].getPrice(); // Calculate change due.
		String changeMessage = dispenseCoins(changeDue); //Dispense correct coins and save the combination of coins dispensed in a string.
		this.setUserMoney(0); //Reset usermoney to 0.
		
		//Store result of deliver method and change due.
		String result = "Change due: �" + String.format("%.2f", changeDue) + '\n' +
				changeMessage + '\n' + (this.stock[index].deliver()); 
		
		stockCheck(); //Set the vending machine status to SERVICE_MODE if that was the last item in the vending machine.
		
		return result;
	}

	/**
	 * Inserts a coin into the vending machine.
	 * @return boolean true if the denomination entered is 5, 10, 20, 50, 1 or 2. Else returns false.
	 */
	public boolean insertCoin(int denomination)
	{	
		int[] acceptable = {2, 1, 50,   20,   10,   5};    //Defines the acceptable coin denominations.
		double[] actual =  {2, 1, 0.50, 0.20, 0.10, 0.05}; //Defines the double values of these denominations.
		
		for (int index = 0; index < 6; index++) //For every denomination:
		{
			if (denomination == acceptable[index]) //If the denomination inserted is a valid denomination:
			{
				addCoin(acceptable[index]); //Adds the coin to vending machine storage to be used to give change.
				this.setUserMoney(this.getUserMoney() + actual[index]);   //Update userMoney.
				this.setTotalMoney(this.getTotalMoney() + actual[index]); //Update totalMoney.
				return true; //Return true to signify coin was accepted.
			}
		}
		return false; //Else, return false to signify coin was not accepted.
	}

	/**
	 * Adds a new item to the vending machine.
	 * @return boolean true if item is added successfully, else returns false.
	 * @throws IllegalArgumentException if the item reference given is null.
	 */
	public boolean addNewItem(VendItem item) throws IllegalArgumentException
	{
		//If the item is null, throw an exception.
		if (item == null)
		{
			throw new IllegalArgumentException("item cannot be null.");
		}
		
		//Else if there is no room for the new item, return false.
		else if (this.getItemCount() == this.getMaxItems())
		{
			return false;
		}
		
		//Else
		else
		{
			//For every item in the vending machine
			for (int index = 0; index < this.getItemCount(); index++)
			{
				//If it's unique id matches the unique id of the item we are trying to add, it means they are the same item
				if (this.getVendItem(index).getItemId() == item.getItemId())
				{
					return false; //So return false.
				}
			}
		}
		
		//Else
		//Everything must be OK.
		this.stock[this.getItemCount()] = item; //Add the item to the stock array.
		this.itemCount++; //Increment itemCount.
		return true;
	}

	/**
	 * Returns an array of formatted Strings containing item name, price, and quantity available.
	 * @return an array of formatted Strings containing item name, price, and quantity available.
	 */
	public String[] listItems()
	{
		String[] result = new String[this.itemCount]; //Initialise String array of length itemCount which will hold item info.
		
		//For every item in stock
		for (int index = 0; index < this.getItemCount(); index++)
		{ 
			//Store it's name, quantity and price.
			String info = this.getVendItem(index).getName() + ": �" + String.format("%.2f", this.getVendItem(index).getPrice()) + " (" + this.getVendItem(index).getQty() + " available)";
			result[index] = info;
		}
		return result; //Return the String array containing item info.
	}

	/**
	 * Returns formatted string containing all details of a vending machine instance.
	 * Includes: Owner, Max items, item count, total money, user money, status, stock (Item name, price, and quantity of each item).
	 * @return Formatted string containing all details of a vending machine instance.
	 */
	public String getSystemInfo()
	{
		String result = "VENDING MACHINE STATUS INFO\n";
		result +=       "---------------------------\n";
		result +=       "Owner: " + this.getOwner() + '\n';
		result +=       "Max items: " + this.getMaxItems() + '\n';
		result +=       "Item count: " + this.getItemCount() + '\n';
		result +=       "Total money: " + String.format("%.2f", this.getTotalMoney()) + '\n';
		result +=       "User money: " + String.format("%.2f", this.getUserMoney()) + '\n';
		result +=       this.vmStatus.getStatus() + '\n';
		result +=       this.getCoinsAvailableList();
		result +=       "Stock\n-----\n";
		
		//For every item
		for (int index = 0; index < this.getItemCount(); index++)
		{
			String[] itemInfo = this.listItems(); //Get an array containing the list of items for sale.
			result += itemInfo[index] + " (id: " + this.getVendItem(index).getItemId() + ")\n"; //Add the item id to the VendItem info and append it to the result string.
		}
	
		return result; //Return the String.
	}

	/**
	 * Getter method for vending machine owner.
	 * @return String owner of the vending machine.
	 */
	public String getOwner()
	{
		return this.owner;
	}

	/**
	 * Getter method for max items.
	 * @return the maximum number of VendItem objects which can be stored.
	 */
	public int getMaxItems()
	{
		return this.maxItems;
	}

	/**
	 * Getter method for item count.
	 * @return the actual number of items for sale.
	 */
	public int getItemCount()
	{
		return this.itemCount;
	}

	/**
	 * Getter method for total money.
	 * @return the total money currently in the machine.
	 */
	public double getTotalMoney()
	{
		return this.totalMoney;
	}

	/**
	 * Getter method for user money.
	 * @return a double representing the money entered by a user for a purchase.
	 */
	public double getUserMoney()
	{
		return this.userMoney;
	}
	
	/**
	 * Getter method for VendItem objects in the stock array.
	 * @param index the index of the VendItem in the stock array to get.
	 * @return the VendItem at index in the stock array.
	 */
	public VendItem getVendItem(int index)
	{
		//If the index is within the range of the stock array:
		if ((index >= 0) && (index <= this.getMaxItems() - 1))
		{
			return this.stock[index]; //Return the VendItem at this index.
		}
		return null; //Else return null.
	}
	
	/**
	 * Getter method for the vending machine status.
	 * @return the status of the vending machine (VENDING_MODE / SERVICE_MODE)
	 */
	public Status getVmStatus()
	{
		return this.vmStatus;
	}

	/**
	 * Setter method for vending machine status.
	 * @param stat the Status to set
	 * @throws IllegalArgumentException if stat is null.
	 */
	public void setStatus(Status stat) throws IllegalArgumentException
	{
		//If stat is null, throw an exception.
		if (stat == null)
		{
			throw new IllegalArgumentException("stat cannot be null.");
		}
		
		else
		{
			this.vmStatus = stat; //Else, set the vmStatus.
		}
	}
	
	/**
	 * Sets the owner of the vending machine.
	 * @param owner String name of owner to set.
	 * @throws IllegalArgumentException if input string is null.
	 */
	private void setOwner(String owner) throws IllegalArgumentException
	{
		//If input string is null or an empty string, throw an exception.
		if (owner == null || owner.equals(""))
		{
			throw new IllegalArgumentException("owner must have a value.");
		}
		
		else
		{
			this.owner = owner; //Set the owner.
		}
	}
	
	/**
	 * Sets the maximum number of items for sale.
	 * @param maxItems the number to set.
	 * @throws IllegalArgumentException if max items is less than 1.
	 */
	private void setMaxItems(int maxItems) throws IllegalArgumentException
	{
		if (maxItems < 1) //If max items is less than 1, throw an exception.
		{
			throw new IllegalArgumentException("maxItems must be at least 1");
		}	
		else
		{
			this.maxItems = maxItems; //Else, set the maxItems.
		}
	}
	
	/**
	 * Sets the user money : the amount of money entered by a user for a purchase.
	 * @param userMoney double value representing the amount of money entered by a user for a purchase.
	 * @throws IllegalArgumentException if input amount is negative.
	 */
	private void setUserMoney(double userMoney) throws IllegalArgumentException
	{
		//If the amount entered is negative, throw an exception.
		if (userMoney < 0)
		{
			throw new IllegalArgumentException("userMoney cannot be negative");
		}
		
		else
		{
			this.userMoney = roundToTwoDP(userMoney); //Sets userMoney (Rounded to 2dp).
		}
	}
	
	/**
	 * Sets the total amount of money in the machine.
	 * @param totalMoney the amount of money entered by a user for a purchase.
	 * @throws IllegalArgumentException if input amount is negative.
	 */
	private void setTotalMoney(double totalMoney) throws IllegalArgumentException
	{
		//If the amount entered is negative, throw an exception.
		if (totalMoney < 0)
		{
			throw new IllegalArgumentException("totalMoney cannot be negative");
		}
		
		else
		{
			this.totalMoney = roundToTwoDP(totalMoney); //Sets totalmoney (Rounded to 2dp).
		}
	}
	
	/**
	 * Sets the vending machine status to SERVICE_MODE if every item is out of stock.
	 */
	private void stockCheck()
	{
		//For every VendItem in the stock array
		for (int index = 0; index < this.getItemCount(); index++)
		{
			//If an item has an index of at least 1
			if (this.stock[index].getQty() > 0)
			{
				return; //Stop the method.
			}
		}
		
		//If every item has a qty of 0, set the status of the vending machine to SERVICE_MODE.
		this.setStatus(Status.SERVICE_MODE);
	}
	
	/**
	 * Set all slots in stock array to null.
	 */
	private void emptyStockArray()
	{
		//For every index in the stock array:
		for (int index = 0; index < this.getItemCount(); index++)
		{
			this.stock[index] = null; //Set it's value to null.
		}
	}
	
	/**
	 * Sets the number of each coins available (in order �2, �1, 50p, 20p, 10p, 5p)
	 * @param coinsAvailable array of integers representing the amount of coins of that denomination 
	 * 		  currently in stock in the machine (in order �2, �1, 50p, 20p, 10p, 5p)
	 * @throws IllegalArgumentException if the given array is null or is not of length 6.
	 */
	private void setCoinsAvailable(int[] coinsAvailable) throws IllegalArgumentException
	{
		if (coinsAvailable == null)
		{
			throw new IllegalArgumentException("coinsAvailable cannot be null.");
		}
		
		if (coinsAvailable.length != 6)
		{
			throw new IllegalArgumentException("coinsAvailable must be of length 6.");
		}
		
		//Copy the values in the new array into the original array.
		for (int index = 0; index < coinsAvailable.length; index++)
		{
			this.coinsAvailable[index] = coinsAvailable[index];
		}
	}
	
	/**
	 * Sets all slots in the coinsAvailable array to 0.
	 */
	private void emptyCoinsAvailableArray()
	{
		int[] empty = {0, 0, 0, 0, 0, 0};
		setCoinsAvailable(empty);
	}
	
	/**
	 * Swap 2 VendItem object references in stock array.
	 * @param first the first VendItem.
	 * @param second the second VendItem.
	 */
	private void swap(int firstIndex, int secondIndex)
	{
		VendItem temp = this.stock[firstIndex];
		this.stock[firstIndex] = this.stock[secondIndex];
		this.stock[secondIndex] = temp;
	}
	
	/**
	 * Adds the coin to vending machine storage to be used to give change.
	 * @param denomination integer denomination of the coin. (�2, �1, 50p, 20p, 10p, 5p)
	 */
	private void addCoin(int denomination)
	{
		switch(denomination)
		{
			case 2:  this.coinsAvailable[0]++; //If denomination = 2, increment number of �2 coins by 1.
					 break;
			case 1:  this.coinsAvailable[1]++; //If denomination = 1, increment number of �1 coins by 1.
					 break;
			case 50: this.coinsAvailable[2]++; //If denomination = 50, increment number of 50p coins by 1.
					 break;
			case 20: this.coinsAvailable[3]++; //If denomination = 20, increment number of 20p coins by 1.
					 break;
			case 10: this.coinsAvailable[4]++; //If denomination = 10, increment number of 10p coins by 1.
					 break;
			case 5:  this.coinsAvailable[5]++; //If denomination = 5, increment number of 5p coins by 1.
					 break;
		}
	}
	
	/**
	 * Calculate the most efficient combination of coins to give as change, and return a string containing the coins dispensed.
	 * @param changeDue double representing the change which is due to return.
	 * @return a string containing the coins dispensed (If insufficient change is given, this is also indicated in the returned string).
	 */
	private String dispenseCoins(double changeDue)
	{
		//Converts change due to pence.
		final int PENCE_IN_POUND = 100; //Define how many pence are in a pound.
		int changeDuePence = (int) (Math.round(changeDue * PENCE_IN_POUND)); //Calculate the change due in pence.
		final double[] ACTUAL    =   {2,    1,    0.50, 0.20, 0.10, 0.05}; //Holds the actual value of coins.
		final int[] ACTUAL_PENCE =   {200,  100,  50,   20,   10,   5};    //Holds the pence value of coins.
		int[] coinsDispensed     =   {0,    0,    0,    0,    0,    0};    //Holds how many of each coin should be given (�2, �1, 50p, 20p, 10p, 5p)
		
		//For every coin denomination
		for (int index = 0; index < this.coinsAvailable.length; index++)
		{
			//While (change can be given with this coin) AND (that coin is in stock)
			while (((changeDuePence - ACTUAL_PENCE[index]) >= 0) && (this.coinsAvailable[index] != 0))
			{
				//Dispense the coin
				this.coinsAvailable[index]--;
				coinsDispensed[index]++;
				
				this.setTotalMoney(this.getTotalMoney() - ACTUAL[index]); //Update totalMoney to reflect this.
				changeDuePence -= ACTUAL_PENCE[index]; //Update changeDuePence to reflect this.
			}
		}
		
		String insufficientChangeMessage = null; //Initialise string that will hold a message if insufficient change is given.
		if (changeDuePence != 0) //If there is still change due which cannot be given:
		{
			insufficientChangeMessage = "\nSorry, change short by �" + String.format("%.2f", (changeDuePence/100.0)); //Store this string to be shown to user.
		}
		
		return buildCoinsDispensedString(coinsDispensed, insufficientChangeMessage); //Return a string holding info about coins dispensed (if any).
	}

	/**
	 * Builds a string containing the coins dispensed (If insufficient change is given, this is also indicated in the returned string).
	 * @param coinsDispensed an int array representing coins which are dispensed. (in the order �2, �1, 50p, 20p, 10p, 5p)
	 * @return a string containing the coins dispensed (If insufficient change is given, this is also indicated in the returned string).
	 */
	private String buildCoinsDispensedString(int[] coinsDispensed, String insufficientChangeMessage)
	{
		String result = "Coins dispensed: "; //Initialises string which prefixes the coins dispensed.
		boolean dispensed = false; //Flag which is set when at least 1 coin is dispensed.
		final String[] DENOMINATIONS = {"�2 ", "�1 ", "50p ", "20p ", "10p ", "5p "}; //Defines the coin denominations as strings which will be appended to the result string.
		
		//For every coin denomination (in the order �2, �1, 50p, 20p, 10p, 5p) :
		for (int index = 0; index < coinsDispensed.length; index++)
		{
			//For every copy of that denomination which should be dispensed:
			while (coinsDispensed[index] > 0)
			{
				result += DENOMINATIONS[index]; //Add it to the string.
				coinsDispensed[index]--; //Update the array to reflect this.
				dispensed = true; //Set the dispensed flag to true.
			}
		}
		
		//If you did not dispense any change:
		if (!dispensed)
		{
			result = "Coins dispensed: None"; //Update the result string to notify user of this.
		}
		
		//If there is insufficient change:
		if (insufficientChangeMessage != null)
		{
			result += insufficientChangeMessage; //Add the insufficient change message to result string.
		}
		
		result += '\n'; //Append a new line to the result string.
		return result; //Return the result string.
	}
	
	/**
	 * Gets a list of current coins in stock. (Used in conjunction with getSystemInfo)
	 * @return a list of current coins in stock.
	 */
	private String getCoinsAvailableList()
	{
		String result = ""; //Initialise result string.
		final String[] DENOMINATIONS = {"�2", "�1", "50p", "20p", "10p", "5p"}; //Defines the coin denominations as strings which will be appended to the result string.
		
		//For every coin:
		for (int index = 0; index < DENOMINATIONS.length; index++)
		{
			//Build correct string.
			result += "No. of " + DENOMINATIONS[index] + " in stock: ";
			
			//If it's 50p, 20p, or 10p, add an extra space so they line up neatly when printed
			if (DENOMINATIONS[index].length() == 2)
			{
				result += " ";
			}
			result += (this.coinsAvailable[index] + "\n");
		}
		return result; //Return the string.
	}
	
	/**
	 * Removes a VendItem from the stock array.
	 * @param index the index of the VendItem in the stock array.
	 * @throws IllegalArgumentException if index is out of range of the stock array.
	 */
	public void removeItem(int index) throws IndexOutOfBoundsException
	{
		//If index is out of range of the stock array, throw an exception.
		if ((index < 0 || index > this.getMaxItems() - 1))
		{
			throw new IndexOutOfBoundsException("index out of range of stock array");
		}
		
		//Remove the item.
		this.stock[index] = null;
		
		//If that was the most recent Item added:
		if (index + 1 == this.getItemCount())
		{
			this.itemCount--; //Decrement itemCount.
			return; //Stop the method.
		}
		
		//Else, for every item after the removed item.
		for (int position = index + 1; position < this.getMaxItems() - 1; position ++)
		{
			//Swap them. (Has the effect of bubbling null values to the end of the array.
			swap(position, position - 1);
		}
		this.itemCount--; //Decrement itemCount.
	}

	/**
	 * Allows the engineer to load machine with coins (as a float) without changing userMoney.
	 * @param coinsAvailable array of integers representing the amount of coins of that denomination 
	 * 		  currently in stock in the machine (in order �2, �1, 50p, 20p, 10p, 5p)
	 **/
	public void loadFloatCoins(int[] coinsAvailable)
	{
		this.setCoinsAvailable(coinsAvailable);
		this.setUserMoney(0);
	}
	
	/**
	 * Saves the state of the VendingMachine to a CSV file. (including it's items and coinsAvailable)
	 * @param csvOutPath String file path of the output CSV file.
	 */
	public void saveState(String csvOutPath)
	{
		try
		{
			File myFile = new File(csvOutPath); //Specify the file path.
			myFile.setWritable(true); //Allow the file to be written to temporarily.
			
			PrintWriter myPw = new PrintWriter(myFile); //Initialises a print writer on the file.
			
			//Print titles to file.
			myPw.println("owner, maxItems, itemCount, totalMoney, userMoney, vmStatus, numOfTwoPounds, numOfOnePounds, numOfFiftyPence, numOfTwentyPence, numOfTenPence, numOfFivePence");
			
			//Vending machine info:
			myPw.print(this.getOwner() + ", ");           //Store owner.
			myPw.print(this.getMaxItems() + ", ");        //Store maxItems.
			myPw.print(this.getItemCount() + ", ");       //Store itemCount.
			myPw.printf("%.2f, ", this.getTotalMoney());  //Store totalMoney.
			myPw.printf("%.2f, ", this.getUserMoney());   //Store userMoney.
			myPw.print(this.getVmStatus() + ", ");        //Store vmStatus.
			
			//For every coin denomination:
			for (int index = 0; index < this.coinsAvailable.length; index++)
			{
				myPw.print(this.coinsAvailable[index] + ", "); //Add the quantity of that denomination which is currently in stock.				
			}
			myPw.println("\n"); //Skip a line 
			
			//Print VendItem headings.
			myPw.println("name, unitPrice, qtyAvailable");
			
			//For every VendItem:
			for (int index = 0; index < this.getItemCount(); index++)
			{
				VendItem thisItem = this.stock[index];
				myPw.print(thisItem.getName() + ", ");   //Store name
				myPw.print(thisItem.getPrice() + ", ");  //Store price
				myPw.println(thisItem.getQty() + ", ");  //Store qty (and take a new line)
			}
			myPw.close(); //Close the PrintWriter.
			myFile.setReadOnly(); //Set the file to READ ONLY. This protects the integrity of the vending machine.
		}
		catch (FileNotFoundException e) //If the target file is not found (EG: It doesn't exist)
		{
			e.printStackTrace(); //Print stack trace.
			System.exit(1); //Exit the program with a value of 1 to indicate an error.
		}
	}
	
	/**
	 * Load the state of the Vending Machine from a CSV file.
	 * @param csvInPath String file path of the CSV file to load.
	 */
	public void loadState(String csvInPath)
	{
		try
		{
			File myFile = new File(csvInPath); //Specify the file path.
			myFile.setReadOnly(); //Sets the file to READ ONLY. (Ensures the state of the CSV cannot be changed while program is running.)
			Scanner mySc = new Scanner(myFile); //Reads file into a scanner.
			mySc.nextLine(); //Skips the headers.
			
			String Info = mySc.nextLine();    //Read VendingMachine info into a String.
			String[] parts = Info.split(","); //Store each piece of info in a String array.

			//Update VendingMachine instance.
			this.setOwner(parts[0].trim()); //Set owner (trim() trims off leading and proceeding whitespace)
			this.setMaxItems(Integer.parseInt(parts[1].trim())); //Set maxItems (Integer.parseInt used to convert from string to int.)
			//ItemCount is stored in parts[2] for completeness only - Doesn't make sense to read this from a file.
			this.setTotalMoney(Double.parseDouble(parts[3].trim())); //Set totalMoney. (Double.parseDouble used to convert from string to double.)
			this.setUserMoney(Double.parseDouble(parts[4].trim())); //Set userMoney.
			this.setStatus(Status.valueOf(parts[5].trim())); //Set status. (Status.valueOf used to convert from type string to ENUMERATION.)
			
			//Setting coins available:
			//For every coin denomination:
			for (int index = 0; index < this.coinsAvailable.length; index++)
			{
				//Set the quantity of this denomination available to the integer stored at location (index+6) of the parts array.
				//[Reason for (index + 6): The difference between index and the position at which the quantity of the corresponding denomination is stored is 6.]
				this.coinsAvailable[index] = Integer.parseInt(parts[index + 6].trim());
			}
			mySc.nextLine(); //Skips the blank line.
			mySc.nextLine(); //Skips the VendItem headers.
			
			//For every VendItem
			while (mySc.hasNextLine())
			{
				Info = mySc.nextLine();  //Read VendItem info into a String.
				parts = Info.split(","); //Store each piece of info in a String array.
				
				//Add the item to the vending machine.
				this.addNewItem(new VendItem(parts[0].trim(), Double.parseDouble(parts[1].trim()), Integer.parseInt(parts[2].trim()) ));				
			}
			mySc.close(); //Closes the scanner.
		}
		catch (FileNotFoundException e) //If the target file is not found (EG: It doesn't exist)
		{
			e.printStackTrace(); //Print stack trace.
			System.exit(1); //Exit the program with a value of 1 to indicate an error.
		}
	}
}
