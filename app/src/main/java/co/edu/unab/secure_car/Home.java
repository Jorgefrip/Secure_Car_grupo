package co.edu.unab.secure_car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Home extends AppCompatActivity {
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    Button btn_agregar;
    Button btn_cerrar_sesion;

    private adaptadorCarro adaptadorCarros;
    private RecyclerView rv_home;
    private ArrayList<Carros> listadocarros= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_agregar = findViewById(R.id.btn_nuevo_vehiculo);
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        rv_home=findViewById(R.id.rv_home);

        rv_home.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CerrarSesion();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Inicio");
        /*actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        CarrosfromFirebase();

    }
    public void onClickAgregar(View view){
        //Ir a la ventana del login
        Intent ingresar = new Intent(Home.this, AgregarVehiculo.class);
        startActivity(ingresar);
    }

    protected void onStart(){
        verificacionInicioSesion();
        super.onStart();
    }

    private void verificacionInicioSesion(){
        if(firebaseUser != null){
            /*Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_SHORT).show();*/
        } else {
            startActivity(new Intent(Home.this, Login.class));
            finish();
        }
    }

    private void CerrarSesion(){
        mAuth.signOut();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Home.this, Login.class));
    }

    private void CarrosfromFirebase(){
        String id = mAuth.getCurrentUser().getUid();

        databaseReference.child("Users").child(id).child("carros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String marca=ds.child("marca").getValue().toString();
                        String modelo=ds.child("modelo").getValue().toString();
                        String placa=ds.child("placa").getValue().toString();
                        String color=ds.child("color").getValue().toString();
                        String nombre=ds.child("nombre").getValue().toString();
                        String genero=ds.child("genero").getValue().toString();
                        String edad=ds.child("edad").getValue().toString();
                        String id=ds.child("id").getValue().toString();

                        //Carros carro =(Carros) ds.child("carro").getValue();
                        //Carros carrod= new Carros(ds.child("marca").getValue().toString(),ds.child("modelo").getValue().toString(),ds.child("placa").getValue().toString(),ds.child("color").getValue().toString(),ds.child("nombre").getValue().toString(),ds.child("edad").getValue().toString(),ds.child("genero").getValue().toString(),ds.child("id").getValue().toString());
                        listadocarros.add(new Carros(marca,modelo,placa,color,nombre,edad,genero,id));

                    }
                    adaptadorCarros=new adaptadorCarro(listadocarros, R.layout.activity_vista_home);
                    adaptadorCarros.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =new Intent(Home.this,MostrarDatos.class);
                            intent.putExtra("marca",listadocarros.get(rv_home.getChildAdapterPosition(view)).getMarca());
                            intent.putExtra("modelo",listadocarros.get(rv_home.getChildAdapterPosition(view)).getModelo());;
                            intent.putExtra("color",listadocarros.get(rv_home.getChildAdapterPosition(view)).getColor());
                            intent.putExtra("placa",listadocarros.get(rv_home.getChildAdapterPosition(view)).getPlaca());
                            intent.putExtra("nombre",listadocarros.get(rv_home.getChildAdapterPosition(view)).getNombre());
                            intent.putExtra("edad",listadocarros.get(rv_home.getChildAdapterPosition(view)).getEdad());
                            intent.putExtra("genero",listadocarros.get(rv_home.getChildAdapterPosition(view)).getGenero());
                            intent.putExtra("id",listadocarros.get(rv_home.getChildAdapterPosition(view)).getId());
                            startActivity(intent);

                        }
                    });
                    rv_home.setAdapter(adaptadorCarros);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

