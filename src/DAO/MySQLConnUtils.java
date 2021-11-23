package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnUtils {

	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		// Thông tin database
		String hostName = "localhost";
		String dbName = "test";
		String userName = "root";
		String password = "563369";
		return getMySQLConnection(hostName, dbName, userName, password);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		// Cấu trúc URL Connection đối với MySQL:
		// jdbc:mysql://localhost:3306/temp
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}
	
}