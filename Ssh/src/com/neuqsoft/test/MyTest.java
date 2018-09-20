package com.neuqsoft.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import com.neuqsoft.entity.User;
import com.neuqsoft.utils.HibernateUtils;

public class MyTest {

	@Test
	public void test1() {
		
		Session session = HibernateUtils.openSession();
		
		Transaction t = session.beginTransaction();
		
		User user = session.get(User.class, "1");
		
		System.out.println(user);
		
		t.commit();
		session.close();
	}

}
