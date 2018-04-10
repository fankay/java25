package com.kaishengit.service;

import com.kaishengit.entity.Product;

public interface ProductService {

    /**
     * 根据主键查找商品
     * @param id 主键
     * @return Product对象
     */
    Product findById(Integer id);
}
