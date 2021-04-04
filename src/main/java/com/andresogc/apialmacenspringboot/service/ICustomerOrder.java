package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.model.Product;

import java.util.Date;
import java.util.Optional;


public interface ICustomerOrder {

    CustomerOrder saveOrder(CustomerOrder customerOrder);
    CustomerOrder updateOrder(CustomerOrder customerOrder);
    Date getOrderDate(Integer orderId);



}
