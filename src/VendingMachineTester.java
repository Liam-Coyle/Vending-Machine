package src;

import java.util.Arrays;

public class VendingMachineTester
{
	/**
	 * Tester class for VendingMachine.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */

	private static int numOfTestsPassed = 0; //Keeps track of number of tests which have passed.
	private static int numOfTestsFailed = 0; //Keeps track of number of tests which have failed.
	
	public static void main(String[] args)
	{
		System.out.println("ADD_NEW_ITEM TESTS\n******************\n");
		addNewItem_WhenStockIsEmpty_ItemAddedToPositionZero(); //Check if adding a valid VendItem object to a vending machine which is empty correctly adds the new item at position 0 of the stock array.
		addNewItem_WhenStockHasOneItem_ItemAddedToPositionOne(); //Check if adding a valid VendItem object to a vending machine with 1 item in it correctly adds the new item at position 1 of the stock array.
		addNewItem_WhenStockHasRoomForOneMoreItem_ItemAddedToFinalPosition(); //Check if adding a valid item to a vending machine which has room for 1 more item correctly adds the new item at the final position of the stock array.
		addNewItem_WhenVendItemIsNull_ThrowIllegalArgument(); //Check if a null input throws an exception.
		addNewItem_WhenStockIsFull_StockRemainsUnchanged(); //Check if trying to add a new item when there is insufficient room in the stock array does not change the stock array.
		
		System.out.println("RESET TESTS\n***********\n");
		reset_WhenTotalMoneyIsNotZero_TotalMoneySetToZero(); //Check if the machine is emptied of 'total money.'
		reset_WhenUserMoneyIsNotZero_UserMoneySetToZero(); //Check if the machine is emptied of 'user money'
		reset_WhenStockIsNotAllNullValues_StockIsSetToAllNullValues(); //Check if all positions in the stock array are set to null.
		reset_WhenStatusIsVendingMode_StatusSetToServiceMode(); //Check if the status of the vending machine is set to SERVICE_MODE.

		System.out.println("PURCHASE_ITEM TESTS\n*******************\n");
		purchaseItem_WhenQtyAvailableIsAtLeastOne_ReduceQtyAvailableByOne(); //Check if the VendItem at given index is available, it's quantitiy is reduced by 1.
		purchaseItem_WhenQtyAvailableIsZero_QtyAvailableRemainsZero(); //Check if the VendItem at given index is NOT available does not reduce it's quantity.
		purchaseItem_WhenInServiceMode_QtyAvailableRemainsUnchanged(); //Check if a purchase request submitted when the vending machine is in SERVICE_MODE does not change item quantity.
		purchaseItem_WhenUserHasInsufficientFunds_QtyAvailableRemainsUnchanged(); //Check if a purchase request submitted when the user has insufficient funds does not change item quantity.
		purchaseItem_WhenLastItemPurchased_StatusSetToServiceMode(); //Check if vending machine is set to SERVICE_MODE when the last item available has been purchased.
		purchaseItem_WhenIndexOutOfBounds_ThrowsIndexOutOfBounds(); //Check if all positions in the stock array are set to null.
		
		System.out.println("INSERT_COIN TESTS\n*****************\n");
		insertCoin_WhenDenominationIsValid_UserMoneyUpdatedCorrectly(); //Check if the VendItem at given index is available, it's quantitiy is reduced by 1.
		insertCoin_WhenDenominationIsInvalid_UserMoneyRemainsUnchanged(); //Check if the given index is out of range of the stock array throws an exception.
		insertCoin_WhenDenominationIsValid_TotalMoneyUpdatedCorrectly(); //Check if the VendItem at given index is NOT available returns null.
		insertCoin_WhenDenominationIsInvalid_TotalMoneyRemainsUnchanged(); //Check if a valid denomination is entered, userMoney is correctly updated.
		
		System.out.println("LIST_ITEMS TESTS\n***************\n");
		listItems_Always_ReturnsCorrectlyFormattedString(); //Check if items are correctly listed.
		
		System.out.println("GET_SYSTEM_INFO TESTS\n*********************\n");
		getSystemInfo_Always_ReturnsCorrectlyFormattedString(); //Check if vending machine info is correctly listed.
		
		System.out.println("RESULTS\n*******");	
		System.out.println("PASSED: " + numOfTestsPassed); //Prints number of tests passed.
		System.out.println("FAILED: " + numOfTestsFailed); //Prints number of tests failed.
	}
	
	/*
	 * ------------------
	 * ADD_NEW_ITEM TESTS
	 * ------------------
	 */
	
	/**
	 * Check if adding a valid VendItem object to a vending machine which is empty correctly adds the new item at position 0 of the stock array.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean addNewItem_WhenStockIsEmpty_ItemAddedToPositionZero()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 5); //Initialise VendingMachine which will be used.
		
		VendItem myVendItem = new VendItem("Twix", 1.50); //Initialise VendItem which will be added.
		String expectedStockArrayState = "[Twix, null, null, null, null]"; //Define the expected state of the stock array after adding the VendItem.
		
		return runAddNewItemTest("addNewItem_WhenStockIsEmpty_ItemAddedToPositionZero", myVendingMachine, myVendItem, expectedStockArrayState, false);
	}
	
	/**
	 * Check if adding a valid VendItem object to a vending machine with 1 item in it correctly adds the new item at position 1 of the stock array.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean addNewItem_WhenStockHasOneItem_ItemAddedToPositionOne()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 5); //Initialise VendingMachine which will be used.
		myVendingMachine.addNewItem(new VendItem("Snickers", 1.20)); //Add 1 item to the VendingMachine initially.
		
		VendItem myVendItem = new VendItem("Twix", 1.50); //Initialise VendItem which will be added.
		String expectedStockArrayState = "[Snickers, Twix, null, null, null]"; //Define the expected state of the stock array after adding the VendItem.
		
		return runAddNewItemTest("addNewItem_WhenStockHasOneItem_ItemAddedToPositionOne", myVendingMachine, myVendItem, expectedStockArrayState, false);
	}
	
	/**
	 * Check if adding a valid item to a vending machine which has room for 1 more item correctly adds the new item at the final position of the stock array.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean addNewItem_WhenStockHasRoomForOneMoreItem_ItemAddedToFinalPosition()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 2); //Initialise VendingMachine which will be used.
		myVendingMachine.addNewItem(new VendItem("Snickers", 1.20)); //Add 1 item to the VendingMachine initially.
		
		VendItem myVendItem = new VendItem("Twix", 1.50); //Initialise VendItem which will be added.
		String expectedStockArrayState = "[Snickers, Twix]"; //Define the expected state of the stock array after adding the VendItem.
		
		return runAddNewItemTest("addNewItem_WhenStockHasRoomForOneMoreItem_ItemAddedToFinalPosition", myVendingMachine, myVendItem, expectedStockArrayState, false);
	}
	
	/**
	 * Check if a null input throws an exception.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean addNewItem_WhenVendItemIsNull_ThrowIllegalArgument()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 5); //Initialise VendingMachine which will be used.
		
		VendItem myVendItem = null; //Initialise VendItem which will be added.
		String expectedStockArrayState = "[null, null, null, null, null]"; //Define the expected state of the stock array after adding the VendItem.
		
		return runAddNewItemTest("addNewItem_WhenVendItemIsNull_ThrowIllegalArgument", myVendingMachine, myVendItem, expectedStockArrayState, true);
	}
	
	/**
	 * Check if trying to add a new item when there is insufficient room in the stock array does not change the stock array.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean addNewItem_WhenStockIsFull_StockRemainsUnchanged()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 2); //Initialise VendingMachine which will be used.
		myVendingMachine.addNewItem(new VendItem("Snickers", 1.20)); //Add item to vending machine.
		myVendingMachine.addNewItem(new VendItem("Mars", 1.30));  //Add item to vending machine. (Now full)
		
		VendItem myVendItem = new VendItem("Twix", 1.50); //Initialise VendItem which will be added.
		String expectedStockArrayState = "[Snickers, Mars]"; //Define the expected state of the stock array after adding the VendItem.
		
		return runAddNewItemTest("addNewItem_WhenStockIsFull_StockRemainsUnchanged", myVendingMachine, myVendItem, expectedStockArrayState, false);
	}
	
	/*
	 * -----------
	 * RESET TESTS
	 * -----------
	 */
	
	
	/**
	 * Check if the machine is emptied of 'total money.'
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean reset_WhenTotalMoneyIsNotZero_TotalMoneySetToZero()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 1); //Initialise vending machine which will be used.
		myVendingMachine.insertCoin(1); //Add a coin to it.
		
		double expectedTotalMoney = 0.0; //Define expected total money after machine has been reset.
		
		return runResetTest_TotalMoney("reset_WhenTotalMoneyIsNotZero_TotalMoneySetToZero", myVendingMachine, expectedTotalMoney);
	}
	
	/**
	 * Check if the machine is emptied of 'user money'
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean reset_WhenUserMoneyIsNotZero_UserMoneySetToZero()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 1); //Initialise vending machine which will be used.
		myVendingMachine.insertCoin(1); //Add a coin to it.
		
		double expectedUserMoney = 0.0; //Define expected total money after machine has been reset.
		
		return runResetTest_UserMoney("reset_WhenUserMoneyIsNotZero_UserMoneySetToZero", myVendingMachine, expectedUserMoney);
	}
	
	/**
	 * Check if all positions in the stock array are set to null.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean reset_WhenStockIsNotAllNullValues_StockIsSetToAllNullValues()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 5); //Initialise vending machine which will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50)); //Add an item to it.
		
		String expectedStockArrayState = "[null, null, null, null, null]"; //Define expected total money after machine has been reset.
		
		return runResetTest_Stock("reset_WhenStockIsNotAllNullValues_StockIsSetToAllNullValues", myVendingMachine, expectedStockArrayState);
	}
	
	/**
	 * Check if the status of the vending machine is set to SERVICE_MODE.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean reset_WhenStatusIsVendingMode_StatusSetToServiceMode()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 5); //Initialise vending machine which will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50)); //Add an item to it (Enables it to enter VENDING_MODE).
		myVendingMachine.setStatus(Status.VENDING_MODE); //Set it's status to VENDING_MODE.
		
		Status expectedStatus = Status.SERVICE_MODE;

		return runResetTest_Status("reset_WhenStatusIsVendingMode_StatusSetToServiceMode", myVendingMachine, expectedStatus);
	}

	/*
	 * --------------------
	 * PURCHASE_ITEMS TESTS
	 * --------------------
	 */
	
	/**
	 * Check if the VendItem at given index is available, it's quantitiy is reduced by 1.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenQtyAvailableIsAtLeastOne_ReduceQtyAvailableByOne()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50, 2)); //Add item which will be purchased to vending machine.
		myVendingMachine.setStatus(Status.VENDING_MODE); //Set vending machine to VENDING_MODE.
		myVendingMachine.insertCoin(2); //Insert a coin.
		
		int index = 0; //The index at which the VendItem requested is at in the stock array.
		int expectedQtyAvailable = 1; //The expected qtyAvailable of the VendItem after purchasing.
		
		return runPurchaseItemTest_Quantity("purchaseItem_WhenQtyAvailableIsAtLeastOne_ReduceQtyAvailableByOne", myVendingMachine, index, expectedQtyAvailable);
	}
	
	/**
	 * Check if the VendItem at given index is NOT available does not reduce it's quantity.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenQtyAvailableIsZero_QtyAvailableRemainsZero()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50)); //Add item which will be purchased to vending machine.
		myVendingMachine.setStatus(Status.VENDING_MODE); //Set vending machine to VENDING_MODE.
		myVendingMachine.insertCoin(2); //Insert a coin.
		
		int index = 0; //The index at which the VendItem requested is at in the stock array.
		int expectedQtyAvailable = 0; //The expected qtyAvailable of the VendItem after purchasing.
		
		return runPurchaseItemTest_Quantity("purchaseItem_WhenQtyAvailableIsZero_QtyAvailableRemainsZero", myVendingMachine, index, expectedQtyAvailable);
	}
	
	/**
	 * Check if a purchase request submitted when the vending machine is in SERVICE_MODE does not change item quantity.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenInServiceMode_QtyAvailableRemainsUnchanged()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50, 2)); //Add item which will be purchased to vending machine.
		myVendingMachine.insertCoin(2); //Insert a coin.
		
		int index = 0; //The index at which the VendItem requested is at in the stock array.
		int expectedQtyAvailable = 2; //The expected qtyAvailable of the VendItem after purchasing.
		
		return runPurchaseItemTest_Quantity("purchaseItem_WhenInServiceMode_QtyAvailableRemainsUnchanged", myVendingMachine, index, expectedQtyAvailable);
	}
	
	/**
	 * Check if a purchase request submitted when the user has insufficient funds does not change item quantity.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenUserHasInsufficientFunds_QtyAvailableRemainsUnchanged()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50, 2)); //Add item which will be purchased to vending machine.
		
		int index = 0; //The index at which the VendItem requested is at in the stock array.
		int expectedQtyAvailable = 2; //The expected qtyAvailable of the VendItem after purchasing.
		
		return runPurchaseItemTest_Quantity("purchaseItem_WhenUserHasInsufficientFunds_QtyAvailableRemainsUnchanged", myVendingMachine, index, expectedQtyAvailable);
	}

	
	/**
	 * Check if vending machine is set to SERVICE_MODE when the last item available has been purchased.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenLastItemPurchased_StatusSetToServiceMode()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50, 1)); //Add item which will be purchased to vending machine.
		myVendingMachine.setStatus(Status.VENDING_MODE);
		myVendingMachine.insertCoin(2); //Insert a coin.
		
		int index = 0; //The index at which the VendItem requested is at in the stock array.
		Status expectedStatus = Status.SERVICE_MODE; //The expected status of the VedningMachine after purchasing.
		
		return runPurchaseItemTest_Status("purchaseItem_WhenLastItemPurchased_StatusSetToServiceMode", myVendingMachine, index, expectedStatus);
	}
	
	/**
	 * Check if the given index is out of range of the stock array throws an exception.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean purchaseItem_WhenIndexOutOfBounds_ThrowsIndexOutOfBounds()
	{
		//Arrange
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine.
		boolean thrown = false; //Set thrown flag to false.
		String returned = null; //Set return value to null initially.
		
		//Act
		try
		{
			returned = myVendingMachine.purchaseItem(3); //Try to purchase item at index 3, which does not exist.
		}
		catch(IndexOutOfBoundsException e) //If an IndexOutOfBoundsException is caught:
		{
			thrown = true; //Set flag.
		}
		
		//Assert
		printTestName("purchaseItem_WhenIndexOutOfBounds_ThrowsIndexOutOfBounds"); //Print test name.
		System.out.println("Input index:     3"); //Print input index.
		System.out.println("Expected output: Throws IndexOutOfBoundsException"); //Print expected output.
		
		if (thrown == true) //If IndexOutOfBoundsException is thrown:
		{
			System.out.println("Actual output:   Throws IndexOutOfBoundsException"); //Print actual output,
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true.
		}
		System.out.println("Actual output:   " + returned); //Else, Print actual output.
		System.out.println("--->FAILED\n"); //The test has failed.
		numOfTestsFailed++; //Increment fail counter.
		return false; //And return false.
	}
	
	
	/*
	 * -----------------
	 * INSERT_COIN TESTS
	 * -----------------
	 */
	
	/**
	 * Check if a valid denomination is entered, userMoney is correctly updated.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean insertCoin_WhenDenominationIsValid_UserMoneyUpdatedCorrectly()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.

		int denomination = 20;
		double expectedUserMoney = 0.20;
		return runInsertCoinTest_UserMoney("insertCoin_WhenDenominationIsValid_UserMoneyUpdatedCorrectly", myVendingMachine, denomination, expectedUserMoney);
	}
	
	/**
	 * Check if a coin denomination which is not recognized is entered, userMoney does not change.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean insertCoin_WhenDenominationIsInvalid_UserMoneyRemainsUnchanged()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.

		int denomination = 30;
		double expectedUserMoney = 0;
		return runInsertCoinTest_UserMoney("insertCoin_WhenDenominationIsInvalid_UserMoneyRemainsUnchanged", myVendingMachine, denomination, expectedUserMoney);
	}
	
	/**
	 * Check if a valid denomination is entered, totalMoney is correctly updated.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean insertCoin_WhenDenominationIsValid_TotalMoneyUpdatedCorrectly()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.

		int denomination = 10;
		double expectedUserMoney = 0.10;
		return runInsertCoinTest_UserMoney("insertCoin_WhenDenominationIsValid_TotalMoneyUpdatedCorrectly", myVendingMachine, denomination, expectedUserMoney);
	}
	
	/**
	 * Check if a coin denomination which is not recognized is entered, totalMoney does not change.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean insertCoin_WhenDenominationIsInvalid_TotalMoneyRemainsUnchanged()
	{
		//Preconditions
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine that will be used.

		int denomination = 40;
		double expectedUserMoney = 0;
		return runInsertCoinTest_UserMoney("insertCoin_WhenDenominationIsInvalid_TotalMoneyRemainsUnchanged", myVendingMachine, denomination, expectedUserMoney);
	}
	
	/*
	 * ----------------
	 * LIST_ITEMS TESTS
	 * ----------------
	 */
	
	/**
	 * Check if items are correctly listed.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean listItems_Always_ReturnsCorrectlyFormattedString()
	{
		//Arrange
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise vending machine which will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50)); //Add VendItem to machine.
		String expected = "Twix: �1.50 (0 available)"; //Define expected string.
		String actual = ""; //Prepare output string.
		
		//Act
		String[] items = myVendingMachine.listItems(); //Call the method.
		
		//Assert
		printTestName("listItems_Always_ReturnsCorrectlyFormattedString"); //Print test title.
		System.out.println("Expected output: " + expected); //Print expected output.
		System.out.print("Actual output:   "); //Print actual output.
		
		for (int index = 0; index < items.length; index++) //Add item info to string.
		{
			System.out.println(items[index]);
			actual += items[0];
		}
		
		if (actual.equals(expected)) //If resul string is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment test counter.
			return true; //And return true.
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++; //Increment test counter.
		return false; //And return false.
	}
	
	
	/*
	 * ---------------------
	 * GET_SYSTEM_INFO TESTS
	 * ---------------------
	 */
	
	/**
	 * Check if vending machine info is correctly listed.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean getSystemInfo_Always_ReturnsCorrectlyFormattedString()
	{
		//Arrange
		VendingMachine myVendingMachine = new VendingMachine("Liam", 3); //Initialise VendingMachine that will be used.
		myVendingMachine.addNewItem(new VendItem("Twix", 1.50)); //Add VendItem to vending machine.
		
		String expected = //Define expected output.
				"VENDING MACHINE STATUS INFO\n"
				+ "---------------------------\n"
				+ "Owner: Liam\n"
				+ "Max items: 3\n"
				+ "Item count: 1\n"
				+ "Total money: 0.00\n"
				+ "User money: 0.00\n"
				+ "Status: Service Mode\n"
				+ "No. of �2 in stock:  0\n"
				+ "No. of �1 in stock:  0\n"
				+ "No. of 50p in stock: 0\n"
				+ "No. of 20p in stock: 0\n"
				+ "No. of 10p in stock: 0\n"
				+ "No. of 5p in stock:  0\n"
				+ "Stock\n"
				+ "-----\n"
				+ "Twix: �1.50 (0 available) (id: 16)\n";
		
		//Act
		String output = myVendingMachine.getSystemInfo(); //Call the method and store return value in output.
		
		//Assert
		printTestName("getSystemInfo_Always_ReturnsCorrectlyFormattedString"); //Print test name.
		System.out.println("Expected output:\n\n" + expected + '\n'); //Print expected outout.
		System.out.println("Actual output:\n\n" + output + '\n'); //Print actual output.
		
		if (output.equals(expected)) //If output is as expected:
		{
			System.out.println("-->PASSED\n"); //The test hasp passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true.
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++; //Inrement fail counter.
		return false; //And return false.
	}
	
	/*
	 * -----------------
	 * TESTING TEMPLATES
	 * -----------------
	 */
	
	/**
	 * Runs a test on the addNewItem method of VendingMachine to determine if valid items are correctly added, and adding invalid items throw an exception.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param vendItemToAdd the VendItem object which will be added.
	 * @param expectedStockArrayState String representing the expected state of the stock array (EG: "[Twix, null, null]")
	 * @param shouldThrowIllegalArgument boolean specifying if adding this item should throw an IllegalArgumentException.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runAddNewItemTest(String testName, VendingMachine vendingMachine, 
			VendItem vendItemToAdd, String expectedStockArrayState, boolean shouldThrowIllegalArgument)
	{
		//Arrange
		boolean thrown = false; //Flag which is set if IllegalArgumentException is thrown.
		String initialStockArrayState = getArrayOfStockNames(vendingMachine); //Gets the initial state of the stock array as a string EG: [Twix, null, null]
		
		//Act
		try
		{
			vendingMachine.addNewItem(vendItemToAdd); //Add the VendItem to the vending machine.			
		}
		catch(IllegalArgumentException e) //If an IllegalArgumentException is thrown:
		{
			thrown = true; //Set the flag to true.
		}
		
		//Assert
		printTestName(testName); //Print the test name.
		System.out.println("Initial state of stock array:  " + initialStockArrayState); //Print the initial state of the stock array.

		//If addNewItem SHOULD throw an IllegalArgumentException:
		if (shouldThrowIllegalArgument)
		{
			System.out.println("Expected output:               Throws IllegalArgumentException"); //Print expected result.

			//If addNewItem DID throw an IllegalArgumentException:
			if (thrown) 
			{
				System.out.println("Actual output:                 Throws IllegalArgumentException"); //Print the actual output.
				System.out.println("-->PASSED\n"); //The test has passed.
				numOfTestsPassed++; //Increment pass counter.
				return true; //And return true.
			}
			//If addNewItem did NOT throw an IllegalArgumentException:
			System.out.println("--->FAILED\n"); //The test has failed.
			numOfTestsFailed++; //Increment fail counter.
			return false; //And return false.
		}
		
		//If addNewItem should NOT throw an IllegalArgumentException:
		else
		{
			//If addNewItem DID throw an IllegalArgumentException:
			if (thrown)
			{
				System.out.println("Actual output:                 Throws IllegalArgumentException"); //Print the actual output.
				System.out.println("--->FAILED\n"); //The test has failed.
				numOfTestsFailed++; //Increment fail counter.
				return false; //And return false.
			}
			
			//If addNewItem did NOT throw an IllegalArgumentException
			else
			{
				System.out.println("Expected state of stock array: " + expectedStockArrayState); //Print expected state of stock array
				System.out.println("Actual state of stock array:   " + getArrayOfStockNames(vendingMachine)); //Print the actual state of the stock array.
				
				//If the actual stock array is as expected:
				if (getArrayOfStockNames(vendingMachine).equals(expectedStockArrayState))
				{
					System.out.println("-->PASSED\n"); //The test has passed.
					numOfTestsPassed++; //Increment pass counter.
					return true; //And return true.
				}
				System.out.println("--->FAILED\n"); //Else, the test has failed.
				numOfTestsFailed++; //Increment fail counter.
				return false; //And return false.
			}
		}
	}
	
	/**
	 * Runs a test on the reset method of VendingMachine to determine if totalMoney is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param expectedTotalMoney double the expected value of totalMoney after machine is reset.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runResetTest_TotalMoney(String testName, VendingMachine vendingMachine, double expectedTotalMoney)
	{
		{
			//Arrange
			double initialTotalMoney = vendingMachine.getTotalMoney(); //Stores initial totalMoney
			
			//Act
			vendingMachine.reset(); //Resets the vending machine.
			
			//Assert
			printTestName(testName); //Prints the test name.
			System.out.printf("Initial totalMoney:   %.2f\n", initialTotalMoney); //Prints initial totalMoney.
			System.out.printf("Expected totalMoney:  %.2f\n", expectedTotalMoney); //Prints expected totalMoney.
			System.out.printf("Actual totalMoney:    %.2f\n", vendingMachine.getTotalMoney()); //Prints actual totalMoney.
			
			//If actual totalMoney is as expected:
			if (vendingMachine.getTotalMoney() == expectedTotalMoney)
			{
				System.out.println("-->PASSED\n"); //The test has passed.
				numOfTestsPassed++; //Increment pass counter.
				return true; //And return true.
			}
			System.out.println("--->FAILED\n"); //Else, the test has failed.
			numOfTestsFailed++; //Increment fail counter.
			return false; //And return false.
		}
	}
	
	/**
	 * Runs a test on the reset method of VendingMachine to determine if userMoney is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param expectedUserMoney double the expected value of userMoney after machine is reset.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runResetTest_UserMoney(String testName, VendingMachine vendingMachine, double expectedUserMoney)
	{
		{
			//Arrange
			double initialUserMoney = vendingMachine.getUserMoney(); //Stores initial totalMoney
			
			//Act
			vendingMachine.reset(); //Resets the vending machine.
			
			//Assert
			printTestName(testName); //Prints the test name.
			System.out.printf("Initial userMoney:   %.2f\n", initialUserMoney); //Prints initial totalMoney.
			System.out.printf("Expected userMoney:  %.2f\n", expectedUserMoney); //Prints expected totalMoney.
			System.out.printf("Actual userMoney:    %.2f\n", vendingMachine.getUserMoney()); //Prints actual totalMoney.
			
			//If actual totalMoney is as expected:
			if (vendingMachine.getUserMoney() == expectedUserMoney)
			{
				System.out.println("-->PASSED\n"); //The test has passed.
				numOfTestsPassed++; //Increment pass counter.
				return true; //And return true.
			}
			System.out.println("--->FAILED\n"); //Else, the test has failed.
			numOfTestsFailed++; //Increment fail counter.
			return false; //And return false.
		}
	}
	
	/**
	 * Runs test on reset method of VendingMachine to determine if the state of the stock array is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param expectedStockArrayState String representing the expected state of the stock array (EG: "[Twix, null, null]")
	 * @return true if test passes, false if test fails
	 */
	private static boolean runResetTest_Stock(String testName, VendingMachine vendingMachine, String expectedStockArrayState)
	{
		//Arrange
		String initialStockArrayState = getArrayOfStockNames(vendingMachine); //Gets the initial state of the stock array as a string EG: [Twix, null, null]
		
		//Act
		vendingMachine.reset(); //Reset the vending machine.
		
		//Assert
		printTestName(testName); //Prints test name.
		System.out.println("Initial state of stock array:  " + initialStockArrayState); //Prints initial state of stock array.
		System.out.println("Expected state of stock array: " + expectedStockArrayState); //Prints expected state of stock array.
		System.out.println("Actual state of stock array:   " + getArrayOfStockNames(vendingMachine)); //Prints actual state of stock array.
		
		if (getArrayOfStockNames(vendingMachine).equals(expectedStockArrayState)) //If the state of the stock array is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true,
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++; //Increment fail counter.
		return false; //And return false.
	}
	
	/**
	 * Run tests on reset method of Vendingmachine to determine if the status of the vending machine is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param expectedStatus Status the expected status of the VendingMachine after being reset.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runResetTest_Status(String testName, VendingMachine vendingMachine, Status expectedStatus)
	{
		//Arrange
		Status initialStatus = vendingMachine.getVmStatus(); //Gets the initial status.
		
		//Act
		vendingMachine.reset(); //Resets the vending machine.
		
		//Assert
		printTestName(testName); //Prints the test name.
		System.out.println("Initial status:  " + initialStatus); //Prints the initial status of the vending machine.
		System.out.println("Expected status: " + expectedStatus); //Prints the expected status of the vending machine.
		System.out.println("Actual status:   " + vendingMachine.getVmStatus()); //Prints the actual status of the vending machine.
		
		if (vendingMachine.getVmStatus() == expectedStatus) //If the status of the vending machine is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true.
		}
		System.out.println("--->FAILED\n"); //The test has failed.
		numOfTestsFailed++; //Increment fail counter.
		return false; //And return false.
	}
	
	/**
	 * Runs a test on the purchaseItem method of VendingMachine to determine if the qtyAvailable of a VendItem which is purchased is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param index the index of the VendItem to purchase in the stock array.
	 * @param expectedQtyAvailable the expected qtyAvailable of the VendItem after purchase.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runPurchaseItemTest_Quantity(String testName, VendingMachine vendingMachine, int index, int expectedQtyAvailable)
	{
		//Arrange
		int initialQtyAvailable = vendingMachine.getVendItem(index).getQty(); //Get the initial qtyAvailable of the VendItem.
		
		//Act
		vendingMachine.purchaseItem(index); //Purchase the item at index.
		
		//Assert
		printTestName(testName); //Print the test name.
		System.out.printf("Initial quantity of VendItem at stock[%d]:  %d\n", index, initialQtyAvailable); //Print the initial qty of the vend item.
		System.out.printf("Expected quantity of VendItem at stock[%d]: %d\n", index, expectedQtyAvailable); //Print the expected qty of the vend item.
		System.out.printf("Actual quantity of VendItem at stock[%d]:   %d\n", index, vendingMachine.getVendItem(index).getQty()); //Print the actual qty of the vend item.
		
		if (vendingMachine.getVendItem(index).getQty() == expectedQtyAvailable) //If the actual qtyAvailable of the VendItem is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment the pass counter. 
			return true; //And return false. 
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++; //So increment the fail conter.
		return false; //And return false.
	}
	
	
	/**
	 * Runs a test on the purchaseItem method of VendingMachine todetermine if the status of the VendingMachinei is updated as expected.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param index the index of the VendItem to purchase in the stock array.
	 * @param expectedStatus Status the expected Status of the VendingMachine after purchasing item at index.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runPurchaseItemTest_Status(String testName, VendingMachine vendingMachine, int index, Status expectedStatus)
	{
		//Arrange
		Status initialStatus = vendingMachine.getVmStatus(); //Get the initial status.
		
		//Act
		vendingMachine.purchaseItem(index); //Purchase item at index.
		
		//Assert
		printTestName(testName);
		System.out.println("Initial status:  " + initialStatus); //Print initial status.
		System.out.println("Expected status: " + expectedStatus); //Print expected status.
		System.out.println("Actual status:   " + vendingMachine.getVmStatus()); //Print actual status.
		
		if (vendingMachine.getVmStatus() == expectedStatus) //If the status is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true.
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++; //Increment fail counter.
		return false; //And return false.
	}	
	
	/**
	 * Run test on insertCoin method of VendingMachine to determine if valid denominations correctly update userMoney, or if invalid denominations do not change userMoney.
	 * @param testName String the name of the test case.
	 * @param vendingMachine the VendingMachine object which will be used.
	 * @param denomination the coin denomination to enter.
	 * @param expectedUserMoney double the expected userMoney after coin inserted.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runInsertCoinTest_UserMoney(String testName, VendingMachine vendingMachine, int denomination, double expectedUserMoney)
	{
		//Arrange
		double initialUserMoney = vendingMachine.getUserMoney(); //Get initial user money.
		int expectedUserMoneyPence = (int) (expectedUserMoney * 100); //Convert expectedUserMoney to pence.
		
		//Act
		vendingMachine.insertCoin(denomination); //Insert coin.
		
		//Assert
		printTestName(testName); //Print test name.
		System.out.printf("Initial userMoney:  %.2f\n", initialUserMoney); //Print initial userMoney.
		System.out.printf("Expected userMoney: %.2f\n", expectedUserMoney); //Print expected userMoney.
		System.out.printf("Actual userMoney:   %.2f\n", vendingMachine.getUserMoney()); //Print actual userMoney.
		
		int actualUserMoneyPence = (int) (vendingMachine.getUserMoney() * 100); //Convert actual userMoney to pence.
		
		if (actualUserMoneyPence == expectedUserMoneyPence) //If userMoney is as expected:
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter.
			return true; //And return true.
		}
		System.out.println("--->FAILED\n"); //The test has failed.
		numOfTestsFailed++; //Increment fail counter.
		return false; //And return false.
	}
	
	/*
	 * --------------
	 * HELPER METHODS
	 * --------------
	 */
	
	/**
	 * Prints the test name underlined by '-' characters.
	 * @param testName String name of the test.
	 */
	private static void printTestName(String testName)
	{
		System.out.println(testName); //Print the name of the test.
		for (int i = 0; i < testName.length(); i++) //For every character in the test name.
		{
			System.out.print('-'); //Underline with with a '-'.
		}
		System.out.println(); //Move on to the next line.
	}
	
	/**
	 * Gets a string representing the state of the a VendingMachine stock array (EG: "[Twix, null, null]")
	 * @param vendingMachine the VendingMachine object
	 * @return string representing the state of the a VendingMachine stock array (EG: "[Twix, null, null]")
	 */
	private static String getArrayOfStockNames(VendingMachine vendingMachine)
	{
		String[] result = new String[vendingMachine.getMaxItems()]; //Initialises string array to hold stock names.
		
		//For every slot in VendingMachine:
		for (int index = 0; index < vendingMachine.getMaxItems(); index++)
		{
			//If there is a VendItem object reference:
			if (vendingMachine.getVendItem(index) != null)
			{
				//Add it's name to result.
				result[index] = vendingMachine.getVendItem(index).getName();				
			}
			//Else if that slot is null:
			else
			{
				//Add null to result.
				result[index] = null;
			}
		}
		return Arrays.toString(result); //Return result as a String.
	}
}

