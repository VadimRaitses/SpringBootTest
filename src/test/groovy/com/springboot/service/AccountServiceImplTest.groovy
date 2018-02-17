package com.springboot.service

import com.springboot.domain.Account
import com.springboot.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Shared
import spock.lang.Specification

import static java.util.Collections.emptyList

/**
 * Created by Developer on 2/17/2018.
 */
class AccountServiceImplTest extends Specification {

    private AccountServiceImpl classUnderTest
    private UserRepository userRepository = Mock()
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
    Account acc
    User user

    def "setup"() {
        acc = new Account(id: 1, email: "va", password: "abc")
        user = new User(acc.getEmail(), acc.getPassword(), emptyList());
        classUnderTest = new AccountServiceImpl(userRepository, bCryptPasswordEncoder)
    }


    @Shared
    def getAccount = {
        acc = new Account(id: 1, email: "va", password: "abc")
        return acc

    }
    @Shared
    def getUser = {
        user = new User(acc.getEmail(), acc.getPassword(), emptyList())
        return user

    }

    def "GetAccount"(mail, callback) {
        given:
        userRepository.findByEmail(mail) >> { return callback.call() }
        expect:
        assert classUnderTest.getAccount(mail).id == acc.id
        assert classUnderTest.getAccount(mail).email == acc.email
        where:
        mail | callback
        "a"  | getAccount
    }


    @Shared
    def nullAccount = {
        return null

    }
    @Shared
    def exceptionMessage = {
        return "a"

    }

    def "LoadAccountByEmail"(mail, callback, userCallack) {
        given:
        userRepository.findByEmail(mail) >> { return callback.call() }
        def actual
        try {
            actual = classUnderTest.loadAccountByEmail(mail)
        } catch (Exception ex) {
            actual = ex.getMessage()
        }
        expect:
        assert actual.toString() == userCallack.call().toString()
        where:
        mail | callback    | userCallack
        "a"  | getAccount  | getUser
        "a"  | nullAccount | exceptionMessage
    }

    def "AddAccount"() {
    }

    def "LoadUserByUsername"(mail, callback, userCallack) {
        given:
        userRepository.findByEmail(mail) >> { return callback.call() }
        def actual
        try {
            actual = classUnderTest.loadAccountByEmail(mail)
        } catch (Exception ex) {
            actual = ex.getMessage()
        }
        expect:
        assert actual.toString() == userCallack.call().toString()
        where:
        mail | callback    | userCallack
        "a"  | getAccount  | getUser
        "a"  | nullAccount | exceptionMessage
    }
}
