package com.example.asus.dconfo_app.presentation.view.activity.docente.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.TipoEjerciciosActividadDocenteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActividadDocenteActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

    private LinearLayout ll_rv_ejercicios;
    private Button btn_fon;
    private Button btn_lex;
    private Button btn_sil;
    private Button btn_crear_actividad;
    private EditText edt_name_actividad;
    private CheckBox cb_agregar_ejercicio;
    private RecyclerView rv_ejercicios_act;
    private CardView cv_item_ejecicio;

    private int iddocente;
    private int idgrupo;
    private int idactividad;

    View view;

    EjercicioG2 ejercicioG2 = null;

    ArrayList<EjercicioG2> listaEjercicios;
    ArrayList<Integer> listaIdEjercicios;

    ProgressDialog progreso;
    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_docente);

        progreso = new ProgressDialog(this);

        Intent intent = this.getIntent();
        Bundle datos = intent.getExtras();
        iddocente = datos.getInt("iddocente");
        idgrupo = datos.getInt("idgrupo");
        showToolbar("Mis Actividades" + " - " + iddocente, true);

        view=findViewById(android.R.id.content);

        listaIdEjercicios = new ArrayList<>();

        ll_rv_ejercicios = (LinearLayout) findViewById(R.id.ll_docente_act_rv);

        btn_fon = (Button) findViewById(R.id.btn_docentente_act_fon);
        btn_fon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 1;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);

            }
        });
        btn_lex = (Button) findViewById(R.id.btn_docentente_act_lex);
        btn_lex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 2;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
            }
        });
        btn_sil = (Button) findViewById(R.id.btn_docentente_act_sil);
        btn_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaEjercicios = new ArrayList<>();
                idactividad = 3;
                cargarEjerciciosPorActividad();
                ll_rv_ejercicios.setVisibility(View.VISIBLE);
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

        cb_agregar_ejercicio = (CheckBox) findViewById(R.id.checkBox_item_ejercicio_act);
        cv_item_ejecicio = (CardView) findViewById(R.id.cv_item_ejercicio);
//        cv_item_ejecicio.getContext();
        //cb_agregar_ejercicio.setOnCheckedChangeListener();

        rv_ejercicios_act = (RecyclerView) findViewById(R.id.rv_docente_actividad_ejercicios);
        rv_ejercicios_act.setLayoutManager(new GridLayoutManager(this, 2));
        rv_ejercicios_act.setHasFixedSize(true);


    }

    private void cargarEjerciciosPorActividad() {

        progreso.setMessage("Cargando...");
        progreso.show();

        String iddoc = "20181";
        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/20wsJSONConsultarListaEjerciciosXactividad.php?iddocente=" + iddocente + "& idactividad=" + idactividad;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);//p21
        // Toast.makeText(getContext(), "LISTA EJERCICIOS DOC.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        // Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        // progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();

        JSONArray json = response.optJSONArray("ejerciciog2");

        try {
            for (int i = 0; i < json.length(); i++) {
                ejercicioG2 = new EjercicioG2();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));

                listaEjercicios.add(ejercicioG2);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }
            final TipoEjerciciosActividadDocenteAdapter tipoEjerciciosActividadDocenteAdapter = new TipoEjerciciosActividadDocenteAdapter(listaEjercicios,view);
            tipoEjerciciosActividadDocenteAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (cb_agregar_ejercicio.isChecked()==true) {
                        listaIdEjercicios.add(ejercicioG2.getIdEjercicioG2());
                        System.out.println("lista id ejercicio: "+listaIdEjercicios);
                        //System.out.println("CB: "+cb_agregar_ejercicio.isChecked());
                    }*/
                    int idejercicio = listaEjercicios.get(rv_ejercicios_act.
                            getChildAdapterPosition(v)).getIdEjercicioG2();
                    listaIdEjercicios.add(idejercicio);
                    int c=getResources().getColor(R.color.green);
                   // cv_item_ejecicio.setCardBackgroundColor(c);
                    System.out.println("lista id ejercicio: "+listaIdEjercicios);

                }
            });
            System.out.println("lista ejercicios: " + listaEjercicios.size() + " idactividad: " + idactividad);
            rv_ejercicios_act.setAdapter(tipoEjerciciosActividadDocenteAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            //Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            progreso.hide();
        }

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
