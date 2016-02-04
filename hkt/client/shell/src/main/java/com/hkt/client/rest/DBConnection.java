package com.hkt.client.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBConnection {

	private Connection conn;
	private  String url = "jdbc:mysql://128.199.205.148:3306/hktconsultant?autoReconnect=true";
	private String client;
	public Connection connection() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
	            System.out.print("Ket noi thanh cong!");
		} catch (ClassNotFoundException e) {
			System.out.print("Ket noi that bai!");
		}
		try {
			conn = (Connection) DriverManager.getConnection(url, "root", "root");
	            System.out.print("Ket noi co so du lieu thanh cong!");	
		} catch (SQLException e) {
			 System.out.print("khong co co so du lieu!");
		}
		return conn;
	}
	
	public DBConnection(){};
	
}
