package cl.saborlinares.saborlinares.servlet;

import cl.saborlinares.dao.LugarDAO;
import cl.saborlinares.model.Lugar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/lugares")
public class LugaresApiServlet extends HttpServlet {

    private LugarDAO lugarDAO;

    @Override
    public void init() {
        lugarDAO = new LugarDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Lugar> todos = lugarDAO.listarLugares();

        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean primero = true;

        for (Lugar lugar : todos) {
            if (lugar.getEstado() == Lugar.Estado.APROBADO) {
                if (!primero) {
                    json.append(",");
                }
                primero = false;
                json.append("{");
                json.append("\"id\":").append(lugar.getId()).append(",");
                json.append("\"nombre\":").append(escapeJson(lugar.getNombre())).append(",");
                json.append("\"descripcion\":").append(escapeJson(lugar.getDescripcion())).append(",");
                json.append("\"categoria\":").append(escapeJson(lugar.getCategoria())).append(",");
                json.append("\"direccion\":").append(escapeJson(lugar.getDireccion())).append(",");
                json.append("\"latitud\":").append(lugar.getLatitud()).append(",");
                json.append("\"longitud\":").append(lugar.getLongitud()).append(",");
                json.append("\"horario\":").append(escapeJson(lugar.getHorario())).append(",");
                json.append("\"precio\":").append(escapeJson(lugar.getPrecio())).append(",");
                json.append("\"imagen\":").append(escapeJson(lugar.getImagen())).append(",");
                json.append("\"estado\":").append(escapeJson(lugar.getEstado().name()));
                json.append("}");
            }
        }
        json.append("]");

        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
            out.flush();
        }
    }

    private String escapeJson(String texto) {
        if (texto == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder("\"");
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"': builder.append("\\\""); break;
                case '\\': builder.append("\\\\"); break;
                case '\b': builder.append("\\b"); break;
                case '\f': builder.append("\\f"); break;
                case '\n': builder.append("\\n"); break;
                case '\r': builder.append("\\r"); break;
                case '\t': builder.append("\\t"); break;
                default:
                    if (c < ' ') {
                        String hex = String.format("\\u%04x", (int) c);
                        builder.append(hex);
                    } else {
                        builder.append(c);
                    }
            }
        }
        builder.append("\"");
        return builder.toString();
    }
}
