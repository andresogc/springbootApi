package com.andresogc.apialmacenspringboot.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double subtotal;
    private double totalIVA;
    private double totalToPay;
    private double shippingValue;
    private Integer state;
    private Date created_at= new Date();
    @ManyToMany
    @JoinTable(
            name="product_detail",
            joinColumns = @JoinColumn(name = "custormerOrder_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



}
