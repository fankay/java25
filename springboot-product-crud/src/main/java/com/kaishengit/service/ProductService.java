package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;

public interface ProductService {


    PageInfo<Product> findByPageNo(Integer pageNo);

    void saveNewProduct(Product product);

    Product findById(Integer id);

    void editProduct(Product product);

    void deleteById(Integer id);

    void buyProduct(Integer id) throws RuntimeException;
}
