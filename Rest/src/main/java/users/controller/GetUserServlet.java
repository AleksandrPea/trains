package users.controller;

import users.db.dao.GenericDao;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.entities.User;
import users.db.mysql.MySqlDaoFactory;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Додає до {@code request} атрибутів об'єкти класу {@code User} та,
 * якщо наданий користувач - перевізник, {@code Carrier}. Надається
 * користувач за допомогою передачі його {@code id} у URL адресу
 * у вигляді параметра з назвою {@code userId}.<br>
 * Приклад URL запиту: {@code /View?userId=5}.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebServlet(name = "GetUser", urlPatterns = {"/View" })
public class GetUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
        try {
            GenericDao dao = factory.getDao(con, User.class);
            User user = (User) dao.getByPK(Integer.parseInt(
                    request.getParameter("userId")));
            if (user.getCarrier_id() != null) {
                dao = factory.getDao(con, Carrier.class);
                Carrier carrier = (Carrier) dao.getByPK(user.getCarrier_id());
                request.setAttribute("reqCarrier", carrier);
            }
            request.setAttribute("reqUser", user);
            RequestDispatcher rd = null;
            if (request.getRequestURI().endsWith("View")) {
                rd = getServletContext().getRequestDispatcher("/view.jsp");
            }
            rd.forward(request, response);
        } catch (PersistException e) {
            throw new ServletException("DB Connection problem.");
        }
    }
}
