import org.hibernate.Session;
import org.hibernate.Transaction;
import orm.Database;
import orm.HibernateUtil;
import parser.Parser;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 08.04.2015.
 */
public class Dump {
    public static void makeDump(String path) throws IOException, InterruptedException {
        Locale local = new Locale("ru","RU");
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
        String[] dumpcmd = {"mysqldump",
                "--opt", "-uroot", "-p1111",
                "--add-drop-table",  "--databases", "timetable",
                "-r", path + df.format(date) + ".sql"};
        Process p = Runtime.getRuntime().exec(dumpcmd);
        p.waitFor();
    }

    public static void parse() {
        Parser p = new Parser();
        p.parse("?????");
        Database.saveDataBase(p.getStations(), p.getRoutes(), p.getWayPoints());
    }

    public static void copy() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String delete = "delete from waypoint";
        session.createSQLQuery(delete).executeUpdate();
        delete = "delete from route";
        session.createSQLQuery(delete).executeUpdate();
        delete = "delete from station";
        session.createSQLQuery(delete).executeUpdate();
        tx.commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        String copy = "INSERT INTO station SELECT * FROM station_temp";
        session.createSQLQuery(copy).executeUpdate();

        copy = "INSERT INTO route SELECT * FROM route_temp";
        session.createSQLQuery(copy).executeUpdate();
        copy = "INSERT INTO waypoint SELECT * FROM waypoint_temp";
        session.createSQLQuery(copy).executeUpdate();
        tx.commit();
        session.close();

        Database.deleteDataBase();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        makeDump(args[0]);
        parse();
        copy();
        System.out.println("0");
        System.exit(0);
    }
}
