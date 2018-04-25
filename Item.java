/* To do list:
 * 
 */

package adventure.item;

public class Item implements java.io.Serializable
{
	private static final long serialVersionUID = 3L;
	
	private String name;
	private String desc;
	
	public Item(String n, String d)
	{
		name = n;
		desc = d;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDesc()
	{
		return desc;
	}
}
