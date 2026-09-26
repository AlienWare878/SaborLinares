package cl.saborlinares.saborlinares.servlet;

import cl.saborlinares.dao.UsuarioDAO;
import cl.saborlinares.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/registro.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = new Usuario(
                0,
                nombre,
                correo,
                contrasena,
                Usuario.Rol.USUARIO
        );

        boolean registrado = usuarioDAO.insertar(usuario);

        if (registrado) {
            response.sendRedirect(request.getContextPath() + "/registro?exito=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/registro?error=true");
        }
    }
}
