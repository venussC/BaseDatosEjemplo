package com.recibe.basedatosejemplo.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// ===== MIHELPERSQLLITE.JAVA =====
/**
 * Helper para la gestión de la base de datos SQLite
 * Extiende SQLiteOpenHelper para crear y actualizar la base de datos
 */
public class MiHelperSQLLite  extends SQLiteOpenHelper {
    public MiHelperSQLLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Crear la estructura de la base de datos
     * Se ejecuta la primera vez que se accede a la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Crear tabla usuario con campos necesarios
        sqLiteDatabase.execSQL("create table usuario (id_usuario INTEGER primary key AUTOINCREMENT, usuario text, password text, nombre text)");
    }

    /**
     * Actualizar la estructura de la base de datos
     * Se ejecuta cuando se cambia la versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
