package users.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.User;
import users.db.mysql.MySqlCarrierDao;
import users.db.mysql.MySqlDaoFactory;
import users.db.mysql.MySqlUserDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        if (category != null && category.equals("carrier")) {
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
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlUserDao udao = (MySqlUserDao) factory.getDao(con, User.class);
                User user = udao.create();
                user.setPassword(password);
                user.setCreate_date(new Date());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);

                if (isCarrier) {
                    MySqlCarrierDao cdao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
                    Carrier carrier = cdao.create();
                    carrier.setTariff(tariff);
                    carrier.setInfo(info);
                    cdao.update(carrier);
                    user.setCarrier_id(carrier.getId());
                }
                user.setEmail(email);
                udao.update(user);
                //forward to login page to login
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=green>Registration successful, please login below.</font>");
                rd.include(request, response);
            } catch (PersistException e) {
                try {
                    SQLException sqlE = (SQLException) e.getCause();
                    if(sqlE.getErrorCode() == 1062) { // код, що свідчить про намагання копіювання унікального поля
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
                        PrintWriter out = response.getWriter();
                        out.println("<font color=red>This email already exists</font>");
                        rd.include(request, response);
                    } else throw new ServletException("DB Connection problem " + sqlE.getMessage());
                } catch (ClassCastException cce) {throw new ServletException("DB Connection problem ");}
            }
        }
    }
}
