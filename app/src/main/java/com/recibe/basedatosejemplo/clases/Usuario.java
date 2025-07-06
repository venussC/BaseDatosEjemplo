package com.recibe.basedatosejemplo.clases;



        import android.app.Activity;
        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import java.util.ArrayList;

public class Usuario {
    private int id_usuario;
    private String usuario;
    private String password;
    private String nombre;
    MiHelperSQLLite admin;
    SQLiteDatabase db;
    String BaseDatos= "basededatos.db";

    public Usuario() {
    }

    public Usuario(Activity activity2) {
        admin  = new MiHelperSQLLite(activity2, BaseDatos, null, 1);
        db =  admin.getWritableDatabase();
    }

    public Usuario(int id_usuario, String usuario, String password, String nombre) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
    }

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

    public boolean insertar(){
        ContentValues registro = new ContentValues();
        registro.put("usuario", this.usuario);
        registro.put("password", Util.cifrar(this.password));
        registro.put("nombre", this.nombre);
        db.insert("usuario", null, registro);
        db.close();
        return true;
    }

    public boolean modificar(){
        ContentValues registro = new ContentValues();
        registro.put("usuario", this.usuario);
        registro.put("password", Util.cifrar(this.password));
        registro.put("nombre", this.nombre);
        db.update("usuario",  registro, "id_usuario="+this.id_usuario, null );
        db.close();
        return true;
    }

    public boolean eliminar(){
        db.delete("usuario","id_usuario="+this.id_usuario, null);
        db.close();
        return true;
    }

    public ArrayList<Usuario> ListarTodos(){
        ArrayList<Usuario> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id_usuario, usuario, password, nombre from usuario", null);
        while (cursor.moveToNext()){
            Usuario o = new Usuario();
            o.id_usuario = cursor.getInt(0);
            o.usuario  = cursor.getString(1);
            o.password = cursor.getString(2);
            o.nombre = cursor.getString(3);
            lista.add(o);
        }
        db.close();
        return lista;
    }

    public boolean buscar(){
        return true;
    }

    public boolean verificar_usuario(){

        String query = "select id_usuario, usuario, password, nombre from usuario where  usuario = '" + this.usuario + "' and password='" + Util.cifrar(this.password) + "'";
        db =  admin.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }



}
