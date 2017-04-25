/*PACKAGE DEFINITION--*/
package com.introtoandroid.samplematerial.Requests;

/*IMPORT SENTENCES--*/

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo.CardBTKTurnServiceInfo;
import com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo.TurnServiceInformationAdapter;

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
public class RequestTurnServices extends AsyncTask<String, Void, List<Turn>> {

    //PARA PASAR EL CONTEXT Y LA VISTA VIEW COMO PARÁMETRO A LA TAREA ASÍNCRONA
    private Context rootContext;
    private View rootView;

    /**
     * Constructor de la clase RequestVehicleTurns_OldVersioin. Es necesario especificar el contexto Context
     * y la vista View para que el método onPostExecute() pueda actualizar la UI.
     * @param rootContext Contexto actual desde donde se ejecuta la AsyncTask.
     * @param rootView Vista actual desde donde se ejecuta la AsyncTask.
     */
    public RequestTurnServices(Context rootContext, View rootView) {
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
                rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM));          // Parámetro realmente no necesario en el cuerpo de la petición

        //SE CREA EL CONTENEDOR SOAP (ENVELOPE) Y SE LE ASOCIA LA PETICIÓN QUE QUEREMOS CREAR
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);                        // Se crea objeto Soap SerializationEnvelope indicando la versión de SOAP que vamos a usar
        envelope.dotNet = true;                                                                                        // Indicamos que se trata de un servicio web .NET activando su propiedad dotNet
        envelope.headerOut = new Element[1];                                                                           // Se crea la cabecera
        envelope.headerOut[0] = SoapRequestHelper.buildAuthHeader(codigoEmpresa, claveCredencial, codigoGrupoEmpresa); // Se actualizan los valores de la cabecera
        envelope.encodingStyle = "UTF-8";                                                                              // Se establece el tipo de codificación
        envelope.setOutputSoapObject(request);                                                                         // Asociamos la petición al contenedor

        // AHORA SE VA A REALIZAR LA PETICIÓN PROPIAMENTE DICHA MEDIANTE EL PROTOCOLO HTTP
        HttpTransportSE transport = new HttpTransportSE(URL);

        try {

            transport.call(SOAP_ACTION, envelope);                         // Se realiza la petición
            Vector<SoapObject> resSoap = (Vector<SoapObject>)envelope.getResponse(); // Se procesa el resultado devuelto con la función dedicada
            String resllam1 = resSoap.toString();                          //

            List<Turn> listaTurnos = GetVcTurnsResponse(resSoap.firstElement());

            return(listaTurnos);
        }
        catch (Exception e) {
            return(null);
        }

    }

    @Override
    //la parte en la que debemos actualizar la interfaz de usuario tras la llamada que debe ir al método onPostExecute().
    protected void onPostExecute(List<Turn> listaTurnos) {
        //Aquí actualizamos la UI y manipulamos las Views
        updateUiWithResult(listaTurnos);
    }

    private void updateUiWithResult(List<Turn> listaTurnos) {

        //Cambiar de actividad pasando la lista como parámetro
        ArrayList<CardBTKTurnServiceInfo> cardsList = new ArrayList<>();
        TurnServiceInformationAdapter adapter = null;
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        //Hay que buscar los servicios del turnos que buscamos




        //Aqui rellenar las tarjetas
        for (int i = 0; i < listaTurnos.size(); i++) {

            CardBTKTurnServiceInfo card = new CardBTKTurnServiceInfo();

            card.setId((long) i);
            //card.setSubLine(listaTurnos.get(i).get);
            card.setServiceCode("ejemplo de codigo");
            card.setStartTime("13:22");
            card.setFinishTime("14:20");

            cardsList.add(card);
        }

        if (adapter == null) {
            adapter = new TurnServiceInformationAdapter(rootContext, cardsList);
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

        List<Turn> TurnList = new ArrayList<Turn>(); //Creamos la lista que contendrá los vehículos

        /*Recorremos los elementos devueltos para asignarlos a la lista de turnos*/
        for (int i = 0; i < resSoap.getPropertyCount(); i++) {

            //Creamos un turno para la iteración
            Turn turno = new Turn();

            SoapObject tn = (SoapObject)resSoap.getProperty(i); //El tn es un turno que hay que ir desmenuzando para asignarlo a Turno

            turno.setTurnID(Integer.parseInt(tn.getProperty(0).toString()));
            turno.setTurnCode(tn.getProperty(1).toString());
            turno.setTurnDescription(tn.getProperty(2).toString());

            // Aqui viene un objeto mas complejo dentro de turno, que es el TurnService
            //y como es una lista habrá que iterar en un for
            SoapObject trnsrv = (SoapObject)tn.getProperty(3);
            List<TurnService> listaServiciosTurno = new ArrayList<TurnService>();                  // Creamos la lista que contendrá los turnos

            for (int j = 0; j < trnsrv.getPropertyCount(); j++){

                TurnService servicio_turno = new TurnService();
                SoapObject ts = (SoapObject)trnsrv.getProperty(0);

                for (int k = 0; k < trnsrv.getPropertyCount(); k++){

                    servicio_turno.setTurnServiceID(Integer.parseInt(ts.getProperty(0).toString()));

                    //Aquí viene un objeto mas complejo, el objeto TurnService
                    //y como es una lista habrá que iterar en un for
                    Service servicio = new Service();
                    SoapObject ss = (SoapObject)ts.getProperty(1);
                    List<Service> servicesList = new ArrayList<Service>();                  // Creamos la lista que contendrá los servicios

                    for (int l = 0; l < ts.getPropertyCount(); l++){

                        servicio.setServiceID(Integer.parseInt(ss.getProperty(0).toString()));
                        servicio.setServiceCode(ss.getProperty(1).toString());
                        servicio.setServiceStartTime(ss.getProperty(2).toString());
                        servicio.setServiceEndTime(ss.getProperty(3).toString());

                        //Aquí viene un objeto mas complejo, el objeto SubLine
                        //implementarr
                        SubLine subLine = new SubLine();
                        SoapObject sl = (SoapObject)ss.getProperty(4);

                        subLine.setSubLineID(Integer.parseInt(sl.getProperty(0).toString()));
                        subLine.setSubLineCode(sl.getProperty(1).toString());
                        subLine.setSubLineName(sl.getProperty(2).toString());
                        subLine.setSubLineColor(sl.getProperty(3).toString());
                        subLine.setSubLineKM(sl.getProperty(4).toString());

                        //Aquí viene un objeto mas complejo, el objeto TurnService
                        //y como es una lista habrá que iterar en un for
                        Line line = new Line();
                        SoapObject ll = (SoapObject)sl.getProperty(5);
                        line.setLineID(Integer.parseInt(ll.getProperty(0).toString()));
                        line.setLineCode(ll.getProperty(1).toString());
                        line.setLineName(ll.getProperty(2).toString());
                        line.setLineDescription(ll.getProperty(3).toString());
                        line.setLineCompany(null); //Aquí en un futuro se necesita se debería implementar

                        subLine.setLine(line);

                        //Aquí viene un objeto mas complejo, el objeto TurnService
                        //y como es una lista habrá que iterar en un for
                        SoapObject prsl = (SoapObject)sl.getProperty(6);
                        List<SubLineRoutePoints> subLineRoutePointsList = new ArrayList<SubLineRoutePoints>();                  // Creamos la lista que contendrá los grupos

                        for (int m = 0; m < prsl.getPropertyCount(); m++){
                            SoapObject prsl_item = (SoapObject)prsl.getProperty(m);
                            for (int n=0; n<prsl_item.getPropertyCount(); n++){

                                SubLineRoutePoints subLineRoutePoints = new SubLineRoutePoints();

                                subLineRoutePoints.setSubLineRoutePointsID(Integer.parseInt(prsl_item.getProperty(0).toString()));
                                subLineRoutePoints.setLatitude(prsl_item.getProperty(1).toString());
                                subLineRoutePoints.setLongitude(prsl_item.getProperty(2).toString());
                                subLineRoutePoints.setSubLineID(prsl_item.getProperty(3).toString());

                                subLineRoutePointsList.add(subLineRoutePoints);
                            }
                        }
                        subLine.setSubLineRoutePoints(subLineRoutePointsList);

                        if(ss.getProperty(5).toString().toLowerCase().equals("true")){
                            servicio.setMondayService(true);
                        }
                        if(ss.getProperty(6).toString().toLowerCase().equals("true")){
                            servicio.setTuesdayService(true);
                        }
                        if(ss.getProperty(7).toString().toLowerCase().equals("true")){
                            servicio.setWednesdayService(true);
                        }
                        if(ss.getProperty(8).toString().toLowerCase().equals("true")){
                            servicio.setThursdayService(true);
                        }
                        if(ss.getProperty(9).toString().toLowerCase().equals("true")){
                            servicio.setFridayService(true);
                        }
                        if(ss.getProperty(10).toString().toLowerCase().equals("true")){
                            servicio.setSaturdayService(true);
                        }
                        if(ss.getProperty(11).toString().toLowerCase().equals("true")){
                            servicio.setSundayService(true);
                        }

                        servicesList.add(servicio);
                    }
                    //servicio_turno.setIdentificadorServicio_ServicioTurno(Integer.parseInt(ts.getProperty(1).toString()));
                    servicio_turno.setTurnService(servicesList.get(1)); /////////////////////////////////////////////////////////////////////////////////ÑAPA
                    servicio_turno.setServiceOrder(Integer.parseInt(ts.getProperty(2).toString()));
                }
            }

            turno.setTurnServices(listaServiciosTurno); //metemos la lista

            // Aqui viene un objeto mas complejo dentro de turno, que es el Empresa_Turno
            //y como es una lista habrá que iterar en un for
            SoapObject et = (SoapObject)tn.getProperty(4);

            Company compania = new Company();

            compania.setCompanyID(Integer.parseInt(et.getProperty(0).toString()));
            compania.setCompanyCode(et.getProperty(1).toString());
            compania.setCompanyName(et.getProperty(2).toString());
            compania.setCompanyDDBBScheme(et.getProperty(3).toString());
            compania.setCompanyServerDir(et.getProperty(4).toString());
            compania.setCompanyDDBBUser(et.getProperty(5).toString());
            compania.setCompanyDDBBPasword(et.getProperty(6).toString());
            compania.setCompanyDDBBPort(et.getProperty(7).toString());

            //Objeto mas complejo dentro de Company (Company_Group)
            SoapObject cg = (SoapObject)et.getProperty(8); //aqui si que tiene que haber un for por que tenemos mas de uno

            List<VehicleGroup> grupos = new ArrayList<VehicleGroup>(); //Creamos la lista que contendrá los grupos

            for (int n = 0; n < cg.getPropertyCount(); n++){

                //Objeto mas complejo dentro de Vehicle_group (company_group)
                SoapObject cgg = (SoapObject)cg.getProperty(0);
                VehicleGroup grupoVehiculo = new VehicleGroup();

                grupoVehiculo.setGroupID(Integer.parseInt(cgg.getProperty(0).toString()));
                grupoVehiculo.setGroupName(cgg.getProperty(1).toString());
                grupoVehiculo.setGroupFollowerProfile(cgg.getProperty(2).toString());
                grupoVehiculo.setGroupCompanyID(Integer.parseInt(cgg.getProperty(3).toString()));

                //Objeto mas complejo dentro de Company_Group (GMT)
                SoapObject gmtcg = (SoapObject)cgg.getProperty(4);
                GMT GMT_content_cg = new GMT();//Creamos objeto GMT vacío para relenarlo y asignarlo luego
                GMT_content_cg.setGmtID(Integer.parseInt(gmtcg.getProperty(0).toString()));
                GMT_content_cg.setGmtValue(gmtcg.getProperty(1).toString());

                grupoVehiculo.setGroupGMT(GMT_content_cg); //Asignamos GMT al grupo_vehicle

                grupos.add(grupoVehiculo); //Añadir el grupo de vehiculo que acabamos de crear a la lista de grupos
            }

            compania.setCompanyGroups(grupos); //se mete el array list de grupos
            turno.setTurnCompany(compania); //se añade la empresa

            //VehicleList.add(vehicle);//Añadimos el vehículo con todos los datos ya correctos a la lista
            TurnList.add(turno);
        }

        return TurnList;
    }

    /**
     *
     * @param turnList
     * @param codeID
     * @return
     */
    public static Turn getTurnByCode(List<Turn> turnList, int codeID){

        //Traversal
        boolean found = false;
        Turn foundedTurn = null;

        for(int i = 0; i < turnList.size(); i++) {
            //Recorremos cada turno
            if(turnList.get(i).getTurnCode().equals(codeID)) {
                //Process data do whatever you want
                Log.d("jmdeb", "Found it!");
                found = true;
                foundedTurn = turnList.get(i);
            }
        }

        if(found == true){
            return foundedTurn;
        }
        else{
            return null;
        }
    }


}
