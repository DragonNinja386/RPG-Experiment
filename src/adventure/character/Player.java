/* To do list:
 * 
 */

package adventure.character;

import java.util.ArrayList;

import adventure.item.Item;

public class Player extends Character
{
	private static final long serialVersionUID = 1L;
	
	private int exp;
	private String attackType;
	private String currentLocation;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int inventorySize;
	
	public Player(String n, int ht, String hc, String sc, int h, int s, double dr)
	{
		super(n, ht, hc, sc, h, s, dr);
		inventorySize = 9;
		currentLocation = "SOUTH";
		level = 3;
		exp = 0;
	}
	
	public void setAttckType(String newType)
	{
		attackType = newType;
	}
	
	public void gainExp(int expGain)
	{
		exp += expGain;
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
	
	public int getExp()
	{
		return exp;
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
