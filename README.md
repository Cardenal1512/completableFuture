# 🎯 Multihilo Anime Search (Hexagonal Architecture + Concurrency)

Este proyecto es una demo educativa que realiza búsquedas de animes en [MyAnimeList](https://myanimelist.net/) aplicando:

- ✅ Arquitectura hexagonal (puertos y adaptadores)
- ⚙️ Búsquedas concurrentes con `CompletableFuture` y `ExecutorService`
- ⏱️ Timeout individual por búsqueda
- 💥 Manejo de errores concurrentes
- 📊 Logs con tiempo total y por búsqueda

---

## 🧱 Estructura del proyecto

```
domain/
├── model/             # Entidades (Anime)
├── port/
│   ├── primary/       # Casos de uso
│   └── secondary/     # Dependencias externas

application/
└── service/           # Lógica de aplicación (SearchAnimeService)

infrastructure/
├── in/rest/           # Controlador REST
├── out/client/        # Adaptador MyAnimeList
└── config/            # Configuración (hilos, beans)
```

---

## 🚀 ¿Qué hace?

Permite realizar búsquedas de anime individuales o múltiples con control de concurrencia:

### 🔍 Búsqueda individual
```http
GET /api/anime/search?query=Naruto&limit=5
```

### 🔎 Búsqueda múltiple en paralelo
```http
GET /api/anime/search-multiple?titles=Naruto&titles=One+Piece&titles=Bleach
```

Cada búsqueda múltiple:

- Se lanza en un hilo separado.
- Tiene un timeout de 1 segundo.
- No bloquea otras si falla una.
- Imprime el tiempo por título + el total.

---

## 🧪 Ejemplo de log

```
🧵 Iniciando búsqueda de "Naruto" en hilo: pool-2-thread-1
📊 "Naruto" tardó 132 ms
❌ Fallo o timeout buscando "gdasgdasgas": TimeoutException
⏱️ Tiempo total de ejecución: 487ms
```

---

## 🧰 Tecnologías

- Java 17+
- Spring Boot
- CompletableFuture
- ExecutorService personalizado
- Arquitectura hexagonal
- Swagger/OpenAPI (opcional)

---

## 🛠️ Ejecución local

```bash
./mvnw spring-boot:run
```

> Puedes probar con Postman o navegador en:
> `http://localhost:8080/api/anime/search-multiple?titles=Naruto&titles=Bleach`

---

## 🧠 ¿Qué se puede mejorar?

- Implementar `Semaphore` para simular rate limit.
- Agregar timeout global a toda la operación.
- Añadir caché concurrente de resultados.
- Exportar tiempos a CSV/JSON.
- Tests unitarios y concurrentes.

---

## 👨‍💻 Autor

Creado por [Cardenal1512](https://github.com/cardenal1512) como práctica didáctica de Java concurrente + arquitectura hexagonal.