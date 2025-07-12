# **Aplicación de Consulta de Precios Inditex**

Este proyecto es una API REST que permite consultar los precios de los productos de Inditex en una fecha y hora específicas, para una marca y producto dados, aplicando la tarifa correcta según su prioridad.

## **Cómo Empezar**

Estas instrucciones te ayudarán a obtener una copia del proyecto en tu máquina local para propósitos de desarrollo y prueba.

### **Prerrequisitos**

Necesitas tener instalado lo siguiente:

* **Java Development Kit (JDK)**: Versión 21 o superior.
* **Maven**: Versión 3.9.9 o superior.

---

### **Clonar el Repositorio**

Primero, clona el repositorio a tu máquina local:

```bash
git clone https://github.com/elm0n0/Test_Inditex
```
---
### **Ejecutar la Aplicación**
Puedes ejecutar la aplicación Spring Boot de varias maneras, pero vamos a explicar la forma de uso desde el IDE eclipse:

Para ejecutar la aplicación desde Eclipse:

* Importa el proyecto como un proyecto Maven en Eclipse.

* Haz clic derecho sobre el proyecto en el explorador de paquetes, selecciona Run As > Maven clean.

* Haz clic derecho sobre el proyecto en el explorador de paquetes, selecciona Run As > Maven install.

* Presiona Ctrl + Shift + R y busca JuanjoseApplication.java.

* Haz clic derecho sobre la clase JuanjoseApplication > Run As > Java Application.

* La aplicación se iniciará en http://localhost:8080 por defecto.

### Endpoint Principal:

El endpoint principal para consultar precios es: GET: **/v1/prices**

#### **Swagger UI**
He habilitado **Swagger UI** para facilitar las pruebas. Puedes acceder a ella en:

http://localhost:8080/swagger-ui/index.html#/Prices%20API/getPrices

---

### **Ejecutar los Tests**
El proyecto incluye tests unitarios y de integración para asegurar la calidad del código.

#### **Ejecutar Tests desde Eclipse**
Para ejecutar los tests desde Eclipse:

* Haz clic derecho en la carpeta **src/test/java** de tu proyecto.

* Selecciona Run As > JUnit Test.
---
#### **Ver Cobertura del Código**
Para ver la cobertura del código desde Eclipse:

* Haz clic derecho en la carpeta **src/test/java**.

* Selecciona Coverage As > JUnit Test.
---
#### **Pruebas de Integración con Postman**
También he preparado un conjunto de pruebas de integración en **Postman** con los casos de uso solicitados y algunos adicionales para validar el comportamiento de la API.

Se puede obtener de la raiz del proyecto el archivo **Inditex_pruebas.postman_collection.json**

el cual se puede importar a postman y probar directamente desde allí.

---
### **Especificaciones del Código**
#### Arquitectura
El proyecto sigue una arquitectura hexagonal para separar las responsabilidades y mejorar la mantenibilidad y testeabilidad. Sus capas principales son:

Capa de Dominio (domain): Contiene la entidad de negocio (Price) y la interfaz PriceRepository.

Capa de Aplicación (application): Contiene la lógica de negocio central, implementada por el UseCase (GetPricesUseCaseImpl). Este UseCase hace uso de la interfaz de dominio (PriceRepository) y orquesta la operación de consulta.

Capa de Infraestructura (infraestructure): es nuestro servicio REST, el que se expone como ruta accesible de http y su estructura interna, esta dividida en 2 partes:

##### **Rest** que es la estructura del servicio que se expone a http:

El controlador que define cómo se llama a la API y cómo responde la misma (PricesRestController).

El manejador de excepciones (PriceExceptionHandler).

La implementación del repositorio, que actúa como una capa superior al repositorio JPA.

Mapper: (PriceRestMapper)

##### **Persistence** que es la capa mas interna, la que conecta directamente con la bbdd (h2):

Repositorios (PriceRepositoryImpl, PriceJpaRepository).

Entidades JPA (PriceJpaEntity).

Mappers (PriceJpaMapper).

##### **Manejo de Errores**
La API utiliza @ControllerAdvice (PriceExceptionHandler) para proporcionar respuestas de error uniformes y significativas en formato JSON. Se manejan las siguientes excepciones:

handlePriceException: Muestra un mensaje de error cuando no se obtiene ningún precio para el triplete (brandId, productId, fecha de búsqueda).

MethodArgumentTypeMismatchException: Para errores de formato en los parámetros de la URL (HTTP 400 Bad Request), como cuando brandId no es un número o una fecha mal formada, indicando el formato correcto de los parámetros.

handleMissingParams: Para cuando no se ha informado algún parámetro obligatorio, indicando qué parámetro se espera (HTTP 400 Bad Request).

##### **Persistencia**
Se utiliza Spring Data JPA para la interacción con la base de datos.

La lógica de búsqueda de precios (findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc) está definida en PriceJpaRepository. Esta consulta ordena por prioridad de forma descendente para asegurar la aplicación de la tarifa correcta cuando hay solapamiento de fechas.

Se utiliza Optional.orElseThrow() para lanzar NoSuchElementException cuando no se encuentra ningún precio, la cual es capturada en el controlador para lanzar una excepción PriceNotFoundException específica del dominio.

##### **Mappers**
Se utiliza un patrón Mapper, implementado con MapStruct (PriceJpaMapper), para convertir entre las entidades JPA (PriceJpaEntity) de la capa de infraestructura y los objetos de dominio (Price). Esto desacopla las capas y evita que la lógica de negocio dependa de detalles de persistencia.

##### **Cobertura de Tests**
El proyecto cuenta con una cobertura de tests estratégica:

Tests de Integración (Web): Los controladores (PricesRestControllerTest con @WebMvcTest) prueban la capa REST y el manejo de excepciones, asegurando que los endpoints responden correctamente a las solicitudes HTTP.

Tests Unitarios: Las clases de UseCase y Repository (PriceRepository y PriceJpaRepository respectivamente) tienen tests unitarios que verifican su lógica interna y sus interacciones con las dependencias mockeadas, garantizando la funcionalidad individual de cada componente.

---
Adicionalmente, se ha incluido PriceRestMapper entre la capa de dominio y la capa REST. Esto proporciona flexibilidad para transformar la lógica de negocio de nuestro dominio en diferentes formatos de respuesta que puedan ser necesarios para distintos controladores. Si se requirieran respuestas en formatos diferentes para futuros controladores, solo sería necesario añadir un mapeado distinto a la respuesta sin afectar la capa de dominio.
