package users.db.mysql;

import users.db.dao.AbstractJDBCDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.entities.UsersCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для управління персистентним станом об'єктів класу {@link User}
 * у базі даних MySql.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {

    /**
     * Клас, що робить метод {@code setId} класу {@link User} доступним
     * тільки для об'єктів відповідного Dao класу {@link MySqlUserDao}.
     */
    private class PersistUser extends User {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    private int counter = 0;

    public MySqlUserDao(Connection connection) {
        super(connection);
    }

    public User create() throws PersistException {
        User u = new User();
        u.setEmail("blank" + counter++ + "-" + hashCode());
        u.setPassword("1111");
        u.setCreate_date(new Date());
        u.setFirstName("None");
        u.setLastName("None");
        u.setAddress("None");
        return persist(u);
    }

    @Override
    public void delete(User obj) throws PersistException {
        MySqlUsersCategoryDao dao = (MySqlUsersCategoryDao) MySqlDaoFactory.getInstance()
                .getDao(getConnection(), UsersCategory.class);
        dao.deleteUserCategories(obj);
        super.delete(obj);
    }

    /** Повертає список користувачів, які відносяться до категорії {@code category}.*/
    public List<User> getByCategory(Category category) throws PersistException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id FROM timetable.users_category WHERE category_id = ?;";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setInt(1, category.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(getByPK(rs.getInt("user_id")));
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    /**
     * Повертає об'єкт класу {@code User}, який відповідає запису з поштовою адресою
     * {@code email} та паролем {@code password}. Якщо такого запису немає, повертає {@code null}.
     */
    public User getByCredentials(String email, String password) throws PersistException {
        List<User> list;
        String sql = getSelectQuery() + " WHERE email = ? AND password = ?;";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new PersistException("Receved more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO timetable.User (email, password, create_date, lastName," +
                "firstName, address, carrier_id) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, email, password, create_date, lastName, firstName, address," +
                "carrier_id FROM timetable.User";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE timetable.User \n" +
                "SET email = ?, password = ?, create_date = ?, lastName = ?, firstName = ?," +
                " address = ?, carrier_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM timetable.User WHERE id = ?;";
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreate_date(rs.getDate("create_date"));
                user.setLastName(rs.getString("lastName"));
                user.setFirstName(rs.getString("firstName"));
                user.setAddress(rs.getString("address"));
                int carrier_id = rs.getInt("carrier_id");
                if (carrier_id != 0) {
                    user.setCarrier_id(carrier_id);
                } else user.setCarrier_id(null);
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stm, User obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getEmail());
            stm.setString(2, obj.getPassword());
            stm.setDate(3, convert(obj.getCreate_date()));
            stm.setString(4, obj.getLastName());
            stm.setString(5, obj.getFirstName());
            stm.setString(6, obj.getAddress());
            if (obj.getCarrier_id() != null) {
                stm.setInt(7, obj.getCarrier_id());
            } else stm.setNull(7, java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stm, User obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getEmail());
            stm.setString(2, obj.getPassword());
            stm.setDate(3, convert(obj.getCreate_date()));
            stm.setString(4, obj.getLastName());
            stm.setString(5, obj.getFirstName());
            stm.setString(6, obj.getAddress());
            if (obj.getCarrier_id() != null) {
                stm.setInt(7, obj.getCarrier_id());
            } else stm.setNull(7, java.sql.Types.INTEGER);
            stm.setInt(8, obj.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    {
        // Очистка непотрібних записів, що були створені методом persist
        String sql = "DELETE FROM timetable.User WHERE email LIKE 'blank%';";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}