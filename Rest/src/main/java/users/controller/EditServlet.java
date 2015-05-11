package users.controller;

import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.User;
import users.db.mysql.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Зберігає зміни профілю користувача, що були здійснені на сторінці {@code /edit.jsp}.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebServlet(name = "Edit", urlPatterns = {"/Edit" })
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String address = request.getParameter("address");
        String tariff = null;
        String info = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Carrier carrier = (Carrier) session.getAttribute("carrier");
        if (carrier != null) {
            tariff = request.getParameter("tariff");
            info = request.getParameter("info");
        }

        String errorMsg = null;
        if (lastName == null || lastName.equals("")) {
            errorMsg = "Last name can't be null or empty";
        }
        if (firstName == null || firstName.equals("")) {
            errorMsg = "First name can't be null or empty";
        }
        if (address == null || address.equals("")) {
            errorMsg = "Address can't be null or empty";
        }
        if (carrier != null && (tariff == null || tariff.equals(""))) {
            errorMsg = "Tariff can't be null or empty";
        }
        if(errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/edit.jsp");
            PrintWriter out = response.getWriter();
            rd.include(request, response);
            out.println("<div class=\"container\">\n<br><div class=\"alert alert-warning fade in\">");
            out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.println(errorMsg + "\n</div>\n</div>");
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                con.setAutoCommit(false);            // Робимо транзакцію
                GenericDao dao = factory.getDao(con, User.class);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);
                dao.update(user);
                if (carrier != null) {
                    dao = factory.getDao(con, Carrier.class);
                    carrier.setTariff(tariff);
                    carrier.setInfo(info);
                    dao.update(carrier);
                }
                con.commit();
                response.sendRedirect("/1111/home.jsp");
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
                throw new ServletException("DB Connection problem ");
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
