package com.example.evaluacion_2_iot_madariaga_velasquez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView1;
    private TextInputLayout tilTarea;
    private EditText campotarea, descripcion;
    private Button btnadd, btnmod, btnbus;

    ArrayList<String> tareas = new ArrayList<>();
    ArrayAdapter<String> myAdapter1;

    Integer indexVal;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referencias();
        eventos();
    }

    // Refs.
    private void referencias(){
        listView1 = (ListView) findViewById(R.id.listview1);
        campotarea = (EditText) findViewById(R.id.campotarea);
        btnadd = (Button) findViewById(R.id.btnAgregar);
        btnmod = (Button) findViewById(R.id.btnModificar);
        btnbus = (Button) findViewById(R.id.btnbuscar);
        descripcion = (EditText) findViewById(R.id.descripcion);

        myAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        listView1.setAdapter(myAdapter1);
        listView1.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int idGenerator(){
        LocalDateTime date = LocalDateTime.now();
        return date.getHour()+date.getMinute()+date.getSecond();
    }

    // Evs.
    private void eventos(){
        btnadd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String stringval1 = campotarea.getText().toString();
                String stringval2 = descripcion.getText().toString();

                if(stringval1.isEmpty() || stringval2.isEmpty()){
                    Toast.makeText(MainActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }

                tareas.add("ID: "+ idGenerator() + ", Tarea: " + stringval1 + ", " + stringval2);
                myAdapter1.notifyDataSetChanged();

                campotarea.setText("");
                descripcion.setText("");
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                item = "La tarea: " + AdapterView.getItemAtPosition(i).toString() + " ha sido seleccionada.";
                indexVal = i;
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });
        btnmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringval = campotarea.getText().toString();
                tareas.set(indexVal, stringval);
                myAdapter1.notifyDataSetChanged();
            }
        });
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = "La tarea: " + adapterView.getItemAtPosition(i).toString() + " ha sido eliminada de la lista.";
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();

                tareas.remove(i);
                myAdapter1.notifyDataSetChanged();
                return true;
            }
        });
        btnbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
                intent.putExtra("tareas", tareas);
                startActivity(intent);
            }
        });

        // Método lambda: btnbus.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SegundaActividad.class)));
    }
    //endregion
}

