/* To do list:
 * 
 */

package adventure;

import adventure.character.Player;

public class Global
{
	public static Player player = new Player("Placeholder", 60, "non-existant", "non-existant", 15, 10, 1);
	public static boolean[] locationClear = new boolean[1]; 
	public static boolean[] dungeonComp = new boolean[7];
	
}