package com.example.gabri.mag_notas.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gabri.mag_notas.Nota.Nota;

/**
 * Created by gabri on 30/05/2017.
 */

public class NotasDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "notasdb";

    public NotasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_USUARIO);
        db.execSQL(SQL_NOTAS);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario;");
        db.execSQL("DROP TABLE nota;");
        onCreate(db);
    }


    private static final String SQL_NOTAS = "CREATE TABLE "+NotasBD.TBLNota.TABLE_NAME+"( "
            + NotasBD.TBLNota._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + NotasBD.TBLNota.COLUMN_ASSUNTO +" VARCHAR(250) NOT NULL, "
            + NotasBD.TBLNota.COLUMN_TEXTO +" VARCHAR(250) NOT NULL, "
            + NotasBD.TBLNota.COLUMN_USUARIO +" VARCHAR(250) NOT NULL, "
            + NotasBD.TBLNota.COLUMN_CATEGORIA +" INTEGER NOT NULL, "
            //+ NotasBD.TBLNota.COLUMN_DATA_INSERIDA +" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + NotasBD.TBLNota.COLUMN_DATA_EXPIRA +" TIMESTAMP NOT NULL"
            //+ " FOREIGN KEY ("+ NotasBD.TBLNota.COLUMN_USUARIO+") REFERENCES "
            //+ NotasBD.TBLUsuario.TABLE_NAME+"("+ NotasBD.TBLUsuario._ID+")"
            +");";


/*    private static final String SQL_USUARIO = "CREATE TABLE "+NotasBD.TBLUsuario.TABLE_NAME+"( "
            + NotasBD.TBLNota._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + NotasBD.TBLUsuario.COLUMN_NOME +" VARCHAR(50) NOT NULL, "
            + NotasBD.TBLUsuario.COLUMN_APELIDO +" VARCHAR(12) NOT NULL, "
            + NotasBD.TBLUsuario.COLUMN_SENHA +" VARCHAR(250) NOT NULL,"
            + NotasBD.TBLUsuario.COLUMN_EMAIL +" VARCHAR(250) NOT NULL"
            +");";
*/

}
