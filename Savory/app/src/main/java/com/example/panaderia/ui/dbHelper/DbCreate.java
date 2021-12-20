package com.example.panaderia.ui.dbHelper;

import android.content.Context;

import androidx.annotation.Nullable;

public class DbCreate extends CRUD{

    private static int versionDb = 1;
    private static String nombreDataBase = "DataBese_Sucursales.db";
    private static String nombreTabla = "Table_Sucursales";
    private String[] columnData = {"nombre","descripcion","precio","favoritos"};
    private static String sqlSentence = "CREATE TABLE "+nombreTabla+"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre TEXT NOT NULL,"+
            "descripcion TEXT NOT NULL,"+
            "precio TEXT NOT NULL,"+
            "favoritos TEXT NOT NULL,"+
            "imagen BLOB NOT NULL)";

    public DbCreate(@Nullable Context context) {
        super(context, nombreTabla, sqlSentence, versionDb, nombreDataBase);
    }

    public String[] getColumnData() {
        return columnData;
    }
}
