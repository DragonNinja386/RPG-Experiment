//Creates a global JFrame to use

package adventure;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final int APPLICATION_HEIGHT = 500;
	private final int APPLICATION_WIDTH = 600;

	public MainFrame()
	{
		super("Cool Game!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Adventure");
	}
}
