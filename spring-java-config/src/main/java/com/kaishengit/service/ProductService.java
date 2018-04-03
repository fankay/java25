package com.kaishengit.service;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE,
                    propagation = Propagation.REQUIRED)
    public void saveProduct(List<Product> productList) throws Exception {
        for(Product product : productList) {
            productDao.save(product);
            throw new Exception("");
        }
        //send wechat
    }
}
