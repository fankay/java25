package com.kaishengit.dao;

import com.kaishengit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(Product product) {
        String sql = "insert into product(name,inventory) values(?,?)";
        jdbcTemplate.update(sql,product.getName(),product.getInventory());
    }

    public void batchSave(List<Product> products) {
        String sql = "insert into product(name,inventory) values(?,?)";

        List<Object[]> params = new ArrayList<Object[]>();
        for(Product product : products) {
            Object[] objects = new Object[2];
            objects[0] = product.getName();
            objects[1] = product.getInventory();
            params.add(objects);
        }

        jdbcTemplate.batchUpdate(sql,params);
    }

    public void update(Product product) {
        String sql = "update product set name = :name,inventory=:inventory where id = :id";

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("name",product.getName());
        params.put("inventory",product.getInventory());
        params.put("id",product.getId());

        namedParameterJdbcTemplate.update(sql,params);
    }

    public Product findById(Long id) {
        String sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Product>(Product.class),id);
    }

    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Product>(Product.class));
    }

    public Long countAll() {
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql,new SingleColumnRowMapper<Long>(Long.class));
    }

    public List<Map<String,Object>> findAllToMap() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql,new ColumnMapRowMapper());
    }

    /*private class ProductRowMapper implements RowMapper<Product> {
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setInventory(resultSet.getLong("inventory"));
            product.setName(resultSet.getString("name"));
            return product;
        }
    }*/

}
