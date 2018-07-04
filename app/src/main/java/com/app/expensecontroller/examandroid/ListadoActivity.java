package com.app.expensecontroller.examandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListadoActivity extends AppCompatActivity {

    ListView lv_establecimientos;
    ArrayList<String> ciudadArray = new ArrayList<>();
    ArrayList<String> nivelArray = new ArrayList<>();
    ArrayList<String> tipoArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lv_establecimientos=findViewById(R.id.lv_establecimientos);
        //ListView resultsListView = (ListView) findViewById(R.id.lv_establecimientos);


        String ciudad=getIntent().getExtras().getString("ciudad");
        String nivel=getIntent().getExtras().getString("nivel");
        String tipo=getIntent().getExtras().getString("tipo");


        ciudadArray.add(ciudad);
        nivelArray.add(nivel);
        tipoArray.add(tipo);

        Log.e("Ciudad",ciudad);
        Log.e("Nivel",nivel);
        Log.e("Tipo",tipo);

        cargarLista(ciudad,nivel,tipo);

        lv_establecimientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(ListadoActivity.this,InformacionActivity.class);
                startActivity(intent);
                String lista=lv_establecimientos.getItemAtPosition(position).toString();
                //String[] array= new String[]{};
                //array=lv_establecimientos.getItemAtPosition(position).toString();
                //String[] listas= (String[]) lv_establecimientos.getItemAtPosition(position);
                //NECESITO CAPTURAR LAS KEYS QUE SON SECOND LINE Y FIRST LINE
                String ciudadSeleccionada = ciudadArray.get(position);
                String nivelSeleccionado = nivelArray.get(position);
                String tipoSeleccionado = tipoArray.get(position);
                Toast.makeText(getApplicationContext(),"Ciudad seleccionada: "+ciudadSeleccionada+"\n Nivel seleccionado: "+nivelSeleccionado+"\n Tipo seleccionado: "+tipoSeleccionado,Toast.LENGTH_SHORT).show();



                Log.e("Item",lista);
            }
        });
        {


        }
/*
        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("Diana", "3214 Broadway Avenue");
        nameAddresses.put("Tyga", "343 Rack City Drive");
        nameAddresses.put("Rich Homie Quan", "111 Everything Gold Way");
        nameAddresses.put("Donna", "789 Escort St");
        nameAddresses.put("Bartholomew", "332 Dunkin St");
        nameAddresses.put("Eden", "421 Angelic Blvd");


        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.lista,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setAdapter(adapter);*/
    }


    public void cargarLista(String st_ciudad,String st_nivel,String st_tipo){

        BaseHelper conn= new BaseHelper(getApplicationContext(),"bd_establecimiento",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        HashMap<String,String> listaEstablecimientos= new HashMap<>();


        String sql = "SELECT * FROM ESTABLECIMIENTO WHERE CIUDAD_ESTABLECIMIENTO='"+st_ciudad+"' AND TIPO_ESTABLECIMIENTO='"+st_tipo+"' AND NIVEL_ESTABLECIMIENTO='"+st_nivel+"'";
        Cursor c = db.rawQuery(sql, null);


        while (c.moveToNext()) {
            String nombre_establecimiento=c.getString(c.getColumnIndex("NOMBRE_ESTABLECIMIENTO"));
            String tipo_establecimiento="Establecimiento "+c.getString(c.getColumnIndex("TIPO_ESTABLECIMIENTO"));
            listaEstablecimientos.put(nombre_establecimiento,tipo_establecimiento);

        }

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.lista,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});

        Iterator it = listaEstablecimientos.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv_establecimientos.setAdapter(adapter);
    }
}
