package com.example.gabri.mag_notas.Nota;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabri.mag_notas.Login.LoginActivity;
import com.example.gabri.mag_notas.R;
import com.example.gabri.mag_notas.Usuario;
import com.example.gabri.mag_notas.banco.NotasBD;
import com.example.gabri.mag_notas.banco.NotasDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragLvNota.OpeN, FragCadastroNota.ItfNota{

    ArrayList<Nota> notas = new ArrayList<Nota>();
    NavigationView navigationView;

    TextView tx_apelido;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fm = getSupportFragmentManager();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        View navHeaderView= navigationView.getHeaderView(0);
        tx_apelido = (TextView)navHeaderView.findViewById(R.id.navUserApelido);
        tx_apelido.setText(preferences.getString("apelido", "anônimo"));



        FragmentTransaction ft = fm.beginTransaction();
        FragLvNota frag = new FragLvNota();
        ft.replace(R.id.frag_container, frag).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Fazer Logout");
            dialog.setMessage("Deseja deslogar do app?");
            dialog.setIcon(R.drawable.ic_menu_send);
            dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
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

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = fm.beginTransaction();

        if (id == R.id.nav_notas) {
            navigationView.getMenu().getItem(0).setChecked(true);

            if(fm.findFragmentByTag("lista_notas") == null) {

                FragLvNota frag = new FragLvNota();
                ft.replace(R.id.frag_container, frag).commit();

            }
            else{

                ft.replace(R.id.frag_container, fm.findFragmentByTag("lista_notas")).commit();

            }
        } else if (id == R.id.nav_new_notas) {
            navigationView.getMenu().getItem(1).setChecked(true);

            if(fm.findFragmentByTag("cadastro_notas") == null) {

                FragCadastroNota frag = new FragCadastroNota();
                ft.replace(R.id.frag_container,frag).addToBackStack("cadastro_notas").commit();

            }
            else{

                ft.replace(R.id.frag_container, fm.findFragmentByTag("cadastro_notas")).commit();

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void sendNota(Nota nota) {
        //Nota n = new Nota(assunto, texto, new Date());
        //notas.add(n);

        NotasDBHelper mdbHelper = new NotasDBHelper(this);
        SQLiteDatabase db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotasBD.TBLNota.COLUMN_ASSUNTO, nota.getAssunto());
        values.put(NotasBD.TBLNota.COLUMN_TEXTO, nota.getTexto());
        values.put(NotasBD.TBLNota.COLUMN_DATA_INSERIDA, nota.getTimeStamp());
        values.put(NotasBD.TBLNota.COLUMN_USUARIO, nota.getUsuario());
        values.put(NotasBD.TBLNota.COLUMN_CATEGORIA, nota.getCatId());
        //Log.i("NAO INSERIU", "NAO INSERIU");
        db.insert(NotasBD.TBLNota.TABLE_NAME, null, values);
        //Log.i("INSERIU", "INSERIU");

        navigationView.getMenu().performIdentifierAction(R.id.nav_notas, 0);
        //onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void updtNota(Nota nota) {
        //Nota n = new Nota(assunto, texto, new Date(), id);
        //notas.remove(id);
        //notas.add(n);

        NotasDBHelper mdbHelper = new NotasDBHelper(this);
        SQLiteDatabase db = mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotasBD.TBLNota.COLUMN_ASSUNTO, nota.getAssunto());
        values.put(NotasBD.TBLNota.COLUMN_TEXTO, nota.getTexto());
        values.put(NotasBD.TBLNota.COLUMN_DATA_INSERIDA, nota.getTimeStamp());
        values.put(NotasBD.TBLNota.COLUMN_USUARIO, nota.getUsuario());
        values.put(NotasBD.TBLNota.COLUMN_CATEGORIA, nota.getCatId());


        navigationView.getMenu().performIdentifierAction(R.id.nav_notas, 0);
    }

    @Override
    public void abreNotas() {
        navigationView.getMenu().performIdentifierAction(R.id.nav_notas, 0);
    }

    @Override
    public void openNovaNota(){
        navigationView.getMenu().performIdentifierAction(R.id.nav_new_notas, 0);
    }

    @Override
    public void openEditNota(Nota nota) {
        FragmentTransaction ft = fm.beginTransaction();
        navigationView.getMenu().getItem(1).setChecked(true);

        if(fm.findFragmentByTag("cadastro_notas") == null) {

            FragCadastroNota frag = new FragCadastroNota(nota.getAssunto(), nota.getTexto(), nota.getId(), nota.getCatId());
            ft.replace(R.id.frag_container,frag).addToBackStack("cadastro_notas").commit();

        }
        else{

            ft.replace(R.id.frag_container, fm.findFragmentByTag("cadastro_notas")).commit();

        }
    }
}
