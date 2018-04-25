/* TODO
 * Make variables protected
 */

package adventure.character;

import java.util.ArrayList;

import adventure.item.Equipment;
import adventure.item.Spells;

public abstract class Character implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	//Appearance
	private String name;
	private int height;
	private String hairColor;
	private String skinColor;
	
	//Values
	private int[] pos;
	private int level;
	private int health;
	private int maxHealth;
	private int strength;
	private int attack;
	private int defense;
	private double damageResist;
	private int drEffect;
	private int exp;
	
	//Arrays
	private ArrayList<StatusE> effects = new ArrayList<StatusE>();
	private ArrayList<Spells> spells = new ArrayList<Spells>();
	private Equipment[] equipment = new Equipment[3];

	public Character(String n, int ht, String hc, String sc, int h, int s, double dr)
	{
		level = 1;
		exp = 0;
		pos = new int[2];
		name = n;
		height = ht;
		hairColor = hc;
		skinColor = sc;
		health = h;
		maxHealth = health;
		strength = s;
		damageResist = dr;
	}
	
	public void levelUp(int levels)
	{
		level += levels;
	}
	
	public void setPos(int[] newPos)
	{
		pos = newPos;
	}
	
	public void applyStatus(String newStatus)
	{
		boolean newEffect = true;
		for (int i = 0; i < effects.size(); i++)
		{
			if (newStatus.equals(effects.get(i).getName()))
			{
				effects.get(i).resetLength();
				newEffect = false;
				break;
			}
		}
		if (newEffect)
		{
			effects.add(new StatusE(newStatus));
		}
	}
	
	public void checkStatus()
	{
		for (int i = 0; i < effects.size(); i++)
		{
			effects.get(i).effectTimer();
			if (effects.get(i).getDelete())
			{
				effects.remove(i);
			}
		}
	}
	
	public String damageCheck(int attack)
	{
		int newAttack = (int)((attack - (defense / 2)) / damageResist);
		if (newAttack > 0)
		{
			health -= newAttack;
		}
		else
		{
			newAttack = 0;
		}
		
		return Integer.toString(newAttack);
	}
	
	public String damageCheck2(int attack)
	{
		int newAttack;
		if (attack / 4 > 1)
		{
			newAttack = (attack / 4);
			health -= newAttack;
		}
		else
		{
			newAttack = 1;
			health--;
		}
		
		return Integer.toString(newAttack);
	}
	
	public void addExp(int num)
	{
		exp += num;
	}
	
	public void addHealth()
	{
		health = maxHealth;
	}
	
	public void addHealth(int healthGain)
	{
		if (health + healthGain > maxHealth)
		{
			health = maxHealth;
		}
		else
		{
			health += healthGain;
		}
	}
	
	public void addEquipment(Equipment equip)
	{
		if (equip.getType().equals("HELMET"))
		{
			equipment[0] = equip;
		}
		else if (equip.getType().equals("CHEST"))
		{
			equipment[1] = equip;
		}
		else if (equip.getType().equals("WEAPON"))
		{
			equipment[2] = equip;
		}
		adjustStats();
	}
	
	private void adjustStats()
	{
		int equip1 = 0, equip2 = 0, equip3 = 0;
		if (equipment[0] != null)
		{
			equip1 = equipment[0].getValue();
		}
		if (equipment[1] != null)
		{
			equip2 = equipment[1].getValue();
		}
		if (equipment[2] != null)
		{
			equip3 = equipment[2].getValue();
		}
		defense = equip1 + equip2;
		attack = strength + equip3;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int[] getPos()
	{
		return pos;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getDamage()
	{
		return attack;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getExp()
	{
		return exp;
	}
	
	public ArrayList<StatusE> getEffects()
	{
		return effects;
	}
	
	public ArrayList<Spells> getSpells()
	{
		return spells;
	}
	
	public Equipment[] getEquip()
	{
		return equipment;
	}
	
}
