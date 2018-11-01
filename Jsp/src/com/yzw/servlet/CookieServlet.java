package com.yzw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yzw.listener.ServletContextInitListener;

/**
 * Servlet implementation class CookieServlet
 */
@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		
		Cookie cookie = new Cookie("author","yinzw");
		Cookie cookie1 = new Cookie("sex","man");
		Cookie cookie2 = new Cookie("ch",URLEncoder.encode("你好", StandardCharsets.UTF_8.name()));
		
		
		//设置cookie生命周期
		cookie.setMaxAge(60*60);//1h
		cookie1.setMaxAge(60*60);
		response.addCookie(cookie);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		out.println(cookie);
		if(session.isNew()) {
			out.println("第一次来");
		}else {
			out.println("回头客");
		}
		out.println(session);
		out.println("当前会话总数是：" + ServletContextInitListener.s);
		
	}

}
