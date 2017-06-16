package com.example.gabri.mag_notas.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;


public class ATDeleteNota extends AsyncTask<String, String, String> {

    // recebe msg php e converte para json
    JSONParser jparser = new JSONParser();

    HashMap<String,String> parametros = new HashMap<String,String>();


    private String url = "";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public ATDeleteNota(HashMap<String,String> parametros, String url) {
        this.url = url;
        this.parametros = parametros;
    }

    @Override
    protected synchronized String doInBackground(String... params) {

        getResult();

        return "";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        //Update the UI
    }

    public synchronized JSONObject getResult() {

        // executa chamada
        return jparser.makeHttpRequest(url, "POST", parametros);
    }

}
