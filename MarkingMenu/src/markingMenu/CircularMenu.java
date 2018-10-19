package markingMenu;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CircularMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	String[] label_bouttons;
	JButton[] boutons;
	JPanel[] jpanels;
	Container leSuperieur;
	
	int x, y;

	public CircularMenu(String titre, String[] tab, int xOrigine, int yOrigine) {
		super(titre);

		x = xOrigine;
		y = yOrigine;
		
		label_bouttons = tab;
		initButton();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1000, 1000));
		
		boutons[0].setMaximumSize(new Dimension(100,100));
		boutons[0].setLocation(800, 800);
		
		
		leSuperieur = this.getContentPane();
		addComponentsToPane(leSuperieur);
		
		
		setVisible(true);
	}
	
	public void addComponentsToPane(Container container)
	{
		container.setLayout(null);
		//this.setLayout(null);
		int xButton;
		int yButton;
		
		Dimension size;
		
		int iBoucle;
		iBoucle = boutons.length;
		if (iBoucle >8)
			iBoucle = 8;
		
		for(int i = 0; i < iBoucle; i++)
		{
			container.add(boutons[i]);
			size = boutons[i].getPreferredSize();
			
			xButton = (int) (x +  Math.cos(2*Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);
			yButton = (int) (y +  Math.sin(2*Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);
						
			boutons[i].setBounds(xButton, yButton, size.width, size.height);
		}
		
		if (boutons.length > 8)
		{
			iBoucle = boutons.length - 8;
			
			for(int i = 0; i < iBoucle; i++)
			{
				container.add(boutons[i+8]);
				size = boutons[i+8].getPreferredSize();
				xButton = (int) (x +  Math.cos(2*Math.PI / 8 * (2 % 8) + 0.0) * 125);
				yButton = (int) (y +  Math.sin(2*Math.PI / 8 * (2 % 8) + 0.0) * 125) + i *75;
				boutons[i+8].setBounds(xButton, yButton, size.width, size.height);

			}
		}
	}
	

	public void initButton() {
		int size = label_bouttons.length;
		boutons = new JButton[size];
		jpanels = new JPanel[size];
		
		for (int i = 0; i < size; i++) {
			boutons[i] = new JButton(label_bouttons[i]);
			jpanels[i] = new JPanel();
		}
	}

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CircularMenu menu = new CircularMenu("Circular Menu", argv, 500, 250);
			}
		});
	}

}
