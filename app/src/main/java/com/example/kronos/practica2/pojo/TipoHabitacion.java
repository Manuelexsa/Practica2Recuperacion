package com.example.kronos.practica2.pojo;

import java.io.Serializable;

/**
 * Created by kronos on 03/04/2015.
 */
public class TipoHabitacion implements Serializable, Comparable<TipoHabitacion>{

    private long id;
    private String precio;
    private String nombre;

    public TipoHabitacion() {
        this(0, "", "");
    }

    public TipoHabitacion(String precio, String nombre) {
        try {
            this.precio =precio;
        } catch (NumberFormatException e) {
            this.precio ="0";
        }
        this.nombre = nombre;
    }

    public TipoHabitacion(long id, String precio, String nombre) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoHabitacion)) return false;

        TipoHabitacion that = (TipoHabitacion) o;

        if (id != that.id) return false;
       // if (Double.compare(that.precio, precio) != 0) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
       // temp = Double.doubleToLongBits(precio);
       // result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(TipoHabitacion another) {
        return 0;
    }

    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "id=" + id +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}