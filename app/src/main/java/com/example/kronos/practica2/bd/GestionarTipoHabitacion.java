package com.example.kronos.practica2.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kronos on 06/04/2015.
 */
public class GestionarTipoHabitacion {

    private SQLiteDatabase bd;

    public GestionarTipoHabitacion(SQLiteDatabase sql) {
        bd=sql;
    }

    public GestionarTipoHabitacion() {
    }

    public void setBd(SQLiteDatabase sql) {
        bd=sql;
    }

    public long insert(TipoHabitacion th){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaTipoHabitacion.NOMBRE,th.getNombre());
        valores.put(Contrato.TablaTipoHabitacion.PRECIO,th.getPrecio());
        long id = bd.insert(Contrato.TablaTipoHabitacion.TABLA,null, valores);
        return id;
    }

    public int delete(TipoHabitacion th) {
        String condicion = Contrato.TablaTipoHabitacion._ID + " = ?";
        String[] argumentos = { th.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaTipoHabitacion.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int update(TipoHabitacion th) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaTipoHabitacion.NOMBRE,th.getNombre());
        valores.put(Contrato.TablaTipoHabitacion.PRECIO,th.getPrecio());
        String condicion = Contrato.TablaTipoHabitacion._ID + " = ?";
        String[] argumentos = { th.getId() + "" };
        int cuenta = bd.update(Contrato.TablaTipoHabitacion.TABLA, valores,condicion, argumentos);
        return cuenta;
    }

    public List<TipoHabitacion> select(String condicion,String[] parametros,String orden) {
        List<TipoHabitacion> ali = new ArrayList<TipoHabitacion>();
        Cursor cursor =getCursor(condicion,null,parametros,null,orden);
        cursor.moveToFirst();
        TipoHabitacion objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            ali.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return ali;
    }

    public List<TipoHabitacion>  select(){
        return select(null,null,null);
    }

    public TipoHabitacion getRow(Cursor c) {
        TipoHabitacion objeto = new TipoHabitacion();
        objeto.setId(c.getLong(0));
        objeto.setNombre(c.getString(1));
        objeto.setPrecio(c.getString(2));
        return objeto;
    }

    public Cursor getCursor(String condicion, String where, String[] parametros, String groupby, String having) {
        String[] proyeccion = { Contrato.TablaTipoHabitacion._ID,
                Contrato.TablaTipoHabitacion.NOMBRE,
                Contrato.TablaTipoHabitacion.PRECIO};
        String orderby = Contrato.TablaTipoHabitacion.PRECIO;
        Cursor c = bd.query(Contrato.TablaTipoHabitacion.TABLA,
                proyeccion,
                where,
                parametros,
                groupby,
                having,
                orderby);
        return c;
    }

    public Cursor getCursor() {
        return getCursor(null,null,null,null,null);
    }

    public Cursor getCursorbypreciodesc() {
        Cursor c=getCursor(null,null,null,null,Contrato.TablaTipoHabitacion.PRECIO+" DESC ");
        return c;
    }

    public TipoHabitacion selectbyid(long idtipo) {
        String where = Contrato.TablaTipoHabitacion._ID + " = ?";
        String[] parametros = new String[] { idtipo+"" };
        Cursor c = getCursor(null,where,parametros,null,null);
        c.moveToFirst();
        TipoHabitacion th = getRow(c);
        c.close();
        return th;
    }

}
