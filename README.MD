# Proyecto de Venta de Productos en Línea

Este proyecto, desarrollado por Juan Díaz Guerrero (Cód. 2020114035), se enfoca en la implementación de una solución de Backend para abordar una problemática común en la venta de productos en línea. El proyecto se desarrolla en el marco de la asignatura de microservicios.

El objetivo principal es cumplir con los requerimientos y especificaciones detallados en el documento `Ejercicio.DesarrolloAPI.pdf`.

La documentación completa del proyecto, incluidos los endpoints y las solicitudes API, se encuentra disponible en el siguiente enlace de Postman:
[Documentación de Postman](https://documenter.getpostman.com/view/15834105/2sA35G3hSp#50dd6757-03c7-42a0-8fd6-1d5cb9155b58) 🚀



## Descripción

El sistema consta de las siguientes entidades:

- **Producto**: Representa los artículos que están a la venta.
- **Cliente**: Representa los clientes que compran los productos.
- **Pedido**: Representa un pedido de compra realizado por un cliente.
- **ItemPedido**: Representa los artículos individuales dentro de un pedido.
- **Pago**: Representa los datos de pago de un pedido.
- **DetalleEnvio**: Representa la información de envío de un pedido.

## Diagrama relacional
[![Whats-App-Image-2024-03-26-at-00-33-29.jpg](https://i.postimg.cc/W37CgbkT/Whats-App-Image-2024-03-26-at-00-33-29.jpg)](https://postimg.cc/Q9HfrDmy)

## Tecnologías utilizadas

- Spring Boot
- Spring Data JPA
- PostgreSQL
- MapStruct
- Lombok
- JUnit
- Mockito
- Testcontainers

## Funcionalidades

- Capa de persistencia utilizando PostgreSQL.
- Implementación de métodos de búsqueda de datos en los repositorios.
- Pruebas de integración utilizando Spring Boot Test, JUnit y Testcontainers.
- Capa de servicios con lógica de negocio.
- Mapeo de DTOs utilizando MapStruct.
- Pruebas unitarias con cobertura del 100% en la capa de repositorios.
- Pruebas unitarias en la capa de servicios utilizando Mockito.
- Implementación de la capa de controladores con endpoints REST.


## Instalación y configuración

1. Clona este repositorio en tu máquina local.
2. Configura una instancia de PostgreSQL y crea una base de datos.
3. Actualiza las propiedades de conexión a la base de datos en el archivo `application.properties`.
4. Ejecuta la aplicación utilizando el comando `./gradlew bootRun`.

## Ejecución de los tests
1. Clonar el repositorio
2. Navegar dentro del profecto `cd TallerMicroservicioApi`
3. Debe tener instalador Docker Desktop y corriendolo
4. Ya puedes ejecutar todos los test con el comando `./gradlew test`

## API
Puedes consultar como usar la api con la documentación de postman 👉 [Aqui](https://documenter.getpostman.com/view/15834105/2sA35G3hSp#50dd6757-03c7-42a0-8fd6-1d5cb9155b58) 👈

### ClienteController
```js
//Objeto cliente:
{
"nombre": "Juan",
"email": "cliente1@example.com",
"direccion": "Calle 123, Ciudad A"
}
```

| Endpoint                       | Método   | Path                              | Variable     | Request Param | Request Body  |
|--------------------------------|----------|-----------------------------------|--------------|---------------|---------------|
| Obtener Cliente por ID         | GET      | /api/v1/customers/{id}            | id           |               |               |
| Obtener todos los Clientes     | GET      | /api/v1/customers                 |              |               |               |
| Obtener Cliente por Email      | GET      | /api/v1/customers/email/{email}   | email        |               |               |
| Obtener Clientes por Ciudad    | GET      | /api/v1/customers/city?cityName=  |              | cityName      |               |
| Crear Cliente                  | POST     | /api/v1/customers                 |              |               | Objeto Cliente|
| Actualizar Cliente por ID      | PUT      | /api/v1/customers/{id}            | id           |               | Objeto Cliente|
| Eliminar Cliente por ID        | DELETE   | /api/v1/customers/{id}            | id           |               |               |


### ProductoController

```js
//Objeto Producto
{
    "nombre": "Computadora",
    "price": 1555,
    "stock": 23
}
```

| Endpoint                          | Método | Path                            | Variable | Request Param | Request Body   |
|-----------------------------------|--------|---------------------------------|----------|---------------|----------------|
| Obtener Producto por ID           | GET    | /api/v1/products/{id}           | id       |               |                |
| Buscar Productos por Término      | GET    | /api/v1/products/search         |          | searchTerm    |                |
| Obtener Productos en Stock        | GET    | /api/v1/products/instock        |          |               |                |
| Obtener todos los Productos      | GET    | /api/v1/products                |          |               |                |
| Crear Producto                    | POST   | /api/v1/products                |          |               | Objeto Producto|
| Actualizar Producto por ID       | PUT    | /api/v1/products/{id}           | id       |               | Objeto Producto|
| Eliminar Producto por ID         | DELETE | /api/v1/products/{id}           | id       |               |                |


### PedidoController

```js
//Objeto Pedido
{
  "fechaPedido": "2023-05-15T10:30:00",
  "status": "PENDIENTE",
  "cliente": {
    "id": "58d60d2f-57d1-4b61-a4fe-455845d4f501" 
    }
}
```


| Endpoint                                   | Método | Path                                         | Variable | Request Param | Request Body  |
|--------------------------------------------|--------|----------------------------------------------|----------|---------------|---------------|
| Obtener Pedido por ID                      | GET    | /api/v1/orders/{id}                          | id       |               |               |
| Obtener todos los Pedidos                  | GET    | /api/v1/orders                               |          |               |               |
| Obtener Pedidos por ID de Cliente y Status | GET    | /api/v1/orders/customer/{customerId}?status= | customerId   |    status       |               |
| Obtener Pedidos por Rango de Fechas        | GET    | /api/v1/orders/date-range                    |          | startDate, endDate |               |
| Crear Pedido                               | POST   | /api/v1/orders                               |          |               | Objeto Pedido |
| Actualizar Pedido por ID                   | PUT    | /api/v1/orders/{id}                          | id       |               | Objeto Pedido |
| Eliminar Pedido por ID                     | DELETE | /api/v1/orders/{id}                          | id       |               |               |


## DetalleEnvioController

```js
//Objeto DetalleEnvio
{
    "direccion": "Santa Marta casa 2",
    "transportadora": "Colombia Express",
    "numeroGuia": 1213131,
    "pedido": {
    "id": "dd69229f-9159-465a-8440-7ff6ce849d24"
}
}

```

| Endpoint                                   | Método | Path                                   | Variable | Request Param | Request Body        |
|--------------------------------------------|--------|----------------------------------------|----------|---------------|---------------------|
| Obtener Detalle de Envío por ID           | GET    | /api/v1/shipping/{id}                 | id       |               |                     |
| Obtener todos los Detalles de Envío       | GET    | /api/v1/shipping                      |          |               |                     |
| Obtener Detalles de Envío por ID de Pedido| GET    | /api/v1/shipping/order/{orderId}      | orderId  |               |                     |
| Obtener Detalles de Envío por Transportista| GET   | /api/v1/shipping/carrier             |          | name          |                     |
| Crear Detalle de Envío                    | POST   | /api/v1/shipping                      |          |               | Objeto DetalleEnvio |
| Actualizar Detalle de Envío por ID        | PUT    | /api/v1/shipping/{id}                 | id       |               | Objeto DetalleEnvio |
| Eliminar Detalle de Envío por ID          | DELETE | /api/v1/shipping/{id}                 | id       |               |                     |


## PagoController

```js
//Objeto Pago
{
    "monto": 1000,
    "fechaPago": "2023-05-15T10:30:00",
    "metodoPago": "PAYPAL",
    "pedido": {
    "id": "dd69229f-9159-465a-8440-7ff6ce849d24"
    }
}
```

| Endpoint                                     | Método | Path                                         | Variable | Request Param | Request Body    |
|----------------------------------------------|--------|----------------------------------------------|----------|---------------|-----------------|
| Obtener Pago por ID                          | GET    | /api/v1/payments/{id}                        | id       |               |                 |
| Obtener todos los Pagos                      | GET    | /api/v1/payments                             |          |               |                 |
| Obtener Pagos por pedido Id y metodo de pago | GET    | /api/v1/payments/order/{orderId}?metodopago= | orderId  |      metodopago         |                 |
| Obtener Pagos por Rango de Fechas            | GET    | /api/v1/payments/date-range                  |          | startDate, endDate |              |
| Crear Pago                                   | POST   | /api/v1/payments                             |          |               | Objeto Payment  |
| Actualizar Pago por ID                       | PUT    | /api/v1/payments/{id}                        | id       |               | Objeto Payment  |
| Eliminar Pago por ID                         | DELETE | /api/v1/payments/{id}                        | id       |               |                 |


## ItemPedidoController

```js
//Objeto ItemPedido
{
    "cantidad": 2,
    "precio": 1000,
    "producto": {
    "id": "dd69229f-9159-465a-8440-7ff6ce849d24"
    },
    "pedido": {
        "id": "dd69229f-9159-465a-8440-7ff6ce849d24"
    }
}
```

| Endpoint                                      | Método | Path                                     | Variable | Request Param | Request Body       |
|-----------------------------------------------|--------|------------------------------------------|----------|---------------|--------------------|
| Obtener Item de Pedido por ID                | GET    | /api/v1/order-items/{id}                | id       |               |                    |
| Obtener todos los Items de Pedido            | GET    | /api/v1/order-items                     |          |               |                    |
| Obtener Items de Pedido por ID de Pedido     | GET    | /api/v1/order-items/order/{orderId}     | orderId  |               |                    |
| Obtener Items de Pedido por ID de Producto   | GET    | /api/v1/order-items/product/{productId}| productId|               |                    |
| Crear Item de Pedido                         | POST   | /api/v1/order-items                     |          |               | Objeto ItemPedido  |
| Actualizar Item de Pedido por ID             | PUT    | /api/v1/order-items/{id}                | id       |               | Objeto ItemPedido  |
| Eliminar Item de Pedido por ID               | DELETE | /api/v1/order-items/{id}                | id       |               |                    |
