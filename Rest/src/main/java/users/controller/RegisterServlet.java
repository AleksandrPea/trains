package users.controller;

import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.User;
import users.db.mysql.MySqlDaoFactory;
import users.db.mysql.MySqlUserDao;

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

        if(errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>"+errorMsg+"</font>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlUserDao dao = (MySqlUserDao) factory.getDao(con, User.class);
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setCreate_date(new Date());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);
                dao.persist(user);
                //forward to login page to login
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=green>Registration successful, please login below.</font>");
                rd.include(request, response);
            } catch (PersistException e) {
                try {
                    SQLException sqlE = (SQLException) e.getCause();
                    if(sqlE.getErrorCode() == 1062) { // код, який вказує на спробу копіювання унікальних полів
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
                        PrintWriter out = response.getWriter();
                        out.println("<font color=red>This email already exists</font>");
                        rd.include(request, response);
                    } else throw new ServletException("DB Connection problem ");
                } catch (ClassCastException cce) {throw new ServletException("DB Connection problem ");}
            }
        }
    }
}
