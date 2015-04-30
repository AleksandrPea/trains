package users.mysql;

import users.dao.AbstractJDBCDao;
import users.dao.PersistException;
import users.entities.Category;

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
public class MySqlCategoryDao extends AbstractJDBCDao<Category, Integer> {

    private class PersistCategory extends Category {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public Category create() throws PersistException {
        Category g = new Category();
        return persist(g);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO timetable.Category (name) \n" +
                "VALUES (?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name FROM timetable.Category";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE timetable.Category \n" +
                "SET name = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM timetable.Category WHERE id = ?;";
    }

    @Override
    protected List<Category> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Category> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistCategory category = new PersistCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                result.add(category);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stm, Category obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stm, Category obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getName());
            stm.setInt(2, obj.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public MySqlCategoryDao(Connection connection) {
        super(connection);
    }
}