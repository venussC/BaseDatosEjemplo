package com.recibe.basedatosejemplo.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.recibe.basedatosejemplo.R;
import com.recibe.basedatosejemplo.adaptadores.UsuarioAdaptador;
import com.recibe.basedatosejemplo.clases.Usuario;
import com.recibe.basedatosejemplo.clases.Util;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.app.Dialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;  // Botón para agregar/modificar usuarios
    EditText txtUserID, txtUsuario, txtPassword, txtNombre; // Campos de entrada
    RecyclerView rec_usuario; // Lista de usuarios
    ProgressBar progressBar;
    private AlertDialog dialogoCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // === INICIALIZACIÓN DE COMPONENTES ===
            btnAgregar = findViewById(R.id.btnAgregar);
            txtUserID = findViewById(R.id.txtIdusuario);
            txtUsuario = findViewById(R.id.txtUsuario1);
            txtPassword = findViewById(R.id.txtPassword1);
            txtNombre = findViewById(R.id.txtNombre);
            rec_usuario = findViewById(R.id.rec_usuario);
            progressBar = findViewById(R.id.progressBar);


            muestra_todos(); // Muestra todos los usuarios al iniciar

            // === CREAR/ACTUALIZAR USUARIO ===
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Validar campos antes de procesar
                        if (txtUsuario.getText().toString().trim().isEmpty()) {
                            txtUsuario.setError("Campo obligatorio");
                            txtUsuario.requestFocus();
                            return;
                        }
                        if (txtPassword.getText().toString().trim().isEmpty()) {
                            txtPassword.setError("Campo obligatorio");
                            txtPassword.requestFocus();
                            return;
                        }
                        if (txtNombre.getText().toString().trim().isEmpty()) {
                            txtNombre.setError("Campo obligatorio");
                            txtNombre.requestFocus();
                            return;
                        }

                        // Crear objeto Usuario con datos del formulario
                        Usuario obj = new Usuario(MainActivity.this);
                        obj.setUsuario(txtUsuario.getText().toString().trim());
                        obj.setPassword(txtPassword.getText().toString().trim());
                        obj.setNombre(txtNombre.getText().toString().trim());

                        // === CREAR o ACTUALIZAR ===
                        if (txtUserID.getText().toString().trim().equals("")) {
                            // === INSERTAR NUEVO USUARIO ===
                            if (obj.insertar()) {
                                Util.muestra_dialogo(MainActivity.this, "Se ha insertado el usuario");
                                muestra_todos(); // Refrescar lista
                                limpia(); // Limpiar formulario
                            }
                        } else {
                            // === MODIFICAR USUARIO EXISTENTE ===
                            obj.setId_usuario(Integer.parseInt(txtUserID.getText().toString().trim()));

                            if (obj.modificar()) {
                                Util.muestra_dialogo(MainActivity.this, "Se ha modificado el usuario");
                                muestra_todos(); // Refrescar lista
                                limpia(); // Limpiar formulario
                            }
                        }
                    } catch (Exception e) {
                        Util.muestra_dialogo(MainActivity.this, "Error al procesar usuario: " + e.getMessage());
                        Log.e("MainActivity", "Error en onClick: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.e("MainActivity", "Error en onCreate: " + e.getMessage());
            Util.muestra_dialogo(this, "Error al inicializar la aplicación");
        }
    }


    private void mostrarDialogoCarga() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.dialogo_cargando, null);
        builder.setView(vista);
        builder.setCancelable(false);
        dialogoCarga = builder.create();
        dialogoCarga.show();
    }

    private void ocultarDialogoCarga() {
        if (dialogoCarga != null && dialogoCarga.isShowing()) {
            dialogoCarga.dismiss();
        }
    }
    public void muestra_todos() {
        try {
            mostrarDialogoCarga(); // ⏳ Mostrar cargador

            // Retrasar la carga para que el diálogo sea visible
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Usuario obj = new Usuario(MainActivity.this);
                        ArrayList<Usuario> usuarios = obj.ListarTodos();

                        UsuarioAdaptador adapter = new UsuarioAdaptador(MainActivity.this, usuarios);
                        rec_usuario.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        rec_usuario.setAdapter(adapter);
                    } catch (Exception e) {
                        Util.muestra_dialogo(MainActivity.this, "Error al cargar usuarios");
                    } finally {
                        ocultarDialogoCarga();
                    }
                }
            }, 800);

        } catch (Exception e) {
            Util.muestra_dialogo(this, "Error al mostrar usuarios");
        }
    }

    // Método para limpiar todos los campos del formulario
    public void limpia() {
        txtNombre.setText("");
        txtUserID.setText("");
        txtPassword.setText("");
        txtUsuario.setText("");
    }
}