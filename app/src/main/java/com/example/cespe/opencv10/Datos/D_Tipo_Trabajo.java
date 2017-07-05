package com.example.cespe.opencv10.Datos;

import android.content.Context;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 25/06/2017.
 */

public class D_Tipo_Trabajo implements Retorno_Consulta{
    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Tipo_Trabajo d_tipo_trabajo;
    private Retorno_Consulta retorno_consulta;

    public D_Tipo_Trabajo(Context context, Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.retorno_consulta = retorno_consulta;
        this.volley_objecto=new Volley_Objecto();
        d_tipo_trabajo=this;
    }

    public void insertar(String nombre){
        String url="FotoScan/Insertar_Tipo_Trabajo.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_tipo_trabajo,Retorno_Consulta.Operacion_Especifica_Insertar_Tipo_trabajo);

    }

    public void get_id(String nombre){
        String url="FotoScan/Consultar_nombre_Tipo_Trabajo.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_tipo_trabajo,Retorno_Consulta.Operacion_Especifica_get_id_tipo_trabajo);
    }

    public void get_todos(){
        String url="FotoScan/Consulta_todos_tipos_trabajos.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_tipo_trabajo,Retorno_Consulta.Operacion_Especifica_todos_tipos_trabajo);
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                mensaje_extra,Retorno_Consulta.D_Tipo_Trabajo,operacion_especificacion);
    }
}
