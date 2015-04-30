package users.mysql;

import javafx.util.Pair;
import users.dao.GenericDao;
import users.dao.PersistException;
import users.entities.UsersCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 26.04.2015.
 */
public class MySqlUsersCategoryDao implements GenericDao<UsersCategory, Pair> {
    private Connection connection;

    @Override
    public UsersCategory create() throws PersistException {
        UsersCategory g = new UsersCategory();
        return persist(g);
    }

    @Override
    public UsersCategory persist(UsersCategory obj) throws PersistException {
        String sql = getCreateQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            prepareStatementForInsert(stm, obj);
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: "
                        + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return obj;
    }

    @Override
    public UsersCategory getByPK(Pair key) throws PersistException {
        List<T> list;
        String sql = getSelectQuery() + " WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, key);
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
    public void update(UsersCategory obj) throws PersistException {

    }

    @Override
    public void delete(UsersCategory obj) throws PersistException {

    }

    @Override
    public List<UsersCategory> getAll() throws PersistException {
        return null;
    }

    public String getCreateQuery() {
        return "INSERT INTO timetable.Users_Category (user_id, category_id) \n" +
                "VALUES (?, ?);";
    }

    public String getSelectQuery() {
        return "SELECT user_email, category_id FROM timetable.Users_Category";
    }

    public String getUpdateQuery() {
        return getCreateQuery();
    }

    public String getDeleteQuery() {
        return "DELETE FROM timetable.Users_Category WHERE user_id = ?, category_id = ?;";
    }


    protected List<UsersCategory> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<UsersCategory> result = new LinkedList<>();
        try {
            while (rs.next()) {
                UsersCategory usersC = new UsersCategory();
                usersC.setuser_id(rs.getString("user_id"));
                usersC.setCategory_id(rs.getInt("category_id"));
                result.add(usersC);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }


    protected void prepareStatementForInsert(PreparedStatement stm, UsersCategory obj)
            throws PersistException {
        try {
            stm.setInt(1, obj.getUser_id());
            stm.setInt(2, obj.getCategory_id());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected void prepareStatementForUpdate(PreparedStatement stm, UsersCategory obj)
            throws PersistException {
        prepareStatementForInsert(stm, obj);
    }

    public MySqlUsersCategoryDao(Connection connection) {
        this.connection = connection;
    }
}