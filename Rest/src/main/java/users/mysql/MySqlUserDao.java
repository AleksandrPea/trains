//package users.mysql;
//
//import users.dao.AbstractJDBCDao;
//import users.dao.PersistException;
//import users.entities.User;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.LinkedList;
//
///**
// * Зроблений Горохом Олександром,
// * КПІ, ФІОТ, гр. ІО-31
// * on 26.04.2015.
// */
//public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {
//
//    private class PersistUser extends User {
//        public void setId(int id) {
//            super.setId(id);
//        }
//    }
//
//    public MySqlUserDao(Connection connection) {
//        super(connection);
//    }
//
//
//    public User create() throws PersistException {
//        User s = new User();
//        return persist(s);
//    }
//
//    @Override
//    public String getCreateQuery() {
//        return "INSERT INTO daotalk.User (name, surname, enrolment_date, group_id) \n" +
//                "VALUES (?, ?, ?, ?);";
//    }
//
//    @Override
//    public String getSelectQuery() {
//        return "SELECT id, name, surname, enrolment_date, group_id FROM daotalk.User";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return "UPDATE daotalk.User \n" +
//                "SET name = ?, surname = ?, enrolment_date = ? group_id = ? WHERE id = ?;";
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM daotalk.User WHERE id = ?;";
//    }
//
//    @Override
//    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
//        LinkedList<User> result = new LinkedList<>();
//        try {
//            while (rs.next()) {
//                PersistUser student = new PersistUser();
//                student.setId(rs.getInt("id"));
//                student.setName(rs.getString("name"));
//                student.setSurname(rs.getString("surname"));
//                student.setEnrolmentDate(rs.getDate("enrolment_date"));
//                student.setGroupId(rs.getInt("group_id"));
//                result.add(student);
//            }
//        } catch (Exception e) {
//            throw new PersistException(e);
//        }
//        return result;
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement stm, User obj)
//            throws PersistException {
//        try {
//            stm.setString(1, obj.getName());
//            stm.setString(2, obj.getSurname());
//            stm.setDate(3, convert(obj.getEnrolmentDate()));
//            if (obj.getGroupId() != null) {
//                stm.setInt(4, obj.getGroupId());
//            } else stm.setNull(4, java.sql.Types.INTEGER);
//        } catch (Exception e) {
//            throw new PersistException(e);
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement stm, User obj)
//            throws PersistException {
//        try {
//            stm.setString(1, obj.getName());
//            stm.setString(2, obj.getSurname());
//            stm.setDate(3, convert(obj.getEnrolmentDate()));
//            stm.setInt(4, obj.getGroupId());
//            stm.setInt(5, obj.getId());
//        } catch (Exception e) {
//            throw new PersistException(e);
//        }
//    }
//
//    protected java.sql.Date convert(java.util.Date date) {
//        if (date == null) {
//            return null;
//        }
//        return new java.sql.Date(date.getTime());
//    }
//}
