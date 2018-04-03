package com.kaishengit.dao;

import com.kaishengit.BaseTestCase;
import com.kaishengit.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductDaoTest extends BaseTestCase {

    @Autowired
    private ProductDao productDao;

    @Test
    public void save() {
        Product product = new Product("水杯",100L);
        productDao.save(product);
    }

    @Test
    public void findById() {
        Product product = productDao.findById(57L);
        System.out.println(product);
        Assert.assertNotNull(product);
    }

    @Test
    public void findAll() {
        List<Product> productList = productDao.findAll();
        for(Product product : productList) {
            System.out.println(product);
        }
        Assert.assertEquals(productList.size(),6);
    }

    @Test
    public void countAll() {
        Long count = productDao.countAll();
        System.out.println("count:" + count);
        Assert.assertEquals(count.longValue(),3);
    }

    @Test
    public void findAllToMap() {
        List<Map<String,Object>> list = productDao.findAllToMap();
        for(Map<String,Object> map : list) {
            for(Map.Entry entry : map.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println("--------------------");
        }
    }
    @Test
    public void update() {
        Product product = productDao.findById(57L);
        product.setName("华为手机");

        productDao.update(product);

    }

    @Test
    public void batchSave() {
        List<Product> productList = Arrays.asList(
                new Product("机械键盘",10L),
                new Product("麦克风",5L),
                new Product("电脑音响",10L)
        );
        productDao.batchSave(productList);
    }

}