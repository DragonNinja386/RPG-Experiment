//Alpha 3: The complete dungeon update
/* Changes:
 * Combat remembers last location
 * Dungeon is now occupied by enemies
 * Dungeon now has an item in it
 */

package adventure;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Game
{
	public static void main(String[] args)
	{		
		try //Sets the UI to match the systems UI
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
		}
		catch (Exception e)
		{
			System.out.println("Could not load System look");
		}
		
		//Initialize global variables
		MainFrame mainFrame = new MainFrame(); //Creates frame to put game on
		for (@SuppressWarnings("unused") boolean i : Global.dungeonComp)
		{
			i = false;
		}
		for (@SuppressWarnings("unused") boolean i : Global.locationClear)
		{
			i = false;
		}
		
		new Title(mainFrame); //Starts game
	}

}
