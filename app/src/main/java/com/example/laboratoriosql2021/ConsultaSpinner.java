package com.example.laboratoriosql2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ConsultaSpinner extends AppCompatActivity {
        private Spinner sp_options;
        private TextView tv_cod,tv_descripcion,tv_precio;

        ConexionSqLite conexion = new ConexionSqLite(this);
        Dto datos=new Dto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_spinner);
        sp_options =findViewById(R.id.sp_options);
        tv_cod=findViewById(R.id.tv_cod);
        tv_descripcion=findViewById(R.id.tvdescripcion);
        tv_precio=findViewById(R.id.tvprecio);
        conexion.consultarListaArticulos();
        ArrayAdapter<CharSequence>adaptador =new ArrayAdapter(this, android.R.layout.simple_spinner_item,conexion.obtenerListaArticulo());
        sp_options.setAdapter((SpinnerAdapter) adaptador);
        sp_options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position!=0){
                tv_cod.setText("Codigo:" +conexion.consultarListaArticulos().get(position-1).getCodigo());
                tv_descripcion.setText("Descripcion:" +conexion.consultarListaArticulos().get(position-1).getDescripcion());
                tv_precio.setText("Precio:" + String.valueOf(conexion.consultarListaArticulos().get(position-1).getprecio()));
        }else{
                tv_cod.setText("Codigo:");
                tv_descripcion.setText("Descripcion:");
                tv_precio.setText("Precio:");
            }
        }

@Override
public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        }
    }
