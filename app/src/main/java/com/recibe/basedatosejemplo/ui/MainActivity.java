package com.recibe.basedatosejemplo.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.recibe.basedatosejemplo.R;
import com.recibe.basedatosejemplo.adaptadores.UsuarioAdaptador;
import com.recibe.basedatosejemplo.clases.Usuario;
import com.recibe.basedatosejemplo.clases.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;  // Botón para agregar/modificar usuarios
    EditText txtUserID, txtUsuario, txtPassword, txtNombre; // Campos de entrada
    RecyclerView rec_usuario; // Lista de usuarios

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

            muestra_todos(); // Muestra todos los usuarios al iniciar

            // === CREAR/ACTUALIZAR USUARIO ===
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Validar campos antes de procesar
                        if (txtUsuario.getText().toString().trim().isEmpty() ||
                                txtPassword.getText().toString().trim().isEmpty() ||
                                txtNombre.getText().toString().trim().isEmpty()) {
                            Util.muestra_dialogo(MainActivity.this, "Por favor complete todos los campos");
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

    public void muestra_todos() {
        try {
            Usuario obj = new Usuario(MainActivity.this);
            ArrayList<Usuario> usuarios = obj.ListarTodos(); // Obtener todos los usuarios

            // Configurar RecyclerView con adaptador
            UsuarioAdaptador adapter = new UsuarioAdaptador(this, usuarios);
            rec_usuario.setLayoutManager(new LinearLayoutManager(this));
            rec_usuario.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("MainActivity", "Error en muestra_todos: " + e.getMessage());
            Util.muestra_dialogo(this, "Error al cargar usuarios");
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