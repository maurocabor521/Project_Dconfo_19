package com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.silabicos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasLetrag2;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.ImagenUrlAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2SilabicoUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2SilabicoUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2SilabicoUpdateFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView iv_c1f1;
    private ImageView iv_c1f2;
    private ImageView iv_c1f3;
    private ImageView iv_c1f4;

    private CircleImageView cv_c1f1;
    private CircleImageView cv_c1f2;
    private CircleImageView cv_c1f3;
    private CircleImageView cv_c1f4;

    private CircleImageView cv_c2f1;
    private CircleImageView cv_c2f2;
    private CircleImageView cv_c2f3;
    private CircleImageView cv_c2f4;

    private RadioButton rb_letraInicial;
    private RadioButton rb_letraFinal;

    private boolean flag_iv_c1f1;
    private boolean flag_iv_c1f2;
    private boolean flag_iv_c1f3;
    private boolean flag_iv_c1f4;

    private boolean flag_iv_c2f1;
    private boolean flag_iv_c2f2;
    private boolean flag_iv_c2f3;
    private boolean flag_iv_c2f4;

    private boolean estado_RbletraInicial;
    private boolean estado_RbletraFinal;

    private TextView txt_name_c1f1;
    private TextView txt_name_c1f2;
    private TextView txt_name_c1f3;
    private TextView txt_name_c1f4;

    private TextView txt_name_c2f1;
    private TextView txt_name_c2f2;
    private TextView txt_name_c2f3;
    private TextView txt_name_c2f4;


    private EditText edt_nameEjercicio;

    private LinearLayout ll_c1f1;
    private LinearLayout ll_c1f2;
    private LinearLayout ll_c1f3;
    private LinearLayout ll_c1f4;

    private LinearLayout ll_c2f1;
    private LinearLayout ll_c2f2;
    private LinearLayout ll_c2f3;
    private LinearLayout ll_c2f4;


    private EjercicioG2HasImagen ejercicioG2HasImagen;
    private EjercicioG2HasLetrag2 ejercicioG2HasLetrag2;
    private EjercicioG2 ejercicioG2;

    private RecyclerView rv_imagenesBancoDatos;

    private Button btn_enviar;

    ArrayList<Imagen> listaImagenes;
    ArrayList<EjercicioG2> listaEjerciciosG2;
    ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<Integer> listaidImagenes;
    ArrayList<Integer> listafilaImagen;
    ArrayList<Integer> listacolumnaImagen;

    ArrayList<String> listaLetras;
    ArrayList<Integer> listafilaLetra;
    ArrayList<Integer> listacolumnaLetra;

    private String nameDocente;
    private int idDocente;
    private int idEjercicio2;
    private int cont = 0;
    private String letra_inicial;
    private String letra_final;


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

    ProgressDialog progreso;
    ImageView imgFoto;
    File fileImagen;
    Bitmap bitmap;

    private OnFragmentInteractionListener mListener;

    public Tipo2SilabicoUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2SilabicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2SilabicoUpdateFragment newInstance(String param1, String param2) {
        Tipo2SilabicoUpdateFragment fragment = new Tipo2SilabicoUpdateFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo2_silabico_update, container, false);

        nameDocente = getArguments().getString("namedocente");
        idDocente = getArguments().getInt("iddocente");

        ejercicioG2HasImagen = new EjercicioG2HasImagen();
        ejercicioG2HasLetrag2 = new EjercicioG2HasLetrag2();

        listaImagenes = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listaEjercicios = new ArrayList<>();
        listaidImagenes = new ArrayList<>();
        listafilaImagen = new ArrayList<>();
        listacolumnaImagen = new ArrayList<>();

        listaLetras = new ArrayList<>();
        listafilaLetra = new ArrayList<>();
        listacolumnaLetra = new ArrayList<>();

        rv_imagenesBancoDatos = (RecyclerView) view.findViewById(R.id.rv_docente_sil2_imgs_update);
        rv_imagenesBancoDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_imagenesBancoDatos.setHasFixedSize(true);
        //rv_imagenesBancoDatos.setVisibility(View.INVISIBLE);

        rb_letraInicial = (RadioButton) view.findViewById(R.id.rb_letraInicial_sil2_update);
        rb_letraFinal = (RadioButton) view.findViewById(R.id.rb_letraFinal_sil2_update);

        ll_c1f1 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c1_f1_update);
        ll_c1f2 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c1_f2_update);
        ll_c1f3 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c1_f3_update);
        ll_c1f4 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c1_f4_update);

        ll_c2f1 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c2_f1_update);
        ll_c2f2 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c2_f2_update);
        ll_c2f3 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c2_f3_update);
        ll_c2f4 = (LinearLayout) view.findViewById(R.id.ll_docente_sil2_c2_f4_update);

        txt_name_c1f1 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c1f1_update);
        txt_name_c1f2 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c1f2_update);
        txt_name_c1f3 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c1f3_update);
        txt_name_c1f4 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c1f4_update);

        txt_name_c2f1 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c2f1_update);
        txt_name_c2f2 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c2f2_update);
        txt_name_c2f3 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c2f3_update);
        txt_name_c2f4 = (TextView) view.findViewById(R.id.txt_docente_sil2_nom_c2f4_update);

        cv_c1f1 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c1_f1_update);
        cv_c1f2 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c1_f2_update);
        cv_c1f3 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c1_f3_update);
        cv_c1f4 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c1_f4_update);

        cv_c2f1 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c2_f1_update);
        cv_c2f2 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c2_f2_update);
        cv_c2f3 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c2_f3_update);
        cv_c2f4 = (CircleImageView) view.findViewById(R.id.iv_docente_sil2_c2_f4_update);

        edt_nameEjercicio = (EditText) view.findViewById(R.id.edt_docente_sil2_nameEjercicio_update);

        cv_c1f1.setOnClickListener(this);
        cv_c1f2.setOnClickListener(this);
        cv_c1f3.setOnClickListener(this);
        cv_c1f4.setOnClickListener(this);
        cv_c2f1.setOnClickListener(this);
        cv_c2f2.setOnClickListener(this);
        cv_c2f3.setOnClickListener(this);
        cv_c2f4.setOnClickListener(this);


        btn_enviar = (Button) view.findViewById(R.id.btn_docente_sil2_enviar_update);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaRadioButton();
                //cargarEdt_letras();
                cargarWS_CrearEjerciciog2_tipo2();
                //System.out.println("id del docente: " + idDocente);
                //verificaRadioButton();
            }
        });

        imgFoto = new ImageView(getContext());

        consultarListaImagenes();

        return view;
    }
    //**********************************************************************************************

    public void verificaRadioButton() {//f1
        if (rb_letraInicial.isChecked() == true) {
            estado_RbletraInicial = rb_letraInicial.isChecked();
            System.out.println("Radio letra inicial estado: " + estado_RbletraInicial);
            //System.out.println("Radio letra final estado: "+estado_RbletraFinal);
            Toast.makeText(getContext(), "Radio letra inicial estado: " + estado_RbletraInicial, Toast.LENGTH_SHORT).show();
        } else if (rb_letraFinal.isChecked() == true) {
            //System.out.println("Radio letra inicial estado: "+estado_RbletraInicial);
            estado_RbletraFinal = rb_letraFinal.isChecked();
            System.out.println("Radio letra final estado: " + estado_RbletraFinal);
            Toast.makeText(getContext(), "Radio letra final estado: " + estado_RbletraFinal, Toast.LENGTH_SHORT).show();
        }
    }

    //**********************************************************************************************

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_docente_sil2_c1_f1:
                mostrarDialogOpciones();
                flag_iv_c1f1 = true;
                break;
            case R.id.iv_docente_sil2_c1_f2:
                mostrarDialogOpciones();
                flag_iv_c1f2 = true;
                break;
            case R.id.iv_docente_sil2_c1_f3:
                mostrarDialogOpciones();
                flag_iv_c1f3 = true;
                break;
            case R.id.iv_docente_sil2_c1_f4:
                mostrarDialogOpciones();
                flag_iv_c1f4 = true;
                break;
            case R.id.iv_docente_sil2_c2_f1:
                mostrarDialogOpciones();
                flag_iv_c2f1 = true;
                Toast.makeText(getContext(), "c2f1", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_docente_sil2_c2_f2:
                mostrarDialogOpciones();
                flag_iv_c2f2 = true;
                break;
            case R.id.iv_docente_sil2_c2_f3:
                mostrarDialogOpciones();
                flag_iv_c2f3 = true;
                break;
            case R.id.iv_docente_sil2_c2_f4:
                mostrarDialogOpciones();
                flag_iv_c2f4 = true;
                break;
        }
    }

    //**********************************************************************************************

    private void mostrarDialogOpciones() {//part 9 //f6
        final CharSequence[] opciones = {"Elegir de Banco de Imágenes", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Imágenes")) {

                    rv_imagenesBancoDatos.setVisibility(View.VISIBLE);
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

    }//*********************************************************************************************

    //----------------------------------------------------------------------------------------------

    private void consultarListaImagenes() {//f5


        String iddoc = "20181";
        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaImagenes.php";

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

                    String rutaImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getRutaImagen();

                    String nameImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getNameImagen();

                    int idImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getIdImagen();

                    cargarImagenWebService(rutaImagen, nameImagen, idImagen);

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    System.out.println("on click: " + rutaImagen);
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_imagenesBancoDatos.setAdapter(imagenUrlAdapter);

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

    private void cargarImagenWebService(String rutaImagen, final String nameImagen, final int idImagen) {//f4

        // String ip = context.getString(R.string.ip);

        String url_lh = Globals.url;

        String urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (flag_iv_c1f1) {
                    cv_c1f1.setBackground(null);
                    cv_c1f1.setImageBitmap(response);
                    //iv_c1f1.setBackground(null);
                    //iv_c1f1.setImageBitmap(response);
                    flag_iv_c1f1 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f1.setText(nameImagen);

                    int fila = 1;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f2) {
                    cv_c1f2.setBackground(null);
                    cv_c1f2.setImageBitmap(response);
                    flag_iv_c1f2 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f2.setText(nameImagen);

                    int fila = 2;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f3) {
                    cv_c1f3.setBackground(null);
                    cv_c1f3.setImageBitmap(response);
                    flag_iv_c1f3 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f3.setText(nameImagen);

                    int fila = 3;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f4) {
                    cv_c1f4.setBackground(null);
                    cv_c1f4.setImageBitmap(response);
                    flag_iv_c1f4 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f4.setText(nameImagen);

                    int fila = 4;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c2f1) {
                    cv_c2f1.setBackground(null);
                    cv_c2f1.setImageBitmap(response);
                    flag_iv_c2f1 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c2f1.setText(nameImagen);

                    int fila = 1;
                    int columna = 2;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c2f2) {
                    cv_c2f2.setBackground(null);
                    cv_c2f2.setImageBitmap(response);
                    flag_iv_c2f2 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c2f2.setText(nameImagen);

                    int fila = 2;
                    int columna = 2;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c2f3) {
                    cv_c2f3.setBackground(null);
                    cv_c2f3.setImageBitmap(response);
                    flag_iv_c2f3 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c2f3.setText(nameImagen);

                    int fila = 3;
                    int columna = 2;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c2f4) {
                    cv_c2f4.setBackground(null);
                    cv_c2f4.setImageBitmap(response);
                    flag_iv_c2f4 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c2f4.setText(nameImagen);

                    int fila = 4;
                    int columna = 2;

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

    private void cargarWS_CrearEjerciciog2_tipo2() {//f3
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

                    //edt_letra.setText("");
                    edt_nameEjercicio.setText("");
                    listarEjerciciosG2Docente();

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito" + response.toString(), Toast.LENGTH_LONG).show();
                    System.out.println("response: " + response.toString());
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
                System.out.println("edt_nameejercicio" + nameEjercicio);
                String docente_iddocente = String.valueOf(idDocente);
                String Tipo_idTipo = "6";
                String Actividad_idActividad = "3";
                if (estado_RbletraInicial) {

                    letra_inicial = "s";
                    letra_final = "n";

                    System.out.println("estado_RbletraInicial :" + estado_RbletraInicial);
                    System.out.println("letra_inicial 1 :" + letra_inicial);
                    System.out.println("letra_final 1 :" + letra_final);
                }
                if (estado_RbletraFinal) {

                    letra_inicial = "n";
                    letra_final = "s";

                    System.out.println("estado_RbletraFinal :" + estado_RbletraFinal);
                    System.out.println("letra_inicial 2:" + letra_inicial);
                    System.out.println("letra_final 2:" + letra_final);
                }

                String imagen = "";
                String cantidad = "";
                String oracion = "";


                // System.out.println("letra inicial" + letra_inicial);

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
                // Map<String, String> parametros = new HashMap<>();
                // return parametros;
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

    //----------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------

    //******************************WEB SERVICE
    public void listarEjerciciosG2Docente() {//f7


        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?docente_iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjercicios_Fonico1_Docente.php?iddocente=" + idDocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/2wsJSONConsultarListaEjercicios.php?iddocente=" + idDocente;

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

    //***********************************************************************************************

    private void ejerciciog2HI_adjuntarImagenes() {//f9

        System.out.println("ejerciciog2HI_adjuntarImagenes: ");

        for (int i = 0; i < listaidImagenes.size(); i++) {

            System.out.println(" listaidImagenes :" + listaidImagenes.toString());

            webService_CrearEjercicioG2_Has_Imagen(listaidImagenes.get(i), listafilaImagen.get(i), listacolumnaImagen.get(i));
            //webService_CrearEjercicioG2_Has_Letra(listaLetras.get(i), listafilaLetra.get(i), listacolumnaLetra.get(i));


            System.out.println(" i :" + i);

          /*  if (cont == 4) {
                Toast.makeText(getContext(), "limpia listas: " + i, Toast.LENGTH_LONG).show();
                listaidImagenes.clear();
                listafilaImagen.clear();
                listacolumnaImagen.clear();
                listacolumnaLetra.clear();
                listafilaLetra.clear();
                listaLetras.clear();
                System.out.println(" listaidImagenes :" + listacolumnaLetra.toString());

            }*/
            try {
                Thread.sleep(100);
                //System.out.println("Dormir for... " + listaidImagenes.get(i));
            } catch (InterruptedException e) {

            }
        }
        edt_nameEjercicio.setText("");

        imgFoto.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        Drawable drawable = imgFoto.getDrawable();
        cv_c1f1.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f2.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f3.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f4.setImageResource(R.drawable.ic_no_foto_80dp);

        txt_name_c1f1.setText("imagen 1");
        txt_name_c1f2.setText("imagen 2");
        txt_name_c1f3.setText("imagen 3");
        txt_name_c1f4.setText("imagen 4");

        cv_c2f1.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c2f2.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c2f3.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c2f4.setImageResource(R.drawable.ic_no_foto_80dp);

        txt_name_c2f1.setText("imagen 5");
        txt_name_c2f2.setText("imagen 6");
        txt_name_c2f3.setText("imagen 7");
        txt_name_c2f4.setText("imagen 8");

        /*btn_img_1.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_2.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_3.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_4.setBackgroundResource(R.drawable.ic_no_foto_80dp);*/


    }
    // ----------------------------------------------------------------------------------------------

    private void webService_CrearEjercicioG2_Has_Imagen(final int id_Imagen, final int fila_imagen, final int columna_imagen) {//f8
       /* progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/
        System.out.println(" Entrando a CREAR EG2_HAS_IMG");
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONCrearEjercicio2HasImagen.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    //edt_letra.setText("");
                    // edt_nameEjercicio.setText("");
                    System.out.println("CREAR EG2_HAS_IMG" + response.toString());


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

    // ----------------------------------------------------------------------------------------------


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
