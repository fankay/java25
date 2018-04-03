package com.kaishengit.entity;

public class Product {

    private Long id;
    private String name;
    private Long inventory;

    public Product() {
    }

    public Product(String name, Long inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
