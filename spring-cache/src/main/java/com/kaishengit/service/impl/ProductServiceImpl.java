package com.kaishengit.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductType;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.mapper.ProductTypeMapper;
import com.kaishengit.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    //@Autowired
    //private JedisPool jedisPool;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void initCache() {
        Cache cache = cacheManager.getCache("products");
        Product product = productMapper.findById(30911);
        cache.put(30911,product);
        logger.info("init {} product in cache ",30911);
    }


    /**
     * 根据主键查找商品
     *
     * @param id 主键
     * @return Product对象
     */
    @Override
    @Cacheable
    public Product findById(Integer id) {
        /*final String KEY = "product:"+id;
        Product product = null;

        Jedis jedis = jedisPool.getResource();
        //1.查询redis中是否存在该键
        if(jedis.exists(KEY)) {
            String json = jedis.get(KEY);
            product = new Gson().fromJson(json,Product.class);
            logger.info("find Product {} from Redis",id);
        } else {
            product = productMapper.findById(id);
            if(product != null) {
                String json = new Gson().toJson(product);
                jedis.set(KEY,json);
            }
            logger.info("find Product {} from MySQL",id);
        }
        jedis.close();
        return product;*/
        return productMapper.findById(id);
    }

    /**
     * 查询所有的商品类型
     *
     * @return
     */
    @Override
    public List<ProductType> findAllProductType() {
        return productTypeMapper.findAll();
    }

    /**
     * 保存商品到数据库中
     *
     * @param product
     */
    @Override
    public void saveProduct(Product product) {
        //默认商品评论数量为0
        product.setCommentNum(Product.DEFAULT_COMMENT_NUM);

        productMapper.save(product);
        logger.info("保存商品 {}",product);
    }

    /**
     * 根据页号查询当前页的商品列表
     *
     * @param pageNo 当前页号，例如1，2，3
     * @return 当前页号的商品列表
     */
    @Override
    public PageInfo<Product> findAllProductByPageNo(Integer pageNo) {

        PageHelper.startPage(pageNo,20);
        List<Product> productList = productMapper.findAllWithType();

        return new PageInfo<>(productList);
    }

    /**
     * 根据ID删除商品
     *
     * @param id 商品ID
     */
    @Override
    @CacheEvict
    public void delProductById(Integer id) {
        Product product = findById(id);
        if(product != null) {
            productMapper.deleteById(id);
            logger.info("删除商品 {}",product);
        }
    }

    /**
     * 修改商品
     *
     * @param product 商品对象
     */
    @Override
    @CacheEvict(key = "#product.id")
    public void updateProduct(Product product) {
        productMapper.update(product);
        logger.info("修改商品 {}",product);
    }

    /**
     * 根据当前页号和查询参数查询商品集合
     *
     * @param pageNo        当前页号
     * @param queryParamMap 查询参数的Map
     * @return 当前页对象
     */
    @Override
    public PageInfo<Product> findAllProductByPageNoAndQueryParam(Integer pageNo, Map<String, Object> queryParamMap) {
        PageHelper.startPage(pageNo,10);
        List<Product> productList = productMapper.findAllWithTypeByQueryParam(queryParamMap);

        return new PageInfo<>(productList);
    }
}
