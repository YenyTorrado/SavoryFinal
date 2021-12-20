package com.example.panaderia.ui.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private String SentenciaSQL;
    private String nombreTabla;

    public dbHelper(@Nullable Context context, @NonNull String nombreTabla,@NonNull String SentenciaSQL,@NonNull int version,@NonNull String nombreDb) {
        super(context, nombreDb, null, version);
        this.SentenciaSQL = SentenciaSQL;
        this.nombreTabla = nombreTabla;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SentenciaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+nombreTabla);
        onCreate(db);
    }
}
