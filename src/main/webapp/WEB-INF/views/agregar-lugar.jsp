<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cl.saborlinares.model.Lugar" %>
<%@ page import="cl.saborlinares.model.Usuario" %>
<%
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
    Lugar lugar = (Lugar) request.getAttribute("lugar");
    boolean esEdicion = (lugar != null);
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Lugar" : "Agregar Lugar" %> - Sabor Linares</title>
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
                    <li><a href="${pageContext.request.contextPath}/mapa">Mapa</a></li>
                    <% if (usuarioSesion != null) { %>
                        <li><a href="${pageContext.request.contextPath}/lugares?accion=nuevo" class="active">+ Publicar Lugar</a></li>
                        <% if (usuarioSesion.getRol() == Usuario.Rol.ADMIN) { %>
                            <li><a href="${pageContext.request.contextPath}/lugares?accion=admin">Moderación</a></li>
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
        <div class="form-card">
            <h1><%= esEdicion ? "✏️ Editar Lugar Gastronómico" : "➕ Publicar Nuevo Lugar" %></h1>
            <p style="color: var(--color-gris); margin-bottom: 1.5rem;">
                <%= esEdicion ? "Actualiza la información de tu local gastronómico." : "Comparte un carrito, food truck o local de Linares con la comunidad." %>
            </p>

            <% if (error != null) { %>
                <div class="alert alert-danger">
                    ⚠️ <%= error %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/lugares" method="post">
                <% if (esEdicion) { %>
                    <input type="hidden" name="id" value="<%= lugar.getId() %>">
                <% } %>

                <div class="form-group">
                    <label for="nombre">Nombre del local:</label>
                    <input type="text" id="nombre" name="nombre" class="form-control" 
                           value="<%= esEdicion ? lugar.getNombre() : "" %>" required placeholder="Ej. El Carrito Sabroso">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción:</label>
                    <textarea id="descripcion" name="descripcion" class="form-control" rows="3" required placeholder="Cuenta un poco sobre la especialidad, ambiente o recomendaciones..."><%= esEdicion ? lugar.getDescripcion() : "" %></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="categoria">Categoría:</label>
                        <select id="categoria" name="categoria" class="form-control" required>
                            <option value="">-- Selecciona una categoría --</option>
                            <option value="Food Truck" <%= (esEdicion && "Food Truck".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Food Truck</option>
                            <option value="Carrito" <%= (esEdicion && "Carrito".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Carrito de comida</option>
                            <option value="Cafetería" <%= (esEdicion && "Cafetería".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Cafetería / Panadería</option>
                            <option value="Comida Casera" <%= (esEdicion && "Comida Casera".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Comida Casera</option>
                            <option value="Restaurante" <%= (esEdicion && "Restaurante".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Restaurante</option>
                            <option value="OTRO" <%= (esEdicion && "OTRO".equalsIgnoreCase(lugar.getCategoria())) ? "selected" : "" %>>Otro</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="direccion">Dirección o Referencia:</label>
                        <input type="text" id="direccion" name="direccion" class="form-control" 
                               value="<%= esEdicion && lugar.getDireccion() != null ? lugar.getDireccion() : "" %>" placeholder="Ej. Av. Aníbal Pinto #450, Linares">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="horario">Horario de atención:</label>
                        <input type="text" id="horario" name="horario" class="form-control" 
                               value="<%= esEdicion && lugar.getHorario() != null ? lugar.getHorario() : "" %>" placeholder="Ej. Lunes a Sábado 12:00 - 22:00">
                    </div>

                    <div class="form-group">
                        <label for="precio">Rango de precio:</label>
                        <select id="precio" name="precio" class="form-control">
                            <option value="$" <%= (esEdicion && "$".equals(lugar.getPrecio())) ? "selected" : "" %>>$ (Económico)</option>
                            <option value="$$" <%= (esEdicion && "$$".equals(lugar.getPrecio())) ? "selected" : "" %>>$$ (Medio)</option>
                            <option value="$$$" <%= (esEdicion && "$$$".equals(lugar.getPrecio())) ? "selected" : "" %>>$$$ (Alto)</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="imagen">URL de la imagen (opcional):</label>
                    <input type="url" id="imagen" name="imagen" class="form-control" 
                           value="<%= esEdicion && lugar.getImagen() != null ? lugar.getImagen() : "" %>" placeholder="https://ejemplo.com/foto.jpg">
                </div>

                <!-- Selección de Ubicación interactiva con Mapa Leaflet -->
                <div class="form-group">
                    <label>📍 Ubicación en el mapa (Haz click en el mapa para marcar):</label>
                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:0.5rem;">
                        <span style="font-size:0.85rem; color:var(--color-gris);">Haz click exacto sobre el mapa para fijar la posición.</span>
                        <button type="button" id="btnGeolocalizar" class="btn btn-secondary btn-sm">🎯 Mi Ubicación Actual</button>
                    </div>
                    
                    <div id="mapaPicker" class="map-picker"></div>

                    <div class="form-row" style="margin-top:0.8rem;">
                        <div>
                            <label for="latitud" style="font-size:0.85rem;">Latitud:</label>
                            <input type="number" step="any" id="latitud" name="latitud" class="form-control" 
                                   value="<%= esEdicion ? lugar.getLatitud() : "-35.8467" %>" readonly required>
                        </div>
                        <div>
                            <label for="longitud" style="font-size:0.85rem;">Longitud:</label>
                            <input type="number" step="any" id="longitud" name="longitud" class="form-control" 
                                   value="<%= esEdicion ? lugar.getLongitud() : "-71.5931" %>" readonly required>
                        </div>
                    </div>
                </div>

                <div style="margin-top: 1.5rem; display: flex; gap: 1rem;">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">
                        <%= esEdicion ? "💾 Guardar Cambios" : "🚀 Publicar Lugar" %>
                    </button>
                    <a href="${pageContext.request.contextPath}/lugares" class="btn btn-outline">Cancelar</a>
                </div>
            </form>
        </div>
    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — Plataforma Comunitaria Gastronómica</p>
    </footer>

    <!-- Leaflet JS -->
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const latInput = document.getElementById("latitud");
            const lngInput = document.getElementById("longitud");
            
            let initialLat = parseFloat(latInput.value) || -35.8467;
            let initialLng = parseFloat(lngInput.value) || -71.5931;

            const map = L.map('mapaPicker').setView([initialLat, initialLng], 14);

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '&copy; OpenStreetMap'
            }).addTo(map);

            let marker = L.marker([initialLat, initialLng], { draggable: true }).addTo(map);

            function updateInputs(lat, lng) {
                latInput.value = lat.toFixed(6);
                lngInput.value = lng.toFixed(6);
            }

            map.on('click', function(e) {
                const lat = e.latlng.lat;
                const lng = e.latlng.lng;
                marker.setLatLng([lat, lng]);
                updateInputs(lat, lng);
            });

            marker.on('dragend', function(e) {
                const position = marker.getLatLng();
                updateInputs(position.lat, position.lng);
            });

            document.getElementById("btnGeolocalizar").addEventListener("click", function() {
                if ("geolocation" in navigator) {
                    navigator.geolocation.getCurrentPosition(function(position) {
                        const lat = position.coords.latitude;
                        const lng = position.coords.longitude;
                        map.setView([lat, lng], 16);
                        marker.setLatLng([lat, lng]);
                        updateInputs(lat, lng);
                    }, function(error) {
                        alert("No se pudo obtener la ubicación actual: " + error.message);
                    });
                } else {
                    alert("Tu navegador no soporta geolocalización.");
                }
            });
        });
    </script>
</body>
</html>