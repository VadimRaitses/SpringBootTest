package com.springboot.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.domain.Account;
import com.springboot.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Developer on 2/17/2018.
 */
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    final static Logger LOGGER = Logger.getLogger(JWTAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;
    private AccountService userDetailsService;

    public JWTAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, AccountService userDetailsService) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        try {
            Account creds = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), Account.class);
            String currentPassword = creds.getPassword();
            if (userDetailsService.getAccount(creds.getEmail()) == null)
                userDetailsService.addAccount(creds);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            currentPassword,
                            new ArrayList<>())
            );
        } catch (IOException e) {
            LOGGER.error(":attemptAuthentication:error during authorization" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(res, auth);
    }

}
