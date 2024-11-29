package io.github.weightrack.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class NoCacheInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, HttpServletResponse response, @NotNull Object handler) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return true;
    }
}
