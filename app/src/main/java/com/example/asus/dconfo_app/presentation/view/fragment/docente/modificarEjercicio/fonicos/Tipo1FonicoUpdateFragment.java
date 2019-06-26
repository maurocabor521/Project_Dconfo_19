package com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasImagen;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.ImagenUrlAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo1FonicoUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1FonicoUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1FonicoUpdateFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edt_letra;

    private EditText edt_letraInicial_img1;
    private EditText edt_letraFinal_img1;
    private EditText edt_cantSilaba_img1;

    private EditText edt_letraInicial_img2;
    private EditText edt_letraFinal_img2;
    private EditText edt_cantSilaba_img2;

    private EditText edt_letraInicial_img3;
    private EditText edt_letraFinal_img3;
    private EditText edt_cantSilaba_img3;

    private EditText edt_letraInicial_img4;
    private EditText edt_letraFinal_img4;
    private EditText edt_cantSilaba_img4;

    private EditText edt_nameEjercicio;

    private Button btn_img1;
    private ImageView btn_img_1;
    private Button btn_img2;
    private ImageView btn_img_2;
    private Button btn_img3;
    private ImageView btn_img_3;
    private Button btn_img4;
    private ImageView btn_img_4;
    private Button btn_enviar;

    private Button btn_crearImg1;
    private Button btn_crearImg2;
    private Button btn_crearImg3;
    private Button btn_crearImg4;

    private boolean btn1Activo = false;
    private boolean btn_1Activo = false;
    private boolean btn2Activo = false;
    private boolean btn_2Activo = false;
    private boolean btn3Activo = false;
    private boolean btn_3Activo = false;
    private boolean btn4Activo = false;
    private boolean btn_4Activo = false;

    private TextView txt_id_img1;
    private TextView txt_id_img2;
    private TextView txt_id_img3;
    private TextView txt_id_img4;

    private RecyclerView rv_tipo1Fonico;
    private EjercicioG2HasImagen ejercicioG2HasImagen;

    ProgressDialog progreso;
    ImageView imgFoto;
    File fileImagen;
    Bitmap bitmap;

    String nameDocente = "";
    int idDocente = 0;
    int idEjercicio2;
    int idejercicio;
    int idgrupo;

    ArrayList<Imagen> listaImagenes;
    ArrayList<EjercicioG2> listaEjerciciosG2;
    ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<Integer> listaidImagenes;
    ArrayList<Integer> listafilaImagen;
    ArrayList<Integer> listacolumnaImagen;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    private OnFragmentInteractionListener mListener;

    public Tipo1FonicoUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1FonicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo1FonicoUpdateFragment newInstance(String param1, String param2) {
        Tipo1FonicoUpdateFragment fragment = new Tipo1FonicoUpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tipo1_fonico_update, container, false);

        nameDocente = getArguments().getString("namedocente");

        idDocente = getArguments().getInt("iddocente");
        idejercicio = getArguments().getInt("idejercicio");
        idgrupo = getArguments().getInt("idgrupo");

        ejercicioG2HasImagen = new EjercicioG2HasImagen();

        edt_letra = (EditText) view.findViewById(R.id.edt_fonico_update_tipo1_vocal);
        edt_letra.setFilters(new InputFilter[]
                {new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(1)}
        );

        edt_nameEjercicio = (EditText) view.findViewById(R.id.edt_fonico_update_nameEjercicio);


        txt_id_img1 = (TextView) view.findViewById(R.id.txt_fonico_id_img1);
        txt_id_img2 = (TextView) view.findViewById(R.id.txt_fonico_id_img2);
        txt_id_img3 = (TextView) view.findViewById(R.id.txt_fonico_id_img3);
        txt_id_img4 = (TextView) view.findViewById(R.id.txt_fonico_id_img4);

        btn_enviar = (Button) view.findViewById(R.id.btn_fonico_update_doc_enviar);


        btn_img_1 = (ImageView) view.findViewById(R.id.btn_fonico_update_doc_img_1);
        btn_img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_1Activo = true;
                //rv_tipo1Fonico.setVisibility(View.VISIBLE);
                mostrarDialogOpciones();
            }
        });
        btn_img_2 = (ImageView) view.findViewById(R.id.btn_fonico_update_doc_img_2);
        btn_img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_2Activo = true;
                //rv_tipo1Fonico.setVisibility(View.VISIBLE);
                mostrarDialogOpciones();
            }
        });

        btn_img_3 = (ImageView) view.findViewById(R.id.btn_fonico_update_doc_img_3);
        btn_img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_3Activo = true;
                //rv_tipo1Fonico.setVisibility(View.VISIBLE);
                mostrarDialogOpciones();
            }
        });
        btn_img_4 = (ImageView) view.findViewById(R.id.btn_fonico_update_doc_img_4);
        btn_img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_4Activo = true;
                //rv_tipo1Fonico.setVisibility(View.VISIBLE);
                mostrarDialogOpciones();
            }
        });


        listaImagenes = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listaEjercicios = new ArrayList<>();
        listaidImagenes = new ArrayList<>();
        listafilaImagen = new ArrayList<>();
        listacolumnaImagen = new ArrayList<>();


        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_2();
                try {
                    Thread.sleep(100);
                    System.out.println("Dormir... crea Ejercicio");
                } catch (InterruptedException e) {

                }
                listarEjerciciosG2Docente();
                try {
                    Thread.sleep(300);
                    System.out.println("Dormir... Lista ejercicios");
                } catch (InterruptedException e) {

                }

               /* for (int i = 0; i < listaidImagenes.size(); i++) {
                    webService_CrearEjercicioG2_Has_Imagen(listaidImagenes.get(i), listafilaImagen.get(i), listacolumnaImagen.get(i));
                    try {
                        Thread.sleep(100);
                        System.out.println("Dormir for... "+listaidImagenes.get(i));
                    } catch (InterruptedException e) {

                    }
                }*/

            }
        });

        consultarListaImagenes();

        rv_tipo1Fonico = (RecyclerView) view.findViewById(R.id.rv_tipo1Fonico_update);
        rv_tipo1Fonico.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_tipo1Fonico.setHasFixedSize(true);
        rv_tipo1Fonico.setVisibility(View.GONE);


        imgFoto = new ImageView(getContext());
        return view;
    }

    //----------------------------------------------------------------------------------------------
    //******************************WEB SERVICE
    //para iniciar el proceso de llamado al webservice
    private void cargarWebService_2() {
       /* progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/
        String ip = Globals.url;
        //String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONRegistroTipo1Fonico.php";//p12.buena
        String url = "http://" + ip + "/proyecto_dconfo_v1/1wsJSONCrearEjercicio.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    edt_letra.setText("");
                    edt_nameEjercicio.setText("");

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                //progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //String idEjercicio = "7";
                String nameEjercicio = edt_nameEjercicio.getText().toString();
                String docente_iddocente = String.valueOf(idDocente);
                String Tipo_idTipo = "1";
                String Actividad_idActividad = "1";
                String letra_inicial = edt_letra.getText().toString();
                String letra_final = "";

                String imagen = "";
                String cantidad = "";
                String oracion = "";

                System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);
                parametros.put("nameEjercicioG2", nameEjercicio);
                parametros.put("docente_iddocente", docente_iddocente);
                parametros.put("Tipo_idTipo", Tipo_idTipo);
                parametros.put("Tipo_Actividad_idActividad", Actividad_idActividad);
                parametros.put("letra_inicial_EjercicioG2", letra_inicial);
                parametros.put("letra_final_EjercicioG2", letra_final);

                parametros.put("imagen", imagen);
                parametros.put("cantidadValidadEjercicio", cantidad);
                parametros.put("oracion", oracion);

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21

    }

    //----------------------------------------------------------------------------------------------

    //******************************WEB SERVICE
    public void listarEjerciciosG2Docente() {
        // final String usuario = etUsuario.getText().toString();
        // final String password = etPass.getText().toString();

        // INICIAR LA CONEXION CON VOLLEY

        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?docente_iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjercicios_Fonico1_Docente.php?iddocente=" + idDocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/2wsJSONConsultarListaEjercicios.php?iddocente=" + idDocente;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjercicios_Fonico1_Docente.php";
        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSON1ConsultarListaEjercicios.php";


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response

                        EjercicioG2 ejercicioG2 = null;
                        Toast.makeText(getContext(), "listar ejercicios g2: ", Toast.LENGTH_LONG).show();

                        try {
                            JSONArray json = response.optJSONArray("ejerciciog2");
                            //JSONARRAY array=new JSONARRAY(response);
                            for (int i = 0; i < json.length(); i++) {
                                ejercicioG2 = new EjercicioG2();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                                ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                                ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                                ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));
                                ejercicioG2.setLetra_inicial_EjercicioG2(jsonObject.optString("letra_inicial_EjercicioG2"));
                                ejercicioG2.setLetra_final_EjercicioG2(jsonObject.optString("letra_final_EjercicioG2"));

                                listaEjerciciosG2.add(ejercicioG2);
                            }
                            idEjercicio2 = listaEjerciciosG2.get(listaEjerciciosG2.size() - 1).getIdEjercicioG2();
                            System.out.println("Último de lista EG2: " + idEjercicio2);

                            if (idEjercicio2 > 0) {

                                ejerciciog2HI_adjuntarImagenes();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR Ejercicios G2: ", error.toString());
                    }
                }
        );
        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }

    private void ejerciciog2HI_adjuntarImagenes() {

        for (int i = 0; i < listaidImagenes.size(); i++) {
            webService_CrearEjercicioG2_Has_Imagen(listaidImagenes.get(i), listafilaImagen.get(i), listacolumnaImagen.get(i));
            try {
                Thread.sleep(100);
                System.out.println("Dormir for... " + listaidImagenes.get(i));
            } catch (InterruptedException e) {

            }
        }
        edt_letra.setText("");
        edt_nameEjercicio.setText("");
        imgFoto.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        Drawable drawable = imgFoto.getDrawable();
        btn_img_1.setImageResource(R.drawable.ic_no_foto_80dp);
        btn_img_2.setImageResource(R.drawable.ic_no_foto_80dp);
        btn_img_3.setImageResource(R.drawable.ic_no_foto_80dp);
        btn_img_4.setImageResource(R.drawable.ic_no_foto_80dp);

        txt_id_img1.setText("imagen 1");
        txt_id_img2.setText("imagen 2");
        txt_id_img3.setText("imagen 3");
        txt_id_img4.setText("imagen 4");

        /*btn_img_1.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_2.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_3.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_4.setBackgroundResource(R.drawable.ic_no_foto_80dp);*/


    }
    // ----------------------------------------------------------------------------------------------

    //para iniciar el proceso de llamado al webservice

    private void webService_CrearEjercicioG2_Has_Imagen(final int id_Imagen, final int fila_imagen, final int columna_imagen) {
       /* progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONCrearEjercicio2HasImagen.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    //edt_letra.setText("");
                    // edt_nameEjercicio.setText("");

                    Toast.makeText(getContext(), "Se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                    System.out.println("error no cargado con exito crearEHI");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar EHI", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                //progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String EjercicioG2_idEjercicioG2 = String.valueOf(idEjercicio2);
                //String EjercicioG2_idEjercicioG2 = String.valueOf(10);
                String Imagen_idImagen_Ejercicio = String.valueOf(id_Imagen);
                String fila_E_h_I = String.valueOf(fila_imagen);
                String columna_E_H_I = String.valueOf(columna_imagen);

                //System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);
                parametros.put("EjercicioG2_idEjercicioG2", EjercicioG2_idEjercicioG2);
                parametros.put("Imagen_idImagen_Ejercicio", Imagen_idImagen_Ejercicio);
                parametros.put("fila_E_h_I", fila_E_h_I);
                parametros.put("columna_E_H_I", columna_E_H_I);

                System.out.println("Parametros: " + parametros.toString());


                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21

        //reemplazar espacios en blanco del nombre por %20
        // url = url.replace(" ", "%20");

        //hace el llamado a la url,no usa en p12
        /*jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);*/
    }
    // ----------------------------------------------------------------------------------------------

    private void consultarListaImagenes() {

       /* progreso.setMessage("Cargando...");
        progreso.show();*/
        // String ip = getString(R.string.ip);
        //int iddoc=20181;
        String iddoc = "20181";
        String url_lh = Globals.url;

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursosDocente.php?iddocente=" + txtiddoc.getText().toString();

        // String url = "http://"+url_lh+"/proyecto_dconfo/wsJSONConsultarListaCursosDocente.php?iddocente=" + txtiddoc.getText().toString();
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaImagenes.php";
        // http://localhost/proyecto_dconfo/
///wsJSONConsultarEstudiante.php?documento=" + edt_codigo.getText().toString();
        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //progreso.hide();
        Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        //progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(final JSONObject response) {
        //progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        Imagen imagen = null;
        JSONArray json = response.optJSONArray("imagen");

        try {
            for (int i = 0; i < json.length(); i++) {
                imagen = new Imagen();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                imagen.setIdImagen(jsonObject.optInt("idImagen_Ejercicio"));
                imagen.setNameImagen(jsonObject.optString("name_Imagen_Ejercicio"));
                imagen.setRutaImagen(jsonObject.optString("ruta_Imagen_Ejercicio"));
                imagen.setLetraInicialImagen(jsonObject.optString("letra_inicial_Imagen"));
                imagen.setLetraFinalImagen(jsonObject.optString("letra_final_Imagen"));
                imagen.setCantSilabasImagen(jsonObject.optInt("cant_silabas_Imagen"));

                listaImagenes.add(imagen);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }

            ImagenUrlAdapter imagenUrlAdapter = new ImagenUrlAdapter(listaImagenes, getContext());
            imagenUrlAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String rutaImagen = listaImagenes.get(rv_tipo1Fonico.
                            getChildAdapterPosition(v)).getRutaImagen();

                    String nameImagen = listaImagenes.get(rv_tipo1Fonico.
                            getChildAdapterPosition(v)).getNameImagen();

                    int idImagen = listaImagenes.get(rv_tipo1Fonico.
                            getChildAdapterPosition(v)).getIdImagen();

                    cargarImagenWebService(rutaImagen, nameImagen, idImagen);

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    System.out.println("on click: " + rutaImagen);
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_tipo1Fonico.setAdapter(imagenUrlAdapter);

            //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
            //Log.i("size", "lista Imágenes: " + listaImagenes.get(0).getNameImagen());

            //rv_bankimages.setAdapter(gruposDocenteAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            //progreso.hide();
        }
    }

    //**********************************************************************************************

    private void cargarImagenWebService(String rutaImagen, final String nameImagen, final int idImagen) {

        // String ip = context.getString(R.string.ip);

        String url_lh = Globals.url;

        String urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (btn_1Activo) {
                    btn_img_1.setBackground(null);
                    btn_img_1.setImageBitmap(response);
                    btn_1Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img1.setText(nameImagen);

                    int fila = 1;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                } else if (btn_2Activo) {
                    btn_img_2.setBackground(null);
                    btn_img_2.setImageBitmap(response);
                    btn_2Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img2.setText(nameImagen);

                    int fila = 1;
                    int columna = 2;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                } else if (btn_3Activo) {
                    btn_img_3.setBackground(null);
                    btn_img_3.setImageBitmap(response);
                    btn_3Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img3.setText(nameImagen);

                    int fila = 1;
                    int columna = 3;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                } else if (btn_4Activo) {
                    btn_img_4.setBackground(null);
                    btn_img_4.setImageBitmap(response);
                    btn_4Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img4.setText(nameImagen);

                    int fila = 1;
                    int columna = 4;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);
                }
                // iv_bank_prueba.setBackground(null);
                // iv_bank_prueba.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);
    }


    //----------------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fonico_crearImg1:
                // code for button when user clicks btnOne.
                Toast.makeText(getContext(), "crear 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fonico_crearImg2:
                // code for button when user clicks btnOne.
                Toast.makeText(getContext(), "crear 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fonico_crearImg3:
                // code for button when user clicks btnOne.
                Toast.makeText(getContext(), "crear 3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fonico_crearImg4:
                // code for button when user clicks btnOne.
                Toast.makeText(getContext(), "crear 4", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //**********************************************************************************************

    private void mostrarDialogOpciones() {//part 9
        final CharSequence[] opciones = {"Elegir de Banco de Imágenes", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Imágenes")) {
                /*    Intent intent = new Intent(getActivity(), BankImagesActivity.class);

                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setAction(Intent.ACTION_PICK);
                    //intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //intent.setType("image/*");

                    startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    System.out.println("info: " + intent);
                    //abriCamara();//part 10 tomar foto
                    //Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                    */
                    //cargarWebService_1();
                    rv_tipo1Fonico.setVisibility(View.VISIBLE);
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
                        //directamente de galeria
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//p9 de startActivityForResult
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                imgFoto.setImageURI(miPath);
                try {//p12
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), miPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cargarImagen();
                break;

            case COD_FOTO://p10
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", "" + path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);

                break;
        }
        bitmap = redimensionarImagen(bitmap, 600, 800);//part 14 redimencionar imágen,guarde en carpeta y BD
    }

    private void cargarImagen() {
        Drawable drawable = imgFoto.getDrawable();

       /* btn_img_4.setBackground(null);
        btn_img_4.setImageBitmap(response);
        btn_4Activo = false;
        rv_tipo1Fonico.setVisibility(View.GONE);
        txt_id_img4.setText(nameImagen);*/
        if (btn_1Activo) {
            btn_img_1.setBackground(null);
            btn_img_1.setImageBitmap(bitmap);
            btn_1Activo = false;

        } else if (btn_2Activo) {
            btn_img_2.setBackground(null);
            btn_img_2.setImageBitmap(bitmap);
            btn_2Activo = false;
        } else if (btn_3Activo) {
            btn_img_3.setBackground(null);
            btn_img_3.setImageBitmap(bitmap);
            btn_3Activo = false;
        } else if (btn_4Activo) {
            btn_img_4.setBackground(null);
            btn_img_4.setImageBitmap(bitmap);
            btn_4Activo = false;
        }
        // btn_Tipo1_pic_Ejercicio.setBackground(drawable);
        //imageView_muestra.setBackground(drawable);
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {//part 14

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();//manipular datos internos de la imagen
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);

        } else {
            return bitmap;
        }
    }

    //******************************WEB SERVICE
    //para iniciar el proceso de llamado al webservice
   /* private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip = Globals.url;
        //String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONRegistroTipo1Fonico.php";//p12.buena
        String url = "http://" + ip + "/proyecto_dconfo_v1/1wsJSONCrearEjercicio.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                   /* edt_letraInicial_img1.setText("");
                    edt_letraFinal_img1.setText("");
                    edt_cantSilaba_img1.setText("");

                    edt_letraInicial_img2.setText("");
                    edt_letraFinal_img2.setText("");
                    edt_cantSilaba_img1.setText("");

                    edt_letraInicial_img3.setText("");
                    edt_letraFinal_img3.setText("");
                    edt_cantSilaba_img3.setText("");

                    edt_letraInicial_img4.setText("");
                    edt_letraFinal_img4.setText("");
                    edt_cantSilaba_img4.setText("");*/

                /*    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String idejercicio = edt_CodigoEjercicio.getText().toString();
                //String idejercicio = "";
               /* String nameejercicio = edt_nameEjercicio.getText().toString();
                String iddocente = String.valueOf(idDocente);
                String idactividad = "2";
                String idtipo = "3";
                String imagen = convertirImgString(bitmap);
                System.out.println("dconfo imagen: "+imagen);
                String cantidadValida = edt_CantLexCorEjercicio.getText().toString();
                String oracion = edt_OrtacionEjercicio.getText().toString();*/
    //System.out.println("cantidadvalida"+cantidadValida);
    //System.out.println("oracion"+oracion);

    //    Map<String, String> parametros = new HashMap<>();
    // parametros.put("idEjercicio", idejercicio);
              /*  parametros.put("nameEjercicio", nameejercicio);
                parametros.put("docente_iddocente", iddocente);
                parametros.put("Actividad_idActividad", idactividad);
                parametros.put("Tipo_idTipo", idtipo);
                parametros.put("imagen", imagen);
                parametros.put("cantidadValidaEG1", cantidadValida);
                parametros.put("oracion", oracion);*/
    // parametros.put("imagen", imagen);

               /* return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21

        //reemplazar espacios en blanco del nombre por %20
        // url = url.replace(" ", "%20");

        //hace el llamado a la url,no usa en p12
        /*jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);*/
    // }

    private String convertirImgString(Bitmap bitmap) {
        //recibe un bitmap
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        //codifica a base64
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    //**********************************************************************************************

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
