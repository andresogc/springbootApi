package com.andresogc.apialmacenspringboot;

import com.andresogc.apialmacenspringboot.model.Product;
import com.andresogc.apialmacenspringboot.model.User;
import com.andresogc.apialmacenspringboot.repository.ProductRepository;
import com.andresogc.apialmacenspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class ApialmacenSpringbootApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApialmacenSpringbootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        saveUser();
        saveAllProducts();
    }

    //Insertar usuario en la base de datos
    private void saveUser(){
        User user = new User();
        user.setDni("12345");
        user.setName("Pepito");
        user.setLastName("Melo");
        user.setAddress("carrera 11# 14-08");
        userRepository.save( user );
    }

    //insetar productos
    private void saveAllProducts(){
        List<Product> productos = getListProducts();
        productRepository.saveAll(productos);
    }

    //Crear lista de productos
    private List<Product> getListProducts(){
        List<Product> productsList = new LinkedList<Product>();

        Product product1 = new Product();
        product1.setName("Audifonos");
        product1.setDescription("Audifonos marca samsung");
        product1.setPrice(25000.00d);

        Product product2 = new Product();
        product2.setName("Memoria USB");
        product2.setDescription("Memoria USB de 16 gigas marcar kingston");
        product2.setPrice(28500.00d);

        Product product3 = new Product();
        product3.setName("Cargador genérico");
        product3.setDescription("Cargador de celular genérico");
        product3.setPrice(18900.00d);

        Product product4 = new Product();
        product4.setName("Teclado con luces");
        product4.setDescription("Teclado usb Genius para pc con luces");
        product4.setPrice(59900.00d);

        Product product5 = new Product();
        product5.setName("Mouse inalámbrico");
        product5.setDescription("Mouse USB Genius inalámbrico para pc");
        product5.setPrice(25000.00d);

        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);
        productsList.add(product4);
        productsList.add(product5);

        return productsList;
    }
}
