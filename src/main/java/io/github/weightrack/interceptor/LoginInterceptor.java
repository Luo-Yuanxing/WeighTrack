package io.github.weightrack.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从Session中获取登录信息
        Object user = request.getSession().getAttribute("user");

        // 如果用户未登录，重定向到登录页面
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // 如果用户已登录，放行请求
        return true;
    }
}
