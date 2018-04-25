/* To do list:
 * More events other than enemies
 */

package adventure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.character.*;
import adventure.location.*;

public class Events
{
	private Dungeon dungeon;
	private Town town;
	private Combat combat;
	private Title title;
	private MainGame mainGame;
	
	private MainFrame mainFrame;
	private JPanel eventsP;
	
	  /**
	   * Constructor to initialize the Events object and all objects that require the Events object
	   * @param mainFrame Lets the Events object interact with the MainFrame object
	   */
	public Events(MainFrame mainFrame) {
		mainFrame.setVisible(false);
		dungeon = new Dungeon(mainFrame, this);
		town = new Town(mainFrame, this);
		combat = new Combat(mainFrame, this);
		title = new Title(mainFrame, this);
		mainGame = new MainGame(mainFrame, this);
		
		this.mainFrame = mainFrame;
		
		eventsP = new JPanel();
		eventsP.setLayout(null);
		eventsP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setVisible(true);
		
		title.titleScreen();
	}
	
	public void setPlayer(Player player) {
		combat.setPlayer(player);
		mainGame.setPlayer(player);
		town.setPlayer(player);
		dungeon.setPlayer(player);
	}
	
	//Switches game state to mainGame
	public void mainScreen() {
		mainGame.mainScreen();
	}
	
	public void event(String info, String name) {
		mainFrame.setContentPane(eventsP);
		if (info.equalsIgnoreCase("town"))
			town(name);
		if (info.equalsIgnoreCase("explore"))
			explore(name);
		if (info.equalsIgnoreCase("dungeon"))
			dungeon(name);
	}
	
	private void explore(String location) {	
		JButton continueB;
		JLabel foundL;
		JLabel enemyL;
		Enemy[] enemy;
		
		switch (location.toUpperCase()) {
			case "THE WALL":
				enemy = new Enemy[]{new Enemy("The Great Wall of America", 10, "Grey", "Grey", 75, 3, 2, 3)};
				
				foundL = new JLabel("An enemy found you, prepare for battle");
				foundL.setBounds(100, 10, 500, 100);
				eventsP.add(foundL);
				
				continueB = new JButton("CONTINUE");
				continueB.setBounds(400, 400, 100, 30);
				continueB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventsP.removeAll();
						combat(enemy, 12, 12, 2);
					}
				});
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
				continueB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventsP.removeAll();
						combat(enemy, 12, 12, 2);
					}
				});
				eventsP.add(continueB);
				break;
				
			default:
				JLabel errorL = new JLabel("Invalid location, please try again");
				errorL.setBounds(100, 10, 500, 100);
				eventsP.add(errorL);
				
				JButton returnB = new JButton("RETURN");
				returnB.setBounds(400, 400, 100, 30);
				returnB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						eventsP.removeAll();
						mainGame.mainScreen();
					}
				});
				eventsP.add(returnB);
				break;
		}
		
		
		eventsP.repaint();
		eventsP.revalidate();
	}
	
	private void town(String info) {
		town.townName1();
	}
	
	private void dungeon(String info) {
		dungeon.dungeon(0);
	}
	
	private void combat(Enemy[] enemy, int w, int h, int obstacleNum) {
		combat.initiateCombat(enemy, w, h, obstacleNum);
	}
}
