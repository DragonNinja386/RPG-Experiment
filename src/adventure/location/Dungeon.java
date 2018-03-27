/* To do list:
 * Finish 1st dungeon
 * 	Locked room & Key
 * 	Boss
 */

package adventure.location;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import adventure.Events;
import adventure.MainFrame;
import adventure.character.*;
import adventure.item.*;

public class Dungeon
{	
	//Dungeon variables
	private Events events;
	private Room[] rooms;
	private int roomNumber;
	private int dirNumCount = 0;
	private MainFrame mainFrame;
	private JPanel dungeonP;
	private Player player;
	private Enemy[] enemy;
	private Equipment equip;
	private Consumable consume;
	
	private class Room
	{
		//Room variables
		private JPanel roomP;
		private int roomNum;
		private String roomType;
		private String roomName;
		private String description;
		private String keyName;
		private boolean completed;
		private boolean locked;
		private Enemy[] enemy;
		private Equipment equip;
		private Consumable consume;
		
		public Room(int rn, String rt, String rm)
		{
			roomP = new JPanel();
			roomP.setLayout(null);
			roomP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
			
			roomNum = rn;
			roomType = rt;
			roomName = rm;
			
			JLabel roomNumL = new JLabel(roomName);
			roomNumL.setBounds(10, 10, 100, 30);
			roomP.add(roomNumL);
		}
		
		public void desc(String d)
		{
			description = d;
			
			JLabel roomDescL = new JLabel(description);
			roomDescL.setBounds(10, 90, 300, 300);
			roomP.add(roomDescL);
		}
		
		public void direction(String dir, int[] dirNum)
		{
			String direction = "";
			if (dir.contains("north"))
			{
				direction += "north ";
			}
			if (dir.contains("east"))
			{
				direction += "east ";
			}
			if (dir.contains("south"))
			{
				direction += "south ";
			}
			if (dir.contains("west"))
			{
				direction += "west ";
			}
			
			dirNumCount = 0;
			int xPos = 10;
			if (direction.contains("north"))
			{
				JButton northB = new JButton("NORTH");
				northB.setBounds(xPos, 400, 100, 30);
				northB.addActionListener(new ActionListener() {
					int numCount = dirNumCount;
					public void actionPerformed(ActionEvent e)
					{
						roomNumber = dirNum[numCount];
						switchRoom(rooms[dirNum[numCount]].roomP, rooms[dirNum[numCount]].roomType);
					}
				});
				dirNumCount++;
				xPos += 110;
				roomP.add(northB);
			}
			if (direction.contains("east"))
			{
				JButton eastB = new JButton("EAST");
				eastB.setBounds(xPos, 400, 100, 30);
				eastB.addActionListener(new ActionListener() {
					int numCount = dirNumCount;
					public void actionPerformed(ActionEvent e)
					{
						roomNumber = dirNum[numCount];
						switchRoom(rooms[dirNum[numCount]].roomP, rooms[dirNum[numCount]].roomType);
					}
				});
				dirNumCount++;
				xPos += 110;
				roomP.add(eastB);
			}
			if (direction.contains("south"))
			{
				if (dirNum[dirNumCount] != -1)
				{
					JButton southB = new JButton("SOUTH");
					southB.setBounds(xPos, 400, 100, 30);
					southB.addActionListener(new ActionListener() {
						int numCount = dirNumCount;
						public void actionPerformed(ActionEvent e)
						{
							roomNumber = dirNum[numCount];
							switchRoom(rooms[dirNum[numCount]].roomP, rooms[dirNum[numCount]].roomType);
						}
					});
					dirNumCount++;
					xPos += 110;
					roomP.add(southB);
				}
				else
				{
					JButton exitB = new JButton("EXIT");
					exitB.setBounds(xPos, 400, 100, 30);
					exitB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							events.mainScreen();
						}
					});
					dirNumCount++;
					xPos += 110;
					roomP.add(exitB);
				}
			}
			if (direction.contains("west"))
			{
				JButton westB = new JButton("WEST");
				westB.setBounds(xPos, 400, 100, 30);
				westB.addActionListener(new ActionListener() {
					int numCount = dirNumCount;
					public void actionPerformed(ActionEvent e)
					{
						roomNumber = dirNum[numCount];
						switchRoom(rooms[dirNum[numCount]].roomP, rooms[dirNum[numCount]].roomType);
					}
				});
				dirNumCount++;
				xPos += 110;
				roomP.add(westB);
			}
		}
		
		public void setEnemy(Enemy[] newEnemy)
		{
			enemy = newEnemy;
		}
		
		public void setEquip(Equipment newEquip)
		{
			equip = newEquip;
		}
		
		public void setConsume(Consumable newConsume)
		{
			consume = newConsume;
		}
		
	}
	
	
	//Normal constructor
	public Dungeon(MainFrame mainFrame, Events events)
	{
		this.events = events;
		this.mainFrame = mainFrame;
		
		dungeonP = new JPanel();
		dungeonP.setLayout(null);
		dungeonP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(dungeonP);
		
		
	}

	
	public void dungeon(int roomNumber)
	{
		//Room creation
		rooms = new Room[7];
		//Room1
		rooms[0] = new Room(1, "entrance", "Gate");
		rooms[0].desc("<html><body style='width:300px'> The entrance of the dungeon");
		rooms[0].direction("north south", new int[] {1, -1});
		//Room2
		rooms[1] = new Room(2, "description", "Crossroads");
		rooms[1].desc("<html><body style='width:300px'> There are multiple paths branching off from this room");
		rooms[1].direction("north east south west", new int[] {4, 3, 0, 2});
		//Room3
		rooms[2] = new Room(3, "enemy", "Guard Room");
		rooms[2].setEnemy(new Enemy[] {new Enemy("Gangster Mage", 68, "Blue", "White", 10, 5, 1.25, 4), new Enemy("Gangster Mage", 68, "Blue", "White", 10, 5, 1.25, 4)});
		rooms[2].desc("<html><body style='width:300px'> This room should have enemies in it");
		rooms[2].direction("east", new int[] {1});
		//Room4
		rooms[3] = new Room(4, "treasure", "Treasury");
		rooms[3].desc("<html><body style='width:300px'> This room should have treasure in it");
		rooms[3].direction("west", new int[] {1});
		rooms[3].setEquip(new Equipment("Golden Armor", "This must have cost a fortune to make, even though it doesn't look very sturdy...", "CHEST", 2));
		//Room5
		rooms[4] = new Room(5, "description", "Torture Room");
		rooms[4].desc("<html><body style='width:300px'> The room is filled with dangerous looking contraptions");
		rooms[4].direction("east south", new int[] {5, 1});
		//Room6
		rooms[5] = new Room(6, "description", "Armory");
		rooms[5].desc("<html><body style='width:300px'> The room is full of sharpened tools and polished armor");
		rooms[5].direction("north west", new int[] {6, 4});
		//Room7
		rooms[6] = new Room(7, "boss", "Throne Room");
		rooms[6].setEnemy(new Enemy[]{new Enemy("The Great Wall of America", 68, "Gray", "Gray", 10, 5, 1.25, 4)});
		rooms[6].desc("<html><body style='width:300px'> The boss should be here");
		rooms[6].direction("south", new int[] {5});
		
		mainFrame.setContentPane(rooms[roomNumber].roomP);
		rooms[0].roomP.repaint();
		rooms[0].roomP.revalidate();
	}
	
	private void switchRoom(JPanel jp, String roomType)
	{
		JButton openB = new JButton("OPEN");
		
		System.out.println(roomNumber);
		if (roomType.equals("enemy") && rooms[roomNumber].completed == false)
		{
			mainFrame.remove(dungeonP);
			enemy = rooms[roomNumber].enemy;
			//TODO new Combat(mainFrame, enemy, "dungeon dungeon " + (roomNumber), 8, 8, 0);
		}
		if (roomType.equals("boss") && rooms[roomNumber].completed == false)
		{
			mainFrame.remove(dungeonP);
			enemy = rooms[roomNumber].enemy;
			//TODO new Combat(mainFrame, enemy, "dungeon dungeon " + (roomNumber), 8, 8, 0);
		}
		else if (roomType.equals("treasure") && rooms[roomNumber].completed == false)
		{
			openB.setBounds(450, 400, 100, 30);
			openB.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					equip = rooms[roomNumber].equip;
					consume = rooms[roomNumber].consume;
					if (equip != null)
					{
						player.addItem(equip);
						JLabel itemL = new JLabel("You obtained " + equip.getName());
						itemL.setBounds(50, 180, 200, 30);
						rooms[roomNumber].roomP.add(itemL);
						rooms[roomNumber].roomP.remove(openB);
						rooms[roomNumber].completed = true;
					}
					else
					{
						player.addItem(consume);
						JLabel itemL = new JLabel("You obtained " + consume.getName());
						itemL.setBounds(50, 180, 200, 30);
						rooms[roomNumber].roomP.add(itemL);
						rooms[roomNumber].roomP.remove(openB);
						rooms[roomNumber].completed = true;
					}
					jp.repaint();
					jp.revalidate();
				}
			});
			jp.add(openB);
			mainFrame.setContentPane(jp);
			jp.repaint();
			jp.revalidate();
		}
		else if (roomType.equals("treasure"))
		{
			jp.remove(openB);
			mainFrame.setContentPane(jp);
			jp.repaint();
			jp.revalidate();
		}
		else
		{
			rooms[roomNumber].completed = true;
			mainFrame.setContentPane(jp);
			jp.repaint();
			jp.revalidate();
		}
	}
}
