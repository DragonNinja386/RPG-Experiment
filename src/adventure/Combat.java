/* TODO
 * Make JPanels for each method / switch between JPanels (?)
 * inventory -> HTML
 * Implement balanced equipment swapping
 * Fix Enemy targeting <<<<<<<<<
 */

package adventure;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import adventure.character.*;
import adventure.item.*;

public class Combat
{
	//Image Variables

	//Swing variables
	private Events events;
	private MainFrame mainFrame;
	private String location;
	
	//Combat variables
	private Player player;
	private Enemy[] enemy;
	private ArrayList<Enemy> enemyTurn;
	private Enemy[] ally;

	private Spells playerSpell;
	private String playerTurn = "";
	
	private boolean winner;
	private String itemType;
	private Consumable consume;
    private int[] targetNum;
    private int targets;
    private int height = 0;
    private int width = 0;
    private Random random = new Random();
    
	//html
    private String html1 = "<html><body style='width: ";
    private String html2 = "px'>";
    
    //Button variables
    private int x2, y2;
    private int playerX2, playerY2;
    private int enemyX, enemyY;

    private Grid[][] gridButton;

    public Combat(MainFrame mainFrame, Events events) {
        //Initialize variables
    	this.events = events;
        this.mainFrame = mainFrame;

        
        
        //instantiatePanels();
    }
    
    //Gives Combat access to the player object
    public void setPlayer(Player player) { this.player = player; }
    
    private void instantiatePanels()
    {
    	JPanel combatP = new JPanel();
        combatP.setLayout(null);
        combatP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
    	JPanel statusP = new JPanel();
    	statusP.setLayout(null);
    	statusP.setSize(mainFrame.getWidth(), mainFrame.getHeight());
    	JPanel spellP = new JPanel();
    	JPanel inventoryP = new JPanel();
    	
		/*
		 *  Main Combat Panel
		 */
    	//Displays Player Information
		JLabel nameL = new JLabel(player.getName());
		nameL.setBounds(10, 140, 200, 30);
		combatP.add(nameL);

		JLabel playerLevelL = new JLabel("Level: " + Integer.toString(player.getLevel()));
		playerLevelL.setBounds(10, 165, 200, 30);
		combatP.add(playerLevelL);

		JLabel healthL = new JLabel("Health: " + Integer.toString(player.getHealth()) + "/" + Integer.toString(player.getMaxHealth()));
		healthL.setBounds(10, 190, 100, 30);
		combatP.add(healthL);

		//Buttons/Commands
		JButton attackB = new JButton("ATTACK");
		attackB.setBounds(10, 400, 100, 30);
		attackB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setAttckType("ATTACK");
				targets = 2;
				setTarget(2, new String[] {"E"});
			}
		});
		combatP.add(attackB);
		
		JButton moveB = new JButton("MOVE");
		moveB.setBounds(10, 440, 100, 30);
		moveB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.setAttckType("MOVE");
				targets = 2;
				int playerX = 0, playerY = 0;
		        for (int x = 0; x < width; x++)
		            for (int y = 0; y < height; y++) {
		                gridButton[x][y].setBounds(x * 30 + 10, y * 30 + 10, 30, 30);
		                if (gridButton[x][y].getSymbol().equals("O")) {
		                    playerX = x;
		                    playerY = y;
		                    break;
		                }
		            }
				setMovement(2, playerX, playerY);
			}
		});
		combatP.add(moveB);

		JButton spellB = new JButton("SPELL");
		spellB.setBounds(120, 400, 100, 30);
		spellB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spellSelection();
			}
		});
		combatP.add(spellB);

		JButton inventoryB = new JButton("INVENTORY");
		inventoryB.setBounds(230, 400, 100, 30);
		inventoryB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inventoryScreen();
			}
		});
		combatP.add(inventoryB);

		JButton statusB = new JButton("STATUS");
		statusB.setBounds(340, 400, 100, 30);
		statusB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setContentPane(statusP);
			}
		});
		combatP.add(statusB);
    	
		/*
		 *  Status Panel
		 */
    	JLabel currentL = new JLabel();
		currentL.setText("This is your equipment");
		currentL.setBounds(300, 10, 300, 30);
		statusP.add(currentL);

		JLabel statusL = new JLabel("STATUS");
		statusL.setBounds(300, 80, 300, 30);
		statusP.add(statusL);

		JLabel equipDesc = new JLabel();
		equipDesc.setBounds(10, 280, 400, 200);
		statusP.add(equipDesc);

		JLabel helmetL = new JLabel("HELMET");
		helmetL.setBounds(10, 10, 100, 30);
		statusP.add(helmetL);

		JLabel chestL = new JLabel("CHEST");
		chestL.setBounds(10, 100, 100, 30);
		statusP.add(chestL);

		JLabel weaponL = new JLabel("WEAPON");
		weaponL.setBounds(10, 190, 100, 30);
		statusP.add(weaponL);

		//Displays status effect information
		JLabel[] effectsL = new JLabel[player.getEquip().length];
		for (int i = 0; i < player.getEffects().size(); i++) {
			effectsL[i] = new JLabel();

			effectsL[i].setText(player.getEffects().get(i).getName());
			effectsL[i].setBounds(300, 130 + (i * 40), 100, 30);
			statusP.add(effectsL[i]);
		}

		//Displays Equipment information
		JLabel[] equipmentL = new JLabel[player.getEquip().length];
		for (int i = 0; i < (equipmentL.length); i++) {
			//Checks if equipment exists
			if (player.getEquip()[i] != null) {
				//Creates empty JLabel to add to array
				equipmentL[i] = new JLabel();

				//Creates equipment data JLabels
				equipmentL[i].setText(player.getEquip()[i].getName());
				equipmentL[i].setBounds(10,50 + (90 * i),100,30);
				int index = i;
				equipmentL[i].addMouseMotionListener(new MouseMotionListener() {
					@Override
					public void mouseMoved(MouseEvent e) {
						equipDesc.setText(html1 + 200 + html2 + player.getEquip()[index].getDesc());
					}

					@Override
					public void mouseDragged(MouseEvent e) {

					}
				});
				statusP.add(equipmentL[i]);
			}
		}
		
		statusP.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				equipDesc.setText("");
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

		//Create back to mainScreen button
		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setContentPane(combatP);
			}
		});
		statusP.add(continueB);
		
		/*
		 *  Spell Panel
		 */
		JLabel spellNameL = new JLabel();
		spellNameL.setBounds(10, 10, 400, 30);
		spellP.add(spellNameL);

		JLabel SpellDescL = new JLabel();
		SpellDescL.setBounds(10, 60, 400, 300);
		spellP.add(SpellDescL);

		ArrayList<JButton> spellButtons = new ArrayList<JButton>();
		for (int i = 0, a = 0; i < player.getSpells().size(); i++, a++) {
			spellButtons.add(new JButton(player.getSpells().get(i).getName()));
			spellButtons.get(i).setName(Integer.toString(a));
			if (a == 4)
				a = 0;
			if (i < 4) {
				spellButtons.get(i).setBounds((110 * a) + 10,360,100,30);
				spellP.add(spellButtons.get(i));
			}
			else if (i < 8) {
				spellButtons.get(i).setBounds((110 * a) + 10,400,100,30);
				spellP.add(spellButtons.get(i));
			}
			else if (i % 8 < 4) {
				spellButtons.get(i).setBounds((110 * a) + 10,360,100,30);
			}
			else {
				spellButtons.get(i).setBounds((110 * a) + 10,400,100,30);
			}
			int index = i;
			spellButtons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO fix spells
					playerSpell = player.getSpells().get(index);
					player.setAttckType("SPELL");
					//outcome();
				}
			});
			spellButtons.get(i).addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseMoved(MouseEvent e) {
					spellNameL.setText(player.getSpells().get(index).getName());
					SpellDescL.setText(html1 + "200" + html2 + player.getSpells().get(index).getDesc());
				}

				@Override
				public void mouseDragged(MouseEvent e) {

				}
			});
		}

		JButton nextB = new JButton("NEXT");
		nextB.setBounds(450, 360, 100, 30);
		nextB.setName("0");
		nextB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO fix this, this needs fixed real bad. Fix it.
				int page = Integer.parseInt(((JButton)e.getSource()).getName());
				if (page == ((player.getSpells().size() - 1) / 8) + 1) {
					for (int i = 0; i < player.getSpells().size(); i++)
						if (i < 8)
							spellP.add(spellButtons.get(i));
						else
							spellP.remove(spellButtons.get(i));
					((JButton)e.getSource()).setName("0");
				}
				else {
					for (int i = 0; i < player.getSpells().size(); i++)
						if (i >= 8 * page && i <= (page + 1) * 8)
							spellP.add(spellButtons.get(i));
						else
							spellP.remove(spellButtons.get(i));
					((JButton)e.getSource()).setName(Integer.toString(page+1));
				}
				spellP.repaint();
				spellP.revalidate();
			}
		});
		if (player.getSpells().size() > 8) {
			spellP.add(nextB);
		}

		spellP.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				spellNameL.setText("");
				SpellDescL.setText("");
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

		JButton spellBackB = new JButton("BACK");
		spellBackB.setBounds(450, 400, 100, 30);
		spellBackB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setContentPane(combatP);
			}
		});
		spellP.add(spellBackB);

		
    }

    
    //Starts Combat between player and enemies
    public void initiateCombat(Enemy[] enemy, int w, int h, int obstacleNum) {
    	//Initialize variables
    	this.enemy = new Enemy[]{new Enemy("The Great Wall of America", 10, "Grey", "Grey", 70, 1, 1, 1), new Enemy("The Great Wall of America", 10, "Grey", "Grey", 75, 3, 2, 3)};
        width = w;
        height = h;
        gridButton = new Grid[width][height];
        winner = false;
        
    	//Initialize Grid
        //Enemy Space
        for (int i = 0, x = 0, y = 0; i < this.enemy.length; i++) {
            do {
                x = random.nextInt(width / 4) + 1;
                y = random.nextInt(height / 2) + height / 4;
            } while (gridButton[x][y] != null);
            gridButton[x][y] = new Grid("E");
            gridButton[x][y].setNum(i);
            gridButton[x][y].setEnemy(this.enemy[i]);
        }
        //Player Space
        gridButton[random.nextInt(width / 4) + (width * 3 / 4) - 1][random.nextInt(height / 2) + height / 4] = new Grid("O");
        //Empty Space
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                if (gridButton[x][y] == null)
                    gridButton[x][y] = new Grid(" ");
                gridButton[x][y].assign(x, y);
            }

        loadImages();
        playerAction();
    }
    
    //Loads images to use
    private void loadImages() {
    	
        //System.out.println("Loading images");
        //System.out.println("Loading Complete");
    }
    
    
    //Player makes combat choice
	private void playerAction() {
		
		
		//Grid
		resetGrid();

		

        
	}


	//Opens "spell book"
	private void spellSelection() {
		
		
	}
	
	//Opens inventory
	private void inventoryScreen() {
		
		JPanel inventoryP = new JPanel();
		inventoryP.setLayout(null);
		inventoryP.setSize(mainFrame.getWidth(), mainFrame.getHeight());

		JLabel currentL = new JLabel();
		currentL.setText("This is your inventory");
		currentL.setBounds(300, 10, 300, 30);
		inventoryP.add(currentL);

		JLabel text1 = new JLabel();
		text1.setBounds(10, 10, 400, 30);
		inventoryP.add(text1);

		JLabel text2 = new JLabel();
		text2.setBounds(10, 60, 400, 300);
		inventoryP.add(text2);

		ArrayList<JButton> invButtons = new ArrayList<JButton>();
		for (int i = 0, a = 0; i < player.getInventory().size(); i++, a++) {
			invButtons.add(new JButton(player.getInventory().get(i).getName()));
			if (a == 4)
				a = 0;
			if (i < 4) {
				invButtons.get(i).setBounds((110 * a) + 10,360,100,30);
				inventoryP.add(invButtons.get(i));
			}
			else if (i < 8) {
				invButtons.get(i).setBounds((110 * a) + 10,400,100,30);
				inventoryP.add(invButtons.get(i));
			}
			else if (i % 8 < 4)
				invButtons.get(i).setBounds((110 * a) + 10,360,100,30);
			else
				invButtons.get(i).setBounds((110 * a) + 10,400,100,30);
			int index = i;
			invButtons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(player.getInventory().get(index) instanceof Consumable) {
						//TODO fix inventory
						consume = (Consumable) player.getInventory().get(index);
						player.getInventory().remove(index);
						System.out.println("item " + index + "has been removed");
						player.setAttckType("ITEM");
						itemType = "CONSUMABLE";
						//outcome();
					}

				}
			});
			invButtons.get(i).addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseMoved(MouseEvent e) {
					Item item = player.getInventory().get(index);
					text1.setText(item.getName());
					text2.setText(item.getDesc() + index);
				}

				@Override
				public void mouseDragged(MouseEvent e) {

				}
			});
		}

		JButton nextB = new JButton("NEXT");
		nextB.setBounds(450, 360, 100, 30);
		nextB.setName("0");
		nextB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO fix this, this needs fixed real bad. Fix it.
				int page = Integer.parseInt(((JButton)e.getSource()).getName());
				if (page == ((player.getInventory().size() - 1) / 8) + 1) {
					for (int i = 0; i < player.getInventory().size(); i++)
						if (i < 8)
							inventoryP.add(invButtons.get(i));
						else
							inventoryP.remove(invButtons.get(i));
					((JButton)e.getSource()).setName("0");
				}
				else {
					for (int i = 0; i < player.getInventory().size(); i++)
						if (i >= 8 * page && i <= (page + 1) * 8)
							inventoryP.add(invButtons.get(i));
						else
							inventoryP.remove(invButtons.get(i));
					((JButton)e.getSource()).setName(Integer.toString(page+1));
				}
				inventoryP.repaint();
				inventoryP.revalidate();
			}
		});
		if (invButtons.size() > 8)
			inventoryP.add(nextB);

		MouseMotionListener mouseOver = new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				text1.setText("");
				text2.setText("");
			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		};

		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inventoryP.removeMouseMotionListener(mouseOver);
				playerAction();
			}
		});
		inventoryP.add(continueB);

		inventoryP.addMouseMotionListener(mouseOver);
	}
	
	//Sets target of attack
	private void setTarget(int range, String[] target)
    {
        combatP.removeAll();
        int playerX = 0, playerY = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                gridButton[x][y].setBounds(x * 30 + 10, y * 30 + 10, 30, 30);
                if (gridButton[x][y].getSymbol().equals("O")) {
                    playerX = x;
                    playerY = y;
                }
                combatP.add(gridButton[x][y]);
            }
        for (int x = playerX - range; x <= playerX + range; x++)
            for (int y = playerY - range; y <= playerY + range; y++)
                if (x < width  && y < height)
                    if (Math.abs(x - playerX) + Math.abs(y - playerY) <= range)
                    	for (int i = 0; i < target.length; i++)
                    		if (gridButton[x][y].getSymbol().equalsIgnoreCase(target[i])) {
                    			gridButton[x][y].setBackground(new Color(200,0,0));
                    			x2 = x;
                    			y2 = y;
                    			gridButton[x][y].addActionListener(new ActionListener() {
                    				//TODO FIX THIS
									@Override
									public void actionPerformed(ActionEvent e) {
										targets--;
										targetNum[targets] = gridButton[x2][y2].getNum();
										if (targets == 0) {
											Enemy[] enemyList = new Enemy[targetNum.length];
											for (int i = 0; i < targetNum.length; i++)
												enemyList[i] = enemy[targetNum[i]];
											outcome(enemyList);
										}
									}
                    				
            					});
                    			break;
                    		}
                    		else
                    			gridButton[x][y].setBackground(new Color(100,0,0));

		JButton continueB = new JButton("BACK");
		continueB.setBounds(450, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerAction();
			}
		});
		combatP.add(continueB);

        //refresh
        combatP.revalidate();
        combatP.repaint();
    }

	//Player chooses movement
	private void setMovement(int range, int startingX, int startingY) {
		enemyTurn = new ArrayList<>();
		ActionListener action;
		combatP.removeAll();
        int playerX = 0, playerY = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                gridButton[x][y].setBounds(x * 30 + 10, y * 30 + 10, 30, 30);
                if (gridButton[x][y].getSymbol().equals("O")) {
                    playerX = x;
                    playerY = y;
                }
                combatP.add(gridButton[x][y]);
            }
        for (int x = playerX - 1; x <= playerX + 1; x++)
            for (int y = playerY - 1; y <= playerY + 1; y++)
                if (x < width  && y < height)
                    if (Math.abs(x - playerX) + Math.abs(y - playerY) <= 1)
                		if (gridButton[x][y].getSymbol().equalsIgnoreCase(" ")) {
                			gridButton[x][y].setBackground(new Color(200,0,0));
                			x2 = x;
                			y2 = y;
                			playerX2 = playerX;
                			playerY2 = playerY;
                			action = new ActionListener() {
                				@Override
                				public void actionPerformed(ActionEvent e) {
            						int x = Integer.parseInt(e.getSource().toString().substring(0, e.getSource().toString().indexOf(" ")));
            						int y = Integer.parseInt(e.getSource().toString().substring(e.getSource().toString().indexOf(" ") + 1));
            						resetGrid();
            						gridButton[x][y].setSymbol("O");
            						gridButton[playerX2][playerY2].setSymbol(" ");
                					if (range != 1)
                						setMovement(range - 1, startingX, startingY);
                					else {
                						System.out.println("Player sucessfully moved");
                						resetGrid();
                						ArrayList<Integer> num = new ArrayList<>();
                						int enemyNum;
                						for (int i = 0; i < enemy.length; i++) {
                							do {
                								enemyNum = (int)(Math.random() * enemy.length);
                							} while(num.contains(enemyNum));
                							num.add(enemyNum);
                							enemyTurn.add(enemy[enemyNum]);
                						}
                						outcome();
                					}
                				}
                			};
                			gridButton[x][y].addActionListener(action);
                		}

        
	}
	
	//Calculates outcome of player choices
	private void outcome(Enemy[] target) {
		combatP.removeAll();
		
		//Player attack
		for (int i = 0; i < targetNum.length; i++)
			if (player.getAttackType().equals("ATTACK")) {
				JLabel playerAttackL = new JLabel();
				playerAttackL.setText(html1 + "200" + html2 + "You slash at the enemy in anger. It only does " + enemy[targetNum[i]].damageCheck(player.getDamage()) + " damage.");
				playerAttackL.setBounds(10, 200, 300, 90);
				combatP.add(playerAttackL);
			}
			else if (player.getAttackType().equals("SPELL")) {
				if (playerSpell.getType().contains("DAMAGE"))
					playerTurn += enemy[targetNum[i]].getName() + " takes " + enemy[targetNum[i]].damageCheck(playerSpell.getAttack()) + " points of damage. ";
				if (playerSpell.getType().contains("HEAL")) {
					player.addHealth(playerSpell.getAttack());
					playerTurn += "You heal yourself for " + Integer.toString(playerSpell.getAttack()) + " hit points. ";
				}
				if (playerSpell.getType().contains("STATUS")) {
					enemy[targetNum[i]].applyStatus(playerSpell.getEffect());
					playerTurn += enemy[targetNum[i]].getName() + " is now inflicted with " + playerSpell.getEffect().toLowerCase() + ". ";
				}
	
				JLabel playerAttackL = new JLabel();
				playerAttackL.setText(html1 + "200" + html2 + playerSpell.getAtckDesc() + " " + playerTurn);
				playerAttackL.setBounds(10, 200, 300, 90);
				combatP.add(playerAttackL);
			}
			else if (player.getAttackType().equals("ITEM"))
				if (itemType.equals("CONSUMABLE"))
					if(consume.getType().equals("HEAL")) {
						player.addHealth(consume.getStrength());
						JLabel playerAttackL = new JLabel();
						playerAttackL.setText(html1 + "200" + html2 + "The item you have selected has healed you for " + consume.getStrength() + " hit points!");
						playerAttackL.setBounds(10, 200, 300, 90);
						combatP.add(playerAttackL);
					}

		JButton continueB = new JButton("CONTINUE");
		continueB.setBounds(400, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (player.getHealth() <= 0) {
					combatP.removeAll();
					defeat();
				}
				for (int i = 0; i <= enemy.length; i++) {
					if (i == enemy.length)
						victory();
					if (enemy[i].getHealth() > 0)
						break;
				}
				playerTurn = "";
				outcome();
			}
		});
		combatP.add(continueB);

		combatP.repaint();
		combatP.revalidate();
	}
	
	//Calculates non-player choices
	private void outcome() {
		//TODO Enemy AI
		combatP.removeAll();
		System.out.println("Movement complete!");
		
		if (enemyTurn.get(0).getHealth() > 0) {
			//Makes intelligent decision
			int enemyX = enemyTurn.get(0).getPos()[0], enemyY = enemyTurn.get(0).getPos()[1];
			int playerX = player.getPos()[0], playerY = player.getPos()[1];
			int range = Math.abs(enemyX - playerX) + Math.abs(enemyY - playerY);
			
			//Enemy attack
			enemyTurn.get(0).randomAttack(range);
			if (enemyTurn.get(0).getAttackType().equals("ATTACK")) {
				JLabel enemyAttackL = new JLabel();
				enemyAttackL.setText(html1 + "200" + html2 + enemyTurn.get(0).getAttackDesc() + " You take " + player.damageCheck(enemyTurn.get(0).getDamage()) + " damage.");
				enemyAttackL.setBounds(10, 10, 300, 90);
				combatP.add(enemyAttackL);
			}
			else if(enemyTurn.get(0).getAttackType().equals("STATUS")) {
				JLabel enemyAttackL = new JLabel();
				enemyAttackL.setText(html1 + "200" + html2 + enemyTurn.get(0).getAttackDesc());
				enemyAttackL.setBounds(10, 10, 300, 90);
				combatP.add(enemyAttackL);
				player.applyStatus(enemyTurn.get(0).getInflictStatus());
			}
			else if (enemyTurn.get(0).getAttackType().contains("SPELL")) {
				String enemyAttackS;
				JLabel enemyAttackL = new JLabel();
				enemyAttackS = html1 + "200" + html2 + enemyTurn.get(0).getAttackDesc();
				enemyAttackL.setBounds(10, 10, 300, 90);
				combatP.add(enemyAttackL);
				if (enemyTurn.get(0).getAttackType().contains("DAMAGE"))
					 enemyAttackS = enemyAttackS + " You take " + player.damageCheck(enemyTurn.get(0).getAttackDamage()) + " damage.";
				if (enemyTurn.get(0).getAttackType().contains("STATUS")) {
					enemyAttackS = enemyAttackS + " You are inflicted with " + enemyTurn.get(0).getInflictStatus() + ".";
					player.applyStatus(enemyTurn.get(0).getInflictStatus());
				}
				if (enemyTurn.get(0).getAttackType().contains("HEAL")) {
					enemyAttackS += " ";
					if (enemyTurn.get(0).getSpecialName()) {
						enemyAttackS = enemyAttackS + enemyTurn.get(0).getName() + " heals for " + enemyTurn.get(0).getAttackDamage() + " points of health";
						enemyTurn.get(0).addHealth(enemyTurn.get(0).getAttackDamage());
					}
					else {
						enemyAttackS = enemyAttackS + "The " + enemyTurn.get(0).getName() + " heals for " + enemyTurn.get(0).getAttackDamage() + " points of health";
						enemyTurn.get(0).addHealth(enemyTurn.get(0).getAttackDamage());
					}
				}
				enemyAttackL.setText(enemyAttackS);
			}
			else if (enemyTurn.get(0).getAttackType().equals("MOVE")) {
				ArrayList<Integer[]> queue = new ArrayList<>();
				ArrayList<Integer[]> branch = new ArrayList<>();
				switch (enemyTurn.get(0).getApproach()) {
					case "AGRO":
						boolean path = true;
						int xDir = enemyX;
						int yDir = enemyY;
						System.out.println("Enemy moves");
						
						//Determine direction
						if (enemyX < playerX)
							if (enemyY < playerY)
								while(path)
								{
									//Adds delay to enemy movement
									try {
										TimeUnit.SECONDS.sleep(1);
									}
									catch (InterruptedException e) {
										e.printStackTrace();
									}
									xDir++;
									//Left movement
									if (!gridButton[xDir][yDir].getSymbol().equalsIgnoreCase(" ")) {
										//If left movement not possible
										xDir--;
										if (!gridButton[xDir][yDir + 1].getSymbol().equalsIgnoreCase(" ")) {
											yDir++;
											gridButton[xDir][yDir].setNum(gridButton[enemyX][enemyY].getNum());
											gridButton[enemyX][enemyY].setNum(-1);
											gridButton[xDir][yDir].setSymbol("E");
											gridButton[enemyX][enemyY].setSymbol(" ");
											resetGrid();
											gridButton[xDir][yDir].setBackground(Color.RED);
											continue;
										}
										else if (!gridButton[xDir][yDir - 1].getSymbol().equalsIgnoreCase(" ")) {
											yDir--;
											gridButton[xDir][yDir].setNum(gridButton[enemyX][enemyY].getNum());
											gridButton[enemyX][enemyY].setNum(-1);
											gridButton[xDir][yDir].setSymbol("E");
											gridButton[enemyX][enemyY].setSymbol(" ");
											resetGrid();
											gridButton[xDir][yDir].setBackground(Color.RED);
											continue;
										}
										else {
											//If no direct movement is possible
											path = false;
											break;
										}
									}
									else {
										gridButton[xDir][yDir].setNum(gridButton[enemyX][enemyY].getNum());
										gridButton[enemyX][enemyY].setNum(-1);
										gridButton[xDir][yDir].setSymbol("E");
										gridButton[enemyX][enemyY].setSymbol(" ");
										resetGrid();
										gridButton[xDir][yDir].setBackground(Color.RED);
									}
									//Adds delay to enemy movement
									try {
										TimeUnit.SECONDS.sleep(1);
									}
									catch (InterruptedException e) {
										e.printStackTrace();
									}
									//Up Movement
									yDir++;
									//TODO
								}
							else if (enemyY > playerY)
								;//yDir = "DOWN";
						else 
							if (enemyY < playerY)
								;//yDir = "UP";
							else if (enemyY > playerY)
								;//yDir = "DOWN";
						
						try {
							TimeUnit.SECONDS.sleep(1);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						System.out.println("Enemy moved");
						break;
					default:
						
						break;
				}
				
			}
			
			//Checks for enemy status
			if (!enemyTurn.get(0).getEffects().isEmpty())
				for (int a = 0; a < enemyTurn.get(0).getEffects().size(); a++)
					if (enemyTurn.get(0).getEffects().get(a).getName().equals("POISON")) {
						JLabel enemyStatusL = new JLabel();
						enemyStatusL.setText(html1 + "200" + html2 + "The corrosive substance eats through the wall, it suffers " + enemyTurn.get(0).damageCheck2(player.getDamage()) + " points of damage");
						enemyStatusL.setBounds(10, 300, 300, 90);
						combatP.add(enemyStatusL);
					}

		}
		
		enemyTurn.remove(0);
		if (enemyTurn.isEmpty())
		{
			//Checks for player status
			if (!player.getEffects().isEmpty())
				for (int a = 0; a < player.getEffects().size(); a++)
					if (player.getEffects().get(a).getName().equals("POISON")) {
						JLabel playerStatusL = new JLabel();
						playerStatusL.setText(html1 + "200" + html2 + "The toxins run through your veins, you suffer " + player.damageCheck2(5) + " points of damage");
						playerStatusL.setBounds(10, 100, 300, 90);
						combatP.add(playerStatusL);
					}
			
			//Continues to playerAction
			JButton continueB = new JButton("CONTINUE");
			continueB.setBounds(400, 400, 100, 30);
			continueB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (player.getHealth() <= 0) {
						combatP.removeAll();
						defeat();
					}
					for (int i = 0; i <= enemy.length; i++) {
						if (i == enemy.length)
							victory();
						if (enemy[i].getHealth() > 0)
							break;
					}
					for (int i = 0; i < enemy.length; i++)
						enemy[i].checkStatus();
					playerTurn = "";
					player.checkStatus();
					playerAction();
				}
			});
			combatP.add(continueB);
		}
		else {
			JButton continueB = new JButton("CONTINUE");
			continueB.setBounds(400, 400, 100, 30);
			continueB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					outcome();
				}
			});
			combatP.add(continueB);
		}
		
	}
	
	//Resets grid to default state
	private void resetGrid() {
		//TODO
        for (int y = 0; y < width; y++)
            for (int x = 0; x < height; x++)
            {
            	if(gridButton[x][y].getSymbol().equalsIgnoreCase("O"))
            		player.setPos(new int[] { x, y});
            	else if(gridButton[x][y].getSymbol().equalsIgnoreCase("E"))
            		enemy[gridButton[x][y].enemyNum].setPos(new int[] { x, y});
            	
            	for (ActionListener act : gridButton[x][y].getActionListeners())
            		gridButton[x][y].removeActionListener(act);
                gridButton[x][y].setBounds(x * 30 + 10, y * 30 + 10, 30, 30);
                gridButton[x][y].setBackground(new JButton().getBackground());
                gridButton[x][y].setTarget(false);
                combatP.add(gridButton[x][y]);
            }
	}
	
	//Player wins
	private void victory() {
		//TODO create win message against multiple enemies
		for (int i = 0; i < enemy.length; i++) {
			JLabel victoryL = new JLabel();
			victoryL.setText(html1 + "200" + html2 + "You have successfully defeated " + enemy[i].getName() + ". You walk away without a reward because you only wanted to fight " + enemy[i].getName() + " for the satisfaction of beating it to the ground.");
			victoryL.setBounds(10, 10, 250, 100);
			combatP.add(victoryL);

			JLabel expGainL = new JLabel();
			expGainL.setText("You gained " + enemy[i].getExp() + " EXP.");
			expGainL.setBounds(10, 200, 100, 30);
			combatP.add(expGainL);
		}

		JButton continueB = new JButton("CONTINUE");
		continueB.setBounds(400, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < enemy.length; i++)
					player.addExp(enemy[i].getExp());
				player.addHealth();
				if (location.contains("newgame"))
					events.mainScreen();
				else if (location.contains("dungeon"))
					events.event("DUNGEON", "");
			}
		});
		combatP.add(continueB);

		
	}

	//Player loses
	private void defeat() {
		//TODO create lose message against multiple enemies
		for (int i = 0; i < enemy.length; i++) {
			JLabel defeatL = new JLabel();
			defeatL.setText(html1 + "200" + html2 + "You lose consciousness after " + enemy[i].getName() + " lands its final blow on you...");
			defeatL.setBounds(10, 10, 250, 200);
			combatP.add(defeatL);
		}

		JLabel lossL = new JLabel();
		lossL.setText(html1 + "200" + html2 + "The wall lets you leave with what little dignity you have left.");
		lossL.setBounds(10, 200, 100, 200);
		combatP.add(lossL);

		JButton continueB = new JButton("CONTINUE");
		continueB.setBounds(400, 400, 100, 30);
		continueB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.addHealth();
				mainFrame.remove(combatP);
				//TODO switch to mainGame
			}
		});
		combatP.add(continueB);

		
	}

	//Grid buttons
    private class Grid extends JButton {
    	private Player player;
    	private Enemy enemy;
    	private String symbol;
    	private int enemyNum;
    	private int x;
    	private int y;
    	private boolean target;
    	
        public Grid(String s) {
        	super(s);
        	symbol = s;
        	target = false;
        	enemyNum = -1;
        	
            setFont(new Font("SanSerif", Font.PLAIN, 10));
            setLayout(null);
            setVisible(true);
            setMargin(new Insets(0, 0, 0, 0));
            setBorder(null);
            setBorderPainted(true);
            setFocusPainted(false);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getText());
                }
            });
        }

        public String getSymbol() { return symbol; }
        
        public int getNum() { return enemyNum; }
        
        public Enemy getEnemy() { return enemy; }

        public void setSymbol(String s) {
        	setText(s);
        	symbol = s;
        }
        
        public void setEnemy(Enemy e) { enemy = e; }
        
        public void assign(int xNew, int yNew) {
        	x = xNew;
        	y = yNew;
        }
        
        public void setNum(int n) { enemyNum = n; }
        
        public void setTarget(boolean setter) { target = setter; }
        
    }
}
