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
}