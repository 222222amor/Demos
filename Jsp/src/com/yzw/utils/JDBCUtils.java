package com.yzw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * jdbc操作工具类
 * 尹志文
 * version 2.0
 */
public class JDBCUtils {
	private static DataSource dataSource = null;
	
	//数据库连接池，应只被初始化一次
	//c3p0
	static {
//		dataSource = new ComboPooledDataSource("c3p0_mysql");//mysql
		dataSource = new ComboPooledDataSource("c3p0_oracle");//oracle
//		dataSource = new ComboPooledDataSource("c3p0_sqlserver");//sqlserver
	}
	
	//dbcp
//	static{
//		Properties properties = new Properties();
//		InputStream inStream = 
//				JDBCUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
//		try {
//			properties.load(inStream);
//			dataSource = BasicDataSourceFactory.createDataSource(properties);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				inStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 获取数据库连接
	 * @Title: getConnection 
	 * @Description: TODO() 
	 * @throws Exception    设定文件
	 * @return Connection    返回类型 
	 */
	public static Connection getConnection() throws Exception{
		return dataSource.getConnection();
	}
	
	/**
	 * 处理数据库事务的提交操作
	 * @Title: commit 
	 * @Description: TODO() 
	 * @param connection    参数 
	 * @return void   返回类型
	 * @throws
	 */
	public static void commit(Connection connection) {
		if(connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理数据库事务的回滚操作
	 * @Title: rollback 
	 * @Description: TODO() 
	 * @param connection   参数
	 * @return void    返回类型
	 * @throws
	 */
	public static void rollback(Connection connection) {
		if(connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 事务开始
	 * @Title: beginTransaction 
	 * @Description: TODO() 
	 * @param connection   参数
	 * @return void   
	 * @throws
	 */
	public static void beginTransaction(Connection connection) {
		if(connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭ResultSet,Statement,Connection
	 * @Title: release 
	 * @Description: TODO() 
	 * @param statement
	 * @param connection    
	 * @throws
	 */
	public static void release(ResultSet rs,Statement statement,Connection connection) {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(connection != null) {
			try {
				//数据库连接池获取的Connection对象close()时
				//并不是真正的关闭，而是把连接还给连接池
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void release(Statement statement,Connection connection) {
		release(null,statement,connection);
	}
	public static void release(Connection connection) {
		release(null,null,connection);
	}
	/**
	 * 通用的跟新方法,包括insert,update,delete
	 * @Title: update 
	 * @Description: TODO() 
	 * @param sql     
	 * @param args    
	 */
	public static void update(Connection conn,String sql,Object ... args) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 通用的查询方法，查询结果封装成对象放在list中返回
	 * @Title: get 
	 * @Description: TODO(ͨ) 
	 * @param clazz       用来承接的类的class对象，即描述对象的类型
	 * @param sql        带占位符的sql语句
	 * @param args       填充占位符的可变参数
	 * @return List<T>    返回类型 
	 * @throws
	 */
	public static <T> List<T> get(Class<T> clazz,Connection conn,String sql,Object ... args ) {
		T entity = null;//clazz�����Ķ���
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> objs = new ArrayList<>();
		try {
			//1.得到preparedStatement，设置参数并执行sql
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//2.得到结果集resultSet
			rs = ps.executeQuery();
			
			//3.得到描述resultSet的元数据对象ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//4.处理结果集
			while(rs.next()) {
				Map<String,Object> values = new HashMap<>();//ÿ����¼��Ҫһ��map
				//1)每条记录放在一个map中，key为列的别名，value为该列的值ֵ
				for(int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnLabel(i + 1);
					Object columnValue = rs.getObject(i + 1);
					values.put(columnName, columnValue);
				}
				if(values.size() > 0) {
					//2)为每条记录创建一个承接对象
					entity = clazz.newInstance();
					//3)使用反射为对象的每个属性赋值ֵ
					for(Map.Entry<String, Object> entry:values.entrySet()) {
						ReflectionUtils.setFieldValue(entity, entry.getKey().toLowerCase(), entry.getValue());//改为小写字段名
					}
					//4)将已赋值的对象存入list中
					objs.add(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//5.返回封装为对象的集合
		return objs;
	}
	/**
	 * 查询一条记录，返回对象
	 * @Title: getOne 
	 * @Description: TODO() 
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return    
	 * @return T   
	 * @throws
	 */
	public static <T> T getOne(Class<T> clazz,Connection conn,String sql,Object ... args ) {
		List<T> list = new ArrayList<>();
		list = get(clazz,conn, sql, args);
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	public static <E> E getValue(Connection conn,String sql,Object ... args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.得到preparedStatement，设置参数并执行sql
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//2.得到结果集
			rs = ps.executeQuery();
			//3.处理结果集
			if(rs.next()) {
				//4.返回结果集中第一个值ֵ
				return (E) rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 通用的批量处理方法，批次为500
	 * @Title: batch 
	 * @Description: TODO(ͨ) 
	 * @param connection
	 * @param sql      table(name,age) values(?,?)
	 * @param args      names,ages   数组，例如String[] names = new String[]{"һ��","����","С��"} 
	 * @return    
	 * @return int   
	 * @throws
	 */
	public static int batch(Connection connection, String sql, Object[]... args) {
		PreparedStatement ps = null;
		ParameterMetaData pmd = null;
        try {
            ps = connection.prepareStatement(sql);
            pmd = ps.getParameterMetaData();
            int stmtCount = pmd.getParameterCount();
            System.out.println(stmtCount);
            for (int i = 0; i < args.length; i++) {
                int paramsCount = args == null ? 0 : args.length;
                if (stmtCount != paramsCount) {
                    throw new SQLException("Wrong number of parameters: expected "
                            + stmtCount + ", was given " + paramsCount);
                }
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        if(args == null) {
        	return 0;
        }
        
        int count = args[0].length;
        JDBCUtils.beginTransaction(connection);
        try {
        	for(int i = 0; i < count; i++ ) {
        		for(int j = 0; j < pmd.getParameterCount(); j++) {
        			ps.setObject(j + 1,args[j][i]);
        		}
        		ps.addBatch();
        		if((i + 1)% 500 == 0) {
        			ps.executeBatch();
                	ps.clearBatch();
        		}
        	}
        	if(count % 500 != 0 ) {
        		ps.executeBatch();
        		ps.clearBatch();
        	}
			JDBCUtils.commit(connection);
		} catch (Exception e) {
			JDBCUtils.rollback(connection);
			e.printStackTrace();
		}
        
      return count; 
               
    }
	
}
