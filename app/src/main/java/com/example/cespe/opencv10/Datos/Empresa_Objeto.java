package com.example.cespe.opencv10.Datos;

import java.io.Serializable;

/**
 * Created by cespe on 25/06/2017.
 */

public class Empresa_Objeto implements Serializable {

    private String id="0";
    private String nombre;
    private String telefono;
    private String nombre_contacto;
    private String telefono_contacto;

    public Empresa_Objeto() {
    }

    public Empresa_Objeto(String id, String nombre, String telefono, String nombre_contacto, String telefono_contacto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nombre_contacto = nombre_contacto;
        this.telefono_contacto = telefono_contacto;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    @Override
    public String toString() {
        return "id: "+id+"; nombre: "+nombre;
    }
}
