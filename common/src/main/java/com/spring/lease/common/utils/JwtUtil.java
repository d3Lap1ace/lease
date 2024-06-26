package com.spring.lease.common.utils;

import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.model.entity.SystemUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 4/6/2024 18:12 周二
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */


public class JwtUtil {
    private static long tokenExpiration = 60 * 600 * 1000L;

    // 密钥
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                .setSubject("USER_INFO")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).
                compact();

        return token;
    }


    public static Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().
                    setSigningKey(tokenSignKey).
                    build().parseClaimsJws(token);
            return claimsJws.getBody();

        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}
