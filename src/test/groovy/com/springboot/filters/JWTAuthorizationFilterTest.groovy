package com.springboot.filters

import com.springboot.domain.Account
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Developer on 2/17/2018.
 */
class JWTAuthorizationFilterTest extends Specification {

    JWTAuthorizationFilterMock classUnderTest
    AuthenticationManager authManager = Mock()
    HttpServletRequest req = Mock()
    HttpServletResponse res = Mock()
    FilterChain chain = Mock()
    Account acc

    def "setup"() {
        acc = new Account(id: 1, email: "va", password: "abc")
        classUnderTest = new JWTAuthorizationFilterMock(authManager)
        chain.doFilter(req, res)
    }


    def doFilterInternal(authHeader, uriPath, state) {
        given:
        if (!state)
            chain.doFilter(req, res) >> { throw new RuntimeException("EX") }
        req.getHeader("Authorization") >> { return authHeader }
        def actual = classUnderTest.authorize(req, res, chain)
        expect:
        assert actual == state
        where:
        authHeader | uriPath          | state
        "token"    | "/token/"        | true
        "token"    | "/another path/" | true
        "Bearer "  | "/token/"        | true
        "Bearer "  | "/token/"        | false
    }

    class JWTAuthorizationFilterMock extends JWTAuthorizationFilter {

        JWTAuthorizationFilterMock(AuthenticationManager authManager) {
            super(authManager)
        }


        @Override
        UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
            return new UsernamePasswordAuthenticationToken(acc, null, new ArrayList<>())
        }
    }

}
