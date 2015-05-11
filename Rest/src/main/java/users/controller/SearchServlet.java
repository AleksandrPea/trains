package users.controller;

import ORMroad.Database;
import ORMroad.Station;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.Category;
import users.db.entities.User;
import users.db.mysql.MySqlCarrierDao;
import users.db.mysql.MySqlCategoryDao;
import users.db.mysql.MySqlDaoFactory;
import users.db.mysql.MySqlUserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Робить пошук усіх перевізників, що мають відношення до наданої станції.
 * Ім'я станції надається на сторінці {@code /search.jsp} у спеціальному полі.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/search.jsp");
        String name = req.getParameter("search");
        List<Station> stations = Database.getStation(name);
        if (stations.size() != 0) {
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlUserDao udao = (MySqlUserDao) factory.getDao(con, User.class);
                MySqlCategoryDao catdao = (MySqlCategoryDao) factory.getDao(con, Category.class);
                MySqlCarrierDao cardao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
                List<User> users = udao.getByCategory(catdao.getFirstOf("Carrier"));
                List<Carrier> requestedCarr = new ArrayList<>();
                List<User> requestedUsers = new ArrayList<>();
                for (User user : users) {
                    Iterator<Station> iter = stations.iterator();
                    Carrier carrier = cardao.getByPK(user.getCarrier_id());
                    boolean hasStation = false;
                    while (iter.hasNext() && !hasStation) {
                        hasStation = cardao.hasStation(carrier, iter.next());
                    }
                    if (hasStation) {
                        requestedUsers.add(user);
                        requestedCarr.add(carrier);
                    }
                }
                req.setAttribute("count", requestedUsers.size());
                req.setAttribute("reqCarriers", requestedCarr);
                req.setAttribute("reqUsers", requestedUsers);
                rd.forward(req, resp);
            } catch (PersistException e) {
                throw new ServletException("DB Connection problem.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                rd.include(req, resp);
                PrintWriter out = resp.getWriter();
                out.print("<br><div class=\"container\">" +
                        "<p>There are no stations with the specified name.</p></div>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
