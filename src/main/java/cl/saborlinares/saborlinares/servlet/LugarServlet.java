package cl.saborlinares.saborlinares.servlet;

import cl.saborlinares.dao.LugarDAO;
import cl.saborlinares.model.Lugar;
import cl.saborlinares.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LugarServlet", urlPatterns = {"/lugares"})
public class LugarServlet extends HttpServlet {

    private LugarDAO lugarDAO;

    @Override
    public void init() {
        lugarDAO = new LugarDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession sesion = request.getSession(false);
        Usuario usuarioSesion = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if ("nuevo".equals(accion)) {
            if (usuarioSesion == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/agregar-lugar.jsp")
                    .forward(request, response);
            return;
        }

        if ("editar".equals(accion)) {
            if (usuarioSesion == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    Lugar lugar = lugarDAO.buscarPorId(id);
                    if (lugar != null) {
                        request.setAttribute("lugar", lugar);
                        request.getRequestDispatcher("/WEB-INF/views/agregar-lugar.jsp")
                                .forward(request, response);
                        return;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            response.sendRedirect(request.getContextPath() + "/lugares");
            return;
        }

        if ("eliminar".equals(accion)) {
            if (usuarioSesion == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    boolean eliminado = lugarDAO.eliminar(id);
                    if (!eliminado) {
                        System.err.println("No se pudo eliminar el lugar con ID: " + id);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("ID inválido para eliminación: " + idParam);
                }
            }
            String referer = request.getHeader("Referer");
            if (referer != null && referer.contains("accion=admin")) {
                response.sendRedirect(request.getContextPath() + "/lugares?accion=admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/lugares");
            }
            return;
        }

        if ("admin".equals(accion)) {
            if (usuarioSesion == null || usuarioSesion.getRol() != Usuario.Rol.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso solo para administradores.");
                return;
            }
            List<Lugar> todosLosLugares = lugarDAO.listarLugares();
            List<Lugar> pendientes = new ArrayList<>();
            for (Lugar l : todosLosLugares) {
                if (l.getEstado() == Lugar.Estado.PENDIENTE) {
                    pendientes.add(l);
                }
            }
            request.setAttribute("lugaresPendientes", pendientes);
            request.setAttribute("todosLosLugares", todosLosLugares);
            request.getRequestDispatcher("/WEB-INF/views/admin-lugares.jsp")
                    .forward(request, response);
            return;
        }

        if ("aprobar".equals(accion) || "rechazar".equals(accion)) {
            if (usuarioSesion == null || usuarioSesion.getRol() != Usuario.Rol.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso solo para administradores.");
                return;
            }
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    Lugar lugar = lugarDAO.buscarPorId(id);
                    if (lugar != null) {
                        if ("aprobar".equals(accion)) {
                            lugar.setEstado(Lugar.Estado.APROBADO);
                        } else {
                            lugar.setEstado(Lugar.Estado.RECHAZADO);
                        }
                        lugarDAO.actualizar(lugar);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            response.sendRedirect(request.getContextPath() + "/lugares?accion=admin");
            return;
        }

        // Listar lugares (Por defecto muestra todos o filtra por categoría si viene en param)
        List<Lugar> listaCompleta = lugarDAO.listarLugares();
        String categoriaFiltro = request.getParameter("categoria");
        List<Lugar> lugaresFiltrados = new ArrayList<>();

        for (Lugar l : listaCompleta) {
            // Si es ADMIN o si el lugar está APROBADO, o si es el creador/solicitante
            boolean visible = (l.getEstado() == Lugar.Estado.APROBADO)
                    || (usuarioSesion != null && usuarioSesion.getRol() == Usuario.Rol.ADMIN);

            if (visible) {
                if (categoriaFiltro != null && !categoriaFiltro.trim().isEmpty() && !"TODAS".equalsIgnoreCase(categoriaFiltro)) {
                    if (categoriaFiltro.equalsIgnoreCase(l.getCategoria())) {
                        lugaresFiltrados.add(l);
                    }
                } else {
                    lugaresFiltrados.add(l);
                }
            }
        }

        request.setAttribute("lugares", lugaresFiltrados);
        request.setAttribute("categoriaSeleccionada", categoriaFiltro);
        request.getRequestDispatcher("/WEB-INF/views/lugares.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sesion = request.getSession(false);
        Usuario usuarioSesion = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        if (usuarioSesion == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String categoria = request.getParameter("categoria");
        String direccion = request.getParameter("direccion");
        String latitudParametro = request.getParameter("latitud");
        String longitudParametro = request.getParameter("longitud");
        String horario = request.getParameter("horario");
        String precio = request.getParameter("precio");
        String imagen = request.getParameter("imagen");

        if (imagen != null && imagen.trim().isEmpty()) {
            imagen = null;
        }

        double latitud;
        double longitud;

        try {
            latitud = Double.parseDouble(latitudParametro);
            longitud = Double.parseDouble(longitudParametro);

            if (latitud < -90.0 || latitud > 90.0 || longitud < -180.0 || longitud > 180.0) {
                request.setAttribute("error", "Las coordenadas deben estar dentro del rango válido (Latitud -90 a 90, Longitud -180 a 180).");
                if (idParam != null && !idParam.isEmpty()) {
                    try {
                        request.setAttribute("lugar", lugarDAO.buscarPorId(Integer.parseInt(idParam)));
                    } catch (Exception ignored) {}
                }
                request.getRequestDispatcher("/WEB-INF/views/agregar-lugar.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("error", "La latitud o longitud no son números válidos.");
            request.getRequestDispatcher("/WEB-INF/views/agregar-lugar.jsp").forward(request, response);
            return;
        }

        int id = 0;
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException ignored) {
            }
        }

        if (id > 0) {
            // Actualizar
            Lugar lugarExistente = lugarDAO.buscarPorId(id);
            if (lugarExistente != null) {
                lugarExistente.setNombre(nombre);
                lugarExistente.setDescripcion(descripcion);
                lugarExistente.setCategoria(categoria);
                lugarExistente.setDireccion(direccion);
                lugarExistente.setLatitud(latitud);
                lugarExistente.setLongitud(longitud);
                lugarExistente.setHorario(horario);
                lugarExistente.setPrecio(precio);
                lugarExistente.setImagen(imagen);

                boolean actualizado = lugarDAO.actualizar(lugarExistente);
                if (actualizado) {
                    response.sendRedirect(request.getContextPath() + "/lugares");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo actualizar el lugar.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "El lugar a actualizar no existe.");
            }
        } else {
            // Insertar nuevo
            Lugar nuevoLugar = new Lugar(
                    0,
                    nombre,
                    descripcion,
                    categoria,
                    direccion,
                    latitud,
                    longitud,
                    horario,
                    precio,
                    imagen,
                    Lugar.Estado.PENDIENTE
            );

            boolean insertado = lugarDAO.insertar(nuevoLugar);
            if (insertado) {
                response.sendRedirect(request.getContextPath() + "/lugares?exito=guardado");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar el lugar.");
            }
        }
    }
}