package com.irecibos.irecibos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Usuario on 16/03/2018.
 */

public class act_BusTelefonos extends AppCompatActivity implements View.OnClickListener {

    private clsAsociacion moAsociacion;
    private Button moBoton;
    private MiTareaAsincrona tarea1;
    private ListView listView;
    private EditText moNombre;
    private LinearLayout moFiltro;
    private ImageView moBotonFiltro;
    private ProgressBar moEspera;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_bustelefonos);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Busqueda de Telefonos");
        setSupportActionBar(toolbar);

        moEspera = (ProgressBar) findViewById(R.id.progressBar);
        moFiltro = (LinearLayout) findViewById(R.id.layFiltro);
        moBotonFiltro = (ImageView) findViewById(R.id.imgFiltro);
        moBotonFiltro.setOnClickListener(this);

        moNombre = (EditText) findViewById(R.id.txtBuscar);
        listView = (ListView) findViewById(R.id.lstTelefonos);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        moBoton = (Button) findViewById(R.id.btnLee);
        moBoton.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos,
                                    long id) {
                //mrLlamaTelefono(pos);
                mrFichaSocio(pos);
            }
        });

    }

    private void mrFichaSocio(int lnPosicion) {

        act_Login.moSocio = moAsociacion.mcolSocios.get(lnPosicion);
        Intent intent = new Intent(act_Login.moContexto, act_Ficha_Socio.class);
        startActivity(intent);

    }

    private void mrLlamaTelefono(int lnPosicion) {

        String lsTelefono = "";
        if (moAsociacion.mcolSocios.get(lnPosicion).msTelefono1.length()>0) lsTelefono = moAsociacion.mcolSocios.get(lnPosicion).msTelefono1;
        else lsTelefono = moAsociacion.mcolSocios.get(lnPosicion).msTelefono2;

        if (lsTelefono.length()>0) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + lsTelefono));
            startActivity(intent);
            /*
            Uri uri = Uri.parse("smsto:" + lsTelefono);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, "Envio de prueba"));
            */
        } else Toast.makeText(act_Login.moContexto,"No tiene telefono",Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //finish(); // close this activity and return to preview activity (if there is any)
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void mrCargaTelefonos() {

        moEspera.setVisibility(View.VISIBLE);
        moBoton.setText("buscando ...");
        moFiltro.setVisibility(View.GONE);

        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    progressStatus +=1;
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    moEspera.setProgress(progressStatus);
                    /*
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            moEspera.setProgress(progressStatus);
                        }
                    });
                    */
                }
            }
        }).start(); // Start the operation

        tarea1 = new MiTareaAsincrona();
        tarea1.execute(this);

    }

    private void mrOcultaFiltro() {
        moEspera.setVisibility(View.GONE);
        moBoton.setText("Buscar");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(moNombre.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.btnLee: {
                mrCargaTelefonos();
                break;
            }
            case  R.id.imgFiltro: {
                moFiltro.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                break;
            }
        }
    }

    private class MiTareaAsincrona extends
            AsyncTask<Activity, Integer, Boolean> {

        Activity context;

        @Override
        protected Boolean doInBackground(Activity... params) {

            context = params[0];
            moAsociacion = new clsAsociacion();
            moAsociacion.mnCodigo = act_Login.moAsociacion.mnCodigo;
            moAsociacion.mrRecuperaSocios(moNombre.getText().toString());

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mrOcultaFiltro();
                listView.setAdapter(new AdaptadorSocios(context));
            }
        }

    }

    class AdaptadorSocios extends ArrayAdapter<clsSocio> {

        Activity context;

        AdaptadorSocios(Activity context) {
            super(context, R.layout.row_socio, moAsociacion.mcolSocios);
            this.context = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.row_socio, null);

            TextView txtNombre = (TextView) item.findViewById(R.id.txtNombre);
            TextView txtDieccion = (TextView) item.findViewById(R.id.txtDireccion);
            TextView txtFP = (TextView) item.findViewById(R.id.txtFormaPago);
            TextView txtTelefono = (TextView) item.findViewById(R.id.txtTelefono);

            try {
                txtNombre.setText(moAsociacion.mcolSocios.get(position).msNombre);
                txtDieccion.setText(moAsociacion.mcolSocios.get(position).msDireccion);
                txtTelefono.setText(moAsociacion.mcolSocios.get(position).msTelefono1 + " - " + moAsociacion.mcolSocios.get(position).msTelefono2);
                txtFP.setText(moAsociacion.mcolSocios.get(position).mfsFormaPago());
            } catch (Exception ex) {
                //Toast.makeText(act_Login.moContexto,ex.getStackTrace().toString(),Toast.LENGTH_LONG).show();
            }

            return item;
        }
    }

}
