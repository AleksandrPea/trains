package dao;

import ORMroad.Database;
import ORMroad.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import users.db.dao.DaoFactory;
import users.db.dao.GenericDao;
import users.db.entities.Identified;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.entities.UsersCategory;
import users.db.mysql.MySqlDaoFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class MySqlDaoTest extends GenericDaoTest<Connection> {

    private Connection connection;

    private GenericDao dao;

    private static final DaoFactory<Connection> factory = MySqlDaoFactory.getInstance();

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Object[][]{
                {Carrier.class, createTestCarrier()},
                {Category.class, createTestCategory()},
                {User.class, createTestUser()}
        });
    }

    @Before
    public void setUp() throws PersistException, SQLException {
        connection = factory.createContext();
        connection.setAutoCommit(false);
        dao = factory.getDao(connection, daoClass);
    }

    @After
    public void tearDown() throws SQLException {
        context().rollback();
        context().close();
    }

    @Override
    public GenericDao dao() {
        return dao;
    }

    @Override
    public Connection context() {
        return connection;
    }

    public MySqlDaoTest(Class clazz, Identified<Serializable> notPersistedDto) {
        super(clazz, notPersistedDto);
    }

    public static Carrier createTestCarrier() {
        Carrier c = new Carrier();
        c.setTariff("12 per hour");
        c.setInfo("Some info");
        List<Station> list = new ArrayList<>();
        for (int i = 1; i<10; i++) {
            list.add((Station) Database.get(Station.class, i + 2));
        }
        c.setStations(list);
        return c;
    }

    public static Category createTestCategory() {
        Category c = new Category();
        c.setName("category");
        return c;
    }

    public static User createTestUser() {
        User u = new User();
        u.setEmail("a@mail.ru");
        u.setPassword("1111");
        u.setFirstName("Aleksandr");
        u.setLastName("Pea");
        u.setCreate_date(new Date());
        u.setAddress("Adress");
        return u;
    }

    public static UsersCategory createTestUsersCategory() {
        UsersCategory uc = new UsersCategory();
        uc.setUser_id(3);
        uc.setCategory_id(1);
        return uc;
    }
}
