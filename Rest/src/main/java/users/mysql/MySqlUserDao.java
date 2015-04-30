package users.mysql;

import users.dao.AbstractJDBCDao;
import users.dao.PersistException;
import users.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 26.04.2015.
 */
public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {

    private class PersistUser extends User {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public User create() throws PersistException {
        User u = new User();
        u.setEmail("blank");
        u.setPassword("1111");
        u.setCreate_date(new Date(System.currentTimeMillis()));
        u.setFirstName("None");
        u.setLastName("None");
        u.setAddress("None");
        return persist(u);
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
                "address = ? carrier_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM timetable.User WHERE id = ?;";
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
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
                user.setCarrier_id(rs.getInt("carrier_id"));
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

    public MySqlUserDao(Connection connection) {
        super(connection);
    }
}
