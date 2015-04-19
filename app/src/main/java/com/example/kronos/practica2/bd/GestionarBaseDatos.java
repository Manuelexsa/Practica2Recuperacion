package com.example.kronos.practica2.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kronos on 03/04/2015.
 */
public class GestionarBaseDatos {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestionarBaseDatos(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public SQLiteDatabase getBd(){
        return bd;
    }

}
