package com.example.cespe.opencv10.Datos;

import java.io.Serializable;

/**
 * Created by cespe on 26/06/2017.
 */

public class Permiso_Objeto implements Serializable {
    private String id;
    private String nombre;

    public Permiso_Objeto() {
    }

    public Permiso_Objeto(String id, String nombre) {
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
}
