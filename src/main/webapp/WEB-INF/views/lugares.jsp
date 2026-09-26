<%-- 
    Document   : lugares
    Created on : 22-09-2026, 5:23:41 p. m.
    Author     : figue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="cl.saborlinares.model.Lugar"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lugares - Sabor Linares</title>
    </head>

    <body>

        <h1>Lugares de Sabor Linares</h1>

<p>
    <a href="${pageContext.request.contextPath}/logout">
        Cerrar sesión
    </a>
</p>

        <%
            List<Lugar> lugares = (List<Lugar>) request.getAttribute("lugares");
        %>

        <p>Cantidad de lugares: <%= lugares.size() %></p>

        <% for (Lugar lugar : lugares) { %>

            <hr>

            <h2><%= lugar.getNombre() %></h2>

            <p>
                <strong>Categoría:</strong>
                <%= lugar.getCategoria() %>
            </p>

            <p>
                <strong>Dirección:</strong>
                <%= lugar.getDireccion() %>
            </p>

            <p>
                <strong>Descripción:</strong>
                <%= lugar.getDescripcion() %>
            </p>

        <% } %>

    </body>
</html>