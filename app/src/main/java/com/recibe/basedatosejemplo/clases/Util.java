package com.recibe.basedatosejemplo.clases;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Util {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String cifrar(String valor){
        return Base64.getEncoder().encodeToString(valor.getBytes());
    }



    public static void muestra_dialogo(Activity activity, String Texto){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("informacion");
        builder.setMessage(Texto);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
