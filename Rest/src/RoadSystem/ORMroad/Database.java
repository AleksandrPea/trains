package ORMroad;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class Database {
	public static Integer save(Object obj) {
		Session ses = HibernateUtil.getSessionFactory().openSession();
		ses.beginTransaction();
		Integer id = (Integer) ses.save(obj);
		ses.getTransaction().commit();
		ses.close();
		return id;
	}

	public static void saveDataBase(ArrayList<Station> stations, ArrayList<Route> routes,
			ArrayList<WayPoint> waypoints) {
		deleteDataBase();
		
		Iterator<Route> it1 = routes.iterator();
		while(it1.hasNext()) {
			Route route = it1.next();
			save(route);
		}
		
		Iterator<Station> it2 = stations.iterator();
		while(it2.hasNext()) {
			Station station = it2.next();
			save(station);
		}
		
		Iterator<WayPoint> it3 = waypoints.iterator();
		while(it3.hasNext()) {
			WayPoint way = it3.next();
			save(way);
		}
	}
	public static void deleteDataBase() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String hqlDelete = "delete WayPoint";
		session.createQuery(hqlDelete).executeUpdate();
		hqlDelete = "delete Route";
		session.createQuery(hqlDelete).executeUpdate();
		hqlDelete = "delete Station";
		session.createQuery(hqlDelete).executeUpdate();
		tx.commit();
		session.close();
	}
	public static Object get(Class clas , Integer id) {
        Session session;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Object result = session.get(clas, id);
		tx.commit();
		//session.close();
		return result;
	}
	public static Station initializeStation(Station entity) {
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  entity = (Station) session.merge(entity);
		  Set<WayPoint> ways = entity.getWaypoints();
		  Iterator<WayPoint> it = ways.iterator();
		  Set<WayPoint> newWays = new HashSet<WayPoint>();
		  while(it.hasNext()) {
			  WayPoint point = it.next();
			  Hibernate.initialize(point);
			  newWays.add(point);
		  }
		  session.close();
		  entity.setWaypoints(newWays);
 		  return entity;
	}
	
	public static Route initializeRoute(Route entity) {
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  entity = (Route) session.merge(entity);
		  Set<WayPoint> ways = entity.getWaypoints();
		  Iterator<WayPoint> it = ways.iterator();
		  Set<WayPoint> newWays = new HashSet<WayPoint>();
		  while(it.hasNext()) {
			  WayPoint point = it.next();
			  Hibernate.initialize(point);
			  newWays.add(point);
		  }
		  session.close();
		  entity.setWaypoints(newWays);
		  return entity;
	}
	
	public static WayPoint initializeWayPoint(WayPoint entity) {
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  entity = (WayPoint) session.merge(entity);
		  Station station = entity.getStation();
		  Hibernate.initialize(station);
		  station = (Station) session.merge(station);
		  //Hibernate.initialize(station);
		  //station.getName();
		  Route route = entity.getRoute();
		  Hibernate.initialize(route);
		  route = (Route) session.merge(route);
		  entity.setStation(station);
		  entity.setRoute(route);
		  session.close();
		  return entity;
	}
	
	public static List getStation(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		return session.createCriteria(Station.class).add(
				Restrictions.like("name", name + "%")).list();
		} finally {
            session.close();
        }
	}

    public static List<Station> getFilteredStation(String name, List<Station> filter) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria cr = session.createCriteria(Station.class).addOrder(Order.asc("name")).add(
                    Restrictions.like("name", name + "%"));
            for (Station station : filter) {
                cr.add(Restrictions.ne("name", station.getName()));
            }
            return cr.list();
        } finally {
            session.close();
        }
    }

    public static List<Station> getSortedStation(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createCriteria(Station.class).addOrder(Order.asc("name")).add(
                    Restrictions.like("name", name + "%")).list();
        } finally {
            session.close();
        }
    }

	public static List getRoute(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		return session.createCriteria(Route.class).add(
				Restrictions.like("name", name + "%")).list();
		} finally { session.close();}
	}
	
	public static List getRouteByTrain(String train) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		return session.createCriteria(Route.class).add(
				Restrictions.like("train", train)).list();
		} finally { session.close();}
	}

	public static List getWayPointByStation(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Station station = (Station) Database.get(Station.class, id);
			return session.createCriteria(WayPoint.class).add(
					Restrictions.like("station", station)).list();
		} finally { session.close();}
	}

	public static List getWayPointByRoute(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Route route = (Route) Database.get(Route.class, id);
			return session.createCriteria(WayPoint.class).add(
					Restrictions.like("route", route)).list();
		} finally { session.close();}
	}

}
