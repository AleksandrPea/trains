package Parser;

import java.util.ArrayList;

import ORMroad.Route;
import ORMroad.Station;
import ORMroad.WayPoint;

public class Parser {
	private ArrayList<Station> stations;
	private ArrayList<Route> routes;
	private ArrayList<WayPoint> wayPoints;
	public ArrayList<Station> getStations() {
		return stations;
	}
	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}
	public ArrayList<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
	public void parse(String name) {
		StationParser parser = new StationParser();
		try {
			parser.parse(name);
			parser.createObjects();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stations = parser.getStations_in_list();
		routes = parser.getRoutes_in_list();
		wayPoints = parser.getWayPoints_in_list();
	}
	public ArrayList<WayPoint> getWayPoints() {
		return wayPoints;
	}
	public void setWayPoints(ArrayList<WayPoint> wayPoints) {
		this.wayPoints = wayPoints;
	}
}
