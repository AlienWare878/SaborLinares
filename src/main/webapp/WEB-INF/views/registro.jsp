<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro - Sabor Linares</title>
</head>
<body>

    <h1>Crear cuenta</h1>

    <% if ("true".equals(request.getParameter("exito"))) { %>
        <p>¡Registro exitoso! Tu cuenta ha sido creada.</p>
    <% } %>

    <% if ("true".equals(request.getParameter("error"))) { %>
        <p>No se pudo completar el registro. Es posible que el correo ya esté registrado.</p>
    <% } %>

    <form action="${pageContext.request.contextPath}/registro" method="post">

        <div>
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required>
        </div>

        <br>

        <div>
            <label for="correo">Correo:</label>
            <input type="email" id="correo" name="correo" required>
        </div>

        <br>

        <div>
            <label for="contrasena">Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required>
        </div>

        <br>

        <button type="submit">Registrarse</button>

    </form>

</body>
</html>
