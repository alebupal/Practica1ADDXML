package com.example.alejandro.proyecto2xml;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alejandro on 19/11/2014.
 */
public class Pelicula implements Comparable<Pelicula>, Parcelable {
    private String titulo;
    private String genero;
    private Integer anio;


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


    @Override
    public int compareTo(Pelicula pelicula) {
        if(this.getGenero().compareTo(pelicula.getGenero())!=0)
            return this.getGenero().compareTo(pelicula.getGenero());
        if(this.getAnio().compareTo(pelicula.getAnio())!=0)
            return this.getAnio().compareTo(pelicula.getAnio());
        if(this.getTitulo().compareTo(pelicula.getTitulo())!=0)
            return this.getTitulo().compareTo(pelicula.getTitulo());
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Pelicula.class != o.getClass()) return false;

        Pelicula pelicula = (Pelicula) o;

        if (anio != null ? !anio.equals(pelicula.anio) : pelicula.anio != null) return false;
        if (genero != null ? !genero.equals(pelicula.genero) : pelicula.genero != null)
            return false;
        if (titulo != null ? !titulo.equals(pelicula.titulo) : pelicula.titulo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = titulo != null ? titulo.hashCode() : 0;
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (anio != null ? anio.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(genero);
        dest.writeString(anio.toString());

    }
}
