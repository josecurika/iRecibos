package com.irecibos.irecibos;

import org.json.JSONObject;

/**
 * Created by Usuario on 20/03/2018.
 */

public class clsSocio {

    public Integer mnCodigo = 0;
    public String msNombre = "";
    public String msDireccion = "";
    public String msPoblacion = "";
    public String msProvincia = "";
    public String msPais = "";
    public String msCodPostal = "";
    public String msCuenta = "";
    public String msNif = "";
    public String msTelefono1 = "";
    public String msTelefono2 = "";
    public String msContacto = "";
    public String msObservaciones = "";
    public String msEmail = "";
    public String msTextoCartas = "";
    public String msAlta = "";
    public String msNacimiento = "";
    public Integer mnTipo = 0;
    public Integer mnFormaPago = 0;
    public Integer mnActivo = 0;
    public Integer mnCorrespondencia = 0;

    public boolean mbEsNuevo = true;

    public void mrCargaDatos(JSONObject json_data) {

        try {
            mnCodigo = json_data.getInt("codigo");
            msNombre = json_data.getString("nombre");
            msDireccion = json_data.getString("direccion");
            msPoblacion = json_data.getString("poblacion");
            msProvincia = json_data.getString("provincia");
            msPais = json_data.getString("pais");
            msCodPostal = json_data.getString("codpostal");
            msCuenta = json_data.getString("cuenta");
            msNif = json_data.getString("nif");
            msTelefono1 = json_data.getString("telefono1");
            msTelefono2 = json_data.getString("telefono2");
            msContacto = json_data.getString("contacto");
            msObservaciones = json_data.getString("observaciones");
            msEmail = json_data.getString("email");
            msTextoCartas = json_data.getString("textocartas");
            msAlta = json_data.getString("alta");
            msNacimiento = json_data.getString("nacimiento");
            mnTipo = json_data.getInt("tipo");
            mnFormaPago = json_data.getInt("formapago");
            mnActivo  = json_data.getInt("activo");
            mnCorrespondencia = json_data.getInt("correspondencia");
        } catch (Exception ex) {}

    }

    public String mfsFormaPago() {

        String lsFormaPago = "";

        try {
            clsFormasPago loFormaPago = new clsFormasPago();
            loFormaPago.mnCodigo = mnFormaPago;
            lsFormaPago = act_Login.moAsociacion.mcolFormasPago.get(loFormaPago.mfsCodigo()).msDescripcion;
        } catch (Exception ex) {}

        return lsFormaPago;

    }

    public String mfsTipo() {

        String lsTipo = "";

        try {
            clsTipos loTipo = new clsTipos();
            loTipo.mnCodigo = mnTipo;
            lsTipo = act_Login.moAsociacion.mcolTipos.get(loTipo.mfsCodigo()).msDescripcion;
        } catch (Exception ex) {}

        return lsTipo;

    }

}
