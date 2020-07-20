package src;

public class StatusTester
{
	/**
	 * Tester class for Status.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */
	
	private static int numOfTestsPassed = 0; //Keeps track of number of tests which have passed.
	private static int numOfTestsFailed = 0; //Keeps track of number of tests which have failed.

	public static void main(String[] args)
	{
		System.out.println("GET_STATUS TESTS\n****************\n");
		getStatus_WhenStatusIsVendingMode_ReturnVendingModeAsString(); //Check if getting the status of a VENDING_MODE status returns correct string.
		getStatus_WhenStatusIsServiceMode_ReturnServiceModeAsString(); //Check if getting the status of a SERVICE_MODE status returns correct string.
		
		System.out.println("RESULTS\n*******");	
		System.out.println("PASSED: " + numOfTestsPassed); //Prints number of tests passed.
		System.out.println("FAILED: " + numOfTestsFailed); //Prints number of tests failed.
	}
	
	/**
	 * Check if getting the status of a VENDING_MODE status returns correct string.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean getStatus_WhenStatusIsVendingMode_ReturnVendingModeAsString()
	{
		return runGetStatusTest("getStatus_WhenStatusIsVendingMode_ReturnVendingModeAsString", Status.VENDING_MODE, "Status: Vending Mode");
	}
	
	/**
	 * Check if getting the status of a SERVICE_MODE status returns correct string.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean getStatus_WhenStatusIsServiceMode_ReturnServiceModeAsString()
	{
		return runGetStatusTest("getStatus_WhenStatusIsServiceMode_ReturnServiceModeAsString", Status.SERVICE_MODE, "Status: Service Mode");
	}
	
	/**
	 * Runs a test in the getStatus method.
	 * @param testName String the name of the test.
	 * @param status Status the current status.
	 * @param expectedOutput String the expected output of the getStatus.
	 * @return boolean true if test passes, else return false.
	 */
	private static boolean runGetStatusTest(String testName, Status status, String expectedOutput)
	{
		//Arrange
		String output; //Declares variable which will hold getStatus method output.
		
		//Act
		output = status.getStatus(); //Calls the get status method.
		
		//Assert
		printTestName(testName); //Prints the test name.
		System.out.println("Current status:  " + status); //Prints the status.
		System.out.println("Expected output: " + expectedOutput); //Prints the expected output.
		System.out.println("Actual output:   " + output); //Prints the actual outpout.
		
		//If the actual output is as expected:
		if (output.equals(expectedOutput))
		{
			System.out.println("-->PASSED\n"); //The test has passed.
			numOfTestsPassed++;
			return true; //So return true.
		}
		System.out.println("--->FAILED\n"); //Else, the test has failed.
		numOfTestsFailed++;
		return false; //So return false.
	}
	
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
