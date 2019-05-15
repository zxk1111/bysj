package com.zxgs.web;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.zxgs.common.Constant;
import com.zxgs.entity.UserInfo;

/**
 * 
 */
public class LoginFilter implements Filter {
	protected static ObjectMapper mapper = new ObjectMapper();
	protected static JsonFactory cqzhsqpro = mapper.getJsonFactory();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String contextPath = request.getContextPath();
		String url = request.getRequestURI();
		url = url.replace(contextPath, "");
		UserInfo sysUser = null;
		//将特定的几个接口放出
		if (!url.contains("/sys/user/login") 
				&& !url.contains("/sys/user/register")&& !url.contains("/sys/user/getOtpCode")) {
			sysUser =(UserInfo)request.getSession().getAttribute(Constant.SESSION_SYS_USER);
			
			if (sysUser == null) {//没有登录
				System.out.println("请登录!");
				HashMap< String, String> re = new HashMap<>();
				re.put("code", "400");
				writeJSON(response,re);
//				response.sendRedirect(contextPath + "/login.jsp");
				return;
			}else{
				System.out.println("用户为:"+sysUser.getUserName());
			}
		}
		if (request.getMethod().equalsIgnoreCase("get")) {
			request = new GetHttpServletRequestWrapper(request, "UTF-8");
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年4月9日 上午10:54:52
	 * @param response
	 * @param string
	 */
	 protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		    response.setContentType("text/html;charset=utf-8");
		    JsonGenerator responseJsonGenerator = cqzhsqpro.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		    responseJsonGenerator.writeObject(obj);
		  }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
