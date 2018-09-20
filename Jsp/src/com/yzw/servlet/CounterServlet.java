package com.yzw.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *网站计数器servlet，实现记录网站浏览量功能
 */
@WebServlet(urlPatterns = "/CounterServlet",loadOnStartup = 5)
public class CounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CounterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	/*@Override
	public void init(ServletConfig config) throws ServletException {
		//父类的init方法有处理和获取ServletContext对象的作用，重写一定要调用。
		super.init(config);
		//从history.txt中读取浏览量
		//获取history.txt发布后的真实路径
		String filePath = this.getServletContext().getRealPath("history.txt");
		
		//filePath = this.getServletContext().getContextPath() + "/history.txt";
		System.out.println("path:" + filePath);
		FileReader reader = null;
		BufferedReader bReader =null;
		
		try {
			reader = new FileReader(filePath);
			bReader = new BufferedReader(reader);
			
			String count = bReader.readLine();
			config.getServletContext().setAttribute("count", count);
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
	}*/
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
//		String count = this.getServletContext().getAttribute("count") + "";
//		request.getServletContext().setAttribute("count", Integer.parseInt(count) + 1 +"");
		response.getWriter().print("doget-访问量：" + request.getServletContext().getAttribute("count"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		doGet(request, response);
	}
	
	/*@Override
	public void destroy() {
		System.out.println("counterServlet---死亡");
		FileWriter writer = null;
		BufferedWriter bWriter = null;
		
		String filePath = this.getServletContext().getRealPath("history.txt");
		System.out.println(filePath);
		try {
			//true是append设为允许，即可以在原文件末端追加;false不允许追加
			writer = new FileWriter(filePath,false);
			bWriter = new BufferedWriter(writer);
			bWriter.write(this.getServletContext().getAttribute("count") + "");
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
		
		super.destroy();
	}*/

	
}
