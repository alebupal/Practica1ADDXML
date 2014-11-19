package com.example.alejandro.proyecto2xml;

/**
 * Created by Alejandro on 19/11/2014.
 */
public class Pelicula {
    private String titulo;
    private String genero;
    private Integer anio;
    private int caratula;
    public String uriCaratula;


    public Pelicula(String titulo, String genero, Integer anio) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
    }

    public Pelicula() {
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


}
