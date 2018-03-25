/* To do list:
 * 
 */

package adventure.character;

public class StatusE
{
	private int length;
	private int maxLength;
	private boolean delete;
	private String name;
	private String effect;
	
	public StatusE(String effectName)
	{
		name = effectName;
		assignEffect();
		
		length = 0;
		delete = false;
	}
	
	private void assignEffect()
	{
		if (name.equals("POISON"))
		{
			effect = "DAMAGE";
			maxLength = 3;
		}
	}
	
	public void effectTimer()
	{
		length++;
		if (length == maxLength)
		{
			delete = true;
		}
		
		//System.out.println(name + " length: " + length);
	}
	
	public void resetLength()
	{
		length = 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean getDelete()
	{
		return delete;
	}
	
	public String getEffect()
	{
		return effect;
	}
	
}
