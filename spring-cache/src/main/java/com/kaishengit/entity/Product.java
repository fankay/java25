package com.kaishengit.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    /**
     * 默认商品评论的数量
     */
    public static final Integer DEFAULT_COMMENT_NUM = 0;

    private Integer id;
    private String productName;
    private BigDecimal price;
    private BigDecimal marketPrice;
    private String place;
    private Integer commentNum;
    private Integer typeId;
    private ProductType productType;

    public Product() {
    }

    public Product(Integer id, String productName, BigDecimal price, BigDecimal marketPrice, String place, Integer commentNum, Integer typeId) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.marketPrice = marketPrice;
        this.place = place;
        this.commentNum = commentNum;
        this.typeId = typeId;
    }

    public Product(String productName, BigDecimal price, BigDecimal marketPrice, String place, Integer commentNum, Integer typeId) {
        this.productName = productName;
        this.price = price;
        this.marketPrice = marketPrice;
        this.place = place;
        this.commentNum = commentNum;
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", marketPrice=" + marketPrice +
                ", place='" + place + '\'' +
                ", commentNum=" + commentNum +
                ", typeId=" + typeId +
                '}';
    }
}
