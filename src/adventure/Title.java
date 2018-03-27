/* To do list:
 * 
 */

package adventure;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import adventure.character.Player;
import adventure.item.Consumable;
import adventure.item.Equipment;
import adventure.item.Spells;

public class Title
{
	//App variables
	private Events events;
	private MainFrame mainFrame;
	private final int APPLICATION_HEIGHT = 500;
	private final int APPLICATION_WIDTH = 600;
	
	//Title variables
	private JPanel titleP;
	
	//Loads Content
	private void loadImages()
	{

	}
	
	//Game starts here
	public Title(MainFrame mainFrame, Events events)
	{
		loadImages();
		
		this.events = events;
		this.mainFrame = mainFrame;
		
		titleP = new JPanel();
		titleP.setLayout(null);
		titleP.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		mainFrame.setContentPane(titleP);
	}

	public void titleScreen()
	{
		mainFrame.setContentPane(titleP);
		
		JLabel titleL = new JLabel("ADVENTURE");
		titleL.setBounds(200, 100, 100, 30);
		titleP.add(titleL);
		
		JButton storyB = new JButton("New Game");
		storyB.setBounds(400, 400, 100, 30);
		storyB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainFrame.remove(titleP);
				createCharacter();
			}
		});
		titleP.add(storyB);
		
		JButton loadB = new JButton("Continue");
		loadB.setBounds(200,400,100,30);
		loadB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				loadGame();
			}
		});
		titleP.add(loadB);
		
		titleP.repaint();
		titleP.revalidate();
	}
	
	private void createCharacter()
	{
		JPanel[] creationP = new JPanel[6];
		for (int i = 0; i < creationP.length; i++)
		{
			creationP[i] = new JPanel();
			creationP[i].setLayout(null);
			creationP[i].setSize(mainFrame.getWidth(), mainFrame.getHeight());
		}
		mainFrame.setContentPane(creationP[0]);
		
		//Name Panel
		JLabel nameL = new JLabel("What is your name?");
		nameL.setBounds(200, 100, 100, 30);
		creationP[0].add(nameL);
		JTextField nameT = new JTextField("");
		nameT.setBounds(nameL.getX(), 150, 150, 30);
		creationP[0].add(nameT);
		JLabel errorL = new JLabel();
		errorL.setBounds(nameT.getX(), nameT.getY() + 40, 350, 30);
		creationP[0].add(errorL);
		JButton nameB = new JButton("CONFIRM");
		nameB.setBounds(nameT.getX() + 160, nameT.getY(), 80, 30);
		nameB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (nameT.getText().equalsIgnoreCase(""))
				{
					errorL.setText("Name cannot be blank");
					creationP[0].repaint();
					creationP[0].revalidate();
				}
				else if (nameT.getText().length() > 15)
				{
					errorL.setText("Name cannot be more than 15 characters");
					creationP[0].repaint();
					creationP[0].revalidate();
				}
				else
				{
					((JButton)e.getSource()).setName(nameT.getText());
					mainFrame.setContentPane(creationP[1]);
				}
			}
		});
		creationP[0].add(nameB);
		
		//History Panel
		JLabel historyL = new JLabel("Who were you?");
		historyL.setBounds(220, 50, 100, 30);
		creationP[1].add(historyL);
		
		JButton[] pathB = new JButton[4];
		pathB[0] = new JButton("ADVENTUROUS");
		pathB[0].setBounds(50, 150, 180, 30);
		pathB[0].setToolTipText("You lived life in constant danger, and loved every second of it");
		pathB[1] = new JButton("RESERVED");
		pathB[1].setBounds(300, 150, 180, 30);
		pathB[1].setToolTipText("You lived life in the shadows, a ghost wandering from place to place");
		pathB[2] = new JButton("GUARDIAN");
		pathB[2].setBounds(50, 250, 180, 30);
		pathB[2].setToolTipText("You lived life proctecting others; you could survive anything");
		pathB[3] = new JButton("PRESERVER");
		pathB[3].setBounds(300, 250, 180, 30);
		pathB[3].setToolTipText("You lived life preserving all forms of written information, and even discovered a few spells in the process");
		for (JButton jb : pathB)
		{
			jb.setName(null);
			jb.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					((JButton)e.getSource()).setName(((JButton)e.getSource()).getText());
					mainFrame.setContentPane(creationP[2]);
				}
			});
			creationP[1].add(jb);
		}
		
		//Gender Panel
		JLabel genderL = new JLabel("What is your gender?");
		genderL.setBounds(200, 50, 450, 30);
		creationP[2].add(genderL);
		
		JButton[] genderB = new JButton[2];
		genderB[0] = new JButton("Male");
		genderB[0].setBounds(50, 200, 100, 30);
		genderB[1] = new JButton("Female");
		genderB[1].setBounds(170, 200, 100, 30);
		for (JButton jb : genderB)
		{
			jb.setName(null);
			jb.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					((JButton)e.getSource()).setName(((JButton)e.getSource()).getText());
					mainFrame.setContentPane(creationP[3]);
				}
			});
			creationP[2].add(jb);
		}
		
		//Hair Panel
		JLabel hairL = new JLabel("What is your hair color?");
		hairL.setBounds(200, 50, 450, 30);
		creationP[3].add(hairL);
		
		JButton[] hairB = new JButton[4];
		hairB[0] = new JButton("Black");
		hairB[0].setBounds(50, 200, 100, 30);
		hairB[1] = new JButton("Blonde");
		hairB[1].setBounds(170, 200, 100, 30);
		hairB[2] = new JButton("Auburn");
		hairB[2].setBounds(290, 200, 100, 30);
		hairB[3] = new JButton("Brown");
		hairB[3].setBounds(410, 200, 100, 30);
		for (JButton jb : hairB)
		{
			jb.setName(null);
			jb.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					((JButton)e.getSource()).setName(((JButton)e.getSource()).getText());
					mainFrame.setContentPane(creationP[4]);
				}
			});
			creationP[3].add(jb);
		}
		
		//Skin Panel
		JLabel skinL = new JLabel("What is your skin complexion?");
		skinL.setBounds(200, 50, 450, 30);
		creationP[4].add(skinL);
		
		JButton[] skinB = new JButton[4];
		skinB[0] = new JButton("Light");
		skinB[0].setBounds(50, 200, 100, 30);
		skinB[1] = new JButton("Tan");
		skinB[1].setBounds(170, 200, 100, 30);
		skinB[2] = new JButton("Olive");
		skinB[2].setBounds(290, 200, 100, 30);
		skinB[3] = new JButton("Dark");
		skinB[3].setBounds(410, 200, 100, 30);
		for (JButton jb : skinB)
		{
			jb.setName(null);
			jb.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					((JButton)e.getSource()).setName(((JButton)e.getSource()).getText());
					creationP[5].add(errorL);
					mainFrame.setContentPane(creationP[5]);
				}
			});
			creationP[4].add(jb);
		}
		
		//Height Panel
		JLabel heightL = new JLabel("What is your height (in inches)?");
		heightL.setBounds(200, 100, 200, 30);
		creationP[5].add(heightL);
		JTextField heightT = new JTextField("");
		heightT.setBounds(heightL.getX(), 150, 150, 30);
		creationP[5].add(heightT);
		JButton heightB = new JButton("CONFIRM");
		heightB.setBounds(heightT.getX() + 160, heightT.getY(), 80, 30);
		heightB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean isDigit = true;
				for (Character c : heightT.getText().toCharArray())
					if (!Character.isDigit(c))
					{
						isDigit = false;
						break;
					}
					
				if (heightT.getText().equalsIgnoreCase("") || !isDigit)
				{
					errorL.setText("Invalid input");
					creationP[5].repaint();
					creationP[5].revalidate();
				}
				else if (Integer.parseInt(heightT.getText()) < 48)
				{
					errorL.setText("height cannot be less then 4 feet (48 inches)");
					creationP[5].repaint();
					creationP[5].revalidate();
				}
				else if (Integer.parseInt(heightT.getText()) > 72)
				{
					errorL.setText("height cannot be more then 7 feet (72 inches)");
					creationP[5].repaint();
					creationP[5].revalidate();
				}
				else
				{
					((JButton)e.getSource()).setName(heightT.getText());
					
					String name = nameB.getName();
					int height = Integer.parseInt(heightB.getName());
					String bgHistory = "error";
					for (JButton jb : pathB)
						if (jb.getName() != null)
							bgHistory = jb.getName();
					String gender = "error";
					for (JButton jb : genderB)
						if (jb.getName() != null)
							gender = jb.getName();
					String hairColor = "error";
					for (JButton jb : hairB)
						if (jb.getName() != null)
							hairColor = jb.getName();
					String skinColor = "error";
					for (JButton jb : skinB)
						if (jb.getName() != null)
							skinColor = jb.getName();

					confirm(name, bgHistory, gender, height, skinColor, hairColor);
				}
			}
		});
		creationP[5].add(heightB);
		
	}
	
	private void confirm(String name, String bgHistory, String gender, int height, String skinColor, String hairColor)
	{
		JPanel creationP = new JPanel();
		creationP.setLayout(null);
		creationP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
		mainFrame.setContentPane(creationP);
		
		JLabel nameL = new JLabel("Name: " + name);
		nameL.setBounds(30, 30, 250, 30);
		creationP.add(nameL);
		JLabel historyL = new JLabel("History: " + bgHistory);
		historyL.setBounds(30, 70, 250, 30);
		creationP.add(historyL);
		JLabel genderL = new JLabel("Gender: " + gender);
		genderL.setBounds(30, 110, 250, 30);
		creationP.add(genderL);
		JLabel heightL = new JLabel("Height: " + Integer.toString(height / 12) + "' " + Integer.toString(height % 12) + "\"");
		heightL.setBounds(30, 150, 250, 30);
		creationP.add(heightL);
		JLabel skinL = new JLabel("Skin Complextion: " + skinColor);
		skinL.setBounds(30, 190, 250, 30);
		creationP.add(skinL);
		JLabel hairL = new JLabel("Hair Color: " + hairColor);
		hairL.setBounds(30, 230, 250, 30);
		creationP.add(hairL);
		
		JLabel confirmL = new JLabel("Is this right?");
		confirmL.setBounds(30, 300, 200, 30);
		creationP.add(confirmL);
		
		JButton yesB = new JButton("YES");
		yesB.setBounds(300, 300, 80, 30);
		creationP.add(yesB);
		
		JButton noB = new JButton("NO");
		noB.setBounds(400, 300, 80, 30);
		creationP.add(noB);
		
		yesB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Player newPlayer = new Player(name, height, hairColor, skinColor, 0, 0, 1);
				
				if (bgHistory.equalsIgnoreCase("adventurous"))
				{
					newPlayer = new Player(name, height, hairColor, skinColor, 20, 10, 1);
				}
				else if (bgHistory.equalsIgnoreCase("reserved"))
				{
					newPlayer = new Player(name, height, hairColor, skinColor, 16, 13, 1);
				}
				else if (bgHistory.equalsIgnoreCase("guardian"))
				{
					newPlayer = new Player(name, height, hairColor, skinColor, 25, 8, 1);
				}
				else if (bgHistory.equalsIgnoreCase("preserver"))
				{
					newPlayer = new Player(name, height, hairColor, skinColor, 18, 8, 1);
				}
				
				newPlayer.getSpells().add(new Spells("Fireball"));
				newPlayer.getSpells().add(new Spells("Holy Hands"));
				newPlayer.getSpells().add(new Spells("Corrupted Syringe"));
				newPlayer.addEquipment(new Equipment("Giant's Helmet", "A helemt made into the shape of a h0rn, made out of the h0rns of giants which are conviently also in the shape of a h0rn", "HELMET", 2));
				newPlayer.addEquipment(new Equipment("Maiden's Armor", /*"Regardless of whether you are male or female, you have to start with this disgraceful piece of armor, that only protects your \"chest\" (regardless if you have one or not)"*/ "to be implemented", "CHEST", 4));
				newPlayer.addEquipment(new Equipment("Sword Bow", "A sword in the shape of a bow. Surprisingly it can be used \"effectively\".", "WEAPON", 3));
				for (int i = 0; i < 4; i++)
				{
					newPlayer.addItem(new Consumable("Health Potion", "A strange red substance flows within the bottle", "HEAL", 12));
				}
				
				events.setPlayer(newPlayer);
				events.mainScreen();
			}
		});
		noB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				createCharacter();
			}
		});
		
		mainFrame.repaint();
		mainFrame.revalidate();
	}
	
	private void loadGame()
	{
		FileDialog dialog = new FileDialog(mainFrame, "Select a file");
	    dialog.setMode(FileDialog.LOAD);
	    dialog.setVisible(true);
	    String file = dialog.getFile();
	    System.out.println(file + " chosen.");
	    
	    if (file != null)
	    {
		    try
			{
				FileInputStream saveFile = new FileInputStream(file);
				ObjectInputStream save = new ObjectInputStream(saveFile);
				
				Player newPlayer = null;
				newPlayer = (Player) save.readObject();
				
				events.setPlayer(newPlayer);
	
				saveFile.close();
				save.close();
				
				events.mainScreen();
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
	    	titleP.add(errorL);
	    	titleP.repaint();
	    	titleP.revalidate();
	    }
	}
}
