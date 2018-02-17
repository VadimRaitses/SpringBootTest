package com.springboot.service;

import com.springboot.domain.Product;

/**
 * Created by Developer on 2/13/2018.
 */
public interface ProductService {
    Iterable<Product> list();

    Product create(Product post);

    Product read(long id);

    Product update(long id, Product product);

    void delete(long id);
}
