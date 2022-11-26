package com.example.evaluacion_2_iot_madariaga_velasquez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SegundaActividad extends AppCompatActivity {
    private ListView listView1;
    private EditText etnewtitulo, etnewdesc;
    private SearchView svfiltrar;
    private Button btnfiltrar, btnVolver;

    private ArrayList<String> tareas;         // Tratar de instanciar la interfaz List en vez de la clase Arraylist para aumentar el rendimiento del compilador
    private ArrayAdapter<String> myAdapter1;  // El adapter es una clase similar al pivote que se utilizó en trabajos de Python para ubicar datos de una lista, pero sin DB esta vez.

    Integer indexVal;   // Variable utilizada para identificar el index de un item seleccionado en la lista.
    String item;        // Almacena el mensaje concatenado que indica el objeto en específico que se ha seleccionado, haciendo referencia a su index (variable i declarada dentro del constructor
    // del evento anónimo).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Herencia.
        setContentView(R.layout.activity_segunda_actividad);

        referencias();  // new
        eventos();      // new
    }

    // region Referencias
    private void referencias() { // new
        listView1 = (ListView) findViewById(R.id.listview1);
        svfiltrar = (SearchView) findViewById(R.id.svFiltrar);
        etnewtitulo = (EditText) findViewById(R.id.nuevotitulo);
        etnewdesc = (EditText) findViewById(R.id.nuevadescripcion);
        btnVolver = (Button) findViewById(R.id.btnVolver);

        Bundle bundle = getIntent().getExtras(); // Básicamente, creamos un objeto de la clase bundle, el mismo tipo de objeto que se usa al declarar el método onCreate.
        // Lo que se busca hacer con este objeto bundle es empaquetar todos los datos rescatados de esta activity y manipularla de tal forma que
        // por medio de los extras los pasamos a la segunda actividad, en la cual se deben mostrar los datos creados listados en la listview1.
        // (Investigado de la clase de IOT diccionarios y listas, mapeado de datos, etc.).
        tareas = bundle.getStringArrayList("tareas"); // la variable tareas, tal como se menciona, almacenará el string del arraylist seleccionado, haciendole referencia con su key tareas.
        // (Concepto de diccionario de datos).
        myAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas); // (Contexto, Recurso de a utilizar, Recurso de donde rescataremos los datos)
        listView1.setAdapter(myAdapter1);        // Se asigna el adapter a la listview1 declarada.
    }
    // Fin región referencias.

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int idGenerator(){
        LocalDateTime date = LocalDateTime.now();
        return date.getHour()+date.getMinute()+date.getSecond();
    }

    // Región eventos, clase anónima.
    private void eventos () { // new
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });
        // Metodo que arroja un toast en cuanto se selecciona un item de la listview.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                item = "La tarea: " + AdapterView.getItemAtPosition(i).toString() + " ha sido seleccionada.";
                indexVal = i;
                Toast.makeText(SegundaActividad.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        // Método que elimina el item de la listview.
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                tareas.remove(i); // Se llama al método remove para remover el item del arraylist tareas.
                Toast.makeText(SegundaActividad.this, item, Toast.LENGTH_SHORT).show();
                item = "La tarea: " + adapterView.getItemAtPosition(i).toString() + " ha sido eliminada de la lista.";
                myAdapter1.notifyDataSetChanged();
                return true;
            }
        });
        // Modificar items de la lista
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stringval1 = etnewtitulo.getText().toString();
                String stringval2 = etnewdesc.getText().toString();
                if(stringval1.isEmpty() || stringval2.isEmpty()){
                    Toast.makeText(SegundaActividad.this, "Uno o más campos están vacíos.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (0 > stringval1.length() || stringval1.length() > 20  ) {
                    etnewtitulo.setError("El título no puede tener más de 20 caracteres.");
                } else if ( 0 < stringval1.length()  && stringval1.length() < 21){
                    tareas.remove(i);
                    tareas.add("- ID: "+ idGenerator() + ", Tarea: " + etnewtitulo.getText().toString() + ", Descripcion: " + stringval2);
                    myAdapter1.notifyDataSetChanged();
                    etnewtitulo.setText("");
                    etnewdesc.setText("");
                }

            }
        });
        svfiltrar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String filtro) {
                SegundaActividad.this.myAdapter1.getFilter().filter(filtro);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nuevoTexto) {
                SegundaActividad.this.myAdapter1.getFilter().filter(nuevoTexto);
                return false;
            }
        });
    }

    private void volver(){
        finish();
    }
    // Fin región eventos.
}