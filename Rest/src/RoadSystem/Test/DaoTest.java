package Test;

import ORMroad.Database;
import ORMroad.HibernateUtil;
import ORMroad.Station;
import org.hibernate.SessionFactory;
import users.dao.AbstractJDBCDao;
import users.dao.DaoFactory;
import users.dao.GenericDao;
import users.dao.PersistException;
import users.entities.Carrier;
import users.entities.User;
import users.entities.UsersCategory;
import users.mysql.MySqlDaoFactory;
import users.mysql.util.Pair;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 28.04.2015.
 */
public class DaoTest {

    public static void main(String[] args) {
//        Carrier c = new Carrier();
//        c.setTariff("12");
//        c.setInfo("Some info");
//        User u = new User();
//        u.setEmail("a@mail.ru");
//        u.setPassword("1111");
//        u.setFirstName("Aleksandr");
//        u.setLastName("Pea");
//        u.setCreate_date(new Date(System.currentTimeMillis()));
//        u.setAddress("Adress");
        DaoFactory factory = new MySqlDaoFactory();
        GenericDao carDao = null;
        GenericDao userDao = null;
//        try {
//            carDao = factory.getDao(factory.getContext(), Carrier.class);
//            userDao = factory.getDao(factory.getContext(), User.class);
//        } catch (PersistException e) {
//            e.printStackTrace();
//        }
////        List<Station> list = new ArrayList<>();
////        for (int i = 1; i<10; i++) {
////            list.add((Station) Database.get(Station.class, i+2));
////        }
////        c.setStations(list);
//        try {
//            Carrier c = (Carrier) carDao.getByPK(3);
//
//        } catch (PersistException e) {
//            e.printStackTrace();
//        }
    }

    private void load() {

    }
}
