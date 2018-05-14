package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfo<Product> findByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        return new PageInfo<>(productMapper.selectByExample(new ProductExample()));
    }

    @Override
    public void saveNewProduct(Product product) {
        productMapper.insertSelective(product);
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editProduct(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void deleteById(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }
}
