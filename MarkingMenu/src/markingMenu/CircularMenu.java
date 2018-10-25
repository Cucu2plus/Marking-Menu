package markingMenu;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CircularMenu
{

	Vector<JButton> boutons;

	JPanel panel;

	int x, y;

	public CircularMenu(String titre, Vector<JButton> boutons, int xOrigine, int yOrigine, JPanel panel)
	{

		x = xOrigine;
		y = yOrigine;
		this.panel = panel;

		this.boutons = boutons;

		boutons.get(0).setMaximumSize(new Dimension(100, 100));
		boutons.get(0).setLocation(800, 800);

		addComponentsToPane(panel);
		hide_buttons();
	}

	public void addComponentsToPane(Container container)
	{
		container.setLayout(null);
		int xButton;
		int yButton;

		Dimension size;

		int iBoucle;
		iBoucle = boutons.size();
		if (iBoucle > 8)
			iBoucle = 8;

		//Pour chacun des boutons, on le place selon la formule suivante
		for (int i = 0; i < iBoucle; i++)
		{
			container.add(boutons.get(i));
			size = boutons.get(i).getPreferredSize();

			xButton = (int) (x + Math.cos(2 * Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);
			yButton = (int) (y + Math.sin(2 * Math.PI / iBoucle * (i % iBoucle) + 0.0) * 125);

			boutons.get(i).setBounds(xButton, yButton, size.width, size.height);
		}

		//Si on a plus de huit bouttons, on les affichent en bas du cercle
		if (boutons.size() > 8)
		{
			iBoucle = boutons.size() - 8;

			for (int i = 0; i < iBoucle; i++)
			{
				container.add(boutons.get(i + 8));
				size = boutons.get(i + 8).getPreferredSize();
				xButton = (int) (x + Math.cos(2 * Math.PI / 8 * (2 % 8) + 0.0) * 125);
				yButton = (int) (y + Math.sin(2 * Math.PI / 8 * (2 % 8) + 0.0) * 125) + (i + 1) * 75;
				boutons.get(i + 8).setBounds(xButton, yButton, size.width, size.height);

			}
		}

	}

	//Procédure pour ne plus afficher les boutons
	void hide_buttons()
	{
		for (JButton button : boutons)
		{
			button.setVisible(false);
		}
	}

	//Procédure pour afficher les boutons, dont le centre du cercle et aux coordonnées données
	void show_buttons(int xOrigine, int yOrigine)
	{
		x = xOrigine;
		y = yOrigine;

		addComponentsToPane(panel);

		for (JButton button : boutons)
		{
			button.setVisible(true);
		}

	}

}
