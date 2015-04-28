package users.mysql;

import users.dao.DaoFactory;
import users.dao.GenericDao;
import users.dao.PersistException;
import users.entities.Carrier;
import users.entities.Category;
import users.entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 26.04.2015.
 */
public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/timetable";
    private String user = "root";
    private String password = "1111";
    private Map<Class, DaoCreator> creators;

    public MySqlDaoFactory() {
        try {
            Class.forName(driver);
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
//        creators.put(User.class, new DaoCreator<Connection>() {
//            @Override
//            public GenericDao create(Connection connection) {
//                return new MySqlUserDao(connection);
//            }
//        });
    }

    @Override
    public Connection getContext() throws PersistException {
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
}
