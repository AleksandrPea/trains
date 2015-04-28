package users.mysql;

import ORMroad.Station;
import users.dao.AbstractJDBCDao;
import users.dao.PersistException;
import users.entities.Carrier;

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
public class MySqlCarrierDao extends AbstractJDBCDao<Carrier, Integer> {

    private class PersistCarrier extends Carrier {
        public void setId(int id) {
            super.setId(id);
        }
    }

    public Carrier create() throws PersistException {
        Carrier c = new Carrier();
        return persist(c);
    }

    @Override
    public Carrier persist(Carrier obj) throws PersistException {
        System.out.println(obj.getStations());
        Carrier persistedObj = super.persist(obj);
        persistedObj.setStations(obj.getStations());
        String sql = "INSERT INTO timetable.Station_list (Carrier_id, Station_id) \n" +
                "VALUES ('" + persistedObj.getId() + "', ?);";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            List<Station> stations = persistedObj.getStations();
            for (Station st: stations) {
                stm.setInt(1, st.getStationId());
                stm.execute();
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return persistedObj;
    }

    @Override
    public Carrier getByPK(Integer key) throws PersistException {
        return super.getByPK(key);
    }

    @Override
    public void update(Carrier obj) throws PersistException {
        super.update(obj);
    }

    @Override
    public void delete(Carrier obj) throws PersistException {
        super.delete(obj);
    }

    @Override
    public List<Carrier> getAll() throws PersistException {
        return super.getAll();
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO timetable.Carrier (tariff, info) \nVALUES (?, ?);";
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, tariff, info FROM timetable.Carrier";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE timetable.Carrier \n" +
                "SET tariff = ?, info = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM timetable.Carrier WHERE id = ?;";
    }

    @Override
    protected List<Carrier> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Carrier> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistCarrier carrier = new PersistCarrier();
                carrier.setId(rs.getInt("id"));
                carrier.setTariff(rs.getString("tariff"));
                carrier.setInfo(rs.getString("info"));
                result.add(carrier);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stm, Carrier obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getTariff());
            stm.setString(2, obj.getInfo());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stm, Carrier obj)
            throws PersistException {
        try {
            stm.setString(1, obj.getTariff());
            stm.setString(2, obj.getInfo());
            stm.setInt(3, obj.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public MySqlCarrierDao(Connection connection) {
        super(connection);
    }
}