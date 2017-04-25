package com.introtoandroid.samplematerial;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.introtoandroid.samplematerial.Activities.TurnViewerActivity;
import com.introtoandroid.samplematerial.Requests.RequestCompanyVehicles;
import com.introtoandroid.samplematerial.VehiculeBasicInfo.CardBTKVehiculeBasicInfo;
import com.introtoandroid.samplematerial.VehiculeBasicInfo.VehiculeBasicInformationAdapter;

import java.util.ArrayList;
import java.util.logging.Handler;

public class Main extends AppCompatActivity {

    /*DEFINICION DE CONSTANTES PARA USO DE LA APLICACIÓN*/
    private static final String DEBUG_TAG = "AppCompatActivity";

    /*DEFINICION DE CONSTANTES PARA LAS TRANSICIONES*/
    public static final String EXTRA_UPDATE = "update";
    public static final String EXTRA_DELETE = "delete";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_COLOR = "color";

    private RecyclerView recyclerView;
    private VehiculeBasicInformationAdapter adapter;
    private ArrayList<CardBTKVehiculeBasicInfo> cardsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_material);

        //SE SINCRONIZA EL THEME MATERIAL
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.primary)));

        //SWIPE LAYOUT


        //SE ACTUALIZA LA UI LLAMANDO AL METODO FILLCARDVIEWS QUE SE ENCARGA DE SINCRONIZAR DATOS
        fillCardViews();

        //SE ESTABLECE EL COMPORTAMIENTO DEL FAB
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    Intent i = new Intent(getApplicationContext(), TurnViewerActivity.class);
            //    i.putExtra("VEHICULE_CODE", "5555");
            //    v.getContext().startActivity(i); //To start an activity we need a context
            //}
        //}
        //);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);

        //Iconos blancos hasta resolcer el tema del theme
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
            }
        }


        return super.onCreateOptionsMenu(menu);
    }

    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    //LLAMADA AL SERVIDOR PARA SINCRONIZAR DATOS CON UI
    private void fillCardViews() {
        RequestCompanyVehicles request = new RequestCompanyVehicles(this, findViewById(android.R.id.content));
        request.execute();
    }
}
