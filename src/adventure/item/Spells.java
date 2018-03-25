/* To do list:
 * 
 */

package adventure.item;

public class Spells implements java.io.Serializable
{
	private static final long serialVersionUID = 2L;
	
	private String name;
	private String type;
	private String attackDesc;
	private String desc;
	private String effect;
	
	private int attack;
	
	public Spells(String n)
	{
		name = n;
		
		switch (name.toUpperCase())
		{
			case "FIREBALL":
				type = "DAMAGE";
				attackDesc = "You form a growing ball of fire in your hand. When the fire's heat starts to burn your hand you chuck it at your foe.";
				desc = "Create a ball that is on fire";
				attack = 8;
				break;
			case "HOLY HANDS":
				type = "HEAL";
				attackDesc = "You summon a swarm of majestic hands from the heavens. They haltingly feel your body, searching for your open wounds. After the hands withdraw you can't help but feel a little violated.";
				desc = "Summon a swarm of heavenly hands";
				attack = 6;
				break;
			case "CORRUPTED SYRINGE":
				type = "STATUS DAMAGE";
				attackDesc = "You carefully conjure a diseased syringe in your hands. You swiftly hurl the deadly needle at your opponent, due to the growing feeling of unconfortableness the longer you hold it.";
				desc = "Carefully conjure a deadly syringe";
				effect = "POISON";
				attack = 1;
				break;
			default:
				type = "NONE";
				attackDesc = "If you are seeing this then an error has occured... or you made a dumb decision?";
				desc = "If you are seeing this there is a huge problem, please report this bug asap!";
				attack = 0;
				break;
		}
	}
	
	public int getAttack()
	{
		return attack;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getAtckDesc()
	{
		return attackDesc;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public String getEffect()
	{
		return effect;
	}
}
