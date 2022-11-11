package co.edu.unab.secure_car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    private EditText pt_correo;
    private EditText pt_usuario_registro;
    private EditText pt_contraseña_registro;
    private EditText pt_confirmar_contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        pt_correo = findViewById(R.id.pt_correo);
        pt_usuario_registro = findViewById(R.id.pt_usuario_registro);
        pt_contraseña_registro = findViewById(R.id.pt_contraseña_registro);
        pt_confirmar_contraseña = findViewById(R.id.pt_confirmar_contraseña);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    //Registro de Usuario
    /*public void registrarUsuario(View view){

        if (pt_contraseña_registro.getText().toString().equals(pt_confirmar_contraseña.getText().toString())){   //Verificar que las contrseñas sean iguales

            mAuth.createUserWithEmailAndPassword(pt_correo.getText().toString(), pt_contraseña_registro.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                                //updateUI(user);
                            } else{
                                Toast.makeText(getApplicationContext(), "Error de registro", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void Registro(View view){
        mAuth.createUserWithEmailAndPassword(pt_correo.getText().toString(), pt_contraseña_registro.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map=new HashMap<>();
                    map.put( "Nombre",pt_usuario_registro.getText());
                    map.put("Correo",pt_correo.getText());
                    map.put("Contraseña",pt_contraseña_registro.getText());

                    String id= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    mdatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()){
                                startActivity(new Intent(Registro.this,Login.class));
                                finish();
                                // StyleableToast.makeText(regitrate.this,"Registro correctamente",R.style.exampleToast).show();
                                Toast.makeText(Registro.this, "Registro correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //StyleableToast.makeText(regitrate.this,"no se pudo crear datos correctamete",R.style.exampleToast).show();
                                Toast.makeText(Registro.this, "no se pudo crear datos ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    //  StyleableToast.makeText(regitrate.this,"no se pudo crear datos correctamente",R.style.exampleToast).show();
                    Toast.makeText(Registro.this, "no se pudo crear datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
