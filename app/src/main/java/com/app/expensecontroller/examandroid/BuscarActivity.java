package com.app.expensecontroller.examandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BuscarActivity extends AppCompatActivity {

    Spinner sp_ciudad,sp_nivel,sp_tipo;
    Button btn_filtrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        sp_ciudad=findViewById(R.id.sp_ciudad);
        sp_tipo=findViewById(R.id.sp_tipo);
        sp_nivel=findViewById(R.id.sp_nivel);
        btn_filtrar=findViewById(R.id.btn_filtrar);

        insertarDatos();
        consultarListaDeTiposDeGasto();


        btn_filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BuscarActivity.this,ListadoActivity.class);
                intent.putExtra("ciudad",sp_ciudad.getSelectedItem().toString());
                intent.putExtra("tipo",sp_tipo.getSelectedItem().toString());
                intent.putExtra("nivel",sp_nivel.getSelectedItem().toString());

                BaseHelper conn= new BaseHelper(getApplicationContext(),"bd_establecimiento",null,1);
                SQLiteDatabase db = conn.getReadableDatabase();
                HashMap<String,String> listaEstablecimientos= new HashMap<>();


                String sql = "SELECT * FROM ESTABLECIMIENTO WHERE CIUDAD_ESTABLECIMIENTO='"+sp_ciudad.getSelectedItem().toString()+"' AND TIPO_ESTABLECIMIENTO='"+sp_tipo.getSelectedItem().toString()+"' AND NIVEL_ESTABLECIMIENTO='"+sp_nivel.getSelectedItem().toString()+"'";
                Cursor c = db.rawQuery(sql, null);

                if (!c.moveToNext()){
                    Toast mensaje=Toast.makeText(getApplicationContext(),"No se encuentra la busqueda",Toast.LENGTH_LONG);
                    mensaje.setGravity(Gravity.BOTTOM,0,0);
                    mensaje.show();
                }
                else {
                    startActivity(intent);
                }


            }
        });


    }


    private void insertarDatos() {

        BaseHelper conn= new BaseHelper(getApplicationContext(),"bd_establecimiento",null,1);

        SQLiteDatabase db= conn.getWritableDatabase();

        try{
            ContentValues cv= new ContentValues();
            cv.put("NOMBRE_ESTABLECIMIENTO", "Liceo Pablo Neruda");
            cv.put("CIUDAD_ESTABLECIMIENTO","Temuco");
            cv.put("TIPO_ESTABLECIMIENTO","Municipal");
            cv.put("NIVEL_ESTABLECIMIENTO","Enseñanza Media");

            db.insert("ESTABLECIMIENTO",null,cv);
        }catch (Exception e){
            Log.e("ERROR INGRESO", e.getMessage().toString());
        }
        db.close();

    }

    private Spinner  consultarListaDeTiposDeGasto() {
        BaseHelper conn= new BaseHelper(getApplicationContext(),"bd_establecimiento",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> lista_ciudad = new ArrayList<String>();


        String sql = "SELECT * FROM ESTABLECIMIENTO";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            String city=c.getString(c.getColumnIndex("CIUDAD_ESTABLECIMIENTO"));

            if(!lista_ciudad.contains(city)){
                lista_ciudad.add(city);
                Log.e("Valor tipo", "" + c.getString(c.getColumnIndex("CIUDAD_ESTABLECIMIENTO")));
            }


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_ciudad);
        sp_ciudad.setAdapter(adapter);
        return sp_ciudad;
    }

}
