package com.introtoandroid.samplematerial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VehiculeBasicInformationMaterialAdapter extends RecyclerView.Adapter<VehiculeBasicInformationMaterialAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "SampleMaterialAdapter";

    public Context context;
    public ArrayList<CardBTKVehiculeBasicInfo> cardsList;

    public VehiculeBasicInformationMaterialAdapter(Context context, ArrayList<CardBTKVehiculeBasicInfo> cardsList) {
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
        //las siguientes dos lineas sobran
        //String name = cardsList.get(position).getName();
        //int color = cardsList.get(position).getColorResource();

        //Se obtienen los datos del elemento position de la CardList
        String vehiculeCode = cardsList.get(position).getVehiculeCode();
        String vehiculeName = cardsList.get(position).getVehiculeName();
        String vehiculeRegNumber = cardsList.get(position).getVehiculeRegNumber();
        String vehiculeType = cardsList.get(position).getVehiculeType();
        int color_resource = cardsList.get(position).getColorResource();


        //Aquí ha surgido un problema y se ha tenido que cambiar tv_code y tv_name
        TextView codeTextView = viewHolder.tv_vcode;
        TextView nameTextView = viewHolder.tv_vname;
        TextView regNumberTextView = viewHolder.tv_vregname;
        TextView typeTextView = viewHolder.tv_vtype;

        nameTextView.setText(vehiculeName);
        codeTextView.setText(vehiculeCode);
        regNumberTextView.setText(vehiculeRegNumber);
        typeTextView.setText(vehiculeType);
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

                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit adapter position " + list_position);
                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                view.setVisibility(View.INVISIBLE);
                cardsList.remove(list_position);
                notifyItemRemoved(list_position);
            }
        });
        animation.start();
    }

    //Se implementa el método añadir. En principio no es necesario para la aplicación
    public void addCard(String vehiculeCode, String vehiculeName, String vehiculeRegNumber,
                        String vehiculeType, int color_resource) {

        CardBTKVehiculeBasicInfo card = new CardBTKVehiculeBasicInfo();

        card.setVehiculeCode(vehiculeCode);
        card.setVehiculeName(vehiculeName);
        card.setVehiculeRegNumber(vehiculeRegNumber);
        card.setVehiculeType(vehiculeType);
        card.setColorResource(color_resource);
        card.setId(getItemCount());

        cardsList.add(card);

        ((Main) context).doSmoothScroll(getItemCount());
        notifyItemInserted(getItemCount());
    }

    //Método para actualizar las tarjetas
    public void updateCard(String name, int list_position) {
        cardsList.get(list_position).setVehiculeName(name);
        Log.d(DEBUG_TAG, "list_position is " + list_position);
        notifyItemChanged(list_position);
    }

    //Se implementa el método borrar. En principio no es necesario para la aplicación
    public void deleteCard(View view, int list_position) {
        animateCircularDelete(view, list_position);
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
        View v = li.inflate(R.layout.cvh_vehicule_basic_info, viewGroup, false);
        return new ViewHolder(v);
    }

    //En esta clase ya se comienzan a rellenar los elementos de la tarjeta
    public class ViewHolder extends RecyclerView.ViewHolder {

        //Tenemos que declarar todos los elementos que habrán en el layout que vamos a utilizar
        private TextView tv_vcode;
        private TextView tv_vname;
        private TextView tv_vregname;
        private TextView tv_vtype;


        public ViewHolder(View v) {
            super(v);

            //Asociamos a las variables declaradas los recursos layout de la vista correspondiente
            tv_vcode = (TextView) v.findViewById(R.id.tv_vcode);
            tv_vname = (TextView) v.findViewById(R.id.tv_vname);
            tv_vregname = (TextView) v.findViewById(R.id.tv_vregname);
            tv_vtype = (TextView) v.findViewById(R.id.tv_vtype);

            //deleteButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                 //   animateCircularDelete(itemView, getAdapterPosition());
                //}
            //});

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Averiguar qué es esto
                    Pair<View, String> p11 = Pair.create((View) tv_vcode, Main.TRANSITION_INITIAL);
                    Pair<View, String> p12 = Pair.create((View) tv_vname, Main.TRANSITION_NAME);
                    Pair<View, String> p13 = Pair.create((View) tv_vregname, Main.TRANSITION_DELETE_BUTTON);

                    ActivityOptionsCompat options;
                    Activity act = (AppCompatActivity) context;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p11, p12, p13);

                    int requestCode = getAdapterPosition();
                    String name = cardsList.get(requestCode).getVehiculeName();
                    int color = cardsList.get(requestCode).getColorResource();

                    Log.d(DEBUG_TAG, "SampleMaterialAdapter itemView listener for Edit adapter position " + requestCode);

                    Intent transitionIntent = new Intent(context, TransitionEditActivity.class);
                    transitionIntent.putExtra(Main.EXTRA_NAME, name);
                    transitionIntent.putExtra(Main.EXTRA_INITIAL, Character.toString(name.charAt(0)));
                    transitionIntent.putExtra(Main.EXTRA_COLOR, color);
                    transitionIntent.putExtra(Main.EXTRA_UPDATE, false);
                    transitionIntent.putExtra(Main.EXTRA_DELETE, false);
                    ((AppCompatActivity) context).startActivityForResult(transitionIntent, requestCode, options.toBundle());
                }
            });
        }
    }
}
