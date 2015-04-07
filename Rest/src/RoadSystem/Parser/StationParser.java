package Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ORMroad.Route;
import ORMroad.Station;
import ORMroad.WayPoint;

public class StationParser {
	private String[] stationName;
	private ArrayList<Station> stations;
	private ArrayList<String> hrefs;
	private String routeName;
	private ArrayList<Route> routes;
	private ArrayList<TStation> tst;
	private ArrayList<WayPoint> wayPoints;
	public ArrayList<WayPoint> getWayPoints_in_list() {
		return wayPoints;
	}
	public ArrayList<Station> getStations_in_list() {
		return stations;
	}
	public ArrayList<Route> getRoutes_in_list() {
		return routes;
	}
	public StationParser() {
		hrefs = new ArrayList<String>();
		stationName = new String[2];
		stationName[0] = "http://uz.gov.ua/passengers/timetables/?station=";
		stationName[1] = "&by_station=Пошук";
		stations = new ArrayList<Station>();
		routeName = "http://uz.gov.ua/passengers/timetables/";
		routes = new ArrayList<Route>();
		wayPoints = new ArrayList<WayPoint>();
	}
	public void parse(String name) throws Exception {
		String spat1 = "<table>.*</tbody></table>";
		Pattern pat1 = Pattern.compile(spat1, Pattern.DOTALL);
		String spat2 = "<tbody>.*</tbody>";
		Pattern pat2 = Pattern.compile(spat2, Pattern.DOTALL);
		String spat3 = "<tr>.*?</tr>";
		Pattern pat3 = Pattern.compile(spat3, Pattern.DOTALL);
		String spat4 = "<td>.*?</td>";
		Pattern pat4 = Pattern.compile(spat4, Pattern.DOTALL);
		String spat5 = "\"[^.]+?\"";
		Pattern pat5 = Pattern.compile(spat5, Pattern.DOTALL);
		
		tst = InternetConector.viewStation(name);
		//System.out.println("tst=" + tst.size());
		Iterator<TStation> it = tst.iterator();
		//int n = 0;
		while(it.hasNext()) {
			//System.out.println(++n);
			TStation tstation = it.next();
			//Station station = new Station();
			//station.setName(tstation.getName());
			//stations.add(station);
			
			String s = InternetConector.conection(this.getStationURL(tstation.getId()));
			Matcher mat = pat1.matcher(s);
			//System.out.println(s);
			if(mat.find()) {
				int start = mat.start();
				int end = mat.end();
				String tab2 = s.substring(start, end);
				//System.out.println(tab2);
				mat = pat2.matcher(tab2);
				mat.find();
				start = mat.start();
				end = mat.end();
				tab2 = tab2.substring(start, end);
				//System.out.println(tab2);
				mat = pat3.matcher(tab2);
				//mat.find();
				while(mat.find()) {
					String tab3 = tab2.substring(mat.start(), mat.end());
					//System.out.println(tab3);
					Matcher mat4 = pat4.matcher(tab3);
					mat4.find();
					Matcher mat5 = pat5.matcher(tab3);
					mat5.find();
					String href = tab3.substring(mat5.start() + 1, mat5.end() - 1);
					
					boolean isConteinsH = false;
					Iterator<String> iter = hrefs.iterator();
					while(iter.hasNext() && !isConteinsH) {
						String temp = iter.next();
						isConteinsH = temp.equals(href);
					}
					if(!isConteinsH) hrefs.add(href);
					
					//System.out.println(href);
					//Route route = new Route();
					
				}
			}
		}
		//System.out.println("hrefs=" + hrefs.size());
		//Iterator<String> ite = hrefs.iterator();
		//while(ite.hasNext()) {
			//System.out.println(ite.next());
		//}
	}
	public void createObjects() throws Exception {
		String spat1 = "<table>.*</tbody></table>";
		Pattern pat1 = Pattern.compile(spat1, Pattern.DOTALL);
		String spat2 = "<tbody>.*</tbody>";
		Pattern pat2 = Pattern.compile(spat2, Pattern.DOTALL);
		String spat3 = "<tr>[^.]+?</tr>";
		Pattern pat3 = Pattern.compile(spat3, Pattern.DOTALL);
		String spat4 = "<td>.*?</td>";
		Pattern pat4 = Pattern.compile(spat4, Pattern.DOTALL);
		String spat5 = "\"[^.]+?\"";
		Pattern pat5 = Pattern.compile(spat5, Pattern.DOTALL);
		String spat6 = "<tbody>[^.]+?</tbody>";
		Pattern pat6 = Pattern.compile(spat6, Pattern.DOTALL);
		String spat7 = ";.*&";
		Pattern pat7 = Pattern.compile(spat7);
		
		Iterator<String> it = hrefs.iterator();
		//int n = 0;
		while(it.hasNext()) {
			//System.out.println(++n);
			String s = InternetConector.conection(
					routeName + it.next());
		
			//String s = InternetConector.conection(
				//	routeName + "?ntrain=52519&by_id=1");
			
			
			//System.out.println(s);
			Matcher mat1 = pat1.matcher(s);
			mat1.find();
			//mat7.find();
			String tab = s.substring(mat1.start(), mat1.end());
			//System.out.println(tab);
			Matcher mat2 = pat2.matcher(tab);
			mat2.find();
			tab = tab.substring(mat2.start(), mat2.end());
			//System.out.println(tab);
			Matcher mat4 = pat4.matcher(tab);
			mat4.find();
			String name = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
			//System.out.println(name);
			mat4.find();
			String train = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
			//System.out.println(train);
			mat4.find();
			String time = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
			//System.out.println(time);
			mat4.find();
			String travelTime = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
			//System.out.println(travelTime);
			Route route = new Route(train, time, travelTime, name);
			routes.add(route);
			
			while(mat4.find()) {
				String st_name = tab.substring(mat4.start(), mat4.end());
				Matcher mat7 = pat7.matcher(st_name);
				mat7.find();
				st_name = st_name.substring(mat7.start() + 1, mat7.end() - 1).trim();
				//System.out.println(st_name);
				mat4.find();
				String arrival = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
				//System.out.println(arrival);
				mat4.find();
				String despatch = tab.substring(mat4.start() + 4, mat4.end() - 5).trim();
				//System.out.println(despatch);
				
				Iterator<Station> it_st = stations.iterator();
				Station add = null;
				while(add == null && it_st.hasNext()) {
					Station st_temp = it_st.next();
					String name_temp = st_temp.getName();
					if(name_temp.equals(st_name)) add = st_temp;
				}
				if(add == null) {
					add = new Station(st_name);
					stations.add(add);
				}
				WayPoint way = new WayPoint(route, add, arrival, despatch);
				//route.addWayPoint(way);
				way.setRoute(route);
				way.setStation(add);
				wayPoints.add(way);
				//add.addRoute(route);
			}
			//String temp = tab.substring(mat4.start(), mat4.end());
			//System.out.println(temp);
			
		}
			//System.out.println("st=" + stations.size());
			//System.out.println("route=" + routes.size());
			//Iterator<Route> itera = routes.iterator();
			//while(itera.hasNext()) {
				//Route r = itera.next();
				//System.out.println(r.getName() + "  " + r.getTrain());
			//}
		
	}
	private String getStationURL(String id) throws MalformedURLException {
		return stationName[0] + id + stationName[1];
	}
	public static void main(String[] args) throws Exception {
		StationParser parser = new StationParser();
		//parser.parse("Варшава");
		//parser.createObjects();
		//parser.less();
		//parser.getHrefs();
		//parser.getStations();
		QueryString q = new QueryString()
		.add("station_id_from","2200001")
		.add("station_id_till", "2200600")
		.add("station_from","Київ")
		.add("station_till","Чернігів")
		.add("date_dep", "12.03.2015")
		.add("time_dep", "00:00")
		.add("time_dep_till", "")
		.add("another_ec", "0")
		.add("search", "");
		
		parser.postExample("http://booking.uz.gov.ua/", q);

		
	}
	private void less() {
		Iterator<Station> it = stations.iterator();
		while(it.hasNext()) {
			Station st = it.next();
			String name = st.getName();
			Iterator<TStation> it2 = tst.iterator();
			TStation temp = null;
			while(temp == null && it2.hasNext()) {
				TStation t2 = it2.next();
				if(t2.getName().equals(name)) temp = t2;
			}
			if(temp != null) {
				tst.remove(temp);
			}
		}
		System.out.println();
		System.out.println("less=" + tst.size());
		Iterator<TStation> t3 = tst.iterator();
		while(t3.hasNext()) {
			TStation tr = t3.next();
			System.out.println(tr.getName() + " . " + tr.getId());
		
		}
	}
	private void getHrefs() {
		Iterator<String> it = hrefs.iterator();
		while(it.hasNext()) System.out.println(it.next());
	}
	private void getStations() {
		System.out.println();
		Iterator<Station> it = stations.iterator();
		while(it.hasNext()) System.out.println(it.next().getName());
	}
	private ArrayList<int[]> parseData(String text) {
		return null;
	}
	public void postExample(String url, QueryString query) throws IOException {
		URLConnection conn = new URL(url).openConnection();
		conn.setDoOutput(true);
		//conn.setRequestProperty("Request URI", "purchase/search/");
		
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
		//out.write("Request URI: /purchase/search/");
		out.write(query.toString());
		out.write("\r\n");
		out.flush();
		out.close();
		
		String html = readStreamToString(conn.getInputStream(), "UTF-8");
		
		//РІС‹РІРѕРґРёРј РёРЅС„РѕСЂРјР°С†РёСЋ РІ РєРѕРЅСЃРѕР»СЊ
		System.out.println("URL:" + url);
		System.out.println("Html:\n" + html);
	}
	private String readStreamToString(InputStream in, String encoding)
	        throws IOException {
	    StringBuffer b = new StringBuffer();
	    InputStreamReader r = new InputStreamReader(in, encoding);
	    int c;
	    while ((c = r.read()) != -1) {
	        b.append((char)c);
	    }
	    return b.toString();
	}

}


