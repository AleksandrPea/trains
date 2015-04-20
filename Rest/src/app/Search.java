import ORMroad.Database;
import ORMroad.Route;
import ORMroad.Station;
import ORMroad.WayPoint;
import collection.RouteList;
import collection.StationList;
import collection.WayPointList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Admin on 25.03.2015.
 */
@Path("/search")
public class Search {
    @Produces({"application/json", "application/xml"})
    @Path("stations_by_name/{symbol}")
    @GET
    public StationList getStation(@PathParam("symbol") String symb) {
        List<Station> list = Database.getStation(symb);
        Iterator<Station> it = list.iterator();
        ArrayList<Station> result = new ArrayList<Station>();
        while(it.hasNext()) {
            Station st = it.next();
            st.setWaypoints(null);
            result.add(st);
        }
        StationList stl = new StationList();
        stl.setStations(result);
        return stl;
    }

    @Produces({"application/json", "application/xml"})
    @Path("station_by_id/{symbol}")
    @GET
    public Station getStationById(@PathParam("symbol") String symb) {
        Integer id = new Integer(symb);
        Station station = (Station) Database.get(Station.class, id);
        station.setWaypoints(null);
        return station;
    }

    @Produces({"application/json", "application/xml"})
    @Path("route_by_id/{symbol}")
    @GET
    public Route getRouteById(@PathParam("symbol") String symb) {
        Integer id = new Integer(symb);
        Route route = (Route) Database.get(Route.class, id);
        route.setWaypoints(null);
        route.splitTrainName();
        return route;
    }

    @Produces({"application/json", "application/xml"})
    @Path("routes_by_train/{symbol}")
    @GET
    public RouteList getRouteByTrain(@PathParam("symbol") String symb) {
        List<Route> routes = Database.getRouteByTrain(symb);
        Iterator<Route> it = routes.iterator();
        while (it.hasNext()) {
            Route route = it.next();
            route.splitTrainName();
            route.setWaypoints(null);
        }
        RouteList list = new RouteList();
        list.setRoutes(routes);
        return list;
    }

    @Produces({"application/json", "application/xml"})
    @Path("routes_by_name/{symbol}")
    @GET
    public RouteList getRouteByName(@PathParam("symbol") String symb) {
        List<Route> routes = Database.getRoute(symb);
        Iterator<Route> it = routes.iterator();
        while (it.hasNext()) {
            Route route = it.next();
            route.splitTrainName();
            route.setWaypoints(null);
        }
        RouteList list = new RouteList();
        list.setRoutes(routes);
        return list;
    }

    @Produces({"application/json", "application/xml"})
    @Path("waypoint_by_id/{symbol}")
    @GET
    public WayPoint getWayPointById(@PathParam("symbol") String symb) {
        Integer id = new Integer(symb);
        WayPoint point = (WayPoint) Database.get(WayPoint.class, id);
        point = Database.initializeWayPoint(point);
        Station station = point.getStation();
        station.setWaypoints(null);
        Route route = point.getRoute();
        route.setWaypoints(null);
        point.setStation(station);
        point.setRoute(route);
        return point;
    }

    @Produces({"application/json", "application/xml"})
    @Path("waypoints_by_station_id/{symbol}")
    @GET
    public WayPointList getWayPointByStationId(@PathParam("symbol") String symb) {
        Integer id = new Integer(symb);
        List<WayPoint> list = Database.getWayPointByStation(id);
        Iterator<WayPoint> it = list.iterator();
        ArrayList<WayPoint> result = new ArrayList<WayPoint>();
        while (it.hasNext()) {
            WayPoint point = it.next();
            point = Database.initializeWayPoint(point);
            Station station = point.getStation();
            station.setWaypoints(null);
            Route route = point.getRoute();
            route.setWaypoints(null);
            point.setStation(station);
            point.setRoute(route);
            result.add(point);
        }
        WayPointList re = new WayPointList();
        re.setWayPoints(result);
        return re;
    }

    @Produces({"application/json", "application/xml"})
    @Path("waypoints_by_route_id/{symbol}")
    @GET
    public WayPointList getWayPointByRouteId(@PathParam("symbol") String symb) {
        Integer id = new Integer(symb);
        List<WayPoint> list = Database.getWayPointByRoute(id);
        Iterator<WayPoint> it = list.iterator();
        ArrayList<WayPoint> result = new ArrayList<WayPoint>();
        while (it.hasNext()) {
            WayPoint point = it.next();
            point = Database.initializeWayPoint(point);
            Station station = point.getStation();
            station.setWaypoints(null);
            Route route = point.getRoute();
            route.setWaypoints(null);
            point.setStation(station);
            point.setRoute(route);
            result.add(point);
        }
        WayPointList re = new WayPointList();
        re.setWayPoints(result);
        return re;
    }

}
