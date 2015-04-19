package com.example.kronos.practica2.actividad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kronos.practica2.R;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

public class ActividadModificarAddTipoHabitacion extends Activity {

    EditText etnombre,etprecio;
    Button btnaceptar,btncancelar;
    private GestionarBaseDatos gbd;
    private GestionarTipoHabitacion gth;
    private TipoHabitacion thabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_anadir_modificar_th);
        initcomponet();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            thabitacion = (TipoHabitacion) extras.get("tipohabitacion");
            if (thabitacion.getId()>0) {
                etnombre.setText(thabitacion.getNombre().toString());
                etprecio.setText(thabitacion.getPrecio().toString());
            }
        }
    }

    private void initcomponet() {
        gbd=new GestionarBaseDatos(this);
        etnombre=(EditText)findViewById(R.id.etnombreth);
        etprecio=(EditText)findViewById(R.id.etprecioth);
        btnaceptar=(Button)findViewById(R.id.btaceptarth);
        btncancelar=(Button)findViewById(R.id.btcancelarth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gbd.open();
        gth=new GestionarTipoHabitacion(gbd.getBd());
    }

    @Override
    protected void onPause() {
        super.onPause();
        gbd.close();
    }

    public void aceptar(View v){
        thabitacion.setNombre(etnombre.getText().toString());
        thabitacion.setPrecio(etprecio.getText().toString());
        Intent i = new Intent();
        if(thabitacion.getId()>0){
            Log.v("habitacion",thabitacion.getId()+" "+thabitacion.getPrecio()+"  "+thabitacion.getNombre());
            gth.update(thabitacion);
            setResult(Activity.RESULT_OK, i);
            finish();
        }else{
            gth.insert(thabitacion);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    public void cancelar(View v){
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }
}
