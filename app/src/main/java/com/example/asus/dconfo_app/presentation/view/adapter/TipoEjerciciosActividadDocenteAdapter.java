package com.example.asus.dconfo_app.presentation.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;

import java.util.List;


/**
 * Created by Andrés Cabal on 20/12/2018.
 */

public class TipoEjerciciosActividadDocenteAdapter extends RecyclerView.Adapter<TipoEjerciciosActividadDocenteAdapter.EjerciciosHolder> implements View.OnClickListener {

    List<EjercicioG2> listaEjerciciosG2;
    private View.OnClickListener listener;

    public TipoEjerciciosActividadDocenteAdapter(List<EjercicioG2> listaEjerciciosG2) {
        this.listaEjerciciosG2 = listaEjerciciosG2;
    }


    @NonNull
    @Override
    public EjerciciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio_actividad, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new EjerciciosHolder(vista);
    }

    @Override
    public void onBindViewHolder(EjerciciosHolder holder, int position) {
        // holder.txtidGrupo.setText(listaGrupos.get(position).getIdGrupo().toString());
        // Log.i("size", "lista_: " + listaGrupos.size());
        if (listaEjerciciosG2.size() != 0) {
            holder.txtidEjercicio.setText(listaEjerciciosG2.get(position).getIdEjercicioG2().toString());
            holder.txtnameEjercicio.setText(listaEjerciciosG2.get(position).getNameEjercicioG2().toString());
        } else {

        }

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return listaEjerciciosG2.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class EjerciciosHolder extends RecyclerView.ViewHolder {
        TextView txtidEjercicio, txtnameEjercicio;
        CheckBox cb_elegido;

        public EjerciciosHolder(View itemView) {
            super(itemView);
            txtidEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioId_home);
            txtnameEjercicio = (TextView) itemView.findViewById(R.id.txt_ejercicioNombre_home);
            cb_elegido = (CheckBox) itemView.findViewById(R.id.checkBox_item_ejercicio_act);

        }
    }
}
