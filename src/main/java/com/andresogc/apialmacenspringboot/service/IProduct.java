package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.Product;


import java.util.List;

public interface IProduct {
    Product getProduct(Integer productId);
    void saveAllProducts(List<Product> productos);
}
