package com.springboot.service;

import com.springboot.domain.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Developer on 2/13/2018.
 */
public interface AccountService extends UserDetailsService {

    Account getAccount(String email);

    void addAccount(Account account);

    UserDetails loadAccountByEmail(String email);

}
