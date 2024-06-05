package com.spring.lease.web.admin.custom.config;

import com.spring.lease.common.context.LoginUser;
import com.spring.lease.common.context.LoginUserContext;
import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 4/6/2024 18:26 周二
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("Access-Token");
        // token为空报错
        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        } else { // 验证token
            Claims claims = JwtUtil.parseToken(token);
            // 从jwt中获取用户id和用户名
            Long userId = claims.get("userId", Long.class);
            String username = claims.get("username", String.class);
            // 将loginUser对象跟当前线程绑定
            LoginUserContext.setLoginUser(new LoginUser(userId, username));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContext.clear();
    }
}
