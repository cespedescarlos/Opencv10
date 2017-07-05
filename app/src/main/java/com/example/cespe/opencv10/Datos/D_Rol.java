package com.example.cespe.opencv10.Datos;

import android.content.Context;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 26/06/2017.
 */

public class D_Rol implements Retorno_Consulta {
    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Rol d_rol;
    private Retorno_Consulta retorno_consulta;

    public D_Rol(Context context, Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.retorno_consulta = retorno_consulta;
        this.d_rol=this;
        this.volley_objecto=new Volley_Objecto();
    }

    public void insertar(String nombre){
        String url="FotoScan/Insertar_Rol.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_Insertar_Rol);

    }

    public void get_id(String nombre){
        String url="FotoScan/Consultar_nombre_Rol.php?nombre="+nombre;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_get_id_rol);
    }

    public void get_todos(){
        String url="FotoScan/Consulta_todos_rol.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_todos_rol);
    }

    public void verificar_permiso_rol(String id_permiso,String id_rol){
        String url="FotoScan/Consultar_rol_permiso.php?rol="+id_rol+"&permiso="+id_permiso;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_verificar_permiso_rol);
    }

    public void asignar_rol_permiso(String id_permiso,String id_rol){
        String url="FotoScan/Insertar_RolPermiso.php?permiso="+id_permiso+"&rol="+id_rol;

        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_asignar_permiso_rol);
    }

    public void get_max_id(){
        String url="FotoScan/Consulta_max_id_rol.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_rol,Retorno_Consulta.Operacion_Especifica_Max_Id_Rol);
    }



    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                mensaje_extra,Retorno_Consulta.D_Rol,operacion_especificacion);
    }
}
