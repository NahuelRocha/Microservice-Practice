Resumen del Proyecto de Introducción a los Microservicios

Objetivo del Proyecto:

El objetivo del proyecto es adentrarme en el desarrollo de microservicios utilizando Java y Spring Cloud para crear una aplicación escalable y segura.
Arquitectura de Microservicios:

El proyecto sigue una arquitectura de microservicios compuesta por varios componentes clave.
Componentes del Sistema:

Registro y Descubrimiento (Discovery):
Este componente actúa como un servidor de registro y descubrimiento.
Ayuda a los microservicios a registrarse y descubrirse entre sí para establecer comunicaciones entre ellos.

Gateway:
El gateway filtra todas las solicitudes y las redirige a los microservicios correspondientes.
Aplica filtros de seguridad y autenticación basados en Spring Security y JWT para proteger la aplicación 
negando el ingreso o la redireccion a cualquier solicitud fraudulenta.

Microservicio RegisterAndAuth:
Proporciona endpoints públicos para registrar y autenticar usuarios.
Utiliza Spring Security y JWT para la autenticación y gestion de acceso a endpoints segun roles.
Asigna el rol USER por defecto a los usuarios y lo incluye en el token JWT junto con el email único del usuario,
con el objetivo de luego extraer las reclamaciones del cuerpo del token y asi poder autenticar y gestionar el acceso a endpoints
en los otros microservicios.

Microservicio Products:
Implementa Spring Security y JWT para autenticar , gestionar permisos , extraer roles e username del usuario.
Gestiona la creación de productos, descripciones, SKU, categorías y el stock disponible.
Ofrece métodos para verificar stock, agregar stock, eliminar stock, etc.

Microservicio Orders:
Implementa Spring Security y JWT para autenticar , gestionar permisos , extraer roles e username del usuario.
Esta vez nos sera de gran ayuda ya que podremos asignar la orden al usuario correspondiento como asi tambien utilizar
el token en el cuerpo de las solicitudes que realizaremos luego mediante webflux al microservicio Products.
Gestiona las órdenes y sus elementos (orderitems) con una relación uno a muchos , de esta forma manejaremos la creacion de
nuevas ordenes mediante una lista de orderitems que contendra el sku de cada uno de los productos como asi tambien las cantidades a adquirir ,
para luego gestionar mediante una llamada asincrona la disponibilidad o no de los productos.
Utiliza WebFlux para consultar el stock en el microservicio de Products antes de crear órdenes.
Mantiene el stock actualizado en tiempo real al agregar o eliminar productos de las órdenes , o eliminar una orden completa.
Configura un productor de Kafka para enviar mensajes a un tópico específico.

Microservicio de Notificaciones:
Configura un consumidor de Kafka que procesa mensajes de órdenes y envía notificaciones correspondientes.
Tecnologías Utilizadas:

Se implementó Docker para contenerizar las bases de datos MySQL para cada microservicio, tambien Redis,
Prometheus, Grafana, OpenTelemetry, Zookeeper y Kafka.
Se aplicó balanceo de cargas en el Gateway para mejorar la escalabilidad.
Spring Security y JWT se usaron para la autenticación y seguridad.
Se integró Resilience4j para mejorar la resiliencia y la capacidad de recuperación mediante Circuit Breaker.
Se exploró el monitoreo y observabilidad utilizando herramientas como Zipkin, Actuator, Micrometer, OpenTelemetry, Prometheus y Grafana.
Estas herramientas se combinaron para administrar datos de telemetría, como rastreos, métricas y registros.
En resumen, tu proyecto de introducción a los microservicios aborda aspectos clave como la autenticación, la seguridad en capas profundas, la gestión de órdenes y productos, y la observabilidad. Además, utiliza tecnologías modernas y herramientas para asegurar la escalabilidad y la resiliencia del sistema.

## ENDPOINTS

### AuthController

| Method   | Route                                 | Description                                        |
| -------- | ------------------------------------- | -------------------------------------------------- |
| POST     | /api/auth/register                    | Register a new user and receive a JWT token       |
| POST     | /api/auth/authenticate                | Use the generated token to authenticate the user   |
| PUT      | /api/auth/promote-to-admin/{username} | Promote a user to an admin role                    |

### UserController

| Method   | Route                                 | Description                                        |
| -------- | ------------------------------------- | -------------------------------------------------- |
| GET      | /api/user/get-all?page=0&size=4       | Retrieve all users, with optional pagination       |
| GET      | /api/user/{id}                        | Get a user by ID                                   |
| DELETE   | /api/user/{id}                        | Delete a user by ID                                |
| PUT      | /api/user/{id}                        | Update a user by ID                                |
| GET      | /api/user/by-username/{username}      | Get a user by username                             |
| GET      | /api/user/by-name/{name}              | Get a user by name                                 |
| GET      | /api/user/by-email/{email}            | Get a user by email                                |

### PurchaseController

| Method   | Route                                 | Description                                        |
| -------- | ------------------------------------- | -------------------------------------------------- |
| POST     | /api/purchase/create                  | Create a new purchase                              |
| GET      | /api/purchase/{id}                    | Get a purchase by ID                               |
| GET      | /api/purchase/user/{username}         | Get purchases by username                          |
| DELETE   | /api/purchase/{id}                    | Delete a purchase by ID                            |
| PUT      | /api/purchase/{id}                    | Update a purchase by ID                            |

### OrderDetailsController

| Method   | Route                                 | Description                                        |
| -------- | ------------------------------------- | -------------------------------------------------- |
| POST     | /api/order-detail/create              | Create a new order detail                          |
| DELETE   | /api/order-detail/{id}                | Delete an order detail by ID                       |
| PUT      | /api/order-detail/update              | Update an order detail                             |
| GET      | /api/order-detail/by-purchase/{id}    | Get order details by purchase ID                  |

### ProductController

| Method   | Route                                 | Description                                        |
| -------- | ------------------------------------- | -------------------------------------------------- |
| POST     | /api/product/create                   | Create a new Product                               |
| GET      | /api/product/{id}                     | Get a Product by ID                                |
| PUT      | /api/product/{id}                     | Update a Product by ID                             |
| DEL      | /api/product/{id}                     | Delete a Product by ID                             |
| GET      | /api/product/all-products             | Get all Products                                   |

