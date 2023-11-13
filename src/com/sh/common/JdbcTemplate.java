package com.sh.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc관련 공통메소드를 static으로 제공
 * - 미리 예외처리해서 호출부쪽에서 간결히 사용
 *
 * - 드라이버 클래스 등록(최초 1회)
 * - getConnection() : Connection
 * - commit(Connection)
 * - rollback(Connection)
 * - close(Connection)
 * - close(PerparedStatement)
 * - close(ResultSet)
 */
public class JdbcTemplate {
	static String driverClassName;
	static String url;
	static String user;
	static String password;
	
	static {
		// datasource.properties 읽어오기
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("resources/datasource.properties"));
			driverClassName = prop.getProperty("driverClassName");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
//			System.out.println(driverClassName);
//			System.out.println(url);
//			System.out.println(user);
//			System.out.println(password);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 드라이버 클래스 등록
		try {
			Class.forName(driverClassName);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // 공용이어서 autocommit도 설정
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
