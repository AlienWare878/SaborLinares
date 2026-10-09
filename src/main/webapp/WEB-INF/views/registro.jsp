<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crear Cuenta - Sabor Linares</title>
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
                    <li><a href="${pageContext.request.contextPath}/login">Iniciar Sesión</a></li>
                    <li><a href="${pageContext.request.contextPath}/registro" class="active">Registrarse</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <main class="main-container">
        <div class="form-card" style="max-width: 450px;">
            <h1 style="text-align: center; margin-bottom: 0.5rem;">📝 Crear Cuenta</h1>
            <p style="text-align: center; color: var(--color-gris); margin-bottom: 1.5rem;">
                Únete a la comunidad gastronómica de Linares.
            </p>

            <% if ("true".equals(request.getParameter("exito"))) { %>
                <div class="alert alert-success">
                    🎉 ¡Registro exitoso! Ya puedes <a href="${pageContext.request.contextPath}/login" style="color: var(--color-verde-aprobado); font-weight: bold; text-decoration: underline;">iniciar sesión</a>.
                </div>
            <% } %>

            <% if ("true".equals(request.getParameter("error"))) { %>
                <div class="alert alert-danger">
                    ❌ No se pudo completar el registro. Es posible que el correo ya esté registrado.
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/registro" method="post">

                <div class="form-group">
                    <label for="nombre">Nombre completo:</label>
                    <input type="text" id="nombre" name="nombre" class="form-control" required placeholder="Juan Pérez">
                </div>

                <div class="form-group">
                    <label for="correo">Correo electrónico:</label>
                    <input type="email" id="correo" name="correo" class="form-control" required placeholder="juan@ejemplo.com">
                </div>

                <div class="form-group">
                    <label for="contrasena">Contraseña:</label>
                    <input type="password" id="contrasena" name="contrasena" class="form-control" required placeholder="••••••••">
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem;">
                    Crear mi cuenta
                </button>

            </form>

            <div style="text-align: center; margin-top: 1.5rem; font-size: 0.9rem;">
                ¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login" style="color: var(--color-naranja-oscuro); font-weight: bold;">Inicia sesión aquí</a>
            </div>
        </div>
    </main>

    <footer>
        <p><strong>Sabor Linares</strong> &copy; 2026 — Plataforma Comunitaria Gastronómica</p>
    </footer>

</body>
</html>
