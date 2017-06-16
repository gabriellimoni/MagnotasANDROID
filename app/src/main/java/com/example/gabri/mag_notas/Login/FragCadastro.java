package com.example.gabri.mag_notas.Login;


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
import android.widget.Toast;

import com.example.gabri.mag_notas.AsyncTask.ATCadastroUsuario;
import com.example.gabri.mag_notas.Nota.FragCadastroNota;
import com.example.gabri.mag_notas.R;
import com.example.gabri.mag_notas.Usuario;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragCadastro extends Fragment {

    ItfCadastro itf;

    EditText edt_nome;
    EditText edt_apelido;
    EditText edt_senha;
    EditText edt_email;

    Button btn_cadastrar;
    Button btn_cancelar;

    String url = "";
    List<NameValuePair> parametros;

    static ATCadastroUsuario async;


    public FragCadastro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_cadastro, container, false);

        edt_apelido = (EditText)v.findViewById(R.id.edt_c_apelido);
        edt_nome = (EditText)v.findViewById(R.id.edt_c_nome);
        edt_email = (EditText)v.findViewById(R.id.edt_c_email);
        edt_senha = (EditText)v.findViewById(R.id.edt_c_senha);

        btn_cadastrar = (Button)v.findViewById(R.id.btn_c_cadastrar);
        btn_cancelar = (Button)v.findViewById(R.id.btn_c_cancelar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itf.abreLogin();
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usu = new Usuario(
                        edt_nome.getText().toString(),
                        edt_apelido.getText().toString(),
                        edt_email.getText().toString(),
                        edt_senha.getText().toString()
                );

                // TODO : implementar validacao antes da conexao com php

                url = "http://10.0.2.2/notasAndroid/php/usuarioCadastro.php";


                //ASYNCTASK
                async = (ATCadastroUsuario) new ATCadastroUsuario(usu, url, getActivity(), itf).execute();
            }

        });


        return v;
    }


    // TODO arrumar constraints no banco
    public static void setJSONObject(Context context, ItfCadastro itf){
        JSONObject receiver = async.getJsonObject();

        // fala quem foi cadastrado
        if(receiver != null) {
            try {
                // se der certo passa result, apelido e email
                if (receiver.getInt("result") == 1) {
                    Log.i("SUCCESS","yes");
                    String apelido = receiver.getString("apelido");

                    Toast.makeText(context, "Usuário " + apelido + " cadastrado com sucesso!", Toast.LENGTH_LONG).show();


                    // salva o email no sharedpreferences
                    String email = receiver.getString("email");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    Log.i("SP",email);

                    editor.putString("email", email);
                    editor.commit();

                    itf.abreLogin();

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



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ItfCadastro)
            itf = (ItfCadastro)context;
        else
            throw new RuntimeException("Falhou");
    }

    public interface ItfCadastro{
        public void abreLogin();
    }
}
