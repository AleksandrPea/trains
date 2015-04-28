package users.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 27.04.2015.
 */
public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Integer>
        implements GenericDao<T, PK> {

    private Connection connection;

    public AbstractJDBCDao (Connection connection) {
        this.connection = connection;
    }

    public abstract String getCreateQuery();
    public abstract String getSelectQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs)
            throws PersistException;
    protected abstract void prepareStatementForInsert(PreparedStatement stm, T obj)
            throws PersistException;
    protected abstract void prepareStatementForUpdate(PreparedStatement stm, T obj)
            throws PersistException;

    @Override
    public T persist(T obj) throws PersistException {
        if (obj.getId() != null) {
            throw new PersistException("Object is already persisted");
        }
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
