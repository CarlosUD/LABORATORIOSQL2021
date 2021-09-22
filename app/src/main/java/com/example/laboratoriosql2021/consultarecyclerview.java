package com.example.laboratoriosql2021;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class consultarecyclerview extends AppCompatActivity {
    private RecyclerView recycler;private AdaptadorArticulos adaptadorArticulos;
    ConexionSqLite datos = new ConexionSqLite(consultarecyclerview.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultarecyclerview);
        recycler = (RecyclerView)findViewById(R.id.rview);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptadorArticulos = new AdaptadorArticulos(consultarecyclerview.this, datos.mostrarArticulos());
        recycler.setAdapter(adaptadorArticulos);
    }
    public List<Dto> obtenerArticulos() {
        List<Dto> articulos = new ArrayList<>();
        articulos.add(new Dto(1, "Laptop", 200.99));
        articulos.add(new Dto(2, "Impresora HP", 100.78));
        articulos.add(new Dto(3, "Disco Duro 1TB", 100.19));return articulos;
    }
}