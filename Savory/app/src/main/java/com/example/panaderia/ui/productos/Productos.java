package com.example.panaderia.ui.productos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.panaderia.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Productos {
    private String id;
    private String nombre;
    private String descripcion;
    private String precio;
    private byte[] byteImg;
    private String favoritos;
    private String id_Imagen;

    public Productos() {
    }

    public Productos(String id, String nombre, String descripcion, String precio, String favoritos, byte[] byteImg) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
        this.favoritos = favoritos;
        this.byteImg = byteImg;
        getCambio();
    }

    public String getId_Imagen(){
        if(favoritos.equals("1")){
            // No favorito
            return String.valueOf(R.drawable.ic_baseline_favorite_border_24);
        }else {
            //Si favorito
            return String.valueOf(R.drawable.ic_baseline_favorite_24);
        }
    }

    public Bitmap getCambio(){
        InputStream input = new ByteArrayInputStream(byteImg);
        Bitmap bit = BitmapFactory.decodeStream(input);
        return bit;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

    public byte[] getByteImg() {
        return byteImg;
    }

    public void setByteImg(byte[] byteImg) {
        this.byteImg = byteImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String direccion) {
        this.descripcion = direccion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
