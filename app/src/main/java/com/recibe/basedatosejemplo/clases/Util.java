package com.recibe.basedatosejemplo.clases;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

// ===== UTIL.JAVA =====
/**
 * Clase de utilidades generales
 * Contiene métodos auxiliares para cifrado y diálogos
 */
public class Util {

    // CIFRADO: Cifrar contraseña usando Base64

    public static String cifrar(String password, String salt){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String passwordConSalt = password + salt;
            byte[] hash = digest.digest(passwordConSalt.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash SHA-256", e);
        }
    }

    // INTERFAZ: Mostrar diálogo de información al usuario
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

//verifica la contraseña que se ingreso con el hash almacenado

    public static boolean verificarPassword(String passwordIngresado, String hashAlmacenado, String salt) {
        String hashIngresado = cifrar(passwordIngresado, salt);
        return hashIngresado.equals(hashAlmacenado);
    }

    //Adición del SALT

    public static String generarsalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return android.util.Base64.encodeToString(salt, android.util.Base64.DEFAULT);
    }
}
