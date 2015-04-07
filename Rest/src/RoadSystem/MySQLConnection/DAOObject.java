package MySQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;

public abstract class DAOObject {
	protected Connection getDefultConnection() {
		return MySQLConnection.getDefultConnection();
	}
	protected abstract ResultSet insert();
	protected abstract ResultSet update();
	protected abstract ResultSet delete();
	protected abstract ResultSet getInfo();
}
