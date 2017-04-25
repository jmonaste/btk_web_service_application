package com.introtoandroid.samplematerial.Activities;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.introtoandroid.samplematerial.R;
import com.introtoandroid.samplematerial.Requests.RequestVehicleTurns;
import com.introtoandroid.samplematerial.VehiculeTurnInfo.CardBTKVehiculeTurnInfo;
import com.introtoandroid.samplematerial.VehiculeTurnInfo.VehiculeTurnInformationAdapter;

import java.util.ArrayList;


public class TurnViewerActivity extends AppCompatActivity {

    /*DEFINICION DE CONSTANTES PARA USO DE LA APLICACIÓN*/
    private static final String VEHICULE_CODE_STRING = "vcode";
    private static final String VEHICULE_NAME_STRING = "vname";
    private static final String VEHICULE_REGNUM_STRING = "vregum";
    private static final String VEHICULE_COMPANYNAME_STRING = "vcompname";
    private static final String VEHICULE_TYPE_STRING = "vtype";

    private RecyclerView recyclerView;
    private VehiculeTurnInformationAdapter adapter;
    private ArrayList<CardBTKVehiculeTurnInfo> cardsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turns_viewer_copy_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SE SINCRONIZA EL THEME MATERIAL
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.primary)));

        //CAMBIOS PROGRAMATICOS DEL APPBAR
        toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.primary)));



        //SE ACTUALIZAN LOS DATOS DE LA CABECERA
        fillHeadetData();

        //SE ACTUALIZA LA UI LLAMANDO AL METODO FILLCARDVIEWS QUE SE ENCARGA DE SINCRONIZAR DATOS
        fillCardViews();

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        fillCardViews();
        //    }
        //});
    }

    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    //LLAMADA AL SERVIDOR PARA SINCRONIZAR DATOS CON UI
    private void fillCardViews() {
        String codigoVehiculo = getIntent().getStringExtra(VEHICULE_CODE_STRING);
        RequestVehicleTurns request = new RequestVehicleTurns(this, findViewById(android.R.id.content));
        request.execute(codigoVehiculo);
    }

    //ACTUALIZACION DE LOS DATOS DE LA CABECERA
    private void fillHeadetData() {
        String codigoVehiculo = getIntent().getStringExtra(VEHICULE_CODE_STRING);
        String vehiculeName = getIntent().getStringExtra(VEHICULE_NAME_STRING);
        String vehiculeRegNumber = getIntent().getStringExtra(VEHICULE_REGNUM_STRING);
        String vehiculeCompanyName = getIntent().getStringExtra(VEHICULE_COMPANYNAME_STRING);
        String vehiculeType = getIntent().getStringExtra(VEHICULE_TYPE_STRING);

        //Obtenemos referencias al layout
        TextView tv_code = (TextView) this.findViewById(R.id.tv_vcode_content);
        TextView tv_vname = (TextView) this.findViewById(R.id.tv_vehicule_name);
        TextView tv_regnum = (TextView) this.findViewById(R.id.tv_vehicule_name_3);
        TextView tv_compname = (TextView) this.findViewById(R.id.tv_vehicule_name_4);
        TextView tv_vtype = (TextView) this.findViewById(R.id.tv_vehicule_name_6);

        tv_code.setText(codigoVehiculo);
        tv_vname.setText(vehiculeName);
        tv_regnum.setText(vehiculeRegNumber);
        tv_compname.setText(vehiculeCompanyName);
        tv_vtype.setText(vehiculeType);

        //El numero de turnos tendra que actualizarse despues de l llamada SOAP
    }
}
