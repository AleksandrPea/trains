package users.controller;

import ORMroad.Database;
import ORMroad.Station;
import users.db.dao.PersistException;
import users.db.entities.Carrier;
import users.db.mysql.MySqlCarrierDao;
import users.db.mysql.MySqlDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "Delete Stations", urlPatterns = {"/carrier/DeleteStations"})
public class DeleteStationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        Connection con = (Connection) getServletContext().getAttribute("DBConnection");
        MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
        try {
            MySqlCarrierDao dao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
            Carrier carrier = (Carrier) session.getAttribute("carrier");
            List<Station> stations = carrier.getStations();
            String[] oldStations = req.getParameterValues("carrierStations");
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
