/* To do list:
 * Finish town1
 */

package adventure.location;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.Global;
import adventure.MainFrame;
import adventure.MainGame;
import adventure.character.Player;
import adventure.item.Equipment;

public class Town
{
	private MainFrame mainFrame;
	private JPanel townP;
	private Player player;
	
	public Town(MainFrame mainFrame, String townName)
	{
		this.mainFrame = mainFrame;
		
		townP = new JPanel();
		townP.setLayout(null);
		townP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(townP);
		
		player = Global.player;
		
		if (townName.equalsIgnoreCase("town1"))
		{
			townName1();
		}
		else
		{
			JLabel errorL = new JLabel("Invalid town, please report this bug ASAP");
			errorL.setBounds(100, 10, 500, 100);
			townP.add(errorL);
			
			JButton returnB = new JButton("RETURN");
			returnB.setBounds(400, 400, 100, 30);
			returnB.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					mainFrame.remove(townP);
					new MainGame(mainFrame);
				}
			});
			townP.add(returnB);
		}
	}
	
	private void townName1()
	{
		
		//Create Shop JPanel
		JPanel shopP = new JPanel();
		shopP.setLayout(null);
		shopP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		
		JButton equipment1B = new JButton("Test Helmet");
		equipment1B.setBounds(10, 400, 100, 30);
		equipment1B.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				equipmentBought(shopP, new Equipment("Test Helmet", "A helmet made for testing", "HELMET", 0));
			}
		});
		shopP.add(equipment1B);
		
		JButton equipment2B = new JButton("Test Chest");
		equipment2B.setBounds(120, 400, 100, 30);
		equipment2B.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				equipmentBought(shopP, new Equipment("Test Chest", "A chest made for testing", "CHEST", 0));
			}
		});
		shopP.add(equipment2B);
		
		JButton equipment3B = new JButton("Test Sword");
		equipment3B.setBounds(230, 400, 100, 30);
		equipment3B.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				equipmentBought(shopP, new Equipment("Test Sword", "A sword made for testing", "WEAPON", 0));
			}
		});
		shopP.add(equipment3B);
		
		JButton backShopB = new JButton("BACK");
		backShopB.setBounds(450, 400, 100, 30);
		backShopB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.setContentPane(townP);
			}
		});
		shopP.add(backShopB);
		
		//Create town JPanel
		JLabel townNameL = new JLabel("Welcome to Introitus Village");
		townNameL.setBounds(10, 10, 300, 30);
		townP.add(townNameL);
		
		JButton shopB = new JButton("SHOP");
		shopB.setBounds(10, 400, 100, 30);
		shopB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.setContentPane(shopP);
			}
		});
		townP.add(shopB);
		
		JButton returnB = new JButton("RETURN");
		returnB.setBounds(450, 400, 100, 30);
		returnB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.remove(townP);
				Global.player = player;
				new MainGame(mainFrame);
			}
		});
		townP.add(returnB);
		
		townP.repaint();
		townP.revalidate();
	}
	
	private void equipmentBought(JPanel panel, Equipment equip)
	{
		//Finish editing
		System.out.println(equip.getName());
		System.out.println(equip.getType());
		player.addItem(equip);
		
		JPanel itemP = new JPanel();
		itemP.setLayout(null);
		itemP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(itemP);
		
		JLabel itemL = new JLabel("You have successfully bought " + equip.getName() + "!");
		itemL.setBounds(10, 10, 400, 30);
		itemP.add(itemL);
		
		JButton continueB = new JButton("CONTINUE");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.setContentPane(panel);
			}
		});
		itemP.add(continueB);
		
		
	}
}
