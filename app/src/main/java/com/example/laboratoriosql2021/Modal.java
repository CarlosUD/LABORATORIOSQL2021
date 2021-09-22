package com.example.laboratoriosql2021;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Modal {
    Dialog myDalog;
    AlertDialog.Builder dialogo;
    boolean validaInput =false;
    String codigo;
    String descripcion;
    String precio;
    SQLiteDatabase bd =null;
    Dto datos = new Dto();

    public void Search(final Context context) {
        myDalog = new Dialog(context);
        myDalog.setContentView(R.layout.ventana1);
        myDalog.setTitle("Search");
        myDalog.setCancelable(false);
        final ConexionSqLite conexion = new ConexionSqLite(context);
        final EditText et_cod = myDalog.findViewById(R.id.et_codigo);
        Button btn_buscar = myDalog.findViewById(R.id.btnbuscar);
        TextView tv_close = myDalog.findViewById(R.id.tvclose);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDalog.dismiss();
            }
        });
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_cod.getText().toString().length() == 0) {
                    validaInput = false;
                    et_cod.setError("Campo obligarorio");
                } else {
                    validaInput = true;

                }
                if (validaInput) {
                    String cod = et_cod.getText().toString();
                    datos.setCodigo(Integer.parseInt(cod));
                    if (conexion.consultaCodigo(datos)) {
                        codigo = String.valueOf(datos.getCodigo());
                        descripcion = datos.getdescripcion();
                        precio = String.valueOf(datos.getprecio());
                        String action;
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("senal", "1");
                        intent.putExtra("codigo", codigo);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("precio", precio);
                        context.startActivity(intent);
                        myDalog.dismiss();

                    } else {
                        Toast.makeText(context, "No se ha encontrado registros para la busqueda especificada", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, "No han especificado lo que desean buscar", Toast.LENGTH_SHORT).show();

                }
            }
        });
        myDalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDalog.show();
    }
}
