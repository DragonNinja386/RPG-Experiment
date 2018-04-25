/* To do list:
 * Finish town1
 */

package adventure.location;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.Events;
import adventure.MainFrame;
import adventure.character.Player;
import adventure.item.*;

public class Town
{
	private Events events;
	private MainFrame mainFrame;
	private JPanel townP;
	private JPanel shopP;
	private JPanel itemP;
	private Player player;
	
	public Town(MainFrame mainFrame, Events events) {
		this.events = events;
		this.mainFrame = mainFrame;
		
		townP = new JPanel();
		townP.setLayout(null);
		townP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void townName1() {		
		//Create Shop JPanel
		shopP = new JPanel();
		shopP.setLayout(null);
		shopP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		shopP.setVisible(false);
		mainFrame.setContentPane(shopP);
		
		JButton[] equipment = new JButton[3];
		
		equipment[0] = new JButton("Test Helmet");
		equipment[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equipmentBought(new Equipment("Test Helmet", "A helmet made for testing", "HELMET", 0));
			}
		});
		
		equipment[1] = new JButton("Test Chest");
		equipment[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equipmentBought(new Equipment("Test Chest", "A chest made for testing", "CHEST", 0));
			}
		});
		
		equipment[2] = new JButton("Test Sword");
		equipment[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				equipmentBought(new Equipment("Test Sword", "A sword made for testing", "WEAPON", 0));
			}
		});
		
		JButton backShopB = new JButton("BACK");
		backShopB.setBounds(mainFrame.backButtonLoc());
		backShopB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setContentPane(townP);
			}
		});
		shopP.add(backShopB);
		
		mainFrame.buttons(equipment);
		townP.setVisible(false);
		mainFrame.setContentPane(townP);
		shopP.setVisible(true);
		
		//Create town JPanel
		JLabel townNameL = new JLabel("Welcome to Introitus Village");
		townNameL.setBounds(10, 10, 300, 30);
		townP.add(townNameL);
		
		JButton[] townB = new JButton[1];
		
		townB[0] = new JButton("SHOP");
		townB[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setContentPane(shopP);
			}
		});
		
		JButton returnB = new JButton("RETURN");
		returnB.setBounds(mainFrame.backButtonLoc());
		returnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				events.mainScreen();
			}
		});
		townP.add(returnB);
		
		mainFrame.buttons(townB);
		
		townP.setVisible(true);
	}
	
	private void equipmentBought(Equipment equip) {
		//Finish editing
		itemP = new JPanel();
		itemP.setLayout(null);
		itemP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(itemP);
		
		JLabel itemL = new JLabel("You have successfully bought " + equip.getName() + "!");
		itemL.setBounds(10, 10, 400, 30);
		itemP.add(itemL);
		
		JButton continueB = new JButton("CONTINUE");
		continueB.setBounds(mainFrame.backButtonLoc());
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.addItem(equip);
				mainFrame.setContentPane(shopP);
			}
		});
		itemP.add(continueB);
		
	}
}
