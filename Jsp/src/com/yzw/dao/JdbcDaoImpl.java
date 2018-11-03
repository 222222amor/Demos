package com.yzw.dao;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yzw.utils.JDBCUtils;
import com.yzw.utils.ReflectionUtils;

public class JdbcDaoImpl<T> implements DAO<T> {
	private QueryRunner queryRunner = null;
	private Class<T> type;

	public JdbcDaoImpl() {
		queryRunner = new QueryRunner();
		type = ReflectionUtils.getSuperGenericType(getClass());
	}
	@Override
	public void update(Connection connection, String sql, Object... args) {
		try {
			connection = JDBCUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getOne(Connection connection, String sql, Object... args) throws SQLException {
		T person = queryRunner.query(connection, sql, new BeanHandler<>(type), args);
		return person;
	}

	@Override
	public List<T> getAsList(Connection connection, String sql, Object... args) throws SQLException {
		List<T> persons = queryRunner.query(connection, sql, new BeanListHandler<>(type),args);
		return persons;
	}

	@Override
	public <E> E getValue(Connection connection, String sql, Object... args) throws SQLException {
		E value = (E) queryRunner.query(connection, sql, new ScalarHandler(),args);
		return value;
	}

	@Override
	public int batch(Connection connection, String sql, Object[]... args) {
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
