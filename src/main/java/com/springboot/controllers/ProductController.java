package com.springboot.controllers;

import com.springboot.domain.Product;
import com.springboot.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Developer on 2/13/2018.
 */
@RestController
@RequestMapping("/entities")
public class ProductController {

    final static Logger LOGGER = Logger.getLogger(ProductController.class);
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> list() {

        LOGGER.info(":get:  get list of product");
        try {
            return new ResponseEntity<>(productService.list(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(":get:  get list of product " + e.getLocalizedMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product post) {
        LOGGER.info(":create:  product" + post);
        try {
            return new ResponseEntity<>(productService.create(post), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(":create: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> read(@PathVariable(value = "id") long id) {

        LOGGER.info(":get:  product by id" + id);
        try {
            return new ResponseEntity<>(productService.read(id), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(":get: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@PathVariable(value = "id") long id, @RequestBody Product product) {
        LOGGER.info(":update:  product by id" + id + " product " + product);
        try {
            return new ResponseEntity<>(productService.update(id, product), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(":update: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") int id) {
        try {
            LOGGER.info(":delete:  product by id" + id);
            productService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            LOGGER.error(":delete: " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}