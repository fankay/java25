package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 根据主键查找商品
     * @param id 主键
     * @return Product对象
     */
    Product findById(Integer id);

    /**
     * 查询所有的商品类型
     * @return
     */
    List<ProductType> findAllProductType();

    /**
     * 保存商品到数据库中
     * @param product
     */
    void saveProduct(Product product);

    /**
     * 根据页号查询当前页的商品列表
     * @param pageNo 当前页号，例如1，2，3
     * @return 当前页号的商品列表
     */
    PageInfo<Product> findAllProductByPageNo(Integer pageNo);

    /**
     * 根据ID删除商品
     * @param id 商品ID
     */
    void delProductById(Integer id);

    /**
     * 修改商品
     * @param product 商品对象
     */
    void updateProduct(Product product);

    /**
     * 根据当前页号和查询参数查询商品集合
     * @param pageNo 当前页号
     * @param queryParamMap 查询参数的Map
     * @return 当前页对象
     */
    PageInfo<Product> findAllProductByPageNoAndQueryParam(Integer pageNo, Map<String, Object> queryParamMap);
}
