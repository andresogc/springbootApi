package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

public interface ICustomerOrder {

    CustomerOrder saveOrder(CustomerOrder customerOrder);
    CustomerOrder updateOrder(CustomerOrder customerOrder);
    Date getOrderDate(Integer orderId);
    void deleteOrder(Integer orderId);
    CustomerOrder getOrder(Integer orderId);



}
