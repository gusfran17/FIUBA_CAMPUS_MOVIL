package com.fiubapp.fiubapp;

import android.location.Location;

import java.util.ArrayList;

public class Alumno {

    private String nombre, apellido, foto, comentario, celular,
            nacionalidad, ciudad, empleo_actual, secundaria, orientacion;
    private int padron;
    private ArrayList<Byte> carreras;
    private ArrayList<Integer> grupos, discusiones, compañeros;
    private Location ubicacion;

    public Alumno() {
    }

    public Alumno(int padron, String nombre, String apellido, Byte carrera) {
        this.padron = padron;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carreras.add(carrera);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmpleo_actual() {
        return empleo_actual;
    }

    public void setEmpleo_actual(String empleo_actual) {
        this.empleo_actual = empleo_actual;
    }

    public String getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(String secundaria) {
        this.secundaria = secundaria;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public int getPadron() {
        return padron;
    }

    public void setPadron(int padron) {
        this.padron = padron;
    }

    public ArrayList<Byte> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<Byte> carreras) {
        this.carreras = carreras;
    }

    public ArrayList<Integer> getGrupos() {
        return grupos;
    }

    public void setGrupos(ArrayList<Integer> grupos) {
        this.grupos = grupos;
    }

    public ArrayList<Integer> getDiscusiones() {
        return discusiones;
    }

    public void setDiscusiones(ArrayList<Integer> discusiones) {
        this.discusiones = discusiones;
    }

    public ArrayList<Integer> getCompañeros() {
        return compañeros;
    }

    public void setCompañeros(ArrayList<Integer> compañeros) {
        this.compañeros = compañeros;
    }

    public Location getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Location ubicacion) {
        this.ubicacion = ubicacion;
    }


}