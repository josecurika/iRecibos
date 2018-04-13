package com.irecibos.irecibos;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Usuario on 22/03/2018.
 */

public class act_Estado_Recibos  extends AppCompatActivity implements View.OnClickListener {

    private MiTareaAsincrona tarea1;
    private clsBusRecibos moBusRecibos;
    private Spinner moSpinnerEjercicio;
    private TextView moEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_estado_recibos);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Estado Actual");
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        moEstado = (TextView) findViewById(R.id.txtMensaje);

        List<String> spinnerArray =  new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int lnI = 0; lnI<10; lnI++) {
            spinnerArray.add(String.valueOf(year-lnI));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moSpinnerEjercicio = (Spinner) findViewById(R.id.spinner_ejercicio);
        moSpinnerEjercicio.setAdapter(adapter);

        moSpinnerEjercicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mrLanzaconsulta();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        mrLanzaconsulta();

    }

    private void mrLanzaconsulta() {
        moEstado.setText("Consultando ...");
        tarea1 = new MiTareaAsincrona();
        tarea1.execute(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.imgFiltro: {
                break;
            }

        }
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

    private void mrPintaGrafica() {

        moEstado.setText("");

        TextView txtAnulados = (TextView) findViewById(R.id.txtAnulados);
        TextView txtPagados = (TextView) findViewById(R.id.txtPagados);
        TextView txtPendientes = (TextView) findViewById(R.id.txtPendientes);
        TextView txtDevueltos = (TextView) findViewById(R.id.txtDevueltos);
        TextView txtRemesados = (TextView) findViewById(R.id.txtRemesados);

        LinearLayout layAnulados = (LinearLayout) findViewById(R.id.layAnulados);
        LinearLayout layPagados = (LinearLayout) findViewById(R.id.layPagados);
        LinearLayout layPendientes = (LinearLayout) findViewById(R.id.layPendientes);
        LinearLayout layDevueltos = (LinearLayout) findViewById(R.id.layDevueltos);
        LinearLayout layRemesados = (LinearLayout) findViewById(R.id.layRemesados);

        txtAnulados.setText(moBusRecibos.mnNum_Anulados + " Recibos Anulados (" + moBusRecibos.mnPor_Anulados + "%) " + moBusRecibos.mnAnulados + "€");
        txtPagados.setText(moBusRecibos.mnNum_Pagados + " Recibos Pagados (" + moBusRecibos.mnPor_Pagados + "%) " + moBusRecibos.mnPagados + "€");
        txtPendientes.setText(moBusRecibos.mnNum_Pendientes + " Recibos Pendientes (" + moBusRecibos.mnPor_Pendientes + "%) " + moBusRecibos.mnPendientes + "€");
        txtDevueltos.setText(moBusRecibos.mnNum_Devueltos + " Recibos Devueltos (" + moBusRecibos.mnPor_Devueltos + "%) " + moBusRecibos.mnDevueltos + "€");
        txtRemesados.setText(moBusRecibos.mnNum_Remesados + " Recibos Remesados (" + moBusRecibos.mnPor_Remesados + "%) " + moBusRecibos.mnRemesados + "€");

        // veo las dimensiones de la pantalla
        DisplayMetrics displayMetrics = act_Login.moContexto.getResources().getDisplayMetrics();
        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels;

        dpWidth = dpWidth - 20; // le quito los margenes

        layAnulados.getLayoutParams().width = (int)(dpWidth * moBusRecibos.mnPor_Anulados / 100);;
        layAnulados.requestLayout();

        layPagados.getLayoutParams().width = (int)(dpWidth * moBusRecibos.mnPor_Pagados / 100);;
        layPagados.requestLayout();

        layPendientes.getLayoutParams().width = (int)(dpWidth * moBusRecibos.mnPor_Pendientes / 100);;
        layPendientes.requestLayout();

        layDevueltos.getLayoutParams().width = (int)(dpWidth * moBusRecibos.mnPor_Devueltos / 100);;
        layDevueltos.requestLayout();

        layRemesados.getLayoutParams().width = (int)(dpWidth * moBusRecibos.mnPor_Remesados / 100);;
        layRemesados.requestLayout();

    }

    private class MiTareaAsincrona extends
            AsyncTask<Activity, Integer, Boolean> {

        Activity context;

        @Override
        protected Boolean doInBackground(Activity... params) {

            int lnEjercicio = Integer.parseInt(moSpinnerEjercicio.getSelectedItem().toString());

            context = params[0];
            moBusRecibos = new clsBusRecibos();
            moBusRecibos.mnAsociacion = act_Login.moAsociacion.mnCodigo;
            moBusRecibos.mrRecuperaTotales(lnEjercicio);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mrPintaGrafica();
            }
        }

    }

}
