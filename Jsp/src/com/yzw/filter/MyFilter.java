package com.yzw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 * @ClassName:  MyFilter   
 * @Description:TODO()   
 * @author: 尹志文 
 * @date:   2018年11月6日 下午10:37:02   
 *     
 * @Copyright: 2018 2583661140@qq.com All rights reserved. 
 *
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter{

	@Override
	public void destroy() {
		System.out.println("myFilter destroy ...");
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		
		String ip = req.getRemoteAddr();
		
		ServletContext context = req.getServletContext();
		String s = context.getServletContextName();
		
		System.out.println(ip + "访问了" + s);
		
		arg2.doFilter(req, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("myFilter init ...");
		
	}

}
