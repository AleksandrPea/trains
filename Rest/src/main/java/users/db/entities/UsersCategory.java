package users.db.entities;

import users.util.Pair;

/**
 * Об'єктне представлення зв'язку <i>many to many</i> між {@link User}
 * та {@link Category}.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */

public class UsersCategory implements Identified<Pair<Integer, Integer>> {

    private Integer user_id = null;
    private Integer category_id = null;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public UsersCategory() {}

    @Override
    public Pair<Integer, Integer> getId() {
        Pair<Integer, Integer> id = null;
        if (user_id != null && category_id != null) {
            id = new Pair<>("user_id", getUser_id(),
                    "category_id", getCategory_id());
        }
        return id;
    }
}
