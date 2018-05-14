package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    @Override
    public void buyProduct(Integer id) throws RuntimeException {
        Product product = findById(id);

        try {
            Thread.sleep(new Random().nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(!product.isStart()) {
            throw new RuntimeException("你来早了，还没有开始");
        }
        if(product.isEnd()) {
            throw  new RuntimeException("抢购已结束");
        }
        /*if(product.getProductInventory() == 0) {
            throw  new RuntimeException("已售罄");
        }*/
        //同一个账号只能购买一件商品
        //过滤异常IP
        //！！！！不要超卖
        //减库存
        product.setProductInventory(product.getProductInventory() - 1);
        productMapper.updateByPrimaryKeySelective(product);
        System.out.println("购买成功....................................");
    }
}
