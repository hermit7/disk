package com.ise.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 不过滤的url
		String[] noFilters = new String[] { "/login", "/register", "/forget", "/main", "/js","/css" };
		// 请求的uri
		String uri = request.getRequestURI();
		// 是否过滤
		boolean doFilter = true;
		for (String str : noFilters) {
			if (uri.contains(str)) {
				// 如果uri中包含不过滤的url，则不过滤
				doFilter = false;
				break;
			}
		}
		if (doFilter) {
			Object obj = request.getSession().getAttribute("user");
			if (null == obj) {
				boolean ajaxRequest = isAjaxRequest(request);
				if (ajaxRequest) {
					response.setCharacterEncoding("UTF-8");
					response.sendError(HttpStatus.UNAUTHORIZED.value(), "太长时间未操作，请刷新");
					return;
				}
				response.sendRedirect("/login.html");
				return;
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

	/**
	 * 判断是否为Ajax请求 <功能详细描述> @param request 59 * @return 是true, 否false
	 * 
	 * @see [类、类#方法、类#成员] 61
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}
