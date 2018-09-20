package com.neuqsoft.utils;
/*
 * 封装hierabteutils
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory sf;
	static {
		Configuration conf = new Configuration().configure();
		
		sf = conf.buildSessionFactory();
		
	}
	
	
	//获得session  获得全新的session
	public static Session openSession() {
		Session session = sf.openSession();
		return session;
	}
	
	//获得一个与线程绑定session
	public static Session getCurrentSession() {
		Session session = sf.getCurrentSession();
		return session;
	}
	
	public static void main(String[] args) {
		
		System.out.println(HibernateUtils.getCurrentSession());
	}
	
	
}
