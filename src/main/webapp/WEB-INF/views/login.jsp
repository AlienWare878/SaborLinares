<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión - Sabor Linares</title>
</head>
<body>

    <h1>Iniciar sesión</h1>

    <% if ("true".equals(request.getParameter("error"))) { %>
        <p>Correo o contraseña incorrectos.</p>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post">

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

        <button type="submit">Iniciar sesión</button>

    </form>

</body>
</html>

