package com.example.panaderia.ui.mapas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.panaderia.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// Correo: reto4misiontic@gmail.com
// Contraseña: misionTic2022
// Clave API Google Maps: AIzaSyB7qSWahJCEi5HQGIZStaWt_qsqjXqltJU

public class MapasFragment extends Fragment implements OnMapReadyCallback {
    private LocationManager locationManager;
    private Location location;

    public MapasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapas, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);





        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
                },100);
            }

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            GoogleMap mapa = googleMap;

            LatLng USA = new LatLng(4.660747,-74.059998); //Nos ubicamos en la USA
            LatLng UIS = new LatLng(7.1390023,-73.1205319); //Nos ubicamos en la UIS
            Toast.makeText(getContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
            LatLng Mi_localizacion = new LatLng(location.getLatitude(),location.getLongitude()); //Nos ubicamos en la UIS
            mapa.addMarker(new MarkerOptions().position(USA).title("Universidad Sergio Arboleda"));
            mapa.addMarker(new MarkerOptions().position(UIS).title("Universidad Industrial de Santander"));
            mapa.addMarker(new MarkerOptions().position(Mi_localizacion).title("Ubicacion Tiempo real"));
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(USA,8));
        }catch (Exception ex){

        }

    }
}