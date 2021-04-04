package com.andresogc.apialmacenspringboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    @ManyToMany (mappedBy = ("products"))
    private List<CustomerOrder> customerOrders = new ArrayList<>();
    @Transient
    private Integer cantidad;


}
