package users.controller;

import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.User;
import users.db.mysql.MySqlDaoFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstParameter");
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
        if (errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>" + errorMsg + "</font>");
            rd.include(request, response);
        } else {

            MySqlDaoFactory factory = new MySqlDaoFactory();
            try {
                GenericDao dao = factory.getDao(factory.getContext(), User.class);
                User user = (User) dao.create();
                user.setEmail(email);
                user.setPassword(password);
                user.setCreate_date(new Date());
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);
                dao.persist(user);
                HttpSession session = request.getSession();
                session.setAttribute("User", user);
                response.sendRedirect("home.jsp");
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkError(HttpServletRequest request) {
        return null;
    }

}
