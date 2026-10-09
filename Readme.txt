Aplicación web desarrollada como proyecto de Java Web.

Tecnologías utilizadas

* Java
* Jakarta EE
* Servlets y JSP
* Apache Tomcat 10.1
* Maven
* MySQL
* JDBC
* Leaflet
* NetBeans

Base de datos

La aplicación utiliza una base de datos MySQL llamada:

sabor_linares

Se incluye una copia de la base de datos en:

BaseDatos/sabor_linares.sql

Para restaurar la base de datos

1. Abrir MySQL mediante Laragon, XAMPP u otro servidor MySQL.
2. Crear/restaurar la base de datos utilizando el archivo `sabor_linares.sql`.
3. Verificar que la base de datos se llame `sabor_linares`.

Configuración de conexión

La aplicación utiliza actualmente:

* Host: `localhost`
* Puerto: `3306`
* Base de datos: `sabor_linares`
* Usuario: `root`
* Contraseña: vacía

Si la configuración de MySQL es diferente, se debe modificar la clase `Conexion.java`.

Ejecución

1. Abrir el proyecto en NetBeans.
2. Verificar que Maven descargue las dependencias.
3. Configurar Apache Tomcat 10.1.
4. Iniciar MySQL.
5. Ejecutar el proyecto desde NetBeans.

La aplicación se ejecuta mediante el servidor Tomcat.
