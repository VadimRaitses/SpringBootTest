package com.springboot.controllers;

import com.springboot.domain.Account;
import com.springboot.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Developer on 2/13/2018.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    final static Logger LOGGER = Logger.getLogger(AccountController.class);
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Account> getAccount() {
        LOGGER.info(":getAccount: get account from authentication");
        try {
            Authentication auth = getAuthentication();
            return new ResponseEntity<>(accountService.getAccount(auth.getName()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(":getAccount: get account from authentication" + e.getLocalizedMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
