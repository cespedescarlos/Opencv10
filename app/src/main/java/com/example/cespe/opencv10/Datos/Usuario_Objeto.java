package com.example.cespe.opencv10.Datos;

import java.io.Serializable;

/**
 * Created by cespe on 26/06/2017.
 */

public class Usuario_Objeto implements Serializable {
    private String id;
    private String correo;
    private String nombre;
    private String password;
    private String id_rol;

    public Usuario_Objeto() {
    }

    public Usuario_Objeto(String id, String correo, String nombre, String password, String id_rol) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
        this.id_rol = id_rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return "id: "+id+"; correo: "+correo;
    }
}
