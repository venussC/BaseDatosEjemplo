package com.recibe.basedatosejemplo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.recibe.basedatosejemplo.*;
import com.recibe.basedatosejemplo.clases.Usuario;
import com.recibe.basedatosejemplo.clases.Util;
import com.recibe.basedatosejemplo.ui.MainActivity;

import java.util.ArrayList;

public class UsuarioAdaptador extends RecyclerView.Adapter<UsuarioAdaptador.ViewHolder> {

    private Context context;
    private ArrayList<Usuario> lista;

    public UsuarioAdaptador(Context context, ArrayList<Usuario> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public UsuarioAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listado_usuarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioAdaptador.ViewHolder holder, int position) {
            holder.lblID.setText(String.valueOf(lista.get(position).getId_usuario()));
            holder.lblNombre.setText(lista.get(position).getNombre());
            holder.lblUsuario.setText(lista.get(position).getUsuario());
            holder.lblPassword.setText(lista.get(position).getPassword());
            holder.Linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) context;
                    EditText txtIdusuario  = mainActivity.findViewById(R.id.txtIdusuario);
                    EditText txtUsuario1  = mainActivity.findViewById(R.id.txtUsuario1);
                    EditText txtPassword1  = mainActivity.findViewById(R.id.txtPassword1);
                    EditText txtNombre  = mainActivity.findViewById(R.id.txtNombre);
                    txtIdusuario.setText(String.valueOf(lista.get(position).getId_usuario()));
                    txtUsuario1.setText(String.valueOf(lista.get(position).getUsuario()));
                    txtPassword1.setText(String.valueOf(lista.get(position).getPassword()));
                    txtNombre.setText(String.valueOf(lista.get(position).getNombre()));
                }
            });

        holder.Linear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    MainActivity mainActivity = (MainActivity) context;
                    Usuario obj = new Usuario(mainActivity);
                    obj.setId_usuario(lista.get(position).getId_usuario());
                    if (obj.eliminar()) {
                        mainActivity.muestra_todos();
                        Util.muestra_dialogo( mainActivity, "Se ha eliminado el usuario");
                        mainActivity.limpia();
                    }
                    return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView lblID, lblNombre, lblUsuario, lblPassword;
        LinearLayout Linear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblID  = itemView.findViewById(R.id.lblID);
            lblNombre  = itemView.findViewById(R.id.lblNombre);
            lblUsuario  = itemView.findViewById(R.id.lblUsuario);
            lblPassword  = itemView.findViewById(R.id.lblPassword);
            Linear = itemView.findViewById(R.id.Linear);

        }
    }
}
