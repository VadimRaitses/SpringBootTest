package com.springboot.controllers

import com.springboot.domain.Account
import com.springboot.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Developer on 2/17/2018.
 */
class AccountControllerTest extends Specification {

    private AccountControllerMock classUnderTest
    private final AccountService accountService = Mock()
    private final Authentication auth = Mock()
    private final String accName = "vadim"
    private def acc

    def "setup"() {
        acc = new Account(id: 1, email: "va", password: "abc")
        classUnderTest = new AccountControllerMock(accountService)
    }

    @Shared
    def getSuccessMessageCallback = {
        return new ResponseEntity<>(acc, HttpStatus.OK)
    }

    @Shared
    def getExceptionMessageCallback = {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }

    @Shared
    def getAccountCallback = {
        return acc
    }
    @Shared
    def getExceptionCallback = {
        throw RuntimeException("exception")
    }

    def "GetAccount"(responseCallback, mockCallback) {

        given:
        accountService.getAccount(accName) >> { return mockCallback.call() }
        auth.getName() >> { return accName }
        def expected = classUnderTest.getAccount()
        expect:
        assert expected == responseCallback.call()
        where:
        responseCallback            | mockCallback
        getSuccessMessageCallback   | getAccountCallback
        getExceptionMessageCallback | getExceptionCallback
    }


    class AccountControllerMock extends AccountController {


        AccountControllerMock(AccountService accountService) {
            super(accountService)
        }

        @Override
        Authentication getAuthentication() {
            return auth
        }
    }
}
