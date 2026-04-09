 # 🧑🏻‍💻 Proyecto de Pruebas - API REST de Productos SPRING BOOT

## 🔍 Descripción

Este proyecto consiste en el desarrollo y validación de una API REST para la gestión de productos, implementada con Spring Boot, 
en el lenguaje de programación ***JAVA*** con el entorno de desarrollo *IntelliJ IDEA*.
Incluye pruebas **unitarias** y  **de integración** implementando la automatización de pruebas con ayuda de ***JUnit 5*** para las pruebas unitarias y pruebas en general,
con ***Mockito*** para emular entornos como conexion a bases de datos, y emular datos que se envian o reciben, por otro lado tenemos la API **"Spring Boot"**,
que este nos facilita la tarea de realizar un CRUD completo dentro del estilo del negocio (en este caso los productos) y fácilita la conexion y manejo de datos de una base de datos. 

El objetivo principal es garantizar la calidad del software mediante la validación del comportamiento de cada capa de la aplicación.

---

## 🧱 Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

* **Controller** → Manejo de peticiones HTTP
* **Service** → Lógica de negocio
* **Repository** → Acceso a base de datos (JPA)
* **Model** → Entidades
* **DTO** → Transferencia de datos
* **Exception** → Manejo global de errores

---

## 🛠️ Tecnologías Utilizadas

* Java 17 / 21
* Spring Boot 3.2.3
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database (pruebas para bases de datos emulados)
* MySQL (producción utilización de bases de datos ya implementadas)
* JUnit 5
* Mockito - Jupiter
* Mockito - core
* MockMvc
* JaCoCo (Cobertura de código)
* Maven

---

>[!IMPORTANT]
>## Tipos de Pruebas Implementadas
>### 🔹 Pruebas Unitarias (UsuarioServiceImpl())
>Validan la lógica de negocio de manera aislada evaluando entradas y salidas del programa y lo que envia o recibe es correcto apra el negocio.
>En estos se incluyen clases de:
>* `ProductoServiceImplTest()`
>* Uso de Mockito para simular el repositorio
>* Validación de:
  >* Creación de productos
  >* Validaciones de negocio
  >* Manejo de errores
>---
>### 🔹 Pruebas de Controlador (WebMvcTest)
>Validan el comportamiento de la API sin levantar todo el contexto y emulando lo que es los request y response al enviarlos al controller.
>* `ProductoControllerTest()`
>* Uso de MockMvc
>* Validación de:
  >* Códigos HTTP (200, 201, 400, 404)
  >* Validaciones de entrada
  >* Manejo de excepciones
>---
>### 🔹 Pruebas de Integración
>Validan el flujo completo de la aplicación ya realizando request validos y validando el estado de los response al devolverlos.
>* `ProductoIntegrationTest()`
>* Uso de base de datos en memoria (H2)
>* Validación de:
  >* Persistencia real
  >* Endpoints REST completos
  >* Flujo CRUD
>---

## 📊 Cobertura de Código

Se utilizó JaCoCo para medir la cobertura de pruebas como método adicional de generar un reporte de las pruebas realizadas y elporcentaje de pruebas que se realizaron.

### ▶️ Ejecutar cobertura:

Especialmente para evaluar que la ejecución de TODOS los test se realicen correctamente y no fallen los test.

```bash
1. .\mvnw.cmd test
```

### 📂 Ubicación del reporte:

```bash
target/site/jacoco/index.html
```

---

## 📈 Resultados de Cobertura

* **Cobertura total: 55%**



* Controller	**76%**
* Service	**64%**
* Exception	**89%**
* Model/DTO	**40%**

## Endpoints Principales

| Método | Endpoint                         | Descripción           |
| ------ | -------------------------------- | --------------------- |
| POST   | `/api/productos`                 | Crear producto        |
| GET    | `/api/productos`                 | Listar productos      |
| GET    | `/api/productos/{id}`            | Obtener por ID        |
| PUT    | `/api/productos/{id}`            | Actualizar producto   |
| DELETE | `/api/productos/{id}`            | Eliminar producto     |
| GET    | `/api/productos/categoria/{cat}` | Filtrar por categoría |

---

## ⚠️ Manejo de Errores

El sistema implementa un manejo centralizado de excepciones:

* `ProductoNotFoundException` → 404
* `MethodArgumentNotValidException` → 400
* `IllegalArgumentException` → 400

Formato de respuesta:

```json
{
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Error de validación",
  "campos": [
    "nombre: El nombre es obligatorio"
  ]
}
```

---

##  🤔 Conclusiones

* Se logró implementar una API REST funcional con buenas prácticas
* Se aplicaron pruebas en diferentes niveles (unitarias, integración, controlador)
* Se identificaron áreas de mejora en cobertura de código
* Se fortaleció el uso de herramientas como Mockito, MockMvc y JaCoCo

---

##  ✍🏻 Autor

**Wilson David Monroy Ramírez**

---
>[!NOTE]
>## 🗒️ Notas Finales
>Este proyecto tiene fines académicos y está enfocado en demostrar:
>* Buenas prácticas en desarrollo backend
>* Implementación de pruebas automatizadas
>* Implementacion de herramientas totalmente utiles como Srping Boot

---
