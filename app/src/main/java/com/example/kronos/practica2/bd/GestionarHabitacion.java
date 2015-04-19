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
public class GestionarHabitacion {

    private SQLiteDatabase bd;

    public GestionarHabitacion(SQLiteDatabase sql) {
            bd=sql;
    }

    public GestionarHabitacion() {
    }

    public void setBd(SQLiteDatabase sql) {
        bd=sql;
    }

    public long insert(Habitacion objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaHabitacion.NUMERO,objeto.getNumero());
        valores.put(Contrato.TablaHabitacion.PLANTA,objeto.getNumeroplanta());
        valores.put(Contrato.TablaHabitacion.ID_TIPOHABITACION, ((long) objeto.getIdtipo()));
        long id = bd.insert(Contrato.TablaHabitacion.TABLA,null, valores);
        return id;
    }

    public int delete(Habitacion objeto) {
        String condicion = Contrato.TablaHabitacion._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaHabitacion.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int update(Habitacion objeto) {
        ContentValues valores = new ContentValues();
        valores.put(String.valueOf(Contrato.TablaHabitacion.NUMERO),objeto.getNumero());
        valores.put(String.valueOf(Contrato.TablaHabitacion.PLANTA),objeto.getNumeroplanta());
        valores.put(String.valueOf(Contrato.TablaHabitacion.ID_TIPOHABITACION),objeto.getIdtipo());
        String condicion = Contrato.TablaHabitacion._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaHabitacion.TABLA, valores,condicion, argumentos);
        return cuenta;
    }

    public List<Habitacion> select(String condicion,String[] parametros,String orden) {
        List<Habitacion> ali = new ArrayList<Habitacion>();
        Cursor cursor = bd.query(Contrato.TablaHabitacion.TABLA, null,condicion, null, null, null, null);
        cursor.moveToFirst();
        Habitacion objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            ali.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return ali;
    }

    public List<Habitacion>  select(){
        return select(null,null,null);
    }

    public Habitacion getRow(Cursor c) {
        Habitacion objeto = new Habitacion();
        objeto.setId(c.getLong(0));
        objeto.setNumero(c.getString(1));
        objeto.setNumeroplanta(c.getString(2));
        objeto.setIdtipo(c.getLong(3));
        return objeto;
    }

    public Cursor getCursor(String[] proyeccion, String condicion, String where, String[] parametros, String groupby, String having,String orderby) {
        String[] proyeccion2=proyeccion;
            if(proyeccion ==null){
                proyeccion2 = new String[]{Contrato.TablaHabitacion._ID,
                        Contrato.TablaHabitacion.NUMERO,
                        Contrato.TablaHabitacion.PLANTA,
                        Contrato.TablaHabitacion.ID_TIPOHABITACION};
            }
        if (orderby==""){
            orderby = Contrato.TablaHabitacion.PLANTA;
        }
            Cursor c = bd.query(Contrato.TablaHabitacion.TABLA,
                    proyeccion2,
                    where,
                    parametros,
                    groupby,
                    having,
                    orderby);
            return c;
    }

    public Cursor getCursor() {
        return getCursor(null, null, null,null,null,null,null);
    }

    public Habitacion selectbyid(long id) {
        String where = Contrato.TablaHabitacion._ID + " = ?";
        String[] parametros = new String[] { id+"" };
        Cursor c = getCursor(null,null,where, parametros,null,null,null);
        c.moveToFirst();
        Habitacion h = getRow(c);
        c.close();
        return h;
    }

    public ArrayList<Habitacion> selectbytipo(long id) {
        ArrayList<Habitacion> ali = new ArrayList<Habitacion>();
        String where = Contrato.TablaHabitacion.ID_TIPOHABITACION + " = ?";
        String[] parametros = new String[] { id+"" };
        Cursor c = getCursor(null,null,where, parametros,null,null,null);
        c.moveToFirst();
        Habitacion objeto;
        while (!c.isAfterLast()) {
            objeto = getRow(c);
            ali.add(objeto);
            c.moveToNext();
        }
        c.close();
        return ali;
    }

    public void updatebytipo(long id,long id2) {
        String where = Contrato.TablaHabitacion.ID_TIPOHABITACION + " = ?";
        String[] parametros = new String[] { id+"" };
        Cursor c = getCursor(null,null,where, parametros,null,null,null);
        c.moveToFirst();
        Habitacion objeto;
        while (!c.isAfterLast()) {
            objeto = getRow(c);
            objeto.setIdtipo(id2);
            update(objeto);
            c.moveToNext();
        }
        c.close();
    }

    public Cursor getCursorbyplanta(Long idplanta) {
        String[] parametros = new String[] { idplanta+"" };
        String where = Contrato.TablaHabitacion.PLANTA + " = ?";
        return getCursor(null,null,where, parametros,null,null, Contrato.TablaHabitacion.PLANTA);
    }

    public Cursor getCursorbytipo(long selectedItemId) {
        String[] parametros = new String[] { selectedItemId+"" };
        String where = Contrato.TablaHabitacion.ID_TIPOHABITACION + " = ?";
        return getCursor(null,null,where, parametros,null, null, Contrato.TablaHabitacion.PLANTA);
    }
}
