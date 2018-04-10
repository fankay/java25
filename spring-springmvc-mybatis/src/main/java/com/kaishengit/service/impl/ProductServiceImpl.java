package com.kaishengit.service.impl;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    /**
     * 根据主键查找商品
     *
     * @param id 主键
     * @return Product对象
     */
    @Override
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }
}
