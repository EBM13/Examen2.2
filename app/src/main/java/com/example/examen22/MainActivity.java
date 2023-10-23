package com.example.examen22;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextID;
    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private CRUDRecetas CRUD;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> listaRecetitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextID = findViewById(R.id.editTextID);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        CRUD = new CRUDRecetas(this);
        listaRecetitas = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRecetitas);

        ListView listaRecetas = findViewById(R.id.listarecetas);
        listaRecetas.setAdapter(adaptador);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText = editTextID.getText().toString();
                if (!idText.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    CRUD.eliminarReceta(id);
                    actualizarListaRecetas();
                    editTextID.setText("");
                }
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();

                if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                    CRUD.insertarReceta(titulo, descripcion);
                    actualizarListaRecetas();
                    editTextTitulo.setText("");
                    editTextDescripcion.setText("");
                }
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText = editTextID.getText().toString();
                String titulo = editTextTitulo.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();

                if (!idText.isEmpty() && !titulo.isEmpty() && !descripcion.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    CRUD.actualizarReceta(id, titulo, descripcion);
                    actualizarListaRecetas();
                    editTextID.setText("");
                    editTextTitulo.setText("");
                    editTextDescripcion.setText("");
                }
            }
        });

        actualizarListaRecetas();
    }

    private void actualizarListaRecetas() {
        listaRecetitas.clear();
        Cursor informacion = CRUD.mostrarRecetas();
        while (informacion.moveToNext()) {
            String titulo = informacion.getString(1);
            listaRecetitas.add(titulo);
        }
        adaptador.notifyDataSetChanged();
    }
}
