package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class db_connection {

	public static Connection conn;
	private static db_connection instance = new db_connection();
	
	public db_connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			//your server ip instead of 127.0.0.1
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/denas_power", "root","root");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static db_connection instance() {
        if (instance == null) {
        	instance = new db_connection();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
	
	
}

