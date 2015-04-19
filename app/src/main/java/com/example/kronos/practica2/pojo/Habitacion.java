package com.example.kronos.practica2.pojo;

import java.io.Serializable;

/**
 * Created by kronos on 03/04/2015.
 */
public class Habitacion implements Serializable, Comparable<Habitacion>{

    private long id,idtipo;
    private String numero,numeroplanta;

    public Habitacion() {
        this(0, "","",1);
    }

    public Habitacion(long id, String numero, String numeroplanta,long idtipo) {
        this.id = id;
        this.numero = numero;
        this.numeroplanta = numeroplanta;
        this.idtipo = idtipo;
    }

    public Habitacion(String numero, String numeroplanta,long idtipo) {
        this.numero = numero;
        this.numeroplanta = numeroplanta;
        this.idtipo = idtipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(long idtipo) {
        this.idtipo = idtipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroplanta() {
        return numeroplanta;
    }

    public void setNumeroplanta(String numeroplanta) {
        this.numeroplanta = numeroplanta;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Habitacion another) {
        return 0;
    }
}
