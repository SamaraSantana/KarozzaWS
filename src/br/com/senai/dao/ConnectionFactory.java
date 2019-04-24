package br.com.senai.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() {
		try {
			// drive de conecção que foi colado na lib
			Class.forName("com.mysql.jdbc.Driver");
			// return
			// DriverManager.getConnection("jdbc:mysql://localhost/senai","root","root");
			return DriverManager.getConnection("jdbc:mysql://10.84.146.9/Karozza", "tt4", "root");
			//return DriverManager.getConnection("jdbc:mysql://localhost/Karozza", "root", "root");

		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);

		}

	}

}
