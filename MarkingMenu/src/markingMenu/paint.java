package markingMenu;
//////////////////////////////////////////////////////////////////////////////

// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/

import static java.lang.Math.*;

import java.util.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import java.awt.event.*;
import javax.swing.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/* paint *******************************************************************/

class Paint extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Shape> shapes = new Vector<Shape>();
	Vector<Color> couleurs = new Vector<Color>();
	Color couleur = Color.BLACK;

	boolean autrequeDebut = false;

	class Tool extends AbstractAction implements MouseInputListener
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Point o;
		Shape shape;
		String name;

		public Tool(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		// Action lorsque l'on choisit un outil
		public void actionPerformed(ActionEvent e)
		{
			// On remet notre booléen à false pour qu'on puisse afficher le circular menu de
			// base de nouveau
			autrequeDebut = false;

			// On cache le circular menu des outils
			circularTools.hide_buttons();
			// System.out.println("using tool " + this);

			// On mets à jour les listeners
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}

		public void mouseClicked(MouseEvent e)
		{
			if (SwingUtilities.isRightMouseButton(e))
			{
				if (!autrequeDebut)
				{
					// Si on a fait un clic droit, on affiche le circular menu de base
					circular.show_buttons(e.getX(), e.getY());
					panel.repaint();
				}

			}
		}

		public void mouseEntered(MouseEvent e)
		{
		}

		public void mouseExited(MouseEvent e)
		{
		}

		public void mousePressed(MouseEvent e)
		{
			o = e.getPoint();
		}

		public void mouseReleased(MouseEvent e)
		{
			shape = null;
		}

		public void mouseDragged(MouseEvent e)
		{
		}

		public void mouseMoved(MouseEvent e)
		{
		}
	}

	Tool tools[] = { new Tool("pen") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void mouseDragged(MouseEvent e)
		{
			Path2D.Double path = (Path2D.Double) shape;
			if (path == null)
			{
				path = new Path2D.Double();
				path.moveTo(o.getX(), o.getY());
				shapes.add(shape = path);
				couleurs.add(couleur);
			}
			path.lineTo(e.getX(), e.getY());
			panel.repaint();
		}
	}, new Tool("rect") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void mouseDragged(MouseEvent e)
		{
			Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null)
			{
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				shapes.add(shape = rect);
				couleurs.add(couleur);
			}
			rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			panel.repaint();
		}
	}, new Tool("ellipse") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void mouseDragged(MouseEvent e)
		{
			Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
			if (ellipse == null)
			{
				ellipse = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
				shapes.add(shape = ellipse);
				couleurs.add(couleur);
			}
			ellipse.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			panel.repaint();
		}
	},

	};

	// Tableau de couleurs pour les boutons qu'on donne au circular menu de couleurs
	Color colors[] =

			{ Color.BLACK, Color.BLUE, Color.CYAN, Color.RED, Color.GRAY, Color.GREEN, Color.PINK, Color.MAGENTA,
					Color.ORANGE };

	// Tableau de String pour les boutons qu'on donne au circular menu de base
	String choix[] = { "Outils", "Couleur" };

	Tool tool;

	JPanel panel;

	CircularMenu circular;
	CircularMenu circularTools;
	CircularMenu circularColors;

	public Paint(String title)
	{
		super(title);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));

		//On créé les boutons pour notre circular menu d'outils
		Vector<JButton> buttonsTools = new Vector<JButton>();
		for (Tool tool : tools)
		{
			JButton button = new JButton();
			button.setText(tool.getName());
			button.addActionListener(tool);
			buttonsTools.add(button);
		}

		//On créé les boutons pour notre circular menu de base
		Vector<JButton> buttons = new Vector<JButton>();
		for (String choix_unitaire : choix)
		{
			JButton button = new JButton();
			button.setText(choix_unitaire);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					//Si on a cliqué sur un des boutons, on affiche le circular menu correspondant
					if (choix_unitaire.equals("Outils"))
					{
						autrequeDebut = true;
						circular.hide_buttons();
						circularTools.show_buttons(circular.x, circular.y);
						panel.repaint();
					} else
					{
						autrequeDebut = true;
						circular.hide_buttons();
						circularColors.show_buttons(circular.x, circular.y);
						panel.repaint();
					}

				}
			});
			buttons.add(button);
		}
		
		//On créé les boutons pour notre circular menu de couleurs
		Vector<JButton> buttonsColors = new Vector<JButton>();
		for (Color color : colors)
		{
			JButton button = new JButton();
			button.setBackground(color);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					autrequeDebut = false;
					circularColors.hide_buttons();
					couleur = color;

				}
			});
			buttonsColors.add(button);
		}

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			//Nécessaire pour afficher le circular menu de base la première fois qu'on fait un clic droit
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
				{

					if (!autrequeDebut)
					{
						circular.show_buttons(e.getX(), e.getY());
						panel.repaint();
					}

				}

			}
		});

		add(panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g)
			{

				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());

				g2.setColor(Color.BLACK);
				int i = 0;
				for (Shape shape : shapes)
				{
					g2.setColor(couleurs.get(i));
					g2.draw(shape);
					i++;
				}
			}
		});

		circular = new CircularMenu("cc", buttons, 200, 200, panel);
		circularTools = new CircularMenu("cc", buttonsTools, 200, 200, panel);
		circularColors = new CircularMenu("cc", buttonsColors, 200, 200, panel);

		pack();
		setVisible(true);
	}

	/* main *********************************************************************/

	public static void main(String argv[])
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Paint("paint");
			}
		});
	}
}