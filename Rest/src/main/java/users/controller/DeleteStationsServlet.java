package users.controller;

import ORMroad.Database;
import ORMroad.Station;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.mysql.MySqlCarrierDao;
import users.db.mysql.MySqlDaoFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@Deprecated
@WebServlet(name = "Delete Stations")
public class DeleteStationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String[] oldStations = req.getParameterValues("carrierStations");
        if (oldStations == null) {
            try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/carrier/manage.jsp");
                rd.include(req, resp);
                resp.setCharacterEncoding("UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<div class=\"container\">\n<div class=\"alert alert-warning fade in\">");
                out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                out.println("Select stations first\n</div>\n</div>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            HttpSession session = req.getSession();
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlCarrierDao dao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
                Carrier carrier = (Carrier) session.getAttribute("carrier");
                List<Station> stations = carrier.getStations();
                for (String stationId : oldStations) {
                    stations.remove(Database.get(Station.class, Integer.parseInt(stationId)));
                }
                dao.update(carrier);
                resp.sendRedirect("/1111/carrier/manage.jsp");
            } catch (PersistException e) {
                throw new ServletException("DB Connection problem." + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
