package com.yzw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
//@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
    	System.out.println("myservlet初始化");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("this is my first servlet. time:" + new Date());
		out.close();
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PrintWriter out = response.getWriter();
		out.println(username);
		out.print(password);
		out.println(request.getQueryString());
		out.println(request.getRemoteAddr());
		out.println(request.getRemoteHost());
		out.println(request.getRemotePort());
		out.println(request.getRemoteUser());
		out.println();
		out.println(request.getLocalAddr());
		out.println(request.getLocalName());
		out.println(request.getLocalPort());
		out.println("reqSessionId:" + request.getRequestedSessionId());
		out.println("URI:" + request.getRequestURI());
		out.println("url:" + request.getRequestURL().toString());
		out.println("contenttype:" + request.getContentType());
		out.println("getDateHeader:" + request.getDateHeader(username));
		out.println("getHeader:" + request.getHeader(username));
		out.close();
	}

}
