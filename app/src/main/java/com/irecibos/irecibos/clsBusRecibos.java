package com.irecibos.irecibos;

import org.json.JSONObject;

/**
 * Created by Usuario on 23/03/2018.
 */

public class clsBusRecibos {

    public Integer mnAsociacion = 0;

    double mnDevueltos = 0;
    int mnNum_Devueltos = 0;
    double mnPor_Devueltos = 0;

    double mnPendientes = 0;
    int mnNum_Pendientes = 0;
    double mnPor_Pendientes = 0;

    double mnRemesados = 0;
    int mnNum_Remesados = 0;
    double mnPor_Remesados = 0;

    double mnPagados = 0;
    int mnNum_Pagados = 0;
    double mnPor_Pagados = 0;

    double mnAnulados = 0;
    int mnNum_Anulados = 0;
    double mnPor_Anulados = 0;

    public void mrRecuperaTotales(int lnEjercicio) {

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "totales";
            loHilo.mrAñadeEntero("id_asociacion",mnAsociacion);
            loHilo.mrAñadeEntero("ejercicio",lnEjercicio);

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
                    mnDevueltos = loResultado.getDouble("devueltos");
                    mnNum_Devueltos = loResultado.getInt("numdevueltos");
                    mnPor_Devueltos = loResultado.getDouble("pordevueltos");
                    mnPendientes = loResultado.getDouble("pendientes");
                    mnNum_Pendientes = loResultado.getInt("numpendientes");
                    mnPor_Pendientes = loResultado.getDouble("porpendientes");
                    mnRemesados = loResultado.getDouble("remesados");
                    mnNum_Remesados = loResultado.getInt("numremesados");
                    mnPor_Remesados = loResultado.getDouble("porremesados");
                    mnPagados = loResultado.getDouble("pagados");
                    mnNum_Pagados = loResultado.getInt("numpagados");
                    mnPor_Pagados = loResultado.getDouble("porpagados");
                    mnAnulados = loResultado.getDouble("anulados");
                    mnNum_Anulados = loResultado.getInt("numanulados");
                    mnPor_Anulados = loResultado.getDouble("poranulados");
                }
            } else {
                //Toast.makeText("ERROR", actLogin.moContexto, "", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {

        }

    }

}
