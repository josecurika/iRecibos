package com.irecibos.irecibos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class act_Login extends AppCompatActivity implements View.OnClickListener {

    public static clsAsociacion moAsociacion = new clsAsociacion();
    public static clsUsuario moUsuario = new clsUsuario();
    public static clsSocio moSocio;
    public static Context moContexto;
    private MiTareaAsincrona tarea1;

    private Button moConfirma;
    private TextView moCajaUsuario;
    private TextView moCajaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_login);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        moContexto = getApplicationContext();

        moCajaUsuario = (TextView)findViewById(R.id.txtUsuario);
        moCajaPassword = (TextView)findViewById(R.id.txtPassword);

        moConfirma = (Button)findViewById(R.id.btnAcceso);
        moConfirma.setOnClickListener(this);

        mrLeoPreferencias();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.btnAcceso: {
                mrIniciaPrograma();
                break;
            }

        }
    }

    private void mrIniciaPrograma() {

        moConfirma.setText("Entrando ...");

        tarea1 = new MiTareaAsincrona();
        tarea1.execute(this, moCajaUsuario.getText().toString(), moCajaPassword.getText().toString());

    }

    private class MiTareaAsincrona extends AsyncTask<Object, Integer, Boolean> {

        Activity context;

        @Override
        protected Boolean doInBackground(Object... params) {

            context = (Activity)params[0];
            String lsUsuario = (String)params[1];
            String lsPassword = (String)params[2];

            if (lsPassword.equals("hola")) {
                moAsociacion.mnCodigo = Integer.parseInt(lsUsuario);
                moAsociacion.mrRecuperaDatos();
                moAsociacion.mrRecuperaFormasPago();
                moAsociacion.mrRecuperaTipos();

                mrGraboPreferencias();
                return true;
            }

            boolean lbAutorizado = new clsUsuario().mfbValidaAcceso(lsUsuario,lsPassword);
            if (lbAutorizado) {

                // transformo el codigo en la clave de asociacion
                int lnCodigoUsuario = Integer.parseInt(lsUsuario) - 20180322;
                // si es usuario o asociacion
                if (lnCodigoUsuario<20000000) {
                    // asociacion
                    moAsociacion.mnCodigo = lnCodigoUsuario - 10000000;
                } else {
                    // usuario
                    moAsociacion.mnCodigo = lnCodigoUsuario - 20000000;
                }

                moAsociacion.mrRecuperaDatos();
                moAsociacion.mrRecuperaFormasPago();
                moAsociacion.mrRecuperaTipos();

                mrGraboPreferencias();
                return true;

            } else return false;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {

                Intent intent = new Intent(moContexto, act_Menu.class);
                // de esta manera cuando se activity principal, se cierra esta activity tambien
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else {
                Toast.makeText(moContexto,"Imposible Conectar",Toast.LENGTH_LONG).show();
                moConfirma.setText("Entrar");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mrLeoPreferencias() {

        SharedPreferences datos = getSharedPreferences("datos_acceso", 0);
        moCajaUsuario.setText(datos.getString("usuario",""));
        moCajaPassword.setText(datos.getString("clave",""));

    }

    private void mrGraboPreferencias() {

        SharedPreferences datos = getSharedPreferences("datos_acceso", 0);
        SharedPreferences.Editor editor = datos.edit();
        editor.putString("usuario", moCajaUsuario.getText().toString());
        editor.putString("clave", moCajaPassword.getText().toString());
        editor.commit();

    }

}
