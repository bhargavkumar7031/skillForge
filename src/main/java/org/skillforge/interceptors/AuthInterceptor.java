package org.skillforge.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");

        System.out.println("in prehandle");
        System.out.println(token == null);
        System.out.println(token == "");

        if(token == null) {
            System.out.println("Authorization header required");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");

            response.getWriter().write("""
        {
          "timestamp": "%s",
          "status": 401,
          "error": "UNAUTHORIZED",
          "message": "Missing Authorization header",
          "path": "%s"
        }
        """.formatted(
                    java.time.Instant.now(),
                    request.getRequestURI()
            ));

            response.getWriter().flush();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
