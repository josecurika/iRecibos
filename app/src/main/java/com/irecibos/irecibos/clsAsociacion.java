package com.irecibos.irecibos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usuario on 20/03/2018.
 */

public class clsAsociacion {

    public Integer mnCodigo = 0;
    public String msNombre = "";
    public String msLogo = "";

    public ArrayList<clsSocio> mcolSocios;
    public Map<String,clsTipos> mcolTipos;
    public Map<String,clsFormasPago> mcolFormasPago;

    public void mrRecuperaDatos() {

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "asociacion";
            loHilo.mrAñadeEntero("id_asociacion",mnCodigo);

            loHilo.join();
            loHilo.start();

            while (loHilo.msEstado.equals("START")) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }

            if (loHilo.msEstado.equals("OK")) {
                if (!loHilo.msResultado.equals("KO")) {
                    JSONObject loResultado = new JSONObject(loHilo.msResultado);
                    msNombre = loResultado.getString("nombre");
                    msLogo = loResultado.getString("logo");
                }
            } else {
                //Toast.makeText("ERROR", actLogin.moContexto, "", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

    public void mrRecuperaSocios(String lsFiltro_Nombre) {

        mcolSocios = new ArrayList<clsSocio>();

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "recuperasocios";
            loHilo.mrAñadeEntero("id_asociacion",mnCodigo);
            loHilo.mrAñadeString("filtro_nombre",lsFiltro_Nombre);

            loHilo.join();
            loHilo.start();

            while (loHilo.msEstado.equals("START")) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }

            if (loHilo.msEstado.equals("OK")) {
                if (!loHilo.msResultado.equals("KO")) {
                    JSONArray jArray = new JSONArray(loHilo.msResultado);
                    for (int i = 0; i < jArray.length(); i++) {
                        clsSocio loSocio = new clsSocio();
                        loSocio.mrCargaDatos(jArray.getJSONObject(i));
                        mcolSocios.add(loSocio);
                    }
                }
            } else {
                //Toast.makeText("ERROR", actLogin.moContexto, "", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

    public void mrRecuperaTipos() {

        mcolTipos = new HashMap<>();

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "recuperatipos";
            loHilo.mrAñadeEntero("id_asociacion",mnCodigo);

            loHilo.join();
            loHilo.start();

            while (loHilo.msEstado.equals("START")) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }

            if (loHilo.msEstado.equals("OK")) {
                if (!loHilo.msResultado.equals("KO")) {
                    JSONArray jArray = new JSONArray(loHilo.msResultado);
                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject json_data = jArray.getJSONObject(i);
                        clsTipos loTipo = new clsTipos();
                        loTipo.mnCodigo = json_data.getInt("idtipo");
                        loTipo.msDescripcion = json_data.getString("descripcion");
                        mcolTipos.put(loTipo.mfsCodigo(),loTipo);
                    }
                }
            } else {
                //Toast.makeText("ERROR", actLogin.moContexto, "", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

    public void mrRecuperaFormasPago() {

        mcolFormasPago = new HashMap<>();

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "recuperaformasfp";
            loHilo.mrAñadeEntero("id_asociacion",mnCodigo);

            loHilo.join();
            loHilo.start();

            while (loHilo.msEstado.equals("START")) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }

            if (loHilo.msEstado.equals("OK")) {
                if (!loHilo.msResultado.equals("KO")) {
                    JSONArray jArray = new JSONArray(loHilo.msResultado);
                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject json_data = jArray.getJSONObject(i);
                        clsFormasPago loFormaPago = new clsFormasPago();
                        loFormaPago.mnCodigo = json_data.getInt("idformapago");
                        loFormaPago.msDescripcion = json_data.getString("descripcion");
                        loFormaPago.mnImporte = json_data.getInt("importe");
                        mcolFormasPago.put(loFormaPago.mfsCodigo(),loFormaPago);
                    }
                }
            } else {
                //Toast.makeText("ERROR", actLogin.moContexto, "", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

}
