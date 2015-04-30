package users.entities;


import users.dao.Identified;
import users.mysql.util.Pair;

import java.util.IntSummaryStatistics;

/**
 * Зроблений Горохом Олександром,
 * КПІ, ФІОТ, гр. ІО-31
 * on 28.04.2015.
 */
public class UsersCategory implements Identified<Pair<Integer, Integer>> {

    private Integer user_id;
    private Integer category_id;

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
        return new Pair<Integer, Integer>("user_id", getUser_id(),
                "category_id", getCategory_id());
    }
}
