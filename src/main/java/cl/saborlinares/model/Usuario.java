package cl.saborlinares.model;

/**
 * Representa a un usuario registrado en Sabor Linares.
 * Puede ser un usuario normal, un emprendedor o un administrador.
 */
public class Usuario {

    /**
     * Rol del usuario dentro del sistema.
     */
    public enum Rol {
        USUARIO,
        EMPRENDEDOR,
        ADMIN
    }

    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Rol rol;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con los atributos principales
    public Usuario(int id, String nombre, String correo, String contrasena, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}