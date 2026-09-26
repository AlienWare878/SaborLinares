<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar lugar - Sabor Linares</title>
</head>
<body>

    <h1>Agregar lugar</h1>

    <form action="${pageContext.request.contextPath}/lugares" method="post">

        <div>
            <label for="nombre">Nombre:</label>
            <br>
            <input type="text" id="nombre" name="nombre" required>
        </div>

        <br>

        <div>
            <label for="descripcion">Descripción:</label>
            <br>
            <textarea
                id="descripcion"
                name="descripcion"
                rows="4"
                cols="50"
                required></textarea>
        </div>

        <br>

        <div>
            <label for="categoria">Categoría:</label>
            <br>
            <input type="text" id="categoria" name="categoria" required>
        </div>

        <br>

        <div>
            <label for="direccion">Dirección:</label>
            <br>
            <input type="text" id="direccion" name="direccion">
        </div>

        <br>

        <div>
            <label for="latitud">Latitud:</label>
            <br>
            <input type="number" step="any" id="latitud" name="latitud" required>
        </div>

        <br>

        <div>
            <label for="longitud">Longitud:</label>
            <br>
            <input type="number" step="any" id="longitud" name="longitud" required>
        </div>

        <br>

        <div>
            <label for="horario">Horario:</label>
            <br>
            <input type="text" id="horario" name="horario">
        </div>

        <br>

        <div>
            <label for="precio">Precio:</label>
            <br>
            <input type="text" id="precio" name="precio">
        </div>

        <br>

        <button type="submit">Guardar lugar</button>

    </form>

</body>
</html>