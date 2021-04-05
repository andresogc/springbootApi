package com.andresogc.apialmacenspringboot.controller;

import com.andresogc.apialmacenspringboot.model.CustomerOrder;
import com.andresogc.apialmacenspringboot.model.Product;
import com.andresogc.apialmacenspringboot.model.User;
import com.andresogc.apialmacenspringboot.repository.ProductRepository;
import com.andresogc.apialmacenspringboot.repository.UserRepository;
import com.andresogc.apialmacenspringboot.service.ICustomerOrder;
import com.andresogc.apialmacenspringboot.service.IProduct;
import com.andresogc.apialmacenspringboot.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class CustomerOrderController {

    @Autowired
    private ICustomerOrder customerOrderService;
    @Autowired
    private IProduct productService;
    @Autowired
    private IUser userService;

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
        //Obtener del request la lista de productos comprados por el cliente
        List<Product> products = customerOrder.getProducts();
        //variables para guardar los calculos del IVA y el valor total de la compra
        double subTotal;
        double totalIVA;
        double totalToPay;
        double shippingValue=3000;
        Integer timeLimit = 5;
        List<Object> response = new LinkedList<>();
        try{
            Integer orderId =  customerOrder.getId();
            //validar que el pedido en DB este en estado 1 osea abierto para poder editarlo, si esta en estado 2 es porque esta cancelado
            CustomerOrder orderDB = customerOrderService.getOrder(orderId);
            if(orderDB.getState() == 2){throw new Exception("No se puede editar este pedido porque ha sido cancelado previamente");}
            //validar si se puede modificar el pedido dependiendo de las horas que hallan transcurrido desde la creacion del pedido
            Integer horas = calculateTimeDiff(orderId,timeLimit);
            if(horas > timeLimit ){throw new Exception("Solo se puede eliminar el pedido si han trasncurrido menos de 5 horas desde su creación");}
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
                CustomerOrder order =  customerOrderService.updateOrder(customerOrder);
                order.setProducts(products);
                response.add(order);
                response.add("El pedido se actualizó con exito");
                return response;

            }
        }catch (Exception e){
            response.add(e.getMessage());

            return response;
        }
        return response;
    }


    @DeleteMapping("/delete/{id}")
    public List<Object> deleteOrder(@PathVariable("id") int orderId) throws Exception {
        List<Object> response = new LinkedList<>();
        Integer timeLimit = 12;
        String mensaje="El Pedido ha sido eliminado exitosamente";
        CustomerOrder customerOrder = customerOrderService.getOrder(orderId);

        try{
            Integer horas = calculateTimeDiff(orderId,timeLimit);
            if(customerOrder.getState() == 2){throw new Exception("No se puede eliminar el pedido porque ha sido cancelado previamente");}
            if(horas > timeLimit ){

                Double totalToPay = customerOrder.getTotalToPay() * 0.10;
                mensaje = "El pedido por valor de $"+customerOrder.getTotalToPay()+" se ha eliminado, sin embargo se hará un cobro del 10%, ya que han pasado mas de 12 horas desde que se solicito el pedido. El monto a cancelar es de: $" + Math.round(totalToPay*100.0)/100.0;
                customerOrder.setState(2);
                customerOrder.setTotalToPay(totalToPay);
                customerOrderService.updateOrder(customerOrder);
                response.add(mensaje);
                return response;
            }else {
                customerOrderService.deleteOrder(orderId);
                response.add(mensaje);
                return response;
            }
        }catch (Exception e){
            response.add(e.getMessage());
            return response;
        }

    }

    //Conseguir el usuario que realizo la compra
    private Boolean getUser(CustomerOrder customerOrder) throws Exception {
        List<Object> response = new LinkedList<>();
        User userRequest = customerOrder.getUser();
        if(!userService.getUser(userRequest.getId())){
            throw new Exception("El usuario debe iniciar sesion o registrase para poder continuar");
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
            Product product = productService.getProduct(id);
            subTotal += product.getPrice() * p.getAmount();
        }

        //No se pueden hacer pedidos menores a $70.000.00
        if(subTotal < 70000){
            throw new Exception("No fue posible registrar el pedido. La compra debe ser igual o superior a $70.000.00");
        }else {

            return subTotal;
        }
    }

    //Obtener diferencia de horas entre dos fechas
    private Integer calculateTimeDiff(Integer orderId,Integer timeLimit)  {

        Date currentDate = new Date();
        Date orderCreated = customerOrderService.getOrderDate(orderId);

        int diferencia=(int) ((currentDate.getTime() - orderCreated.getTime())/1000);
        int horas=0;

        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
        }



        return horas;
    }

}
