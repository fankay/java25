package com.kaishengit.service;

import com.kaishengit.BaseTestCase;
import com.kaishengit.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends BaseTestCase {

    @Autowired
    private ProductService productService;

    @Test
    public void saveProduct() throws Exception {
        List<Product> productList = Arrays.asList(
                new Product("投影仪",10L),
                new Product("鼠标",10L)
        );
        productService.saveProduct(productList);
    }
}