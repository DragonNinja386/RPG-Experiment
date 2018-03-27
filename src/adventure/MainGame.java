/* To do list:
 * inventory: polish text?
 */


package adventure;

import java.util.ArrayList;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.character.Player;
import adventure.item.Equipment;
import adventure.item.Item;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MainGame
{
	private Events events;
	private MainFrame mainFrame;
	private JPanel mainGameP;
	private Player player;
	
	//html
    private String html1 = "<html><body style='width: ";
    private String html2 = "px'>";
	
	//Loop Variables
	private int i, a, pageNumber;
	private ArrayList<JButton> invButtons;
	private JButton[] locationButtons;
	
	public MainGame(MainFrame mainFrame, Events events)
	{
		this.player = player;
		this.events = events;
		this.mainFrame = mainFrame;
		
		mainGameP = new JPanel();
		mainGameP.setLayout(null);
		mainGameP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(mainGameP);
		
		mainScreen();
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void mainScreen()
	{
		mainFrame.setContentPane(mainGameP);
		mainGameP.removeAll();
		
		JLabel inDevelopmentL = new JLabel("This is what the main game screen would look like, but i'm to lazy to fully design it right now.");
		inDevelopmentL.setBounds(10, 10, 500, 100);
		mainGameP.add(inDevelopmentL);
		
		JButton exploreB = new JButton("EXPLORE");
		exploreB.setBounds(10, 400, 100, 30);
		exploreB.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					explore();
				}
			});
		mainGameP.add(exploreB);
		
		JButton inventoryB = new JButton("INVENTORY");
		inventoryB.setBounds(120, 400, 100, 30);
		inventoryB.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					inventoryScreen();
				}
			});
		mainGameP.add(inventoryB);
		
		JButton saveB = new JButton("SAVE GAME");
		saveB.setBounds(230, 400, 100, 30);
		saveB.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					saveGame();
				}
			});
		mainGameP.add(saveB);
		
		JButton statusB = new JButton("STATUS");
		statusB.setBounds(340, 400, 100, 30);
		statusB.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					statusScreen();
				}
			});
		mainGameP.add(statusB);
		
		mainGameP.repaint();
		mainGameP.revalidate();
	}
	
	private void inventoryScreen()
	{
		mainGameP.removeAll();
		pageNumber = 1;
		
		JLabel currentL = new JLabel();
		currentL.setText("This is your inventory");
		currentL.setBounds(300, 10, 300, 30);
		mainGameP.add(currentL);
		
		JLabel text1 = new JLabel();
		text1.setBounds(10, 10, 400, 30);
		mainGameP.add(text1);
		
		JLabel text2 = new JLabel();
		text2.setBounds(10, 60, 400, 300);
		mainGameP.add(text2);
		
		invButtons = new ArrayList<JButton>();
		for (i = 0, a = 0; i < player.getInventory().size(); i++, a++)
		{
			invButtons.add(new JButton(player.getInventory().get(i).getName()));
			if (a == 4)
			{
				a = 0;
			}
			if (i < 4)
			{
				invButtons.get(i).setBounds((110 * a) + 10,360,100,30);
				mainGameP.add(invButtons.get(i));
			}
			else if (i < 8)
			{
				invButtons.get(i).setBounds((110 * a) + 10,400,100,30);
				mainGameP.add(invButtons.get(i));
			}
			else if (i % 8 < 4)
			{
				invButtons.get(i).setBounds((110 * a) + 10,360,100,30);
			}
			else
			{
				invButtons.get(i).setBounds((110 * a) + 10,400,100,30);
			}
			MouseMotionListener invMouse = new MouseMotionListener()
			{
				Item item = player.getInventory().get(i);
				@Override
				public void mouseMoved(MouseEvent e)
				{
					text1.setText(item.getName());
					text2.setText(html1 + 200 + html2 + item.getDesc());
				}

				@Override
				public void mouseDragged(MouseEvent e)
				{
					
				}
			};
			invButtons.get(i).addActionListener(new ActionListener()
			{
				int index = i;
				Equipment equip;
				Equipment tempEquip = null;
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (player.getInventory().get(index) instanceof Equipment)
					{
						//Grabs Equipment from inventory index
						equip = (Equipment)player.getInventory().get(index);
						
						//Checks for current equipment type
						if (equip.getType().equals("HELMET") && player.getEquip()[0] != null)
						{
							tempEquip = player.getEquip()[0];
						}
						else if (equip.getType().equalsIgnoreCase("chest") && player.getEquip()[1] != null)
						{
							tempEquip = player.getEquip()[1];
						}
						else if (equip.getType().equalsIgnoreCase("weapon") && player.getEquip()[2] != null)
						{
							tempEquip = player.getEquip()[2];
						}
						
						//Equips equipment and takes off current one
						player.addEquipment(equip);
						if (tempEquip != null)
						{
							player.getInventory().add(index, tempEquip);
							player.getInventory().remove(index + 1);
							equip = tempEquip;
							text1.setText(tempEquip.getName());
							text2.setText(tempEquip.getDesc());
							invButtons.get(index).setText(tempEquip.getName());
							invButtons.get(index).removeMouseMotionListener(invMouse);
							MouseMotionListener invMouse = new MouseMotionListener()
							{
								Equipment equipTemp = tempEquip;
								@Override
								public void mouseMoved(MouseEvent e)
								{
									text1.setText(equipTemp.getName());
									text2.setText(html1 + 200 + html2 + equipTemp.getDesc());
								}
								@Override
								public void mouseDragged(MouseEvent e)
								{
									
								}
							};
							invButtons.get(index).addMouseMotionListener(invMouse);
							mainGameP.repaint();
							mainGameP.revalidate();
						}
						else
						{
							player.getInventory().remove(index);
							mainGameP.remove(invButtons.get(index));
						}
						mainGameP.repaint();
						mainGameP.revalidate();
					}
					else if (player.getInventory().get(index).getClass() == Item.class)
					{
						
					}
					else
					{
						System.out.println("Item error 404, item class doesn't exist");
					}
				}
			});
			invButtons.get(i).addMouseMotionListener(invMouse);
		}
		
		JButton nextB = new JButton("NEXT");
		nextB.setBounds(450, 360, 100, 30);
		nextB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (pageNumber == ((invButtons.size() - 1) / 8) + 1)
				{
					for (int i = 0; i < invButtons.size(); i++)
					{
						if (i < 8)
						{
							mainGameP.add(invButtons.get(i));
						}
						else
						{
							mainGameP.remove(invButtons.get(i));
						}
					}
					pageNumber = 1;
				}
				else
				{
					for (int i = 0; i < invButtons.size(); i++)
					{
						if (i >= 8 * pageNumber && i <= (pageNumber + 1) * 8)
						{
							mainGameP.add(invButtons.get(i));
						}
						else
						{
							mainGameP.remove(invButtons.get(i));
						}
					}
					pageNumber++;
				}
				mainGameP.repaint();
				mainGameP.revalidate();
			}
		});
		if (invButtons.size() > 8)
		{
			mainGameP.add(nextB);
		}
		
		MouseMotionListener mouseOver = new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				text1.setText("");
				text2.setText("");
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				
			}
		};
		
		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainGameP.removeMouseMotionListener(mouseOver);
				mainScreen();
			}
		});
		mainGameP.add(continueB);
		
		mainGameP.addMouseMotionListener(mouseOver);
		
		mainGameP.repaint();
		mainGameP.revalidate();
	}
	
	private void explore()
	{
		mainGameP.removeAll();
		pageNumber = 1;
		
		JLabel currentL = new JLabel();
		currentL.setText("Your current location is at the southern part of the country");
		currentL.setBounds(100, 100, 300, 30);
		mainGameP.add(currentL);
		
		locationButtons = new JButton[4];
		if (player.getLocation().equals("SOUTH"))
		{
			locationButtons[0] = new JButton("THE WALL");
			locationButtons[1] = new JButton("BORDER CROSSING");
			locationButtons[2] = new JButton("TOWN1");
			locationButtons[3] = new JButton("DUNGEON");
			locationButtons[0].setName("EXPLORE");
			locationButtons[2].setName("EXPLORE");
			locationButtons[2].setName("TOWN");
			locationButtons[3].setName("DUNGEON");
		}
		
		for (i = 0, a = 0; i < locationButtons.length; i++, a++)
		{
			if (a == 4)
			{
				a = 0;
			}
			if (i < 4)
			{
				locationButtons[i].setBounds((110 * a) + 10,360,100,30);
				mainGameP.add(locationButtons[i]);
			}
			else if (i < 8)
			{
				locationButtons[i].setBounds((110 * a) + 10,400,100,30);
				mainGameP.add(locationButtons[i]);
			}
			else if (i % 8 < 4)
			{
				locationButtons[i].setBounds((110 * a) + 10,360,100,30);
			}
			else
			{
				locationButtons[i].setBounds((110 * a) + 10,400,100,30);
			}
			locationButtons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					events.event(((JButton)e.getSource()).getName(), ((JButton)e.getSource()).getText());
				}
			});
		}
		
		JButton nextB = new JButton("NEXT");
		nextB.setBounds(450, 360, 100, 30);
		nextB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (pageNumber == ((locationButtons.length - 1) / 8) + 1)
				{
					for (int i = 0; i < locationButtons.length; i++)
					{
						if (i < 8)
						{
							mainGameP.add(locationButtons[i]);
						}
						else
						{
							mainGameP.remove(locationButtons[i]);
						}
					}
					pageNumber = 1;
				}
				else
				{
					for (int i = 0; i < locationButtons.length; i++)
					{
						if (i >= 8 * pageNumber && i <= (pageNumber + 1) * 8)
						{
							mainGameP.add(locationButtons[i]);
						}
						else
						{
							mainGameP.remove(locationButtons[i]);
						}
					}
					pageNumber++;
				}
				mainGameP.repaint();
				mainGameP.revalidate();
			}
		});
		if (locationButtons.length > 8)
		{
			mainGameP.add(nextB);
		}
		
		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainScreen();
			}
		});
		mainGameP.add(continueB);
		
		mainGameP.repaint();
		mainGameP.revalidate();
	}
	
	private void saveGame()
	{
		FileDialog dialog = new FileDialog(mainFrame, "Select a file");
	    dialog.setMode(FileDialog.SAVE);
	    dialog.setVisible(true);
	    String file = dialog.getFile();
	    System.out.println(file + " chosen.");
	    if (file != null)
	    {
			try
			{ 
				FileOutputStream saveFile = new FileOutputStream(file);
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				
				//Save variables
				save.writeObject(player);
				
				save.close();
				saveFile.close();
			}
			catch(Exception exc)
			{
				exc.printStackTrace();
			}
	    }
	    else
	    {
	    	JLabel errorL = new JLabel("Save not found");
	    	errorL.setBounds(200, 200, 100, 30);
	    	mainGameP.add(errorL);
	    	mainGameP.repaint();
	    	mainGameP.revalidate();
	    }
	}
	
	private void statusScreen()
	{
		mainGameP.removeAll();
		
		JLabel currentL = new JLabel();
		currentL.setText("This is your equipment");
		currentL.setBounds(300, 10, 300, 30);
		mainGameP.add(currentL);
		
		JLabel text = new JLabel();
		text.setBounds(10, 280, 400, 200);
		mainGameP.add(text);
		
		JLabel helmetL = new JLabel("HELMET");
		helmetL.setBounds(10,10,100,30);
		mainGameP.add(helmetL);
		
		JLabel chestL = new JLabel("CHEST");
		chestL.setBounds(10,100,100,30);
		mainGameP.add(chestL);
		
		JLabel weaponL = new JLabel("WEAPON");
		weaponL.setBounds(10,190,100,30);
		mainGameP.add(weaponL);
		
		JLabel[] equipmentL = new JLabel[player.getEquip().length];
		for (int i = 0; i < (equipmentL.length); i++)
		{
			//Checks if equipment exists
			if (player.getEquip()[i] != null)
			{
				//Creates empty JLabel to add to array
				JLabel equipL = new JLabel();
				equipmentL[i] = equipL;
				
				//Creates equipment data JLabels
				equipmentL[i].setText(player.getEquip()[i].getName());
				equipmentL[i].setBounds(10,50 + (90 * i),100,30);
				int index = i;
				equipmentL[i].addMouseMotionListener(new MouseMotionListener()
				{
					@Override
					public void mouseMoved(MouseEvent e)
					{
						text.setText(html1 + 200 + html2 + player.getEquip()[index].getDesc());
					}
	
					@Override
					public void mouseDragged(MouseEvent e)
					{
						
					}
				});
				mainGameP.add(equipmentL[i]);
			}
		}
		
		MouseMotionListener mouseOver = new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				text.setText("");
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				
			}
		};
		
		//Create back to mainScreen button
		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainGameP.removeMouseMotionListener(mouseOver);
				mainScreen();
			}
		});
		mainGameP.add(continueB);
		
		mainGameP.addMouseMotionListener(mouseOver);
		
		//Updates Screen
		mainGameP.repaint();
		mainGameP.revalidate();
	}
	
}
