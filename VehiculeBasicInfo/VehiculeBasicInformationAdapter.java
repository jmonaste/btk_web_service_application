package com.introtoandroid.samplematerial.VehiculeBasicInfo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.introtoandroid.samplematerial.Activities.ServicesViewerActivity;
import com.introtoandroid.samplematerial.Activities.TurnViewerActivity;
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.Main;
import com.introtoandroid.samplematerial.R;

import java.util.ArrayList;

import static com.introtoandroid.samplematerial.VehiculeTurnInfo.VehiculeTurnInformationAdapter.EXTRA_CUSTOM_TURN;

public class VehiculeBasicInformationAdapter extends RecyclerView.Adapter<VehiculeBasicInformationAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "SampleMaterialAdapter";
    private static final String VEHICULE_CODE_STRING = "vcode";
    private static final String VEHICULE_NAME_STRING = "vname";
    private static final String VEHICULE_REGNUM_STRING = "vregum";
    private static final String VEHICULE_COMPANYNAME_STRING = "vcompname";
    private static final String VEHICULE_TYPE_STRING = "vtype";

    public Context context;
    public ArrayList<CardBTKVehiculeBasicInfo> cardsList;

    public VehiculeBasicInformationAdapter(Context context, ArrayList<CardBTKVehiculeBasicInfo> cardsList) {
        this.context = context;
        this.cardsList = cardsList;
    }

    /**
     *Este método lo que hace es enlazar el conjunto de datos a las views que se encuentran en el layout card_view_holder
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //SE OBTIENEN LOS DATOS DE LA CARD LIST DE LA POSICION ACTUAL
        String vehiculeCode = cardsList.get(position).getVehiculeCode();
        String vehiculeName = cardsList.get(position).getVehiculeName();
        String vehiculeRegNumber = cardsList.get(position).getVehiculeRegNumber();

        //SE OBTIENEN LAS REFERENCIAS A LOS TEXT VIEWS PARA PODER RELLENARLOS
        //TextView codeTextView = viewHolder.tv_vcode;
        TextView nameTextView = viewHolder.tv_vname;
        TextView regNumberTextView = viewHolder.tv_vregname;
        TextView codeMain = viewHolder.tv_vcode_main;

        //SE RELLENAN LOS TEXT VIEWS
        nameTextView.setText(vehiculeName);
        //codeTextView.setText(vehiculeCode);
        regNumberTextView.setText(vehiculeRegNumber);
        codeMain.setText(vehiculeCode);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    //No @Override?
    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    @Override
    public int getItemCount() {
        if (cardsList.isEmpty()) {
            return 0;
        } else {
            return cardsList.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return cardsList.get(position).getId();
    }

    //METODO QUE RELLENA EL LAYOUT CARDVIEWHOLDER
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.cvh_vehicule_basic_info_copy, viewGroup, false);
        return new ViewHolder(v);
    }

    //CLASE QUE YA COMIENZA A RELLENAR LOS ELEMENTOS DE LA TARJETA
    public class ViewHolder extends RecyclerView.ViewHolder {

        //SE DECLARAN TODOS LOS ELEMENTOS DEL LAYOUT QUE VAMOS A UTILIZAR
        private TextView tv_vcode_main;
        private TextView tv_vcode;
        private TextView tv_vname;
        private TextView tv_vregname;

        public ViewHolder(View v) {
            super(v);

            //SE ASOCIAN A LAS VARIABLES DECLARADAS LOS RECURSOS LAYOUT DE LA VIEW CORRESPONDIENTE
            tv_vcode_main = (TextView) v.findViewById(R.id.tv_scode_title);
            tv_vname = (TextView) v.findViewById(R.id.tv_vname_content);
            //tv_vcode = (TextView) v.findViewById(R.id.tv_vcode_title_2);
            tv_vregname = (TextView) v.findViewById(R.id.tv_tvregnum_title);


            //LISTENER PARA EL CLICK EN CADA CARDVIEW DE LOS VEHICULOS
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    String codigoVehichulo = cardsList.get(requestCode).getVehiculeCode();
                    String vehiculeName = cardsList.get(requestCode).getVehiculeName();
                    String vehiculeRegNumber = cardsList.get(requestCode).getVehiculeRegNumber();
                    String vehiculeCompanyName = cardsList.get(requestCode).getCompanyName();
                    String vehiculeType = cardsList.get(requestCode).getVehiculeType();

                    Intent i = new Intent(context, TurnViewerActivity.class);
                    i.putExtra(VEHICULE_CODE_STRING, codigoVehichulo);
                    i.putExtra(VEHICULE_NAME_STRING, vehiculeName);
                    i.putExtra(VEHICULE_REGNUM_STRING, vehiculeRegNumber);
                    i.putExtra(VEHICULE_COMPANYNAME_STRING, vehiculeCompanyName);
                    i.putExtra(VEHICULE_TYPE_STRING, vehiculeType);
                    //Podriamos pasar el nombre y demás para mostrarlo en la siguiente actividad
                    v.getContext().startActivity(i); //To start an activity we need a context
                }
            });
        }
    }
}
