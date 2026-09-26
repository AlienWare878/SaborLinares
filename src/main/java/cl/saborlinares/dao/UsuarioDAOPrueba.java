package cl.saborlinares.dao;

import cl.saborlinares.model.Usuario;
import java.util.List;

public class UsuarioDAOPrueba {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.println("===== PRUEBA UsuarioDAO =====");

        // 1. INSERTAR
        Usuario usuario = new Usuario(
                0,
                "Usuario Prueba",
                "prueba@saborlinares.cl",
                "123456",
                Usuario.Rol.USUARIO
        );

        System.out.println("\n--- 1. INSERTAR ---");

        boolean insertado = usuarioDAO.insertar(usuario);

        System.out.println("Resultado insertar: " + insertado);

        if (!insertado) {
            System.out.println("No se pudo insertar el usuario. Se detiene la prueba.");
            return;
        }

        // 2. BUSCAR POR CORREO
        System.out.println("\n--- 2. BUSCAR POR CORREO ---");

        Usuario usuarioEncontrado = usuarioDAO.buscarPorCorreo("prueba@saborlinares.cl");

        if (usuarioEncontrado != null) {
            System.out.println("Usuario encontrado:");
            System.out.println("ID: " + usuarioEncontrado.getId());
            System.out.println("Nombre: " + usuarioEncontrado.getNombre());
            System.out.println("Correo: " + usuarioEncontrado.getCorreo());
            System.out.println("Rol: " + usuarioEncontrado.getRol());
        } else {
            System.out.println("No se encontró el usuario.");
        }

        // 3. BUSCAR POR ID
        System.out.println("\n--- 3. BUSCAR POR ID ---");

        if (usuarioEncontrado != null) {

            int id = usuarioEncontrado.getId();

            Usuario usuarioPorId = usuarioDAO.buscarPorId(id);

            if (usuarioPorId != null) {
                System.out.println("Usuario encontrado por ID:");
                System.out.println("ID: " + usuarioPorId.getId());
                System.out.println("Nombre: " + usuarioPorId.getNombre());
                System.out.println("Correo: " + usuarioPorId.getCorreo());
                System.out.println("Rol: " + usuarioPorId.getRol());
            } else {
                System.out.println("No se encontró el usuario por ID.");
            }

            // 4. ACTUALIZAR
            System.out.println("\n--- 4. ACTUALIZAR ---");

            usuarioEncontrado.setNombre("Usuario Prueba Actualizado");
            usuarioEncontrado.setRol(Usuario.Rol.EMPRENDEDOR);

            boolean actualizado = usuarioDAO.actualizar(usuarioEncontrado);

            System.out.println("Resultado actualizar: " + actualizado);

            // 5. LISTAR
            System.out.println("\n--- 5. LISTAR USUARIOS ---");

            List<Usuario> usuarios = usuarioDAO.listarUsuarios();

            System.out.println("Cantidad de usuarios: " + usuarios.size());

            for (Usuario u : usuarios) {
                System.out.println(
                        "ID: " + u.getId()
                        + " | Nombre: " + u.getNombre()
                        + " | Correo: " + u.getCorreo()
                        + " | Rol: " + u.getRol()
                );
            }

            // 6. ELIMINAR
            System.out.println("\n--- 6. ELIMINAR ---");

            boolean eliminado = usuarioDAO.eliminar(id);

            System.out.println("Resultado eliminar: " + eliminado);
        }

        System.out.println("\n===== FIN DE LA PRUEBA =====");
    }
}
