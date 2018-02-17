package com.springboot.controllers

import com.springboot.domain.Product
import com.springboot.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Developer on 2/17/2018.
 */
class ProductControllerTest extends Specification {

    private ProductService productService = Mock()
    private ProductController classUnderTest
    private Product entity
    private List<Product> entityList

    def "setup"() {
        classUnderTest = new ProductController(productService)
        entity = new Product(id: 1, title: "one", body: "body", price: 1.2)
        entityList = Arrays.asList(
                new Product(id: 1, title: "one", body: "body", price: 1.2),
                new Product(id: 2, title: "two", body: "body", price: 1.2),
                new Product(id: 3, title: "three", body: "body", price: 1.2))
    }


    @Shared
    def getSuccessMessageCallback = {
        return new ResponseEntity<>(entityList, HttpStatus.OK)
    }


    @Shared
    def getExceptionMessageCallback = {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }

    @Shared
    def getProductCallback = {
        return entityList
    }


    @Shared
    def getExceptionCallback = {
        throw RuntimeException("exception")
    }

    def "List"(responseCallback, mockCallback) {
        given:
        productService.list() >> { return mockCallback.call() }
        def actual = classUnderTest.list()
        expect:
        assert actual == responseCallback.call()
        where:
        responseCallback            | mockCallback
        getSuccessMessageCallback   | getProductCallback
        getExceptionMessageCallback | getExceptionCallback
    }


    @Shared
    def postSucessMessageCallback = {
        return new ResponseEntity<>(entity, HttpStatus.OK)
    }

    @Shared
    def postProductCallback = {
        return entity
    }

    def "Create"() {
        given:
        productService.create(entity) >> { return mockCallback.call() }
        def actual = classUnderTest.create(entity)
        expect:
        assert actual == responseCallback.call()
        where:
        responseCallback            | mockCallback
        postSucessMessageCallback   | postProductCallback
        getExceptionMessageCallback | getExceptionCallback
    }


    @Shared
    def readSucessMessageCallback = {
        return new ResponseEntity<>(entity, HttpStatus.OK)
    }

    @Shared
    def readProductCallback = {
        return entity
    }

    def "Read"() {
        given:
        productService.read(1) >> { return mockCallback.call() }
        def actual = classUnderTest.read(1)
        expect:
        assert actual == responseCallback.call()
        where:
        responseCallback            | mockCallback
        readSucessMessageCallback   | readProductCallback
        getExceptionMessageCallback | getExceptionCallback
    }

    def "Update"() {
        given:
        productService.update(1, entity) >> { return mockCallback.call() }
        def actual = classUnderTest.update(1, entity)
        expect:
        assert actual == responseCallback.call()
        where:
        responseCallback            | mockCallback
        postSucessMessageCallback   | postProductCallback
        getExceptionMessageCallback | getExceptionCallback
    }


    @Shared
    def deleteSucessMessageCallback = {
        return new ResponseEntity<>(null, HttpStatus.OK)
    }

    @Shared
    def deleteProductCallback = {
    }

    def "Delete"() {
        given:
        productService.delete(1) >> { return mockCallback.call() }
        def actual = classUnderTest.delete(1)
        expect:
        assert actual == responseCallback.call()
        where:
        responseCallback            | mockCallback
        deleteSucessMessageCallback | deleteProductCallback
        getExceptionMessageCallback | getExceptionCallback
    }
}
