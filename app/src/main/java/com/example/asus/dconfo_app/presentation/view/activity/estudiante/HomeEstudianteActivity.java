package com.example.asus.dconfo_app.presentation.view.activity.estudiante;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.dconfo_app.R;


import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.CasaHomeEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.InicioEjercicioFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo1EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.Tipo2EstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo1FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico.Tipo2FonicoFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo1silabicoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico.Tipo2silabicoEstudianteFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeEstudianteActivity extends AppCompatActivity implements
        CasaHomeEstudianteFragment.OnFragmentInteractionListener,
        Tipo1EstudianteFragment.OnFragmentInteractionListener,
        Tipo2EstudianteFragment.OnFragmentInteractionListener,
        InicioEjercicioFragment.OnFragmentInteractionListener,
        Tipo1FonicoFragment.OnFragmentInteractionListener,
        Tipo2FonicoFragment.OnFragmentInteractionListener,
        Tipo1silabicoEstudianteFragment.OnFragmentInteractionListener,
        Tipo2silabicoEstudianteFragment.OnFragmentInteractionListener {


    private BottomBar bottomBar;
    CasaHomeEstudianteFragment homeEstudianteFragment;
    Tipo1EstudianteFragment tipo1EstudianteFragment;
    Tipo2EstudianteFragment tipo2EstudianteFragment;
    String nameestudiante;
    int idestudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_estudiante);
        bottomBar = findViewById(R.id.bottombar_estudiante);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        nameestudiante = extra.getString("nameestudiante");
        idestudiante = extra.getInt("idestudiante");
        showToolbar("Est: " + nameestudiante + " ,id: " + idestudiante, true);
        cargarBottombar();
    }

    //método que permite volver al padre conservando las variables
   /* @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }*/

    private void cargarBottombar() {

        bottomBar.setDefaultTab(R.id.bot_deber_home_deberes);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.bot_deber_home_deberes:
                        Bundle bundle = new Bundle();
                        bundle.putInt("idEstudiante", idestudiante);
                        bundle.putString("nameEstudiante", nameestudiante);

                        homeEstudianteFragment = new CasaHomeEstudianteFragment();
                        homeEstudianteFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, homeEstudianteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        //Toast.makeText(getApplicationContext(), "Ejercicios Home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.bot_deber_t1:

                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("idEstudiante", idestudiante);
                        bundle1.putString("nameEstudiante", nameestudiante);

                        tipo1EstudianteFragment = new Tipo1EstudianteFragment();
                        tipo1EstudianteFragment.setArguments(bundle1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo1EstudianteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                    case R.id.bot_deber_t2:

                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("idEstudiante", idestudiante);
                        bundle2.putString("nameEstudiante", nameestudiante);

                        tipo2EstudianteFragment = new Tipo2EstudianteFragment();
                        tipo2EstudianteFragment.setArguments(bundle2);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, tipo2EstudianteFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                }
            }
        });
    }

    public void showToolbar(String tittle, boolean upButton) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_ejercicio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        //getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
