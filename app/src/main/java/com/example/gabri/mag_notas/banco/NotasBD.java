package com.example.gabri.mag_notas.banco;

import android.provider.BaseColumns;

/**
 * Created by gabri on 30/05/2017.
 */

public class NotasBD {
    private NotasBD() {}

    public static class TBLNota implements BaseColumns {
        public static final String TABLE_NAME = "nota";
        public static final String COLUMN_ASSUNTO = "n_assunto";
        public static final String COLUMN_TEXTO = "n_texto";
        public static final String COLUMN_DATA_INSERIDA = "n_data_inserida";
        public static final String COLUMN_DATA_EXPIRA= "n_data_expira";
        public static final String COLUMN_USUARIO = "n_usu";
        public static final String COLUMN_CATEGORIA = "n_categoria";
    }

    /*
    public static class TBLUsuario implements  BaseColumns{
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_NOME = "u_nome";
        public static final String COLUMN_SENHA = "u_senha";
        public static final String COLUMN_APELIDO = "u_apelido";
        public static final String COLUMN_EMAIL = "u_email";
    }
    */
}
