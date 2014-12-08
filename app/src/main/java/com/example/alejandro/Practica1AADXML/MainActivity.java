package com.example.alejandro.Practica1AADXML;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private ArrayList<Pelicula> peliculas;
    private ArrayList<Pelicula> datos = new ArrayList<Pelicula>();
    private AdaptadorArrayList ad;
    private final int ANADIR = 0;


    /****************************************************/
    /*                                                  */
    /*                  metodos on                      */
    /*                                                  */
    /****************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_anadir) {
            anadir();
        } else if (id == R.id.action_fecha) {
            ordenarFecha();
        } else if (id == R.id.action_genero) {
            ordenarGenero();
        } else if (id == R.id.action_nombre) {
            ordernarNombre();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (id == R.id.action_borrar) {
            borrar(index);
        } else if (id == R.id.action_editar) {
            editar(index);
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ANADIR) {
            String titulo, genero, fecha;
            Bundle dsc = data.getExtras();
            titulo = dsc.getString("titulo");
            genero = dsc.getString("genero");
            fecha = dsc.getString("fecha");
            datos.add(new Pelicula(titulo, genero, Integer.parseInt(fecha)));
            crearXML();
            visualizarPeliculas();
            ad.notifyDataSetChanged();
            tostada(getString(R.string.mensaje_anadir));
        }
    }

    /****************************************************/
    /*                                                  */
    /*              cambio orientacion                  */
    /*                                                  */
    /****************************************************/

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        guardaEstado.putSerializable("peliculas", datos);
    }
    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        datos = (ArrayList<Pelicula>) recuperaEstado.getSerializable("peliculas");
        visualizarPeliculas();
    }

    /****************************************************/
    /*                                                  */
    /*               auxiliares                         */
    /*                                                  */
    /****************************************************/

    private void initComponents() {
        datos = new ArrayList<Pelicula>();
        leerXML();
        visualizarPeliculas();
    }
    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public void visualizarPeliculas() {
        ad = new AdaptadorArrayList(this, R.layout.lista_detalle, datos);
        final ListView ls = (ListView) findViewById(R.id.lvLista);
        ls.setAdapter(ad);
        registerForContextMenu(ls);
    }
    public boolean comprueba(String titulo) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getTitulo().equalsIgnoreCase(titulo) == true) {
                return false;
            }
        }
        return true;
    }

    /****************************************************/
    /*                                                  */
    /*               metodos click                      */
    /*                                                  */
    /****************************************************/

    private void anadir() {

        Intent i = new Intent(this, Anadir.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList("arraylist", datos);
        i.putExtras(b);
        startActivityForResult(i, ANADIR);
    }
    private boolean borrar(final int pos) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.tituloBorrar));
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                borrarXML(pos);
                Collections.sort(datos);
                tostada(getString(R.string.mensaje_eliminar));
                visualizarPeliculas();
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }

    private boolean editar(final int index) {
        final String titulo, genero;
        final Integer fecha;
        titulo = datos.get(index).getTitulo();
        genero = datos.get(index).getGenero();
        fecha = datos.get(index).getAnio();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.titulo_editar);

        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.editar, null);
        alert.setView(vista);

        final EditText etTitulo, etGenero, etFecha;
        etTitulo = (EditText) vista.findViewById(R.id.etTitulo2);
        etGenero = (EditText) vista.findViewById(R.id.etGenero2);
        etFecha = (EditText) vista.findViewById(R.id.etAnio2);

        etTitulo.setText(titulo);
        etGenero.setText(genero);
        etFecha.setText(fecha.toString());

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (comprueba(etTitulo.getText().toString()) == true) {
                    if (etTitulo.getText().toString().equals("") == true || etFecha.getText().toString().equals("") == true || etGenero.getText().toString().equals("") == true) {
                        tostada(getString(R.string.vacio));
                    }else {
                        Pelicula pAntigua = new Pelicula(titulo, genero, fecha);
                        Pelicula pNueva = new Pelicula(etTitulo.getText().toString(), etGenero.getText().toString(), Integer.parseInt(etFecha.getText().toString()));
                        Collections.sort(datos);
                        editarXML(pNueva, pAntigua);
                        ad.notifyDataSetChanged();
                        tostada(getString(R.string.mensaje_editar));
                        visualizarPeliculas();
                    }
                } else {
                    tostada(getString(R.string.duplicado));
                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }
    private boolean settings() {
        return true;
    }

    /****************************************************/
    /*                                                  */
    /*               metodos ordenar                    */
    /*                                                  */
    /****************************************************/

    public void ordenarFecha() {
        Collections.sort(datos, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula p1, Pelicula p2) {
                return p1.getAnio().compareTo(p2.getAnio());
            }
        });
        ad.notifyDataSetChanged();
    }
    public void ordenarGenero() {
        Collections.sort(datos, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula p1, Pelicula p2) {
                return p1.getGenero().compareTo(p2.getGenero());
            }
        });
        ad.notifyDataSetChanged();
    }
    public void ordernarNombre() {
        Collections.sort(datos, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula p1, Pelicula p2) {
                return p1.getTitulo().compareTo(p2.getTitulo());
            }
        });
        ad.notifyDataSetChanged();
    }

    /****************************************************/
    /*                                                  */
    /*               metodos xml                        */
    /*                                                  */
    /****************************************************/


    public void leerXML(){
        try {
            XmlPullParser lectorxml = Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null), "peliculas.xml")), "utf-8");
            int evento = lectorxml.getEventType();
            while (evento != XmlPullParser.END_DOCUMENT) {
                if (evento == XmlPullParser.START_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("pelicula") == 0) {
                        datos.add(new Pelicula(lectorxml.getAttributeValue(null, "titulo").toString(), lectorxml.getAttributeValue(null, "genero").toString(), Integer.parseInt(lectorxml.getAttributeValue(null, "fecha").toString())));
                    }
                }
                evento = lectorxml.next();
            }
        } catch (Exception e) {
            System.out.println("ERROR AL LEER");
        }
    }
    public void crearXML(){
        try {
            FileOutputStream fosxml = new FileOutputStream(new File(getExternalFilesDir(null), "peliculas.xml"));
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            docxml.startTag(null, "peliculas");
            for (int r = 0; r < datos.size(); r++) {
                docxml.startTag(null, "pelicula");
                docxml.attribute(null, "titulo", datos.get(r).getTitulo());
                docxml.attribute(null, "genero", datos.get(r).getGenero());
                docxml.attribute(null, "fecha", datos.get(r).getAnio().toString());
                docxml.endTag(null, "pelicula");
            }
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        } catch (Exception e) {
            System.out.println("error escribir xml");
        }
    }
    public void editarXML(Pelicula pNueva, Pelicula pAntigua){
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).equals(pAntigua)) {
                datos.remove(i);
                datos.add(pNueva);
                crearXML();
                Collections.sort(datos);
            }
        }
    }
    public void borrarXML(int pos){
        String titulo, genero;
        Integer fecha;
        titulo = datos.get(pos).getTitulo();
        genero = datos.get(pos).getGenero();
        fecha = datos.get(pos).getAnio();
        Pelicula p = new Pelicula(titulo, genero, fecha);
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).equals(p)) {
                datos.remove(i);
                crearXML();
                Collections.sort(datos);
            }
        }
    }
}