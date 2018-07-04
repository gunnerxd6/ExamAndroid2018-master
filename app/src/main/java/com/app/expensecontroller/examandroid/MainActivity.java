package com.app.expensecontroller.examandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast mensaje=Toast.makeText(getApplicationContext(),"Bienvenido a la aplicación",Toast.LENGTH_LONG);
        mensaje.setGravity(Gravity.CENTER,0,0);
        mensaje.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(MainActivity.this,BuscarActivity.class);
                startActivity(intent);

            }
        },4000);
    }
}
