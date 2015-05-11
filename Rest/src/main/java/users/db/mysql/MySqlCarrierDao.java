package users.db.mysql;

import ORMroad.Database;
import ORMroad.Station;
import users.db.dao.AbstractJDBCDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для управління персистентним станом об'єктів класу {@link Carrier}
 * у базі даних MySql.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class MySqlCarrierDao extends AbstractJDBCDao<Carrier, Integer> {

    /**
     * Клас, що робить метод {@code setId} класу {@link Carrier} доступним
     * тільки для об'єктів відповідного Dao класу {@link MySqlCarrierDao}.
     */
    private class PersistCarrier extends Carrier {
        public void setId(Integer id) {
            super.setId(id);
        }
    }

    public MySqlCarrierDao(Connection connection) {
        super(connection);
    }

    public Carrier create() throws PersistException {
        Carrier c = new Carrier();
        c.setInfo("None");
        c.setTariff("-");
        c.setStations(new ArrayList<Station>());
        return persist(c);
    }

    @Override
    public Carrier persist(Carrier obj) throws PersistException {
        Carrier persistedObj = super.persist(obj);
        persistedObj.setStations(obj.getStations());
        stationListInsert(persistedObj);
        return persistedObj;
    }

    @Override
    public Carrier getByPK(Integer key) throws PersistException {
        Carrier obj = super.getByPK(key);
        obj.setStations(stationListSelect(obj));
        return obj;
    }

    /**
     * Зберігає стан об'єкту obj в базі даних.
     * Записи з таблиці, яка містить асоційовані станції
     * з перевізником obj, спочатку видаляються, а потім
     * встановлюються нові.
     */
    @Override
    public void update(Carrier obj) throws PersistException {
        stationListDelete(obj);
        super.update(obj);
        stationListInsert(obj);
    }

    @Override
    public void delete(Carrier obj) throws PersistException {
        stationListDelete(obj);
        super.delete(obj);

    }

    @Override
    public List<Carrier> getAll() throws PersistException {
        List<Carrier> list = super.getAll();
        for (Carrier carrier: list) {
            carrier.setStations(stationListSelect(carrier));
        }
        return list;
    }

    /**
     * Визначає, чи має відношення перевізник {@code obj} до станції {@code station}.
     * @return {@code true} якщо перевізник має асоціацію зі станцією, інакше - {@code false}.
     */
    public boolean hasStation(Carrier obj, Station station) throws PersistException {
        String sql = "SELECT Station_id FROM timetable.Station_list " +
                "WHERE Carrier_id = ? AND Station_id = ?";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            stm.setInt(2, station.getStationId());
            return stm.executeQuery().first();
        } catch (Exception e) {
            throw new PersistException(e);
        }
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
        ArrayList<Carrier> result = new ArrayList<>();
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

    /**
     * Створює записи у таблиці, яка представляє зв'язок <i>many to many</i> між
     * {@link Carrier} та {@link Station}. Тобто асоціює об'єкт {@code obj} зі
     * станціями, які він інкапсулює, у базі даних.
     */
    private void stationListInsert(Carrier obj) throws PersistException {
        String sql = "INSERT INTO timetable.Station_list (Carrier_id, Station_id) \n" +
                "VALUES (?, ?);";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            List<Station> stations = obj.getStations();
            stm.setInt(1, obj.getId());
            for (Station st: stations) {
                stm.setInt(2, st.getStationId());
                stm.execute();
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    /** Повертає список станцій з бази даних, що асоційовані з об'єктом {@code obj}. */
    private List<Station> stationListSelect(Carrier obj) throws PersistException {
        List<Station> stations = null;
        String sql = "SELECT Station_id FROM timetable.Station_list WHERE Carrier_id = ?";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            stations = new ArrayList<>();
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                stations.add((Station) Database.get(Station.class, result.getInt("Station_id")));
            }
            obj.setStations(stations);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return stations;
    }

    /** Видаляє список станцій з бази даних, що асоційовані з об'єктом {@code obj}. */
    private int stationListDelete(Carrier obj) throws PersistException {
        int number;
        String sql = "DELETE FROM timetable.Station_list WHERE Carrier_id = ?;";
        try (PreparedStatement stm = getConnection().prepareStatement(sql)) {
            stm.setInt(1, obj.getId());
            number = stm.executeUpdate();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return number;
    }
}