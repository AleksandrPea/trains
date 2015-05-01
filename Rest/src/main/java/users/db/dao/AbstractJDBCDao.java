package users.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Абстрактний клас, який надає базову реалізацію CRUD операцій з використанням JDBC.
 *
 * @param <T> тип об'єкту персистенції
 * @param <PK> тип первинного ключа
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Integer>
        implements GenericDao<T, PK> {

    private Connection connection;

    public AbstractJDBCDao (Connection connection) {
        this.connection = connection;
    }

    /**
     * Повертає sql запит для вставки нового запису в базу даних.
     * <p>INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);</p>
     */
    public abstract String getCreateQuery();

    /**
     * Повертає sql запит для отримання всіх записів із бази даних.
     * <p>SELECT * FROM [Table]</p>
     */
    public abstract String getSelectQuery();

    /**
     * Повертає sql запит для оновлення запису.
     * <p>UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;</p>
     */
    public abstract String getUpdateQuery();

    /**
     * Повертає sql запит для видалення запису з бази даних.
     * <p>DELETE FROM [Table] WHERE id= ?;</p>
     */
    public abstract String getDeleteQuery();

    /**
     * Розбирає {@code ResultSet} і повертає список об'єктів,
     * які відповідають вмісту {@code ResultSet</code>.
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;

    /**
     * Встановлює аргументи insert запиту у відповідності зі значеннями
     * полів об'єкту {@code obj}.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement stm, T obj)
            throws PersistException;

    /**
     * Встановлює аргументи update запиту у відповідності зі значеннями
     * полів об'єкту {@code obj}.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement stm, T obj)
            throws PersistException;

    @Override
    public T persist(T obj) throws PersistException {
        if (obj.getId() != null) {
            throw new PersistException("Object is already persisted");
        }
        // Додаємо запис
        T persistInstance;
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
        // Отримуємо тільки-но вставлений запис
        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return persistInstance;
    }

    @Override
    public T getByPK(Integer key) throws PersistException {
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
    public void update(T obj) throws PersistException {
        String sql = getUpdateQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(stm, obj);
            int count = stm.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: "
                        + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(T obj) throws PersistException {
        String sql = getDeleteQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try {
                stm.setObject(1, obj.getId());
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
    public List<T> getAll() throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    protected Connection getConnection() {
        return connection;
    }
}
