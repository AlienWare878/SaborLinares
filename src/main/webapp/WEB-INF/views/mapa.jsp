<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cl.saborlinares.model.Usuario" %>
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mapa Gastronómico - Sabor Linares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
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
                    <li><a href="${pageContext.request.contextPath}/mapa" class="active">Mapa</a></li>
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
                <h1>🗺️ Mapa Gastronómico de Linares</h1>
                <p>Explora todos los carritos, food trucks y picadas recomendadas en Linares.</p>
            </div>
            <div>
                <% if (usuarioSesion != null) { %>
                    <a href="${pageContext.request.contextPath}/lugares?accion=nuevo" class="btn btn-primary">+ Agregar mi local</a>
                <% } %>
            </div>
        </div>

        <div id="mapa" class="map-container" style="height: 550px;"></div>
    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — Plataforma Comunitaria Gastronómica de Linares, Chile.</p>
    </footer>

    <!-- Leaflet JS -->
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Inicializar mapa centrado en Linares, Chile
            const mapa = L.map('mapa').setView([-35.8467, -71.5931], 14);

            // Capa de OpenStreetMap
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(mapa);

            // Cargar datos desde el API de lugares
            fetch('${pageContext.request.contextPath}/api/lugares')
                .then(response => response.json())
                .then(lugares => {
                    if (!lugares || lugares.length === 0) {
                        console.log("No hay lugares para mostrar en el mapa.");
                        return;
                    }

                    const bounds = [];

                    lugares.forEach(lugar => {
                        if (lugar.latitud && lugar.longitud) {
                            const marker = L.marker([lugar.latitud, lugar.longitud]).addTo(mapa);
                            bounds.push([lugar.latitud, lugar.longitud]);

                            const popupContent = `
                                <div style="font-family: system-ui; max-width: 220px;">
                                    <h3 style="margin: 0 0 5px 0; color: #E76F00;">\${lugar.nombre}</h3>
                                    <p style="margin: 0 0 5px 0; font-size: 0.85rem; color: #756F6A;">
                                        <b>Categoría:</b> \${lugar.categoria}<br>
                                        \${lugar.direccion ? '<b>Dirección:</b> ' + lugar.direccion + '<br>' : ''}
                                        \${lugar.horario ? '<b>Horario:</b> ' + lugar.horario + '<br>' : ''}
                                        \${lugar.precio ? '<b>Precio:</b> ' + lugar.precio : ''}
                                    </p>
                                    <p style="margin: 5px 0; font-size: 0.9rem;">\${lugar.descripcion || ''}</p>
                                    <a href="${pageContext.request.contextPath}/resenas?lugarId=\${lugar.id}" 
                                       style="display: inline-block; background-color: #F28C28; color: white; padding: 6px 12px; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 0.85rem; margin-top: 5px;">
                                       Ver Reseñas / Detalles 💬
                                    </a>
                                </div>
                            `;

                            marker.bindPopup(popupContent);
                        }
                    });

                    if (bounds.length > 0) {
                        mapa.fitBounds(bounds, { padding: [50, 50] });
                    }
                })
                .catch(err => console.error("Error al obtener los lugares del mapa:", err));
        });
    </script>
</body>
</html>
