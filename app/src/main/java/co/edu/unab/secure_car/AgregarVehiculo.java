package co.edu.unab.secure_car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AgregarVehiculo extends AppCompatActivity {

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    EditText pt_nombre, pt_edad, pt_genero, pt_id, pt_marca, pt_modelo, pt_placa, pt_color;
    Button btn_continuar_ag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vehiculo);

        pt_nombre = findViewById(R.id.pt_nombre);
        pt_edad = findViewById(R.id.pt_edad);
        pt_genero = findViewById(R.id.pt_genero);
        pt_id = findViewById(R.id.pt_id);
        pt_marca = findViewById(R.id.pt_marca);
        pt_modelo = findViewById(R.id.pt_modelo);
        pt_placa = findViewById(R.id.pt_placa);
        pt_color = findViewById(R.id.pt_color);
        btn_continuar_ag = findViewById(R.id.btn_continuar_ag);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }
    public void Actualizar (View view){
        String id = mAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {

                    Carros carro=new Carros(pt_marca.getText().toString(),pt_modelo.getText().toString(),pt_placa.getText().toString(),pt_color.getText().toString(),pt_nombre.getText().toString(),pt_edad.getText().toString(),pt_genero.getText().toString(),pt_id.getText().toString());
                    databaseReference.child("Users").child(id).child("carros").push().setValue(carro).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Intent intent = new Intent(AgregarVehiculo.this, Home.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}