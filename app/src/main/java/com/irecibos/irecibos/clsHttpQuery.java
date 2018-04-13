package com.irecibos.irecibos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Usuario on 31/08/2017.
 */

public class clsHttpQuery extends Thread {

    public static final String SOAP_ACTION = "urn:webserv";
    public static final String NAMESPACE = "urn:webserv";
    public static final String ENDPOINTWS = "http://irecibos.com/webservice/serv.php";

    public ArrayList<PropertyInfo> mcolParametros;

    public String msFuncion = "";
    public String msEstado = "START";
    public String msResultado = "";
    public String msError = "";
    public int mnTimeOut_Milisegundos = 2500;

    public clsHttpQuery() {
        msEstado = "START";
        mcolParametros = new ArrayList<PropertyInfo>();
    }

    public void mrAñadeString(String lsNombre, String lsValor) {

        PropertyInfo loParametro = new PropertyInfo();
        loParametro.setName(lsNombre);
        loParametro.setValue(lsValor);
        loParametro.setType(String.class);
        mcolParametros.add(loParametro);

    }

    public void mrAñadeDoble(String lsNombre, double lnValor) {

        PropertyInfo loParametro = new PropertyInfo();
        loParametro.setName(lsNombre);
        loParametro.setValue(lnValor);
        loParametro.setType(double.class);
        mcolParametros.add(loParametro);

    }

    public void mrAñadeEntero(String lsNombre, int lnValor) {

        PropertyInfo loParametro = new PropertyInfo();
        loParametro.setName(lsNombre);
        loParametro.setValue(lnValor);
        loParametro.setType(int.class);
        mcolParametros.add(loParametro);

    }

    public void run() {

        SoapObject userRequest = new SoapObject(NAMESPACE, msFuncion);

        for (int x=0; x < mcolParametros.size(); x++) {
            userRequest.addProperty(mcolParametros.get(x));
        }

        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(userRequest);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(ENDPOINTWS);
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION, envelope);

            msResultado = envelope.getResponse().toString();
            msEstado = "OK";

        } catch (Exception exception) {
            msError = "Callsoap error " + exception.toString() + " Err=" + msResultado;
            msEstado = "KO";
        }

    }

}
