//package users.controller.listeners;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//
//@WebListener
//public class AppContextListener implements ServletContextListener {
//
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ServletContext ctx = servletContextEvent.getServletContext();
//
//        String dbURL = ctx.getInitParameter("dbURL");
//        String user = ctx.getInitParameter("dbUser");
//        String pwd = ctx.getInitParameter("dbPassword");
//        try {
//            DBConnectionManager connectionManager = new DBConnectionManager(dbURL, user, pwd);
//            ctx.setAttribute("DBConnection", connectionManager.getConnection());
//            System.out.println("DB Connection initialized successfully.");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        Connection con = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
//        try {
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
