package parser;

public class TStation {
	private String name;
	private String id;
	private String country;
	public TStation (String name, String id, String country) {
		this.name = name;
		this.id = id;
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
