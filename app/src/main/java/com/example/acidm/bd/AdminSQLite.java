package com.example.acidm.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by acidm on 18/10/2017.
 */

public class AdminSQLite extends SQLiteOpenHelper {
    //Se crean variables para la Base de Datos
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BaseDato.db";
    public static final String TABLA = "Datos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATO = "dato";



    public AdminSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    //Se construye y se crea la Base de Datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATO + " TEXT" +
                ");";

        db.execSQL(query);

    }

    // Cada vez que se actualiza la tabla, borra y crea nueva.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }

    //Añade un nuevo Row de evento a la Base de Datos

    public void addDato(String dato) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DATO, dato);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLA, null, values);
        db.close();

    }

    // Borrar una evento por su id de la Base de Datos

    public void borrarEvento(String dato){
        // los datos vienen de tres tablas por tanto puede duplicarse id de evento ais que diferenciamos por el tipo tambien
        String deleteEvento = "DELETE FROM " + TABLA
                + " WHERE " + COLUMN_DATO + " = '" + dato + "';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(deleteEvento);
        db.close();
    }

    //listar a todos los eventos guardados en la base
    public Cursor listarTodos(){
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLA + ";");
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    //busca evento por id en la base
    public Cursor BuscarporDato(String dato){
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLA + " WHERE " + COLUMN_DATO + " = '" + dato + "';");

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    public void ActualizarDato(String newdato, String dato)
    {
        String query = ("UPDATE " + TABLA + " SET " + COLUMN_DATO + " = '" + newdato + "' WHERE " + COLUMN_DATO + " = '" + dato + "';");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}