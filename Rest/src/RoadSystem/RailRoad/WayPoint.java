package RailRoad;


public class WayPoint {
	private Station station;
	private String arrival;
	private String despatch;
	private Route route;
	public WayPoint() {}
	public WayPoint(Station station, String arrival, String despatch) {
		setStation(station);
		setArrival(arrival);
		setDespatch(despatch);
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public void setDespatch(String despatch) {
		this.despatch = despatch;
	}
	public Station getStation() {
		return station;
	}
	public String getArrival() {
		return arrival;
	}
	public String getDespatch() {
		return despatch;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
}