package com.example.alejandro.proyecto2xml;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends Activity{
private ArrayList<Pelicula> peliculas;
private ArrayList <Pelicula> datos= new ArrayList <Pelicula>();
private AdaptadorArrayList ad;


        /****************************************************/
    /*                                                  */
    /*                  metodos on                      */
    /*                                                  */
        /****************************************************/


        public boolean onContextItemSelected(MenuItem item) {
            int id=item.getItemId();
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


            //1 indice
            int index=info.position;
            //2 Objeto view + el patron viewHolder
            Object o=info.targetView.getTag();



            AdaptadorArrayList.ViewHolder vh;
            vh=(AdaptadorArrayList.ViewHolder) o;
            tostada(vh.tvTitulo.getText().toString());

            if (id == R.id.action_borrar) {
            /*peliculas.remove(index);
            ad.notifyDataSetChanged();*/
                borrar(index);

            }else if(id==R.id.action_editar){
                editar(index);
            }
            return super.onContextItemSelected(item);


        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initComponents();
        }

        //al hacer long click sobre item de listview
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.contextual,menu);
        }

        //tecla menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        //opcion del menu
        //si proceso el click: true y si no no el que lo devuelve es el super

        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if(id==R.id.action_anadir){
                return anadir();
            }
            return super.onOptionsItemSelected(item);
        }


        /****************************************************/
    /*                                                  */
    /*               auxiliares                         */
    /*                                                  */
        /****************************************************/


        private void initComponents(){
            Pelicula peli= new Pelicula("Pulp Fiction","Acción",1994);
            Pelicula peli2= new Pelicula("Atrápame si puedes","Comedia",2002);
            Pelicula peli3= new Pelicula("El efecto mariposa","Ciencia Ficción",2004);
            Pelicula peli4= new Pelicula("El silencio de los corderos","Novela de suspense",1991);
            Pelicula peli5= new Pelicula("Valkiria","Novela de suspense",2008);
            Pelicula peli6= new Pelicula("Un ciudadano ejemplar","Novela de suspense",2009);
            Pelicula peli7= new Pelicula("Luces rojas","Novela de suspense",2012);
            Pelicula peli8= new Pelicula("Malditos bastardos","Aventura",2009);
            Pelicula peli9= new Pelicula("Infiltrados","Crimen",2006);
            Pelicula peli10= new Pelicula("Acero puro","Acción",2011);

            Pelicula datos[]={peli,peli2,peli3,peli4,peli5,peli6,peli7,peli8,peli9,peli10};
            peliculas=new ArrayList<Pelicula>();
            for (Pelicula s:datos){
                peliculas.add(s);
            }
            ad=new AdaptadorArrayList(this,R.layout.lista_detalle,peliculas);
            final ListView lv=(ListView)findViewById(R.id.lvLista);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object o=view.getTag();
                    AdaptadorArrayList.ViewHolder vh;
                    vh=(AdaptadorArrayList.ViewHolder)o;
                    tostada(vh.tvTitulo.getText().toString());
                    tostada(peliculas.get(i)+" "+lv.getItemAtPosition(i));
                }
            });
            registerForContextMenu(lv);


        }

        private void tostada(String s){
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }

        public static boolean isNumeric(String str) {
            try {
                double d = Double.parseDouble(str);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }


        /****************************************************/
    /*                                                  */
    /*               metodos click                      */
    /*                                                  */
        /****************************************************/

        private boolean anadir(){

            //Crea una ventana nueva donde podemos añadir nuevos elementos
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setTitle("Añadir Película");
            LayoutInflater inflater= LayoutInflater.from(this);
            final View vista = inflater.inflate(R.layout.anadir, null);
            alert.setView(vista);
            alert.setPositiveButton("Añadir",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    EditText etTitulo,etAnio,etGenero;
                    etTitulo = (EditText) vista.findViewById(R.id.etTitulo);
                    etAnio = (EditText) vista.findViewById(R.id.etAnio);
                    etGenero = (EditText) vista.findViewById(R.id.etGenero);
                    if(isNumeric(etAnio.getText().toString())){
                        Pelicula peli= new Pelicula();
                        peli.setTitulo(etTitulo.getText().toString());
                        peli.setAnio(Integer.parseInt(etAnio.getText().toString()));
                        peli.setGenero(etGenero.getText().toString());
                        peliculas.add(peli);
                        ad.notifyDataSetChanged();
                        tostada("Película añadida");
                    }else{
                        tostada("Revisa fecha");
                    }
                }
            });
            alert.setNegativeButton("Cancelar",null);
            alert.show();
            tostada("Película añadida");
            return true;
        }
        private boolean borrar(final int pos){

            //Crea una ventana nueva donde podemos añadir nuevos elementos
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setTitle("¿Seguro que desea borrarlo?");
            LayoutInflater inflater= LayoutInflater.from(this);

            alert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    peliculas.remove(pos);
                    ad.notifyDataSetChanged();
                    tostada("Película borrada");
                }
            });
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();
            return true;
        }

        private boolean editar(final int index) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Editar");
            LayoutInflater inflater = LayoutInflater.from(this);
            final View vista = inflater.inflate(R.layout.editar, null);
            alert.setView(vista);
            final EditText etTitulo, etAnio, etGenero;
            etTitulo = (EditText) vista.findViewById(R.id.etTitulo2);
            etAnio = (EditText) vista.findViewById(R.id.etAnio2);
            etGenero = (EditText) vista.findViewById(R.id.etGenero2);

            etTitulo.setText(peliculas.get(index).getTitulo().toString());
            etAnio.setText(peliculas.get(index).getAnio().toString());
            etGenero.setText(peliculas.get(index).getGenero().toString());
            alert.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if (isNumeric(etAnio.getText().toString())) {
                        Pelicula peli = new Pelicula();
                        peli.setTitulo(etTitulo.getText().toString());
                        peli.setAnio(Integer.parseInt(etAnio.getText().toString()));
                        peli.setGenero(etGenero.getText().toString());
                        peliculas.set(index, peli);
                        ad.notifyDataSetChanged();
                        tostada("Película modificada");

                    } else {
                        tostada("Revisa fecha");
                    }
                }
            });
            alert.setNegativeButton(android.R.string.no, null);
            alert.show();
            return true;

        }

        private boolean settings(){
            return true;
        }
        /**Cuando haces click en la imagen aparece el modificar que hemos declarado antes*/
    /*public void edit(View v){
        int elemento=-1;
        //guardamos la posicion en ViewHolder de la imagen y se la pasamos a la variable elementoV2
        //metodo 1
        int elementoV2;
        elementoV2=(Integer)v.getTag();
        //metodo 2
        ViewParent vp= v.getParent();
        View vp2=(View)vp;
        Object o=vp2.getTag();
        elemento= ((AdaptadorArrayList.ViewHolder) o).posicion;
        //
        editar(elementoV2);
    }*/
        /****************************************************/
    /*                                                  */
    /*               menus                              */
    /*                                                  */
        /****************************************************/

        /****************************************************/
    /*                                                  */
    /*               clases internas                    */
    /*                                                  */
        /****************************************************/
}