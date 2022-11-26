package com.example.evaluacion_2_iot_madariaga_velasquez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class SegundaActividad extends AppCompatActivity {
    private ListView listView1;
    private SearchView svfiltrar;
    private Button btnfiltrar, btnVolver;

    private ArrayList<String> tareas; // Tratar de instanciar la interfaz List en vez de la clase Arraylist para aumentar el rendimiento del compilador
    private ArrayAdapter<String> myAdapter1;

    Integer indexVal;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_actividad);

        referencias();  // new
        eventos();      // new
    }

    // region Referencias y eventos.
    private void referencias() { // new
        listView1 = (ListView) findViewById(R.id.listview1);
        svfiltrar = (SearchView) findViewById(R.id.svFiltrar);
        btnfiltrar = (Button) findViewById(R.id.btnFiltrar);
        btnVolver = (Button) findViewById(R.id.btnVolver);

        Bundle bundle = getIntent().getExtras(); // Android Bundles are generally used for passing data from one activity to another.
        // Basically here concept of key-value pair is used where the data that one wants to pass is the value of the map,
        // which can be later retrieved by using the key.
        tareas = bundle.getStringArrayList("tareas");

        myAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        listView1.setAdapter(myAdapter1);
    }

    private void eventos () { // new
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                item = "La tarea: " + AdapterView.getItemAtPosition(i).toString() + " ha sido seleccionada.";
                indexVal = i;
                Toast.makeText(SegundaActividad.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = "La tarea: " + adapterView.getItemAtPosition(i).toString() + " ha sido eliminada de la lista.";
                Toast.makeText(SegundaActividad.this, item, Toast.LENGTH_SHORT).show();

                tareas.remove(i);
                myAdapter1.notifyDataSetChanged();
                return true;
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
}