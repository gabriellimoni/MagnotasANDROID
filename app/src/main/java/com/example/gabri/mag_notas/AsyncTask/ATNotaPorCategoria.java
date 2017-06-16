package com.example.gabri.mag_notas.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gabri.mag_notas.Nota.FragLvNota;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gabri on 16/06/2017.
 */

public class ATNotaPorCategoria extends AsyncTask<String, String, String>{

    // recebe msg php e converte para json
    JSONParser jparser = new JSONParser();
    JSONObject jsonObject = new JSONObject();


    HashMap<String,String> parametros = new HashMap<String,String>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    Usuario usu;
    String url;
    Context contexto;

    public ATNotaPorCategoria(HashMap<String,String> parametros, String url, Context contexto) {
        this.parametros = parametros;
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
        //Toast.makeText(contexto, values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String result) {
        //Update the UI
        FragLvNota.setJSONObjectByCat(contexto);
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public synchronized JSONObject getResult() {

        // executa chamada
        return jparser.makeHttpRequest(url, "POST", parametros);
    }

}
