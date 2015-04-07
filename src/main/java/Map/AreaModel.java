package Map;

import java.awt.Point;
import java.util.ArrayList;

public class AreaModel {
	private Point left_max_point = new Point(0, 1);
	private int x_km = 1;
	private int y_km = 1;
	private ArrayList<Building> buildings = new ArrayList<Building>();
	public Point getLeft_max_point() {
		return left_max_point;
	}
	public void setLeft_max_point(Point left_max_point) {
		this.left_max_point = left_max_point;
	}
	public int getX_km() {
		return x_km;
	}
	public void setX_km(int x_km) {
		this.x_km = x_km;
	}
	public int getY_km() {
		return y_km;
	}
	public void setY_km(int y_km) {
		this.y_km = y_km;
	}
	public ArrayList<Building> getBuildings() {
		return buildings;
	}
	public void setBuildings(ArrayList<Building> buildings) {
		this.buildings = buildings;
	}
	
}
