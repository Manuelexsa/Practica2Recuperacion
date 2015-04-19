package com.example.kronos.practica2.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kronos.practica2.Principal;
import com.example.kronos.practica2.R;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarHabitacion;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

import java.util.ArrayList;

/**
 * Created by kronos on 03/04/2015.
 */
public class AdaptadorHabitaciones extends CursorAdapter{

    private Cursor c;

    public AdaptadorHabitaciones(Context context, Cursor c) {
        super(context, c,true);
        this.c=c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle_lista,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh;
        Habitacion h=null;
        GestionarHabitacion gh = new GestionarHabitacion();
        if(view!=null){
            h = gh.getRow(cursor);
            //th=selectbyid(h.getIdtipo());
            vh = new ViewHolder();
            vh.ivtipo = (ImageView)view.findViewById(R.id.ivtipo);
            vh.tvnumero = (TextView)view.findViewById(R.id.tvnumhab);
            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }
       /* String tipo = h.get();
        int imagen = conseguirImagen(tipo);
        vh.ivTipo.setImageResource(imagen);*/
        vh.ivtipo.setImageResource(R.drawable.individual);
        vh.tvnumero.setText(h.getNumeroplanta() + "" + h.getNumero());
    }

    public static class ViewHolder{
        public TextView tvnumero;
        public ImageView ivtipo;
    }

    private int conseguirImagen(String tipo){
        int resultado = -1;
        if(tipo.equals(c.getString(R.string.tipo_individual))){
            resultado = R.drawable.individual;
        }
        return resultado;
    }
}
