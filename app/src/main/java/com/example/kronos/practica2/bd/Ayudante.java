package com.example.kronos.practica2.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kronos.practica2.bd.Contrato;

/**
 * Created by kronos on 03/04/2015.
 */
public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "habitaciones.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table "+ Contrato.TablaHabitacion.TABLA+
                " ("+Contrato.TablaHabitacion._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaHabitacion.NUMERO+" text, "+
                Contrato.TablaHabitacion.PLANTA+" text, "+
                Contrato.TablaHabitacion.ID_TIPOHABITACION+" long)";
        Log.v("sql", sql);
        db.execSQL(sql);
        sql="";
        sql = "create table "+Contrato.TablaTipoHabitacion.TABLA+
                " ("+Contrato.TablaHabitacion._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaTipoHabitacion.NOMBRE+" text, "+
                Contrato.TablaTipoHabitacion.PRECIO+" text)";
        Log.v("sql", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
