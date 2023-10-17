package com.example.crudactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudactivity.controladores.ContactoBD;
import com.example.crudactivity.modelos.Contacto;

public class BuscarContactoActivity extends AppCompatActivity {

    Context context;

    EditText txtNombre;
    Button btnBuscar;
    ContactoBD contactoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_contacto);
        init();
    }

    private void init() {
        context = this;
        txtNombre = findViewById(R.id.bus_txt_nombre);
        btnBuscar = findViewById(R.id.bus_btn_buscar);

        btnBuscar.setOnClickListener(v -> buscar());
    }

    private void buscar() {
        String nombre = txtNombre.getText().toString();
        Contacto contacto = buscarEnBD(nombre);
        if (contacto != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", contacto.getId());
            bundle.putString("nombre", contacto.getNombre());
            bundle.putString("telefono", contacto.getTelefono());
            bundle.putString("correo", contacto.getCorreo());
            bundle.putString("direccion", contacto.getDireccion());

            Intent intent = new Intent(context, ActualizarContactoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(context, "No se encontró el contacto", Toast.LENGTH_LONG).show();
        }
    }

    private Contacto buscarEnBD(String nombre) {
        contactoBD = new ContactoBD(context, "contactosDB.db", null, 1);
        Contacto contacto = contactoBD.persona(nombre);
        return contacto;
    }
}