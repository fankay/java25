package com.kaishengit.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("Hello,Filter");
        //RequestContext封装了HttpServletRequest和HttpServletResponse
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取HttpServletRequest对象
        HttpServletRequest request = requestContext.getRequest();
        String accessToken = request.getParameter("accessToken");
        if(accessToken == null || "".equalsIgnoreCase(accessToken)) {
            //拒绝路由
            requestContext.setSendZuulResponse(false);
            //给出401状态码
            requestContext.setResponseStatusCode(401);
        }
        return null;
    }
}
