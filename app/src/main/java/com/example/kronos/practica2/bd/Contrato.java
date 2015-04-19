package com.example.kronos.practica2.bd;

import android.provider.BaseColumns;

/**
 * Created by kronos on 03/04/2015.
 */
public class Contrato {

    public Contrato() {
    }

    public static abstract class TablaHabitacion implements BaseColumns{
        public static final String TABLA = "Habitacion";
        public static final String NUMERO = "numero";
        public static final String PLANTA = "planta";
        public static final String ID_TIPOHABITACION = "tipohabitacion";

    }

    public static abstract class TablaTipoHabitacion implements BaseColumns{
        public static final String TABLA = "TipoHabitacion";
        public static final String NOMBRE = "nombre";
        public static final String PRECIO = "precio";
    }
}
