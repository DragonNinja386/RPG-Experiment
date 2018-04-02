//Creates a global JFrame to use

package adventure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	//Frame variables
	private static final long serialVersionUID = 1L;
	private final int APPLICATION_HEIGHT = 500;
	private final int APPLICATION_WIDTH = 600;

	//Other variables
	private JButton nextB;
	
	public MainFrame() {
		super("Cool Game!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Adventure");
		
		nextB = new JButton("NEXT");
		nextB.setBounds(340, 400, 100, 30);
	}
	
	public void buttons(JButton[] jb) {
		int numOfPages = 1;
		for (ActionListener al : nextB.getActionListeners())
			nextB.removeActionListener(al);
		if (jb.length > 8)
			this.getContentPane().add(nextB);
		else if (this.getContentPane().getComponentAt(nextB.getX(), nextB.getY()) == nextB)
			this.getContentPane().remove(nextB);
		for (int i = 0, a = 0; i < jb.length; i++, a++) {
			if (a == 8) {
				a = 0;
				numOfPages++;
			}
			if (a < 5)
				jb[i].setBounds(110 * a + 10, 360, 100, 30);
			else
				jb[i].setBounds(110 * (a - 5) + 10, 400, 100, 30);
			this.getContentPane().add(jb[i]);
			jb[i].setVisible(false);
		}
		for (int i = 0; i < 8; i++)
			if (i < jb.length)
				jb[i].setVisible(true);
			else
				break;
		
		final int NUMOFPAGES = numOfPages;
		nextB.setName("1");
		nextB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pageNum = Integer.parseInt(nextB.getName());
				if (++pageNum > NUMOFPAGES)
					pageNum = 1;
				((JButton)e.getSource()).setName(Integer.toString(pageNum));
				
				for (int i = 0; i < jb.length; i++)
					if (i >= 8 * (pageNum - 1) && i <= pageNum * 8)
						jb[i].setVisible(true);
					else
						jb[i].setVisible(false);
			}
		});
	}
	
	public void buttons(ArrayList<JButton> jb) {
		int numOfPages = 1;
		for (ActionListener al : nextB.getActionListeners())
			nextB.removeActionListener(al);
		if (jb.size() > 8)
			this.getContentPane().add(nextB);
		else if (this.getContentPane().getComponentAt(nextB.getX(), nextB.getY()) == nextB)
			this.getContentPane().remove(nextB);
		for (int i = 0, a = 0; i < jb.size(); i++, a++) {
			if (a == 8) {
				a = 0;
				numOfPages++;
			}
			if (a < 5)
				jb.get(i).setBounds(110 * a + 10, 360, 100, 30);
			else
				jb.get(i).setBounds(110 * (a - 5) + 10, 400, 100, 30);
			this.getContentPane().add(jb.get(i));
		}
		
		final int NUMOFPAGES = numOfPages;
		nextB.setName("1");
		nextB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pageNum = Integer.parseInt(nextB.getName());
				if (++pageNum > NUMOFPAGES)
					pageNum = 1;
				((JButton)e.getSource()).setName(Integer.toString(pageNum));
				
				for (int i = 0; i < jb.size(); i++)
				{
					if (i >= 8 * (pageNum - 1) && i <= pageNum * 8)
						jb.get(i).setVisible(true);
					else
						jb.get(i).setVisible(false);
				}
			}
		});
	}
}
