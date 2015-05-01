package Test;

import users.db.dao.DaoFactory;
import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.User;
import users.db.mysql.MySqlDaoFactory;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 28.04.2015.
 */
public class DaoTest {

    public static void main(String[] args) throws PersistException {
        DaoFactory factory = new MySqlDaoFactory();
        GenericDao carDao = null;
        GenericDao userDao = null;

        carDao = factory.getDao(factory.getContext(), Carrier.class);
        userDao = factory.getDao(factory.getContext(), User.class);


    }

}
