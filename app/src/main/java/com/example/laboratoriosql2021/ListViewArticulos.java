package com.example.laboratoriosql2021;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewArticulos extends AppCompatActivity {
 ListView listViewPersonas;
 ArrayAdapter adaptador;
 SearchView searchView;
 ListView listView;
 ArrayList<String>list;
 ArrayAdapter adapter;
 String[] version = {"Aestro", "Blender", "CupCake", "Donut", "Eclarir", "Froyo", "GingerBread", "HomeyComb", "Jelly Bean", "KitKat", "Lolipop", "Marshmallow", "Nought", "Oreo"};
 ConexionSqLite conexion = new ConexionSqLite(this);
 Dto datos = new Dto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_articulos);
        listViewPersonas=findViewById(R.id.listViewPersonas);
//        searchView =findViewById(R.id.searchView);
        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,conexion.consultaListaArticulos1());
        listViewPersonas.setAdapter(adaptador);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text ="";
                adaptador.getFilter().filter(text);
                return false;
            }
        });
        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion="codigo:"+ conexion.consultarListaArticulos().get(position).getDescripcion()+"\n";
                informacion+="Descripcion:"+ conexion.consultarListaArticulos().get(position).getDescripcion()+"\n";
                informacion+="Precio"+ conexion.consultarListaArticulos().get(position).getprecio()+"\n";
                Dto articulos=conexion.consultarListaArticulos().get(position);
                Intent intent = new Intent(ListViewArticulos.this, DetalleArticulos.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("articulo", articulos);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
