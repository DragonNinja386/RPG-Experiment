package game;

import javax.swing.*;

public class GraphicInterface {
	MainFrame mainFrame = new MainFrame();
	
	public GraphicInterface() {
		
	}
	
	public void setPanel(JPanel jp) {
		mainFrame.setContentPane(jp);
	}
}
