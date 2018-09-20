package com.yzw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TicketServlet
 */
//@WebServlet("urlPatterns="/ticket",loadOnStartup=3")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int index = 1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		synchronized(this) {
			if(index > 0) {
				try {
					Thread.sleep(10*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				index--;
				System.out.println("剩余票数：" + index);
				out.println("您买到火车票了~");
			}else {
				out.println("火车票已售完。。。");
			}
		}
		out.print("<br/>");
		out.print("requestArr:" + request.getAttribute("requestArr"));
		out.print("<br/>");
		out.print("sessionArr:" + request.getSession().getAttribute("sessionArr"));
		out.print("<br/>");
		out.print("servletContextValue:" + this.getServletContext().getAttribute("servletContextValue"));
		out.print("<br/>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
