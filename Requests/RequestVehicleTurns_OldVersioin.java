package com.introtoandroid.samplematerial.Requests;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.introtoandroid.samplematerial.CompanyData.Company;
import com.introtoandroid.samplematerial.CompanyData.GMT;
import com.introtoandroid.samplematerial.CompanyData.Line;
import com.introtoandroid.samplematerial.CompanyData.SubLineRoutePoints;
import com.introtoandroid.samplematerial.CompanyData.Service;
import com.introtoandroid.samplematerial.CompanyData.SubLine;
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
public class RequestVehicleTurns_OldVersioin extends AsyncTask<String, Void, List<Turn>> {

    //PARA PASAR EL CONTEXT Y LA VISTA VIEW COMO PARÁMETRO A LA TAREA ASÍNCRONA
    private Context rootContext;
    private View rootView;

    /**
     * Constructor de la clase RequestVehicleTurns_OldVersioin. Es necesario especificar el contexto Context
     * y la vista View para que el método onPostExecute() pueda actualizar la UI.
     * @param rootContext Contexto actual desde donde se ejecuta la AsyncTask.
     * @param rootView Vista actual desde donde se ejecuta la AsyncTask.
     */
    public RequestVehicleTurns_OldVersioin(Context rootContext, View rootView) {
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
        String NAMESPACE = rootContext.getResources().getString(R.string.myNAMESPACE);;                                                  // Espacio de nombres utilizado por el servicio web
        String URL = rootContext.getResources().getString(R.string.myURL);                     // URL para realizar la conexión
        String METHOD_NAME = rootContext.getResources().getString(R.string.myMETHOD_NAME_OTV);                                               // Nombre del método
        String SOAP_ACTION = rootContext.getResources().getString(R.string.mySOAP_ACTION_OTV);                            // Equivalente al anterior pero en la notación SOAP

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);                                // Creamos la nueva petición SOAP

        //RELLENA EL CUERPO DE LA PETICION SOAP (BODY)
        request.addProperty(rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_CODV), codigoVehichulo);                                     // Codigo de vehículo como parámetro en el cuerpo de la petición
        request.addProperty(rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM),
                rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM));                                                                // Parámetro realmente no necesario en el cuerpo de la petición

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

        /*Recorremos los elementos devueltos para asignarlos a la lista de turnos*/
        for (int i = 0; i < resSoap.getPropertyCount(); i++) {

            Turn turno = new Turn();                                                                // Creamos un turno para la iteración
            SoapObject tn = (SoapObject)resSoap.getProperty(i);                                     // El tn es un turno que hay que ir desmenuzando para asignarlo a Turno

            turno.setTurnID(Integer.parseInt(tn.getProperty(0).toString()));           // Se asigna el ID del turno al turno actual
            turno.setTurnCode(tn.getProperty(1).toString());                                    // Se asigna el código del turno al turno actual
            turno.setTurnDescription(tn.getProperty(2).toString());                               // Se asigna la descripción del turno al turno actual

            // Aqui viene un objeto mas complejo dentro de turno, que es el TurnService
            SoapObject trnsrv = (SoapObject)tn.getProperty(3);                                      // Obtenemos el objeto turn_service de la respuesta SOAP
            List<TurnService> listaServiciosTurno = new ArrayList<TurnService>();                             // Creamos la lista que contendrá los servicios

            //Recorremos todos los servicios que tiene el turno actual
            for (int j = 0; j < trnsrv.getPropertyCount(); j++){

                TurnService servicio_turno = new TurnService();                                     // Creamos un servicio_turno para introducir los datos
                SoapObject ts = (SoapObject)trnsrv.getProperty(0);                                  // Obtenemos el objeto TurnService de la respuesta SOAP

                for (int k = 0; k < trnsrv.getPropertyCount(); k++){

                    servicio_turno.setTurnServiceID(Integer.parseInt(ts.getProperty(0).toString()));

                    //Aquí viene un objeto mas complejo, el objeto Service
                    Service servicio = new Service();                                               // Creamos un servicio para introducir los datos
                    SoapObject ss = (SoapObject)ts.getProperty(1);                                  // Obtenemos el objeto Service de la respuesta SOAP
                    List<Service> servicesList = new ArrayList<Service>();                                // Creamos la lista que contendrá los servicios

                    //Como el objeto TurnService tiene varios Services, habrá que iterar
                    for (int l = 0; l < ts.getPropertyCount(); l++){

                        servicio.setServiceID(Integer.parseInt(ss.getProperty(0).toString())); // Se asigna el identificador del servicio
                        servicio.setServiceCode(ss.getProperty(1).toString());                // Se asigna el codigo del servicio
                        servicio.setServiceStartTime(ss.getProperty(2).toString());        // Se asigna la hora de inicio estimada del servicio
                        servicio.setServiceEndTime(ss.getProperty(3).toString());           // Se asigna la hora estimada del fin del servicio

                        //Aquí viene un objeto mas complejo, el objeto SubLine
                        SubLine subLine = new SubLine();                                            // Creamos una sublinea
                        SoapObject sl = (SoapObject)ss.getProperty(4);                              // Obtenemos el objeto Line de la respuesta SOAP

                        subLine.setSubLineID(Integer.parseInt(sl.getProperty(0).toString())); // Se asigna el ID de la sublinea
                        subLine.setSubLineCode(sl.getProperty(1).toString());                    // Se asigna el codigo de la sublinea
                        subLine.setSubLineName(sl.getProperty(2).toString());                    // Se asigna el nombre de la sublinea
                        subLine.setSubLineColor(sl.getProperty(3).toString());                     // Se asigna el color de la Sublinea
                        subLine.setSubLineKM(sl.getProperty(4).toString());                        // Se asignan los km de la sublinea

                        //Aquí viene un objeto mas complejo dentro de la subliea, el objeto Line_Subline
                        Line line = new Line();                                                     // Creamos una linea para almacenar los datos
                        SoapObject ll = (SoapObject)sl.getProperty(5);                              // Obtenemos el objeto line_subline de la respuesta SOAP
                        line.setLineID(Integer.parseInt(ll.getProperty(0).toString())); // Se asigna el ID de la linea
                        line.setLineCode(ll.getProperty(1).toString());                          // Se asigna el código a la linea
                        line.setLineName(ll.getProperty(2).toString());                          // Se asigna el nombre a la linea
                        line.setLineDescription(ll.getProperty(3).toString());                     // Se asigna la descripción a la linea
                        line.setLineCompany(null);                                                 // IMPLEMENTAR SI SE NECESITA EN UN FUTURO

                        subLine.setLine(line); // --> Añadir la linea a la sublinea

                        //Aquí viene un objeto mas complejo dentro de la SubLine, el objeto SubLineRoutePoints
                        //Y como es una lista habrá que iterar en un for
                        SoapObject prsl = (SoapObject)sl.getProperty(6);                            // Obtenemos el objeto PuntosRutaSubLineas
                        List<SubLineRoutePoints> subLineRoutePointsList = new ArrayList<SubLineRoutePoints>(); // Creamos la lista que contendrá los puntos de la sublinea

                        for (int m = 0; m < prsl.getPropertyCount(); m++){
                            SoapObject prsl_item = (SoapObject)prsl.getProperty(m);

                                SubLineRoutePoints subLineRoutePoints = new SubLineRoutePoints();   // Creamos una SubLineRoutePoints para almacenar los datos

                                subLineRoutePoints.setSubLineRoutePointsID(Integer.parseInt(prsl_item.getProperty(0).toString()));
                                subLineRoutePoints.setLatitude(prsl_item.getProperty(1).toString());
                                subLineRoutePoints.setLongitude(prsl_item.getProperty(2).toString());
                                subLineRoutePoints.setSubLineID(prsl_item.getProperty(3).toString());
                                subLineRoutePointsList.add(subLineRoutePoints);                     // Se añade el elemento de la iteración actual al conjunto de elementos u objetos
                        }
                        subLine.setSubLineRoutePoints(subLineRoutePointsList);                      // Se añade la lista de puntos a la sulinea
                        servicio.setSubLine(subLine);                                               // Se añade la sublinea al servicio

                        if(ss.getProperty(5).toString().toLowerCase().equals("true")){              // Se obtiene el status de Lunes
                            servicio.setMondayService(true);
                        }
                        if(ss.getProperty(6).toString().toLowerCase().equals("true")){              // Se obtiene el status de Martes
                            servicio.setTuesdayService(true);
                        }
                        if(ss.getProperty(7).toString().toLowerCase().equals("true")){              // Se obtiene el status de Miércoles
                            servicio.setWednesdayService(true);
                        }
                        if(ss.getProperty(8).toString().toLowerCase().equals("true")){              // Se obtiene el status de Jueves
                            servicio.setThursdayService(true);
                        }
                        if(ss.getProperty(9).toString().toLowerCase().equals("true")){              // Se obtiene el status de Viernes
                            servicio.setFridayService(true);
                        }
                        if(ss.getProperty(10).toString().toLowerCase().equals("true")){             // Se obtiene el status de Sábado
                            servicio.setSaturdayService(true);
                        }
                        if(ss.getProperty(11).toString().toLowerCase().equals("true")){             // Se obtiene el status de Domingo
                            servicio.setSundayService(true);
                        }

                        servicesList.add(servicio); // --> Añadir el servicio a la lista de servicios
                    }
                    //servicio_turno.setIdentificadorServicio_ServicioTurno(Integer.parseInt(ts.getProperty(1).toString()));
                    servicio_turno.setTurnService(servicesList.get(1));/////////////////////////////////ÑAPA
                    servicio_turno.setServiceOrder(Integer.parseInt(ts.getProperty(2).toString()));
                }

                listaServiciosTurno.add(servicio_turno);
            }

            turno.setTurnServices(listaServiciosTurno); //metemos la lista

            // Aqui viene un objeto mas complejo dentro de turno, que es el Empresa_Turno
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
