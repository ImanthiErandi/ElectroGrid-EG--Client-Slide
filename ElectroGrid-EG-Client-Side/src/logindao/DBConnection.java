package logindao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private final static String login = "root";
	private final static String password = "User1994";
	private final static String url ="jdbc:mysql://127.0.0.1:3306/electrogrid_db";
	private static DBConnection instance = null;
	private Connection connection = null;

	private DBConnection() throws ClassNotFoundException {
		if (connection == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, login, password);
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() {
		instance = null;
	}

}
