package com.andresogc.apialmacenspringboot.jpa;

import com.andresogc.apialmacenspringboot.model.Product;
import com.andresogc.apialmacenspringboot.repository.ProductRepository;
import com.andresogc.apialmacenspringboot.service.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProduct {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(Integer productId) {
        Optional<Product> optional = productRepository.findById(productId);
        Product product = optional.get();
        return product;
    }
}
