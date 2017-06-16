package com.example.gabri.mag_notas;

/**
 * Created by gabri on 01/06/2017.
 */

public class Usuario {
    public static int id;


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario(String nome, String apelido, String email, String senha) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
    }

    private String nome, apelido, email, senha;

}
