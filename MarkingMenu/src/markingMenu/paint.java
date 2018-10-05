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
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;


/* paint *******************************************************************/

class Paint extends JFrame {
	Vector<Shape> shapes = new Vector<Shape>();
	Vector<Color> couleurs = new Vector<Color>();
	Color couleur = Color.BLACK;

	class Tool extends AbstractAction
	           implements MouseInputListener {
	   Point o;
		Shape shape;
		public Tool(String name) { super(name); }
		public void actionPerformed(ActionEvent e) {
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) { o = e.getPoint(); }
		public void mouseReleased(MouseEvent e) { shape = null; }
		public void mouseDragged(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
	}
	
	Tool tools[] = {
		new Tool("pen") {
			public void mouseDragged(MouseEvent e) {
				Path2D.Double path = (Path2D.Double)shape;
				if(path == null) {
					path = new Path2D.Double();
					path.moveTo(o.getX(), o.getY());
					shapes.add(shape = path);
					couleurs.add(couleur);
				}
				path.lineTo(e.getX(), e.getY());
				panel.repaint();
			}
		},
		new Tool("rect") {
			public void mouseDragged(MouseEvent e) {
				Rectangle2D.Double rect = (Rectangle2D.Double)shape;
				if(rect == null) {
					rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
					shapes.add(shape = rect);
					couleurs.add(couleur);
				}
				rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
				             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
				panel.repaint();
			}
		},
		new Tool("ellipse") {
			public void mouseDragged(MouseEvent e) {
				Ellipse2D.Double ellipse = (Ellipse2D.Double)shape;
				if(ellipse == null) {
					ellipse = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
					shapes.add(shape = ellipse);
					couleurs.add(couleur);
				}
				ellipse.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()),
			             abs(e.getX()- o.getX()), abs(e.getY()- o.getY()));
				panel.repaint();
			}
		},
		new Tool("Color") {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Couleur", Color.WHITE);
				couleur = color;
			}
				
				
		}
	};
	Tool tool;

	JPanel panel;
	
	public Paint(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		add(new JToolBar() {{
			for(AbstractAction tool: tools) {
				add(tool);
			}
		}}, BorderLayout.NORTH);
		add(panel = new JPanel() {	
			public void paintComponent(Graphics g) {
				super.paintComponent(g);	
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				                    RenderingHints.VALUE_ANTIALIAS_ON);
		
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());
				
				g2.setColor(Color.BLACK);
				int i =0;
				for(Shape shape: shapes) {
					g2.setColor(couleurs.get(i));
					g2.draw(shape);
					i++;
				}
			}
		});

		pack();
		setVisible(true);
	}


/* main *********************************************************************/

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Paint paint = new Paint("paint");
			}
		});
	}
}