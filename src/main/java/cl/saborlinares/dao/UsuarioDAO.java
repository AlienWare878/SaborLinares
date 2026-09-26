package cl.saborlinares.dao;

import cl.saborlinares.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de acceder a la tabla "usuarios" de la base de datos
 * sabor_linares y transformar sus registros en objetos Usuario.
 */
public class UsuarioDAO {

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return lista de objetos Usuario. Si no hay registros, devuelve una lista vacía.
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = convertirUsuario(resultSet);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id identificador del usuario.
     * @return el usuario encontrado o null si no existe.
     */
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return convertirUsuario(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el usuario: " + e.getMessage());
        }

        return null;
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo correo electrónico del usuario.
     * @return el usuario encontrado o null si no existe.
     */
    public Usuario buscarPorCorreo(String correo) {

        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, correo);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return convertirUsuario(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar el usuario por correo: " + e.getMessage());
        }

        return null;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario objeto Usuario que se desea insertar.
     * @return true si la inserción fue exitosa.
     */
    public boolean insertar(Usuario usuario) {

        String sql = "INSERT INTO usuarios "
                + "(nombre, correo, contrasena, rol) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getCorreo());
            statement.setString(3, usuario.getContrasena());
            statement.setString(4, usuario.getRol().name());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario objeto Usuario con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET "
                + "nombre = ?, "
                + "correo = ?, "
                + "contrasena = ?, "
                + "rol = ? "
                + "WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getCorreo());
            statement.setString(3, usuario.getContrasena());
            statement.setString(4, usuario.getRol().name());
            statement.setInt(5, usuario.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id identificador del usuario.
     * @return true si la eliminación fue exitosa.
     */
    public boolean eliminar(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte la fila actual de un ResultSet en un objeto Usuario.
     *
     * @param resultSet resultado de una consulta sobre la tabla usuarios.
     * @return objeto Usuario construido con los datos de la fila.
     * @throws SQLException si ocurre un error al obtener algún dato.
     */
    private Usuario convertirUsuario(ResultSet resultSet) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(resultSet.getInt("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setCorreo(resultSet.getString("correo"));
        usuario.setContrasena(resultSet.getString("contrasena"));
        usuario.setRol(
                Usuario.Rol.valueOf(resultSet.getString("rol"))
        );

        return usuario;
    }
}
