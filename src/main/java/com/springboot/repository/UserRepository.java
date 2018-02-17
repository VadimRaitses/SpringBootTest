package com.springboot.repository;

import com.springboot.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Developer on 2/13/2018.
 */

@Repository
public interface UserRepository extends CrudRepository<Account, Long> {


    Account findByEmailAndPassword(String email, String password);

    Account findByEmail(String email);

}
