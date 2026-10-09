# SaborLinares — Plataforma de Geolocalización y Descubrimiento Gastronómico

SaborLinares es una aplicación web interactiva orientada a la visibilización de pequeños emprendimientos gastronómicos (food trucks, puestos locales y picadas). La plataforma integra mapeo en tiempo real, gestión de información comercial y un sistema de moderación de contenidos para conectar a la comunidad con la oferta gastronómica local.

---

## Características Principales

* **Mapeo Interactivo:** Integración con la API de Leaflet para geolocalizar locales cercanos, desplegar ubicaciones exactas, rangos de precios, horarios de atención y datos de contacto.
* **Perfiles Diferenciados:** Acceso adaptado para consumidores (búsqueda y descubrimiento) y vendedores (registro de locales).
* **Sistema de Reseñas:** Módulo para que los usuarios registrados ingresen calificaciones y comentarios sobre los establecimientos.
* **Flujo de Moderación y Verificación:**
* Los locales ingresados por usuarios estándar requieren validación administrativa.
* Panel de administración centralizado para aprobar, verificar o eliminar publicaciones.


* **Control de Acceso y Sesiones (RBAC):** Autenticación de usuarios y restricción de funciones mediante roles de Usuario y Administrador.

---

## Arquitectura y Tecnologías

* **Backend:** Java, Jakarta EE (Servlets y JSP)
* **Gestión de Dependencias:** Apache Maven
* **Servidor de Aplicaciones:** Apache Tomcat 10.1
* **Base de Datos & Persistencia:** MySQL, JDBC (`Conexion.java`)
* **Frontend & Mapas:** HTML5, CSS3, JavaScript, Leaflet API
* **Entorno de Desarrollo:** NetBeans IDE, Git / GitHub

---

## Configuración de la Base de Datos

El sistema utiliza la base de datos MySQL `sabor_linares`.

### Pasos para la importación:

1. Iniciar el servicio MySQL mediante Laragon, XAMPP o entorno local.
2. Importar el archivo SQL ubicado en la siguiente ruta del repositorio:
```text
BaseDatos/sabor_linares.sql

```


3. Parámetros de conexión por defecto (`src/.../Conexion.java`):
* **Host:** localhost:3306
* **Base de Datos:** sabor_linares
* **Usuario:** root
* **Contraseña:** (Vacía)



---

## Instalación y Ejecución

1. **Clonar el repositorio:**
```bash
git clone https://github.com/AlienWare878/SaborLinares.git

```


2. **Importación:** Abrir el proyecto en NetBeans IDE y permitir la descarga de dependencias mediante Maven (`pom.xml`).
3. **Servidor:** Desplegar el proyecto sobre un servidor Apache Tomcat 10.1.
4. **Navegación:** Al ejecutar la aplicación, la ruta de autenticación e inicio de sesión se encuentra mapeada en el endpoint `/login`.
