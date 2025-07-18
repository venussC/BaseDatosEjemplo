package com.recibe.basedatosejemplo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * Adaptador para RecyclerView que maneja la lista de usuarios
 * Implementa las operaciones de edición y eliminación desde la lista
 */
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
        // === MOSTRAR DATOS EN LA VISTA ===
        holder.lblID.setText(String.valueOf(lista.get(position).getId_usuario()));
        holder.lblNombre.setText(lista.get(position).getNombre());
        holder.lblUsuario.setText(lista.get(position).getUsuario());
        holder.lblPassword.setText(lista.get(position).getPassword());

        // === EVENTO: CLIC PARA EDITAR ===
        holder.Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CORREGIDO: Usar getAdapterPosition() en lugar de position
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Cargar datos del usuario seleccionado en el formulario
                    MainActivity mainActivity = (MainActivity) context;
                    EditText txtIdusuario = mainActivity.findViewById(R.id.txtIdusuario);
                    EditText txtUsuario1 = mainActivity.findViewById(R.id.txtUsuario1);
                    EditText txtPassword1 = mainActivity.findViewById(R.id.txtPassword1);
                    EditText txtNombre = mainActivity.findViewById(R.id.txtNombre);

                    // Establecer valores en los campos - CORREGIDO: usar currentPosition
                    txtIdusuario.setText(String.valueOf(lista.get(currentPosition).getId_usuario()));
                    txtUsuario1.setText(lista.get(currentPosition).getUsuario());
                    txtPassword1.setText(lista.get(currentPosition).getPassword());
                    txtNombre.setText(lista.get(currentPosition).getNombre());
                }
            }
        });

        // === EVENTO: CLIC LARGO PARA ELIMINAR ===
        holder.Linear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // CORREGIDO: Usar getAdapterPosition() en lugar de position
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // DELETE: Eliminar usuario directamente
                    MainActivity mainActivity = (MainActivity) context;
                    Usuario obj = new Usuario(mainActivity);
                    obj.setId_usuario(lista.get(currentPosition).getId_usuario());

                    new AlertDialog.Builder(mainActivity)
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Estás seguro de que deseas eliminar este usuario?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (obj.eliminar()) {
                                        mainActivity.muestra_todos(); // Refrescar lista
                                        Util.muestra_dialogo(mainActivity, "Se ha eliminado el usuario");
                                        mainActivity.limpia();  // Limpiar formulario
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblID, lblNombre, lblUsuario, lblPassword;
        LinearLayout Linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblID = itemView.findViewById(R.id.lblID);
            lblNombre = itemView.findViewById(R.id.lblNombre);
            lblUsuario = itemView.findViewById(R.id.lblUsuario);
            lblPassword = itemView.findViewById(R.id.lblPassword);
            Linear = itemView.findViewById(R.id.Linear);
        }
    }
}