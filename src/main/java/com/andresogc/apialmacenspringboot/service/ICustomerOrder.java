package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.model.Product;

import java.util.Optional;


public interface ICustomerOrder {

    CustomerOrder saveOrder(CustomerOrder customerOrder);


}
