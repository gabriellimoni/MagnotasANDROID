package com.example.gabri.mag_notas.Nota;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabri.mag_notas.AsyncTask.ATCadastroNota;
import com.example.gabri.mag_notas.Login.FragCadastro;
import com.example.gabri.mag_notas.Nota.Nota;
import com.example.gabri.mag_notas.R;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class FragCadastroNota extends Fragment {
    EditText assunto = null;
    EditText texto = null;
    Spinner categoria = null;
    Button btn = null;

    int id = -1, t_cat;
    String t_assunto, t_texto;

    static ATCadastroNota atc;

    ItfNota itfNota;

    public FragCadastroNota() {
        // Required empty public constructor
    }

    public FragCadastroNota(String assunto, String texto, int id, int categoria){
        this.id = id;
        this.t_assunto = assunto;
        this.t_texto = texto;
        this.t_cat = categoria;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_cadastro_nota, container, false);

        assunto = (EditText)v.findViewById(R.id.nova_assunto);
        texto = (EditText)v.findViewById(R.id.nova_texto);
        categoria = (Spinner)v.findViewById(R.id.spn_categoria);
        btn = (Button)v.findViewById(R.id.btn_add);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/notasAndroid/php/cadastroNota.php";

                HashMap<String, String> parametros = new HashMap<String, String>();

                parametros.put("assunto", assunto.getText().toString());
                parametros.put("texto", texto.getText().toString());
                parametros.put("categoria", String.valueOf(categoria.getSelectedItemPosition()));
                parametros.put("usuario", String.valueOf(Usuario.id));
                parametros.put("id", String.valueOf(id));

                atc = (ATCadastroNota) new ATCadastroNota(parametros, url, getActivity(), itfNota).execute();
                    /*
                        Nota nota = new Nota(assunto.getText().toString(),
                                            texto.getText().toString(),
                                            new Date(),
                                            id,
                                            usuario,
                                            categoria.getSelectedItemPosition());
                        itfNota.updtNota(nota);
                    }
                    else {
                        Nota nota = new Nota(assunto.getText().toString(),
                                texto.getText().toString(),
                                new Date(),
                                usuario,
                                categoria.getSelectedItemPosition());
                        itfNota.sendNota(nota);
                    }
                    */

            }
        });

        if (id != -1){
            assunto.setText(t_assunto);
            texto.setText(t_texto);
            categoria.setSelection(t_cat);
        }

        return v;
    }

    // interface para se comunicar com act principal
    public interface ItfNota {
        public void sendNota(Nota nota);
        public void updtNota(Nota nota);

        public void abreNotas();
    }

    @Override
    public void onAttach(Context contexto){
        super.onAttach(contexto);
        // pegar od do usuario conectado!
        if(contexto instanceof ItfNota){
            // pega aimplementação da interface
            itfNota = (ItfNota)contexto;
        }
        else{
            throw new RuntimeException(contexto.toString()+
                    "interface não implementada na ACT!");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        itfNota = null;
    }

    public static void setJSONObject(Context context, FragCadastroNota.ItfNota itf){
        JSONObject receiver = atc.getJsonObject();

        // fala quem foi cadastrado
        if(receiver != null) {
            try {
                // se der certo passa result, apelido e email
                if (receiver.getInt("result") == 1) {
                    Log.i("SUCCESS","yes");
                    String sucesso = receiver.getString("sucesso");

                    Toast.makeText(context, sucesso, Toast.LENGTH_LONG).show();
                    itf.abreNotas();


                    // se for erro envia erroMsg e result
                } else {

                    String erro = receiver.getString("erroMsg");
                    Toast.makeText(context, "Erro: " + erro + ".", Toast.LENGTH_LONG).show();

                }
            }catch (JSONException e) {

                e.printStackTrace();
            }

        }else{

            Toast.makeText(context, "Problema com o servidor!", Toast.LENGTH_LONG).show();

        }
    }

    private void clearAll() {
        assunto.setText("");
    }
}
