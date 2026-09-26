package cl.saborlinares.saborlinares.servlet;

import cl.saborlinares.dao.LugarDAO;
import cl.saborlinares.model.Lugar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LugarServlet", urlPatterns = {"/lugares"})
public class LugarServlet extends HttpServlet {

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String accion = request.getParameter("accion");

if ("nuevo".equals(accion)) {
    request.getRequestDispatcher("/WEB-INF/views/agregar-lugar.jsp")
            .forward(request, response);
    return;
}

    System.out.println(">>> LugarServlet fue ejecutado <<<");

    LugarDAO lugarDAO = new LugarDAO();

    List<Lugar> lugares = lugarDAO.listarLugares();

    System.out.println(">>> Lugares encontrados: " + lugares.size());

    for (Lugar lugar : lugares) {
        System.out.println(">>> Lugar: " + lugar.getNombre());
    }

    request.setAttribute("lugares", lugares);

    request.getRequestDispatcher("/WEB-INF/views/lugares.jsp")
                       .forward(request, response);
}
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String nombre = request.getParameter("nombre");
    String descripcion = request.getParameter("descripcion");
    String categoria = request.getParameter("categoria");
    String direccion = request.getParameter("direccion");
    String latitudParametro = request.getParameter("latitud");
    String longitudParametro = request.getParameter("longitud");
    String horario = request.getParameter("horario");
    String precio = request.getParameter("precio");

    double latitud;
    double longitud;

    try {
        latitud = Double.parseDouble(latitudParametro);
        longitud = Double.parseDouble(longitudParametro);
    } catch (NumberFormatException | NullPointerException e) {
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "La latitud o longitud no son válidas."
        );
        return;
    }

    Lugar lugar = new Lugar(
            0,
            nombre,
            descripcion,
            categoria,
            direccion,
            latitud,
            longitud,
            horario,
            precio,
            null,
            Lugar.Estado.PENDIENTE
    );

    LugarDAO lugarDAO = new LugarDAO();

    boolean insertado = lugarDAO.insertar(lugar);

    if (insertado) {
        response.sendRedirect(request.getContextPath() + "/lugares");
    } else {
        response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "No se pudo guardar el lugar."
        );
    }
}
}