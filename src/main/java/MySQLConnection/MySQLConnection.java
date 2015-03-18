package MySQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	public static Connection getDefultConnection() {
		String url = "jdbc:mysql://localhost:3306/timetable";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, "root", "1111");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}
