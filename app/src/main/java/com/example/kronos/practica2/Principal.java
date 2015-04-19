package com.example.kronos.practica2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kronos.practica2.actividad.ActividadDetalle;
import com.example.kronos.practica2.actividad.ActividadModificarAddHabitacion;
import com.example.kronos.practica2.actividad.ActividadModificarAddTipoHabitacion;
import com.example.kronos.practica2.adaptadores.AdaptadorHabitaciones;
import com.example.kronos.practica2.adaptadores.AdaptadorTipoHabitaciones;
import com.example.kronos.practica2.bd.Contrato;
import com.example.kronos.practica2.bd.GestionarBaseDatos;
import com.example.kronos.practica2.bd.GestionarHabitacion;
import com.example.kronos.practica2.bd.GestionarTipoHabitacion;
import com.example.kronos.practica2.pojo.Habitacion;
import com.example.kronos.practica2.pojo.TipoHabitacion;

import java.util.ArrayList;


public class Principal extends Activity {

    private static final int CREARHAB =0 ;
    private static final int CREARTIPO = 1;
    private static final int MODIFICARTIPO = 2 ;
    private static final int MODIFICARHAB =3;

    private GestionarBaseDatos gbd;
    private GestionarHabitacion gh;
    private GestionarTipoHabitacion gth;
    private AdaptadorHabitaciones adhabitaciones;
    private ListView lvh,lvth;
    private AdaptadorTipoHabitaciones adtipohabitaciones;
    private TabHost tabs;
    public ArrayList<TipoHabitacion> listatipohabitaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        /************* TABS ************/
        tabs= (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Habitaciones");
        tabs.addTab(spec);
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tipo Habitaciones");
        tabs.addTab(spec);
        tabs.setCurrentTab(0);//para la pestaña seleccionada
        lvh=(ListView)findViewById(R.id.lvhabitaciones);
        lvth=(ListView)findViewById(R.id.lvtipohabitaciones);
        eventosOnclick();
        gbd = new GestionarBaseDatos(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            switch (requestCode){
                case CREARTIPO:
                    adtipohabitaciones.notifyDataSetChanged();
                    break;
                case MODIFICARTIPO:
                    adtipohabitaciones.notifyDataSetChanged();
                    break;
                case CREARHAB:
                    adhabitaciones.notifyDataSetChanged();
                    break;
                case MODIFICARHAB:
                    adhabitaciones.notifyDataSetChanged();
                    break;
            }
        }else {
            switch (requestCode){
                case CREARTIPO:
                    Log.v("no","no se a insertado el tipo");
                    break;


            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gbd.open();
        Cursor c,cth;
        gth=new GestionarTipoHabitacion(gbd.getBd());
        cth = gth.getCursor();
        adtipohabitaciones = new AdaptadorTipoHabitaciones(this, cth);
        gh = new GestionarHabitacion(gbd.getBd());
        c = gh.getCursor();
        adhabitaciones = new AdaptadorHabitaciones(this, c);
        lvh.setAdapter(adhabitaciones);
        registerForContextMenu(lvh);
        tabs.setCurrentTab(0);
        lvth.setVisibility(View.GONE);
        lvh.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gbd.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gbd.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.anadirhab) {
            Intent i = new Intent(this,ActividadModificarAddHabitacion.class);
            startActivityForResult(i, CREARHAB);
            return true;
        }else  if (id == R.id.anadirtipohab) {
            Intent i = new Intent(this,ActividadModificarAddTipoHabitacion.class);
            startActivityForResult(i, CREARTIPO);
            return true;
        }else if (id == R.id.oxplanta) {
            alertPlanta();
            return true;
        }else if (id == R.id.oxtipo) {
            alerTipo();
            return true;
        }else  if (id == R.id.oxprecio) {
            alertPrecio();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (tabs.getCurrentTab()==0) {
            inflater.inflate(R.menu.menuhabitaciones, menu);
        } else if (tabs.getCurrentTab()==1) {
            inflater.inflate(R.menu.menutipohabitacion, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (tabs.getCurrentTab()==0) {
            if (id == R.id.modificarhab) {
                editHab(index);
                adhabitaciones.notifyDataSetChanged();
            } else if (id == R.id.borrarhab) {
                removeHab(index);
                adhabitaciones.notifyDataSetChanged();
            }
        } else if (tabs.getCurrentTab()==1) {
            if (id == R.id.modificartipohab) {
                editTipoHab(index);
            } else if (id == R.id.borrartipohab) {
                removeTipoHab(index);
            }
        }
        return super.onContextItemSelected(item);
    }

    private boolean removeTipoHab(int index) {
        final Cursor c = (Cursor) lvth.getItemAtPosition(index);
        final TipoHabitacion thabitacion = gth.getRow(c);
        //dialogo
        final AlertDialog dialogo = new AlertDialog.Builder(this)
                .setTitle("Desea borrar el tipo de habitacion:" + thabitacion.getNombre())
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = dialogo.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        tostada(getApplicationContext(), "La habitacion tipo " + thabitacion.getNombre() + " ha sido borrada");
                        gh.updatebytipo(thabitacion.getId(),gth.select().get(0).getId());
                        int i=gth.delete(thabitacion);
                        Log.v("int delteeeee",i+"");
                        dialogo.dismiss();
                    }


                });
            }
        });
        dialogo.show();
        return true;
    }

    private void editTipoHab(int index) {
        Cursor c = (Cursor) lvth.getItemAtPosition(index);
        final TipoHabitacion thabitacion = gth.getRow(c);
        Intent i = new Intent(getApplicationContext(), ActividadModificarAddTipoHabitacion.class);
        i.putExtra("tipohabitacion",thabitacion);
        startActivityForResult(i, MODIFICARTIPO);
    }

    private boolean removeHab(int index) {
        Cursor c = (Cursor) lvh.getItemAtPosition(index);
        final Habitacion habitacion = gh.getRow(c);
        //dialogo
        final AlertDialog dialogo = new AlertDialog.Builder(this)
                .setTitle("Desea borrar la habitacion Nº:" + habitacion.getNumeroplanta()+""+habitacion.getNumero())
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = dialogo.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        tostada(getApplicationContext(), "La habitacion Nº " + habitacion.getNumeroplanta() + "" + habitacion.getNumero() + " ha sido borrada");
                        gh.delete(habitacion);
                        adhabitaciones.notifyDataSetChanged();
                        dialogo.dismiss();
                        adhabitaciones.notifyDataSetChanged();
                    }


                });
            }
        });
        dialogo.show();
        adhabitaciones.notifyDataSetChanged();
        return true;
    }

    private void editHab(int index) {
        Cursor c = (Cursor) lvh.getItemAtPosition(index);
        Habitacion habitacion = gh.getRow(c);
        TipoHabitacion tipoHabitacion=gth.selectbyid(habitacion.getIdtipo());
        Intent i = new Intent(getApplicationContext(), ActividadModificarAddHabitacion.class);
        i.putExtra("habitacion", habitacion);
        i.putExtra("tipohabitacion", tipoHabitacion);
        startActivityForResult(i, MODIFICARHAB);
    }

    private void eventosOnclick() {
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int i = tabs.getCurrentTab();
                if (i == 0) {
                    lvh.setVisibility(View.VISIBLE);
                    lvh.setAdapter(adhabitaciones);
                    registerForContextMenu(lvh);
                    lvth.setVisibility(View.GONE);
                } else if (i == 1) {
                    lvth.setVisibility(View.VISIBLE);
                    lvth.setAdapter(adtipohabitaciones);
                    registerForContextMenu(lvth);
                    lvh.setVisibility(View.GONE);
                }

            }
        });
        lvh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                Habitacion h;
                h = gh.selectbyid(id);
                TipoHabitacion th;
                th = gth.selectbyid(h.getIdtipo());
                Intent i = new Intent(getApplicationContext(), ActividadDetalle.class);
                i.putExtra("habitacion", h);
                i.putExtra("tipohabitacion", th);
                startActivityForResult(i, MODIFICARHAB);
            }
        });
    }

    private void alertPrecio() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.planta));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_precio, null);
        alert.setView(vista);
        final Spinner spinner = (Spinner) vista.findViewById(R.id.spprecio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listamodoprecio, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int i=spinner.getSelectedItemPosition();
                Cursor c;
                if(i==0){
                    c=gth.getCursor();
                }else{
                    c=gth.getCursorbypreciodesc();
                }
                adtipohabitaciones =new AdaptadorTipoHabitaciones(getApplicationContext(),c);
                lvth.setAdapter(adtipohabitaciones);
                adtipohabitaciones.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        AlertDialog alerta = alert.create();
        alerta.show();
    }

    private void alertPlanta() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.planta));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_spinner, null);
        alert.setView(vista);
        final Spinner spinner = (Spinner) vista.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numerosplanta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Cursor c=gh.getCursorbyplanta(spinner.getSelectedItemId());
                adhabitaciones=new AdaptadorHabitaciones(getApplicationContext(),c);
                lvh.setAdapter(adhabitaciones);
                adhabitaciones.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        AlertDialog alerta = alert.create();
        alerta.show();
    }

    public static Toast tostada(Context c,String t) {
        Toast toast = Toast.makeText(c, t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    private void alerTipo() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.thab));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo_spinner, null);
        alert.setView(vista);
        final Spinner spinner=(Spinner)vista.findViewById(R.id.spinner);
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
                Cursor c=gh.getCursorbytipo(spinner.getSelectedItemId());
                adhabitaciones=new AdaptadorHabitaciones(getApplicationContext(),c);
                lvh.setAdapter(adhabitaciones);
                adhabitaciones.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        AlertDialog alerta = alert.create();
        alerta.show();
    }

}
