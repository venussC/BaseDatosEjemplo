package com.recibe.basedatosejemplo.clases;



        import android.app.Activity;
        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

        import java.util.ArrayList;

// ===== USUARIO.JAVA =====
/**
 * Modelo de datos Usuario
 * Implementa todas las operaciones CRUD para la tabla usuario
 *
 * OPERACIONES CRUD:
 * - CREATE: insertar()
 * - READ: ListarTodos()
 * - UPDATE: modificar()
 * - DELETE: eliminar()
 *
 * OPERACIONES ADICIONALES:
 * - buscar(): Búsqueda específica
 * - verificar_usuario(): Autenticación
 */
public class Usuario {
    // === PROPIEDADES DEL MODELO ===
    private int id_usuario;
    private String usuario;
    private String password;
    private String nombre;
    private String salt;

    // === COMPONENTES DE BASE DE DATOS ===
    MiHelperSQLLite admin;
    SQLiteDatabase db;
    String BaseDatos = "basededatos.db";

    // Constructor vacío
    public Usuario() {
    }

    /**
     * Constructor con contexto para inicializar base de datos
     */
    public Usuario(Activity activity2) {
        admin = new MiHelperSQLLite(activity2, BaseDatos, null, 2); // Versión 2
        db = admin.getWritableDatabase();
    }

    /**
     * Constructor con todos los parámetros
     */
    public Usuario(int id_usuario, String usuario, String password, String nombre) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
    }

    // === GETTERS Y SETTERS ===
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // === OPERACIONES CRUD ===

    // CREATE: Insertar nuevo usuario en la base de datos
    public boolean insertar() {
        try {
            ContentValues registro = new ContentValues();
            String saltGenerado = Util.generarsalt();
            String passwordHasheado = Util.cifrar(this.password, saltGenerado);

            registro.put("usuario", this.usuario);
            registro.put("password", passwordHasheado);
            registro.put("salt", saltGenerado);
            registro.put("nombre", this.nombre);

            long resultado = db.insert("usuario", null, registro);
            db.close();
            return resultado != -1;
        } catch (Exception e) {
            Log.e("Usuario", "Error al insertar: " + e.getMessage());
            if (db != null && db.isOpen()) {
                db.close();
            }
            return false;
        }
    }

    // UPDATE: Modificar usuario existente
    public boolean modificar() {
        try {
            ContentValues registro = new ContentValues();
            String saltGenerado = Util.generarsalt();
            String passwordHasheado = Util.cifrar(this.password, saltGenerado);

            registro.put("usuario", this.usuario);
            registro.put("password", passwordHasheado);
            registro.put("salt", saltGenerado);
            registro.put("nombre", this.nombre);

            int filasAfectadas = db.update("usuario", registro, "id_usuario=?", new String[]{String.valueOf(this.id_usuario)});
            db.close();
            return filasAfectadas > 0;
        } catch (Exception e) {
            Log.e("Usuario", "Error al modificar: " + e.getMessage());
            if (db != null && db.isOpen()) {
                db.close();
            }
            return false;
        }
    }

    // DELETE: Eliminar usuario de la base de datos
    public boolean eliminar() {
        try {
            int filasAfectadas = db.delete("usuario", "id_usuario=?", new String[]{String.valueOf(this.id_usuario)});
            db.close();
            return filasAfectadas > 0;
        } catch (Exception e) {
            Log.e("Usuario", "Error al eliminar: " + e.getMessage());
            if (db != null && db.isOpen()) {
                db.close();
            }
            return false;
        }
    }

    // READ: Obtener todos los usuarios de la base de datos
    public ArrayList<Usuario> ListarTodos() {
        ArrayList<Usuario> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Verificar que la base de datos esté abierta
            if (db == null || !db.isOpen()) {
                db = admin.getWritableDatabase();
            }

            cursor = db.rawQuery("SELECT id_usuario, usuario, password, nombre, salt FROM usuario", null);

            while (cursor.moveToNext()) {
                Usuario o = new Usuario();
                o.id_usuario = cursor.getInt(0);
                o.usuario = cursor.getString(1);
                o.password = cursor.getString(2);
                o.nombre = cursor.getString(3);
                // Verificar si la columna salt existe
                if (cursor.getColumnCount() > 4) {
                    o.salt = cursor.getString(4);
                }
                lista.add(o);
            }

        } catch (Exception e) {
            Log.e("Usuario", "Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return lista;
    }

    // BÚSQUEDA: Buscar usuario específico
    public boolean buscar() {
        return true;
    }

    // AUTENTICACIÓN: Verificar credenciales de usuario
    public boolean verificar_usuario() {
        try {
            String query = "SELECT password, salt FROM usuario WHERE usuario = ?";
            db = admin.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{this.usuario});

            if (cursor.moveToNext()) {
                String hashAlmacenado = cursor.getString(0);
                String saltAlmacenado = cursor.getString(1);
                cursor.close();
                db.close();
                return Util.verificarPassword(this.password, hashAlmacenado, saltAlmacenado);
            }

            cursor.close();
            db.close();
            return false;

        } catch (Exception e) {
            Log.e("Usuario", "Error al verificar usuario: " + e.getMessage());
            if (db != null && db.isOpen()) {
                db.close();
            }
            return false;
        }
    }
}