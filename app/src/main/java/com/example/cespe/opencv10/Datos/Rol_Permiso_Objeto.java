package com.example.cespe.opencv10.Datos;

import java.io.Serializable;

/**
 * Created by cespe on 26/06/2017.
 */

public class Rol_Permiso_Objeto implements Serializable {
    private Rol_Objeto rol_objeto;
    private Permiso_Objeto permiso_objeto;

    public Rol_Permiso_Objeto() {
    }

    public Rol_Permiso_Objeto(Rol_Objeto rol_objeto, Permiso_Objeto permiso_objeto) {
        this.rol_objeto = rol_objeto;
        this.permiso_objeto = permiso_objeto;
    }

    public Rol_Objeto getRol_objeto() {
        return rol_objeto;
    }

    public void setRol_objeto(Rol_Objeto rol_objeto) {
        this.rol_objeto = rol_objeto;
    }

    public Permiso_Objeto getPermiso_objeto() {
        return permiso_objeto;
    }

    public void setPermiso_objeto(Permiso_Objeto permiso_objeto) {
        this.permiso_objeto = permiso_objeto;
    }
}
