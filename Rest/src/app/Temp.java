import ORMroad.WayPoint;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Admin on 25.03.2015.
 */
@XmlRootElement
public class Temp {
    private String name;

    public Temp(){}

    public Temp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        //WayPoint point = (WayPoint) Database.get(WayPoint.class, new Integer(1000));
        //point = Database.initializeWayPoint(point);
        //System.out.println(point.getArrival());
        Search sh = new Search();
        WayPoint point = sh.getWayPointById("10000");
        //Station st2 = point.getStation();

        //List list = Database.getStation("Чернігів");
        //Iterator<Station> it = list.iterator();
        //Station st = it.next();
        //st.setWaypoints(null);
    }
}
