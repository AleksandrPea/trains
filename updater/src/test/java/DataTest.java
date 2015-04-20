import orm.Database;
import parser.Parser;

/**
 * Created by Admin on 09.04.2015.
 */
public class DataTest {
    public static void main(String[] args) {
        Parser p = new Parser();
        p.parse("Че");
        //Station st = new Station("1010");
        Database.saveDataBase(p.getStations(), p.getRoutes(), p.getWayPoints());
       // Database.save(st);
        System.out.println(1);
    }
}
