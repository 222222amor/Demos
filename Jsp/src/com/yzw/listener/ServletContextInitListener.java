package com.yzw.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * servletContext监听器
 * @ClassName:  ServletContextInitListener   
 * @Description:TODO()   
 * @author: 尹志文 
 * @date:   2018年9月20日 下午8:56:24   
 *     
 * @Copyright: 2018 2583661140@qq.com All rights reserved. 
 *
 */
//@WebListener
public class ServletContextInitListener implements ServletContextListener, ServletContextAttributeListener,HttpSessionListener,ServletRequestListener {

 
    public ServletContextInitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
	 * <p>Title: attributeAdded</p>   
	 * <p>Description: </p>   
	 * @param arg0   
	 * @see javax.servlet.ServletContextAttributeListener#attributeAdded(javax.servlet.ServletContextAttributeEvent)
	 */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	FileWriter writer = null;
		BufferedWriter bWriter = null;
		
		String filePath = arg0.getServletContext().getRealPath("history.txt");
		System.out.println(filePath);
		try {
			//true是append设为允许，即可以在原文件末端追加;false不允许追加
			writer = new FileWriter(filePath,false);
			bWriter = new BufferedWriter(writer);
			bWriter.write(arg0.getServletContext().getAttribute("count") + "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	//从history.txt中读取浏览量
		//获取history.txt发布后的真实路径
		String filePath = arg0.getServletContext().getRealPath("history.txt");
		
		//filePath = this.getServletContext().getContextPath() + "/history.txt";
		System.out.println("path:" + filePath);
		FileReader reader = null;
		BufferedReader bReader =null;
		
		try {
			reader = new FileReader(filePath);
			bReader = new BufferedReader(reader);
			
			String count = bReader.readLine();
			arg0.getServletContext().setAttribute("count", count);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bReader.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		String count = session.getServletContext().getAttribute("count") + "";
		session.getServletContext().setAttribute("count", Integer.parseInt(count) + 1 +"");
		System.out.println("访问量：" + session.getServletContext().getAttribute("count"));
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext sc = arg0.getServletContext();
		ServletRequest res = arg0.getServletRequest();
		
		//if(res.getRemoteHost())
		String count = sc.getAttribute("count") + "";
		sc.setAttribute("count", Integer.parseInt(count) + 1);
		System.out.println("ip：" + res.getRemoteAddr() + ",访问量加一");
	}

	

	

	
}
