package com.introtoandroid.samplematerial.Requests;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.introtoandroid.samplematerial.VehiculeBasicInfo.CardBTKVehiculeBasicInfo;
import com.introtoandroid.samplematerial.VehiculeCompleteInfo.CardBTKVehiculeCompleteInfo;
import com.introtoandroid.samplematerial.CompanyData.Company;
import com.introtoandroid.samplematerial.CompanyData.GMT;
import com.introtoandroid.samplematerial.CompanyData.Vehicle;
import com.introtoandroid.samplematerial.CompanyData.VehicleGroup;
import com.introtoandroid.samplematerial.CompanyData.VehicleType;
import com.introtoandroid.samplematerial.R;
import com.introtoandroid.samplematerial.VehiculeBasicInfo.VehiculeBasicInformationAdapter;

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
 * RequestCompanyVehicles es una clase que encapsula toda la funcionalidad correspondiente al
 * tratamiento de la información que devuelve el WebSevice cuando se realiza una petición con el
 * método ObtenerVehiculosEmpresa. Encapsula la petición propiamente dicha al servidor y también
 * encapsula el tratamiento de la respuesta del mismo, adecuando los valores devueltos para crear
 * una lista de objetos Vehiculo que puedan ser convenientemente tratados por otros métodos.
 */
public class RequestCompanyVehicles extends AsyncTask<String, Void, List<Vehicle>> {

    private Context rootContext;
    private View rootView;

    /**
     * Constructor de la clase RequestCompanyVehicles. Es necesario especificar el contexto Context
     * y la vista View para que el método onPostExecute() pueda actualizar la UI.
     * @param rootContext Contexto actual desde donde se ejecuta la AsyncTask.
     * @param rootView Vista actual desde donde se ejecuta la AsyncTask.
     */
    public RequestCompanyVehicles (Context rootContext, View rootView){
        this.rootContext = rootContext;
        this.rootView = rootView;
    }

    @Override
    protected List<Vehicle> doInBackground(String... params) {

        //OBTENIENDO LOS DATOS DE ACCESO AL SERVIDOR DEFINIDOS EN LOS RECURSOS
        String codComp = rootContext.getResources().getString(R.string.company_code);
        String codGrpComp = rootContext.getResources().getString(R.string.company_group_code);
        String password = rootContext.getResources().getString(R.string.password);
        password = TDESHelper(password);

        //DEFINICIÓN DE CONSTANTES PARA PODER REALIZAR LAS PETICIONES AL SERVIDOR CON SOAP
        String NAMESPACE = rootContext.getResources().getString(R.string.myNAMESPACE);                                                 // Espacio de nombres utilizado por el servicio web
        String URL = rootContext.getResources().getString(R.string.myURL);                   // URL para realizar la conexión
        String METHOD_NAME = rootContext.getResources().getString(R.string.myMETHOD_NAME_OVE);                                           // Nombre del método
        String SOAP_ACTION = rootContext.getResources().getString(R.string.mySOAP_ACTION_OVE);                        // Equivalente al anterior pero en la notación SOAP

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);                              // Creamos la nueva petición SOAP

        request.addProperty(rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM),
                rootContext.getResources().getString(R.string.mySOAP_BODY_PARAM_RESLLAM));                                                              // Parámetro realmente no necesario en el cuerpo de la petición

        //SE CREA EL CONTENEDOR SOAP (ENVELOPE) Y SE LE ASOCIA LA PETICIÓN QUE QUEREMOS CREAR
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);   // Se crea objeto Soap SerializationEnvelope indicando la versión de SOAP que vamos a usar
        envelope.dotNet = true;                                                                   // Indicamos que se trata de un servicio web .NET activando su propiedad dotNet
        envelope.headerOut = new Element[1];                                                      // Se crea la cabecera
        envelope.headerOut[0] = SoapRequestHelper.buildAuthHeader(codComp, password, codGrpComp); // Se actualizan los valores de la cabecera
        envelope.encodingStyle = "UTF-8";                                                         // Se establece el tipo de codificación
        envelope.setOutputSoapObject(request);                                                    // Asociamos la petición al contenedor

        // AHORA SE VA A REALIZAR LA PETICIÓN PROPIAMENTE DICHA MEDIANTE EL PROTOCOLO HTTP
        HttpTransportSE transport = new HttpTransportSE(URL);

        try {
            transport.call(SOAP_ACTION, envelope);                                               // Se realiza la petición
            Vector<SoapObject>  resSoap = (Vector<SoapObject>)envelope.getResponse();            // Se obtiene la respuesta
            List<Vehicle> listaVehiculosEmpresa = GetCompVehiclesResp(resSoap.firstElement());   // Se procesa el resultado devuelto
            return(listaVehiculosEmpresa);                                                       // Se devuelve la lista con los vehículos de la compañía seleccionada
        }
        catch (Exception e) {
            return(null);                                                                        // Si la petición no se ha realizado se devuelve un null que debe ser tratado
        }
    }

    @Override
    protected void onPostExecute(List<Vehicle> listaVehiculosEmpresa) {

        ArrayList<CardBTKVehiculeBasicInfo> cardsList = new ArrayList<>();
        ArrayList<CardBTKVehiculeCompleteInfo> cardsListCompleteInfo = new ArrayList<>();
        VehiculeBasicInformationAdapter adapter = null;
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        //SE RELLENAN LAS TARJETAS DEL RECYVCLERVIEW
        for (int i = 0; i < listaVehiculosEmpresa.size(); i++) {

            CardBTKVehiculeBasicInfo card = new CardBTKVehiculeBasicInfo();

            card.setId((long) i);
            card.setVehiculeCode(listaVehiculosEmpresa.get(i).getCodigo_Vehiculo());
            card.setVehiculeName(listaVehiculosEmpresa.get(i).getNombre_Vehiculo());
            card.setVehiculeRegNumber(listaVehiculosEmpresa.get(i).getMatricula_Vehiculo());
            card.setVehiculeType(listaVehiculosEmpresa.get(i).getTipoVehiculo_Vehiculo().getNombre_TipoVehiculo());
            card.setCompanyName(listaVehiculosEmpresa.get(i).getTipoVehiculo_Vehiculo().getEmpresa_TipoVehiculo().getCompanyName());
            card.setColorResource(1);

            cardsList.add(card);
        }

        if (adapter == null) {
            adapter = new VehiculeBasicInformationAdapter(rootContext, cardsList);
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
     * GetCompVehiclesResp obtiene con ayuda del WebService una lista con todos los vehículos de una
     * compañía determinada. La respuesta devuelta es convenientemente tratada para poder operar con
     * ella con facilidad. El resultado devuelto es una lista con todos los vehículos de una empresa
     * determinada con todas sus propiedades rellenadas según los datos devueltos por el WS. Para
     * obtener datos de un vehículo en concreto utilice otros métodos dentro de esta misma clase.
     * @param resSoap La respuesta sin tratar del WebService.
     * @return List<Vehicle> Lista con los vehículos de una compañía determinada.
     */
    public List<Vehicle> GetCompVehiclesResp(SoapObject resSoap){

        List<Vehicle> VehicleList = new ArrayList<Vehicle>();                                             // Se crea la lista que contendrá todos los vehículos

        //Recorremos los elementos devueltos para asignarlos a un vehículo de manera correcta
        for (int i = 0; i < resSoap.getPropertyCount(); i++) {

            Vehicle vehicle = new Vehicle();                                                        // Creamos un vehículo para la iteración
            SoapObject vc = (SoapObject)resSoap.getProperty(i);                                     // Se crea un objeto SOAP para almacenar el primer objeto devuelto
            vehicle.setIdentificador_Vehiculo(Integer.parseInt(vc.getProperty(0).toString()));      // El identificador es lo primero que se devuelve, se asigna

            //Objeto más compejo dentro de Vehicle (Grupo_Vehiculo)---------------------------------
            SoapObject vgp = (SoapObject)vc.getProperty(1);                                         // Después del ID viene un objeto del tipo VehiculoGroup
            VehicleGroup grupo = new VehicleGroup();                                                // Se crea el objeto para almacenar el VehiculeGroup
            grupo.setGroupID(Integer.parseInt(vgp.getProperty(0).toString()));          // Se almacena el ID del grupo
            grupo.setGroupName(vgp.getProperty(1).toString());                                   // Se almacena el nombre del grupo
            grupo.setGroupFollowerProfile(vgp.getProperty(2).toString());                        // Se almacena el perfil de seguimiento
            grupo.setGroupCompanyID(Integer.parseInt(vgp.getProperty(3).toString()));        // Se almacena el ID de la empresa

            //Objeto mas complejo dentro de Vehicle_Group (GMT)-------------------------------------
            SoapObject gmt = (SoapObject)vgp.getProperty(4);                                        // Después del ID de la empresa viene un objeto GMT, se vuelve a iterar
            GMT GMT_content = new GMT();                                                            // Se crea objeto GMT vacío para relenarlo y asignarlo luego
            GMT_content.setGmtID(Integer.parseInt(gmt.getProperty(0).toString()));      // Se almacena el ID del GMT
            GMT_content.setGmtValue(gmt.getProperty(1).toString());                                // Se almacena el valor para ese GMT
            grupo.setGroupGMT(GMT_content);                                                              // Se asigna el GMT al grupo

            vehicle.setGrupo_Vehiculo(grupo);                                                       // Y asignamos el grupo al vehiculo
            vehicle.setNombre_Vehiculo(vc.getProperty(2).toString());                               // Una vez rellenado el grupo y asignado, se asigna el nombre del vehículo
            vehicle.setMatricula_Vehiculo(vc.getProperty(3).toString());                            // Se almacena la matrícula del vehículo
            vehicle.setCodigo_Vehiculo(vc.getProperty(4).toString());                               // Se almacena el código del vehículo

            //Objeto mas complejo dentro de Vehicle (Vehicle_Type)----------------------------------
            SoapObject vt = (SoapObject)vc.getProperty(5);                                          // Después del código del vehículo viene un objeto Vhicule_Type
            VehicleType tipoVehiculo = new VehicleType();                                           // Se crea el objeto Vehicule_Type
            tipoVehiculo.setIdentificador_TipoVehiculo(Integer.parseInt(vt.getProperty(0).toString())); // Se almacena el ID del tipo de vehiculo

            //Objeto mas complejo dentro de Vehicle_Type (Vehicle_Company)--------------------------
            SoapObject vcomp = (SoapObject)vt.getProperty(1);                                       // Después del ID del tipo de vehículo se tiene un objeto Vehicule_Company
            Company compania = new Company();                                                       // Se crea el objeto Company vacío
            compania.setCompanyID(Integer.parseInt(vcomp.getProperty(0).toString()));   // Se almacena el ID de la empresa
            compania.setCompanyCode(vcomp.getProperty(1).toString());                            // Se almacena el Código de la empresa
            compania.setCompanyName(vcomp.getProperty(2).toString());                            // Se almacena el Nombre de la empresa
            compania.setCompanyDDBBScheme(vcomp.getProperty(3).toString());                       // Se almacena el Esquema en BBDD de la empresa
            compania.setCompanyServerDir(vcomp.getProperty(4).toString());            // Se almacena la dirección del servidor de la empresa
            compania.setCompanyDDBBUser(vcomp.getProperty(5).toString());                       // Se almacena el usuario en BBDD de la empresa
            compania.setCompanyDDBBPasword(vcomp.getProperty(6).toString());                   // Se almacena el ID de la empresa
            compania.setCompanyDDBBPort(vcomp.getProperty(7).toString());                        // Se almacena el ID de la empresa

            //Objeto mas complejo dentro de Vehicle_Company (Company_Group)-------------------------
            SoapObject cg = (SoapObject)vcomp.getProperty(8);                                       // Aquí si que tiene que haber un for porque tenemos mas de un Company_Group
            List<VehicleGroup> grupos = new ArrayList<VehicleGroup>();                                         // Creamos la lista que contendrá los grupos

            for (int n = 0; n < cg.getPropertyCount(); n++){

                //Objeto mas complejo dentro de Company_Group (Vehicule_group)----------------------
                SoapObject vgroup = (SoapObject)cg.getProperty(0);                                  // Obtenemos el objeto SOAP
                VehicleGroup grupoVehiculo = new VehicleGroup();                                    // Se crea un objeto VehiculeGroup
                grupoVehiculo.setGroupID(Integer.parseInt(vgroup.getProperty(0).toString())); // Se almacena el ID del grupo para esta iteración
                grupoVehiculo.setGroupName(vgroup.getProperty(1).toString());                    // Se almacena el nombre del grupo para esta iteración
                grupoVehiculo.setGroupFollowerProfile(vgroup.getProperty(2).toString());         // Se almacena el perfil de seguimiento del grupo para esta iteración
                grupoVehiculo.setGroupCompanyID(Integer.parseInt(vgroup.getProperty(3).toString())); // Se almacena el ID de la empresa

                //Objeto mas complejo dentro de Company_Group (GMT)---------------------------------
                SoapObject gmtcg = (SoapObject)vgroup.getProperty(4);
                GMT GMT_content_cg = new GMT();                                                     // Creamos objeto GMT vacío para relenarlo y asignarlo luego
                GMT_content_cg.setGmtID(Integer.parseInt(gmtcg.getProperty(0).toString())); // Se almacena el ID del GMT
                GMT_content_cg.setGmtValue(gmtcg.getProperty(1).toString());                       // Se almacena el valor para el GMT de esta iteracion
                grupoVehiculo.setGroupGMT(GMT_content_cg);                                               // Asignamos GMT al grupo_vehicle
                grupos.add(grupoVehiculo);                                                          // Añadir el grupo de vehiculo que acabamos de crear a la lista de grupos
            }

            compania.setCompanyGroups(grupos);                                                     // Se mete el array list de grupos

            tipoVehiculo.setEmpresa_TipoVehiculo(compania);                                         // Se mete la compañia en la empresa del vehiculo
            tipoVehiculo.setNombre_TipoVehiculo(vt.getProperty(2).toString());                      // Se completa el tipo de vehículo con su nombre
            vehicle.setTipoVehiculo_Vehiculo(tipoVehiculo);                                         // Se asigna el tipo al vehiculo

            VehicleList.add(vehicle);                                                               // Añadimos el vehículo con todos los datos ya correctos a la lista
        }

        return VehicleList;
    }

    /**
     * getVehicleByID devuelve un único vehículo dado el ID del mismo.
     * @param VehicleList Lista con todos los vehículos devueltos por el WebService.
     * @param ID ID del vehículo que se quiere buscar.
     * @return Vehículo con el ID especificado. Si no se ha encontrado el vehículo se devuelve null
     */
    public Vehicle getVehicleByID(List<Vehicle> VehicleList, int ID){

        boolean found = false;           // Variable booleana para indicar si se ha encontrado o no el vehículo
        Vehicle foundedVehicle = null;   // Variable Vehículo que almacenará el vehículo encontrado, si existe

        for(int i = 0; i < VehicleList.size(); i++) {

            if(!VehicleList.get(i).getCodigo_Vehiculo().equals("") && VehicleList.get(i).getCodigo_Vehiculo() != null){
                if(Integer.parseInt(VehicleList.get(i).getCodigo_Vehiculo()) == ID) {
                    found = true;                         // Se ha encontrado el vehículo, variable booleana lo indidará
                    foundedVehicle = VehicleList.get(i);  // Asignamos el vehículo encontrado a la variable que se devolverá
                    break;                                // Si encontramos el vehículo, salimos del bucle porque sería perder el tiempo seguir buscando
                }
            }
        }

        if(found){return foundedVehicle;} // Si se ha encontrado el vehículo, se devuelve
        else{return null;}                // Si no se ha encontrado nada se devuelve un null que será tratado mas adelante
    }

}
