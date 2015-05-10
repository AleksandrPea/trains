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
import java.util.ListIterator;

@WebServlet(name = "Select Stations", urlPatterns = {"/carrier/SelectStations"})
public class SelectStationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String[] newStations = req.getParameterValues("stations");
        if(newStations == null) {
            try {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/carrier/manage.jsp");
                PrintWriter out = resp.getWriter();
                out.println("<div class=\"container\">\n<div class=\"alert alert-warning fade in\">");
                out.println("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                out.println("Select stations first\n</div>\n</div>");
                rd.include(req, resp);
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
                for (String stationId : newStations) {
                    insert (stations, (Station) Database.get(Station.class, Integer.parseInt(stationId)));
                }
                dao.update(carrier);
                resp.sendRedirect("/1111/carrier/manage.jsp");
            } catch (PersistException e) {
                throw new ServletException("DB Connection problem.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insert(List<Station> stations, Station station) {
        ListIterator<Station> iter = stations.listIterator();
        while (iter.hasNext() && iter.next().getName().compareToIgnoreCase(station.getName()) < 0);
        iter.add(station);
    }
}
