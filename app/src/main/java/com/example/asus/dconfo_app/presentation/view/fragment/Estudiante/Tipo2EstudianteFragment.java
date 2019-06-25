package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2EstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2EstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2EstudianteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextToSpeech mTTS;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    private Button mButtonSpeak;
    private Button btn_b1;
    private Button btn_b2;
    private Button btn_b3;
    private Button btn_b4;
    private Button btn_b5;
    private Button btn_responder;

    private int idEjercicio;
    private int cantLexemas;
    private String cantLexemas_bundle;
    private String oracion;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;

    String usuario;//250619

    private OnFragmentInteractionListener mListener;

    public Tipo2EstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2EstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2EstudianteFragment newInstance(String param1, String param2) {
        Tipo2EstudianteFragment fragment = new Tipo2EstudianteFragment();
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
        View view;
        view = inflater.inflate(R.layout.fragment_tipo2_estudiante, container, false);

        idEjercicio = getArguments().getInt("idejercicio");
        usuario = getArguments().getString("usuario");
        cantLexemas_bundle = getArguments().getString("cantLexemas");
        System.out.println("cantLexemas_bundle: " + cantLexemas_bundle);
        oracion = getArguments().getString("oracion");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("id Ejercicio: " + idEjercicio);

        mSeekBarPitch = (SeekBar) view.findViewById(R.id.estudiante_seek_bar_pitch);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.estudiante_seek_bar_speed);

        mButtonSpeak = (Button) view.findViewById(R.id.btn_estudiante_escuchar_tipo2);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        btn_b1 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla1);
        btn_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_b1.setBackground(null);
                btn_b1.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c1: " + cantLexemas);
            }
        });

        btn_b2 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla2);
        btn_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_b2.setBackground(null);
                btn_b2.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c2: " + cantLexemas);
            }
        });

        btn_b3 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla3);
        btn_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_b3.setBackground(null);
                btn_b3.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c3: " + cantLexemas);
            }
        });

        btn_b4 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla4);
        btn_b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_b4.setBackground(null);
                btn_b4.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c4: " + cantLexemas);
            }
        });

        btn_b5 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla5);
        btn_b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_b5.setBackground(null);
                btn_b5.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c5: " + cantLexemas);
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

        btn_responder = (Button) view.findViewById(R.id.btn_estudiante_tipo2_enviar_respuesta);
        btn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantLexemas == Integer.parseInt(cantLexemas_bundle)) {
                    Toast.makeText(getContext(), "CORRECTO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "INCORRECTO", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void speak() {
        // String text = edt_OrtacionEjercicio.getText().toString();
        //String text = edt_OrtacionEjercicio.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(oracion, TextToSpeech.QUEUE_FLUSH, null);
        System.out.println("oración: " + oracion);
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
