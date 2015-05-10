package users.controller;

import ORMroad.Station;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.entities.UsersCategory;
import users.db.mysql.MySqlCarrierDao;
import users.db.mysql.MySqlDaoFactory;
import users.db.mysql.MySqlUserDao;
import users.db.mysql.MySqlUsersCategoryDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String errorMsg = null;

        if(email == null || email.equals("")){
            errorMsg ="User Email can't be null or empty";
        }
        if(password == null || password.equals("")){
            errorMsg = "Password can't be null or empty";
        }

        if(errorMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= response.getWriter();
            out.println("<div class=\"container\">\n<div class=\"alert alert-warning fade in\">");
            out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.println(errorMsg + "\n</div>\n</div>");
            rd.include(request, response);
        } else {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlUserDao dao = (MySqlUserDao) factory.getDao(con, User.class);
                User user = dao.getByCredentials(email, password);
                if(user != null) {
                    MySqlUsersCategoryDao ucdao = (MySqlUsersCategoryDao) factory.getDao(con, UsersCategory.class);
                    List<Category> categories = ucdao.getCategoriesOf(user);
                    Set<String> names = new HashSet<>();
                    for (Category category: categories) {
                        names.add(category.getName());
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("categories", names);
                    if (user.getCarrier_id() != null) {
                        MySqlCarrierDao cardao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
                        Carrier carrier = cardao.getByPK(user.getCarrier_id());
                        carrier.getStations().sort(new Comparator<Station>() {
                            @Override
                            public int compare(Station o1, Station o2) {
                                return o1.getName().compareToIgnoreCase(o2.getName());
                            }
                        });
                        session.setAttribute("carrier", carrier);
                    }
                    response.sendRedirect("home.jsp");
                } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
                    PrintWriter out = response.getWriter();
                    System.out.println(("User not found with email=" + email));
                    out.println("<div class=\"container\">\n<div class=\"alert alert-warning fade in\">");
                    out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                    out.println("No user found with given email id, please register first\n</div>\n</div>");
                    rd.include(request, response);
                }
            } catch (PersistException e) {
                throw new ServletException("DB Connection problem.");
            }
        }
    }
}
