# AyesaFlix API — Referencia

API REST para la gestión de un catálogo personal de películas: catálogo con filtros y paginación, watchlist (vistas / pendientes), favoritas, valoraciones, reseñas y estadísticas.

| | |
|---|---|
| **Versión** | 0.0.1-SNAPSHOT |
| **Stack** | Spring Boot 2.7.18 · Spring Data JPA · H2 (en memoria) · Bean Validation |
| **Base URL** | `http://localhost:8080` |
| **Formato** | `application/json` (UTF-8) |
| **Autenticación** | Ninguna (entorno local) |

> **Importar en Postman:** usa *Import → File* con `docs/AyesaFlix.postman_collection.json`. La colección incluye las variables `baseUrl`, `peliculaId` y `generoId`.

---

## Índice

- [Modelos de datos](#modelos-de-datos)
- [Películas](#películas)
- [Géneros](#géneros)
- [Estadísticas](#estadísticas)
- [Códigos de estado](#códigos-de-estado)

---

## Modelos de datos

### Película

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| `id` | `Long` | autogenerado | Identificador único |
| `titulo` | `String` | obligatorio, no vacío | Título |
| `duracion` | `Integer` | ≥ 1 | Duración en minutos |
| `anio` | `Integer` | ≥ 1888 | Año de estreno |
| `sinopsis` | `String` (TEXT) | — | Sinopsis |
| `actores` | `String` | — | Reparto principal |
| `urlPortada` | `String` | — | URL de la portada |
| `urlTrailer` | `String` | — | URL del tráiler |
| `valoracion` | `Integer` | 1–5 | Valoración en estrellas |
| `favorita` | `Boolean` | por defecto `true` | Marca de favorita |
| `vista` | `Boolean` | por defecto `true` | `true` = vista, `false` = pendiente |
| `resenaPersonal` | `String` (TEXT) | — | Reseña personal |
| `genero` | `Genero` | obligatorio | Género asociado (enviar `{ "id": N }`) |

### Género

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| `id` | `Long` | autogenerado | Identificador único |
| `nombre` | `String` | obligatorio, único | Nombre del género |

---

## Películas

Recurso base: `/api/peliculas`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/peliculas` | Listado paginado con filtros |
| `GET` | `/api/peliculas/favoritas` | Lista de favoritas |
| `GET` | `/api/peliculas/{id}` | Ficha por ID |
| `POST` | `/api/peliculas` | Crear película |
| `PUT` | `/api/peliculas/{id}` | Actualizar (completa) |
| `PATCH` | `/api/peliculas/{id}/valoracion` | Actualizar valoración |
| `PATCH` | `/api/peliculas/{id}/resena` | Actualizar reseña |
| `POST` | `/api/peliculas/{id}/favorita` | Alternar favorita |
| `PATCH` | `/api/peliculas/{id}/vista` | Alternar vista / pendiente |
| `DELETE` | `/api/peliculas/{id}` | Borrar película |

### `GET /api/peliculas`

Devuelve un objeto `Page` de Spring. Tamaño de página **fijo en 4**.

**Query params** (todos opcionales y combinables):

| Param | Tipo | Por defecto | Descripción |
|---|---|---|---|
| `buscar` | `String` | — | Filtra por título (contiene) |
| `generoId` | `Long` | — | Filtra por género |
| `page` | `int` | `0` | Página (0-based) |
| `vista` | `Boolean` | `true` | `true` = vistas, `false` = watchlist |

**Ejemplos**

```
GET /api/peliculas
GET /api/peliculas?buscar=matrix
GET /api/peliculas?generoId=2
GET /api/peliculas?buscar=matrix&generoId=2
GET /api/peliculas?page=1
GET /api/peliculas?vista=false
```

**Respuesta `200 OK`** (estructura abreviada)

```json
{
  "content": [ { "id": 1, "titulo": "The Matrix", "valoracion": 5, "genero": { "id": 1, "nombre": "Ciencia ficción" } } ],
  "pageable": { "pageNumber": 0, "pageSize": 4 },
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 4,
  "first": true,
  "last": true
}
```

### `GET /api/peliculas/favoritas`

Lista (sin paginar) de películas favoritas. → `200 OK`

### `GET /api/peliculas/{id}`

→ `200 OK` con la película · `404 Not Found` si no existe.

### `POST /api/peliculas`

Crea una película. Cuerpo validado con Bean Validation.

**Body**

```json
{
  "titulo": "The Matrix",
  "duracion": 136,
  "anio": 1999,
  "sinopsis": "Un hacker descubre la verdadera naturaleza de su realidad.",
  "actores": "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
  "urlPortada": "https://example.com/matrix.jpg",
  "urlTrailer": "https://www.youtube.com/watch?v=vKQi3bBA1y8",
  "valoracion": 5,
  "favorita": true,
  "vista": true,
  "resenaPersonal": "Un clásico imprescindible.",
  "genero": { "id": 1 }
}
```

→ `201 Created` con la película (incluye `id`) · `400 Bad Request` si falla la validación.

### `PUT /api/peliculas/{id}`

Reemplaza **todos** los campos. Mismo body que en la creación.

→ `200 OK` · `404 Not Found` · `400 Bad Request`.

### `PATCH /api/peliculas/{id}/valoracion`

Actualiza solo la valoración. Query param `valoracion` (1–5, obligatorio).

```
PATCH /api/peliculas/3/valoracion?valoracion=4
```

→ `200 OK` con la película · `404 Not Found`.

### `PATCH /api/peliculas/{id}/resena`

Actualiza solo la reseña.

**Body**

```json
{ "resenaPersonal": "Mi opinión actualizada." }
```

→ `200 OK` · `404 Not Found`.

### `POST /api/peliculas/{id}/favorita`

Alterna favorita. → `200 OK` con `true` (ahora favorita) o `false`.

### `PATCH /api/peliculas/{id}/vista`

Alterna vista/pendiente. → `200 OK` con `true` (vista) o `false` (pendiente).

### `DELETE /api/peliculas/{id}`

→ `204 No Content` · `404 Not Found`.

---

## Géneros

Recurso base: `/api/generos`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/generos` | Listar todos |
| `GET` | `/api/generos/{id}` | Obtener por ID |
| `POST` | `/api/generos` | Crear |
| `PUT` | `/api/generos/{id}` | Actualizar |
| `DELETE` | `/api/generos/{id}` | Borrar |

### `POST /api/generos` · `PUT /api/generos/{id}`

**Body**

```json
{ "nombre": "Ciencia ficción" }
```

- `POST` → `200 OK` con el género creado (incluye `id`).
- `PUT` → `200 OK` · `404 Not Found`.

### `DELETE /api/generos/{id}`

→ `204 No Content` · `404 Not Found`.

> Borrar un género referenciado por películas puede fallar por la restricción de clave foránea (`id_genero` no nulo).

---

## Estadísticas

### `GET /api/estadisticas`

**Respuesta `200 OK`**

```json
{
  "totalRegistradas": 12,
  "watchlistPendientes": 3,
  "tiempoTotalMinutos": 1450,
  "totalResenas": 5,
  "generoFavorito": "Ciencia ficción"
}
```

| Campo | Descripción |
|---|---|
| `totalRegistradas` | Total de películas |
| `watchlistPendientes` | Películas con `vista = false` |
| `tiempoTotalMinutos` | Suma de duración de las vistas |
| `totalResenas` | Películas con reseña personal |
| `generoFavorito` | Género más frecuente |

---

## Códigos de estado

| Código | Significado | Cuándo |
|---|---|---|
| `200 OK` | Éxito | GET/PUT/PATCH correctos |
| `201 Created` | Creado | `POST /api/peliculas` |
| `204 No Content` | Sin contenido | DELETE correcto |
| `400 Bad Request` | Validación fallida | Body inválido |
| `404 Not Found` | No encontrado | Recurso inexistente |

---

### Recursos útiles en desarrollo

- **Consola H2:** `http://localhost:8080/h2-console` (JDBC `jdbc:h2:mem:ayesaflixdb`, usuario `sa`, sin contraseña).
