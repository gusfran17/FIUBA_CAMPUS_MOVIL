package com.fiubapp.fiubapp.dominio;

import java.util.List;

public class Carrera {
    private String codigo;
    private String nombre;
    private int currentAmount;
    private int totalAmount;
    private List<String> subjects;
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

    public void setSePuedeEliminar(boolean sePuedeEliminar) {
        this.sePuedeEliminar = sePuedeEliminar;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

}