package com.example.gabri.mag_notas.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gabri.mag_notas.Login.FragCadastro;
import com.example.gabri.mag_notas.Nota.FragCadastroNota;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gabri on 06/06/2017.
 */

public class ATCadastroNota extends AsyncTask<String, String, String> {
    // recebe msg php e converte para json
    JSONParser jparser = new JSONParser();
    JSONObject jsonObject = new JSONObject();

    FragCadastroNota.ItfNota itf;

    HashMap<String,String> parametros = new HashMap<String,String>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    Usuario usu;
    String url;
    Context contexto;

    public ATCadastroNota(HashMap<String,String> parametros, String url, Context contexto, FragCadastroNota.ItfNota itf) {
        this.parametros = parametros;
        this.url = url;
        this.contexto = contexto;
        this.itf = itf;
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
        FragCadastroNota.setJSONObject(contexto, itf);
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public synchronized JSONObject getResult() {

        // executa chamada
        return jparser.makeHttpRequest(url, "POST", parametros);
    }



}
