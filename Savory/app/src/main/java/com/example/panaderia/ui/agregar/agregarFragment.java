package com.example.panaderia.ui.agregar;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.panaderia.R;
import com.example.panaderia.ui.dbHelper.CRUD;
import com.example.panaderia.ui.dbHelper.DbCreate;

import java.io.ByteArrayOutputStream;


public class agregarFragment extends Fragment implements View.OnClickListener {

    public agregarFragment() {
    }

    // ------ Datos base de datos --------


    // -------  Atributos necesarios para ejecutar db ---------
    private DbCreate crud;
    private SQLiteDatabase db;

    private ImageView imgImagen;
    private EditText edNombre,edDescripcion,edPrecio;
    private Button btnAgregar;
    private static final int PHOTO_SELECTED = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar, container, false);

        edNombre = (EditText) view.findViewById(R.id.edNombreAgregar);
        edDescripcion = (EditText) view.findViewById(R.id.edDescripcionAgregar);
        edPrecio = (EditText) view.findViewById(R.id.edPrecioAgregar);
        btnAgregar = (Button) view.findViewById(R.id.btnAgregar);
        imgImagen = (ImageView) view.findViewById(R.id.imgFotoAgregar);




        // --------- Inicializar la db ------------
        crud = new DbCreate(view.getContext());
        db = crud.getWritableDatabase();

        btnAgregar.setOnClickListener(this);
        imgImagen.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                String nombre = edNombre.getText().toString().trim();
                String direccion = edDescripcion.getText().toString().trim();
                String telefono = edPrecio.getText().toString().trim();
                String favorito = "1";

                if(!nombre.equals(null) && !direccion.equals(null) && !telefono.equals(null)){
                    String [] valueData = {nombre,direccion,telefono,favorito};
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Guardar")
                            .setMessage("¿Quiere guardar?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // ----- Uso base de datos CRUD ---------
                                    byte[] img = imaImagenToByte();

                                    boolean rta = crud.create(crud.getColumnData(),valueData,img);
                                    if(rta){
                                        Toast.makeText(v.getContext(),"Agregado con exito",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(v.getContext(),"Problemas",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(),"No se agrego",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                break;
            case R.id.imgFotoAgregar:
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                in.setType("image/*");
                startActivityForResult(in,PHOTO_SELECTED);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == PHOTO_SELECTED){
            Uri imageUri = data.getData();
            imgImagen.setImageURI(imageUri);
        }

    }

    public byte[] imaImagenToByte(){
        Bitmap bitmap = ((BitmapDrawable) imgImagen.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}