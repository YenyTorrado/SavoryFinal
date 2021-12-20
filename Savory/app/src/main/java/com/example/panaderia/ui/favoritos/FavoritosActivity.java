package com.example.panaderia.ui.favoritos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.panaderia.R;
import com.example.panaderia.ui.dbHelper.DbCreate;
import com.example.panaderia.ui.productos.AdapterProductos;
import com.example.panaderia.ui.productos.Productos;

import java.util.LinkedList;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity {
    private DbCreate crud;
    private SQLiteDatabase db;
    private RecyclerView contenedor;
    private AdapterProductos adaptador;
    private List<Productos> favoritelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        crud = new DbCreate(this);
        db = crud.getWritableDatabase();

        contenedor = (RecyclerView) findViewById(R.id.contenedorFavorite);
        contenedor.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdapterProductos(getFavoritelist());
        contenedor.setAdapter(adaptador);

    }

    private List<Productos> getFavoritelist() {
        favoritelist = new LinkedList<Productos>();
        List<String> dataList = new LinkedList<String>();
        dataList = crud.readAllFavorite(5);

        for(int i = 0;i<dataList.size();i++){
            String [] data = dataList.get(i).split(",");
            String id = data[0];
            String nombre = data[1];
            String descripcion = data[2];
            String precio = data[3];
            String favoritos = data[4];
            byte[] byteimg = crud.readImg(Integer.parseInt(id));
            Productos sucursal = new Productos(id,nombre,descripcion,precio,favoritos,byteimg);
            favoritelist.add(sucursal);
        }
        return favoritelist;
    }
}