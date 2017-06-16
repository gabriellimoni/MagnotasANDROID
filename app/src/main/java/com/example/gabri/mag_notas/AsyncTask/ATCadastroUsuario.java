package com.example.gabri.mag_notas.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gabri.mag_notas.Login.FragCadastro;
import com.example.gabri.mag_notas.Usuario;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gabri on 01/06/2017.
 */

public class ATCadastroUsuario extends AsyncTask<String, String, String> {

    // recebe msg php e converte para json
    JSONParser jparser = new JSONParser();
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    Usuario usu;
    String url;
    Context contexto;
    FragCadastro.ItfCadastro itf;

    public ATCadastroUsuario(Usuario usu, String url, Context contexto, FragCadastro.ItfCadastro itf) {
        this.usu = usu;
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
        FragCadastro.setJSONObject(contexto, itf);
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public synchronized JSONObject getResult() {

        // JSON PARSER
        HashMap<String,String> parametros = new HashMap<String,String>();
        parametros.put("nome", usu.getNome());
        parametros.put("nick", usu.getApelido());
        parametros.put("senha", usu.getSenha());
        parametros.put("email", usu.getEmail());



        // executa chamada
        return jparser.makeHttpRequest(url, "POST", parametros);
    }
}
