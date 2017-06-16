package com.example.gabri.mag_notas.Login;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gabri.mag_notas.R;

public class LoginActivity extends AppCompatActivity implements FragLogin.ItfLogin, FragCadastro.ItfCadastro{
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        FragLogin frag = new FragLogin();
        ft.replace(R.id.container_login,frag);
        ft.commit();
    }


    @Override
    public void abreCadastro() {
        FragmentTransaction ft = fm.beginTransaction();
        if(fm.findFragmentByTag("cadastro_login") == null) {
            FragCadastro frag = new FragCadastro();
            ft.replace(R.id.container_login, frag, "cadastro_login").addToBackStack("cadastro_login");
            ft.commit();
        }
        else{
            ft.replace(R.id.container_login, fm.findFragmentByTag("cadastro_login")).addToBackStack("cadastro_login");
            ft.commit();
        }
    }

    @Override
    public void abreLogin() {

        onBackPressed();

    }
}
