package com.example.laboratoriosql2021;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etcodigo, etdescripcion, etprecio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_Eliminar, btn_actualizar;
    private TextView tv_resultado;

    boolean inputEt = false;
    boolean inputEd = false;
    boolean input1 = false;
    int resultadoInsert = 0;

    Modal ventanas = new Modal();
    ConexionSqLite conexion = new ConexionSqLite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        finish();finishAffinity();
                        }
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);
                    Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
                    toolbar.setTitleTextColor(getResources().getColor(R.color.cardview_shadow_start_color));
                    toolbar.setTitleMargin(0, 0, 0, 0);
                    toolbar.setSubtitle("CRUD SQLITE-2021");
                    toolbar.setSubtitleTextColor(getResources().getColor(R.color.cardview_shadow_start_color));
                    toolbar.setTitle("Carlos Eduardo Del Cid");
                    setSupportActionBar(toolbar);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmation();
                        }
                    });
                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ventanas.Search(MainActivity.this);
                        }
                    });
                    etcodigo = findViewById(R.id.et_codigo);
                    etdescripcion = findViewById(R.id.et_descripcion);
                    etprecio = findViewById(R.id.et_precio);
                    btn_guardar = findViewById(R.id.btn_guardar);
                    btn_consultar1 = findViewById(R.id.btn_consultar1);
                    btn_consultar2 = findViewById(R.id.btn_consultar2);
                    btn_Eliminar = findViewById(R.id.btn_Eliminar);
                    btn_actualizar = findViewById(R.id.btn_actualizar);

                    String senal = "''";
                    String codigo = "''";
                    String descripcion = "''";
                    String precio = "''";
                    try {
                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            codigo = bundle.getString("codigo");
                            senal = bundle.getString("senal");
                            descripcion = bundle.getString("descripcion");
                            precio = bundle.getString("precio");
                        }
                        if (senal.equals("1")) {
                            etcodigo.setText(codigo);
                            etdescripcion.setText(descripcion);
                            etprecio.setText(precio);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                private void confirmation() {
                    String mensaje = "¿Realmente desea salir?";
                    dialogo = new AlertDialog.Builder(MainActivity.this);
                    dialogo.setIcon(R.drawable.ic_close);
                    dialogo.setTitle("Warning");
                    dialogo.setMessage(mensaje);
                    dialogo.setCancelable(false);
                    dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    });
                    dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialogo.show();
                }

                @Override
                public boolean onCreateOptionsMenu(Menu menu) {
                    getMenuInflater().inflate(R.menu.menu_main, menu);
                    return true;

                }

                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.action_limpiar) {
                        etcodigo.setText(null);
                        etdescripcion.setText(null);
                        etprecio.setText(null);
                        return true;
                    } else if (id == R.id.action_listaArticulos) {
                        Intent spinnerActivity = new Intent(MainActivity.this, ConsultaSpinner.class);
                        startActivity(spinnerActivity);
                        return true;
                    } else if (id == R.id.action_listaArticulos1) {
                        Intent listViewActivity = new Intent(MainActivity.this, ListViewArticulos.class);
                        startActivity(listViewActivity);
                        return true;
                    }
                    return super.onOptionsItemSelected(item);
                }

                public void alta(View v) {
                    if (etcodigo.getText().toString().length() == 0) {
                        etcodigo.setError("Campo Obligatorio");
                        inputEt = false;

                    } else {
                        inputEt = true;
                    }
                    if (etdescripcion.getText().toString().length() == 0) {
                        etdescripcion.setError("Campo Obligatorio");
                        inputEd = false;

                    } else {
                        inputEd = true;
                    }
                    if (etprecio.getText().toString().length() == 0) {
                        etprecio.setError("Campo Obligatorio");

                    }
                    input1 = false;
                    if(inputEt &&inputEd &&input1){
                        try {
                            datos.setCodigo(Integer.parseInt(etcodigo.getText().toString()));
                            datos.setdescripcion(etdescripcion.getText().toString());
                            datos.setprecio(Double.parseDouble(etprecio.getText().toString()));
                            if (conexion.inserTradicional(datos)) {
                                Toast.makeText(this, "Registro agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error Ya existe un registro \n" + "Codigo: " + etcodigo.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                            limpiarDatos();

                        } catch (Exception e){
                            Toast.makeText(this, "Error. Ya Existe.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                private void limpiarDatos() {
                    etcodigo.setText(null);
                    etdescripcion.setText(null);
                    etprecio.setText(null);
                    etcodigo.requestFocus();

                }

                public void consultaporcodigo(View v) {
                    if (etcodigo.getText().toString().length() == 0) {
                        etcodigo.setError("Campo Obligatorio");
                        inputEt = false;

                    } else {
                        inputEt = true;
                    }if (inputEt){
                        String codigo =etcodigo.getText().toString();
                        datos.setCodigo(Integer.parseInt(codigo));
                        if (conexion.consultarArticulos(datos)){
                            etdescripcion.setText(datos.getdescripcion());
                            etprecio.setText("''"+datos.getprecio());
                        }else{
                            Toast.makeText(this, "No existe un articulos con dicho codigo", Toast.LENGTH_SHORT).show();
                            limpiarDatos();
                        }
                    }else {
                        Toast.makeText(this, "Ingrese el codigo del articulo a buscar.", Toast.LENGTH_SHORT).show();
                    }
                }

                public void consultapordescripcion(View v) {
                    if (etdescripcion.getText().toString().length() == 0) {
                        etdescripcion.setError("Campo Obligatorio");
                        inputEd = false;

                    } else {
                        inputEd = true;
                    }if (inputEd){
                        String descripcion =etdescripcion.getText().toString();
                        datos.setCodigo(Integer.parseInt(descripcion));
                        if (conexion.consultarDescripcion(datos)){
                            etcodigo.setText("''"+datos.getCodigo());
                            etdescripcion.setText(datos.getdescripcion());
                            etprecio.setText("''"+datos.getprecio());
                        }else{
                            Toast.makeText(this, "No existe un articulos con dicho codigo", Toast.LENGTH_SHORT).show();
                            limpiarDatos();
                        }
                    }else {
                        Toast.makeText(this, "Ingrese el codigo del articulo a buscar.", Toast.LENGTH_SHORT).show();
                    }
                }

                public void bajaporcodigo(View v) {
                    if (etcodigo.getText().toString().length() == 0) {
                        etcodigo.setError("Campo Obligatorio");
                        inputEt = false;

                    } else {
                        inputEt = true;
                    }if (inputEt){
                        String codigo =etcodigo.getText().toString();
                        datos.setCodigo(Integer.parseInt(codigo));
                        if (!conexion.EliminarCodigo(MainActivity.this, datos)) {
                            Toast.makeText(this, "No existe un articulos con dicho codigo", Toast.LENGTH_SHORT).show();
                        }
                        limpiarDatos();
                    }
                }

                public void modificacion(View v) {
                    if (etcodigo.getText().toString().length() == 0) {
                        etcodigo.setError("Campo Obligatorio");
                        inputEt = false;

                    } else {
                        inputEt = true;
                    }if (inputEt){
                        String codigo =etcodigo.getText().toString();
                        String descripcion = etdescripcion.getText().toString();
                        double precio =Double.parseDouble(etprecio.getText().toString());

                        datos.setCodigo(Integer.parseInt(codigo));
                        datos.setdescripcion(descripcion);
                        datos.setprecio(Double.parseDouble(String.valueOf(precio)));

                        if (conexion.modificar(datos)){
                            Toast.makeText(this, "Registro Modificado Correctamente.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "No se han encontrado resultado para la busqueda especificada", Toast.LENGTH_SHORT).show();

                        }
                    }
             }
        }
