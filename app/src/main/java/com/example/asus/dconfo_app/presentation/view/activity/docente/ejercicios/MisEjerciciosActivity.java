package com.example.asus.dconfo_app.presentation.view.activity.docente.ejercicios;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;

import java.util.ArrayList;

public class MisEjerciciosActivity extends AppCompatActivity {

    private int idgrupo;
    private int iddocente;

    private LinearLayout ll_rv_ejercicios;
    private Button btn_fon;
    private Button btn_lex;
    private Button btn_sil;
    private Button btn_crear_actividad;
    private EditText edt_name_actividad;
    private RecyclerView rv_ejercicios_act;

    private EjercicioG2 ejercicioG2 = null;

    private ArrayList<EjercicioG2> listaEjercicios;

    private ProgressDialog progreso;
    private FloatingActionButton fb_nuevo_GrupoEstudiantes;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_ejercicios);

        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        showToolbar("Mis Ejercicios" + " - " + iddocente, true);

        rv_ejercicios_act = (RecyclerView) findViewById(R.id.rv_docente_mis_ejercicios);

        btn_fon = (Button) findViewById(R.id.btn_docentente_ejer_fon);
        btn_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btn_lex = (Button) findViewById(R.id.btn_docentente_ejer_lex);
        btn_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_sil = (Button) findViewById(R.id.btn_docentente_ejer_sil);
        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }//*********************************************************************************************

    //método que permite volver al padre conservando las variables
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
