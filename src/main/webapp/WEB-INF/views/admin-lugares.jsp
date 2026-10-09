<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="cl.saborlinares.model.Lugar" %>
<%@ page import="cl.saborlinares.model.Usuario" %>
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    List<Lugar> lugaresPendientes = (List<Lugar>) request.getAttribute("lugaresPendientes");
    List<Lugar> todosLosLugares = (List<Lugar>) request.getAttribute("todosLosLugares");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Moderación de Lugares - Sabor Linares</title>
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
                            <li><a href="${pageContext.request.contextPath}/lugares?accion=admin" class="active">Moderación</a></li>
                        <% } %>
                        <li><span class="user-badge"><%= usuarioSesion.getNombre() %> (<%= usuarioSesion.getRol() %>)</span></li>
                        <li><a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a></li>
                    <% } else { %>
                        <li><a href="${pageContext.request.contextPath}/login">Iniciar Sesión</a></li>
                    <% } %>
                </ul>
            </nav>
        </div>
    </header>

    <main class="main-container">
        <div class="page-header">
            <div>
                <h1>🛡️ Panel de Moderación (Admin)</h1>
                <p>Revisa y aprueba los lugares registrados por la comunidad.</p>
            </div>
        </div>

        <h2>Lugares Pendientes de Aprobación</h2>
        <br>

        <% if (lugaresPendientes == null || lugaresPendientes.isEmpty()) { %>
            <div class="alert alert-success">
                ✅ No hay lugares pendientes de revisión en este momento.
            </div>
        <% } else { %>
            <div class="lugares-grid">
                <% for (Lugar lugar : lugaresPendientes) { %>
                    <div class="card-lugar">
                        <div class="card-img-wrapper">
                            <% if (lugar.getImagen() != null && !lugar.getImagen().trim().isEmpty()) { %>
                                <img src="<%= lugar.getImagen() %>" alt="<%= lugar.getNombre() %>" class="card-img">
                            <% } else { %>
                                <div class="card-img-placeholder">🍲</div>
                            <% } %>
                            <span class="badge-categoria"><%= lugar.getCategoria() %></span>
                            <span class="badge-estado estado-<%= lugar.getEstado() %>"><%= lugar.getEstado() %></span>
                        </div>
                        <div class="card-body">
                            <h3 class="card-title"><%= lugar.getNombre() %></h3>
                            <p class="card-desc"><%= lugar.getDescripcion() %></p>
                            <div class="card-info">
                                <div><strong>📍 Dirección:</strong> <%= lugar.getDireccion() != null ? lugar.getDireccion() : "No especificada" %></div>
                                <div><strong>⏱️ Horario:</strong> <%= lugar.getHorario() != null ? lugar.getHorario() : "No especificado" %></div>
                                <div><strong>💰 Precio:</strong> <%= lugar.getPrecio() != null ? lugar.getPrecio() : "No especificado" %></div>
                                <div><strong>🌐 Coordenadas:</strong> <%= lugar.getLatitud() %>, <%= lugar.getLongitud() %></div>
                            </div>
                            <div class="card-footer">
                                <a href="${pageContext.request.contextPath}/lugares?accion=aprobar&id=<%= lugar.getId() %>" class="btn btn-success btn-sm">
                                    ✓ Aprobar
                                </a>
                                <a href="${pageContext.request.contextPath}/lugares?accion=rechazar&id=<%= lugar.getId() %>" class="btn btn-danger btn-sm">
                                    ✗ Rechazar
                                </a>
                                <a href="${pageContext.request.contextPath}/lugares?accion=editar&id=<%= lugar.getId() %>" class="btn btn-outline btn-sm">
                                    ✏️ Editar
                                </a>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>

        <br><hr><br>

        <h2>Historial de Todos los Lugares</h2>
        <br>

        <% if (todosLosLugares != null && !todosLosLugares.isEmpty()) { %>
            <table style="width:100%; border-collapse: collapse; background:white; border-radius:12px; overflow:hidden; box-shadow: 0 4px 12px var(--color-sombra);">
                <thead>
                    <tr style="background-color: var(--color-naranja); color: white; text-align: left;">
                        <th style="padding: 12px;">ID</th>
                        <th style="padding: 12px;">Nombre</th>
                        <th style="padding: 12px;">Categoría</th>
                        <th style="padding: 12px;">Estado</th>
                        <th style="padding: 12px;">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Lugar lugar : todosLosLugares) { %>
                        <tr style="border-bottom: 1px solid var(--color-gris-claro);">
                            <td style="padding: 12px;"><%= lugar.getId() %></td>
                            <td style="padding: 12px;"><strong><%= lugar.getNombre() %></strong></td>
                            <td style="padding: 12px;"><%= lugar.getCategoria() %></td>
                            <td style="padding: 12px;">
                                <span class="badge-estado estado-<%= lugar.getEstado() %>"><%= lugar.getEstado() %></span>
                            </td>
                            <td style="padding: 12px;">
                                <% if (lugar.getEstado() != Lugar.Estado.APROBADO) { %>
                                    <a href="${pageContext.request.contextPath}/lugares?accion=aprobar&id=<%= lugar.getId() %>" class="btn btn-success btn-sm">Aprobar</a>
                                <% } %>
                                <% if (lugar.getEstado() != Lugar.Estado.RECHAZADO) { %>
                                    <a href="${pageContext.request.contextPath}/lugares?accion=rechazar&id=<%= lugar.getId() %>" class="btn btn-danger btn-sm">Rechazar</a>
                                <% } %>
                                <a href="${pageContext.request.contextPath}/lugares?accion=editar&id=<%= lugar.getId() %>" class="btn btn-outline btn-sm">Editar</a>
                                <a href="${pageContext.request.contextPath}/lugares?accion=eliminar&id=<%= lugar.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro de eliminar este lugar?');">Eliminar</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — Panel de Administración</p>
    </footer>

</body>
</html>
