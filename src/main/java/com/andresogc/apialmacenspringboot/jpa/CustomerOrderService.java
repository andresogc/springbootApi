package com.andresogc.apialmacenspringboot.jpa;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.repository.CustomerOrderRepository;
import com.andresogc.apialmacenspringboot.service.ICustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService implements ICustomerOrder {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public CustomerOrder saveOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }


}
