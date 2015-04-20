package Test;

import ORMroad.Database;
import ORMroad.Station;

/**
 * Created by Admin on 08.04.2015.
 */
public class TempTest {

    public static void main(String[] args) {
        Station st = new Station("1234");
        Database.save(st);
        System.out.println("1");
    }
}
