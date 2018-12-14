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
		// �����˵�url
		String[] noFilters = new String[] { "/login", "/register", "/forget" };
		// �����uri
		String uri = request.getRequestURI();
		// �Ƿ����
		boolean doFilter = true;
		for (String str : noFilters) {
			if (uri.contains(str)) {
				// ���uri�а��������˵�url���򲻹���
				doFilter = false;
				break;
			}
		}
		if (doFilter) {
			Object obj = request.getSession().getAttribute("user");
			if (null == obj) {
				boolean ajaxRequest = isAjaxRequest(request);
				if(ajaxRequest) {
					response.setCharacterEncoding("UTF-8");
					response.sendError(HttpStatus.UNAUTHORIZED.value(), "̫��ʱ��δ��������ˢ��");
					return;
				}
				response.sendRedirect("/login.html");
				return;
			}else {
				filterChain.doFilter(request, response);
			}
		}else {
			filterChain.doFilter(request, response);
		}

	}

	/**
	  * �ж��Ƿ�ΪAjax���� <������ϸ����>  @param request 59 * @return ��true, ��false
	  * @see [�ࡢ��#��������#��Ա] 61
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}
