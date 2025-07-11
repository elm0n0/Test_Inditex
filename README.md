# Aplicación de Consulta de Precios Inditex

Este proyecto es una API REST que permite consultar los precios de los productos de Inditex en una fecha y hora específicas, para una marca y producto dados, aplicando la tarifa correcta según su prioridad.

## 🚀 Cómo Empezar

Estas instrucciones te ayudarán a obtener una copia del proyecto en tu máquina local para propósitos de desarrollo y prueba.

### Prerrequisitos

Necesitas tener instalado lo siguiente:

* **Java Development Kit (JDK)**: Versión 21 o superior.
* **Maven**: Versión 3.9.9 o superior.

### Clonar el Repositorio

Primero, clona el repositorio a tu máquina local:

```bash
[git clone [https://github.com/user/repo.git](https://github.com/user/repo.git)](https://github.com/elm0n0/Test_Inditex.git)

Ejecutar la Aplicación

1. usando eclipse: Puedes importar el proyecto como un proyecto Maven en eclipse y ejecutar la clase JuanjoseApplication directamente.
- importas el proyecto como un proyecto Maven a eclipse
- click secundario sobre el proyecto -> Run As -> maven clean
- click secundario sobre el proyecto -> Run As -> maven install
- pulsa ctrl + shift + r y busca JuanjoseApplication.java
- click secundario sobre la clase JuanjoseApplication -> Run As -> java application

La aplicación se iniciará en http://localhost:8080 por defecto y el endpoint principal es: GET /v1/prices

he habilitado un swagger en http://localhost:8080/swagger-ui/index.html#/Prices%20API/getPrices donde se pueden realizar las pruebas


🧪 Ejecutar los Tests
El proyecto incluye tests unitarios y de integración para asegurar la calidad del código.

Para ejecutar todos los tests desde eclipse puedes hacer click secundario a la carpeta de set/test/java -> Run As -> JUnit Test.
Tambien puedes ver la cobertura del código haicendo click secundario a la carpeta de  set/test/java -> Coverage As -> JUnit Test.

tambien he preparado unas pruebas de integracion en Postman con los casos de uso solicitados y algunos mas.

💻 Especificaciones del Código
Arquitectura
El proyecto sigue una arquitectura hexagonal:

Capa de Dominio (domain): Contiene las entidades de negocio (Price) y las interfaces del mismo PriceRepository. Es el corazón de la aplicación y no tiene dependencias de otras capas.

Capa de Aplicación (application): Contiene la lógica de negocio central El Use Case GetPricesUseCaseImpl. hace uso de la interfaz dominio y orquesta la operacion de consulta.
tambien se ha incluido en esta capa las excepciones especificas del dominio de Price, para lanzar sus propias excepciones segun sea necesario.

Capa de Infraestructura (infraestructure): Contiene las implementaciones del dominio. Esto incluye:
Rest:
El controlador con la definicion de cómo se llama a la API y cómo responde la misma (PricesRestController).
El manejador de excepciones (PriceExceptionHandler).
La implementacion del repositorio, capa superior del reposityJPA.

Persistence:
Repositorios (PriceRepositoryImpl, PriceJpaRepository), entidades JPA (PriceJpaEntity) y mappers (PriceJpaMapper).
SEGUIR POR AQUí
Manejo de Errores
La API utiliza un @ControllerAdvice (PriceExceptionHandler) para proporcionar respuestas de error uniformes y significativas en formato JSON. Se manejan excepciones como:

PriceNotFoundException: Para recursos no encontrados (HTTP 404 Not Found), por ejemplo, si no hay precios para los criterios dados.

handleMethodArgumentTypeMismatch: Para errores de formato en los parámetros de la URL (HTTP 400 Bad Request), como cuando brandId no es un número.

handleMissingParams: Para parámetros obligatorios que faltan (HTTP 400 Bad Request).

Persistencia
Se utiliza Spring Data JPA para la interacción con la base de datos.

La lógica de búsqueda de precios (findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc) está definida en PriceJpaRepository, ordenando por prioridad de forma descendente para asegurar la aplicación de la tarifa correcta cuando hay solapamiento.

Se utiliza Optional.orElseThrow() para lanzar NoSuchElementException cuando no se encuentra ningún precio.

Mappers
Se utiliza un patrón Mapper (implementado con MapStruct si es el caso, o manualmente) (PriceJpaMapper) para convertir entre las entidades JPA (PriceJpaEntity) de la capa de infraestructura y los objetos de dominio (Price) de la capa de dominio. Esto desacopla las capas y evita que la lógica de negocio dependa de detalles de persistencia.

Cobertura de Tests
Tests Unitarios: Las clases de UseCase y Repository (cuando se mockea el JpaRepository) tienen tests unitarios que verifican su lógica interna y sus interacciones con las dependencias mockeadas.

Tests de Integración (Web): Los controladores (PricesRestControllerTest con @WebMvcTest) prueban la capa REST y el manejo de excepciones, asegurando que los endpoints responden correctamente.

Tests de Integración (Data JPA): (Si se incluyen, como discutimos) Prueban la interacción real con la base de datos y la lógica de las consultas JPA.
