package com.springboot.filters;


import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.springboot.filters.SecurityConstants.HEADER_STRING;
import static com.springboot.filters.SecurityConstants.TOKEN_PREFIX;


/**
 * Created by Developer on 2/16/2018.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    final static Logger LOGGER = Logger.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        authorize(req, res, chain);
    }

    boolean authorize(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            String header = req.getHeader(HEADER_STRING);
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(req, res);
                return true;
            }
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
            return true;
        } catch (Exception e) {
            LOGGER.error("error during authorization" + e.getMessage());
            return false;
        }
    }


    UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        return TokenAuthenticationService.getAuthentication(request);

    }
}
