package RailRoad;


public class Route {
	private int route_id;
	private String train;
	private String time;
	private String travelTime;
	private String name;
	public Route(String train, String time, String travelTime, String name) {
		setTime(time);
		setTrain(train);
		setTravelTime(travelTime);
		setName(name);
	}
	public void setTrain(String train) {
		this.train = train;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTrain() {
		return train;
	}
	public String getTime() {
		return time;
	}
	public void setTravelTime(String time) {
		this.travelTime = time;
	}
	public String getTravelTime() {
		return this.travelTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
}
