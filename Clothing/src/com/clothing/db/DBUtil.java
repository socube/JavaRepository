package com.clothing.db;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtil {

	// oracle连接数据库的地址
	// private static String URL="jdbc:oracle:thin:@localhost:1521:orcl";

	// Sql server连接数据库的地址   databasename 为操作的数据库的名称
	//private static String URL = "jdbc:sqlserver://localhost:1433;databasename=ajaxdb";
	// 连接数据库的用户名
	//private static String USERNAME = "sa";

	// 连接数据库的密码
	//private static String PASSWORD = "123456";
	// oracle连接数据库的驱动
	// private static String DRIVER="oracle.jdbc.driver.OracleDriver";

	// sql server连接数据库的驱动
	//private static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

         //mysql
        private static String URL="jdbc:mysql://localhost:3306/clothing";
        private static String DRIVER="com.mysql.jdbc.Driver";
        private static String USERNAME = "root";
        private static String PASSWORD = "123456";

	public DBUtil() {
		System.out.println("构造方法");
	}

	// 加载驱动类,加载驱动只需要加载一次就行了
	static {
		// 静态块,在整个程序中只会执行一次
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 提供获取连接的方法
	public static Connection getConnection() {
		Connection cn = null;

		try {
			cn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cn;
	}

	// 关闭资源的方法
	public static void closeAll(Connection cn, Statement st, ResultSet rs) {
		try {
			if (cn != null)
				cn.close();
			if (st != null)
				st.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 通用增删改的方法
	public static int executeUpdate(String sql,Object...pars) {
		Connection cn = DBUtil.getConnection();
		PreparedStatement st = null;
		int count = 0;
		try {

			st = cn.prepareStatement(sql);

			if (pars != null) {
				// 使用循环来设置参数
				for (int i = 0; i < pars.length; i++) {
					st.setObject(i + 1, pars[i]);
				}
			}

			count = st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(cn, st, null);
		}
		return count;
	}

	// 通用的查询方法
	public static <T> List<T> query(String sql, Class<T> cls, Object... obj) {
		List<T> list = new ArrayList<T>();//创建一个集合  
		Connection cn = DBUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = cn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pst.setObject(i + 1, obj[i]);
				}
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				T t = cls.newInstance();// 利用无参的构造方法创建对象
				// 利用set方法初始化对象
				Field[] fd = cls.getDeclaredFields();// 获取该类的所有的属性
				for (int i = 0; i < fd.length; i++) {
					// 通过属性获取对应的set方法
					String fieldName = fd[i].getName();// 获取属性的名字  user_id
					String methodName = "set"      
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);// 拼接得到对应属性的set方法名
					Method method = cls.getDeclaredMethod(methodName,
							fd[i].getType()); // 通过方法名和属性的数据类型 获取对应set方法
					Object value = null;
					if (fd[i].getType().equals(int.class)) {
						value = rs.getInt(fieldName);
					}
					if (fd[i].getType().equals(double.class)) {
						value = rs.getDouble(fieldName);
					}
					if (fd[i].getType().equals(String.class)) {
						value = rs.getString(fieldName);
					}
					if (fd[i].getType().equals(Date.class)) {
						value = rs.getDate(fieldName);
					}
					method.invoke(t, value);
				}
				list.add(t);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(cn, pst, rs);
		}
		return list;
	}

	public static void main(String[] args) {
		if(DBUtil.getConnection()==null){
			System.out.println("数据库连接失败!");
		}
		else{
			System.out.println("数据库连接成功!");
		}

	}
}
