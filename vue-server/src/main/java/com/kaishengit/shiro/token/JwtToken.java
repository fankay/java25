package com.kaishengit.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Shiro中的JwtToken
 */
public class JwtToken implements AuthenticationToken {

    private String token;
    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
