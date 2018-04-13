package com.irecibos.irecibos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Usuario on 28/06/2017.
 */

public class clsUsuario {

    public Integer mnCodigo = 0;
    public Integer mnClave = 0;
    public String msNombre = "";
    public String msActivo = "";

    public Integer mnError = 0;
    public String msMensajeError = "";

    public boolean mbEsNuevo = true;

    public boolean mfbValidaAcceso(String lsUsuario, String lsPassword) {

        boolean lbAutorizado = false;

        try {

            clsHttpQuery loHilo = new clsHttpQuery();
            loHilo.msFuncion = "login";
            loHilo.mrAñadeString("user",lsUsuario);
            loHilo.mrAñadeString("password",lsPassword);

            loHilo.join();
            loHilo.start();

            while (loHilo.msEstado.equals("START")) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }

            if (loHilo.msEstado.equals("OK")) {
                if (loHilo.msResultado.equals("SI")) lbAutorizado = true;
            } else {}

        } catch (Exception ex) {}

        return lbAutorizado;

    }

}
