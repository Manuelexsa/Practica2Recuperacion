package com.example.kronos.practica2.actividad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kronos.practica2.R;
import com.example.kronos.practica2.bd.Contrato;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarHabitacion;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ActividadModificarAddHabitacion extends Activity {


    Button btnaceptar,btncancelar;
    private TextView tvplanta,tvnumerohab,tvtipohab;
    private ImageButton ivbtplanta,ivbtnumhab,ivbttipohab;
    private GestionarBaseDatos gbd;
    private GestionarTipoHabitacion gth;
    private GestionarHabitacion gh;
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private AlertDialog alerta;
    private Habitacion hab;
    private TipoHabitacion tipohab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_anadir_modificar_h);
        gbd=new GestionarBaseDatos(this);
        hab=new Habitacion("","",0);
        tipohab=new TipoHabitacion(null,null);
        initcomponet();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hab = (Habitacion) extras.get("habitacion");
            tipohab = (TipoHabitacion) extras.get("tipohabitacion");
            if (hab.getId()>0) {
                tvplanta.setText(hab.getNumeroplanta().toString());
                tvnumerohab.setText(hab.getNumeroplanta()+""+hab.getNumero());
                tvtipohab.setText(tipohab.getNombre()+"");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anadir_modificar_habitacion, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        gbd.open();
        gth = new GestionarTipoHabitacion(gbd.getBd());
        gh=new GestionarHabitacion(gbd.getBd());
    }

    @Override
    protected void onPause() {
        super.onPause();
        gbd.close();
    }

    public void aceptar(View v){
        Intent i = new Intent();
        if(hab.getId()>0){
            gh.update(hab);
            setResult(Activity.RESULT_OK, i);
            finish();
        }else{
            gh.insert(hab);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    public void cancelar(View v){
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }

    public void spplanta(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.planta));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_spinner, null);
        alert.setView(vista);
        spinner=(Spinner)vista.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.numerosplanta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                hab.setNumeroplanta(spinner.getSelectedItem().toString());
                tvplanta.setText(hab.getNumeroplanta().toString());
                if (hab.getNumero()!="") {
                    Log.v("aaaaaa","entra");
                    tvnumerohab.setText(spinner.getSelectedItem().toString() + "" + hab.getNumero());
                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alerta = alert.create();
        alerta.show();
    }

    public void spnumerohab(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.numerohab));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_spinner, null);
        alert.setView(vista);
        spinner=(Spinner)vista.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.numeroshabitaciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                hab.setNumero(spinner.getSelectedItem().toString());
                if (hab.getNumeroplanta()!="") {
                    tvnumerohab.setText(tvplanta.getText()+""+spinner.getSelectedItem().toString());
                }
                tvnumerohab.setText("1"+spinner.getSelectedItem().toString());
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        AlertDialog alerta = alert.create();
        alerta.show();
    }

    public void sptipohab(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.thab));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_spinner, null);
        alert.setView(vista);
        spinner=(Spinner)vista.findViewById(R.id.spinner);
        SimpleCursorAdapter adapter2;
        adapter2 = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                gth.getCursor(),
                new String[]{Contrato.TablaTipoHabitacion.NOMBRE,Contrato.TablaTipoHabitacion.PRECIO},
                new int[]{android.R.id.text1, android.R.id.text2},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TipoHabitacion th=gth.selectbyid(spinner.getSelectedItemId());
                hab.setIdtipo(th.getId());
                tvtipohab.setText(th.getNombre().toString());
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        AlertDialog alerta = alert.create();
        alerta.show();
    }

    private void initcomponet() {
        spinner=(Spinner)findViewById(R.id.spinner);
        tvplanta=(TextView)findViewById(R.id.tvaddmodplanta);
        tvnumerohab=(TextView)findViewById(R.id.tvaddmodnhab);
        tvtipohab=(TextView)findViewById(R.id.tvaddmodthab);
        ivbtplanta=(ImageButton)findViewById(R.id.imageButton);
        ivbtnumhab=(ImageButton)findViewById(R.id.imageButton2);
        ivbttipohab=(ImageButton)findViewById(R.id.imageButton3);

    }

}
