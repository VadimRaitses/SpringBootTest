package com.springboot.repository;

import com.springboot.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Developer on 2/13/2018.
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findFirstByOrderByPostedOnDesc();

    List<Product> findAllByOrderByPostedOnDesc();

}
