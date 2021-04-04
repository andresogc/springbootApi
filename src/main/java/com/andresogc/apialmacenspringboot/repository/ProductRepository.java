package com.andresogc.apialmacenspringboot.repository;

import com.andresogc.apialmacenspringboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
