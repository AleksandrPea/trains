package users.controller;

import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.entities.UsersCategory;
import users.db.mysql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Здійснює регістрацію користувача, заносячи дані до бази даних.
 * Іншими словами, оброблює запит користувача на сторінці {@code /register.html}.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebServlet(name = "Register", urlPatterns = {"/Register" })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String address = request.getParameter("address");

        boolean isCarrier = false;
        String tariff = null;
        String info = null;
        String[] category = request.getParameterValues("category");
        if (category != null && category[0].equals("carrier")) {
            isCarrier = true;
            tariff = request.getParameter("tariff");
            info = request.getParameter("info");
        }

        String errorMsg = null;
        if (email == null || email.equals("")) {
            errorMsg = "User Email can't be null or empty";
        }
        if (password == null || password.equals("")) {
            errorMsg = "Password can't be null or empty";
        }
        if (lastName == null || lastName.equals("")) {
            errorMsg = "Last name can't be null or empty";
        }
        if (firstName == null || firstName.equals("")) {
            errorMsg = "First name can't be null or empty";
        }
        if (address == null || address.equals("")) {
            errorMsg = "Address can't be null or empty";
        }
        if (isCarrier && (tariff == null || tariff.equals(""))) {
            errorMsg = "Tariff can't be null or empty";
        }

        if(errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"container\">\n<br><div class=\"alert alert-warning fade in\">");
            out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.println(errorMsg + "\n</div>\n</div>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                con.setAutoCommit(false);            // Робимо транзакцію
                MySqlUserDao udao = (MySqlUserDao) factory.getDao(con, User.class);
                User user = udao.create();
                user.setPassword(password);
                user.setCreate_date(new Date());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);
                user.setEmail(email);
                udao.update(user);
                if (isCarrier) {
                    GenericDao dao = factory.getDao(con, Carrier.class);
                    Carrier carrier = (Carrier) dao.create();
                    carrier.setTariff(tariff);
                    carrier.setInfo(info);
                    dao.update(carrier);
                    user.setCarrier_id(carrier.getId());
                    // Встановлюємо асоціацію
                    MySqlCategoryDao cdao = (MySqlCategoryDao) factory.getDao(con, Category.class);
                    dao = factory.getDao(con, UsersCategory.class);
                    UsersCategory uc = (UsersCategory) dao.create();
                    uc.setCategory_id(cdao.getFirstOf("Carrier").getId());
                    uc.setUser_id(user.getId());
                    dao.persist(uc);
                    udao.update(user);
                }
                con.commit();
                // Посилаємо на сторінку login
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<div class=\"container\">\n<br><div class=\"alert alert-success fade in\">");
                out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                out.println("Registration successful, please login below\n</div>\n</div>");
                rd.include(request, response);
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (SQLException e2) {
                    throw new ServletException("DB Connection problem ");
                }
                throw new ServletException("DB Connection problem ");
            } catch (PersistException e) {
                try {
                    con.rollback();
                } catch (SQLException e2) {
                    throw new ServletException("DB Connection problem ");
                }
                if (e.getCause() instanceof SQLException) {
                    SQLException sqlE = (SQLException) e.getCause();
                    if(sqlE.getErrorCode() == 1062) { // Код, що свідчить про намагання копіювання унікального поля
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
                        PrintWriter out = response.getWriter();
                        out.println("<div class=\"container\"><br>\n<div class=\"alert alert-warning fade in\">");
                        out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                        out.println("This email already exists\n</div>\n</div>");
                        rd.include(request, response);
                    }
                } else throw new ServletException("DB Connection problem ");
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new ServletException("DB Connection problem ");
                }
            }
        }
    }
}
