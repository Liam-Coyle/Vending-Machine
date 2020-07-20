package src;

public enum Status
{	
	/**
	 * This enumeration defines the various states which a vending machine is capable of being in.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */
	
	VENDING_MODE(0), SERVICE_MODE(1); //The 2 status' possible
	
	private int statIntValue; //Stores the integer value which corresponds to the correct status.
	private String info[] = {"Status: Vending Mode", "Status: Service Mode"}; //Defines an array which will be used to return the status of a machine.
	
	/**
	 * The constructor method for Status enumeration.
	 * It stores the integer value which corresponds to the chosen status.
	 * @param value The integer representation of the chosen enumeration (0 for VENDING_MODE, 1 for SERVICE_MODE).
	 */
	private Status(int value)
	{
		statIntValue = value;
	}
	
	/**
	 * Returns a string which indicates the status of the machine.
	 * @return a string which indicates the status of the machine in format "Status: ____________"
	 */
	public String getStatus()
	{
		return info[statIntValue];
	}
}
