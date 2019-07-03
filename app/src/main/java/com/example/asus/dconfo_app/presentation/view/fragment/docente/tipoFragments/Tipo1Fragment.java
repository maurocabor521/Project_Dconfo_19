package com.example.asus.dconfo_app.presentation.view.fragment.docente.tipoFragments;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1Fragment extends Fragment {
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
    private Button btn_Tipo1_pic_Ejercicio;
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

    String nameDocente="";
    int idDocente=0;

    private TextToSpeech mTTS;
    private Button mButtonSpeak;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;


    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    RelativeLayout layoutRegistrar;//permisos

    private OnFragmentInteractionListener mListener;

    public static Tipo1Fragment getInstance() {
        return new Tipo1Fragment();
    }

    public Tipo1Fragment() {
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
    public static Tipo1Fragment newInstance(String param1, String param2) {
        Tipo1Fragment fragment = new Tipo1Fragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo1, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_tipo1);

        nameDocente=getArguments().getString("namedocente");
        idDocente=getArguments().getInt("iddocente");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Docente Tipo 1: "+nameDocente);

        ll_tipo_ejercicio=(LinearLayout)view.findViewById(R.id.ll_tipo_muestra_ejercicio);
        ll_tipo_ejercicio_form=(LinearLayout)view.findViewById(R.id.ll_docente_lex_t1);

        imageView_muestra=(ImageView)view.findViewById(R.id.iv_imagen_muestra);

        mSeekBarPitch = (SeekBar)view.findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = (SeekBar)view.findViewById(R.id.seek_bar_speed);

        mButtonSpeak=(Button)view.findViewById(R.id.btn_tipo1_textToS);
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
                    int result = mTTS.setLanguage(new Locale("spa","ESP"));

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

        edt_CantLexCorEjercicio = (EditText) view.findViewById(R.id.edt_tipo1_cant_lex_corr);
        edt_CodigoEjercicio = (EditText) view.findViewById(R.id.edt_tipo1_codigoEjercicio);
        edt_nameEjercicio = (EditText) view.findViewById(R.id.edt_tipo1_nameEjercicio);
        edt_OrtacionEjercicio = (EditText) view.findViewById(R.id.edt_tipo1_oracion);
        btn_NewTipo1_Ejercicio = (Button) view.findViewById(R.id.btn_tipo1_send_ejercicio);
        btn_Tipo1_pic_Ejercicio = (Button) view.findViewById(R.id.btn_tipo1_pic);
        imgFoto = new ImageView(getContext());
        btn_Tipo1_pic_Ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        btn_NewTipo1_Ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();

            }
        });
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

    private void mostrarDialogOpciones_() {//part 9
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    //abriCamara();//part 10 tomar foto
                    Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
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
    private void mostrarDialogOpciones() {//part 9
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Banco de Datos", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Datos")) {
                    //abriCamara();//part 10 tomar foto
                   // ll_rv_ejercicios.setVisibility(View.VISIBLE);
                    //ll_body.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
                        //directamente de galeria
                        //isGalleryChoise = true;
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                    if (opciones[i].equals("Tomar Foto")) {
                        //isGalleryChoise = true;
                        abriCamara();//part 10 tomar foto
                        Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.show();

    }

    private void abriCamara() {//part 10
        Toast.makeText(getContext(), "abricamara", Toast.LENGTH_LONG).show();
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Toast.makeText(getContext(), "abricamara, istrue", Toast.LENGTH_LONG).show();
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;//indicamos la ruta de almacenamiento

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));//necesario para activar la cámara,como minimo

            ////

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Toast.makeText(getContext(), "abricamara, N", Toast.LENGTH_LONG).show();
                String authorities = getContext().getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                Toast.makeText(getContext(), "abricamara, Not N", Toast.LENGTH_LONG).show();
            }

            startActivityForResult(intent, COD_FOTO);

            ////

        }

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
        String url = "http://" + ip + "/proyecto_dconfo_v1/1wsJSONCrearEjercicio.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_CantLexCorEjercicio.setText("");
                    edt_CodigoEjercicio.setText("");
                    edt_nameEjercicio.setText("");
                   // edt_OrtacionEjercicio.setText("");
                    ll_tipo_ejercicio.setVisibility(View.VISIBLE);
                    ll_tipo_ejercicio_form.setVisibility(View.GONE);
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
                System.out.println("error: " + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String idejercicio = edt_CodigoEjercicio.getText().toString();
                //String idejercicio = "";
                String nameejercicio = edt_nameEjercicio.getText().toString();
                String iddocente = String.valueOf(idDocente);
                String idactividad = "2";
                String idtipo = "3";
                String imagen = convertirImgString(bitmap);
                //System.out.println("dconfo imagen: "+imagen);
                String cantidadValida = edt_CantLexCorEjercicio.getText().toString();
                String oracion = edt_OrtacionEjercicio.getText().toString();
                //System.out.println("cantidadvalida"+cantidadValida);
                //System.out.println("oracion"+oracion);
                String letraInicial = "";
                String letraFinal = "";

                Map<String, String> parametros = new HashMap<>();
               // parametros.put("idEjercicio", idejercicio);
                parametros.put("nameEjercicioG2", nameejercicio);
                parametros.put("docente_iddocente", iddocente);
                parametros.put("Tipo_Actividad_idActividad", idactividad);
                parametros.put("Tipo_idTipo", idtipo);
                parametros.put("imagen", imagen);
                parametros.put("cantidadValidadEjercicio", cantidadValida);
                parametros.put("oracion", oracion);
                parametros.put("letra_inicial_EjercicioG2", letraInicial);
                parametros.put("letra_final_EjercicioG2", letraFinal);
                // parametros.put("imagen", imagen);

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
