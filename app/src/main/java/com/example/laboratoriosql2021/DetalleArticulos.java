package com.example.laboratoriosql2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;


public class DetalleArticulos extends AppCompatActivity {
    private TextView tv_codigo, tv_descripcion, tv_precio;
    private TextView tv_codigo1, tv_descripcion1, tv_precio1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_articulos);
        tv_codigo=findViewById(R.id.tvcodigo);
        tv_descripcion=findViewById(R.id.tvdescripcion);
        tv_precio=findViewById(R.id.tvprecio);

        tv_codigo1=findViewById(R.id.tvcodigo1);
        tv_descripcion1=findViewById(R.id.tvdescripcion1);
        tv_precio1=findViewById(R.id.tvprecio1);

        Bundle objeto =getIntent().getExtras();
        Dto dto=null;
        if (objeto !=null){
            dto =(Dto)objeto.getSerializable("articulos");
            tv_codigo.setText("''"+dto.getCodigo());
            tv_descripcion.setText(dto.getdescripcion());
            tv_precio.setText(String.valueOf(dto.getprecio()));

            tv_codigo1.setText("''"+dto.getCodigo());
            tv_descripcion1.setText(dto.getdescripcion());
            tv_precio1.setText(String.valueOf(dto.getprecio()));
        }


    }
}