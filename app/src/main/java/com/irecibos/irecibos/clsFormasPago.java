package com.irecibos.irecibos;

/**
 * Created by Usuario on 26/03/2018.
 */

public class clsFormasPago {

    public Integer mnCodigo = 0;
    public String msDescripcion = "";
    public double mnImporte = 0;

    public String mfsCodigo() {
        return "clsFormasPago-" + mnCodigo;
    }

}
