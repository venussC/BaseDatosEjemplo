package com.recibe.basedatosejemplo.clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

// ===== MIHELPERSQLLITE.JAVA =====
/**
 * Helper para la gestión de la base de datos SQLite
 * Extiende SQLiteOpenHelper para crear y actualizar la base de datos
 */
public class MiHelperSQLLite extends SQLiteOpenHelper {

    private static final String TAG = "MiHelperSQLLite";

    public MiHelperSQLLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Crear la estructura de la base de datos
     * Se ejecuta la primera vez que se accede a la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            Log.d(TAG, "Creando tabla usuario...");
            // Crear tabla usuario con campos necesarios
            String createTable = "CREATE TABLE usuario (" +
                    "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "salt TEXT, " +
                    "nombre TEXT NOT NULL)";

            sqLiteDatabase.execSQL(createTable);
            Log.d(TAG, "Tabla usuario creada exitosamente");

        } catch (Exception e) {
            Log.e(TAG, "Error al crear tabla: " + e.getMessage());
        }
    }

    /**
     * Actualizar la estructura de la base de datos
     * Se ejecuta cuando se cambia la versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            Log.d(TAG, "Actualizando base de datos de versión " + oldVersion + " a " + newVersion);

            if (oldVersion < 2) {
                // Verificar si la columna salt ya existe
                Cursor cursor = sqLiteDatabase.rawQuery("PRAGMA table_info(usuario)", null);
                boolean saltExists = false;

                while (cursor.moveToNext()) {
                    String columnName = cursor.getString(1);
                    if ("salt".equals(columnName)) {
                        saltExists = true;
                        break;
                    }
                }
                cursor.close();

                if (!saltExists) {
                    sqLiteDatabase.execSQL("ALTER TABLE usuario ADD COLUMN salt TEXT");
                    Log.d(TAG, "Columna salt agregada");
                } else {
                    Log.d(TAG, "Columna salt ya existe");
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error en onUpgrade: " + e.getMessage());
            // En caso de error, recrear la tabla
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuario");
            onCreate(sqLiteDatabase);
        }
    }
}