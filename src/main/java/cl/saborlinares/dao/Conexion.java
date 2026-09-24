package cl.saborlinares.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer la conexión JDBC con la base de datos
 * MySQL "sabor_linares" (gestionada localmente mediante Laragon).
 */
public class Conexion {

    // TODO: Completa estos valores según tu configuración de Laragon
    private static final String URL = "jdbc:mysql://localhost:3306/sabor_linares";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    // Cargar explícitamente el driver JDBC de MySQL para entornos de servlets / Tomcat
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver MySQL JDBC: " + e.getMessage());
        }
    }

    // Constructor privado: esta clase no debe ser instanciada
    private Conexion() {
    }

    /**
     * Obtiene una nueva conexión activa hacia la base de datos sabor_linares.
     *
     * @return objeto Connection listo para ser utilizado por un DAO.
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos sabor_linares: " + e.getMessage());
            throw e;
        }
    }
}