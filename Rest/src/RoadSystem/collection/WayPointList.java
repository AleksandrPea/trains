package collection;

import ORMroad.WayPoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Admin on 31.03.2015.
 */
@XmlRootElement(name = "wayPoints")
@XmlAccessorType(XmlAccessType.FIELD)
public class WayPointList {
    @XmlElement(name = "wayPoint")
    private List<WayPoint> wayPoints = null;

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }
}
