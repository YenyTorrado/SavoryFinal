package com.example.panaderia.ui.productos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panaderia.AppNavegacion;
import com.example.panaderia.R;
import com.example.panaderia.ui.dbHelper.DbCreate;

import java.util.List;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolder>  {

    private List<Productos> listaProductos;

    public AdapterProductos(List<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_productos,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_Nombre.setText(listaProductos.get(position).getNombre());
        holder.txt_Descripcion.setText(listaProductos.get(position).getDescripcion());
        holder.txt_Precio.setText(listaProductos.get(position).getPrecio());
        holder.imgImagen.setImageBitmap(listaProductos.get(position).getCambio());
        holder.id = listaProductos.get(position).getId();
        holder.favoritos = listaProductos.get(position).getFavoritos();
        holder.btn_Favorite.setImageResource(Integer.parseInt(listaProductos.get(position).getId_Imagen()));
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String id;
        private String favoritos;
        private byte[] mapbyte;
        private TextView txt_Nombre,txt_Descripcion,txt_Precio;
        private EditText ed_Nombre,ed_Descripcion,ed_Precio;
        private ImageView imgImagen;
        private Button btn_Delete,btn_Update;
        private ImageButton btn_Favorite;
        private DbCreate crud;
        private SQLiteDatabase db;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Nombre = (TextView)  itemView.findViewById(R.id.txtnombreProducto);
            txt_Descripcion = (TextView)  itemView.findViewById(R.id.txtdescripcionProducto);
            txt_Precio = (TextView)  itemView.findViewById(R.id.txtPrecioProducto);
            btn_Delete = (Button)  itemView.findViewById(R.id.btnDelete);
            btn_Update = (Button)  itemView.findViewById(R.id.btnUpdate);
            btn_Favorite = (ImageButton)  itemView.findViewById(R.id.btnFavorite);
            imgImagen = (ImageView)  itemView.findViewById(R.id.imgProducto);




            //imgImagen.setImageBitmap(bit);


            crud = new DbCreate(itemView.getContext());
            db = crud.getWritableDatabase();

            btn_Favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(favoritos.equals("1")){
                        // No favorito
                        crud.updateFavorite(Integer.parseInt(id),"2");

                    }else {
                        //Si favorito
                        crud.updateFavorite(Integer.parseInt(id),"1");
                    }

                }
            });

            btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Delete")
                            .setMessage("¿Quiere eliminar el id: "+id+"?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   crud.delete(Integer.parseInt(id));
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(itemView.getContext(), favoritos, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            btn_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Update")
                            .setMessage("¿Quiere actualizar el id: "+id+"?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String [] columnValue = new String[3];
                                    columnValue[0] = ed_Nombre.getText().toString();
                                    columnValue[1] = ed_Descripcion.getText().toString();
                                    columnValue[2] = ed_Precio.getText().toString();
                                    crud.update(Integer.parseInt(id), crud.getColumnData(), columnValue);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(),"No se actualizo",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false);
                    AlertDialog alert = builder.create();
                    LayoutInflater inflater = AppNavegacion.layoutInflater;
                    View vi = inflater.inflate(R.layout.fragment_dialog_actualizar,null);
                    alert.setView(vi);
                    alert.show();

                    ed_Nombre = (EditText) alert.findViewById(R.id.edNombreActualizar);
                    ed_Descripcion = (EditText) alert.findViewById(R.id.edDireccionActualizarr);
                    ed_Precio = (EditText) alert.findViewById(R.id.edTelefonoActualizar);
                }
            });


        }
    }
}
