package com.kaishengit.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtUtil {

    /**
     * token有效期为30天
     */
    private static final long TOKEN_EXPIRES = 1000 * 60 * 60 * 24 * 30L;

    /**
     * 创建Token
     * @param userId userID
     * @param password 做为密钥
     * @return
     */
    public String createToken(Integer userId,String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(password);
            return JWT.create().withClaim("userId",userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRES))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("创建JWT Token异常",e);
        }
    }

    /**
     * 从Token中获取账号的ID
     * @param token
     * @return
     */
    public Integer getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("userId").asInt();
    }


    /**
     * 验证Token
     * @param userId
     * @param password
     * @param token
     */
    public void verifyToken(Integer userId,String password,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(password);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("userId",userId).build();
            jwtVerifier.verify(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw  new RuntimeException("JWT Token不合法",e);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException("JWT Token已过期",e);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw  new RuntimeException("JWT Token不合法",e);
        }
    }


}
