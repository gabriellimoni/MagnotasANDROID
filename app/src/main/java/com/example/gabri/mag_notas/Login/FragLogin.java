package com.example.gabri.mag_notas.Login;


import android.content.Context;
import android.content.Intent;
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

import com.example.gabri.mag_notas.AsyncTask.ATVerificaLogin;
import com.example.gabri.mag_notas.Nota.MainActivity;
import com.example.gabri.mag_notas.R;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends Fragment {
    private static int ready = 0;
    private static String apelido;
    ItfLogin itf;

    EditText edt_email;
    EditText edt_senha;
    Button btn_cadastrar;
    Button btn_entrar;

    static ATVerificaLogin atv;

    public FragLogin() {
        // Required empty public constructor
    }

    public static void setJSONObject(Context context) {
        JSONObject receiver = atv.getJsonObject();

        if(receiver != null){
            try {
                // se der certo passa result, apelido e email
                if (receiver.getInt("result") == 1) {
                    Log.i("SUCCESS","yes");

                    apelido = receiver.getString("apelido");

                    Usuario.id = receiver.getInt("id");


                    ready = 1;

                } else {

                    String erro = receiver.getString("erroMsg");
                    Toast.makeText(context, "Erro: " + erro + ".", Toast.LENGTH_LONG).show();
                    ready = -1;

                }
            }catch (JSONException e) {

                e.printStackTrace();
            }
        }
        else{

            Toast.makeText(context, "Problema com o servidor!", Toast.LENGTH_LONG).show();
            ready = -1;

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        // carrega email se tiver salvo
        edt_email.setText(preferences.getString("email", ""));

        if(!edt_email.getText().toString().equals(""))
            edt_senha.requestFocus();

        edt_senha.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_login, container, false);


        edt_email = (EditText)v.findViewById(R.id.edt_l_usuario);
        edt_senha = (EditText)v.findViewById(R.id.edt_l_senha);
        btn_cadastrar = (Button)v.findViewById(R.id.btn_l_cadastro);
        btn_entrar = (Button)v.findViewById(R.id.btn_l_login);



        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itf.abreCadastro();
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/notasAndroid/php/validaLogin.php";

                atv = (ATVerificaLogin) new ATVerificaLogin(
                        edt_email.getText().toString(),
                        edt_senha.getText().toString(),
                        url,
                        getActivity()
                ).execute();


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(ready == 0){

                        }

                        if(ready == 1) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", edt_email.getText().toString());
                            editor.putString("apelido", apelido);
                            editor.commit();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }

                        ready = 0;
                    }
                }).start();
            }
        });


        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ItfLogin){
            itf = (ItfLogin)context;
        }else
            throw new RuntimeException(context.toString()+"Falha ao carregar itf");
    }

    public interface ItfLogin{
        public void abreCadastro();
    }

}
