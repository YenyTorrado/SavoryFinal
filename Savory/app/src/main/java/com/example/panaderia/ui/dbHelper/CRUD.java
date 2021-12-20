package com.example.panaderia.ui.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;


public class CRUD extends dbHelper {

    private SQLiteDatabase db;
    private String nombreTabla;

    public CRUD(@Nullable Context context, @NonNull String nombreTabla, @NonNull String SentenciaSQL, @NonNull int version, @NonNull String nombreDb) {
        super(context, nombreTabla, SentenciaSQL, version, nombreDb);
        dbHelper dbHelper = new dbHelper(context,nombreTabla,SentenciaSQL,version,nombreDb);
        db = dbHelper.getWritableDatabase();
        this.nombreTabla = nombreTabla;
    }

    public boolean create(String[] nombreColumna, String[] valoresColumnas,byte[] img){
        try {
            ContentValues datos = new ContentValues();
            for(int i=0;i<valoresColumnas.length;i++) {
                datos.put(nombreColumna[i], valoresColumnas[i]);
            }
            datos.put("imagen",img);
            db.insert(nombreTabla,null,datos);
            return true;
        }catch (Exception ex){
            ex.toString();
            return false;
        }
    }

    public byte[] readImg(int id){
        try {
            byte [] dato = null;
            Cursor cursor_Tiendas = null;
            cursor_Tiendas = db.rawQuery("SELECT * FROM "+nombreTabla+" WHERE ID = '"+id+"'",null);
            if(cursor_Tiendas.moveToFirst()){
                dato = cursor_Tiendas.getBlob(5);
                return dato;
            }
        }catch (Exception ex){
            ex.toString();
        }
        return null;
    }

   //------------------------- Prueba all favarite --------------------------------------------------

    public List<String> readAllFavorite(int numColumnas){
        List<String> listaDatos = new LinkedList<String>();

        try {
            Cursor cursor_Tiendas = null;
            cursor_Tiendas = db.rawQuery("SELECT * FROM "+nombreTabla+" WHERE FAVORITOS = '"+"2"+"'",null);

            if(cursor_Tiendas.moveToFirst()) {
                do{
                    String dato = "";
                    for(int i=0;i<numColumnas;i++){
                        dato = dato + cursor_Tiendas.getString(i)+",";
                    }
                    listaDatos.add(dato);
                }while(cursor_Tiendas.moveToNext());
            }
            return listaDatos;
        }catch (Exception ex){
            ex.toString();
            return null;
        }
    }
    // -------------- ------------------------------------------------





    public boolean update(int id, String[] nombreColumna, String[] valoresColumnas){
        try {
            ContentValues datos = new ContentValues();
            for(int i=0;i<valoresColumnas.length;i++) {
                datos.put(nombreColumna[i], valoresColumnas[i]);
            }
            db.update(nombreTabla,datos,"id = ?",new String[] {String.valueOf(id)});
            return true;
        }catch (Exception ex){
            ex.toString();
            return false;
        }
    }

    public boolean updateFavorite(int id,String favorite){
        try {
            ContentValues datos = new ContentValues();
            datos.put("favoritos", favorite);
            db.update(nombreTabla,datos,"id = ?",new String[] {String.valueOf(id)});
            return true;
        }catch (Exception ex){
            ex.toString();
            return false;
        }
    }

    public boolean delete(int id){
        try{
            db.delete(nombreTabla,"id = ?",new String[] {String.valueOf(id)});
            return true;
        }catch (Exception ex){
            ex.toString();
            return false;
        }
    }

    public List<String> readAll(int numColumnas){
        List<String> listaDatos = new LinkedList<String>();

        try {
            Cursor cursor_Tiendas = null;
            cursor_Tiendas = db.rawQuery("SELECT * FROM " + nombreTabla, null);

            if(cursor_Tiendas.moveToFirst()) {
                do{
                    String dato = "";
                    for(int i=0;i<numColumnas;i++){
                        dato = dato + cursor_Tiendas.getString(i)+",";
                    }
                    listaDatos.add(dato);
                }while(cursor_Tiendas.moveToNext());
            }
            return listaDatos;
        }catch (Exception ex){
            ex.toString();
            return null;
        }
    }
}
