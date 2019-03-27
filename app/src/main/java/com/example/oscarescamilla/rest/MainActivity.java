package com.example.oscarescamilla.rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



//Importamos las librerias para mostrar Json en ListView

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView lv_clientes_list;
    private ArrayAdapter adapter;


    private String getAllClientesURL = "http://192.168.0.107:8080/api_clientes?user_hash=12345&action=get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        initComponents();
        webServiceRest(getAllClientesURL);
    }


    private void initComponents(){
        lv_clientes_list = (ListView)findViewById(R.id.lv_clientes_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_clientes_list.setAdapter(adapter);
    }


    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line="";
            String webServiceResult="";

            while((line=bufferedReader.readLine())!=null){
                webServiceResult += line;
            }

            bufferedReader.close();

            parseInformation(webServiceResult);


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;


        String id_clientes;
        String nombre;
        String telefono;
        String email;

        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException ex){
            ex.printStackTrace();
        }


        for(int i=0; i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_clientes = jsonObject.getString("id_clientes");
                nombre = jsonObject.getString("nombre");
                telefono = jsonObject.getString("telefono");
                email = jsonObject.getString("email");


                adapter.add(nombre +  "\n"+ telefono + "\n" + email);

            }catch (JSONException ex){
                ex.printStackTrace();
            }
        }


    }
}
