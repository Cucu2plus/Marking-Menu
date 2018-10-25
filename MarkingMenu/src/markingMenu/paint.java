package markingMenu;
//////////////////////////////////////////////////////////////////////////////

// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/

import static java.lang.Math.*;

import java.util.Vector;

import java.awt.BorderLayout;
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
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/* paint *******************************************************************/

class Paint extends JFrame
{
	Vector<Shape> shapes = new Vector<Shape>();
	Vector<Color> couleurs = new Vector<Color>();
	Color couleur = Color.BLACK;

	
	boolean afficheCircular = false;

	class Tool extends AbstractAction implements MouseInputListener
	{
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

		public void actionPerformed(ActionEvent e)
		{
			circular.hide_buttons();
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}

		public void mouseClicked(MouseEvent e)
		{
			if(SwingUtilities.isRightMouseButton(e))
			{
				System.out.println("cc");
				
				circular.show_buttons(e.getX(),e.getY());
				panel.repaint();
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
	}, new Tool("Color") {
		public void actionPerformed(ActionEvent e)
		{
			circular.hide_buttons();
			Color color = JColorChooser.showDialog(null, "Couleur", Color.WHITE);
			couleur = color;
		}

	} };
	Tool tool;

	JPanel panel;
	
	CircularMenu circular;

	public Paint(String title)
	{
		super(title);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));

		/*add(new JToolBar() {
			{
				for (AbstractAction tool : tools)
				{
					add(tool);
				}
			}
		}, BorderLayout.NORTH);*/
				
		Vector<JButton> buttons = new Vector<JButton>();
		for (Tool tool : tools)
		{
			JButton button = new JButton();
			button.setText(tool.getName());
			button.addActionListener(tool);
			buttons.add(button);
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
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(SwingUtilities.isRightMouseButton(e))
				{
					System.out.println("cc");
					
					circular.show_buttons(e.getX(),e.getY());
					panel.repaint();
				}
				
			}
		});
		


		add(panel = new JPanel() {
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

		pack();
		setVisible(true);
	}

	/* main *********************************************************************/

	public static void main(String argv[])
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				Paint paint = new Paint("paint");
			}
		});
	}
}