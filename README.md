# MS-Alumnos Service

Microservicio reactivo desarrollado en **Spring Boot 3.3.5** y **Java 21** para la gestión de alumnos.
Utiliza **R2DBC** para conexión no bloqueante con PostgreSQL y expone una API documentada con **Swagger (OpenAPI 3.0)**.

---

## Requisitos previos

* Docker instalado
* Java 21
* Gradle (opcional si se usa `./gradlew`)
* Puerto `8082` libre para el microservicio

---

## 1. Levantar la base de datos en Docker

Ejecutar el siguiente comando para iniciar un contenedor de PostgreSQL:

```bash
docker run --name alumnos-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=alumnosdb \
  -p 5435:5432 \
  -d postgres
```

Esto crea una base de datos llamada `alumnosdb` en el puerto `5435`.

---

## 2. Inicialización automática del esquema y datos

El microservicio ejecuta automáticamente los scripts **`schema.sql`** y **`data.sql`** ubicados en `src/main/resources` al levantarse.

* `schema.sql`: crea las tablas necesarias en la base de datos.
* `data.sql`: inserta registros de prueba (alumnos con estado ACTIVO e INACTIVO).

De esta forma, no es necesario ejecutar manualmente ningún script: la base se crea y llena de forma automática.

---

## 3. Variables de entorno

Antes de ejecutar el microservicio, configurar las siguientes variables (por ejemplo, en el IDE o en la terminal):

```bash
export R2DBC_URL=r2dbc:postgresql://localhost:5435/alumnosdb
export DB_HOST=localhost
export DB_PORT=5435
export DB_NAME=alumnosdb
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export SERVER_PORT=8082
```

En Windows (PowerShell):

```powershell
$env:R2DBC_URL="r2dbc:postgresql://localhost:5435/alumnosdb"
$env:DB_HOST="localhost"
$env:DB_PORT="5435"
$env:DB_NAME="alumnosdb"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"
$env:SERVER_PORT="8082"
```

---

## 4. Ejecutar el microservicio

Compilar y ejecutar directamente:

```bash
./gradlew clean bootRun
```

O generar el JAR y ejecutarlo:

```bash
./gradlew clean build
java -jar build/libs/ms-alumnos-service-1.0.0.jar
```

---

## 5. Endpoints principales

| Método | Endpoint               | Descripción                     |
| -----: | ---------------------- | ------------------------------- |
| `POST` | `/api/v1/alumnos`      | Crea un nuevo alumno            |
|  `PUT` | `/api/v1/alumnos/{id}` | Actualiza un alumno existente   |
|  `GET` | `/api/v1/alumnos`      | Lista todos los alumnos activos |

---

## 6. Ejemplo de request (POST /api/v1/alumnos)

```json
{
  "nombre": "Carlos",
  "apellido": "Yunca",
  "edad": 25
}
```

---

## 7. Documentación API

Una vez levantado el servicio, acceder a:

* Documentación JSON:
  [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs)

* Interfaz Swagger UI:
  [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

---

## 8. Estructura del proyecto

```
ms-alumnos-service/
├── src/
│   ├── main/
│   │   ├── java/pe/scotiabank/ms/alumnos/service/
│   │   │   ├── application/       → Lógica de negocio
│   │   │   ├── domain/            → Entidades del dominio
│   │   │   ├── infrastructure/    → Controladores, repositorios y configuración
│   │   └── resources/
│   │       ├── application.yml    → Configuración principal
│   │       ├── schema.sql         → Creación automática de tablas
│   │       └── data.sql           → Datos iniciales de prueba
│   └── test/                      → Pruebas unitarias con JUnit + Reactor Test
└── build.gradle
```

---

## 10. Descripción funcional

El microservicio permite:

* Registrar nuevos alumnos con validaciones usando `@Valid`.
* Consultar todos los alumnos con estado **ACTIVO**.
* Actualizar los datos de un alumno existente.
* Persistencia reactiva con **R2DBC + PostgreSQL**.
* Inicialización automática de base de datos (`schema.sql` y `data.sql`).
* Respuestas estandarizadas mediante `ApiResponse`.
* Documentación automática con **OpenAPI 3.0 / Swagger UI**.
* Pruebas unitarias con **Mockito** y **StepVerifier** para flujos reactivos.
---