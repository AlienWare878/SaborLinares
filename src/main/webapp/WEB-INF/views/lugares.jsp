<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cl.saborlinares.model.Lugar" %>
<%@ page import="cl.saborlinares.model.Usuario" %>
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    List<Lugar> lugares = (List<Lugar>) request.getAttribute("lugares");
    String categoriaSeleccionada = (String) request.getAttribute("categoriaSeleccionada");
    String exito = request.getParameter("exito");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lugares - Sabor Linares</title>
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
                    <li><a href="${pageContext.request.contextPath}/lugares" class="active">Lugares</a></li>
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
        
        <% if ("guardado".equals(exito)) { %>
            <div class="alert alert-success">
                🎉 ¡Lugar guardado exitosamente! Ha sido enviado a revisión por un administrador.
            </div>
        <% } %>

        <div class="page-header">
            <div>
                <h1>🍔 Descubre la Gastronomía de Linares</h1>
                <p>Encuentra los mejores carritos, food trucks, cafeterías y picadas locales.</p>
            </div>
            <div>
                <% if (usuarioSesion != null) { %>
                    <a href="${pageContext.request.contextPath}/lugares?accion=nuevo" class="btn btn-primary">+ Agregar mi local</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Iniciar Sesión para Publicar</a>
                <% } %>
            </div>
        </div>

        <!-- Filtros de categoría -->
        <div class="filtro-categorias">
            <a href="${pageContext.request.contextPath}/lugares" 
               class="filtro-btn <%= (categoriaSeleccionada == null || "TODAS".equalsIgnoreCase(categoriaSeleccionada)) ? "active" : "" %>">
               Todas
            </a>
            <a href="${pageContext.request.contextPath}/lugares?categoria=Food Truck" 
               class="filtro-btn <%= "Food Truck".equalsIgnoreCase(categoriaSeleccionada) ? "active" : "" %>">
               Food Trucks
            </a>
            <a href="${pageContext.request.contextPath}/lugares?categoria=Carrito" 
               class="filtro-btn <%= "Carrito".equalsIgnoreCase(categoriaSeleccionada) ? "active" : "" %>">
               Carritos
            </a>
            <a href="${pageContext.request.contextPath}/lugares?categoria=Cafetería" 
               class="filtro-btn <%= "Cafetería".equalsIgnoreCase(categoriaSeleccionada) ? "active" : "" %>">
               Cafeterías
            </a>
            <a href="${pageContext.request.contextPath}/lugares?categoria=Comida Casera" 
               class="filtro-btn <%= "Comida Casera".equalsIgnoreCase(categoriaSeleccionada) ? "active" : "" %>">
               Comida Casera
            </a>
            <a href="${pageContext.request.contextPath}/lugares?categoria=Restaurante" 
               class="filtro-btn <%= "Restaurante".equalsIgnoreCase(categoriaSeleccionada) ? "active" : "" %>">
               Restaurantes
            </a>
        </div>

        <% if (lugares == null || lugares.isEmpty()) { %>
            <div class="alert alert-danger" style="text-align: center; padding: 3rem;">
                <h3>🍽️ No se encontraron lugares gastronómicos.</h3>
                <p>¡Sé el primero en agregar una picada o food truck de Linares!</p>
                <% if (usuarioSesion != null) { %>
                    <br>
                    <a href="${pageContext.request.contextPath}/lugares?accion=nuevo" class="btn btn-primary">Publicar Lugar Ahora</a>
                <% } %>
            </div>
        <% } else { %>
            <div class="lugares-grid">
                <% for (Lugar lugar : lugares) { %>
                    <div class="card-lugar">
                        <div class="card-img-wrapper">
                            <% if (lugar.getImagen() != null && !lugar.getImagen().trim().isEmpty()) { %>
                                <img src="<%= lugar.getImagen() %>" alt="<%= lugar.getNombre() %>" class="card-img">
                            <% } else { %>
                                <div class="card-img-placeholder">🍟</div>
                            <% } %>
                            <span class="badge-categoria"><%= lugar.getCategoria() %></span>
                            <% if (usuarioSesion != null && usuarioSesion.getRol() == Usuario.Rol.ADMIN) { %>
                                <span class="badge-estado estado-<%= lugar.getEstado() %>"><%= lugar.getEstado() %></span>
                            <% } %>
                        </div>
                        <div class="card-body">
                            <h2 class="card-title"><%= lugar.getNombre() %></h2>
                            <p class="card-desc"><%= lugar.getDescripcion() %></p>
                            <div class="card-info">
                                <div><strong>📍 Dirección:</strong> <%= lugar.getDireccion() != null ? lugar.getDireccion() : "Linares, Chile" %></div>
                                <div><strong>⏱️ Horario:</strong> <%= lugar.getHorario() != null ? lugar.getHorario() : "No especificado" %></div>
                                <div><strong>💰 Precio:</strong> <%= lugar.getPrecio() != null ? lugar.getPrecio() : "$" %></div>
                            </div>
                            <div class="card-footer">
                                <a href="${pageContext.request.contextPath}/resenas?lugarId=<%= lugar.getId() %>" class="btn btn-primary btn-sm">
                                    💬 Reseñas
                                </a>
                                <% if (usuarioSesion != null) { %>
                                    <a href="${pageContext.request.contextPath}/lugares?accion=editar&id=<%= lugar.getId() %>" class="btn btn-outline btn-sm">
                                        ✏️ Editar
                                    </a>
                                    <% if (usuarioSesion.getRol() == Usuario.Rol.ADMIN) { %>
                                        <a href="${pageContext.request.contextPath}/lugares?accion=eliminar&id=<%= lugar.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro de eliminar este lugar?');">
                                            🗑️
                                        </a>
                                    <% } %>
                                <% } %>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>

    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — La guía gastronómica de Linares, Chile.</p>
    </footer>

</body>
</html>