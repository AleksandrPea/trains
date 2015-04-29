package Test;

import ORMroad.Database;
import ORMroad.Station;
import users.dao.DaoFactory;
import users.dao.GenericDao;
import users.dao.PersistException;
import users.entities.Carrier;
import users.mysql.MySqlDaoFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 28.04.2015.
 */
public class DaoTest {

    public static void main(String[] args) {
        Carrier c = new Carrier();
        c.setTariff("12");
        c.setInfo("Some info");
        DaoFactory factory = new MySqlDaoFactory();
        GenericDao dao = null;
        try {
            dao = factory.getDao(factory.getContext(), Carrier.class);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        List<Station> list = new ArrayList<>();
        for (int i = 1; i<10; i++) {
            list.add((Station) Database.get(Station.class, i));
        }
        c.setStations(list);
        try {
            dao.persist(c);
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    private void load() {

    }
}
