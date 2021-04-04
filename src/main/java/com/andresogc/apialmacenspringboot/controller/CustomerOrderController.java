package com.andresogc.apialmacenspringboot.controller;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.model.Product;
import com.andresogc.apialmacenspringboot.model.User;
import com.andresogc.apialmacenspringboot.repository.ProductRepository;
import com.andresogc.apialmacenspringboot.repository.UserRepository;
import com.andresogc.apialmacenspringboot.service.ICustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class CustomerOrderController {

    @Autowired
    private ICustomerOrder customerOrderService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public CustomerOrder saveOrder(@RequestBody CustomerOrder customerOrder) throws Exception {
        //Obtener del request la lista de productos comprados por el cliente
        List<Product> products = customerOrder.getProducts();
        //variables para guardar los calculos del IVA y el valor total de la compra
        double subTotal = 0;
        double totalIVA;
        double totalToPay;
        double shippingValue=3000;

        //Obtener usuario que realiza la compra y verificar que este registrado en la base de datos
        User userRequest = customerOrder.getUser();
        Optional<User> user =  userRepository.findById(userRequest.getId());
        try{
            if (!user.isPresent()){ throw new Exception("El usuario debe iniciar sesion o registrase para poder continuar con la compra");}
            //For para guardar la suma de los precios de los productos en el subtotal (sin el IVA)
            for (Product p: products) {
                Integer id = p.getId();
                //obtener el precio del producto de la base de datos y calcular el monto segun la cantidad
                Optional<Product> optional = productRepository.findById(id);
                Product product = optional.get();
                subTotal += product.getPrice() * p.getCantidad();
                System.out.println();
            }
            try {
                //No se pueden hacer pedidos menores a $70.000.00
                if(subTotal < 70000){
                    throw new Exception("La compra debe ser igual o superior a $70.000.00");
                }

                //calculando el IVA
                totalIVA = subTotal * 0.19d;

                //Calculando el total a pagar incluido el IVA y envio
                totalToPay = subTotal + totalIVA;

                //validar que el pedido sea mayor o igual a 70000

               //pedidos mayores o iguales a $100.000.00 no tienen cobro de envio
                if (subTotal >= 100000) {
                    shippingValue=0;
                }
                //Sumar el valor del envio
                totalToPay += shippingValue;

                customerOrder.setShippingValue(shippingValue);
                customerOrder.setState(1);
                customerOrder.setSubtotal(subTotal);
                customerOrder.setTotalIVA(totalIVA);
                customerOrder.setTotalToPay(totalToPay);
                return customerOrderService.saveOrder(customerOrder);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
