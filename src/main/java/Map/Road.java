package Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Road {
	private ArrayList<Point> road;
	public Road() {
		road = new ArrayList<Point>();
	}
	public Road(ArrayList<Point> road) {
		this.road = road;
	}
	public void addLine(Point p) {
		road.add(p);
	}
	public void paint(Graphics2D g2, Area area) {
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(2));
		if(road.size() > 1) {
			int i = 0;
			Point p1 = new Point(road.get(i));
			p1.x = area.getXOnPanel(p1.x);
			p1.y = area.getYOnPanel(p1.y);
			for(i = 1; i < road.size(); i = i + 1) {
				Point p2 = new Point(road.get(i));
				p2.x = area.getXOnPanel(p2.x);
				p2.y = area.getYOnPanel(p2.y);
				g2.draw(new Line2D.Double(p1, p2));
				p1 = p2;
			}
		}
	}
}
