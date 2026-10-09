package cl.saborlinares.dao;

import cl.saborlinares.model.Lugar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de acceder a la tabla "lugares" de la base de datos
 * sabor_linares y transformar sus registros en objetos Lugar.
 */
public class LugarDAO {

    /**
     * Obtiene todos los lugares almacenados en la base de datos.
     *
     * @return lista de objetos Lugar. Si no hay registros, devuelve una lista vacía.
     */
    public List<Lugar> listarLugares() {
        List<Lugar> lugares = new ArrayList<>();
        String sql = "SELECT * FROM lugares";

        try (Connection conexion = Conexion.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Lugar lugar = new Lugar();
                lugar.setId(resultSet.getInt("id"));
                lugar.setNombre(resultSet.getString("nombre"));
                lugar.setDescripcion(resultSet.getString("descripcion"));
                lugar.setCategoria(resultSet.getString("categoria"));
                lugar.setDireccion(resultSet.getString("direccion"));
                lugar.setLatitud(resultSet.getDouble("latitud"));
                lugar.setLongitud(resultSet.getDouble("longitud"));
                lugar.setHorario(resultSet.getString("horario"));
                lugar.setPrecio(resultSet.getString("precio"));
                lugar.setImagen(resultSet.getString("imagen"));
                lugar.setEstado(Lugar.Estado.valueOf(resultSet.getString("estado")));

                lugares.add(lugar);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los lugares: " + e.getMessage());
        }

        return lugares;
    }
    
    public Lugar buscarPorId(int id) {

    String sql = "SELECT * FROM lugares WHERE id = ?";

    try (Connection conexion = Conexion.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setInt(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                Lugar lugar = new Lugar();

                lugar.setId(resultSet.getInt("id"));
                lugar.setNombre(resultSet.getString("nombre"));
                lugar.setDescripcion(resultSet.getString("descripcion"));
                lugar.setCategoria(resultSet.getString("categoria"));
                lugar.setDireccion(resultSet.getString("direccion"));
                lugar.setLatitud(resultSet.getDouble("latitud"));
                lugar.setLongitud(resultSet.getDouble("longitud"));
                lugar.setHorario(resultSet.getString("horario"));
                lugar.setPrecio(resultSet.getString("precio"));
                lugar.setImagen(resultSet.getString("imagen"));
                lugar.setEstado(
                    Lugar.Estado.valueOf(resultSet.getString("estado"))
                );

                return lugar;
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al buscar el lugar: " + e.getMessage());
    }

    return null;
}

    public boolean insertar(Lugar lugar) {

    String sql = "INSERT INTO lugares "
            + "(nombre, descripcion, categoria, direccion, latitud, longitud, "
            + "horario, precio, imagen, estado) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = Conexion.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setString(1, lugar.getNombre());
        statement.setString(2, lugar.getDescripcion());
        statement.setString(3, lugar.getCategoria());
        statement.setString(4, lugar.getDireccion());
        statement.setDouble(5, lugar.getLatitud());
        statement.setDouble(6, lugar.getLongitud());
        statement.setString(7, lugar.getHorario());
        statement.setString(8, lugar.getPrecio());
        statement.setString(9, lugar.getImagen());
        statement.setString(10, lugar.getEstado().name());

        return statement.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al insertar el lugar: " + e.getMessage());
        return false;
    }
}
    
    public boolean actualizar(Lugar lugar) {

    String sql = "UPDATE lugares SET "
            + "nombre = ?, "
            + "descripcion = ?, "
            + "categoria = ?, "
            + "direccion = ?, "
            + "latitud = ?, "
            + "longitud = ?, "
            + "horario = ?, "
            + "precio = ?, "
            + "imagen = ?, "
            + "estado = ? "
            + "WHERE id = ?";

    try (Connection conexion = Conexion.obtenerConexion();
         PreparedStatement statement = conexion.prepareStatement(sql)) {

        statement.setString(1, lugar.getNombre());
        statement.setString(2, lugar.getDescripcion());
        statement.setString(3, lugar.getCategoria());
        statement.setString(4, lugar.getDireccion());
        statement.setDouble(5, lugar.getLatitud());
        statement.setDouble(6, lugar.getLongitud());
        statement.setString(7, lugar.getHorario());
        statement.setString(8, lugar.getPrecio());
        statement.setString(9, lugar.getImagen());
        statement.setString(10, lugar.getEstado().name());
        statement.setInt(11, lugar.getId());

        return statement.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar el lugar: " + e.getMessage());
        return false;
    }
    
    
}
    public boolean eliminar(int id) {

        String sqlResenas = "DELETE FROM resenas WHERE lugar_id = ?";
        String sqlLugar = "DELETE FROM lugares WHERE id = ?";

        try (Connection conexion = Conexion.obtenerConexion()) {

            // Eliminar reseñas asociadas para respetar la restricción de clave foránea
            try (PreparedStatement stmtResenas = conexion.prepareStatement(sqlResenas)) {
                stmtResenas.setInt(1, id);
                stmtResenas.executeUpdate();
            }

            // Eliminar el lugar
            try (PreparedStatement stmtLugar = conexion.prepareStatement(sqlLugar)) {
                stmtLugar.setInt(1, id);
                return stmtLugar.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar el lugar (ID " + id + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}