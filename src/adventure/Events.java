/* To do list:
 * More events other than enemies
 */

package adventure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.character.Enemy;
import adventure.location.Dungeon;
import adventure.location.Town;

public class Events
{
	private Combat combat;
	private MainFrame mainFrame;
	private JPanel eventsP;
	
	public Events(MainFrame mainFrame, String event, String info)
	{
		this.mainFrame = mainFrame;
		
		eventsP = new JPanel();
		eventsP.setLayout(null);
		eventsP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(eventsP);
	}
	
	public void explore(ActionListener al, String location)
	{	
		JButton continueB;
		JLabel foundL;
		JLabel enemyL;
		Enemy[] enemy;
		
		switch (location.toUpperCase())
		{
			case "THE WALL":
				enemy = new Enemy[]{new Enemy("The Great Wall of America", 10, "Grey", "Grey", 75, 3, 2, 3)};
				
				foundL = new JLabel("An enemy found you, prepare for battle");
				foundL.setBounds(100, 10, 500, 100);
				eventsP.add(foundL);
				
				continueB = new JButton("CONTINUE");
				continueB.setBounds(400, 400, 100, 30);
				continueB.addActionListener(al);
				eventsP.add(continueB);
				break;
			case "BORDER CROSSING":
				enemy = new Enemy[]{new Enemy("Gangster Mage", 68, "Blue", "White", 45, 5, 1.25, 4)};
				
				foundL = new JLabel("An enemy found you, prepare for battle");
				foundL.setBounds(100, 10, 500, 100);
				eventsP.add(foundL);
				
				enemyL = new JLabel("\"GIVE ME YOUR MONEY! I RAN OUT OF WIZARD DRUGS!\"");
				enemyL.setBounds(100, 100, 500, 100);
				eventsP.add(enemyL);
				
				continueB = new JButton("CONTINUE");
				continueB.setBounds(400, 400, 100, 30);
				continueB.addActionListener(al);
				eventsP.add(continueB);
				break;
				
			default:
				JLabel errorL = new JLabel("Invalid location, please try again");
				errorL.setBounds(100, 10, 500, 100);
				eventsP.add(errorL);
				
				JButton returnB = new JButton("RETURN");
				returnB.setBounds(400, 400, 100, 30);
				returnB.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						mainFrame.remove(eventsP);
						new MainGame(mainFrame);
					}
				});
				eventsP.add(returnB);
				break;
		}
		
		
		eventsP.repaint();
		eventsP.revalidate();
	}
	
	public void town(String info)
	{
		mainFrame.remove(eventsP);
		new Town(mainFrame, info);
	}
	
	public void dungeon(String info)
	{
		mainFrame.remove(eventsP);
		new Dungeon(mainFrame, info);
	}
	
	public void combat(Enemy[] enemy, int w, int h, int obstacleNum)
	{
		combat.initiateCombat(enemy, w, h, obstacleNum);
	}
}
