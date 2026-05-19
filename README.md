# Productos API — Pipeline CI/CD con GitHub Actions y Docker Hub

![CI/CD Status](https://github.com/Johan09CD/Carre-o_post2-u12/actions/workflows/ci.yml/badge.svg)

**Unidad 12: Despliegue y CI/CD — Post-Contenido 2**  
Ingeniería de Sistemas · Universidad de Santander (UDES) · 2026

---

## Descripción

API REST desarrollada con Spring Boot para la gestión de productos, con pipeline CI/CD completo usando GitHub Actions que automatiza compilación, pruebas con cobertura JaCoCo, y publicación de imagen Docker en Docker Hub en cada push a `main`.

---

## Pipeline CI/CD

El pipeline se activa automáticamente en cada push a `main` y realiza:

1. **build-and-test**: Compilación con Maven, ejecución de pruebas unitarias y generación de reporte de cobertura JaCoCo (disponible como artefacto descargable)
2. **docker-publish**: Construcción de imagen Docker con multi-stage build y publicación en Docker Hub con tags `latest` y `sha-<commit>`

---

## GitHub Secrets Requeridos

Configurar en **Settings → Secrets and variables → Actions → New repository secret**:

| Secret | Descripción | Cómo obtenerlo |
|--------|-------------|----------------|
| `DOCKERHUB_USERNAME` | Nombre de usuario de Docker Hub | Tu usuario en hub.docker.com |
| `DOCKERHUB_TOKEN` | Access Token de Docker Hub | hub.docker.com → Account Settings → Security → New Access Token |

> **Importante:** Nunca usar la contraseña directamente. Siempre usar un Access Token.

---

## Imagen Docker

```bash
# Descargar la imagen publicada
docker pull

# Ejecutar localmente con perfil dev
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \

# Ejecutar con perfil prod y base de datos externa
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:postgresql://host:5432/productosdb \
  -e DB_USER=usuario \
  -e DB_PASS=contraseña \
```

---

## Ejecutar Localmente con Docker Compose

```bash
# Levantar app + PostgreSQL
docker compose up -d --build

# Verificar estado
docker compose ps

# Health check
curl http://localhost:8080/actuator/health

# Detener
docker compose down
```

---

## Endpoints REST

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/actuator/health` | Estado de la aplicación |

---

## Estructura del Proyecto

```
carreño-post2-u12/
├── .github/
│   └── workflows/
│       └── ci.yml              ← Pipeline CI/CD
├── src/
│   └── main/
│       ├── java/co/edu/udes/castellanos/post2u12/
│       │   ├── config/ProductoDataSeeder.java
│       │   ├── domain/Producto.java
│       │   ├── repository/ProductoRepository.java
│       │   ├── service/ProductoService.java
│       │   ├── service/ProductoServiceImpl.java
│       │   ├── web/
│       │   │   ├── dto/ProductoRequest.java
│       │   │   ├── dto/ProductoResponse.java
│       │   │   ├── exception/ApiError.java
│       │   │   ├── exception/GlobalExceptionHandler.java
│       │   │   ├── exception/NotFoundException.java
│       │   │   ├── HomeController.java
│       │   │   └── ProductoController.java
│       │   └── Post2U12Application.java
│       └── resources/
│           ├── application.properties
│           ├── application-dev.properties
│           ├── application-prod.properties
│           └── data.sql
├── Dockerfile
├── .dockerignore
├── docker-compose.yml
└── pom.xml
```