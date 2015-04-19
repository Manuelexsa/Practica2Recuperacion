package com.example.kronos.practica2.adaptadores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kronos.practica2.R;
import com.example.kronos.practica2.bd.Ayudante;
import com.example.kronos.practica2.bd.Contrato;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

/**
 * Created by kronos on 06/04/2015.
 */
public class AdaptadorTipoHabitaciones extends CursorAdapter {

    private Cursor c;

    public AdaptadorTipoHabitaciones(Context context, Cursor c) {
        super(context, c,true);
        this.c=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle_lista_tipohab,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh;
        TipoHabitacion th=null;

        if(view!=null){
            GestionarTipoHabitacion gh = new GestionarTipoHabitacion();
            th = gh.getRow(cursor);
            vh = new ViewHolder();
            vh.tvdnom = (TextView)view.findViewById(R.id.tvdnombre);
            vh.tvdprec = (TextView)view.findViewById(R.id.tvdprecio);

            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
        vh.tvdnom.setText(th.getNombre());
        vh.tvdprec.setText(th.getPrecio()+"€");
    }

    public static class ViewHolder{
        public TextView tvdnom,tvdprec;
    }

    private int conseguirImagen(String tipo){
        int resultado = -1;
        if(tipo.equals(c.getString(R.string.tipo_individual))){
            resultado = R.drawable.individual;
        }
        return resultado;
    }

}
