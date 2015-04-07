package collection;

import ORMroad.Station;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Admin on 30.03.2015.
 */
@XmlRootElement(name = "stations")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationList {
    @XmlElement(name = "station")
    private List<Station> stations = null;

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}