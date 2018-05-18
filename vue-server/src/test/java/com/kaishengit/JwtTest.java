package com.kaishengit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaishengit.util.JwtUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtTest {

    private JwtUtil jwtUtil = new JwtUtil();

    @Test
    public void createToken() throws UnsupportedEncodingException {

        System.out.println(jwtUtil.createToken(1001,"123123"));

        /*Algorithm algorithm = Algorithm.HMAC384("123123");
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis()+1000))
                .withClaim("userId",1000)
                .sign(algorithm);
        System.out.println(token);*/
    }

    @Test
    public void verifyToken() throws UnsupportedEncodingException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJleHAiOjE1MjkyMDA3NDUsInVzZXJJZCI6MTAwMX0.GgYTyLQ6mRUKK2KDuiw7XoWO9QPxEJs1RtTD54uhaMw8v2rbucEv8hqDmYVmAZnj";
        jwtUtil.verifyToken(1001,"123123",token);


        /*String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJleHAiOjE1MjY2MDc3MjIsInVzZXJJZCI6MTAwMH0._efDZADJ6LzFhfui_iHpyxywPNaCffaPDsjeoAG97xGO3TXKy7LLkGhI68ZskSS8";
        Algorithm algorithm = Algorithm.HMAC384("123123");
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        jwtVerifier.verify(token);
        System.out.println("ok");*/
    }

    @Test
    public void getUserId() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJleHAiOjE1MjkyMDA3NDUsInVzZXJJZCI6MTAwMX0.GgYTyLQ6mRUKK2KDuiw7XoWO9QPxEJs1RtTD54uhaMw8v2rbucEv8hqDmYVmAZnj";
        Integer id = jwtUtil.getUserIdFromToken(token);
        System.out.println(id);
        /*String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJleHAiOjE1MjY2MDc3MjIsInVzZXJJZCI6MTAwMH0._efDZADJ6LzFhfui_iHpyxywPNaCffaPDsjeoAG97xGO3TXKy7LLkGhI68ZskSS8";
        DecodedJWT decodedJWT = JWT.decode(token);
        long id = decodedJWT.getClaim("userId").asLong();
        System.out.println(id);*/
    }

}
