package com.example.examenfinalpmo1_p3.Clases;

public class sitioweb {
    private Integer id;
    private String descripcion;
    private String fecha;
    private String path;
    private byte[] imagen;

    public sitioweb() {
    }

    public sitioweb(Integer id, String descripcion, String fecha, String path, byte[] imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.path = path;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
