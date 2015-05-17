package com.fiubapp.fiubapp.dominio;

import com.fiubapp.fiubapp.Alumno;

public class Grupo {
    private String nombre;
    private String id;
    private Alumno owner;
    private int cantMiembros;
    private boolean amIaMember;
    private String imgURL;

    public boolean getAmIaMember() {
        return amIaMember;
    }
    public void setAmIaMember(boolean amIaMember) {
        this.amIaMember = amIaMember;
    }

    public int getCantMiembros() {
        return cantMiembros;
    }
    public void setCantMiembros(int cantMiembros) {
        this.cantMiembros = cantMiembros;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOwner(Alumno owner) { this.owner = owner; }
    public Alumno getOwner () { return owner; }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
