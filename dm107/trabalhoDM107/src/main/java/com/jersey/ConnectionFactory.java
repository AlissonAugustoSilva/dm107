package com.jersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionFactory {

	public Connection getConnection() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());	
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/dm107", "root", "root");
	 }

}
