package cn.edu.guet.util;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ������� �ر�����
 *
 * @author liwei
 *
 */
public class DBConnection {

	private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
	public static Connection getConn() {
		// �ȴ�ThreadLocal��ȡ��
		Connection conn = connectionThreadLocal.get();
		if (conn == null) {
			Properties prop = new Properties();
			InputStream in;
			try {
				in = Class.forName("cn.edu.guet.util.DBConnection").getResourceAsStream("/db.properties");
				prop.load(in);

				String url = prop.getProperty("url");
				Class.forName(prop.getProperty("driver"));// ��������
				conn = DriverManager.getConnection(url, prop.getProperty("user"), prop.getProperty("password"));
				// ��һ�λ�ȡconn��Ϊ�գ���ʼ�������ThreadLocal�Ա������ȡͬһ��conn
				connectionThreadLocal.set(conn);
				return conn;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void closeConn(Connection conn)
	{
		try {
			if(conn!=null) 	conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}