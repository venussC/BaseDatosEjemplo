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

    Button btnAgregar;
    EditText txtUserID, txtUsuario, txtPassword, txtNombre;
    RecyclerView rec_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgregar = findViewById(R.id.btnAgregar);
        txtUserID = findViewById(R.id.txtIdusuario);
        txtUsuario = findViewById(R.id.txtUsuario1);
        txtPassword = findViewById(R.id.txtPassword1);
        txtNombre = findViewById(R.id.txtNombre);
        rec_usuario = findViewById(R.id.rec_usuario);

        muestra_todos();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario obj = new Usuario(MainActivity.this);
                obj.setUsuario(txtUsuario.getText().toString());
                obj.setPassword(txtPassword.getText().toString());
                obj.setNombre(txtNombre.getText().toString());
                if (txtUserID.getText().toString().trim().equals("")){
                    if (obj.insertar())
                    {
                        Util.muestra_dialogo( MainActivity.this,"Se ha isnertado el usuario");
                        muestra_todos();
                        limpia();
                    }
                }else {
                    obj.setId_usuario( Integer.parseInt(txtUserID.getText().toString()));

                    if(obj.modificar())
                    {
                        Util.muestra_dialogo( MainActivity.this,"Se ha modificado el usuario");
                        muestra_todos();
                        limpia();
                    }
                }




            }
        });


    }


    public void muestra_todos(){
        Usuario ojb = new Usuario(MainActivity.this);
        ArrayList<Usuario> usuarios = ojb.ListarTodos();

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



    public void limpia(){
        txtNombre.setText("");
        txtUserID.setText("");
        txtPassword.setText("");
        txtUsuario.setText("");
    }

}