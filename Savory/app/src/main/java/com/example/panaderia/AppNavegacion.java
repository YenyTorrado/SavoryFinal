package com.example.panaderia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.panaderia.ui.favoritos.FavoritosActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.panaderia.databinding.ActivityAppNavegacionBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class AppNavegacion extends AppCompatActivity implements Serializable {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAppNavegacionBinding binding;
    public static LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        layoutInflater = getLayoutInflater();

        setSupportActionBar(binding.appBarAppNavegacion.toolbar);


        binding.appBarAppNavegacion.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AppNavegacion.this, FavoritosActivity.class);
                startActivity(in);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_mapas,R.id.nav_servicios,R.id.nav_tiendas,R.id.nav_agregar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_app_navegacion);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_navegacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_close:
                Toast.makeText(this,"Cerrar sesion",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_app_navegacion);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}