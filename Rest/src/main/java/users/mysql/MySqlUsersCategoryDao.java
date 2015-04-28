//package users.mysql;
//
//import users.dao.AbstractJDBCDao;
//import users.dao.PersistException;
//import users.entities.Category;
//import users.entities.UsersCategory;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Зроблений Горохом Олександром,
// * КПІ, ФІОТ, гр. ІО-31
// * on 26.04.2015.
// */
//public class MySqlUsersCategoryDao extends AbstractJDBCDao<, Integer> {
//
//    @Override
//    public UsersCategory create() throws PersistException {
//        UsersCategory g = new UsersCategory();
//        return persist(g);
//    }
//
//    @Override
//    public String getCreateQuery() {
//        return "INSERT INTO timetable.Users_Category (user_email, category_id) \n" +
//                "VALUES (?, ?);";
//    }
//
//    @Override
//    public String getSelectQuery() {
//        return "SELECT user_email, category_id FROM timetable.Users_Category";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return getCreateQuery();
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM timetable.Users_Category WHERE user_email = ?, category_id = ?;";
//    }
//
//    @Override
//    protected List<UsersCategory> parseResultSet(ResultSet rs) throws PersistException {
//        LinkedList<UsersCategory> result = new LinkedList<>();
//        try {
//            while (rs.next()) {
//                UsersCategory usersC = new UsersCategory();
//                usersC.setUser_email(rs.getString("user_email"));
//                usersC.setCategory_id(rs.getInt("category_id"));
//                result.add(usersC);
//            }
//        } catch (Exception e) {
//            throw new PersistException(e);
//        }
//        return result;
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement stm, UsersCategory obj)
//            throws PersistException {
//        try {
//            stm.setString(1, obj.getUser_email()));
//            stm.setInt(2, obj.getCategory_id());
//        } catch (Exception e) {
//            throw new PersistException(e);
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement stm, UsersCategory obj)
//            throws PersistException {
//        prepareStatementForInsert(stm, obj);
//    }
//
//    public MySqlUsersCategoryDao(Connection connection) {
//        super(connection);
//    }
//
//
//    @Override
//    public UsersCategory getByPK(String[] key) throws PersistException {
//        return null;
//    }
//}