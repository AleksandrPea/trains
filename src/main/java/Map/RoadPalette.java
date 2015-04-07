package Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JButton;

public class RoadPalette implements Palette {
	private Area area;
	private ArrayList<Point> temp_road;
	private Point focus = null;
	private RoadListener l1 = null;
	private EscEnterListener l2 = null;
	public RoadPalette(Area area) {
		this.area = area;
		temp_road = new ArrayList<Point>();
	}
	
	public void paint(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(2));
		if(temp_road.size() > 0) {
			System.out.println("p");
			int i = 0;
			Point p1 = new Point(temp_road.get(i));
			p1.x = area.getXOnPanel(p1.x);
			p1.y = area.getYOnPanel(p1.y);
			for(i = 1; i < temp_road.size(); i = i + 1) {
				Point p2 = new Point(temp_road.get(i));
				p2.x = area.getXOnPanel(p2.x);
				p2.y = area.getYOnPanel(p2.y);
				g2.draw(new Line2D.Double(p1, p2));
				p1 = p2;
			}
			if(focus != null) {
				System.out.println("k");
				g2.drawLine(p1.x, p1.y, focus.x, focus.y);
			}
		}
	}
	
	public void setPaintMode() {
		if(l1 == null) {
			l1 = new RoadListener();
			l2 = new EscEnterListener();
			area.addMouseListener(l1);
			area.addMouseMotionListener(l1);
			area.addKeyListener(l2);
			System.out.println(true);
		}
		else {
			area.removeMouseListener(l1);
			area.removeKeyListener(l2);
			area.removeMouseMotionListener(l1);
			temp_road = new ArrayList<Point>();
			focus = null;
			l1 = null;
			l2 = null;
			area.repaint();
			System.out.println(false);
		}
	}
	
	public JButton getCotrolButton() {
		JButton b = new JButton("Add line");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RoadPalette.this.setPaintMode(); 
			}
		});
		return b;
	}
	
	private class RoadListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent event) {
			if(area.isPointBelongMap(event.getPoint())) {
				int x;
				int y;
				if(event.isShiftDown()) {
					x = area.getXCoordinates(focus.x);
					y = area.getYCoordinates(focus.y);
				}
				else {
					x = area.getXCoordinates(event.getX());
					y = area.getYCoordinates(event.getY());
				}
				temp_road.add(new Point(x, y));
			}
			area.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent event) {
			if(event.isShiftDown() && temp_road.size() > 0) {
				int x = event.getX() - area.getXOnPanel(temp_road.
						get(temp_road.size() - 1).x);
				int y = -(event.getY() - area.getYOnPanel(temp_road.
						get(temp_road.size() - 1).y));
				if(x == 0 || y == 0) {
					focus = event.getPoint();
				}
				else {
					double tan = (double) y/x;
					boolean p = true;
					if(p && tan > 0.404 && tan < 2.355) {
						p = false;
						x = (x + y)/2;
						y = x;
					}
					if(p && tan > -4.705 && tan < -0.404) {
						p = false;
						x = (x - y)/2;
						y = -x;
					}
					if(p && (tan > 2.355 || tan < -4.705)) {
						x = 0;
					}
					if(p && tan < 2.355 && tan > -4.705) {
						y = 0;
					}
					int x0 = x + area.getXOnPanel(temp_road.
							get(temp_road.size() - 1).x);
					int y0 = -y + area.getYOnPanel(temp_road.
							get(temp_road.size() - 1).y);
					focus = new Point(x0, y0);
				}
			}
			else focus = event.getPoint();
			area.repaint();
		}
	}
	
	private class EscEnterListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				temp_road = new ArrayList<Point>();
				focus = null;
				area.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				Road road = new Road(temp_road);
				area.addRoad(road);
				temp_road = new ArrayList<Point>();
				focus = null;
				area.repaint();
			}
		}
	
	}

}
