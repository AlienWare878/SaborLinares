<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cl.saborlinares.model.Resena" %>
<%@ page import="cl.saborlinares.model.Usuario" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reseñas - Sabor Linares</title>
</head>
<body>

    <h1>Reseñas del lugar</h1>

    <p>ID del lugar: ${lugarId}</p>
    
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
%>

<% if (usuarioSesion != null) { %>

    <%
        Resena resenaEditar = (Resena) request.getAttribute("resenaEditar");
    %>

    <% if (resenaEditar != null) { %>

        <h2>Editar reseña</h2>

        <form action="${pageContext.request.contextPath}/resenas" method="post">

            <input type="hidden" name="lugarId" value="${lugarId}">
            <input type="hidden" name="resenaId" value="<%= resenaEditar.getId() %>">

            <div>
                <label for="comentario">Comentario:</label>
                <br>
                <textarea
                    id="comentario"
                    name="comentario"
                    rows="4"
                    cols="50"
                    required><%= resenaEditar.getComentario() %></textarea>
            </div>

            <br>

            <div>
                <label for="calificacion">Calificación:</label>

                <select id="calificacion" name="calificacion" required>

                    <option value="1" <%= resenaEditar.getCalificacion() == 1 ? "selected" : "" %>>
                        1 estrella
                    </option>

                    <option value="2" <%= resenaEditar.getCalificacion() == 2 ? "selected" : "" %>>
                        2 estrellas
                    </option>

                    <option value="3" <%= resenaEditar.getCalificacion() == 3 ? "selected" : "" %>>
                        3 estrellas
                    </option>

                    <option value="4" <%= resenaEditar.getCalificacion() == 4 ? "selected" : "" %>>
                        4 estrellas
                    </option>

                    <option value="5" <%= resenaEditar.getCalificacion() == 5 ? "selected" : "" %>>
                        5 estrellas
                    </option>

                </select>
            </div>

            <br>

            <button type="submit">Guardar cambios</button>

        </form>

    <% } else { %>

        <h2>Escribe una reseña</h2>

        <form action="${pageContext.request.contextPath}/resenas" method="post">

            <input type="hidden" name="lugarId" value="${lugarId}">

            <div>
                <label for="comentario">Comentario:</label>
                <br>
                <textarea
                    id="comentario"
                    name="comentario"
                    rows="4"
                    cols="50"
                    required></textarea>
            </div>

            <br>

            <div>
                <label for="calificacion">Calificación:</label>

                <select id="calificacion" name="calificacion" required>
                    <option value="">Selecciona una calificación</option>
                    <option value="1">1 estrella</option>
                    <option value="2">2 estrellas</option>
                    <option value="3">3 estrellas</option>
                    <option value="4">4 estrellas</option>
                    <option value="5">5 estrellas</option>
                </select>
            </div>

            <br>

            <button type="submit">Publicar reseña</button>

        </form>

    <% } %>

    <hr>

<% } else { %>

    <p>
        Debes iniciar sesión para escribir una reseña.
    </p>

<% } %>

    <%
        List<Resena> resenas = (List<Resena>) request.getAttribute("resenas");
    %>

    <% if (resenas == null || resenas.isEmpty()) { %>

        <p>Este lugar todavía no tiene reseñas.</p>

    <% } else { %>

        <p>Cantidad de reseñas: <%= resenas.size() %></p>

        <% for (Resena resena : resenas) { %>

            <article>

                <h2>
                    <%= resena.getCalificacion() %> / 5
                </h2>

                <p>
                    <strong>Comentario:</strong>
                    <%= resena.getComentario() %>
                </p>

                <p>
                    <strong>Usuario:</strong>
                    <%= resena.getUsuario().getNombre() %>
                </p>

                <p>
                    <strong>Fecha:</strong>
                    <%= resena.getFecha() %>
                </p>
                <% if (usuarioSesion != null
        && resena.getUsuario() != null
        && resena.getUsuario().getId() == usuarioSesion.getId()) { %>

    <p>
        <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= resena.getLugar().getId() %>&editarId=<%= resena.getId() %>">
            Editar
        </a>

        <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= resena.getLugar().getId() %>&eliminarId=<%= resena.getId() %>">
            Eliminar
        </a>
    </p>

<% } %>
               
                <hr>

            </article>

        <% } %>

    <% } %>

</body>
</html>

