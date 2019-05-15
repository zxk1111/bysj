package com.zxgs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zxgs.common.Constant;
import com.zxgs.common.Util;
import com.zxgs.web.GetHttpServletRequestWrapper;
import com.zxgs.web.LoginFilter;

@Component("SecurityFilter")
public class SecurityFilter extends OncePerRequestFilter implements Constant {
	LoginFilter loginFilter = new LoginFilter();

    protected enum UrlType {
        //后台管理
        BROWSER_MANAGE_BASE_URL,
        //浏览器应用端
        BROWSER_APPLY_BASE_URL,
        //文件下载
        LOAD_URL,
        //移动端
        APP_URL,
        //错误
        ERROR_URL;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 请求的uri
        String url = request.getRequestURI();
        //获得根路径
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "");
        UrlType flag = getUrlType(url);
        if (flag == UrlType.BROWSER_MANAGE_BASE_URL) {
            //后台管理系统PC端登陆处理
            if (request.getMethod().equalsIgnoreCase("get")) {
                request = new GetHttpServletRequestWrapper(request, "UTF-8");
            }
            filterChain.doFilter(request, response);

        } else if (flag == UrlType.LOAD_URL) {//
            filterChain.doFilter(request, response);
        } else if (flag == UrlType.BROWSER_APPLY_BASE_URL) {
            //后台管理系统PC端登陆处理
            if (request.getMethod().equalsIgnoreCase("get")) {
                request = new GetHttpServletRequestWrapper(request, "UTF-8");
            }
            System.out.println("test....正在测试...");
            String sesssionID = request.getSession().getId();
            System.out.println("sesssionID-------" + sesssionID);
            loginFilter.doFilter(request, response, filterChain);
        }
    }
    
 
    

    /**
     * 获取url类型
     *
     * @param url
     * @return
     */
    private UrlType getUrlType(String url) {
        UrlType type = UrlType.ERROR_URL;
        if (!Util.isNullString(url)) {
            //客户端接口
            type = UrlType.BROWSER_APPLY_BASE_URL;
        }
        return type;
    }
}
