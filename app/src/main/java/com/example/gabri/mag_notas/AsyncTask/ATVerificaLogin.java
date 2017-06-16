package com.example.gabri.mag_notas.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gabri.mag_notas.Login.FragCadastro;
import com.example.gabri.mag_notas.Login.FragLogin;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gabri on 01/06/2017.
 */

public class ATVerificaLogin extends AsyncTask<String, String, String> {

    // recebe msg php e converte para json
    JSONParser jparser = new JSONParser();
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    String email;
    String senha;
    String url;
    Context contexto;

    public ATVerificaLogin(String email, String senha, String url, Context contexto) {
        this.email = email;
        this.senha = senha;
        this.url = url;
        this.contexto = contexto;
    }

    @Override
    protected synchronized String doInBackground(String... params) {

        publishProgress("...conectando...");

        jsonObject = getResult();

        return "";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(contexto, values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String result) {
        //Update the UI
        FragLogin.setJSONObject(contexto);
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public synchronized JSONObject getResult() {

        // JSON PARSER
        HashMap<String,String> parametros = new HashMap<String,String>();
        parametros.put("email", email);
        parametros.put("senha", senha);

        // executa chamada
        return jparser.makeHttpRequest(url, "POST", parametros);
    }
}

