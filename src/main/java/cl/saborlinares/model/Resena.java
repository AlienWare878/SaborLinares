package cl.saborlinares.model;

import java.time.LocalDateTime;

/**
 * Representa una reseña realizada por un usuario sobre un lugar
 * gastronómico de Sabor Linares.
 */
public class Resena {

    private int id;
    private String comentario;
    private int calificacion;
    private LocalDateTime fecha;
    private Usuario usuario;
    private Lugar lugar;

    // Constructor vacío
    public Resena() {
    }

    // Constructor con los atributos necesarios
    public Resena(int id, String comentario, int calificacion, LocalDateTime fecha,
                  Usuario usuario, Lugar lugar) {
        this.id = id;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha = fecha;
        this.usuario = usuario;
        this.lugar = lugar;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
}