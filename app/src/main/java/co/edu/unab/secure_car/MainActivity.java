package co.edu.unab.secure_car;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_ingresar = (Button)findViewById(R.id.btn_ingresar);
    }

    public void onClickLogin(View view){
        //Ir a la ventana del login
        Intent ingresar = new Intent(MainActivity.this, Login.class);
        startActivity(ingresar);
    }
}