package cl.saborlinares.dao;

import cl.saborlinares.model.Lugar;
import cl.saborlinares.model.Resena;
import cl.saborlinares.model.Usuario;

import java.util.List;

public class ResenaDAOPrueba {

    public static void main(String[] args) {

        ResenaDAO resenaDAO = new ResenaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LugarDAO lugarDAO = new LugarDAO();

        // Obtener datos reales de la base de datos
        Usuario usuario = usuarioDAO.buscarPorId(2);
        Lugar lugar = lugarDAO.buscarPorId(5);

        if (usuario == null) {
            System.out.println("No se encontró el usuario con ID 2.");
            return;
        }

        if (lugar == null) {
            System.out.println("No se encontró el lugar con ID 1.");
            return;
        }

        // ==========================================
        // 1. INSERTAR
        // ==========================================

        Resena resena = new Resena(
                0,
                "Excelente lugar para probar comida local.",
                5,
                null,
                usuario,
                lugar
        );

        boolean insertada = resenaDAO.insertar(resena);

        System.out.println("1. insertar(): " + insertada);

        if (!insertada) {
            System.out.println("No se pudo insertar la reseña.");
            return;
        }

        // ==========================================
        // 2. BUSCAR POR ID
        // ==========================================

        List<Resena> resenas = resenaDAO.listarResenas();

        if (resenas.isEmpty()) {
            System.out.println("No se encontraron reseñas.");
            return;
        }

        Resena resenaEncontrada = resenas.get(resenas.size() - 1);

        System.out.println("\n2. buscarPorId():");

        Resena buscada = resenaDAO.buscarPorId(resenaEncontrada.getId());

        if (buscada != null) {
            System.out.println("ID: " + buscada.getId());
            System.out.println("Comentario: " + buscada.getComentario());
            System.out.println("Calificación: " + buscada.getCalificacion());
            System.out.println("Usuario: " + buscada.getUsuario().getNombre());
            System.out.println("Lugar: " + buscada.getLugar().getNombre());
            System.out.println("Fecha: " + buscada.getFecha());
        }

        // ==========================================
        // 3. LISTAR POR LUGAR
        // ==========================================

        System.out.println("\n3. listarPorLugar():");

        List<Resena> resenasDelLugar = resenaDAO.listarPorLugar(lugar.getId());

        for (Resena r : resenasDelLugar) {
            System.out.println(
                    r.getId() + " - "
                    + r.getComentario() + " - "
                    + r.getCalificacion() + " estrellas"
            );
        }

        // ==========================================
        // 4. LISTAR TODAS
        // ==========================================

        System.out.println("\n4. listarResenas():");

        List<Resena> todas = resenaDAO.listarResenas();

        for (Resena r : todas) {
            System.out.println(
                    r.getId() + " - "
                    + r.getComentario() + " - "
                    + r.getUsuario().getNombre() + " - "
                    + r.getLugar().getNombre()
            );
        }

        // ==========================================
        // 5. ACTUALIZAR
        // ==========================================

        resenaEncontrada.setComentario(
                "Excelente lugar. La comida y la atención fueron muy buenas."
        );
        resenaEncontrada.setCalificacion(4);

        boolean actualizada = resenaDAO.actualizar(resenaEncontrada);

        System.out.println("\n5. actualizar(): " + actualizada);

        // Comprobar actualización
        Resena actualizadaResena =
                resenaDAO.buscarPorId(resenaEncontrada.getId());

        if (actualizadaResena != null) {
            System.out.println(
                    "Comentario actualizado: "
                    + actualizadaResena.getComentario()
            );

            System.out.println(
                    "Calificación actualizada: "
                    + actualizadaResena.getCalificacion()
            );
        }

        // ==========================================
        // 6. ELIMINAR
        // ==========================================

        boolean eliminada =
                resenaDAO.eliminar(resenaEncontrada.getId());

        System.out.println("\n6. eliminar(): " + eliminada);

        // Comprobar eliminación
        Resena eliminadaResena =
                resenaDAO.buscarPorId(resenaEncontrada.getId());

        System.out.println(
                "Resultado después de eliminar: "
                + eliminadaResena
        );
    }
}
