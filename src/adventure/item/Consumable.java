/* To do list:
 * 
 */

package adventure.item;

public class Consumable extends Item
{
	private static final long serialVersionUID = 4L;
	
	private String type;
	private int strength;
	
	public Consumable(String n, String d, String t, int s)
	{
		super(n, d);
		
		type = t;
		strength = s;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getStrength()
	{
		return strength;
	}
}
