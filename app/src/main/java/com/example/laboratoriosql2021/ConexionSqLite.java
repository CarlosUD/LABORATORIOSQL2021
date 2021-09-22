package com.example.laboratoriosql2021;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import  java.util.ArrayList;
import java.util.List;

public class ConexionSqLite  extends SQLiteOpenHelper {

    ArrayList<String> listaArticulos;
    ArrayList<Dto> articuloList;

    public ConexionSqLite(Context context) {
        super(context, "administracion.db", null, 1);
    }

    public ConexionSqLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo integer not null primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists articulos");
        onCreate(db);
    }

    public SQLiteDatabase db() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }

    public boolean inserTradicional(Dto datos) {
        boolean estado = true;
        int resutado;
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getdescripcion();
            double precio = datos.getprecio();
            @SuppressLint("Recycle") Cursor fila = db().rawQuery("select codigo from articulos where codigo='" + codigo + "'", null);
            if (true == fila.moveToFirst()) {
                estado = false;
            } else {
                String sql = "insert into articulos \n" + "(codigo,descripcion,precio)\n" + "values \n" + "('" + String.valueOf(codigo) + "','" + descripcion + "','" + String.valueOf(precio) + "');";
                db().execSQL(sql);
                db().close();
                estado = true;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }

    public boolean insertardatos(Dto datos) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            registro.put("codigo", datos.getCodigo());
            registro.put("descripcion", datos.getdescripcion());
            registro.put("precio", datos.getprecio());
            Cursor fila = db().rawQuery("select codigo from articulos where codigo='" + datos.getCodigo() + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                resultado = (int) db().insert("articulos", null, registro);
                if (resultado > 0) {
                    estado = true;
                } else {
                    estado = false;
                }
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }
public boolean consultaCodigo(Dto datos){
    boolean estado = true;
    int resultado;
    SQLiteDatabase db = this.getWritableDatabase();
    try {
        int codigo = datos.getCodigo();
        Cursor fila = db.rawQuery("select codigo,descripcion,precio form articulos where codigo"+ codigo,null);
        if (fila.moveToFirst()) {
            datos.setCodigo(Integer.parseInt(fila.getString(0)));
            datos.setdescripcion(fila.getString(0));
            datos.setprecio(Integer.parseInt(fila.getString(0)));
        estado =true;
        }else{
            estado=false;
        }
        db.close();
    } catch (Exception e) {
        estado=false;
        Log.e("Error",e.toString());
    }
    return estado;
}
    public boolean consultarArticulos(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] parametros = {String.valueOf(datos.getCodigo())};
            String[] campos= { "codigo", "descripcion", "precio" };
            Cursor fila = db.query("articulos", campos, "codigo=?", parametros, null, null, null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setdescripcion(fila.getString(1));
                datos.setprecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            fila.close();
            db.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }

    public boolean consultarDescripcion(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String descripcion = datos.getdescripcion();
            Cursor fila = db.rawQuery("select codigo ,descripcion, precio from where descripcion='"+descripcion+"'",null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setdescripcion(fila.getString(1));
                datos.setprecio(Double.parseDouble(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            db.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }

    public boolean EliminarCodigo(final Context context, final Dto datos) {
        final boolean[] estadodelete = {true};
        try {
            int codigo = datos.getCodigo();
            Cursor fila = db().rawQuery("Select *from articulos codigo="+codigo , null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setdescripcion(fila.getString(1));
                datos.setprecio(Double.parseDouble(fila.getString(2)));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                /*builder.setIcon(R.drawable.ic_clear);*/
                builder.setTitle("Warnning");
                builder.setMessage("Esras seguro de borrar este registro?\n  Codigo: " + datos.getCodigo() + "\n Descripcion: " + datos.getdescripcion());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = datos.getCodigo();
                        int cant = db().delete("articulos", "codigo=" + codigo, null);
                        if (cant > 0) {
                            estadodelete[0] = true;
                            Toast.makeText(context, "Registro Eliminado Satisfactoriamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            estadodelete[0] = false;
                        }
                        db().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay Resultados Encontrados para la Busqueda Especificada", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            estadodelete[0] = false;
            Log.e("error", e.toString());
        }
        return estadodelete[0];
    }

    public boolean modificar(Dto datos) {
        boolean estado = true;
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getdescripcion();
            double precio = datos.getprecio();
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            int cant = (int) db().update("articulos", registro, "codigo=" + codigo, null);
            db().close();
            if (cant > 0) {
                estado = true;
            } else {
                estado = false;
            }
            db().close();
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }

    public ArrayList<Dto> consultarListaArticulos() {
        boolean estado = true;
        SQLiteDatabase db = this.getReadableDatabase();
        Dto articulos = null;
        ArrayList<Dto> articulosList = new ArrayList<Dto>();
        try {
            Cursor fila = db.rawQuery("select *from articulos", null);
            while (fila.moveToNext()) {
                articulos = new Dto();
                articulos.setCodigo(fila.getInt(0));
                articulos.setdescripcion(fila.getString(1));
                articulos.setprecio(fila.getDouble(2));
                articulosList.add(articulos);
                Log.i("codigo", String.valueOf(articulos.getCodigo()));
                Log.i("descripcion", String.valueOf(articulos.getdescripcion()));
                Log.i("precio", String.valueOf(articulos.getprecio()));
            }
            obtenerListaArticulo();
        } catch (Exception e) {
        }
        return articulosList;
    }

    public ArrayList<String> obtenerListaArticulo() {
        listaArticulos = new ArrayList<String>();
        listaArticulos.add("Seleccione");
        for (int i = 0; i < articuloList.size(); i++) {
            listaArticulos.add(articuloList.get(i).getCodigo() + "~" + articuloList.get(i).getdescripcion());
        }
        return listaArticulos;
    }

    public ArrayList<String> consultaListaArticulos1() {
        boolean estado = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Dto articulos = null;
        ArrayList<Dto> articulosList = new ArrayList<Dto>();
        try {
            Cursor fila = db.rawQuery("select *from articulos", null);
            articulos = new Dto();
            articulos.setCodigo(fila.getInt(0));
            articulos.setdescripcion(fila.getString(1));
            articulos.setprecio(fila.getDouble(2));
            articulosList.add(articulos);

        listaArticulos = new ArrayList<String>();
        for (int i = 0; i < articuloList.size(); i++) {
            listaArticulos.add(articuloList.get(i).getCodigo() + "~" + articuloList.get(i).getdescripcion());
        }
    } catch (Exception e) {
        }
        return listaArticulos;
    }
    public List<Dto> mostrarArticulos(){
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM articulos order by codigo desc", null);
        List<Dto> articulos = new ArrayList<>();if(cursor.moveToFirst()){do{
            articulos.add(new Dto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));
    }
    while (cursor.moveToNext());
    }
    return articulos;
    }
}
