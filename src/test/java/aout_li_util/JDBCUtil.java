package aout_li_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



public class JDBCUtil {
	//将连接数据库静态化，只需执行一次
	public static Properties properties = new Properties();
	static {
		System.out.println("静态代码块解析properties数据");
		InputStream iStream;
		try {
			iStream = new FileInputStream(new File("src/test/resources/jdbc.properties"));
			properties.load(iStream);
		} catch (Exception e) {
			System.out.println("发生了异常");
			e.printStackTrace();
		}
	}
	/**
	 * 根据sql查询表数据，并以map返回，key为字段名，value为字段值
	 * @param sql 要执行的sql语句
	 * @param values 条件字段的值
	 * @return
	 */
	public static Map<String, Object> query(String sql ,Object ... values) {
		//?代表占位符
		
		//1.根据连接信息，获取数据库连接（连上数据库）
//		String url ="jdbc:mysql://rm-2ze89g4x102400p5oho.mysql.rds.aliyuncs.com:3306/guangxidb?useUnicode=true&characterEncoding=utf-8";
//		String user = "dbtestuser";
//		String password = "hanyastar@2018";
		Map<String, Object> columnLableAndValues = null;
		try {
			//封装到静态代码块了
//			Properties properties = new Properties();
//			InputStream iStream = new FileInputStream(new File("src/test/resources/jdbc.properties"));
//			properties.load(iStream);
			//直接从静态代码块里的properties取
			Connection connection = getConnection();
			//2.获取preparestatement对象（此类型的对象有提供数据库操作方法）
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//3.设置条件字段的值
//			preparedStatement.setObject(1, "1");
			for (int i = 0; i < values.length; i++) {
				preparedStatement.setObject(i+1, values[i]);
			}
			//4.调用查询方法，执行查询，返回resultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			//获取查询相关的信息
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			//定义一个map将结果值放入
			columnLableAndValues = new HashMap<String, Object>();
			//5.从结果集里取查询数据
			while (resultSet.next()) {
				for (int i =1 ; i <= columnCount; i++) {
				//循环取出每个字段的列名
				String columnLable = metaData.getColumnLabel(i);
				//循环取出每个字段的值
				String  columnValue = resultSet.getObject(columnLable).toString();
				columnLableAndValues.put(columnLable, columnValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnLableAndValues;
		
	}
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		String url = properties.getProperty("jdbc.url");
		String user = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		Connection connection = DriverManager.getConnection(url,user,password);
		return connection;
	}
	
	public static void main(String[] args) {
		JDBCUtil jdbc = new JDBCUtil();
		String sql = "select user_name,name from sys_user where id = ?";
		Object [] values = {"1"};
		Map<String, Object> columnLabelAndValues = jdbc.query(sql,values);
		Set<String> columnLabels = columnLabelAndValues.keySet();
		for (String columnLabel : columnLabels) {
//			System.out.println("coloumnLabel:"+columnLabel+"****columnValue:"+columnLabelAndValues.get(columnLabel));
		}
	}
}

