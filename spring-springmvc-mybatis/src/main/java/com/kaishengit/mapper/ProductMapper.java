package com.kaishengit.mapper;

import com.kaishengit.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductMapper {


    Product findById(Integer id);

    void save(Product product);

    List<Product> findAll();

    List<Product> findAllWithType();

    void deleteById(Integer id);

    void update(Product product);

    List<Product> findAllWithTypeByQueryParam(Map<String, Object> queryParamMap);
}
