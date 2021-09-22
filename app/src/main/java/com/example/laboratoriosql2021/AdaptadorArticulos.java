package com.example.laboratoriosql2021;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import android.os.Bundle;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticulosViewHolder>{

    private  List<Dto> articulosList;
    private  Context mCtx;

    public AdaptadorArticulos(consultarecyclerview consultarecyclerview, List<Dto> mostrarArticulos) {

    }

    @NonNull
    @Override
    public ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_recyclerview, null);
        return new ArticulosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorArticulos.ArticulosViewHolder holder, int position) {
        Dto dto = articulosList.get(position);
        holder.textViewCodigo1.setText(String.valueOf(dto.getCodigo()));
        holder.textViewDescripcion1.setText((Integer) dto.getDescripcion());
        holder.textViewPrecio1.setText(String.valueOf(dto.getprecio()));
        holder.textViewOtro.setText(String.valueOf("Registro #:" + (position+1)) + "/" + getItemCount());
    }

    @Override
    public int getItemCount() {
        return articulosList.size();
    }

    public class ArticulosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCodigo1, textViewDescripcion1, textViewPrecio1, textViewOtro;
        public ArticulosViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCodigo1 = itemView.findViewById(R.id.textViewCodigo1);
            textViewDescripcion1 = itemView.findViewById(R.id.textViewDescripcion1);
            textViewPrecio1= itemView.findViewById(R.id.textViewPrecio1);
            textViewOtro = itemView.findViewById(R.id.textViewOtro);
        }

    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}