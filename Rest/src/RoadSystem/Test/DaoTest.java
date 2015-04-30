package Test;

import users.dao.DaoFactory;
import users.dao.GenericDao;
import users.dao.PersistException;
import users.entities.Carrier;
import users.entities.User;
import users.mysql.MySqlDaoFactory;

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
