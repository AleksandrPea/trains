package Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class BuildingView extends MouseAdapter {
	private Area area;
	private Building focusBuilding;
	public BuildingView(Area area) {
		this.area = area;
	}
	public void paint(Graphics2D g2) {
		ArrayList<Building> buildings = area.getBuildings();
		if(buildings.size() > 0) {
			g2.setColor(Color.YELLOW);
			Iterator<Building> it = buildings.iterator();
			while(it.hasNext()) {
				Building b = it.next();
				Polygon p = area.transformBuilding(b);
				g2.fill(p);
			}
			if(focusBuilding != null) {
				Polygon p = area.transformBuilding(focusBuilding);
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(1));
				g2.draw(p);
			}
		}
	}
	public Building getFocusBuilding() {
		return this.focusBuilding;
	}
	public void setFocusBuilding(Building b) {
		this.focusBuilding = b;
	}
	@Override
	public void mousePressed(MouseEvent event) {
		if(area.isPointBelongMap(event.getPoint())) {
			ArrayList<Building> buildings = area.getBuildings();
			if(buildings.size() > 0) {
				Point p2 = new Point(area.getXCoordinates(event.getX()),
						area.getYCoordinates(event.getY()));
				Iterator<Building> it = buildings.iterator();
				boolean consist = true;
				while(it.hasNext() && consist) {
					Building b = it.next();
					Polygon p = new Polygon(b.x, b.y, b.x.length);
					if(p.contains(p2)) {
						consist = false;
						focusBuilding = b;
					}
				}
				if(consist) focusBuilding = null;
				area.repaint();
			}
		}
	}
	public void buildingsChange() {
		focusBuilding = null;
	}
}
