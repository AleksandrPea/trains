package Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.proxy.HibernateProxy;

import ORMroad.Database;
import ORMroad.HibernateUtil;
import ORMroad.Route;
import ORMroad.WayPoint;
import Parser.Parser;
import ORMroad.Station;

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
