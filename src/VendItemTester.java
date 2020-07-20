package src;

public class VendItemTester {
	
	/**
	 * Tester class for VendItem.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */
	
	private static int numOfTestsPassed = 0; //Keeps track of number of tests which have passed.
	private static int numOfTestsFailed = 0; //Keeps track of number of tests which have failed.
	
	public static void main(String[] args) 
	{
		System.out.println("CONSTRUCTOR TESTS\n*****************\n");
		constructor_WhenParametersAreValid_ConstructsValidVendItem(); //Check if constructing a VendItem with valid name, unitPrice, and qtyAvailable works correctly.
		constructor_WhenNameIsNull_ThrowsIllegalArgument(); //Check if constructing a VendItem with null name throws an exception.
		constructor_WhenUnitPriceIsNegative_ThrowsIllegalArgument(); //Check if constructing a vendItem with negative unit price throws an exception.
		constructor_WhenUnitPriceIsZero_ThrowsIllegalArgument(); //Check if constructing a VendItem with unit price of 0 throws an exception.
		constructor_WhenUnitPriceIsMax_ConstructsValidVendItem(); //Check if constructing a VendItem with unit price of 2 works correctly.
		constructor_WhenUnitPriceIsOverTwo_ThrowsIllegalArgument(); //Check if constructing a VendItem with unit price of over 2 throws an exception.
		constructor_WhenQtyAvailableIsNegative_ThrowsIllegalArgument(); //Check if constructing a VendItem with qtyAvailable below 0 throws an exception.
		constructor_WhenQtyAvailableIsZero_ConstructsValidVendItem(); //Check if constructing a VendItem with qtyAvailable equal of 0 works correctly.
		constructor_WhenQtyAvailableIsTen_ConstructsValidVendItem(); //Check if constructing a VendItem with a qtyAvailable of 10 works correctly.
		constructor_WhenQtyAvailableIsOverTen_ThrowsIllegalArgument();//Check if constructing a VendItem with a qtyAvailable of over 10 throws an exception.
		
		System.out.println("RESTOCK TESTS\n*************\n");
		restock_WhenAmountIsNegative_ItemQuantityDoesNotChange(); //Check re-stocking by a negative amount does not change item quantity available.
		restock_WhenAmountIsZero_ItemQuantityDoesNotChange(); //Check if re-stocking by 0 does not change item quantity available.
		restock_WhenAmountIsPositiveAndValid_ItemQuantityIsIncreasedByCorrectAmount(); //Check if re-stocking by a positive number increases item quantity available by correct amount.
		restock_WhenAmountWillPutQtyAvailableToTen_ItemQuantityIsIncreasedToTen(); //Check if re-stocking by an amount which will put the qtyAvailable to 10 increases the item quantity available by correct amount.
		restock_WhenAmountWillPutQtyAvailableOverTen_ItemQuantityDoesNotChange(); //Check if re-stocking by an amount which will put the qtyAvailable over 10 does not change item quantity available.

		System.out.println("DELIVER TESTS\n*************\n");
		deliver_WhenQtyAvailableIsZeroOrMore_QtyAvailableDecreasedByOne(); //Check if delivering when qtyAvailable is above 0 correctly changes qtyAvailable.
		deliver_WhenQtyAvailableIsZero_ReturnsNull(); //Check if delivering when qtyAvailable is 0 returns null.
		
		System.out.println("RESULTS\n*******");	
		System.out.println("PASSED: " + numOfTestsPassed); //Prints number of tests passed.
		System.out.println("FAILED: " + numOfTestsFailed); //Prints number of tests failed.
	}
	
	/*
	 * -----------------
	 * CONSTRUCTOR TESTS
	 * -----------------
	 */
	
	/**
	 * Check if constructing a VendItem with valid name, unitPrice, and qtyAvailable works correctly.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenParametersAreValid_ConstructsValidVendItem()
	{
		return runConstructorTest("constructor_WhenParametersAreValid_ConstructsValidVendItem", "Twix", 1.50, 5, false);
	}

	/**
	 * Check if constructing a VendItem with null name throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenNameIsNull_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenNameIsNull_ThrowsIllegalArgument", null, 1.50, 5, true);
	}
	
	/**
	 * Check if constructing a vendItem with negative unit price throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenUnitPriceIsNegative_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenUnitPriceIsNegative_ThrowsIllegalArgument", "Twix", -1.00, 5, true);
	}
	
	/**
	 * Check if constructing a vendItem with unit price of 0 throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenUnitPriceIsZero_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenUnitPriceIsZero_ThrowsIllegalArgument", "Twix", 0, 5, true);
	}
	
	/**
	 * Check if constructing a VendItem with unit price of 2.00 works correctly.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenUnitPriceIsMax_ConstructsValidVendItem()
	{
		return runConstructorTest("constructor_WhenUnitPriceIsMax_ConstructsValidVendItem", "Twix", 2.00, 5, false);
	}
	
	/**
	 * Check if constructing a VendItem with unit price of over 2 throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenUnitPriceIsOverTwo_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenUnitPriceIsOverTwo_ThrowsIllegalArgument", "Twix", 2.01, 5, true);
	}
	
	/**
	 * Check if constructing a VendItem with qtyAvailable below 0 throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenQtyAvailableIsNegative_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenQtyAvailableIsNegative_ThrowsIllegalArgument", "Twix", 1.50, -1, true);
	}
	
	/**
	 * Check if constructing a VendItem with qtyAvailable equal to 0 works correctly.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenQtyAvailableIsZero_ConstructsValidVendItem()
	{
		return runConstructorTest("constructor_WhenQtyAvailableIsZero_ConstructsValidVendItem", "Twix", 1.50, 0, false);
	}
	
	/**
	 * Check if constructing a VendItem with a qtyAvailable of 10 works correctly.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenQtyAvailableIsTen_ConstructsValidVendItem()
	{
		return runConstructorTest("constructor_WhenQtyAvailableIsTen_ConstructsValidVendItem", "Twix", 1.50, 10, false);
	}
	
	/**
	 * Check if constructing a VendItem with a qtyAvailable of over 10 throws an exception.
	 * @return true if test passes, false if test fails
	 */
	private static boolean constructor_WhenQtyAvailableIsOverTen_ThrowsIllegalArgument()
	{
		return runConstructorTest("constructor_WhenQtyAvailableIsOverTen_ThrowsIllegalArgument", "Twix", 1.50, 11, true);
	}
	
	/*
	 * -------------
	 * RESTOCK TESTS
	 * -------------
	 */
	
	/**
	 * Check re-stocking by a negative amount does not change item quantity available.
	 * @return true if test passes, false if test fails
	 */
	private static boolean restock_WhenAmountIsNegative_ItemQuantityDoesNotChange()
	{
		return runRestockTest("restock_WhenAmountIsNegative_ItemQuantityDoesNotChange", 5, -2, 5);
	}
	
	/**
	 * Check if re-stocking by 0 does not change item quantity available.
	 * @return true if test passes, false if test fails
	 */
	private static boolean restock_WhenAmountIsZero_ItemQuantityDoesNotChange()
	{
		return runRestockTest("restock_WhenAmountIsZero_ItemQuantityNotChange", 5, 0, 5);
	}
	
	/**
	 * Check if re-stocking by a positive number increases item quantity available by correct amount.
	 * @return true if test passes, false if test fails
	 */
	private static boolean restock_WhenAmountIsPositiveAndValid_ItemQuantityIsIncreasedByCorrectAmount()
	{
		return runRestockTest("restock_WhenAmountIsPositiveAndValid_ItemQuantityIsIncreasedByCorrectAmount", 5, 2, 7);
	}
	
	/**
	 * Check if re-stocking by an amount which will put the qtyAvailable to 10 increases the item quantity available by correct amount.
	 * @return true if test passes, false if test fails
	 */
	private static boolean restock_WhenAmountWillPutQtyAvailableToTen_ItemQuantityIsIncreasedToTen()
	{
		return runRestockTest("restock_WhenAmountWillPutQtyAvailableToTen_ItemQuantityIsIncreasedToTen", 5, 5, 10);
	}
	
	/**
	 * Check if re-stocking by an amount which will put the qtyAvailable over 10 does not change item quantity available.
	 * @return true if test passes, false if test fails
	 */
	private static boolean restock_WhenAmountWillPutQtyAvailableOverTen_ItemQuantityDoesNotChange()
	{
		return runRestockTest("restock_WhenAmountWillPutQtyAvailableOverTen_ItemQuantityDoesNotChange", 5, 6, 5);
	}
	
	/*
	 * -------------
	 * DELIVER TESTS
	 * -------------
	 */
	
	/**
	 * Check if delivering when qtyAvailable is above 0 correctly changes qtyAvailable.
	 * @return true if test passes, false if test fails
	 */
	private static boolean deliver_WhenQtyAvailableIsZeroOrMore_QtyAvailableDecreasedByOne()
	{
		return runDeliverTest_Quantity("deliver_WhenQtyAvailableIsZeroOrMore_QtyAvailableDecreasedByOne", 5, 4);
	}
	
	/**
	 * Check if delivering when qtyAvailable is 0 returns null.
	 * @return true if test passes, false if test fails
	 */
	private static boolean deliver_WhenQtyAvailableIsZero_ReturnsNull()
	{
		return runDeliverTest_ReturnValue("deliver_WhenQtyAvailableIsZero_ReturnsNull", 0, null);
	}
	
	/*
	 * -----------------
	 * TESTING TEMPLATES
	 * -----------------
	 */
	
	/**
	 * Runs a test on the VendItem constructor.
	 * @param testName String the name of the test case.
	 * @param name String the name of the VendItem to be constructed.
	 * @param unitPrice String the unitPrice of the VendItem to be constructed.
	 * @param qtyAvailable The qtyAvailable of the VendItem to be constructed.
	 * @param shouldThrowIllegalArgument boolean specifying if this construction should throw an IllegalArgumentException.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runConstructorTest(String testName, String name, double unitPrice, int qtyAvailable, boolean shouldThrowIllegalArgument)
	{
		//Arrange
		boolean thrown = false; //Flag which is set if IllegalArgumentException is thrown.
		VendItem myItem = null; //Initialises VendItem to null initially.
		
		//Act
		try
		{
			myItem = new VendItem(name, unitPrice, qtyAvailable); //Tries to initialise the VendItem.	
		}
		catch (IllegalArgumentException e) //If an IllegalArgumentException is thrown:
		{
			thrown = true; //Set flag.
		}

		//Assert
		printTestName(testName); //Print the test name underlined by '-' characters.
		System.out.printf("Input:           Name: %s, unitPrice: %.2f, qtyAvailable: %d\n", name, unitPrice, qtyAvailable); //Print the input to constructor.
		
		//If the constructor SHOULD throw an IllegalArgumentException:
		if (shouldThrowIllegalArgument)
		{
			System.out.println("Expected Output: Throws IllegalArgumentException"); //Print the expected output.
			if (thrown) //If the constructor DID throw an IllegalArgumentException:
			{
				System.out.println("Actual Output:   Throws IllegalArgumentException"); //Print actual output.
				System.out.println("-->PASSED\n"); //The test has passed.
				numOfTestsPassed++; //Increment pass counter. //Increment pass counter.
				return true; //And return true.
			}
			//ELSE, if the constructor did NOT throw an IllegalArgumentException:
			System.out.printf("Actual Output:   VendItem object constructed with the following data: Name: %s, unitPrice: %.2f, qtyAvailable: %d\n", myItem.getName(), myItem.getPrice(), myItem.getQty()); //Print actual output.
			System.out.println("-->FAILED\n"); //The test has failed.
			numOfTestsFailed++; //Increment fail counter. //Increment fail counter.
			return false; //And return false.
		}
		
		//Else if the constructor should NOT throw an IllegalArgumentException:
		else
		{
			//If the constructor DID throw an IllegalArgumentException:
			if (thrown) 
			{
				System.out.println("Actual Output:   Throws IllegalArgumentException"); //Print actual output.
				System.out.println("-->FAILED\n"); //The test has failed.
				numOfTestsFailed++; //Increment fail counter. //Increment fail counter.
				return false; //And return false.
			}
			
			//If the constructor did NOT throw an IllegalArgumentException:
			else
			{
				System.out.printf("Expected Output: VendItem object constructed with the following data: Name: %s, unitPrice: %.2f, qtyAvailable: %d\n", name, unitPrice, qtyAvailable); //Print expected output.
				System.out.printf("Actual Output:   VendItem object constructed with the following data: Name: %s, unitPrice: %.2f, qtyAvailable: %d\n", myItem.getName(), myItem.getPrice(), myItem.getQty()); //Print actual output.
				
				//If the VendItem has the correct state:
				if ((myItem.getName().equals(name)) && (myItem.getPrice() == unitPrice) && (myItem.getQty() == qtyAvailable))
				{
					System.out.println("-->PASSED\n"); //The test has passed.
					numOfTestsPassed++; //Increment pass counter. //Increment pass counter. 
					return true; //So return true.
				}
				System.out.println("--->FAILED\n"); //Else the test failed.
				numOfTestsFailed++; //Increment fail counter. 
				return false; //So return false.
			}
		}
	}
	
	/**
	 * Runs a test on the restock method of VendItem.
	 * @param testName String the name of the test case.
	 * @param initialQtyAvailable the initialQty of the VendItem before being re-stocked.
	 * @param amountToRestockBy the amount to re-stock by. 
	 * @param expectedQtyAvailable the expected qtyAvailable of the VendItem after re-stocking.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runRestockTest(String testName, int initialQtyAvailable, int amountToRestockBy, int expectedQtyAvailable)
	{
		//Arrange
		VendItem myItem = new VendItem("Twix", 1.50, initialQtyAvailable); //Constructs VendItem.
		
		//Act
		myItem.restock(amountToRestockBy); //Re-stocks the VendItem.
		
		printTestName(testName); //Prints the test name.
		System.out.printf("Initial quantity:   %d\n", initialQtyAvailable); //Prints the initial quantity.
		System.out.printf("Input:              %d\n", amountToRestockBy); //Prints the input into the restock method.
		System.out.printf("Expected quantity:  %d\n", expectedQtyAvailable); //Prints the expected qtyAvailable of the VendItem after re-stocking.
		System.out.printf("Actual quantity:    %d\n", myItem.getQty()); //Prints the actual qtyAvaialble of the VendItem after re-stocking.
		
		//If the actual qtyAvailable of the VendItem after re-stocking is as expected:
		if (myItem.getQty() == expectedQtyAvailable)
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter. 
			return true; //So return true.
		}
		System.out.println("--->FAILED\n"); //Else the test has failed.
		numOfTestsFailed++; //Increment fail counter. 
		return false; //So return false.
	}
	
	/**
	 * Runs a test on the deliver method of VendItem to determine if the qtyAvailable of the VendItem is as expected.
	 * @param testName String the name of the test case.
	 * @param initialQtyAvailable the initial qtyAvailable of the VendItem before being delivered.
	 * @param expectedQtyAvailable the expected qtyAvailable of the VendItem after delivery.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runDeliverTest_Quantity(String testName, int initialQtyAvailable, int expectedQtyAvailable)
	{
		//Arrange
		VendItem myItem = new VendItem("Twix", 1.50, initialQtyAvailable); //Constructs VendItem.

		//Act
		myItem.deliver(); //Delivers VendItem.
		
		//Assert
		printTestName(testName); //Prints the test name.
		System.out.printf("Initial Quantity:  %d\n", initialQtyAvailable);  //Prints the initial qtyAvailable of the VendItem before being delivered.
		System.out.printf("Expected Quantity: %d\n", expectedQtyAvailable); //Prints the expected qtyAvailable of the VendItem after delivery.
		System.out.printf("Actual Quantity:   %d\n", myItem.getQty()); //Prints the actual qtyAvailable of the VendItem after delivery.
		
		//If the actual qtyAvailable of the VendItem after re-stocking is as expected:
		if (myItem.getQty() == expectedQtyAvailable)
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter. 
			return true; //So return true.
		}
		System.out.println("--->FAILED\n"); //Else the test has failed.
		numOfTestsFailed++; //Increment fail counter. 
		return false; //So return false.
	}
	
	/**
	 * Runs a test on the deliver method of VendItem to determine if the return value is as expected.
	 * @param testName String the name of the test case.
	 * @param initialQtyAvailable the initial qtyAvailable of the VendItem before being delivered.
	 * @param expectedReturnValue String the expected return value of the deliver method.
	 * @return true if test passes, false if test fails
	 */
	private static boolean runDeliverTest_ReturnValue(String testName, int initialQtyAvailable, String expectedReturnValue)
	{
		//Arrange
		VendItem myItem = new VendItem("Twix", 1.50, initialQtyAvailable); //Constructs VendItem.
		String returnValue; //Declares string which will store the return value of the deliver method.

		//Act
		returnValue = myItem.deliver(); //Delivers the item.
		
		//Assert
		printTestName(testName); //Prints the test name.
		System.out.printf("Initial Quantity:      %d\n", initialQtyAvailable); //Prints the initial qtyAvailable of the VendItem before being delivered.
		System.out.printf("Expected return value: %d\n", expectedReturnValue); //Prints the expected qtyAvailable of the VendItem after delivery.
		System.out.printf("Actual return value:   %d\n", returnValue); //Prints the value which the deliver method returns.
		
		//If the return value is as expected:
		if (returnValue == expectedReturnValue)
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++; //Increment pass counter. 
			return true; //So return true.
		}
		System.out.println("--->FAILED\n"); //Else the test has failed.
		numOfTestsFailed++; //Increment fail counter. 
		return false; //So return false.	
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
}