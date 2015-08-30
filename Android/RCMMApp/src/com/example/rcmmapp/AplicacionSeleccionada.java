package com.example.rcmmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
 
public class AplicacionSeleccionada extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.elemento_list_view);
         
        TextView txtAplicacion = (TextView) findViewById(R.id.aplicacion);
        
        Intent i = getIntent();
        String aplicacion = i.getStringExtra("aplicacion");
        txtAplicacion.setText(aplicacion);
         
    }
}