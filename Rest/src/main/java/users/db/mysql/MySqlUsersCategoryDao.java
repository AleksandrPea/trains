package users.db.mysql;

import users.db.dao.AbstractJDBCDao;
import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Category;
import users.db.entities.UsersCategory;
import users.util.Pair;
import users.db.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Клас для управління зв'язком <i>many to many</i> між {@link User}
 * та {@link Category} у базі даних MySql.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class MySqlUsersCategoryDao implements GenericDao<UsersCategory, Pair<Integer, Integer>> {

    private Connection connection;

    public MySqlUsersCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UsersCategory create() throws PersistException {
        UsersCategory g = new UsersCategory();
        return g;
    }

    @Override
    public UsersCategory persist(UsersCategory obj) throws PersistException {
        String sql = getCreateQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, obj.getUser_id());
            stm.setInt(2, obj.getCategory_id());
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
    public UsersCategory getByPK(Pair pair) throws PersistException {
        List<UsersCategory> list;
        String sql = getSelectQuery() + " WHERE user_id = ? AND category_id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, (Integer) pair.getObject("user_id"));
            stm.setInt(2, (Integer) pair.getObject("category_id"));
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
        persist(obj);
    }

    @Override
    public void delete(UsersCategory obj) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try {
                stm.setInt(1, obj.getUser_id());
                stm.setInt(2, obj.getCategory_id());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: "
                        + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<UsersCategory> getAll() throws PersistException {
        List<UsersCategory> list;
        String sql = getSelectQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    /**
     * Дивись <b>see also</b>.
     * @see AbstractJDBCDao#getCreateQuery()
     */
    public String getCreateQuery() {
        return "INSERT INTO timetable.Users_Category (user_id, category_id) \n" +
                "VALUES (?, ?);";
    }

    /**
     * Дивись <b>see also</b>.
     * @see AbstractJDBCDao#getSelectQuery()
     */
    public String getSelectQuery() {
        return "SELECT user_id, category_id FROM timetable.Users_Category ";
    }

    /**
     * Дивись <b>see also</b>.
     * @see AbstractJDBCDao#getDeleteQuery()
     */
    public String getDeleteQuery() {
        return "DELETE FROM timetable.Users_Category WHERE user_id = ?, category_id = ?;";
    }

    /**
     * Дивись <b>see also</b>.
     * @see AbstractJDBCDao#parseResultSet(ResultSet)
     */
    protected List<UsersCategory> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<UsersCategory> result = new LinkedList<>();
        try {
            while (rs.next()) {
                UsersCategory usersC = new UsersCategory();
                usersC.setUser_id(rs.getInt("user_id"));
                usersC.setCategory_id(rs.getInt("category_id"));
                result.add(usersC);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
}