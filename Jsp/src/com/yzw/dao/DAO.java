package com.yzw.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @ClassName: DAO 
 * @Description: DAO接口 
 * @param T 处理的实体的类型
 */
public interface DAO<T> {
	/**
	 * 
	 * @Title: update 
	 * @Description: TODO(INSERT,UPDATE,DELETE) 
	 * @param connection 数据库连接
	 * @param sql     sql语句
	 * @param args    填充占位符的可变参数
	 * @return void  返回类型
	 * @throws
	 */
	void update(Connection connection,String sql,Object ... args);
	
	/**
	 * @throws SQLException 
	 * 
	 * @Title: getOne 
	 * @Description: 将一条记录以一个T对象返回
	 * @param connection
	 * @param sql
	 * @param args
	 * @return    
	 * @return T     
	 * @throws
	 */
	T getOne(Connection connection,String sql,Object ... args) throws SQLException;
	
	/**
	 * @throws SQLException 
	 * 
	 * @Title: getAsList 
	 * @Description: 多条记录以T类对象的集合返回
	 * @param connection
	 * @param sql
	 * @param args
	 * @return  
	 * @return List<T>    返回类型
	 * @throws
	 */
	List<T> getAsList(Connection connection,String sql,Object ... args) throws SQLException;
	
	/**
	 * @throws SQLException 
	 * 
	 * @Title: getValue 
	 * @Description: 获得一个具体的值，例如所有记录的条数
	 * @param connection
	 * @param sql
	 * @param args
	 * @return 
	 * @return E  
	 * @throws
	 */
	<E> E getValue(Connection connection,String sql,Object ... args) throws SQLException;
	
	/**
	 * @return 
	 * 
	 * @Title: batch 
	 * @Description: 批处理方法
	 * @param connection
	 * @param sql
	 * @param args   填充占位符的Object[]类型的可变参数，因为是批量处理每条记录
	 *       的占位符用一个Object[]表示，有很多条记录则表示为可变多个Object[]
	 * @return void    
	 * @throws
	 */
	int batch(Connection connection,String sql,Object[] ... args);
}
