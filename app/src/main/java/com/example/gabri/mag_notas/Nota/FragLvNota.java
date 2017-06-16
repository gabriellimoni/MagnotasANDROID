package com.example.gabri.mag_notas.Nota;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.mag_notas.AsyncTask.ATCadastroNota;
import com.example.gabri.mag_notas.AsyncTask.ATDeleteNota;
import com.example.gabri.mag_notas.AsyncTask.ATNotaPorCategoria;
import com.example.gabri.mag_notas.AsyncTask.ATTodasNotas;
import com.example.gabri.mag_notas.Nota.Nota;
import com.example.gabri.mag_notas.Nota.NotaAdapter;
import com.example.gabri.mag_notas.R;
import com.example.gabri.mag_notas.Usuario;
import com.example.gabri.mag_notas.banco.NotasBD;
import com.example.gabri.mag_notas.banco.NotasDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragLvNota extends Fragment {
    private static ATTodasNotas att;
    private static ATNotaPorCategoria atc;

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    OpeN opeN;

    NotasDBHelper mydbHelper;
    static NotaAdapter adapter;
    TextView assunto;

    ListView lv;
    static ArrayList<Nota> notas = new ArrayList<Nota>();
    FloatingActionButton btn;

    private Spinner spn_filter_categoria;

    public FragLvNota() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_lv_notas, container, false);

        ArrayList<Nota> notas_aux = new ArrayList<Nota>();

        spn_filter_categoria = (Spinner)v.findViewById(R.id.spn_filter_categoria);
        spn_filter_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // carrega por categoria e se for a cat = 4 retorna todas
                loadByCategoria(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_filter_categoria.setSelection(4);

        ATTodasNotas att;

        mydbHelper = new NotasDBHelper(getActivity());
        SQLiteDatabase db = mydbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ NotasBD.TBLNota.TABLE_NAME, null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            // ver como faz pra pegar timestamp e formatar para hora e data: https://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp

            c.moveToNext();
        }
        notas = notas_aux;

        lv = (ListView)v.findViewById(R.id.notas_lv);
        adapter = new NotaAdapter(v.getContext(), R.layout.notas_custom, notas);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        btn = (FloatingActionButton)v.findViewById(R.id.btn_float);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opeN.openNovaNota();
            }
        });

        registerForContextMenu(lv);

        assunto = (TextView)v.findViewById(R.id.nota_texto);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.findViewById(R.id.nota_texto).getVisibility() == View.GONE)
                    view.findViewById(R.id.nota_texto).setVisibility(View.VISIBLE);
                else
                    view.findViewById(R.id.nota_texto).setVisibility(View.GONE);
            }
        });
        return v;
    }

    private void loadByCategoria(int cat) {
        if(cat < 4) {
            notas.clear();

            String url = "http://10.0.2.2/notasAndroid/php/getNotasPorCategoria.php";

            HashMap<String,String> parametros = new HashMap<String, String>();
            parametros.put("categoria", String.valueOf(cat));

            atc = (ATNotaPorCategoria) new
                                        ATNotaPorCategoria(parametros, url, getActivity()).execute();
        }
        else
            loadTodasNotas();
        adapter.notifyDataSetChanged();
    }

    private void loadTodasNotas() {
        notas.clear();

        String url = "http://10.0.2.2/notasAndroid/php/getTodasNotas.php";

        att = (ATTodasNotas) new ATTodasNotas(new HashMap<String,String>(), url, getActivity()).execute();
    }

    @Override
    public void onAttach(Context contexto){
        super.onAttach(contexto);
        if(contexto instanceof OpeN){
            // pega aimplementação da interface
            opeN = (OpeN)contexto;
        }
        else{
            throw new RuntimeException(contexto.toString()+
                    "interface não implementada na ACT!");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        opeN = null;
    }

    public static void setJSONObject(Context context) {
        JSONObject receiver = att.getJsonObject();

        // fala quem foi cadastrado
        if(receiver != null) {
            try {
                JSONArray notasJson = receiver.getJSONArray("notas");
                // se der certo passa result, apelido e email
                if (receiver.getInt("result") == 1) {
                    Log.i("SUCCESS","yes");

                    for(int i = 0; i < notasJson.length(); i++){
                        JSONObject notaJ = notasJson.getJSONObject(i);
                        Nota n = new Nota(
                                notaJ.getInt("_id"),
                                notaJ.getString("assunto"),
                                notaJ.getString("texto"),
                                formatter.parse(notaJ.getString("data")),
                                notaJ.getString("apelido"),
                                notaJ.getInt("categoria"),
                                notaJ.getInt("usu_id")
                        );

                        notas.add(n);
                    }
                    adapter.notifyDataSetChanged();



                    // se for erro envia erroMsg e result
                } else {

                    String erro = receiver.getString("erroMsg");
                    Toast.makeText(context, "Erro: " + erro + ".", Toast.LENGTH_LONG).show();

                }
            }catch (JSONException e) {

                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{

            Toast.makeText(context, "Problema com o servidor!", Toast.LENGTH_LONG).show();

        }
    }

    public static void setJSONObjectByCat(Context context) {
        JSONObject receiver = atc.getJsonObject();

        // fala quem foi cadastrado
        if(receiver != null) {
            try {
                JSONArray notasJson = receiver.getJSONArray("notas");
                // se der certo passa result, apelido e email
                if (receiver.getInt("result") == 1) {
                    Log.i("SUCCESS","yes");

                    for(int i = 0; i < notasJson.length(); i++){
                        JSONObject notaJ = notasJson.getJSONObject(i);
                        Nota n = new Nota(
                                notaJ.getInt("_id"),
                                notaJ.getString("assunto"),
                                notaJ.getString("texto"),
                                formatter.parse(notaJ.getString("data")),
                                notaJ.getString("apelido"),
                                notaJ.getInt("categoria"),
                                notaJ.getInt("usu_id")
                        );

                        notas.add(n);
                    }
                    adapter.notifyDataSetChanged();



                    // se for erro envia erroMsg e result
                } else {

                    String erro = receiver.getString("erroMsg");
                    Toast.makeText(context, "Erro: " + erro + ".", Toast.LENGTH_LONG).show();

                }
            }catch (JSONException e) {

                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{

            Toast.makeText(context, "Problema com o servidor!", Toast.LENGTH_LONG).show();

        }
    }

    public boolean isUsuario() {
        // verificar se é o próprio usuário da nota
        return true;
    }


    public interface OpeN{
        public void openNovaNota();
        public void openEditNota(Nota nota);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, v, info);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_lv, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menu){
        if(isUsuario()) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuInfo();
            if(Usuario.id == notas.get(info.position).getUsu_id()) {
                if (menu.getItemId() == R.id.ct_exc) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Excluir nota");
                    dialog.setMessage("Deseja excluir a nota selecionada?");
                    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "http://10.0.2.2/notasAndroid/php/deletaNota.php";

                            HashMap<String, String> parametros = new HashMap<String, String>();

                            parametros.put("id", String.valueOf(notas.get(info.position).getId()));

                            ATDeleteNota at = (ATDeleteNota) new ATDeleteNota(parametros, url).execute();

                            Toast.makeText(getActivity(),"Nota excluída com sucesso!", Toast.LENGTH_SHORT).show();
                            spn_filter_categoria.setSelection(4);
                        }
                    });
                    dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();

                    return true;
                }
                if (menu.getItemId() == R.id.ct_edt) {
                    opeN.openEditNota(notas.get(info.position));
                    return true;
                }
            }
            else
                Toast.makeText(getActivity(), "Você não é o proprietário da nota!", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(menu);

    }

}
