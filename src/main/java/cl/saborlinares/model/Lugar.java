package cl.saborlinares.model;

/**
 * Representa un lugar gastronómico dentro de Sabor Linares.
 * Puede ser un carrito de comida, food truck, cafetería, panadería, etc.
 */
public class Lugar {

    /**
     * Estado de aprobación del lugar dentro del sistema de moderación.
     */
    public enum Estado {
        PENDIENTE,
        APROBADO,
        RECHAZADO
    }

    private int id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String direccion;
    private double latitud;
    private double longitud;
    private String horario;
    private String precio;
    private String imagen;
    private Estado estado;

    // Constructor vacío (útil para frameworks, formularios y DAOs)
    public Lugar() {
    }

    // Constructor completo
    public Lugar(int id, String nombre, String descripcion, String categoria,
                 String direccion, double latitud, double longitud,
                 String horario, String precio, String imagen, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.horario = horario;
        this.precio = precio;
        this.imagen = imagen;
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}