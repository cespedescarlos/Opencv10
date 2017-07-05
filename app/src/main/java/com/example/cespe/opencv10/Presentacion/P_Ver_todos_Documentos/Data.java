package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos;

import com.example.cespe.opencv10.Momentos_Hu_Serial;

import java.io.Serializable;

/**
 * Created by cespe on 23/06/2017.
 */

public class Data implements Serializable {
    private String id,fecha,nombre,ruta,tipo,empresa;
    private Momentos_Hu_Serial hu_serial1;
    private Momentos_Hu_Serial hu_serial2;
    private String nombre_imagen,direccion;
    private float tamaño;
    private int filas,columnas;

    public Data() {
    }

    public Data(String id, String fecha, String nombre, String ruta, String tipo, String empresa) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.ruta = ruta;
        this.tipo = tipo;
        this.empresa = empresa;
    }

    public Data(String id, String fecha, String nombre, String ruta, String tipo, String empresa,
                Momentos_Hu_Serial hu_serial1, Momentos_Hu_Serial hu_serial2, String nombre_imagen,
                String direccion, float tamaño, int filas, int columnas) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.ruta = ruta;
        this.tipo = tipo;
        this.empresa = empresa;
        this.hu_serial1 = hu_serial1;
        this.hu_serial2 = hu_serial2;
        this.nombre_imagen = nombre_imagen;
        this.direccion = direccion;
        this.tamaño = tamaño;
        this.filas = filas;
        this.columnas = columnas;
    }

    public Momentos_Hu_Serial getHu_serial1() {
        return hu_serial1;
    }

    public void setHu_serial1(Momentos_Hu_Serial hu_serial1) {
        this.hu_serial1 = hu_serial1;
    }

    public Momentos_Hu_Serial getHu_serial2() {
        return hu_serial2;
    }

    public void setHu_serial2(Momentos_Hu_Serial hu_serial2) {
        this.hu_serial2 = hu_serial2;
    }

    public String getNombre_imagen() {
        return nombre_imagen;
    }

    public void setNombre_imagen(String nombre_imagen) {
        this.nombre_imagen = nombre_imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getTamaño() {
        return tamaño;
    }

    public void setTamaño(float tamaño) {
        this.tamaño = tamaño;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "id: "+id+"; nom: "+nombre;
    }
}
