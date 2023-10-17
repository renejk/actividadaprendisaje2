package com.example.crudactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Context context;

    Button  btnBuscar, btnListar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        context = this;
        btnBuscar = findViewById(R.id.btn_buscar);
        btnListar = findViewById(R.id.btn_listar);
        btnRegistrar = findViewById(R.id.btn_registrar);

        btnBuscar.setOnClickListener(v -> buscar());
        btnListar.setOnClickListener(v -> listar());
        btnRegistrar.setOnClickListener(v -> registrar());
    }


    private void registrar() {
        Intent intent = new Intent(context, ActualizarContactoActivity.class);
        startActivity(intent);
    }

    private void buscar() {
        Intent intent = new Intent(context, BuscarContactoActivity.class);
        startActivity(intent);
    }


    private void listar() {
        Intent intent = new Intent(context, ListadoContactosActivity.class);
        startActivity(intent);
    }
}