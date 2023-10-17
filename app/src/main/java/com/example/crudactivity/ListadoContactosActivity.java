package com.example.crudactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.crudactivity.controladores.ContactoBD;
import com.example.crudactivity.modelos.Contacto;
import com.example.crudactivity.repositorios.IListener;

import java.util.ArrayList;
import java.util.List;

public class ListadoContactosActivity extends AppCompatActivity implements IListener {

    ListView lstContactos;
    ArrayList<String> nombres;
    ArrayList<Integer> ids;
    ContactoBD contactoBD;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_contactos);
        init();
    }

    private void init() {
        context = this;
        lstContactos = findViewById(R.id.list_contactos);
        contactoBD = new ContactoBD(context, "contactosDB.db", null, 1);

        llenarLista();
    }

    private void llenarLista() {
        nombres = new ArrayList<String>();
        ids = new ArrayList<Integer>();

        List<Contacto> contactos = contactoBD.personas();



        for (int i = 0; i < contactos.size(); i++) {
            Contacto contacto = contactos.get(i);
            nombres.add(contacto.getNombre());
            ids.add(contacto.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, nombres);

        Log.d("contactos", adapter.isEmpty() ? "vacio" : "lleno");
        lstContactos.setAdapter(adapter);
        lstContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idContacto = ids.get(position);
                String nombreContacto = nombres.get(position);

                Bundle bundle = new Bundle();
                bundle.putInt("id", idContacto);
                bundle.putString("nombre", nombreContacto);
                bundle.putString("telefono", contactoBD.persona(idContacto).getTelefono());
                bundle.putString("correo", contactoBD.persona(idContacto).getCorreo());
                bundle.putString("direccion", contactoBD.persona(idContacto).getDireccion());

                Intent intent = new Intent(context, ActualizarContactoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }});
    }

    @Override
    public void onItemClick(String nombres) {

    }
}