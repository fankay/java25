package com.kaishengit.service;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public Product findById(Integer id) {
        return productMapper.findById(id);
    }

}
