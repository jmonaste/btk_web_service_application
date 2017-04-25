package com.introtoandroid.samplematerial.Activities;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.DataProccessing.TextProccessing;
import com.introtoandroid.samplematerial.R;
import com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo.CardBTKTurnServiceInfo;
import com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo.TurnServiceInformationAdapter;

import java.util.ArrayList;


public class ServicesViewerActivity extends AppCompatActivity {

    /*DEFINICION DE CONSTANTES PARA USO DE LA APLICACIÓN*/
    public static final String EXTRA_CUSTOM_TURN = "extra_custom_turn";

    private RecyclerView recyclerView;
    private TurnServiceInformationAdapter adapter;
    private ArrayList<CardBTKTurnServiceInfo> cardsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_viewer);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.primary)));

        //Actualizamos UI
        fillCardViews();

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        //Actualizamos las CardViewa simplemente
        //        fillCardViews();

         //   }
        //});
    }

    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    //Aquí está el método para iniciar las CardViews.
    private void fillCardViews() {
        //Obtenemos el turno
        Turn turn = new Turn();
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            turn = bundle.getParcelable(EXTRA_CUSTOM_TURN);
        }



        //UI--------------->
        //Cambiar de actividad pasando la lista como parámetro
        Context rootContext = this;
        View rootView = findViewById(android.R.id.content);

        ArrayList<CardBTKTurnServiceInfo> cardsList = new ArrayList<>();
        TurnServiceInformationAdapter adapter = null;
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        //Aqui rellenar las tarjetas
        for (int i = 0; i < turn.getTurnServices().size(); i++) {

            CardBTKTurnServiceInfo card = new CardBTKTurnServiceInfo();

            card.setId((long) i);
            card.setServiceCode(turn.getTurnServices().get(i).getTurnService().getServiceCode());
            card.setSubLine(TextProccessing.allFirstLettersToUpperCase(turn.getTurnServices().get(i).getTurnService().getSubLine().getSubLineName()));
            card.setStartTime(turn.getTurnServices().get(i).getTurnService().getServiceStartTime());
            card.setFinishTime(turn.getTurnServices().get(i).getTurnService().getServiceEndTime());

            cardsList.add(card);
        }

        if (adapter == null) {
            adapter = new TurnServiceInformationAdapter(rootContext, cardsList);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootContext));
        //UI--------------->

    }
}
