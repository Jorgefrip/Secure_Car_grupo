package co.edu.unab.secure_car;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;


public class MostrarDatos extends AppCompatActivity  implements  View.OnClickListener{
    TextView txt_marca;
    TextView txt_modelo;
    TextView txt_placa;
    TextView txt_color;
    TextView txt_nombre;
    TextView txt_edad;
    TextView txt_genero;
    TextView txt_id;
    Button btn_mapa2;

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    private FusedLocationProviderClient mFusedLocationClient;
    DatabaseReference mDatabase;

    ImageButton btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        txt_marca = findViewById(R.id.tv_marca2);
        txt_modelo = findViewById(R.id.tv_modelo2);
        txt_placa = findViewById(R.id.tv_placa2);
        txt_color = findViewById(R.id.tv_color2);
        txt_nombre = findViewById(R.id.tv_nombre2);
        txt_edad = findViewById(R.id.tv_edad2);
        txt_genero = findViewById(R.id.tv_genero2);
        txt_id = findViewById(R.id.tv_id2);
        btn_mapa2 = findViewById(R.id.btn_mapa2);

        txt_marca.setText(getIntent().getStringExtra("marca"));

        txt_modelo.setText(getIntent().getStringExtra("modelo"));
        txt_placa.setText(getIntent().getStringExtra("placa"));
        txt_color.setText(getIntent().getStringExtra("color"));
        txt_nombre.setText(getIntent().getStringExtra("nombre"));
        txt_edad.setText(getIntent().getStringExtra("edad"));
        txt_genero.setText(getIntent().getStringExtra("genero"));
        txt_id.setText(getIntent().getStringExtra("id"));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        subirLatLongFirebase();


        subirLatLongFirebase();
    }

    private void subirLatLongFirebase() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( MostrarDatos.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Got last know location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e("Latitud: ", +location.getLatitude() + "Longitud: " + location.getLongitude());

                            Map<String, Object> latlang = new HashMap<>();
                            latlang.put("latitud", location.getLatitude());
                            latlang.put("longitud", location.getLongitude());
                            mDatabase.child("Users").push().setValue(latlang);


                        }

                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_mapa2:Intent intent = new Intent(MostrarDatos.this,MapsActivity.class);
                startActivity(intent);
                break;
        }

    }

/*
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS ;
    private FusedLocationProviderClient mFusedLocationClient;
    DatabaseReference mDatabase;

    ImageButton btn_volver;
*/


/*

    private void subirLatLongFirebase() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( MostrarDatos.this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);





            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Got last know location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e( "Latitud: ", +location.getLatitude()+"Longitud: "+location.getLongitude());

                            Map<String,Object> latlang = new HashMap<>();
                            latlang.put("latitud",location.getLatitude());
                            latlang.put("longitud",location.getLongitude());
                            mDatabase.child("Users").push().setValue(latlang);


                        }

                    }
                });
    }
*/



}
