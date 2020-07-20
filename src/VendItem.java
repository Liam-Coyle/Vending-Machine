package src;

public class VendItem implements Vendible
{
	/**
	 * This class represents an item in a vending machine.
	 * @author LiamCoyle 40270954
	 * @version V1.0
	 */
	
	private int itemId; //The unique item id.
	private static int nextId = 0; //The next unique item id which should be assigned.
	private String name; //The name of the item.
	private double unitPrice; //The price of the item in pounds. (Must be >�0, <=�2, and divisible by 5p)
	private int qtyAvailable; //The quantity of the item remaining in the machine.
	
	/**
	 * The constructor method for VendItem.
	 * Sets name and unitPrice using input parameters.
	 * Sets itemId to the next available id automatically.
	 * Sets qtyAvailable to 0.
	 * @param name string name of the item.
	 * @param unitPrice the unit price of the item in pounds. (Must be a multiple of 5p and between �0 and �2)  
	 */
	public VendItem(String name, double unitPrice)
	{
		this.itemId = nextId; //Sets item id.
		nextId++; //Increments id for next item.
		this.qtyAvailable = 0; //Sets default quantity to 0.
		this.setName(name); //Sets item name using setter for validation.
		this.setUnitPrice(unitPrice); //Sets unit price using a setter for validation.
	}
	
	/**
	 * The constructor method for VendItem. (Overloaded)
	 * Sets name, unitPrice and qtyAvailable using input parameters.
	 * Sets itemId to the next available id automatically.
	 * @param name string name of the item.
	 * @param unitPrice the unit price of the item in pounds. (Must be a multiple of 5p and between �0 and �2)  
	 * @param qtyAvailable the quantity of the item available.
	 */
	public VendItem(String name, double unitPrice, int qtyAvailable)
	{
		this.itemId = nextId; //Sets item id.
		nextId++; //Increments id for next item.
		this.setName(name); //Sets item name using setter for validation.
		this.setUnitPrice(unitPrice);  //Sets unit price using a setter for validation.
		this.setQtyAvailable(qtyAvailable); //Sets qtyAvailable using a setter for validation.
	}
	
	/**
	 * Increases the quantity of items available by given amount.
	 * @param amount the desired increase in quantity. (qtyAvailable)
	 * @return boolean true if restock is successful, else returns false.
	 */
	public boolean restock(int amount)
	{
		//If input integer is less than 0 OR restocking by input amount will put itemQtyAvailable over 10:
		if (amount <= 0 || (this.getQty() + amount) > 10)
		{
			return false;
		}
		
		else 
		{
			this.qtyAvailable = this.getQty() + amount; //Add the input amount to current qtyAvailable.
			return true;
		}
	}
	
	/**
	 * Dispenses the selected item if available. (Reduces qtyAvailable by 1)
	 * @return String "Thanks for purchasing: (item-name)" if item is available, else returns null.
	 */
	public String deliver()
	{
		if (this.getQty() <= 0) //If this out of stock:
		{
			return null; //Return null to indicate this.
		}
		
		else //If it is in stock:
		{
			this.decrement(); //Decrement this items qtyAvailable by 1.
			return ("Thanks for purchasing: " + this.getName()); //Return string which will be shown to user.
		}
	}
	
	/**
	 * Decrements the quantity of items available by 1.
	 * @return boolean true if decrement is successful (ie. There is an item left), else returns false.
	 */
	private boolean decrement()
	{
		if (this.getQty() > 0) //If current quantity is at least 1:
		{
			this.qtyAvailable--; //decrement it.
			return true;
		}
		
		else
		{
			return false;
		}
	}

	/**
	 * Getter method for item name.
	 * @return the name of the item.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Getter method for item unit price.
	 * @return the unit price of the item.
	 */
	public double getPrice()
	{
		return this.unitPrice;
	}

	/**
	 * Getter method for item quantity available.
	 * @return the quantity of items available.
	 */
	public int getQty()
	{
		return this.qtyAvailable;
	}
	
	/**
	 * Getter method for item Id.
	 * @return the item Id.
	 */
	public int getItemId()
	{
		return this.itemId;
	}
	
	/**
	 * Sets the item name.
	 * @param name the string name to set.
	 * @throws IllegalArgumentException if input is null.
	 */
	private void setName(String name) throws IllegalArgumentException
	{
		if (name == null || name.equals("")) //If input name is null or an empty string:
		{
			throw new IllegalArgumentException("name must have a value."); //Throw an exception.
		}
		
		else //If input name is valid:
		{
			this.name = name;
		}
	}
	
	/**
	 * Sets the item unit price in pounds.
	 * @param unitPrice the unit price to set in pounds. (Must be >�0, <=�2, and divisible by 5p)
	 * @throws IllegalArgumentException if the input is <=�0 or >�2 or is not divisible by 5p. 
	 */
	private void setUnitPrice(double unitPrice) throws IllegalArgumentException
	{
		int unitPricePence = (int) (Math.round(unitPrice * 100)); //Converts input to pence
		
		if (unitPricePence <= 0 || unitPricePence > 200) //If the input is outside the allowed range:
		{
			throw new IllegalArgumentException("unitPrice must be >�0 and <=�2"); //Throw an exception.
		}
		
		else if (unitPricePence % 5 != 0) // If the input is not divisible by 5p:
		{
			throw new IllegalArgumentException("unitPrice must be divisible by 5p"); //Throw an exception.
		}
		
		else //If the input is valid:
		{
			this.unitPrice = unitPricePence / 100.0; //Set unitPrice to the correct double amount.
		}
	}

	/**
	 * Sets the quantity of items available.
	 * @param qtyAvailable the quantity of items available.
	 * @throws IllegalArgumentException if the input is not between 0 and 10.
	 */
	private void setQtyAvailable(int qtyAvailable) throws IllegalArgumentException
	{
		if (qtyAvailable < 0 || qtyAvailable > 10) //If the input is not between 0 and 10:
		{
			throw new IllegalArgumentException("qtyAvailable must be beween 0 and 10"); //Throw an exception.
		}
		
		else //if the input is between 0 and 10:
		{
			this.qtyAvailable = qtyAvailable; //Set qtyAvailable.
		}
	}
}
