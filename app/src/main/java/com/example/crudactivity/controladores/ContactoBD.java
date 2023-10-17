package com.example.crudactivity.controladores;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crudactivity.modelos.Contacto;
import com.example.crudactivity.repositorios.IContactoBD;

import java.util.ArrayList;
import java.util.List;

public class ContactoBD  extends SQLiteOpenHelper implements IContactoBD {

    Context contexto;

    private List<Contacto> contactos = new ArrayList<>();

    public ContactoBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.contexto = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE contacto (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT, telefono TEXT, correo TEXT, direccion TEXT)";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public Contacto persona(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contacto WHERE id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        try {
           if (cursor.moveToFirst()) {
               return extraerContacto(cursor);
           }
        } finally {
           cursor.close();
           db.close();
         }

        return null;
    }

    @Override
    public Contacto persona(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contacto WHERE nombre = '" + nombre + "'";
        Cursor cursor = db.rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                return extraerContacto(cursor);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return null;
    }

    private Contacto extraerContacto(Cursor cursor) {
        Contacto contacto = new Contacto();
        contacto.setId(cursor.getInt(0));
        contacto.setNombre(cursor.getString(1));
        contacto.setTelefono(cursor.getString(2));
        contacto.setCorreo(cursor.getString(3));
        contacto.setDireccion(cursor.getString(4));

        return contacto;
    }

    @Override
    public List<Contacto> personas() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM contacto ORDER BY nombre";
        Cursor cursor = db.rawQuery(sql, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    contactos.add(extraerContacto(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        return contactos;

    }

    @Override
    public void agregar(Contacto persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO contacto (nombre, telefono, correo, direccion) VALUES ('" +
                persona.getNombre() + "', '" + persona.getTelefono() + "', '" + persona.getCorreo() + "', '" +
                persona.getDireccion() + "')";
        db.execSQL(sql);
        db.close();

    }

    @Override
    public void actualizar(int id, Contacto persona) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE contacto SET nombre = '" + persona.getNombre() + "', telefono = '" +
                persona.getTelefono() + "', correo = '" + persona.getCorreo() + "', direccion = '" +
                persona.getDireccion() + "' WHERE id = " + id;
        db.execSQL(sql);
        db.close();

    }

    @Override
    public void borrar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM contacto WHERE id = " + id;
        db.execSQL(sql);
        db.close();

    }
}
