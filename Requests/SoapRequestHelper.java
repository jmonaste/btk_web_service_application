package com.introtoandroid.samplematerial.Requests;


import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

/**
 * Created by jmonaste on 14/03/2017.
 */

public class SoapRequestHelper {

    public static Element buildAuthHeader(String... params) {

        String codigoEmpresa = "";
        String codigoGrupoEmpresa = "";
        String claveCredencial = "";

        /*AQUI DEBEMOS RECIBIR LOS PARÁMETROS*/
        /*SUPONGAMOS QUE ESTAMOS ESPERANDO TRES PARÁMETROS*/
        if (params.length == 3) {
            codigoEmpresa = params[0];
            claveCredencial = params[1];
            codigoGrupoEmpresa = params[2];
        }

        /*Constant*/
        String NAMESPACE = "http://tempuri.org/";  //Espacio de nombres utilizado por el servicio web

        /*Header container*/
        Element h = new Element().createElement(NAMESPACE, "Credenciales");

        /*Adding Company Code to the header*/
        Element companyCode = new Element().createElement(NAMESPACE, "CodigoEmpresa");
        companyCode.addChild(Node.TEXT, codigoEmpresa);
        h.addChild(Node.ELEMENT, companyCode);

        /*Adding Credentials to the header*/
        Element credential = new Element().createElement(NAMESPACE, "ClaveCredencial");
        //credential.addChild(Node.TEXT, claveCredencial);
        credential.addChild(Node.TEXT, claveCredencial);
        h.addChild(Node.ELEMENT, credential);

        /*Adding Company Group Code to the header*/
        Element companyGroupCode = new Element().createElement(NAMESPACE, "CodigoGrupoEmpresa");
        companyGroupCode.addChild(Node.TEXT, codigoGrupoEmpresa);
        h.addChild(Node.ELEMENT, companyGroupCode);

        return h;
    }



}
