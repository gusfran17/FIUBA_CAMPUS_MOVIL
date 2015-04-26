package com.fiubapp.fiubapp.dominio;

public class Carrera {
    private String codigo;
    private String nombre;
    private boolean sePuedeEliminar;

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getSePuedeEliminar() {
        return sePuedeEliminar;
    }
}
