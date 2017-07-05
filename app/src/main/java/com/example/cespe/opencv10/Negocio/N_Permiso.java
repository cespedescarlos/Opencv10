package com.example.cespe.opencv10.Negocio;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Permiso;
import com.example.cespe.opencv10.Datos.D_Rol;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 25/06/2017.
 */

public class N_Permiso implements Retorno_Consulta {
    Context context;
    D_Permiso d_permiso;
    Retorno_Consulta ret_get_id;
    Retorno_Consulta ret_get_todos;
    String nombre_a_insertar="";
    Boolean insertando=false;


    public N_Permiso(Context context) {
        this.context = context;
        d_permiso =new D_Permiso(context,this);
    }


    public void get_todos(Retorno_Consulta ret){
        this.ret_get_todos=ret;
        d_permiso.get_todos();
    }


    public void get_id(String nombre,Retorno_Consulta ret){
        ret_get_id=ret;
        d_permiso.get_id(nombre);
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Permiso &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_permiso){
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                        //String id = respuesta.getJSONObject(0).getString("id");
                            ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Permiso 53",true);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Permiso &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todos_permisos) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_todos.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Rol 93",true);
            }
        }

        inicializar_var_globales();

    }

    private void inicializar_var_globales(){
        this.insertando=false;
        this.nombre_a_insertar="";
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }
    }
}
