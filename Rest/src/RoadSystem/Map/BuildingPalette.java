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
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

public class BuildingPalette implements Palette {
	private Area area;
	private ArrayList<Point> temp_building = null;
	private Point first_point = null;
	private Point focus = null;
	private Point last_point = null;
	private BuildingListener l1 = null;
	private EscListener l2 = null;
	public BuildingPalette(Area area) {
		this.area = area;
	}
	
	public void paint(Graphics2D g2) {
		if(first_point != null) {
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			Point p0 = new Point(area.getXOnPanel(first_point.x),
					area.getYOnPanel(first_point.y));
			Point p1 = p0;
			g2.setColor(Color.WHITE);
			g2.fillRect(p0.x - 3, p0.y - 3, 6, 6);
			g2.setColor(Color.BLACK);
			g2.drawRect(p0.x - 3, p0.y - 3, 6, 6);
			Iterator<Point> it = temp_building.iterator();
			while(it.hasNext()) {
				Point p = it.next();
				Point p2 = new Point(area.getXOnPanel(p.x),
						area.getYOnPanel(p.y));
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
				p1 = p2;
			}
			if(focus != null && last_point != null) {
				g2.drawLine(area.getXOnPanel(last_point.x), area.getYOnPanel(last_point.y), focus.x, focus.y);
			}
		}
	}
	
	public void setPaintMode() {
		if(l1 == null) {
			l1 = new BuildingListener();
			l2 = new EscListener();
			area.addMouseListener(l1);
			area.addMouseMotionListener(l1);
			area.addKeyListener(l2);
			System.out.println(true);
		}
		else {
			area.removeMouseListener(l1);
			area.removeKeyListener(l2);
			area.removeMouseMotionListener(l1);
			first_point = null;
			temp_building = null;
			focus = null;
			last_point = null;
			l1 = null;
			l2 = null;
			area.repaint();
			System.out.println(false);
		}
	}
	
	public JButton getCotrolButton() {
		JButton b = new JButton("Add building");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BuildingPalette.this.setPaintMode(); 
				
			}
			
		});
		return b;
	}
	
	private class BuildingListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent event) {
			if(area.isPointBelongMap(event.getPoint())) {
				if(first_point == null) {
					first_point = new Point(area.getXCoordinates(event.getX()), 
							area.getYCoordinates(event.getY()));
					temp_building = new ArrayList<Point>();
					last_point = first_point;
				}
				else {
					int first_x = area.getXOnPanel(first_point.x);
					int first_y = area.getYOnPanel(first_point.y);
					if(event.getX() > first_x - 3 && event.getX() < first_x + 3 &&
						event.getY() > first_y - 3 && event.getY() < first_y + 3) {
							temp_building.add(first_point);
							int[] x = new int[temp_building.size()];
							int[] y = new int[temp_building.size()];
							for(int i = 0; i < x.length; i++) {
								x[i] = temp_building.get(i).x;
								y[i] = temp_building.get(i).y;
							}
							area.addBuilding(new Building(x, y));
							first_point = null;
							temp_building = null;
							focus = null;
							last_point = null;
						}
					else {
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
						temp_building.add(new Point(x, y));
						//System.out.println("k");
						last_point = new Point(x, y);
					}
				}
			}
			if(first_point != null) area.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent event) {
			if(event.isShiftDown() && last_point != null) {
				int x = event.getX() - area.getXOnPanel(last_point.x);
				int y = -(event.getY() - area.getYOnPanel(last_point.y));
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
					int x0 = x + area.getXOnPanel(last_point.x);
					int y0 = -y + area.getYOnPanel(last_point.y);
					focus = new Point(x0, y0);
				}
			}
			else focus = event.getPoint();
			area.repaint();
		}
	}
	
	private class EscListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				first_point = null;
				temp_building = null;
				focus = null;
				last_point = null;
				area.repaint();
			}
		}
	}
	
}
