<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cl.saborlinares.model.Resena" %>
<%@ page import="cl.saborlinares.model.Usuario" %>
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    List<Resena> resenas = (List<Resena>) request.getAttribute("resenas");
    Object lugarIdObj = request.getAttribute("lugarId");
    String lugarId = lugarIdObj != null ? lugarIdObj.toString() : request.getParameter("lugarId");
    Resena resenaEditar = (Resena) request.getAttribute("resenaEditar");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reseñas y Opiniones - Sabor Linares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

    <header>
        <div class="nav-container">
            <a href="${pageContext.request.contextPath}/lugares" class="logo-brand">
                <span>🍊</span> Sabor Linares
            </a>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/lugares">Lugares</a></li>
                    <li><a href="${pageContext.request.contextPath}/mapa">Mapa</a></li>
                    <% if (usuarioSesion != null) { %>
                        <li><a href="${pageContext.request.contextPath}/lugares?accion=nuevo">+ Publicar Lugar</a></li>
                        <% if (usuarioSesion.getRol() == Usuario.Rol.ADMIN) { %>
                            <li><a href="${pageContext.request.contextPath}/lugares?accion=admin">Moderación</a></li>
                        <% } %>
                        <li><span class="user-badge"><%= usuarioSesion.getNombre() %> (<%= usuarioSesion.getRol() %>)</span></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a></li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/login">Iniciar Sesión</a></li>
                        <li><a href="${pageContext.request.contextPath}/registro">Registrarse</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <main class="main-container">

        <div class="page-header">
            <div>
                <h1>💬 Reseñas del Lugar #<%= lugarId %></h1>
                <p>Lee las opiniones de la comunidad o comparte tu experiencia gastronómica.</p>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/lugares" class="btn btn-outline">← Volver a Lugares</a>
            </div>
        </div>

        <div class="reviews-section">

            <% if (usuarioSesion != null) { %>

                <div class="form-card" style="margin-bottom: 2rem;">

                    <% if (resenaEditar != null) { %>

                        <h2>✏️ Editar tu reseña</h2>

                        <form action="${pageContext.request.contextPath}/resenas" method="post">
                            <input type="hidden" name="lugarId" value="<%= lugarId %>">
                            <input type="hidden" name="resenaId" value="<%= resenaEditar.getId() %>">

                            <div class="form-group">
                                <label for="comentario">Comentario:</label>
                                <textarea id="comentario" name="comentario" class="form-control" rows="3" required><%= resenaEditar.getComentario() %></textarea>
                            </div>

                            <div class="form-group">
                                <label for="calificacion">Calificación:</label>
                                <select id="calificacion" name="calificacion" class="form-control" required>
                                    <option value="1" <%= resenaEditar.getCalificacion() == 1 ? "selected" : "" %>>⭐ 1 estrella - Malo</option>
                                    <option value="2" <%= resenaEditar.getCalificacion() == 2 ? "selected" : "" %>>⭐⭐ 2 estrellas - Regular</option>
                                    <option value="3" <%= resenaEditar.getCalificacion() == 3 ? "selected" : "" %>>⭐⭐⭐ 3 estrellas - Bueno</option>
                                    <option value="4" <%= resenaEditar.getCalificacion() == 4 ? "selected" : "" %>>⭐⭐⭐⭐ 4 estrellas - Muy Bueno</option>
                                    <option value="5" <%= resenaEditar.getCalificacion() == 5 ? "selected" : "" %>>⭐⭐⭐⭐⭐ 5 estrellas - Excelente</option>
                                </select>
                            </div>

                            <div style="display:flex; gap: 1rem; margin-top: 1rem;">
                                <button type="submit" class="btn btn-primary">Guardar cambios</button>
                                <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= lugarId %>" class="btn btn-outline">Cancelar</a>
                            </div>
                        </form>

                    <% } else { %>

                        <h2>✍️ Escribe una reseña</h2>

                        <form action="${pageContext.request.contextPath}/resenas" method="post">
                            <input type="hidden" name="lugarId" value="<%= lugarId %>">

                            <div class="form-group">
                                <label for="comentario">Tu comentario u opinión:</label>
                                <textarea id="comentario" name="comentario" class="form-control" rows="3" required placeholder="¿Qué tal estuvo la comida, atención y precios?"></textarea>
                            </div>

                            <div class="form-group">
                                <label for="calificacion">Calificación:</label>
                                <select id="calificacion" name="calificacion" class="form-control" required>
                                    <option value="">-- Selecciona estrellas --</option>
                                    <option value="1">⭐ 1 estrella - Malo</option>
                                    <option value="2">⭐⭐ 2 estrellas - Regular</option>
                                    <option value="3">⭐⭐⭐ 3 estrellas - Bueno</option>
                                    <option value="4">⭐⭐⭐⭐ 4 estrellas - Muy Bueno</option>
                                    <option value="5">⭐⭐⭐⭐⭐ 5 estrellas - Excelente</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary" style="margin-top: 1rem;">Publicar reseña</button>
                        </form>

                    <% } %>

                </div>

            <% } else { %>

                <div class="alert alert-danger" style="display: flex; justify-content: space-between; align-items: center;">
                    <span>🔒 Debes iniciar sesión para escribir una reseña sobre este lugar.</span>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-sm">Iniciar Sesión</a>
                </div>

            <% } %>

            <h2>Opiniones de la Comunidad (<%= resenas != null ? resenas.size() : 0 %>)</h2>
            <br>

            <% if (resenas == null || resenas.isEmpty()) { %>

                <div class="alert alert-success">
                    🌱 Este lugar aún no tiene reseñas. ¡Sé el primero en dejar una opinión!
                </div>

            <% } else { %>

                <% for (Resena resena : resenas) { %>

                    <div class="review-card">
                        <div class="review-header">
                            <div>
                                <strong>👤 <%= resena.getUsuario() != null ? resena.getUsuario().getNombre() : "Usuario" %></strong>
                                <span style="font-size: 0.8rem; color: var(--color-gris); margin-left: 0.5rem;">
                                    <%= resena.getFecha() != null ? resena.getFecha().toString().replace("T", " ") : "" %>
                                </span>
                            </div>
                            <div class="star-rating">
                                <% for (int i = 0; i < resena.getCalificacion(); i++) { %>⭐<% } %> 
                                (<%= resena.getCalificacion() %>/5)
                            </div>
                        </div>

                        <p style="margin: 0.8rem 0; color: var(--color-texto); font-size: 1rem;">
                            <%= resena.getComentario() %>
                        </p>

                        <% if (usuarioSesion != null && resena.getUsuario() != null && resena.getUsuario().getId() == usuarioSesion.getId()) { %>
                            <div style="display: flex; gap: 0.5rem; margin-top: 0.8rem; border-top: 1px solid var(--color-gris-claro); padding-top: 0.5rem;">
                                <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= lugarId %>&editarId=<%= resena.getId() %>" class="btn btn-outline btn-sm">
                                    ✏️ Editar mi reseña
                                </a>
                                <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= lugarId %>&eliminarId=<%= resena.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro de eliminar tu reseña?');">
                                    🗑️ Eliminar
                                </a>
                            </div>
                        <% } %>
                    </div>

                <% } %>

            <% } %>

        </div>

    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — Plataforma Comunitaria Gastronómica</p>
    </footer>

</body>
</html>
