package com.example.asus.dconfo_app.presentation.view.activity.docente.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.asus.dconfo_app.R;

public class ActividadDocenteActivity extends AppCompatActivity {

    private LinearLayout ll_rv_ejercicios;
    private Button btn_fon;
    private Button btn_lex;
    private Button btn_sil;
    private Button btn_crear_actividad;
    private EditText edt_name_actividad;
    private RecyclerView rv_ejercicios_act;

    private int iddocente;
    private int idgrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_docente);

        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        showToolbar("Mis Actividades" + " - " + iddocente, true);

        ll_rv_ejercicios = (LinearLayout) findViewById(R.id.ll_docente_act_rv);

        btn_fon = (Button) findViewById(R.id.btn_docentente_act_fon);
        btn_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_lex = (Button) findViewById(R.id.btn_docentente_act_lex);
        btn_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_sil = (Button) findViewById(R.id.btn_docentente_act_sil);
        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_crear_actividad = (Button) findViewById(R.id.btn_docente_actividad_crear);
        btn_crear_actividad.setEnabled(false);
        btn_crear_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edt_name_actividad = (EditText) findViewById(R.id.edt_docente_actividad_name);

        rv_ejercicios_act = (RecyclerView) findViewById(R.id.rv_docente_actividad_ejercicios);
        rv_ejercicios_act.setLayoutManager(new GridLayoutManager(this, 3));
        rv_ejercicios_act.setHasFixedSize(true);

    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
