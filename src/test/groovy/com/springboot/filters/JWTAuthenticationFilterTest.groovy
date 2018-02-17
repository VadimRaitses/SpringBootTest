package com.springboot.filters

import com.springboot.domain.Account
import com.springboot.service.AccountService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets

/**
 * Created by Developer on 2/17/2018.
 */
class JWTAuthenticationFilterTest extends Specification {


    private JWTAuthenticationFilter classUnderTest
    private final AuthenticationManager authManager = Mock()
    private final AccountService userDetailsService = Mock()
    private final HttpServletRequest req = Mock()
    private final HttpServletResponse res = Mock()
    private final FilterChain chain = Mock()
    private final url = "/token/"
    private Account acc


    def "setup"() {
        acc = new Account(id: 1, email: "va", password: "abc")
        classUnderTest = new JWTAuthenticationFilter(url, authManager, userDetailsService)
    }

    @Shared
    def createInputStream = {
        def myString = "{\"email\":\"va\",\"password\": \"abc\"}"
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(myString.getBytes(StandardCharsets.UTF_8))

        ServletInputStream servletInputStream = new ServletInputStream() {

            int read() throws IOException {
                return byteArrayInputStream.read()
            }

            @Override
            boolean isFinished() {
                return false
            }

            @Override
            boolean isReady() {
                return false
            }

            @Override
            void setReadListener(ReadListener readListener) {

            }
        }

        return servletInputStream
    }


    @Shared
    def userAuth = {

        return new UsernamePasswordAuthenticationToken(
                "va",
                "ac",
                new ArrayList<>())

    }

    @Shared
    def auth = {
        return Authentication()
    }

    def "AttemptAuthentication"(servletCallback, userAuthCallback, authCallback, expected) {
        given:
        req.getInputStream() >> { return servletCallback.call() }
        authManager.authenticate(userAuthCallback.call()) >> { return authCallback.call() }
        def actual = classUnderTest.attemptAuthentication(req, res)
        expect:
        assert actual == expected
        where:
        servletCallback   | userAuthCallback | authCallback | expected
        createInputStream | userAuth         | auth         | null
    }

    def "SuccessfulAuthentication"() {
    }
}
