package com.irecibos.irecibos;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Usuario on 27/03/2018.
 */

public class act_Ficha_Socio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_ficha_socio);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Ficha de Socio");
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView loNombre = (TextView) findViewById(R.id.txtNombre);
        loNombre.setText(act_Login.moSocio.msNombre);

        TextView loDireccion = (TextView) findViewById(R.id.txtDireccion);
        loDireccion.setText(act_Login.moSocio.msDireccion);

        TextView loPoblacion = (TextView) findViewById(R.id.txtPoblacion);
        loPoblacion.setText(act_Login.moSocio.msPoblacion + " (" + act_Login.moSocio.msProvincia + ")");

        TextView loDni = (TextView) findViewById(R.id.txtDni);
        loDni.setText(act_Login.moSocio.msNif);

        TextView loEmail = (TextView) findViewById(R.id.txtEmail);
        loEmail.setText(act_Login.moSocio.msEmail);

        TextView loTelefono = (TextView) findViewById(R.id.txtTelefono);
        loTelefono.setText(act_Login.moSocio.msTelefono1 + " - " + act_Login.moSocio.msTelefono2);

        TextView loTipo = (TextView) findViewById(R.id.txtTipo);
        loTipo.setText(act_Login.moSocio.mfsTipo());

        TextView loFP = (TextView) findViewById(R.id.txtFormaPago);
        loFP.setText(act_Login.moSocio.mfsFormaPago());

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

}
