[README.md](https://github.com/user-attachments/files/28873262/README.md)
# AyesaFlix

> Gestor personal de películas: catálogo, watchlist, favoritas, reseñas, valoraciones y estadísticas.

Aplicación web full-stack construida con Spring Boot (API REST) y un frontend estático (HTML + Bootstrap + JavaScript vanilla). Permite registrar tu colección de cine, marcar lo que tienes pendiente de ver, gestionar favoritas, escribir reseñas personales, puntuar películas y consultar un resumen de tu actividad.

![Java](https://img.shields.io/badge/Java-8-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen)
![Maven](https://img.shields.io/badge/Maven-build-blue)
![H2](https://img.shields.io/badge/DB-H2-darkblue)
![Swagger](https://img.shields.io/badge/API%20Docs-Swagger-85EA2D)

---

## Características

- **Catálogo** con buscador dinámico por título y filtro por género.
- **Watchlist**: películas pendientes de ver, con buscador y "marcar como vista".
- **Favoritas**: colección de favoritas, con buscador.
- **Ficha detallada** (modal): sinopsis, reparto, tráiler, valoración por estrellas y reseña personal editable.
- **Estadísticas**: resumen en tarjetas (total de películas, tiempo total visto, género favorito, pendientes y reseñas escritas).
- **CRUD completo** de películas (insertar, editar, borrar) y de géneros (vía API).
- **Validación** de datos en servidor y manejo centralizado de errores.
- **Documentación interactiva** de la API con Swagger UI + colección Postman lista para importar.

---

## Stack tecnológico

| Capa | Tecnología |
|------|------------|
| Backend | Java 8, Spring Boot 2.7 (Web, Data JPA, Validation) |
| Base de datos | H2 (en memoria), inicializada con `data.sql` |
| Frontend | HTML5, CSS3, Bootstrap 5, JavaScript (sin framework) |
| Documentación | springdoc-openapi (Swagger UI) + colección Postman |
| Build / Test | Maven (wrapper incluido), JUnit 5, Mockito, AssertJ |

---

## Arquitectura

Arquitectura por capas clásica (Controller → Service → Repository → Entity):

```
Navegador (HTML/JS/Bootstrap)
        │  fetch() a /api/**
        ▼
┌─────────────────────────────┐
│  @RestController            │  PeliculaController, GeneroController, EstadisticasController
├─────────────────────────────┤
│  @Service                   │  PeliculaService, GeneroService  (lógica de negocio)
├─────────────────────────────┤
│  @Repository (Spring Data)  │  PeliculaRepository, GeneroRepository
├─────────────────────────────┤
│  @Entity (JPA)              │  Pelicula, Genero
└─────────────────────────────┘
        ▼
     H2 (en memoria)
```

Las excepciones se centralizan en `GlobalExceptionHandler` (`@RestControllerAdvice`), que devuelve JSON de error con el código HTTP adecuado (400 validación, 404 no encontrado).

---

## Modelo de datos

**Pelicula**: `id`, `titulo`, `duracion`, `anio`, `sinopsis`, `actores`, `urlPortada`, `urlTrailer`, `valoracion` (1–5), `favorita`, `vista`, `resenaPersonal`, `genero` (→ Genero).

**Genero**: `id`, `nombre` (único).

Relación: cada `Pelicula` pertenece a un `Genero` (`@ManyToOne`).

---

## 🔌 API REST

### Películas — `/api/peliculas`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/peliculas?buscar=&generoId=&vista=&page=` | Listado paginado (4/pág) con filtros opcionales |
| GET | `/api/peliculas/favoritas` | Lista de favoritas |
| GET | `/api/peliculas/{id}` | Ficha de una película (404 si no existe) |
| POST | `/api/peliculas` | Crear (valida con `@Valid`, devuelve 201) |
| PUT | `/api/peliculas/{id}` | Reemplazar todos los datos |
| DELETE | `/api/peliculas/{id}` | Borrar (204 / 404) |
| PATCH | `/api/peliculas/{id}/valoracion?valoracion=` | Actualizar solo la valoración |
| PATCH | `/api/peliculas/{id}/vista` | Alternar película vista/pendiente de ver |
| POST | `/api/peliculas/{id}/favorita` | Alternar favorita |
| PATCH | `/api/peliculas/{id}/resena` | Guardar/editar la reseña personal |

### Géneros — `/api/generos`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/generos` | Lista de géneros |
| GET | `/api/generos/{id}` | Género por id |
| POST · PUT · DELETE | `/api/generos[/{id}]` | CRUD de géneros |

### Estadísticas — `/api/estadisticas`
| Método | Ruta | Devuelve |
|--------|------|----------|
| GET | `/api/estadisticas` | `totalRegistradas`, `watchlistPendientes`, `tiempoTotalMinutos`, `totalResenas`, `generoFavorito` |

---

## Documentación de la API

La documentación interactiva está generada con **Swagger** (springdoc-openapi). Con la aplicación arrancada en local, accede a la interfaz gráfica en:

**http://localhost:8080/swagger-ui/index.html**

Desde ahí puedes explorar todos los endpoints y probarlos directamente desde el navegador.

Además, en la carpeta [`/documentacion`](./documentacion) se incluye la colección exportada de **Postman** (`AyesaFlix_Postman.json`), lista para importar y probar los endpoints:

> En Postman → **Import** → selecciona `documentacion/AyesaFlix_Postman.json`.

---

## Cómo ejecutar

**Requisitos:** JDK 8+ (no hace falta Maven, se incluye el *wrapper*).

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

Luego abre **http://localhost:8080/**

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Consola H2:** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:ayesaflixdb` · Usuario: `sa` · Contraseña: *(vacía)*

> La base de datos es **en memoria**: se recrea y se rellena con `src/main/resources/data.sql` en cada arranque.

---

## Tests

```bash
# Windows
mvnw.cmd test

# Linux / macOS
./mvnw test
```

Cobertura incluida:
- **`PeliculaServiceTest`** — unitario con Mockito: lógica de `toggleVista`/`toggleFavorita` y manejo de id inexistente.
- **`PeliculaRepositoryTest`** — `@DataJpaTest`: consultas personalizadas (conteos, suma de duración, género favorito) sobre H2.
- **`PeliculaControllerTest`** — `@WebMvcTest` + MockMvc: respuestas 200/404 y endpoints PATCH/DELETE.

---

## Estructura del proyecto

```
src/main/java/com/example/demo/
├── controller/      # PeliculaController, GeneroController, EstadisticasController
├── services/        # PeliculaService, GeneroService
├── repository/      # PeliculaRepository, GeneroRepository
├── model/           # Pelicula, Genero
└── exceptions/      # GlobalExceptionHandler
src/main/resources/
├── static/          # Frontend: index.html + /peliculas/*.html + Bootstrap
├── application.properties
└── data.sql         # Datos iniciales
documentacion/       # Colección Postman (AyesaFlix_Postman.json)
src/test/java/...    # Tests (JUnit 5 + Mockito)
```

---

## Posibles mejoras (roadmap)

- Persistencia real (H2 fichero o PostgreSQL) + despliegue público.
- Integración con TMDB para autocompletar póster, sinopsis y reparto.
- Autenticación multiusuario (Spring Security).
- CI con GitHub Actions + Docker.

---

## Autor

Proyecto desarrollado como prácticas de empresa en Ayesa Digital. AyesaFlix © 2026.
