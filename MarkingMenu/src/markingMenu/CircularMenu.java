package markingMenu;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CircularMenu {

	//	String[] label_bouttons;
//	JButton[] boutons;
	Vector<JButton> boutons;
	//JPanel[] jpanels;
	
	JPanel panel;
	
	int x, y;

	public CircularMenu(String titre, Vector<JButton> boutons, int xOrigine, int yOrigine, JPanel panel) {
	//	super(titre);

		x = xOrigine;
		y = yOrigine;
		this.panel = panel;
		
//		label_bouttons = tab;
		this.boutons = boutons;
		
//		initButton();

//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setMinimumSize(new Dimension(1000, 1000));
		
		
		boutons.get(0).setMaximumSize(new Dimension(100,100));
		boutons.get(0).setLocation(800, 800);
		
		
		
		
		
		
	/*	boutons[0].setMaximumSize(new Dimension(100,100));
		boutons[0].setLocation(800, 800);*/
		
		
//		leSuperieur = this.getContentPane();
		addComponentsToPane(panel);
		hide_buttons();
	}
	
	
	
	public void addComponentsToPane(Container container)
	{
		container.setLayout(null);
		//this.setLayout(null);
		int xButton;
		int yButton;
		
		Dimension size;
		
		int iBoucle;
		iBoucle = boutons.size();
		if (iBoucle >8)
			iBoucle = 8;
		
		for(int i = 0; i < iBoucle; i++)
		{
			container.add(boutons.get(i));
			size = boutons.get(i).getPreferredSize();
			
			xButton = (int) (x +  Math.cos(2*Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);
			yButton = (int) (y +  Math.sin(2*Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);
						
			boutons.get(i).setBounds(xButton, yButton, size.width, size.height);
		}
		
		if (boutons.size() > 8)
		{
			iBoucle = boutons.size() - 8;
			
			for(int i = 0; i < iBoucle; i++)
			{
				container.add(boutons.get(i+8));
				size = boutons.get(i+8).getPreferredSize();
				xButton = (int) (x +  Math.cos(2*Math.PI / 8 * (2 % 8) + 0.0) * 125);
				yButton = (int) (y +  Math.sin(2*Math.PI / 8 * (2 % 8) + 0.0) * 125) + (i+1) *75;
				boutons.get(i+8).setBounds(xButton, yButton, size.width, size.height);

			}
		}
		 
	}
	
	void hide_buttons()
	{
		for(JButton button : boutons)
		{
			//button.setEnabled(false);
			button.setVisible(false);
		}
	}
	
	void show_buttons(int xOrigine, int yOrigine)
	{
		x = xOrigine;
		y = yOrigine;
		
		addComponentsToPane(panel);
		
		for(JButton button : boutons)
		{
			//button.setEnabled(true);
			button.setVisible(true);
		}
		
	}
	
	

/*	public void initButton() {
		int size = label_bouttons.length;
		boutons = new JButton[size];
		jpanels = new JPanel[size];
		
		for (int i = 0; i < size; i++) {
			boutons[i] = new JButton(label_bouttons[i]);
			jpanels[i] = new JPanel();
		}
	}*/

	/*public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CircularMenu menu = new CircularMenu("Circular Menu", argv, 500, 250);
			}
		});
	}*/

}
