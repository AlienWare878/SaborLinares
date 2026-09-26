package cl.saborlinares.saborlinares.servlet;

import cl.saborlinares.dao.ResenaDAO;
import cl.saborlinares.model.Resena;
import cl.saborlinares.dao.LugarDAO;
import cl.saborlinares.model.Lugar;
import cl.saborlinares.model.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/resenas")
public class ResenaServlet extends HttpServlet {

    private ResenaDAO resenaDAO;

    @Override
    public void init() {
        resenaDAO = new ResenaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("lugarId");

        if (idParametro == null) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "No se especificó el ID del lugar."
            );
            return;
        }

        int lugarId;

        try {
            lugarId = Integer.parseInt(idParametro);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del lugar no es válido."
            );
            return;
        }

        List<Resena> resenas = resenaDAO.listarPorLugar(lugarId);

String editarIdParametro = request.getParameter("editarId");

if (editarIdParametro != null) {

    HttpSession sesion = request.getSession(false);

    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    Usuario usuarioSesion = (Usuario) sesion.getAttribute("usuario");

    int editarId;

    try {
        editarId = Integer.parseInt(editarIdParametro);
    } catch (NumberFormatException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "El ID de la reseña no es válido."
        );
        return;
    }

    Resena resenaEditar = resenaDAO.buscarPorId(editarId);

    if (resenaEditar == null) {
        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "La reseña no existe."
        );
        return;
    }

    if (resenaEditar.getUsuario() == null
            || resenaEditar.getUsuario().getId() != usuarioSesion.getId()) {

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "No tienes permiso para editar esta reseña."
        );
        return;
    }

    request.setAttribute("resenaEditar", resenaEditar);
}
String eliminarIdParametro = request.getParameter("eliminarId");

if (eliminarIdParametro != null) {

    HttpSession sesion = request.getSession(false);

    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    Usuario usuarioSesion = (Usuario) sesion.getAttribute("usuario");

    int eliminarId;

    try {
        eliminarId = Integer.parseInt(eliminarIdParametro);
    } catch (NumberFormatException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "El ID de la reseña no es válido."
        );
        return;
    }

    Resena resenaEliminar = resenaDAO.buscarPorId(eliminarId);

    if (resenaEliminar == null) {
        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "La reseña no existe."
        );
        return;
    }

    if (resenaEliminar.getUsuario() == null
            || resenaEliminar.getUsuario().getId() != usuarioSesion.getId()) {

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "No tienes permiso para eliminar esta reseña."
        );
        return;
    }

    boolean eliminada = resenaDAO.eliminar(eliminarId);

    if (eliminada) {
        response.sendRedirect(
                request.getContextPath()
                + "/resenas?lugarId=" + resenaEliminar.getLugar().getId()
        );
    } else {
        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo eliminar la reseña."
        );
    }

    return;
}


        request.setAttribute("resenas", resenas);
        request.setAttribute("lugarId", lugarId);

        request.getRequestDispatcher("/WEB-INF/views/resenas.jsp")
                .forward(request, response);
    }
    
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    // Obtener el usuario desde la sesión
    HttpSession sesion = request.getSession(false);

    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    Usuario usuario = (Usuario) sesion.getAttribute("usuario");

    // Obtener los datos enviados por el formulario
    String lugarIdParametro = request.getParameter("lugarId");
    String comentario = request.getParameter("comentario");
    String calificacionParametro = request.getParameter("calificacion");
    String resenaIdParametro = request.getParameter("resenaId");

    int lugarId;
    int calificacion;

    try {
        lugarId = Integer.parseInt(lugarIdParametro);
        calificacion = Integer.parseInt(calificacionParametro);
    } catch (NumberFormatException | NullPointerException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Los datos enviados no son válidos."
        );
        return;
    }
    
    if (resenaIdParametro != null) {

    int resenaId;

    try {
        resenaId = Integer.parseInt(resenaIdParametro);
    } catch (NumberFormatException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "El ID de la reseña no es válido."
        );
        return;
    }

    Resena resenaExistente = resenaDAO.buscarPorId(resenaId);

    if (resenaExistente == null) {
        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "La reseña no existe."
        );
        return;
    }

    if (resenaExistente.getUsuario() == null
            || resenaExistente.getUsuario().getId() != usuario.getId()) {

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "No tienes permiso para editar esta reseña."
        );
        return;
    }

    resenaExistente.setComentario(comentario);
    resenaExistente.setCalificacion(calificacion);

    boolean actualizada = resenaDAO.actualizar(resenaExistente);

    if (actualizada) {
        response.sendRedirect(
                request.getContextPath()
                + "/resenas?lugarId=" + lugarId
        );
    } else {
        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo actualizar la reseña."
        );
    }

    return;
}
    if (resenaIdParametro != null) {

    int resenaId;

    try {
        resenaId = Integer.parseInt(resenaIdParametro);
    } catch (NumberFormatException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "El ID de la reseña no es válido."
        );
        return;
    }

    Resena resenaExistente = resenaDAO.buscarPorId(resenaId);

    if (resenaExistente == null) {
        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "La reseña no existe."
        );
        return;
    }

    if (resenaExistente.getUsuario() == null
            || resenaExistente.getUsuario().getId() != usuario.getId()) {

        response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "No tienes permiso para editar esta reseña."
        );
        return;
    }

    resenaExistente.setComentario(comentario);
    resenaExistente.setCalificacion(calificacion);

    boolean actualizada = resenaDAO.actualizar(resenaExistente);

    if (actualizada) {
        response.sendRedirect(
                request.getContextPath()
                + "/resenas?lugarId=" + lugarId
        );
    } else {
        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo actualizar la reseña."
        );
    }

    return;
}

    // Buscar el lugar
    LugarDAO lugarDAO = new LugarDAO();
    Lugar lugar = lugarDAO.buscarPorId(lugarId);

    if (lugar == null) {
        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "El lugar no existe."
        );
        return;
    }

    // Crear la reseña
    Resena resena = new Resena(
            0,
            comentario,
            calificacion,
            null,
            usuario,
            lugar
    );

    boolean insertada = resenaDAO.insertar(resena);

    if (insertada) {
        response.sendRedirect(
                request.getContextPath()
                + "/resenas?lugarId=" + lugarId
        );
    } else {
        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo guardar la reseña."
        );
    }
}
}

