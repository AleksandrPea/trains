package users.db.entities;

import ORMroad.Station;

import java.util.List;

/**
 * Об'єктне представлення сутності "Перевізник".
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class Carrier implements Identified<Integer> {

    private Integer id;

    /** Тариф перевезень. */
    private String tariff;

    /** Додаткова інформація. Може бути null. */
    private String info;

    /** Список станцій, до яких перевізник має відношення. */
    private List<Station> stations;

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Carrier() {}
}
