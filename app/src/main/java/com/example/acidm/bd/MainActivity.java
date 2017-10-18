package com.example.acidm.bd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    //Button btnActualizar;
    Button btnGuardar, btnBuscar, btnBorrar, btnUpdate;
    EditText edtDato, edtNuevoDato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista= (ListView)findViewById(R.id.listDato);
        /*btnActualizar=(Button)findViewById(R.id.btnActualizar);*/
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnUpdate = (Button) findViewById(R.id.btnUpgrade);

        edtDato = (EditText) findViewById(R.id.edtDato);
        edtNuevoDato = (EditText) findViewById(R.id.edtNuevoDato);


        AdminSQLite dbHandler;
        // Parametros que se van al constructor
        dbHandler = new AdminSQLite(this, null, null, 1);
        //Hander para ver metodos de la clase
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        Cursor resultados = dbHandler.listarTodos();
        List<BdModel> modelo = new ArrayList<BdModel>();
        //BdModel elemento;
        String tempHorario;
        int cuantos = resultados.getCount();
        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        for (int c = 0; c < cuantos ; c++){

            /*elemento = new BdModel();
            elemento.setDato(resultados.getString(1));*/


            adaptador.add(resultados.getString(1));
            resultados.moveToNext();

        }


        lista.setAdapter(adaptador);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminSQLite dbHandler;
                dbHandler = new AdminSQLite(getBaseContext(), null, null, 1);
                SQLiteDatabase db = dbHandler.getWritableDatabase();
                String edt_dato = edtDato.getText().toString();
                dbHandler.addDato(edt_dato);

                refresh(dbHandler);

                Toast.makeText(MainActivity.this, "Se ha añadido '" + edt_dato + "'",
                    Toast.LENGTH_SHORT).show();

            }
        });

        /*btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLite dbHandler;
                dbHandler = new AdminSQLite(getBaseContext(), null, null, 1);
                SQLiteDatabase db = dbHandler.getWritableDatabase();

                Cursor resultados = dbHandler.listarTodos();
                List<BdModel> modelo = new ArrayList<BdModel>();
                BdModel elemento;
                String tempHorario;
                int cuantos = resultados.getCount();
                ArrayAdapter<String> adaptador;
                adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1);
                adaptador.clear();
                for (int c = 0; c < cuantos ; c++){

                    elemento = new BdModel();
                    elemento.setDato(resultados.getString(1));


                    adaptador.add(resultados.getString(1));
                    resultados.moveToNext();

                }
                lista.setAdapter(adaptador);
            }
        });*/

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite dbHandler;
                dbHandler = new AdminSQLite(getBaseContext(), null, null, 1);
                SQLiteDatabase db = dbHandler.getWritableDatabase();

                Cursor resultados = dbHandler.BuscarporDato(edtDato.getText().toString());

                refresh(dbHandler);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite dbHandler;
                dbHandler = new AdminSQLite(getBaseContext(), null, null, 1);
                SQLiteDatabase db = dbHandler.getWritableDatabase();

                String edt_dato = edtDato.getText().toString();
                dbHandler.borrarEvento(edt_dato);

                refresh(dbHandler);

                Toast.makeText(MainActivity.this, "Se ha eliminado '" + edt_dato + "'",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminSQLite dbHandler;
                dbHandler = new AdminSQLite(getBaseContext(), null, null, 1);
                SQLiteDatabase db = dbHandler.getWritableDatabase();

                String edtDato_old = edtDato.getText().toString();
                String edtDato_new = edtNuevoDato.getText().toString();
                dbHandler.ActualizarDato(edtDato_new, edtDato_old);

                refresh(dbHandler);

                Toast.makeText(MainActivity.this, "Se ha actualizado '" + edtDato_old + "' por '" + edtDato_new + "'",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void refresh(AdminSQLite handler){

        Cursor resultados = handler.listarTodos();
        List<BdModel> modelo = new ArrayList<BdModel>();
        BdModel elemento;
        String tempHorario;
        int cuantos = resultados.getCount();
        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1);
        adaptador.clear();
        for (int c = 0; c < cuantos ; c++){

            elemento = new BdModel();
            elemento.setDato(resultados.getString(1));


            adaptador.add(resultados.getString(1));
            resultados.moveToNext();

        }
        lista.setAdapter(adaptador);



    }
}
