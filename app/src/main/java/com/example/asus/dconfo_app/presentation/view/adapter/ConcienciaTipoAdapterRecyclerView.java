package com.example.asus.dconfo_app.presentation.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.ConcienciaTipo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Andrés Cabal on 29/07/2018.
 */

public class ConcienciaTipoAdapterRecyclerView extends RecyclerView.Adapter<ConcienciaTipoAdapterRecyclerView.ConcienciaViewHolder> {

    private ArrayList<ConcienciaTipo> listaConcienciaTipo;
    private int resuource;
    private Activity activity;

    public ConcienciaTipoAdapterRecyclerView(ArrayList<ConcienciaTipo> listaConcienciaTipo) {
        this.listaConcienciaTipo = listaConcienciaTipo;

    }

    @NonNull
    @Override
    public ConcienciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conciencia, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ConcienciaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ConcienciaViewHolder holder, int position) {
        ConcienciaTipo concienciaTipo = listaConcienciaTipo.get(position);
        holder.titulo.setText(concienciaTipo.getTitleConciencia());
        holder.description.setText(concienciaTipo.getDescription());
        if (holder.titulo.equals("Conciencia Fónica")) {
            Drawable drawable = holder.iv_conciencia.getResources().getDrawable(R.drawable.ic_conciencia_fonica);
            holder.iv_conciencia.setBackground(drawable);
        } else if (holder.titulo.equals("Conciencia Sílabica")) {
            Drawable drawable = holder.iv_conciencia.getResources().getDrawable(R.drawable.ic_conciencia_silabica);
            holder.iv_conciencia.setBackground(drawable);
        } else if (holder.titulo.equals("Conciencia Léxica")) {
            Drawable drawable = holder.iv_conciencia.getResources().getDrawable(R.drawable.ic_conciencia_lexica);
            holder.iv_conciencia.setBackground(drawable);
        }

    }

    @Override
    public int getItemCount() {
        return listaConcienciaTipo.size();
    }

    public class ConcienciaViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_conciencia;
        private TextView titulo;
        private TextView description;


        public ConcienciaViewHolder(View itemView) {
            super(itemView);
            iv_conciencia = (ImageView) itemView.findViewById(R.id.iv_conciencia_imagen);
            titulo = (TextView) itemView.findViewById(R.id.txt_conciencia_titulo);
            description = (TextView) itemView.findViewById(R.id.txt_conciencia_description);

        }
    }


}
