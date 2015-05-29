package com.fiubapp.fiubapp.dominio;

import com.fiubapp.fiubapp.Alumno;

public class MensajeMuro {

    private String nombre;
    private String id;
    private Alumno remitente;
    private String fecha;
    private String imgURL;
    private String mensaje;
    private boolean msjPrivado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Alumno getRemitente() {
        return remitente;
    }

    public void setRemitente(Alumno remitente) {
        this.remitente = remitente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isMsjPrivado() {
        return msjPrivado;
    }

    public void setMsjPrivado(boolean msjPrivado) {
        this.msjPrivado = msjPrivado;
    }

}
