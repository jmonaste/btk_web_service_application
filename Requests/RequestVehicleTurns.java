package com.introtoandroid.samplematerial.Requests;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.introtoandroid.samplematerial.CompanyData.Company;
import com.introtoandroid.samplematerial.CompanyData.GMT;
import com.introtoandroid.samplematerial.CompanyData.Line;
import com.introtoandroid.samplematerial.CompanyData.Service;
import com.introtoandroid.samplematerial.CompanyData.SubLine;
import com.introtoandroid.samplematerial.CompanyData.SubLineRoutePoints;
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.CompanyData.TurnService;
import com.introtoandroid.samplematerial.CompanyData.VehicleGroup;
import com.introtoandroid.samplematerial.R;
import com.introtoandroid.samplematerial.VehiculeTurnInfo.CardBTKVehiculeTurnInfo;
import com.introtoandroid.samplematerial.VehiculeTurnInfo.VehiculeTurnInformationAdapter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.introtoandroid.samplematerial.EncryptDecrypt.EncryptDecryptHelper.TDESHelper;

/**
 * PRIVATE CLASS TO RUN AN ASYNC TASK TO CONNECT TO THE SERRVER TO GET THE INFORMATION
 * Created by jmonaste on 13/03/2017.
 *
 * This class lets the user request some information related to company vehicles
 */
public class RequestVehicleTurns extends AsyncTask<String, Void, List<Turn>> {

    //PARA PASAR EL CONTEXT Y LA VISTA VIEW COMO PARÁMETRO A LA TAREA ASÍNCRONA
    private Context rootContext;
    private View rootView;

    /**
     * Constructor de la clase RequestVehicleTurns_OldVersioin. Es necesario especificar el contexto Context
     * y la vista View para que el método onPostExecute() pueda actualizar la UI.
     * @param rootContext Contexto actual desde donde se ejecuta la AsyncTask.
     * @param rootView Vista actual desde donde se ejecuta la AsyncTask.
     */
    public RequestVehicleTurns(Context rootContext, View rootView) {
        this.rootContext = rootContext;
        this.rootView = rootView;
    }

    @Override
    protected List<Turn> doInBackground(String... params) {

        //Getting Access Data
        String codigoEmpresa = rootContext.getResources().getString(R.string.company_code);
        String codigoGrupoEmpresa = rootContext.getResources().getString(R.string.company_group_code);
        String claveCredencial = rootContext.getResources().getString(R.string.password);
        claveCredencial = TDESHelper(claveCredencial);
        String codigoVehichulo = params[0];

        //DEFINICIÓN DE CONSTANTES PARA PODER REALIZAR LAS PETICIONES AL SERVIDOR CON SOAP
        String NAMESPACE = rootContext.getResources().getString(R.string.myNAMESPACE);;             // Espacio de nombres utilizado por el servicio web
        String URL = rootContext.getResources().getString(R.string.myURL);                          // URL para realizar la conexión
        String METHOD_NAME = rootContext.getResources().getString(R.string.myMETHOD_NAME_OTV);      // Nombre del método
        String SOAP_ACTION = rootContext.getResources().getString(R.string.mySOAP_ACTION_OTV);      // Equivalente al anterior pero en la notación SOAP

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);                                // Creamos la nueva petición SOAP

        //RELLENA EL CUERPO DE LA PETICION SOAP (BODY)
        request.addProperty(rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_CODV), codigoVehichulo); // Codigo de vehículo como parámetro en el cuerpo de la petición
        request.addProperty(rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM),
                rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM));           // Parámetro realmente no necesario en el cuerpo de la petición

        //SE CREA EL CONTENEDOR SOAP (ENVELOPE) Y SE LE ASOCIA LA PETICIÓN QUE QUEREMOS CREAR
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);     // Se crea objeto Soap SerializationEnvelope indicando la versión de SOAP que vamos a usar
        envelope.dotNet = true;                                                                     // Indicamos que se trata de un servicio web .NET activando su propiedad dotNet
        envelope.headerOut = new Element[1];                                                        // Se crea la cabecera
        envelope.headerOut[0] = SoapRequestHelper.buildAuthHeader(codigoEmpresa, claveCredencial, codigoGrupoEmpresa); // Se actualizan los valores de la cabecera
        envelope.encodingStyle = "UTF-8";                                                           // Se establece el tipo de codificación
        envelope.setOutputSoapObject(request);                                                      // Asociamos la petición al contenedor

        // AHORA SE VA A REALIZAR LA PETICIÓN PROPIAMENTE DICHA MEDIANTE EL PROTOCOLO HTTP
        HttpTransportSE transport = new HttpTransportSE(URL);

        try {

            transport.call(SOAP_ACTION, envelope);                                                  // Se realiza la petición
            Vector<SoapObject> resSoap = (Vector<SoapObject>)envelope.getResponse();                // Se obtiene el vector respuesta SOAP
            List<Turn> listaTurnos = GetVcTurnsResponse(resSoap.firstElement());                    // Se procesa el resultado devuelto con la función dedicada
            return(listaTurnos);                                                                    // Se devuelve la lista con todos los turnos para el vehículo dado
        }
        catch (Exception e) {
            return(null);
        }

    }

    @Override
    protected void onPostExecute(List<Turn> listaTurnos) {
        //Cambiar de actividad pasando la lista como parámetro
        ArrayList<CardBTKVehiculeTurnInfo> cardsList = new ArrayList<>();
        VehiculeTurnInformationAdapter adapter = null;
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        //String tNumber = String.valueOf(listaTurnos.size());

        //Aqui rellenar las tarjetas
        for (int i = 0; i < listaTurnos.size(); i++) {

            CardBTKVehiculeTurnInfo card = new CardBTKVehiculeTurnInfo();

            card.setId((long) i);
            card.setVehiculeCode("Vehicule Code");
            card.setVehiculeName("Vehicule Name");
            card.setVehiculeRegNumber("Vehicule Registration Number");
            card.setVehiculeType("Vehicule Type");
            card.setColorResource(1);

            card.setIdentificador_Turno(listaTurnos.get(i).getTurnID());
            card.setCodigo_Turno(listaTurnos.get(i).getTurnCode());
            card.setDescripcion_Turno(listaTurnos.get(i).getTurnDescription());
            card.setTurn(listaTurnos.get(i));
            card.setEmpresa_Turno(listaTurnos.get(i).getTurnCompany().getCompanyName());

            cardsList.add(card);
        }

        if (adapter == null) {
            adapter = new VehiculeTurnInformationAdapter(rootContext, cardsList);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootContext));
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    /**
     *
     * @param resSoap
     * @return
     */
    public static List<Turn> GetVcTurnsResponse(SoapObject resSoap){

        List<Turn> TurnList = new ArrayList<Turn>();                                                   // Creamos la lista que contendrá los turnos

        for (int i = 0; i < resSoap.getPropertyCount(); i++) {                                      // Recorremos tantas veces como Turnos tenga la respuesta SOAP
            SoapObject turnSOAP = (SoapObject)resSoap.getProperty(i);                               // El turnSOAP es un Turn que hay que ir desmenuzando para asignarlo a turn
            Turn turn = getTurnFromResponse(turnSOAP);                                              // Obtenemos los datos tratados y encapsulados en sus clases
            TurnList.add(turn);                                                                     // Añadimos el Turn a la lista de Turns creada
        }
        return TurnList;                                                                            // Se devuelve la lista de Turns
    }

    /**
     *
     * @param turnList
     * @param codeID
     * @return
     */
    public static Turn getTurnByCode(List<Turn> turnList, String codeID){

        boolean found = false;
        Turn foundedTurn = null;

        for(int i = 0; i < turnList.size(); i++) {
            if(turnList.get(i).getTurnCode().equals(codeID)) {
                found = true;
                foundedTurn = turnList.get(i);
            }
        }
        if(found){ return foundedTurn; }
        else{ return null; }
    }


    //METODOS PARA TRATAR LOS OBJETOS SOAP DEVUELTOS POR EL SERVIDOR --->

    private static GMT getGMTFromResponse(SoapObject gmtSOAP){
        GMT gmt = new GMT();                                                                        // Creamos objeto GMT para asignar los datos
        gmt.setGmtID(Integer.parseInt(gmtSOAP.getProperty(0).toString()));              // Obtenemos el ID del GMT
        gmt.setGmtValue(gmtSOAP.getProperty(1).toString());                                        // Obtenemos el valor del GMT
        return gmt;                                                                                 // Devolvemos el objeto GMT creado
    }
    private static VehicleGroup getVehiculeGroupFromResponse(SoapObject vgSOAP){
        VehicleGroup vehicleGroup = new VehicleGroup();                                             // se crea un objeto VehiculeGroup para asignar los datos
        vehicleGroup.setGroupID(Integer.parseInt(vgSOAP.getProperty(0).toString()));    // Se asigna el identificador del grupo del vehículo
        vehicleGroup.setGroupName(vgSOAP.getProperty(1).toString());                             // Se asigna el nombre del grupo del vehiculo
        vehicleGroup.setGroupFollowerProfile(vgSOAP.getProperty(2).toString());                  // Se asignal el perfil de seguimiento
        vehicleGroup.setGroupCompanyID(Integer.parseInt(vgSOAP.getProperty(3).toString()));  // Se asigna el identificador de la empresa

        //Objeto mas complejo dentro de VehiculeGroup(GMT) ------->
        SoapObject gmtSOAP = (SoapObject)vgSOAP.getProperty(4);                                     // Se obtiene el objeto SOAP del cual se saca el GMT
        GMT gmt = getGMTFromResponse(gmtSOAP);                                                      // Se obtiene el GMT
        vehicleGroup.setGroupGMT(gmt);                                                                   // Se ssignamos GMT al VehiculeGroup

        return vehicleGroup;                                                                        // Se devuelve el objeto creado con los datos correspondientes
    }
    private static List<VehicleGroup> getVehiculeGroupListFromResponse(SoapObject vglSOAP){

        List<VehicleGroup> vehicleGroupList = new ArrayList<VehicleGroup>();                        // Creamos la lista que contendrá los grupos

        for (int i = 0; i < vglSOAP.getPropertyCount(); i++){                                       // Se itera tantas veces como grupos se tengan
            SoapObject vgSOAP = (SoapObject)vglSOAP.getProperty(0);                                 // Se obtiene el eleménto i de la lista de grupos
            VehicleGroup vehicleGroup = getVehiculeGroupFromResponse(vgSOAP);                       // Se procesa el objeto SOAP para obtener los datos
            vehicleGroupList.add(vehicleGroup);                                                     // Se añade el VehiculeGroup a la lista
        }

        return vehicleGroupList;                                                                    // Se devuelce el objeto lista que contiene los grupos
    }
    private static Company getCompanyFromResponse(SoapObject compSOAP){

        Company company = new Company();                                                            // Se crea el objeto Company para introducir los datos

        company.setCompanyID(Integer.parseInt(compSOAP.getProperty(0).toString()));     // Se asigna el ID de la empresa
        company.setCompanyCode(compSOAP.getProperty(1).toString());                              // Se asigna el código de la empresa
        company.setCompanyName(compSOAP.getProperty(2).toString());                              // Se asigna el nombre de la empresa
        company.setCompanyDDBBScheme(compSOAP.getProperty(3).toString());                         // Se asigna el esquema de BBDD de la empesa
        company.setCompanyServerDir(compSOAP.getProperty(4).toString());              // Se asigna la dirección del sesrvidor de la empresa
        company.setCompanyDDBBUser(compSOAP.getProperty(5).toString());                         // Se asigna el usuario en BBDD de la empresa
        company.setCompanyDDBBPasword(compSOAP.getProperty(6).toString());                     // Se asigna la contraseña de la BBDD de la empresa
        company.setCompanyDDBBPort(compSOAP.getProperty(7).toString());                          // Se asigna el puerto de BBDD de la empesa

        //Objeto mas complejo dentro de Company (VehiculeGroup-LIST) ------->
        SoapObject vglSOAP = (SoapObject)compSOAP.getProperty(8);                                   // Se obtiene el objeto SOAP del cual se saca la lista de grupos
        List<VehicleGroup> vehicleGroupList = getVehiculeGroupListFromResponse(vglSOAP);            // Se procesa el objeto y se obtiene la lista
        company.setCompanyGroups(vehicleGroupList); //se mete el array list de grupos              // Se asigna la lista de grupos a la empresa

        return company;                                                                             // Se devuelce el objeto Company
    }
    private static SubLineRoutePoints getSubLineRoutePointsFromResponse(SoapObject sblrpSOAP){

        SubLineRoutePoints subLineRoutePoints = new SubLineRoutePoints();                           // Creamos una SubLineRoutePoints para almacenar los datos

        subLineRoutePoints.setSubLineRoutePointsID(Integer.parseInt(sblrpSOAP.getProperty(0).toString())); // Se asigna el ID del punto
        subLineRoutePoints.setLatitude(sblrpSOAP.getProperty(1).toString());        // Se asigna la latitud del punto
        subLineRoutePoints.setLongitude(sblrpSOAP.getProperty(2).toString());       // Se asigna la longitud del punto
        subLineRoutePoints.setSubLineID(sblrpSOAP.getProperty(3).toString());           // Se asigna el ID de la sublinea

        return subLineRoutePoints;                                                                  // Devolvemos el objeto creado
    }
    private static List<SubLineRoutePoints> getSubLineRoutePointsListFromResponse(SoapObject sblrpListSOAP){

        List<SubLineRoutePoints> subLineRoutePointsList = new ArrayList<SubLineRoutePoints>();      // Creamos la lista que contendrá los puntos de la ruta

        for (int i = 0; i < sblrpListSOAP.getPropertyCount(); i++){                                 // Recorremos tantas veces como conjuntos de puntos existan
            SoapObject sblrpSOAP = (SoapObject)sblrpListSOAP.getProperty(i);                        // Obtenemos el objeto de la iteración
            SubLineRoutePoints subLineRoutePoints = getSubLineRoutePointsFromResponse(sblrpSOAP);   // Obtenemos los datos ya tratados
            subLineRoutePointsList.add(subLineRoutePoints);                                         // Se añade el conjunto de puntos actual a la lista
        }

        return subLineRoutePointsList;                                                              // Se devuelve la lista de puntos
    }
    private static Line getLineFromResponse(SoapObject subLineSOAP){

        Line line = new Line();                                                                     // Creamos una linea para almacenar los datos
        SoapObject lineSOAP = (SoapObject)subLineSOAP.getProperty(5);                               // Obtenemos el objeto Line de la respuesta SOAP SubLine, que está en la posición 5

        line.setLineID(Integer.parseInt(lineSOAP.getProperty(0).toString()));           // Se asigna el ID de la linea
        line.setLineCode(lineSOAP.getProperty(1).toString());                                    // Se asigna el código a la linea
        line.setLineName(lineSOAP.getProperty(2).toString());                                    // Se asigna el nombre a la linea
        line.setLineDescription(lineSOAP.getProperty(3).toString());                               // Se asigna la descripción a la linea
        line.setLineCompany(null);                                                                 // IMPLEMENTAR SI SE NECESITA EN UN FUTURO

        return line;                                                                                // Se devuelve el objeto Line
    }
    private static SubLine getSubLineFromResponse(SoapObject serviceSOAP){

        SubLine subLine = new SubLine();                                                            // Creamos una sublinea para añadir los datos
        SoapObject subLineSOAP = (SoapObject)serviceSOAP.getProperty(4);                            // Obtenemos el objeto SubLine de la respuesta SOAP

        subLine.setSubLineID(Integer.parseInt(subLineSOAP.getProperty(0).toString()));  // Se asigna el ID de la sublinea
        subLine.setSubLineCode(subLineSOAP.getProperty(1).toString());                           // Se asigna el codigo de la sublinea
        subLine.setSubLineName(subLineSOAP.getProperty(2).toString());                           // Se asigna el nombre de la sublinea
        subLine.setSubLineColor(subLineSOAP.getProperty(3).toString());                            // Se asigna el color de la Sublinea
        subLine.setSubLineKM(subLineSOAP.getProperty(4).toString());                               // Se asignan los km de la sublinea

        // Objeto mas complejo dentro de la subliea, el objeto SubLine ----->
        Line line = getLineFromResponse(subLineSOAP);
        subLine.setLine(line);

        // Objeto mas complejo dentro de la SubLine, el objeto SubLineRoutePoints ----->
        SoapObject sblrpListSOAP = (SoapObject)subLineSOAP.getProperty(6);
        List<SubLineRoutePoints> subLineRoutePointsList = getSubLineRoutePointsListFromResponse(sblrpListSOAP);
        subLine.setSubLineRoutePoints(subLineRoutePointsList);

        return subLine;
    }
    private static Service getServiceFromResponse(SoapObject serviceSOAP){

        Service service = new Service();                                                            // Se crea un objeto Service para añadir los datos

        service.setServiceID(Integer.parseInt(serviceSOAP.getProperty(0).toString())); // Se asigna el identificador del service
        service.setServiceCode(serviceSOAP.getProperty(1).toString());                        // Se asigna el codigo del service
        service.setServiceStartTime(serviceSOAP.getProperty(2).toString());                // Se asigna la hora de inicio estimada del service
        service.setServiceEndTime(serviceSOAP.getProperty(3).toString());                   // Se asigna la hora estimada del fin del service

        //Objeto mas complejo, el objeto SubLine ----->
        SubLine subLine = getSubLineFromResponse(serviceSOAP);                                      // Se obtiene la SubLine
        service.setSubLine(subLine);                                                                // Se añade la SubLine al Service

        if(serviceSOAP.getProperty(5).toString().toLowerCase().equals("true")){                     // Se obtiene el status de Lunes
            service.setMondayService(true);
        }
        if(serviceSOAP.getProperty(6).toString().toLowerCase().equals("true")){                     // Se obtiene el status de Martes
            service.setTuesdayService(true);
        }
        if(serviceSOAP.getProperty(7).toString().toLowerCase().equals("true")){                     // Se obtiene el status de Miércoles
            service.setWednesdayService(true);
        }
        if(serviceSOAP.getProperty(8).toString().toLowerCase().equals("true")){                     // Se obtiene el status de Jueves
            service.setThursdayService(true);
        }
        if(serviceSOAP.getProperty(9).toString().toLowerCase().equals("true")){                     // Se obtiene el status de Viernes
            service.setFridayService(true);
        }
        if(serviceSOAP.getProperty(10).toString().toLowerCase().equals("true")){                    // Se obtiene el status de Sábado
            service.setSaturdayService(true);
        }
        if(serviceSOAP.getProperty(11).toString().toLowerCase().equals("true")){                    // Se obtiene el status de Domingo
            service.setSundayService(true);
        }

        return service;
    }
    private static TurnService getTurnServiceFromResponse(SoapObject turnServiceSOAP){

        TurnService turnService = new TurnService();                                                // Creamos un TurnService para introducir los datos

        turnService.setTurnServiceID(Integer.parseInt(turnServiceSOAP.getProperty(0).toString())); // Se asigna el ID del ServiceTurn

        //Aquí viene un objeto mas complejo, el objeto Service ---->
        SoapObject serviceSOAP = (SoapObject)turnServiceSOAP.getProperty(1);                    // Obtenemos el objeto Service de la respuesta SOAP
        Service service = getServiceFromResponse(serviceSOAP);                   // Obtenemos los dato procesados en forma de lista de servicios

        turnService.setTurnService(service);                                                 // Se asigna la lista de servicios al TurnService
        turnService.setServiceOrder(Integer.parseInt(turnServiceSOAP.getProperty(2).toString())); // Se asigna el orden del servicio del ServiceTurn

        return turnService;                                                                         // Se devuelve el objeto TurnService

    }
    private static List<TurnService> getTurnServiceListFromResponse(SoapObject turnServiceListSOAP){

        List<TurnService> turnServicesList = new ArrayList<TurnService>();                          // Creamos la lista que contendrá la lista de los TurnServices del Turno

        for (int i = 0; i < turnServiceListSOAP.getPropertyCount(); i++){                           // Recorremos tantas veces como conjuntos de TurnServices existan
            SoapObject turnServiceSOAP = (SoapObject)turnServiceListSOAP.getProperty(i);            // Obtenemos el objeto de la iteración
            TurnService turnService = getTurnServiceFromResponse(turnServiceSOAP);                  // Obtenemos los datos ya tratados
            turnServicesList.add(turnService);                                                      // Se añade el TurnService actual a la lista
        }

        return turnServicesList;                                                                    // Se devuelve la lista de TurnServices
    }
    private static Turn getTurnFromResponse(SoapObject turnSOAP){

        Turn turn = new Turn();                                                                     // Creamos un Turn para la iteración

        turn.setTurnID(Integer.parseInt(turnSOAP.getProperty(0).toString()));          // Se asigna el ID del Turn al Turn actual
        turn.setTurnCode(turnSOAP.getProperty(1).toString());                                   // Se asigna el código del Turn al Turn actual
        turn.setTurnDescription(turnSOAP.getProperty(2).toString());                              // Se asigna la descripción del Turn al Turn actual

        // Aqui viene un objeto mas complejo dentro de turn, que es el TurnService ----->
        SoapObject turnServiceListSOAP = (SoapObject)turnSOAP.getProperty(3);                       // Obtenemos el objeto turnServiceList de la respuesta SOAP
        List<TurnService> turnServiceList = getTurnServiceListFromResponse(turnServiceListSOAP);    // Obtenemos los datos tratados y encapsulados
        turn.setTurnServices(turnServiceList);                                              // Añadimos al turno la lista de TurnServices

        // Aqui viene un objeto mas complejo dentro de Turn, que es el Empresa_Turno ----->
        SoapObject compSOAP = (SoapObject)turnSOAP.getProperty(4);                                  // Obtenemos el objeto Company de la respuesta SOAP
        Company company = getCompanyFromResponse(compSOAP);                                         // Obtenemos los datos ya tratados y encapsulados
        turn.setTurnCompany(company);                                                             // Se añade la empresa al TurnService

        return turn;                                                                                // Se devuelve el turno
    }

    private static List<Service> getServiceListFromResponse(SoapObject serviceListSOAP){

        List<Service> servicesList = new ArrayList<Service>();                                      // Creamos la lista que contendrá los servicios del turno

        for (int i = 0; i < serviceListSOAP.getPropertyCount(); i++){                               // Recorremos tantas veces como conjuntos de servicios existan
            SoapObject serviceSOAP = (SoapObject)serviceListSOAP.getProperty(i);                    // Obtenemos el objeto de la iteración
            Service service = getServiceFromResponse(serviceSOAP);                                  // Obtenemos los datos ya tratados
            servicesList.add(service);                                                              // Se añade el conjunto de servicios actual a la lista
        }

        return servicesList;                                                                        // Se devuelve la lista de servicios
    }

}
