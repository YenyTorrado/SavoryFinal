package com.example.panaderia.ui.productos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.panaderia.R;
import com.example.panaderia.ui.dbHelper.DbCreate;

import java.util.LinkedList;
import java.util.List;

public class ProductosFragment extends Fragment {


    private DbCreate crud;
    private SQLiteDatabase db;

    private RecyclerView contenedor;
    private AdapterProductos adaptador;
    private List<Productos> productoslist;

    public ProductosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        // --------- Inicializar la db ------------
        crud = new DbCreate(view.getContext());
        db = crud.getWritableDatabase();

        contenedor = (RecyclerView) view.findViewById(R.id.contenedorTiendas);
        contenedor.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adaptador = new AdapterProductos(getSucursaleslist());
        contenedor.setAdapter(adaptador);



        return view;
    }

    public List<Productos> getSucursaleslist() {
        productoslist = new LinkedList<Productos>();
        List<String> dataList = new LinkedList<String>();
        dataList = crud.readAll(5);

        for(int i = 0;i<dataList.size();i++){
            String [] data = dataList.get(i).split(",");
            String id = data[0];
            String nombre = data[1];
            String direccion = data[2];
            String telefono = data[3];
            String favoritos = data[4];
            byte[] byteimg = crud.readImg(Integer.parseInt(id));
            Productos sucursal = new Productos(id,nombre,direccion,telefono,favoritos,byteimg);
            productoslist.add(sucursal);
        }

        return productoslist;
    }
}