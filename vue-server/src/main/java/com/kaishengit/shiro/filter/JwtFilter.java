package com.kaishengit.shiro.filter;

import com.kaishengit.shiro.token.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends AccessControlFilter {

    private static final String  AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin",
                httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods",
                "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
            // 跨域时会首先发送一个option请求，这里给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //从请求头中获取header
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isEmpty(token)) {
            loginError(servletResponse,"请登录后再继续");
        } else {
            JwtToken jwtToken = new JwtToken(token);
            Subject subject = getSubject(servletRequest, servletResponse);
            try {
                subject.login(jwtToken);
                return true;
            } catch (AuthenticationException e) {
                e.printStackTrace();
                loginError(servletResponse,e.getMessage());
            }
        }
        return false;
    }

    /**
     * 验证失败
     * @param servletResponse
     * @param message
     */
    private void loginError(ServletResponse servletResponse, String message) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendError(401,message);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
}
