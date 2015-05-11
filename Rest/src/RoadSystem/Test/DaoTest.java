package Test;

import ORMroad.Database;
import ORMroad.Station;
import users.db.dao.PersistException;

import java.util.List;


public class DaoTest {

    public static void main(String[] args) throws PersistException {
        List<Station> stations = (List<Station>) Database.getStation("Антонівка");
        System.out.println(stations.size());
    }

}
