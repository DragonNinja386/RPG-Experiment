/* TODO
 * Implement attack range
 */

package adventure.character;

import java.util.Random;

public class Enemy extends Character
{
	private static final long serialVersionUID = 1L;
	
	private String approach;
	private String attackType;
	private String inflictStatus;
	private String attackDesc;
	private int attackDamage;
	private String[] attacks;
	private String[] attacksT;
	private String[] attacksS;
	private int[] attacksD;
	private int[] attackChance;
	private int[] attackRange;
	private int maxRange;
	private boolean specialName;
	
	public Enemy(String n, int ht, String hc, String sc, int h, int s, double dr, int lv)
	{
		super(n, ht, hc, sc, h, s, dr);
		approach = "AGRO";
		super.addExp(50);
		super.levelUp(2);
		
		switch (n.toLowerCase())
		{
		case "the great wall of america":
			specialName = true;
			maxRange = 3;
			attacks = new String[2];
			attacksT = new String[2];
			attacksS = new String[2];
			attacksD = new int[2];
			attackChance = new int[2];
			attackRange = new int[2];
			
			attacks[0] = "The wall stares you down, it hurts your feelings.";
			attacksT[0] = "ATTACK";
			attacksS[0] = null;
			attacksD[0] = 5;
			attackChance[0] = 6;
			attackRange[0] = 2;
			attacks[1] = "The wall strikes you with small doses of neurotoxin.";
			attacksT[1] = "STATUS";
			attacksS[1] = "POISON";
			attacksD[1] = 0;
			attackChance[1] = 2;
			attackRange[1] = 3;
			break;
		case "gangster mage":
			specialName = false;
			maxRange = 4;
			attacks = new String[3];
			attacksT = new String[3];
			attacksS = new String[3];
			attacksD = new int[3];
			attackChance = new int[3];
			attackRange = new int[3];
			
			attacks[0] = "The cloacked figure forms a glowing ball of fire in his hands. \"BURN HERETI...\" He starts screaming profanities due to losing focus on his fire causing it to start to burn his hands. He hurls the ball of fire in your direction.";
			attacksT[0] = "SPELL DAMAGE";
			attacksS[0] = null;
			attacksD[0] = 12;
			attackChance[0] = 5;
			attackRange[0] = 3;
			attacks[1] = "The gangster calls out to the heavens. In response a sawrm of hands are summoned and surround him. Although you can't see anything, you have an idea of what is happening due to the screams of extreme discomfort. After the hands dissipate you can visibly see that the cloacked figure almost regrets his decision.";
			attacksT[1] = "SPELL HEAL";
			attacksS[1] = null;
			attacksD[1] = 9;
			attackChance[1] = 2;
			attackRange[1] = 0;
			attacks[2] = "The mage conjures an unholy bent(?) needle. After a sleight of hand you see that the needle vanished. \"GET REKT SCRUB!\" Immediately after he shouts you hear something approaches you with inhuman speed. Before you can turn around you feel a sharp pain in your back. A demonic horned creature runs past you and dematerializes when it reaches the mage.";
			attacksT[2] = "SPELL STATUS DAMAGE";
			attacksS[2] = "POISON";
			attacksD[2] = 6;
			attackChance[2] = 2;
			attackRange[2] = 4;
			break;
		default:
			System.out.println("If you see this game couldn't find the correct enemy");
			break;
		}
	}

	public void randomAttack(int range)
	{
		//Initialize Random
		Random random = new Random();
		
		//Initialize total chance
		int chanceNum = 0;
		for (int i = 0; i < attacks.length; i++)
		{
			if (attackRange[i] >= range)
			{
				if (!attacksT[i].equals("HEAL") && !attacksT[i].equals("SPELL HEAL"))
				{
					chanceNum += attackChance[i];
				}
				else
				{
					if (super.getHealth() < super.getMaxHealth() * 0.75)
					{
						chanceNum += attackChance[i];
					}
				}
			}
		}
		int[] chance = new int[chanceNum];
		
		//Create chance table (i = attack index, x = attackChance index tracker, c = attackChance index)
		for (int i = 0, x = 0; i < attacks.length; i++)
		{
			if (!attacksT[i].equals("HEAL") && !attacksT[i].equals("SPELL HEAL"))
			{
				if (attackRange[i] >= range)
				{
					for (int c = x; c < attackChance[i] + x; c++)
					{
						chance[c] = i;
					}
					x += attackChance[i];
				}
			}
			else
			{
				if (super.getHealth() < super.getMaxHealth() * 0.75)
				{
					for (int c = x; c < attackChance[i] + x; c++)
					{
						chance[c] = i;
					}
					x += attackChance[i];
				}
			}
		}
		
		//Sets selected attack
		if (chance.length == 0)
		{
			attackType = "MOVE";
		}
		else
		{
			int attack = chance[random.nextInt(chanceNum)];
			attackDesc = attacks[attack];
			attackType = attacksT[attack];
			inflictStatus = attacksS[attack];
			attackDamage = attacksD[attack];
		}

	}
	
	public String getApproach()
	{
		return approach;
	}
	
	public String getInflictStatus()
	{
		return inflictStatus;
	}
	
	public String getAttackType()
	{
		return attackType;
	}
	
	public String getAttackDesc()
	{
		return attackDesc;
	}
	
	public int getAttackDamage()
	{
		return attackDamage;
	}
	
	public boolean getSpecialName()
	{
		return specialName;
	}
}
