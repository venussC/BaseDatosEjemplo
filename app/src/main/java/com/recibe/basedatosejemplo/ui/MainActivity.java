package com.recibe.basedatosejemplo.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
                // Crear objeto Usuario con datos del formulario
                Usuario obj = new Usuario(MainActivity.this);
                obj.setUsuario(txtUsuario.getText().toString());
                obj.setPassword(txtPassword.getText().toString());
                obj.setNombre(txtNombre.getText().toString());

                // === CREAR o ACTUALIZAR ===
                if (txtUserID.getText().toString().trim().equals("")){
                    // === INSERTAR NUEVO USUARIO ===
                    if (obj.insertar())
                    {
                        Util.muestra_dialogo( MainActivity.this,"Se ha isnertado el usuario");
                        muestra_todos(); // Refrescar lista
                        limpia(); // Limpiar formulario
                    }
                }else {
                    // MODIFICAR USUARIO EXISTENTE ===
                    obj.setId_usuario( Integer.parseInt(txtUserID.getText().toString()));

                    if(obj.modificar())
                    {
                        Util.muestra_dialogo( MainActivity.this,"Se ha modificado el usuario");
                        muestra_todos(); // Refrescar lista
                        limpia(); // Limpiar formulario
                    }
                }




            }
        });


    }

    public void muestra_todos(){
        Usuario ojb = new Usuario(MainActivity.this);
        ArrayList<Usuario> usuarios = ojb.ListarTodos(); // Obtener todos los usuarios

        // Configurar RecyclerView con adaptador
        UsuarioAdaptador adapter = new UsuarioAdaptador(this, usuarios);
        rec_usuario.setLayoutManager(new LinearLayoutManager(this));
        rec_usuario.setAdapter(adapter);
      /*

        lista_usuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Esta seguro que desea eliminarlo");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int posicion=(int)l;
                        Usuario obj = new Usuario(MainActivity.this);
                        obj.setId_usuario(usuarios.get(posicion).getId_usuario());
                        if (obj.eliminar()) {
                            Util.muestra_dialogo( MainActivity.this, "Se ha eliminado el usuario");
                            muestra_todos();
                            limpia();
                        }

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;
            }
        });
*/
    }

    // Método para limpiar todos los campos del formulario
    public void limpia(){
        txtNombre.setText("");
        txtUserID.setText("");
        txtPassword.setText("");
        txtUsuario.setText("");
    }

}