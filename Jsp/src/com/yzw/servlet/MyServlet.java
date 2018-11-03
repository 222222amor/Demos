package com.yzw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yzw.entity.User;
import com.yzw.utils.JDBCUtils;

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
		User user = null;
		List<User> user1 = null;
		try {
			Connection conn = JDBCUtils.getConnection();
			out.print(conn);
			//JDBCUtils.beginTransaction(conn);
			
			String sql = "select id,username,nickname,password,createtime from C_USER";
			user = JDBCUtils.getOne(User.class, conn, sql);
			user1 = JDBCUtils.get(User.class, conn, sql);
			JDBCUtils.release(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("this is my first servlet. time:" + new Date());
		if(user!= null) {
			out.println(user);
		}
		out.println("list:" + user1.size());
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
