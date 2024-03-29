# Proyecto de Venta de Productos en Línea

Este proyecto implementa una solución de Backend para una problemática típica de venta de productos en línea, siguiendo los requerimientos y especificaciones descritos en el documento `Ejercicio.DesarrolloAPI.pdf`.

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

### ClienteController
```json
//Objeto cliente:
{
"nombre": "Juan",
"email": "cliente1@example.com",
"direccion": "Calle 123, Ciudad A"
}
```

| Endpoint                                     | Método | Path                            | Variable     | Request Param | Request Body  |
|----------------------------------------------|--------|---------------------------------|--------------|---------------|---------------|
| Obtener Cliente por ID                      | GET    | /api/v1/customers/{id}         | id           |               |               |
| Obtener todos los Clientes                  | GET    | /api/v1/customers              |              |               |               |
| Obtener Cliente por Email                   | GET    | /api/v1/customers/email/{email}| email        |               |               |
| Obtener Clientes por Ciudad                 | GET    | /api/v1/customers/city         | cityName     |               |               |
| Crear Cliente                                | POST   | /api/v1/customers              |              |               | Objeto Cliente|
| Actualizar Cliente por ID                   | PUT    | /api/v1/customers/{id}         | id           |               | Objeto Cliente|
| Eliminar Cliente por ID                     | DELETE | /api/v1/customers/{id}         | id           |               |               |
