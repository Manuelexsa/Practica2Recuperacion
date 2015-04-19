package com.example.kronos.practica2.actividad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kronos.practica2.R;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarHabitacion;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;


public class ActividadDetalle extends Activity {

    private TextView tvplanta,tvnumerohab,tvtipohab,tvpreciodia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);
        initcomponet();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Habitacion hab = (Habitacion) extras.get("habitacion");
            TipoHabitacion thab = (TipoHabitacion) extras.get("tipohabitacion");
            if (hab != null) {
                tvplanta.setText(hab.getNumeroplanta().toString());
                tvnumerohab.setText(hab.getNumeroplanta().toString()+""+hab.getNumero().toString());
                tvtipohab.setText(thab.getNombre().toString());
                tvpreciodia.setText(thab.getPrecio().toString()+"€");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void volver(View v){
        finish();
    }

    private void initcomponet() {
        tvplanta=(TextView)findViewById(R.id.tvdetalleplanta);
        tvnumerohab=(TextView)findViewById(R.id.tvdetallenhab);
        tvtipohab=(TextView)findViewById(R.id.tvdetallethab);
        tvpreciodia=(TextView)findViewById(R.id.tvdetalleprecio);
    }

}
