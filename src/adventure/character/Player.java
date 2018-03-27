/* To do list:
 * 
 */

package adventure.character;

import java.util.ArrayList;

import adventure.item.Item;

public class Player extends Character
{
	private static final long serialVersionUID = 1L;

	private String attackType;
	private String currentLocation;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int inventorySize;
	
	public Player(String n, int ht, String hc, String sc, int h, int s, double dr)
	{
		super(n, ht, hc, sc, h, s, dr);
		super.levelUp(2);
		inventorySize = 9;
		currentLocation = "SOUTH";
	}
	
	public void setAttckType(String newType)
	{
		attackType = newType;
	}
	
	public void addItem(Item newItem)
	{
		inventory.add(newItem);
	}
	
	public String getAttackType()
	{
		return attackType;
	}
	
	public String getLocation()
	{
		return currentLocation;
	}
	
	public ArrayList<Item> getInventory()
	{
		return inventory;
	}
	
	public int getInvSize()
	{
		return inventorySize;
	}
	
	public void loadSave()
	{
		
	}
}
