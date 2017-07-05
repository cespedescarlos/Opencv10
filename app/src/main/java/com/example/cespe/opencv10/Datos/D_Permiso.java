package com.example.cespe.opencv10.Datos;

import android.content.Context;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 26/06/2017.
 */

public class D_Permiso implements Retorno_Consulta {
    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Permiso d_permiso;
    private Retorno_Consulta retorno_consulta;

    public D_Permiso(Context context, Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.retorno_consulta = retorno_consulta;
        this.d_permiso =this;
        this.volley_objecto=new Volley_Objecto();
    }


    public void get_id(String nombre){
        String url="FotoScan/Consultar_nombre_permiso.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_permiso,Retorno_Consulta.Operacion_Especifica_get_id_permiso);
    }

    public void get_todos(){
        String url="FotoScan/Consulta_todos_permisos.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_permiso,Retorno_Consulta.Operacion_Especifica_todos_permisos);
    }



    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                mensaje_extra,Retorno_Consulta.D_Permiso,operacion_especificacion);
    }
}
