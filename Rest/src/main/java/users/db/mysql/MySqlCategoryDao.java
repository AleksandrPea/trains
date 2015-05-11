package users.db.mysql;

import users.db.dao.AbstractJDBCDao;
import users.db.dao.PersistException;
import users.db.entities.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для управління персистентним станом об'єктів класу {@link Category}
 * у базі даних MySql.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class MySqlCategoryDao extends AbstractJDBCDao<Category, Integer> {

    /**
     * Клас, що робить метод {@code setId} класу {@link Category} доступним
     * тільки для об'єктів відповідного Dao класу {@link  MySqlCategoryDao}.
     */
    private class PersistCategory extends Category {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public MySqlCategoryDao(Connection connection) {
        super(connection);
    }

    public Category create() throws PersistException {
        Category g = new Category();
        g.setName("Unknown");
        return persist(g);
    }

    /**
     * Повертає категорію, яка відповідає першому входженню
     * імені {@code name} в таблиці Category, або {@code null}.
     */
    public Category getFirstOf(String name) throws PersistException {
        Category category = null;
        String sql = "SELECT id FROM timetable.Category WHERE name = ? LIMIT 1;";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                category = getByPK(rs.getInt("id"));
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return category;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO timetable.Category (name, parent_id) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, name, parent_id FROM timetable.Category";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE timetable.Category \n" +
                "SET name = ?, parent_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM timetable.Category WHERE id = ?;";
    }

    @Override
    protected List<Category> parseResultSet(ResultSet rs) throws PersistException {
        ArrayList<Category> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistCategory category = new PersistCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                int parent_id = rs.getInt("parent_id");
                if (parent_id != 0) {
                    category.setParent_id(parent_id);
                } else category.setParent_id(null);
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
            if (obj.getParent_id() != null) {
                stm.setInt(2, obj.getParent_id());
            } else stm.setNull(2, java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stm, Category obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getName());
            if (obj.getParent_id() != null) {
                stm.setInt(2, obj.getParent_id());
            } else stm.setNull(2, java.sql.Types.INTEGER);
            stm.setInt(3, obj.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}