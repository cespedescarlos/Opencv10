package com.example.cespe.opencv10.Datos;

import android.content.Context;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 25/06/2017.
 */

public class D_Empresa implements Retorno_Consulta{
    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Empresa d_empresa;
    private Retorno_Consulta retorno_consulta;

    public D_Empresa(Context context, Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.retorno_consulta = retorno_consulta;
        this.volley_objecto=new Volley_Objecto();
        d_empresa =this;
    }

    public void insertar(Empresa_Objeto empresa_objeto){
        String url="FotoScan/Insertar_Empresa.php?nombre="+empresa_objeto.getNombre()+
                "&telefono="+empresa_objeto.getTelefono()+"&nombre_contacto="+
                empresa_objeto.getNombre_contacto()+"&telefono_contacto="+empresa_objeto.getTelefono_contacto();

        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context, d_empresa,Retorno_Consulta.Operacion_Especifica_Insertar_Empresa);

    }

    public void get_id(String nombre){
        String url="FotoScan/Consultar_nombre_Empresa.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_empresa,Retorno_Consulta.Operacion_Especifica_get_id_empresa);
    }

    public void get_empresas_todas(){
        String url="FotoScan/Consulta_todas_empresas.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_empresa,Retorno_Consulta.Operacion_Especifica_todas_empresas);

    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                mensaje_extra,Retorno_Consulta.D_Empresa,operacion_especificacion);
    }
}
