package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.Product;

public interface IProduct {
    Product getProduct(Integer productId);
}
