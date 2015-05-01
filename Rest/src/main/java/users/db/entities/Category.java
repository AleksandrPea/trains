package users.db.entities;

import users.db.dao.Identified;

/**
 * Об'єктне представлення сутності "Категорія".
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
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
