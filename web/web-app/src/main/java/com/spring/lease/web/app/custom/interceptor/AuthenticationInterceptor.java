package com.spring.lease.web.app.custom.interceptor;

import com.spring.lease.common.context.LoginUser;
import com.spring.lease.common.context.LoginUserContext;
import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 5/6/2024 21:27 周三
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Access-Token");

        if (token == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_AUTH);
        } else {
            Claims claims = JwtUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);
            String username = claims.get("username", String.class);
            LoginUserContext.setLoginUser(new LoginUser(userId, username));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContext.clear();
    }
}
