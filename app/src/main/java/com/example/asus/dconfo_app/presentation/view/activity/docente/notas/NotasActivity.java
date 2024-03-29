package com.example.asus.dconfo_app.presentation.view.activity.docente.notas;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.DeberEstudiante;
import com.example.asus.dconfo_app.domain.model.Grupo_Estudiantes;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.Grupos_Estudiante_DocenteAdapter;
import com.example.asus.dconfo_app.presentation.view.adapter.NotasDeberesEstudianteAdapter;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.FindNotasXEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.FindNotasXGrupoEstFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.notas.ShowNotasGrupoEstudianteFragment;
import com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments.HomeTiposFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotasActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener,
        FindNotasXEstudianteFragment.OnFragmentInteractionListener,
        FindNotasXGrupoEstFragment.OnFragmentInteractionListener,
        ShowNotasGrupoEstudianteFragment.OnFragmentInteractionListener {

    public int iddocente = 0;
    public String id_estudiante;
    String flag;
    Integer idgrupo;
    RecyclerView rv_docente_notas;
    ProgressDialog progreso;
    FloatingActionButton fb_;
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<DeberEstudiante> listaDeberes_full;
    ArrayList<Integer> lista_idEstudiante;
    private BottomBar bottomBar;
    FindNotasXEstudianteFragment fnXest;
    FindNotasXGrupoEstFragment fnXgrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        Bundle datos = this.getIntent().getExtras();

        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");

        rv_docente_notas = findViewById(R.id.rv_docente_notas);
        rv_docente_notas.setLayoutManager(new LinearLayoutManager(this));
        rv_docente_notas.setHasFixedSize(true);

        listaDeberes_full = new ArrayList<>();
        lista_idEstudiante = new ArrayList<>();

        progreso = new ProgressDialog(this);
        flag = "1";
        cargarWebService();

        showToolbar("Notas Estudiantes" + " - " + iddocente, true);
        bottomBar = findViewById(R.id.bottombar_notas);
        cargarBottombar();

    }

    private void cargarBottombar() {

        bottomBar.setDefaultTab(R.id.btn_home_notas);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.btn_home_notas:


                        rv_docente_notas.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Notas Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.btn_estudiante:
                        Toast.makeText(getApplicationContext(), "Notas X Estudiante", Toast.LENGTH_LONG).show();
                        rv_docente_notas.setVisibility(View.GONE);

                        Bundle args1 = new Bundle();

                        args1.putInt("iddocente", iddocente);
                        args1.putInt("idgrupo", idgrupo);

                        fnXest = new FindNotasXEstudianteFragment();
                        fnXest.setArguments(args1);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, fnXest)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                    case R.id.btn_grupos:
                        Toast.makeText(getApplicationContext(), "Notas X Estudiante", Toast.LENGTH_LONG).show();
                        rv_docente_notas.setVisibility(View.GONE);

                        Bundle args2 = new Bundle();

                        args2.putInt("iddocente", iddocente);
                        args2.putInt("idgrupo", idgrupo);

                        fnXgrupo = new FindNotasXGrupoEstFragment();
                        fnXgrupo.setArguments(args2);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_contenedor_notas, fnXgrupo)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                }

            }
        });
    }

    private void cargarWebService() {

        progreso.setMessage("Cargando...");
        progreso.show();
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        if (flag.equals("1")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5wsJSONConsultarListaDeberesEst_nota.php?estudiante_idestudiante=";

            url = url.replace(" ", "%20");
            //hace el llamado a la url
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            final int MY_DEFAULT_TIMEOUT = 15000;
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        } else if (flag.equals("2")) {

            String url = "http://" + url_lh + "/proyecto_dconfo_v1/8_5wsJSONConsultarListaDeberesEst_nota.php?estudiante_idestudiante=" + id_estudiante;

            url = url.replace(" ", "%20");
            //hace el llamado a la url
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

            final int MY_DEFAULT_TIMEOUT = 15000;
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
            //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();}
        }//flag="2"


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        if (flag.equals("1")) {
            //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
            DeberEstudiante deberEstudiante = null;
            JSONArray json = response.optJSONArray("deber_nota");


            try {
                for (int i = 0; i < json.length(); i++) {
                    deberEstudiante = new DeberEstudiante();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    // jsonObject = new JSONObject(response);
                    deberEstudiante.setIdEjercicio2(jsonObject.optInt("EjercicioG2_idEjercicioG2"));
                    deberEstudiante.setIdEstudiante(jsonObject.optInt("estudiante_idestudiante"));
                    deberEstudiante.setFechaDeber(jsonObject.optString("fechaestudiante_has_Deber"));
                    deberEstudiante.setTipoDeber(jsonObject.optString("tipoDeber"));
                    deberEstudiante.setIdDocente(jsonObject.optInt("docente_iddocente"));
                    deberEstudiante.setIdCalificacion(jsonObject.optInt("calificacionestudiante_has_Deber"));
                    deberEstudiante.setIdEstHasDeber(jsonObject.optInt("id_estudiante_has_Debercol"));
                    deberEstudiante.setIdAsignacion(jsonObject.optInt("Asignacion_idGrupoAsignacion"));
                    deberEstudiante.setIdGrupoHdeber(jsonObject.optInt("grupo_estudiante_has_deber_id_GE_H_D"));
                    listaDeberes_full.add(deberEstudiante);
                    lista_idEstudiante.add(deberEstudiante.getIdEstudiante());

                }
                //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
                // Log.i("size", "lista: " + listaGrupos.size());
                NotasDeberesEstudianteAdapter notasDeberesEstudianteAdapter = new NotasDeberesEstudianteAdapter(listaDeberes_full);

                notasDeberesEstudianteAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
                rv_docente_notas.setAdapter(notasDeberesEstudianteAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", response.toString());

                Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

                progreso.hide();
            }
           // System.out.println("Lista id estudiante: " + lista_idEstudiante.toString());
            for (int i = 0; i < listaDeberes_full.size(); i++) {
                System.out.println("Lista listaDeberes_full show: i=" + (i + 1) + " - asignación - " + listaDeberes_full.get(i).getIdAsignacion());
                System.out.println("Lista listaDeberes_full show: i=" + (i + 1) + " - GrupoHdeber - " + listaDeberes_full.get(i).getIdGrupoHdeber());
            }
        }//flag="1"


    }//********************************************

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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
