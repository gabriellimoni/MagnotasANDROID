package com.example.gabri.mag_notas.Nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by gabri on 30/05/2017.
 */

public class Nota {
    private String assunto, texto, usuario;
    private Date data;
    private int categoria; // 0= carona 1=festa 2=academico 3=outros
    private int id;
    private int usu_id;


    public int getId() {
        return id;
    }



    public Nota(String assunto, String texto, Date data, String usuario, int categoria) {
        this.assunto = assunto;
        this.texto = texto;
        this.data = data;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public int getUsu_id() {
        return usu_id;
    }

    public Nota(int id, String assunto, String texto, Date data, String usuario, int categoria, int usu_id) {
        this.assunto = assunto;
        this.texto = texto;
        this.data = data;
        this.usuario = usuario;
        this.categoria = categoria;
        this.id = id;
        this.usu_id = usu_id;

    }

    public String getCategoria() {
        String cat = "";
        switch(categoria){
            case 0:
                cat = "Carona";
                break;
            case 1:
                cat = "Festa";
                break;
            case 2:
                cat = "Academico";
                break;
            case 3:
                cat = "Outros";
                break;
        }

        return cat;
    }

    public int getCatId(){
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }


    public String getAssunto() {
        return assunto;
    }

    public String getTexto() {
        return texto;
    }

    public String getDate(){
        SimpleDateFormat f_data = new SimpleDateFormat("dd/MM/yyyy");
        return f_data.format(data);
    }

    public String getTimeStamp(){
        SimpleDateFormat f_data_tm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return f_data_tm.format(data);
    }

    public String getHora(){
        SimpleDateFormat f_hora = new SimpleDateFormat("HH:mm:ss");
        return f_hora.format(data);
    }

    public int getDia(){
        Calendar c = new GregorianCalendar();
        c.setTime(data);
        return c.get(c.DAY_OF_WEEK);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNome() {

        return "teste";
    }
}
