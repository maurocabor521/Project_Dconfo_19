package com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.lexicos;

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
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
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
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TipoLexicoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TipoLexicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipoLexicoFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edt_nameEjercicio;
    private EditText edt_CodigoEjercicio;
    private EditText edt_OrtacionEjercicio;
    private EditText edt_CantLexCorEjercicio;
    private Button btn_NewTipo1_Ejercicio;
    private CircleImageView btn_Tipo1_pic_Ejercicio;
    private LinearLayout ll_tipo_ejercicio;
    private LinearLayout ll_tipo_ejercicio_form;
    private ImageView imageView_muestra;

    ProgressDialog progreso;
    ImageView imgFoto;
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen

    String nameDocente = "";
    int idDocente = 0;
    int idgrupo = 0;
    int idejercicio = 0;

    private TextToSpeech mTTS;
    private Button mButtonSpeak;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    private LinearLayout ll_rv_ejercicios;
    private LinearLayout ll_body;
    private RecyclerView rv_tipo1Lexico;

    ArrayList<Imagen> listaImagenes;

    private boolean isGalleryChoise = false;
    private String ruta_Imagen;


    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    RelativeLayout layoutRegistrar;//permisos

    private OnFragmentInteractionListener mListener;

    public static TipoLexicoFragment getInstance() {
        return new TipoLexicoFragment();
    }

    public TipoLexicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipoLexicoFragment newInstance(String param1, String param2) {
        TipoLexicoFragment fragment = new TipoLexicoFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo_lexico, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_tipo1);

        listaImagenes = new ArrayList<>();

        ll_rv_ejercicios = (LinearLayout) view.findViewById(R.id.ll_docente_mod_lex_t1);
        ll_body = (LinearLayout) view.findViewById(R.id.ll_docente_mod_body_lext1);

        rv_tipo1Lexico = (RecyclerView) view.findViewById(R.id.rv_docente_mod_lex_t1);
        rv_tipo1Lexico.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_tipo1Lexico.setHasFixedSize(true);

        nameDocente = getArguments().getString("namedocente");
        idDocente = getArguments().getInt("iddocente");
        idgrupo = getArguments().getInt("idgrupo");
        idejercicio = getArguments().getInt("idejercicio");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("id ejercicio: " + idejercicio);

        ll_tipo_ejercicio = (LinearLayout) view.findViewById(R.id.ll_tipo_muestra_ejercicio);
        ll_tipo_ejercicio_form = (LinearLayout) view.findViewById(R.id.ll_docente_lex_t1);

        imageView_muestra = (ImageView) view.findViewById(R.id.iv_imagen_muestra);

        mSeekBarPitch = (SeekBar) view.findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.seek_bar_speed);

        mButtonSpeak = (Button) view.findViewById(R.id.btn_tipo1_textToS);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //int result = mTTS.setLanguage(Locale.getDefault());
                    int result = mTTS.setLanguage(new Locale("spa", "ESP"));

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        // mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        imgFoto = new ImageView(getContext());

        edt_CantLexCorEjercicio = (EditText) view.findViewById(R.id.edt_update_lex1_cant_lex_corr);
        // edt_CodigoEjercicio = (EditText) view.findViewById(R.id.edt_tipo1_codigoEjercicio);
        edt_nameEjercicio = (EditText) view.findViewById(R.id.edt_update_lex1_nameEjercicio);
        edt_OrtacionEjercicio = (EditText) view.findViewById(R.id.edt_update_lex1_oracion);


        //********************************CIRCLEVIEW NO BOTÓN

        btn_Tipo1_pic_Ejercicio = (CircleImageView) view.findViewById(R.id.civ_tipo1_pic);
        btn_Tipo1_pic_Ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        //********************************

        btn_NewTipo1_Ejercicio = (Button) view.findViewById(R.id.btn_update_lex1_send_ejercicio);
        btn_NewTipo1_Ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();

            }
        });

        consultarListaImagenes();
        return view;

    }

    private void speak() {
        String text = edt_OrtacionEjercicio.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void mostrarDialogOpciones() {//part 9
        final CharSequence[] opciones = {"Elegir de Banco de Datos", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Datos")) {
                    //abriCamara();//part 10 tomar foto
                    ll_rv_ejercicios.setVisibility(View.VISIBLE);
                    ll_body.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
                        //directamente de galeria
                        isGalleryChoise = true;
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
        btn_Tipo1_pic_Ejercicio.setBackground(drawable);
        imageView_muestra.setBackground(drawable);
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
    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip = Globals.url;
        //String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONRegistroTipo1.php";//p12.buena
        String url = "http://" + ip + "/proyecto_dconfo_v1/23wsJSON_UpdateLexcio1.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_CantLexCorEjercicio.setText("");
                   // edt_CodigoEjercicio.setText("");
                    edt_nameEjercicio.setText("");
                    // edt_OrtacionEjercicio.setText("");
                    ll_tipo_ejercicio.setVisibility(View.VISIBLE);
                    ll_tipo_ejercicio_form.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    System.out.println("error: " + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error: " + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String idejercicio = edt_CodigoEjercicio.getText().toString();

                String id_ejercicio = String.valueOf(idejercicio);
                String nameejercicio = edt_nameEjercicio.getText().toString();
                //String iddocente = String.valueOf(idDocente);
                // String idactividad = "2";
                // String idtipo = "3";
                //String imagen = convertirImgString(bitmap);
                String imagen = null;
                String cantidadValida = edt_CantLexCorEjercicio.getText().toString();
                String oracion = edt_OrtacionEjercicio.getText().toString();
                String de_galeria;
                String rutaImagen = "";

                if (isGalleryChoise == true) {
                    de_galeria = "si";
                    imagen = convertirImgString(bitmap);
                    System.out.println("dconfo imagen: " + imagen);
                } else {
                    de_galeria = "no";
                }


                Map<String, String> parametros = new HashMap<>();
                // parametros.put("idEjercicio", idejercicio);

                parametros.put("idejercicio", id_ejercicio);
                parametros.put("nameEjercicioG2", nameejercicio);
                // parametros.put("docente_iddocente", iddocente);
                // parametros.put("Tipo_Actividad_idActividad", idactividad);
                //parametros.put("Tipo_idTipo", idtipo);
                // parametros.put("imagen", imagen);
                parametros.put("cantidadValidadEjercicio", cantidadValida);
                parametros.put("oracion", oracion);
                // parametros.put("letra_inicial_EjercicioG2", letraInicial);
                //parametros.put("letra_final_EjercicioG2", letraFinal);
                // parametros.put("imagen", imagen);

                if (isGalleryChoise == true) {
                    parametros.put("imagen", imagen);
                    parametros.put("de_galeria", de_galeria);
                    parametros.put("rutaImagen", rutaImagen);
                    isGalleryChoise = false;
                } else {
                    parametros.put("imagen", "");
                    parametros.put("de_galeria", de_galeria);
                    parametros.put("rutaImagen", ruta_Imagen);
                }

                System.out.println("parametros: " + parametros.toString());
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

    private String convertirImgString(Bitmap bitmap) {
        //recibe un bitmap
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        //codifica a base64
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

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

                    String rutaImagen = listaImagenes.get(rv_tipo1Lexico.
                            getChildAdapterPosition(v)).getRutaImagen();

                    String nameImagen = listaImagenes.get(rv_tipo1Lexico.
                            getChildAdapterPosition(v)).getNameImagen();

                    int idImagen = listaImagenes.get(rv_tipo1Lexico.
                            getChildAdapterPosition(v)).getIdImagen();

                    ruta_Imagen = rutaImagen;

                    cargarImagenWebService(rutaImagen, nameImagen, idImagen);

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    System.out.println("on click: " + rutaImagen);
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_tipo1Lexico.setAdapter(imagenUrlAdapter);

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
                //  if (btn_1Activo) {
                btn_Tipo1_pic_Ejercicio.setBackground(null);
                btn_Tipo1_pic_Ejercicio.setImageBitmap(response);
                rv_tipo1Lexico.setVisibility(View.GONE);
                ll_body.setVisibility(View.VISIBLE);
                   /* btn_1Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img1.setText(nameImagen);*/

                int fila = 1;
                int columna = 1;

                   /* ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);*/

                //  }
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
