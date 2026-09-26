package cl.saborlinares.dao;

import cl.saborlinares.model.Lugar;
import cl.saborlinares.model.Resena;
import cl.saborlinares.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de acceder a la tabla "resenas"
 * de la base de datos sabor_linares.
 */
public class ResenaDAO {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LugarDAO lugarDAO = new LugarDAO();

    /**
     * Obtiene todas las reseñas almacenadas en la base de datos.
     *
     * @return lista de reseñas. Si no hay registros, devuelve una lista vacía.
     */
    public List<Resena> listarResenas() {
        List<Resena> resenas = new ArrayList<>();
        String sql = "SELECT * FROM resenas";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Resena resena = convertirResena(resultSet);
                resenas.add(resena);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar las reseñas: " + e.getMessage());
        }

        return resenas;
    }

    /**
     * Busca una reseña por su ID.
     *
     * @param id identificador de la reseña.
     * @return la reseña encontrada o null si no existe.
     */
    public Resena buscarPorId(int id) {
        String sql = "SELECT * FROM resenas WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return convertirResena(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar la reseña: " + e.getMessage());
        }

        return null;
    }

    /**
     * Obtiene todas las reseñas pertenecientes a un lugar.
     *
     * @param lugarId identificador del lugar.
     * @return lista de reseñas del lugar.
     */
    public List<Resena> listarPorLugar(int lugarId) {
        List<Resena> resenas = new ArrayList<>();
        String sql = "SELECT * FROM resenas WHERE lugar_id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, lugarId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Resena resena = convertirResena(resultSet);
                    resenas.add(resena);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar las reseñas del lugar: " + e.getMessage());
        }

        return resenas;
    }

    /**
     * Inserta una nueva reseña en la base de datos.
     *
     * @param resena reseña que se desea insertar.
     * @return true si la inserción fue exitosa.
     */
    public boolean insertar(Resena resena) {
        String sql = "INSERT INTO resenas "
                + "(comentario, calificacion, usuario_id, lugar_id) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, resena.getComentario());
            statement.setInt(2, resena.getCalificacion());
            statement.setInt(3, resena.getUsuario().getId());
            statement.setInt(4, resena.getLugar().getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar la reseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza una reseña existente.
     *
     * @param resena reseña con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public boolean actualizar(Resena resena) {
        String sql = "UPDATE resenas SET "
                + "comentario = ?, "
                + "calificacion = ?, "
                + "usuario_id = ?, "
                + "lugar_id = ? "
                + "WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, resena.getComentario());
            statement.setInt(2, resena.getCalificacion());
            statement.setInt(3, resena.getUsuario().getId());
            statement.setInt(4, resena.getLugar().getId());
            statement.setInt(5, resena.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar la reseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una reseña por su ID.
     *
     * @param id identificador de la reseña.
     * @return true si la eliminación fue exitosa.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM resenas WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar la reseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte un registro de la tabla resenas en un objeto Resena.
     */
    private Resena convertirResena(ResultSet resultSet) throws SQLException {

        Resena resena = new Resena();

        resena.setId(resultSet.getInt("id"));
        resena.setComentario(resultSet.getString("comentario"));
        resena.setCalificacion(resultSet.getInt("calificacion"));

        Timestamp timestamp = resultSet.getTimestamp("fecha");

        if (timestamp != null) {
            resena.setFecha(timestamp.toLocalDateTime());
        }

        int usuarioId = resultSet.getInt("usuario_id");
        int lugarId = resultSet.getInt("lugar_id");

        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        Lugar lugar = lugarDAO.buscarPorId(lugarId);

        resena.setUsuario(usuario);
        resena.setLugar(lugar);

        return resena;
    }
}
