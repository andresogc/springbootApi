# springbootApi
Api de  almacén para realizar pagos en linea con Spring Boot.


# La api permite hacer lo siguiente:

- Hacer el registro de un pedido
- Editar el pedido si fue creado antes de 5 horas
- Eliminar pedido si fue creado antes de 12 horas, si psaron mas de 12 horas no se elimna el pedido pero si se cancela y al cliente se le factura el %10.

# Condiciones

- Pedido superiores a $100.000.00 no tiene cobro de envio
- Pedido inferior a $100.000.00  tiene cobro de envio
- Los pedidos deben ser mayor a $70.000.00
- Pedidos con mas de 5 horas de creados no se pueden editar
- Pedidos con mas de 12 horas de creados no se pueden eliminar, seran cancelados y se cobrara un %10 del pedido al cliente.
- Pedidos con menos de 5 horas de creados se pueden eliminar

#Estados que puede tener el pedido:
- 1 : Abierto
- 2 : Cancelado

# Para ver la consola de H2, ingresar a la url:

- http://localhost:8080/console


# Endpoint de solicitud para crear pedido: 

- http://localhost:8080/api/order/save
  
  El body de la solictud tiene el sigueinte ejemplo de json:
  
  {
    "state":1,
    "user":{
        "id":1,
        "name":"Pepito",
        "lastName":"Melo",
        "dni":"12345",
        "address":"carrera 11# 14-08"
    },
    "products": [
        {   
            "id":1,
            "name":"Audifonos",
            "description":"Audifonos marca samsung",
            "price": 25000.00,
            "amount":"4"
        },
        {   
            "id":2,
            "name":"Memoria USB",
            "description":"Memoria USB de 16 gigas marcar kingston",
            "price": 28500.00,
            "amount":"2"
        },
        {   
            "id":3,
            "name":"Cargador genérico",
            "description":"Cargador de celular genérico",
            "price": 18900.00,
            "amount":"1"
        },
        {   
            "id":4,
            "name":"Teclado con luces",
            "description":"Teclado usb Genius para pc con luces",
            "price": 59900.00,
            "amount":"1"
        },
        {   
            "id":5,
            "name":"Mouse inalámbrico",
            "description":"Mouse USB Genius inalámbrico para pc",
            "price": 25000.00,
            "amount":"3"
        }
    ]
}

Y como ejemplo de respuesta tenemos:

[
    {
        "id": 1,
        "subtotal": 310800.0,
        "totalIVA": 59052.0,
        "totalToPay": 369852.0,
        "shippingValue": 0.0,
        "state": 1,
        "created_at": "2021-04-05T02:07:32.548+00:00",
        "products": [
            {
                "id": 1,
                "name": "Audifonos",
                "description": "Audifonos marca samsung",
                "price": 25000.0,
                "amount": 4
            },
            {
                "id": 2,
                "name": "Memoria USB",
                "description": "Memoria USB de 16 gigas marcar kingston",
                "price": 28500.0,
                "amount": 2
            },
            {
                "id": 3,
                "name": "Cargador genérico",
                "description": "Cargador de celular genérico",
                "price": 18900.0,
                "amount": 1
            },
            {
                "id": 4,
                "name": "Teclado con luces",
                "description": "Teclado usb Genius para pc con luces",
                "price": 59900.0,
                "amount": 1
            },
            {
                "id": 5,
                "name": "Mouse inalámbrico",
                "description": "Mouse USB Genius inalámbrico para pc",
                "price": 25000.0,
                "amount": 3
            }
        ],
        "user": {
            "id": 1,
            "dni": "12345",
            "name": "Pepito",
            "lastName": "Melo",
            "address": "carrera 11# 14-08"
        }
    },
    "El pedido se registro con exito"
]




# Endpoint de solicitud para editar pedido: 

- http://localhost:8080/api/order/update
  
   El body de la solictud tiene el sigueinte ejemplo de json:
  
  {   
    "id":1,
    "state":1,
    "user":{
        "id":1,
        "name":"Pepito",
        "lastName":"Melo",
        "dni":"12345",
        "address":"carrera 11# 14-08"
    },
    "products": [
        {   
            "id":1,
            "name":"Audifonos",
            "description":"Audifonos marca samsung",
            "price": 25000.00,
            "amount":"1"
        },
        {   
            "id":2,
            "name":"Memoria USB",
            "description":"Memoria USB de 16 gigas marcar kingston",
            "price": 28500.00,
            "amount":"1"
        },
        {   
            "id":5,
            "name":"Mouse inalámbrico",
            "description":"Mouse USB Genius inalámbrico para pc",
            "price": 25000.00,
            "amount":"1"
        }
    ]
}



Y como ejemplo de respuesta tenemos:

[
    {
        "id": 1,
        "subtotal": 78500.0,
        "totalIVA": 14915.0,
        "totalToPay": 96415.0,
        "shippingValue": 3000.0,
        "state": 1,
        "created_at": "2021-04-05T02:07:37.026+00:00",
        "products": [
            {
                "id": 1,
                "name": "Audifonos",
                "description": "Audifonos marca samsung",
                "price": 25000.0,
                "amount": 1
            },
            {
                "id": 2,
                "name": "Memoria USB",
                "description": "Memoria USB de 16 gigas marcar kingston",
                "price": 28500.0,
                "amount": 1
            },
            {
                "id": 5,
                "name": "Mouse inalámbrico",
                "description": "Mouse USB Genius inalámbrico para pc",
                "price": 25000.0,
                "amount": 1
            }
        ],
        "user": {
            "id": 1,
            "dni": "12345",
            "name": "Pepito",
            "lastName": "Melo",
            "address": "carrera 11# 14-08"
        }
    },
    "El pedido se actualizó con exito"
]


# Endpoint de solicitud para eliminar o cancelar pedido pedido: 

- http://localhost:8080/api/order/delete/{id}
  
  Como ejemplo de respuesta tenemos:

[
    "El Pedido ha sido eliminado exitosamente"
]



# Otras respuestas:

- Si el usuario intenta crear una orden y no esta en la base de datos recibe la sigueinte respuesta:

[
    "El usuario debe iniciar sesion o registrase para poder continuar"
]


- Si han pasado mas de 12 horas y el usuario cancela un pedido:

[
    "El pedido por valor de $369852.0 se ha cancelado, sin embargo se hará un cobro del 10%, ya que han pasado mas de 12 horas desde que se solicito el pedido. El monto a cancelar es de: $36985.2"
]


- Si han pasado mas de 5 horas y el usuario intenta canelar un pedido:

[
    "Solo se puede editar el pedido si han trasncurrido menos de 5 horas desde su creación"
]

- Si el pedido esta en estado cancelado:

[
    "No se puede eliminar el pedido porque ha sido cancelado previamente"
]



