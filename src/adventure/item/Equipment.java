/* To do list:
 * 
 */

package adventure.item;

public class Equipment extends Item
{
	private static final long serialVersionUID = 4L;
	
	private String type;
	private int value;

	public Equipment(String n, String d, String t, int v)
	{
		super(n, d);
		
		type = t;
		value = v;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getValue()
	{
		return value;
	}
}
