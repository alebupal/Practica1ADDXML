package com.example.alejandro.proyecto2xml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Anadir extends Activity {

    private String titulo, fecha, genero;
    private EditText etTitulo, etGenero, etFecha;
    private ArrayList<Pelicula> datos;
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        Bundle b = getIntent().getExtras();
        datos = b.getParcelableArrayList("arraylist");

        Log.v("d", datos.size()+"");


        etTitulo = (EditText)findViewById(R.id.etTitulo);
        etGenero = (EditText)findViewById(R.id.etGenero);
        etFecha = (EditText)findViewById(R.id.etAnio);


    }


    public void AnadirBoton(View v){

        titulo = etTitulo.getText().toString();
        genero = etGenero.getText().toString();
        fecha = etFecha.getText().toString();


        if(comprueba(etTitulo.getText().toString())==true){
            Intent result = new Intent();
            result.putExtra("titulo", titulo);
            result.putExtra("genero", genero);
            result.putExtra("fecha", fecha);
            setResult(Activity.RESULT_OK, result);
            finish();
            this.finish();
        }else{
            tostada(getString(R.string.duplicado));
        }

    }



    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //guardamos en las variables el texto de los campos EditText
        titulo = etTitulo.getText().toString();
        genero = etGenero.getText().toString();
        fecha = etFecha.getText().toString();
        //lo "guardamos" en el Bundle
        guardaEstado.putString("titulo", titulo);
        guardaEstado.putString("genero", genero);
        guardaEstado.putString("fecha", fecha);
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //recuperamos el String del Bundle
        titulo = recuperaEstado.getString("titulo");
        genero = recuperaEstado.getString("genero");
        fecha = recuperaEstado.getString("fecha");

        //Seteamos el valor del EditText con el valor de nuestra cadena
        etTitulo.setText(titulo);
        etGenero.setText(genero);
        etFecha.setText(fecha);
    }

    public boolean comprueba(String titulo){
        for (int i=0;i<datos.size();i++){
            if(datos.get(i).getTitulo().equals(titulo)==true){
                return false;
            }
        }
        return true;
    }

    private void tostada(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
