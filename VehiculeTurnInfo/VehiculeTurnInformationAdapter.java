package com.introtoandroid.samplematerial.VehiculeTurnInfo;

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
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.R;

import java.util.ArrayList;

public class VehiculeTurnInformationAdapter extends RecyclerView.Adapter<VehiculeTurnInformationAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "VehiculeTurnInformationAdapter";
    public static final String EXTRA_CUSTOM_TURN = "extra_custom_turn";

    public Context context;
    private ArrayList<CardBTKVehiculeTurnInfo> cardsList;

    public VehiculeTurnInformationAdapter(Context context, ArrayList<CardBTKVehiculeTurnInfo> cardsList) {
        this.context = context;
        this.cardsList = cardsList;
    }

    /**
     *Este método lo que hace es enlazar el conjunto de datos a las views que se encuentran en el layout card_view_holder
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //Se obtienen los datos del elemento position de la CardList
        String Codigo_Turno = cardsList.get(position).getCodigo_Turno();
        String Descripcion_Turno =cardsList.get(position).getDescripcion_Turno();
        String Empresa_Turno = cardsList.get(position).getEmpresa_Turno();

        int turnServicesQuantity = cardsList.get(position).getTurn().getTurnServices().size();

        //Aquí ha surgido un problema y se ha tenido que cambiar tv_code y tv_name
        TextView turnCode = viewHolder.tv_tcode_content;
        TextView turnDescription = viewHolder.tv_tdesc_content;
        TextView turnCompany = viewHolder.tv_tcompany_content;
        TextView turnServicesNumber = viewHolder.tv_tservices_content;

        turnCode.setText(Codigo_Turno);
        turnDescription.setText(Descripcion_Turno);
        turnCompany.setText(Empresa_Turno);
        turnServicesNumber.setText(Integer.toString(turnServicesQuantity));
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

    //Para la animación en el scrolling de la lista
    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    //Animación para cuando eliminamos un elemento de la lista
    public void animateCircularDelete(final View view, final int list_position) {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int startRadius = view.getWidth();
        int endRadius = 0;
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        animation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                view.setVisibility(View.INVISIBLE);
                cardsList.remove(list_position);
                notifyItemRemoved(list_position);
            }
        });
        animation.start();
    }

    //Método para actualizar las tarjetas
    public void updateCard(String name, int list_position) {
        cardsList.get(list_position).setVehiculeName(name);
        notifyItemChanged(list_position);
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

    //Este método es que rellena el layout CardViewHolder, habrá que modificarlo
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.cvh_vehicule_turn_info, viewGroup, false);
        return new ViewHolder(v);
    }

    //En esta clase ya se comienzan a rellenar los elementos de la tarjeta
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Tenemos que declarar todos los elementos que habrán en el layout que vamos a utilizar
        private TextView tv_tcode_content;
        private TextView tv_tdesc_content;
        private TextView tv_tcompany_content;
        private TextView tv_tservices_content;

        public ViewHolder(View v) {
            super(v);

            //Asociamos a las variables declaradas los recursos layout de la vista correspondiente
            tv_tcode_content = (TextView) v.findViewById(R.id.tv_sServiceSubLine_content);
            tv_tdesc_content = (TextView) v.findViewById(R.id.tv_sStart_content);
            tv_tcompany_content = (TextView) v.findViewById(R.id.tv_sFinish_content);
            tv_tservices_content = (TextView) v.findViewById(R.id.tv_sServiceCode_content);

            //deleteButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                 //   animateCircularDelete(itemView, getAdapterPosition());
                //}
            //});

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //El problema aqui es obtener el codigo del vehiculo que se ha seleccionado hace un par de actividades

                    int requestCode = getAdapterPosition();
                    Turn turn = cardsList.get(requestCode).getTurn();
                    printTurnLogInfo(cardsList.get(requestCode).getTurn());

                    Intent intent = new Intent(context, ServicesViewerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EXTRA_CUSTOM_TURN, turn);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    private void printTurnLogInfo(Turn turn){
        Log.d("TurnTAG: ", "Has clickado en un turno");

        Integer numberOfServices = turn.getTurnServices().size();
        int j = 1;
        for(int i=0; i<numberOfServices; i++){
            Log.d("TurnTAG: ",  "Turno " + j + ". ID del servicio: " + Integer.toString(turn.getTurnServices().get(i).getTurnService().getServiceID()));
            j++;
        }

    }
}
