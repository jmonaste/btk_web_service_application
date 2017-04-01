/*PACKAGE DEFINITION--*/
package com.introtoandroid.samplematerial.Requests;

/*IMPORT SENTENCES--*/

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.introtoandroid.samplematerial.CompanyData.Company;
import com.introtoandroid.samplematerial.CompanyData.GMT;
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.CompanyData.TurnService;
import com.introtoandroid.samplematerial.CompanyData.VehicleGroup;
import com.introtoandroid.samplematerial.R;

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
     * Constructor de la clase RequestVehicleTurns. Es necesario especificar el contexto Context
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
        //HACEMOS REFERENIA A LA UI
        //AQUI SE TERMINA LA TAREA Y PODEMOS ACTUALIZAR LOS VALORES EN LA UI, CUIDADO CON CONCURRENCIA

        //ListView listView = (ListView)rootView.findViewById(R.id.ListView_v2);

        ArrayList<Turn> turnos = new ArrayList<>();

        //Añadimos los turnos al array list
        for(int i=0; i<listaTurnos.size(); i++){
            turnos.add(listaTurnos.get(i));
        }

        //TurnListAdapter adapter = new TurnListAdapter(rootContext, R.layout.row_turn_view, turnos);
        //listView.setAdapter(adapter);



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

            turno.setIdentificador_Turno(Integer.parseInt(tn.getProperty(0).toString()));
            turno.setCodigo_Turno(tn.getProperty(1).toString());
            turno.setDescripcion_Turno(tn.getProperty(2).toString());

            // Aqui viene un objeto mas complejo dentro de turno, que es el TurnService
            //y como es una lista habrá que iterar en un for
            SoapObject gp = (SoapObject)tn.getProperty(3);

            List<TurnService> ServiciosTurno_Turno = new ArrayList<TurnService>();                  // Creamos la lista que contendrá los grupos

            for (int j = 0; j < gp.getPropertyCount(); j++){

                TurnService servicio_turno = new TurnService();

                SoapObject ts = (SoapObject)gp.getProperty(0);
                for (int k = 0; k < ts.getPropertyCount(); k++){

                    servicio_turno.setIdentificadorTurno_ServicioTurno(Integer.parseInt(ts.getProperty(0).toString()));
                    servicio_turno.setIdentificadorServicio_ServicioTurno(Integer.parseInt(ts.getProperty(1).toString()));
                    servicio_turno.setOrdenServicio_ServicioTurno(Integer.parseInt(ts.getProperty(2).toString()));

                }

                ServiciosTurno_Turno.add(servicio_turno);
            }

            turno.setServiciosTurno_Turno(ServiciosTurno_Turno); //metemos la lista

            // Aqui viene un objeto mas complejo dentro de turno, que es el Empresa_Turno
            //y como es una lista habrá que iterar en un for
            SoapObject et = (SoapObject)tn.getProperty(4);

            Company compania = new Company();

            compania.setIdentificador_Empresa(Integer.parseInt(et.getProperty(0).toString()));
            compania.setCodigo_Empresa(et.getProperty(1).toString());
            compania.setNombre_Empresa(et.getProperty(2).toString());
            compania.setEsquemaBBDD_Empresa(et.getProperty(3).toString());
            compania.setDireccionServidor_BBDD_Empresa(et.getProperty(4).toString());
            compania.setUsuarioBBDD_Empresa(et.getProperty(5).toString());
            compania.setContraseniaBBDD_Empresa(et.getProperty(6).toString());
            compania.setPuertoBBDD_Empresa(et.getProperty(7).toString());

            //Objeto mas complejo dentro de Company (Company_Group)
            SoapObject cg = (SoapObject)et.getProperty(8); //aqui si que tiene que haber un for por que tenemos mas de uno

            List<VehicleGroup> grupos = new ArrayList<VehicleGroup>(); //Creamos la lista que contendrá los grupos

            for (int n = 0; n < cg.getPropertyCount(); n++){

                //Objeto mas complejo dentro de Vehicle_group (company_group)
                SoapObject cgg = (SoapObject)cg.getProperty(0);
                VehicleGroup grupoVehiculo = new VehicleGroup();

                grupoVehiculo.setIdentificador_Grupo(Integer.parseInt(cgg.getProperty(0).toString()));
                grupoVehiculo.setNombre_Grupo(cgg.getProperty(1).toString());
                grupoVehiculo.setPerfilSeguimiento_Grupo(cgg.getProperty(2).toString());
                grupoVehiculo.setIdentificador_Empresa(Integer.parseInt(cgg.getProperty(3).toString()));

                //Objeto mas complejo dentro de Company_Group (GMT)
                SoapObject gmtcg = (SoapObject)cgg.getProperty(4);
                GMT GMT_content_cg = new GMT();//Creamos objeto GMT vacío para relenarlo y asignarlo luego
                GMT_content_cg.setIdentificador_GMT(Integer.parseInt(gmtcg.getProperty(0).toString()));
                GMT_content_cg.setValor_GMT(gmtcg.getProperty(1).toString());

                grupoVehiculo.setGMT(GMT_content_cg); //Asignamos GMT al grupo_vehicle

                grupos.add(grupoVehiculo); //Añadir el grupo de vehiculo que acabamos de crear a la lista de grupos
            }

            compania.setGrupos_Empresa(grupos); //se mete el array list de grupos
            turno.setEmpresa_Turno(compania); //se añade la empresa

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
            if(turnList.get(i).getCodigo_Turno().equals(codeID)) {
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
