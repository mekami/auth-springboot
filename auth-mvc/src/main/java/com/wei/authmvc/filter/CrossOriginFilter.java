package com.wei.authmvc.filter;

import com.wei.authmvc.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 静态资源允许跨域处理，Nginx可在配置文件中指定
 * 
 * @author Joe
 */
public class CrossOriginFilter implements Filter {


	public void init(FilterConfig filterConfig) throws ServletException {
	}


	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		String acrh = httpRequest.getHeader("Access-Control-Request-Headers");
		if (StringUtils.isNotBlank(acrh)) {
			httpResponse.addHeader("Access-Control-Allow-Headers", acrh);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}


	public void destroy() {
	}
}