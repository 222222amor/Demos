package com.yzw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletContextServlet
 */
/*@WebServlet(urlPatterns = "/servletcontext/test",initParams={
		@WebInitParam(name="click",value="0"),
		@WebInitParam(name="x",value="0")
	},loadOnStartup = 4)*/
public class ServletContextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletContextServlet() {
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
		String click = this.getServletConfig().getInitParameter("click");
		String clicks = this.getServletContext().getInitParameter("clicks");
		String people = this.getServletContext().getInitParameter("people");
		
		ServletContext servletContext = request.getServletContext();
		servletContext.setAttribute("servletContextValue", "1");
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/ticket");
		HttpSession session = request.getSession();
		session.setAttribute("sessionArr", "sessionValue");
		request.setAttribute("requestArr", "requestValue");
		dispatcher.forward(request, response);
//		out.print("servlet访问量：" + click + "<br/>");
//		out.print("点击量：" + clicks + "<br/>");
//		out.print("在线人数：" + people);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
