package com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.introtoandroid.samplematerial.Activities.ServicesViewerActivity;
import com.introtoandroid.samplematerial.CompanyData.Service;
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.R;

import java.util.ArrayList;

public class TurnServiceInformationAdapter extends RecyclerView.Adapter<TurnServiceInformationAdapter.ViewHolder> {

    public Context context;
    private ArrayList<CardBTKTurnServiceInfo> cardsList;

    public TurnServiceInformationAdapter(Context context, ArrayList<CardBTKTurnServiceInfo> cardsList) {
        this.context = context;
        this.cardsList = cardsList;
    }

    /**
     *Este método lo que hace es enlazar el conjunto de datos a las views que se encuentran en el layout card_view_holder
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //Se obtienen los datos del elemento position de la CardList
        Long id = cardsList.get(position).getId();
        String subLine = cardsList.get(position).getSubLine();
        String serviceCode = cardsList.get(position).getServiceCode();
        String startTime = cardsList.get(position).getStartTime();
        String finishTime = cardsList.get(position).getFinishTime();

        //Aquí ha surgido un problema y se ha tenido que cambiar tv_code y tv_name
        TextView sServiceSubLine = viewHolder.tv_sServiceSubLine_content;
        TextView sServiceCode = viewHolder.tv_sServiceCode_content;
        TextView sServiceStartTime = viewHolder.tv_sStart_content;
        TextView sServiceFinishTime = viewHolder.tv_sFinish_content;

        sServiceSubLine.setText(subLine);
        sServiceCode.setText(serviceCode);
        sServiceStartTime.setText(startTime);
        sServiceFinishTime.setText(finishTime);

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
        //cardsList.get(list_position).setVehiculeName(name);
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
        View v = li.inflate(R.layout.cvh_vehicule_service_info, viewGroup, false);
        return new ViewHolder(v);
    }

    //En esta clase ya se comienzan a rellenar los elementos de la tarjeta
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Tenemos que declarar todos los elementos que habrán en el layout que vamos a utilizar
        private TextView tv_sServiceSubLine_content;
        private TextView tv_sServiceCode_content;
        private TextView tv_sStart_content;
        private TextView tv_sFinish_content;

        public ViewHolder(View v) {
            super(v);

            //Asociamos a las variables declaradas los recursos layout de la vista correspondiente
            tv_sServiceSubLine_content = (TextView) v.findViewById(R.id.tv_sServiceSubLine_content);
            tv_sServiceCode_content = (TextView) v.findViewById(R.id.tv_sServiceCode_content);
            tv_sStart_content = (TextView) v.findViewById(R.id.tv_sStart_content);
            tv_sFinish_content = (TextView) v.findViewById(R.id.tv_sFinish_content);

            //deleteButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                 //   animateCircularDelete(itemView, getAdapterPosition());
                //}
            //});

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    cardsList.get(requestCode).getSubLine();
                    Log.d("TEST: ", cardsList.get(requestCode).getSubLine());
                    //printTurnLogInfo(cardsList.get(requestCode).getTurn());

                }
            });
        }
    }
}
