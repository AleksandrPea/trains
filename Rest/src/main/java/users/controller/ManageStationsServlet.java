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
import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;

/**
 * Оброблює запит перевізника на сторінці {@code /carrier/manage.jsp} :
 * видаляє або додає обрані станції до списку станцій, що асоційовані з перевізником.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
@WebServlet(name = "ManageStations", urlPatterns =
        {"/carrier/StationsSelect","/carrier/StationsDelete"})
public class ManageStationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String[] paramStations;
        boolean selectStations;
        if (req.getRequestURI().endsWith("Select")) {
            paramStations = req.getParameterValues("stations");
            selectStations = true;
        } else {
            paramStations = req.getParameterValues("carrierStations");
            selectStations = false;
        }
        if(paramStations != null) {
            HttpSession session = req.getSession();
            Connection con = (Connection) getServletContext().getAttribute("DBConnection");
            MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
            try {
                MySqlCarrierDao dao = (MySqlCarrierDao) factory.getDao(con, Carrier.class);
                Carrier carrier = (Carrier) session.getAttribute("carrier");
                List<Station> stations = carrier.getStations();
                for (String stationId : paramStations) {
                    Station station = (Station) Database.get(Station.class, Integer.parseInt(stationId));
                    if (selectStations) {
                        insert(stations, station);
                    } else stations.remove(station);
                }
                dao.update(carrier);
            } catch (PersistException e) {
                throw new ServletException("DB Connection problem.");
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/carrier/manage.jsp");
        try {
            rd.forward(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Встановлює станцію у відсортованому списку на потрібне місце. */
    private void insert(List<Station> stations, Station station) {
        ListIterator<Station> iter = stations.listIterator();
        while (iter.hasNext() && iter.next().getName().compareToIgnoreCase(station.getName()) < 0);
        iter.add(station);
    }
}
