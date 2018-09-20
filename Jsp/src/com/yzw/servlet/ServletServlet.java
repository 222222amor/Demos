package com.yzw.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns="/servlet2",loadOnStartup=2)
public class ServletServlet implements Servlet {

	public ServletServlet() {
		System.out.println("初始化servlet2");
	}
	/**
	 * 从内存中清除
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("清除内存");
		
	}

	/**
	 * 得到servletConfig对象
	 */
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		System.out.println("servletconfig");
		return null;
	}

	/**
	 * 得到servlet配置信息
	 */
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		System.out.println("info,配置信息");
		return null;
	}

	/**
	 * 用于初始化servlet，把servlet装载到内存中
	 * 该函数只会调用一次
	 */
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("初始化servlet");
	}

	/**
	 * 服务函数，业务逻辑代码
	 * 该函数每次都会被调用
	 */
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		arg1.getWriter().println("servlet");
	}

}
