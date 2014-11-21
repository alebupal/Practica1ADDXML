package com.example.alejandro.proyecto2xml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Anadir extends MainActivity {

    private String titulo, fecha, genero;
    private EditText etTitulo, etGenero, etFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        etTitulo = (EditText)findViewById(R.id.etTitulo);
        etGenero = (EditText)findViewById(R.id.etGenero);
        etFecha = (EditText)findViewById(R.id.etAnio);
    }


    public void AnadirBoton(View v){

        titulo = etTitulo.getText().toString();
        genero = etGenero.getText().toString();
        fecha = etFecha.getText().toString();

        Intent result = new Intent();
        result.putExtra("titulo", titulo);
        result.putExtra("genero", genero);
        result.putExtra("fecha", fecha);
        setResult(Activity.RESULT_OK, result);
        finish();
        this.finish();
    }



}
