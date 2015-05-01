package users.db.mysql;

import users.db.dao.DaoFactory;
import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.entities.UsersCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Реалізація інтерфейсу {@link DaoFactory} для бази даних MySql.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/timetable";
    private String user = "root";
    private String password = "1111";
    private Map<Class, DaoCreator> creators;

    private MySqlDaoFactory() {
        try {
            Class.forName(driver);              // Регістрація драйвера
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoCreator>();
        creators.put(Category.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlCategoryDao(connection);
            }
        });
        creators.put(Carrier.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlCarrierDao((connection));
            }
        });
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlUserDao(connection);
            }
        });
        creators.put(UsersCategory.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySqlUsersCategoryDao(connection);
            }
        });
    }

    @Override
    public Connection createContext() throws PersistException {
        return createContext(url, user, password);
    }

    /**
     * Cтворює підключення до MySql бази даних за заданими параметрами.
     * @param url URL адреса бази даних
     * @param user логін користувача бази даних
     * @param password пароль користувача бази даних
     */
    public Connection createContext(String url, String user, String password)
            throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public static MySqlDaoFactory getInstance() {
        return SingletonHelper.instance;
    }

    private static class SingletonHelper {
        private static final MySqlDaoFactory instance = new MySqlDaoFactory();
    }
}