package com.yzw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.yzw.utils.JDBCUtils;

/**
 * 
 * @ClassName: DAO 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 淼淼 
 * @date 2018年2月10日 下午11:50:44 
 *
 */
public class DAOPast {
	/**
	 * 
	 * @Title: update 
	 * @Description: TODO(使用PreparedStatement包装的通用跟新方法,包括INSERT,UPDATE,DELETE) 
	 * @param sql     带占位符的SQL语句
	 * @param args    不定参,传入占位符处的数据
	 */
	public static void update(String sql,Object ... args) {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCUtils.release(ps, conn);
		}
		
	}
	
	/**
	 * 
	 * @Title: get 
	 * @Description: TODO(通用的查询方法，查询结果封装成对象放在list中返回) 
	 * @param clazz       用来承接的类的class对象，即描述对象的类型
	 * @param sql        带占位符的sql
	 * @param args       填充占位符的可变参数
	 * @return List<T>    返回类型 
	 * @throws
	 */
	public static <T> List<T> get(Class<T> clazz,String sql,Object ... args ) {
		T entity = null;//clazz创建的对象
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> objs = new ArrayList<>();
		try {
			//1.获取数据库连接
			conn = JDBCUtils.getConnection();
			//2.得到prepareStatement,填充占位符
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//3.得到结果集resultSet
			rs = ps.executeQuery();
			
			//4.得到描述resultSet的元数据对象ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			
			//5.处理结果集
			while(rs.next()) {
				Map<String,Object> values = new HashMap<>();//每条记录需要一个map
				//1)每条记录存放在一个map中,其中key为列的别名,value为该列的值
				for(int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnLabel(i + 1);
					Object columnValue = rs.getObject(i + 1);
					values.put(columnName, columnValue);
				}
				if(values.size() > 0) {
					//2)为每条记录创建一个承接对象
					entity = clazz.newInstance();
					//3)使用反射为对象的每个属性赋值
					for(Map.Entry<String, Object> entry:values.entrySet()) {
						//ReflectionUtils.setFieldValue(entity, entry.getKey(), entry.getValue());
						BeanUtils.setProperty(entity, entry.getKey(), entry.getValue());
					}
					//4)将已赋值的对象存入list中
					objs.add(entity);
				}
			}
			//6.返回封装为对象的结果集
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//7.关闭数据库资源
			JDBCUtils.release(rs, ps, conn);
		}
		return objs;
	}
	/**
	 * 
	 * @Title: getOne 
	 * @Description: TODO(查询一条记录，返回对应的对象) 
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return    设定文件 
	 * @return T    返回类型 
	 * @throws
	 */
	public static <T> T getOne(Class<T> clazz,String sql,Object ... args ) {
		List<T> list = new ArrayList<>();
		list = get(clazz, sql, args);
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	/**
	 * 
	 * @Title: getForValue 
	 * @Description: TODO(查询结果为一个值) 
	 * @param sql
	 * @param args
	 * @return    设定文件 
	 * @return E    返回类型 
	 * @throws
	 */
	public static <E> E getForValue(String sql,Object ... args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取数据库连接
			conn = JDBCUtils.getConnection();
			//2.得到prepareStatement,填充占位符
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//3.得到结果集resultSet
			rs = ps.executeQuery();
			
			//4.处理结果集
			if(rs.next()) {
				return (E) rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//5.关闭数据库资源
			JDBCUtils.release(rs, ps, conn);
		}
		return null;
	}
	
	
}
