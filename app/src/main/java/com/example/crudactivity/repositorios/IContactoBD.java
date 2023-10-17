package com.example.crudactivity.repositorios;

import com.example.crudactivity.modelos.Contacto;

import java.util.List;

public interface IContactoBD {

    Contacto persona(int id);
    Contacto persona(String nombre);

    List<Contacto> personas();

    void agregar(Contacto persona);
    void actualizar(int id, Contacto persona);
    void borrar(int id);
}
