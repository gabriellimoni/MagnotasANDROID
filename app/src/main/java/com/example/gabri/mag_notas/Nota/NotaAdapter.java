package com.example.gabri.mag_notas.Nota;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gabri.mag_notas.Nota.Nota;
import com.example.gabri.mag_notas.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gabri on 30/05/2017.
 */

public class NotaAdapter extends ArrayAdapter {
    private ArrayList<Nota> notas;
    private Context contexto;

    public NotaAdapter(Context context, int resource, ArrayList<Nota> notas) {
        super(context, resource, notas);

        this.contexto = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = ((Activity)contexto).getLayoutInflater();
            convertView = inflater.inflate(R.layout.notas_custom, parent, false);

            holder = new ViewHolder();
            //holder.nota_img = (ImageView)convertView.findViewById(R.id.nota_img);
            holder.nota_assunto = (TextView)convertView.findViewById(R.id.nota_assunto);
            holder.nota_texto = (TextView)convertView.findViewById(R.id.nota_texto);
            holder.nota_data = (TextView)convertView.findViewById(R.id.nota_data);
            //holder.nota_hora = (TextView)convertView.findViewById(R.id.nota_hora);
            holder.nota_usuario = (TextView)convertView.findViewById(R.id.nota_usuario);
            holder.nota_categoria = (TextView)convertView.findViewById(R.id.nota_categoria);
            holder.nota_layout = (LinearLayout)convertView.findViewById(R.id.nota_layout);




            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        Nota n = notas.get(position);

        // adicionar um icone para cada categoria
        //if(n.getDia() == Calendar.SUNDAY || n.getDia() == Calendar.SATURDAY)
        //    holder.nota_img.setImageResource(R.drawable.ic_menu_manage);
        //else
        //    holder.nota_img.setImageResource(R.drawable.ic_menu_share);

        holder.nota_assunto.setText(n.getAssunto());
        holder.nota_texto.setText(n.getTexto());
        holder.nota_data.setText("Expira em: "+n.getTimeStamp());
        holder.nota_usuario.setText(n.getUsuario());
        holder.nota_categoria.setText(n.getCategoria());

        switch(n.getCatId()){
            case 0:
                holder.nota_layout.setBackgroundResource(R.color.colorCarona);
                break;
            case 1:
                holder.nota_layout.setBackgroundResource(R.color.colorFesta);
                break;
            case 2:
                holder.nota_layout.setBackgroundResource(R.color.colorAcademico);
                break;
            case 3:
                holder.nota_layout.setBackgroundResource(R.color.colorOutros);
                break;
        }

        return convertView;
    }

    private static class ViewHolder{
        //ImageView nota_img;
        TextView nota_assunto;
        TextView nota_texto;
        TextView nota_data;
        TextView nota_usuario;
        TextView nota_categoria;
        LinearLayout nota_layout;
    }
}
