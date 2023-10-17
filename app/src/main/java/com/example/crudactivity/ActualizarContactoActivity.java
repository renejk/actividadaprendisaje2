package com.example.crudactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudactivity.controladores.ContactoBD;
import com.example.crudactivity.modelos.Contacto;

public class ActualizarContactoActivity extends AppCompatActivity {

    Context context;
    EditText txtNombre, txtTelefono, txtCorreo, txtDireccion;
    Button btnActualizar, btnEliminar, btnGuardar;
    int id;
    ContactoBD contactoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contacto);
        init();
    }

    private void init() {
        context = this;
        txtNombre = findViewById(R.id.ges_txt_nombre);
        txtTelefono = findViewById(R.id.ges_txt_telefono);
        txtCorreo = findViewById(R.id.ges_txt_correo);
        txtDireccion = findViewById(R.id.ges_txt_direccion);
        btnActualizar = findViewById(R.id.ges_btn_actualizar);
        btnEliminar = findViewById(R.id.ges_btn_eliminar);
        btnGuardar = findViewById(R.id.ges_btn_guardar);

        btnGuardar.setOnClickListener(v -> actualizar());
        btnActualizar.setOnClickListener(v -> actualizar());
        btnEliminar.setOnClickListener(v -> borrar());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getInt("id");
            String nombre = bundle.getString("nombre");
            String telefono = bundle.getString("telefono");
            String correo = bundle.getString("correo");
            String direccion = bundle.getString("direccion");

            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtCorreo.setText(correo);
            txtDireccion.setText(direccion);
            btnGuardar.setEnabled(false);
        }else {
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }


    private Contacto llenarCampos() {
        Contacto contacto = new Contacto();
        String nombre = txtNombre.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String correo = txtCorreo.getText().toString();
        String direccion = txtDireccion.getText().toString();

        contacto.setId(id);
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setCorreo(correo);
        contacto.setDireccion(direccion);

        return contacto;

    }

    private void actualizar() {
        Contacto contacto = llenarCampos();
        contactoBD = new ContactoBD(context, "contactosDB.db", null, 1);

        if (id > 0) {
            contactoBD.actualizar(id,contacto);
            Toast.makeText(context, "Contacto Actualizado", Toast.LENGTH_LONG).show();
        } else {
            contactoBD.agregar(contacto);
            btnGuardar.setEnabled(false);
            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
            Toast.makeText(context, "Contacto Nuevo Agregado", Toast.LENGTH_LONG).show();
        }
    }

    private void borrar() {
        Contacto contacto = llenarCampos();
        contactoBD = new ContactoBD(context, "contactosDB.db", null, 1);

        if (id > 0) {
            contactoBD.borrar(id);
            limpiarCampos();
            btnGuardar.setEnabled(true);
            btnActualizar.setEnabled(false);
            btnEliminar.setEnabled(false);
            Toast.makeText(context, "Contacto borrado", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(context, "No es Posible Borrar", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }
}