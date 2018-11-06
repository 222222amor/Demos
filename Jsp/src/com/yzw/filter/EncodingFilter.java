package com.yzw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 编码过滤器
 * @ClassName:  EncodingFilter   
 * @Description:TODO()   
 * @author: 尹志文 
 * @date:   2018年11月7日 上午12:06:18   
 *     
 * @Copyright: 2018 2583661140@qq.com All rights reserved. 
 *
 */
@WebFilter(filterName = "encodingFilter",urlPatterns="/*",
initParams={
		@WebInitParam(name="encoding",value="utf-8")
	}
		)
public class EncodingFilter implements Filter{

	private String encoding;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		arg1.setCharacterEncoding(encoding);
		
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		encoding = arg0.getInitParameter(encoding);
		
	}

}
