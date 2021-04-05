package com.andresogc.apialmacenspringboot.jpa;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.repository.CustomerOrderRepository;
import com.andresogc.apialmacenspringboot.service.ICustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderService implements ICustomerOrder {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public CustomerOrder saveOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder updateOrder(CustomerOrder customerOrder) { return customerOrderRepository.save(customerOrder); }

    @Override
    public Date getOrderDate(Integer orderId) {
        Optional<CustomerOrder> optional = customerOrderRepository.findById(orderId);
        CustomerOrder order = optional.get();
        return order.getCreated_at();
    }

    @Override
    public void deleteOrder(Integer id) {
        customerOrderRepository.deleteById(id);
    }

    @Override
    public CustomerOrder getOrder(Integer orderId) {
        Optional<CustomerOrder> optional = customerOrderRepository.findById(orderId);
        CustomerOrder order = optional.get();
        return order;
    }


}
