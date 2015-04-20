package Test;

import ORMroad.Database;
import ORMroad.Station;

import java.util.Iterator;
import java.util.List;

public class DataBaseTest {
	public static void main(String[] args) {
		//WayPoint route = (WayPoint) Database.get(WayPoint.class, new Integer(12524));
		//System.out.println(route.getArrival());
		//route = Database.initializeWayPoint(route);
		//System.out.println(route.getRoute().getTravelTime());
		List list = Database.getStation("Чернігів");
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Station station = (Station) it.next();
			System.out.println(station.getName());
		}
		//list = Database.getRouteByTrain("1");
		//Iterator it2 = list.iterator();
		//while(it2.hasNext()) {
			//Route station = (Route) it2.next();
			//System.out.println(station.getName());
		//}
		//HibernateUtil.getSessionFactory().close();
 	}

}
