package users.entities;

import ORMroad.Station;
import users.dao.Identified;

import java.util.List;

/**
 * ��'����� ������������� ������� "���������".
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public class Carrier implements Identified<Integer> {

    private Integer id;

    /** ����� ����������. */
    private String tariff;

    /** ��������� ����������. ���� ���� null. */
    private String info;

    /** ������ �������, �� ���� ��������� �� ���������. */
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
