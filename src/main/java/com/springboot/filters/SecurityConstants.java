package com.springboot.filters;

/**
 * Created by Developer on 2/16/2018.
 */
public class SecurityConstants {
    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000; // 10 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/token/";
}
