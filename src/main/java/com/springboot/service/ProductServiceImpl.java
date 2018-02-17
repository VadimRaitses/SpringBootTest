package com.springboot.service;

import com.springboot.domain.Product;
import com.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Developer on 2/13/2018.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public Iterable<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public Product read(long id) {
        return productRepository.findOne(id);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(long id) {
        productRepository.delete(id);
    }

    @Override
    public Product update(long id, Product update) {
        Product product = productRepository.findOne(id);
        if (update.getTitle() != null) {
            product.setTitle(update.getTitle());
        }
        return productRepository.save(product);
    }

}
