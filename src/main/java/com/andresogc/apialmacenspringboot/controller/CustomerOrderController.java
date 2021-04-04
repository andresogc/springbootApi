package com.andresogc.apialmacenspringboot.controller;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.model.Product;
import com.andresogc.apialmacenspringboot.model.User;
import com.andresogc.apialmacenspringboot.repository.ProductRepository;
import com.andresogc.apialmacenspringboot.repository.UserRepository;
import com.andresogc.apialmacenspringboot.service.ICustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
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
    public List<Object> saveOrder(@RequestBody CustomerOrder customerOrder) throws Exception {

        //Obtener del request la lista de productos comprados por el cliente
        List<Product> products = customerOrder.getProducts();
        //variables para guardar los calculos del IVA y el valor total de la compra
        double subTotal;
        double totalIVA;
        double totalToPay;
        double shippingValue=3000;
        List<Object> response = new LinkedList<>();
        try{

            //Validar si usuario que viene en el request, quien es el que realiza la compra, existe en la base de datos
            Boolean userExist = getUser(customerOrder);

            if(userExist){

                //calcular el subtotal (sin el IVA)

                subTotal = calculateSubtotal(products);

                //calculando el IVA
                totalIVA = subTotal * 0.19d;
                //Calculando el total a pagar incluido el IVA y envio
                totalToPay = subTotal + totalIVA;

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
                CustomerOrder order =  customerOrderService.saveOrder(customerOrder);
                response.add(order);
                response.add("El pedido se registro con exito");
                System.out.println(response);
                return response;

            }
        }catch (Exception e){
            response.add(e.getMessage());

            return response;
        }
        return response;
    }



    @PutMapping("/update")
    public List<Object> updateOrder(@RequestBody CustomerOrder customerOrder){
        return null;
    }


    @DeleteMapping("/delete")
    public CustomerOrder deleteOrder(){
        return null;
    }

    //Conseguir el usuario que realizo la compra
    private Boolean getUser(CustomerOrder customerOrder) throws Exception {

        User userRequest = customerOrder.getUser();

        Optional<User> user =  userRepository.findById(userRequest.getId());

        if (!user.isPresent()){
            throw new Exception("El usuario debe iniciar sesion o registrase para poder continuar con la compra");
        }else{
            return true;
        }
    }

    //calcular el subtotal
    private Double calculateSubtotal(List<Product> products) throws Exception {

        double subTotal = 0d;
        for (Product p: products) {
            Integer id = p.getId();
            //obtener el precio del producto de la base de datos y calcular el monto segun la cantidad
            Optional<Product> optional = productRepository.findById(id);
            Product product = optional.get();
            subTotal += product.getPrice() * p.getAmount();
        }

        //No se pueden hacer pedidos menores a $70.000.00
        if(subTotal < 70000){
            throw new Exception("No fue posible registrar el pedido. La compra debe ser igual o superior a $70.000.00");
        }else {

            return subTotal;
        }
    }

}
