package users.controller.listeners;

import users.db.dao.PersistException;
import users.db.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Слідкує за створенням та закриттям підключення до MySql бази даних.
 * Необхідні параметри задються у конфігураційному файлі web.xml.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        String dbURL = ctx.getInitParameter("dbURL");
        String user = ctx.getInitParameter("dbUser");
        String pwd = ctx.getInitParameter("dbPassword");
        try {
            ctx.setAttribute("DBConnection",
                    MySqlDaoFactory.getInstance().createContext(dbURL, user, pwd));
            System.out.println("DB Connection initialized successfully.");
        } catch (PersistException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Connection con = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
