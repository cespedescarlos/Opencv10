package com.example.cespe.opencv10.Datos;

import java.io.Serializable;

/**
 * Created by cespe on 25/06/2017.
 */

public class Tipo_Trabajo_Objecto implements Serializable {
    private String id;
    private String nombre;

    public Tipo_Trabajo_Objecto() {
        id=nombre="";
    }

    public Tipo_Trabajo_Objecto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "id: "+id+"; nombre: "+nombre;
    }
}
