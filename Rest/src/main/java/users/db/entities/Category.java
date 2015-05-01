package users.db.entities;

import users.db.dao.Identified;

/**
 * ��'����� ������������� ������� "��������".
 *
 * @author ����� ��������� ���������, ��. ��-31, Բ��, ���� �ϲ
 */
public class Category implements Identified<Integer> {

    private Integer id;
    private String name;

    protected void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {}
}
