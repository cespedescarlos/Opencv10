package com.example.cespe.opencv10.Negocio;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Tipo_Trabajo;
import com.example.cespe.opencv10.Presentacion.P_Tipo_Trabajo;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 25/06/2017.
 */

public class N_Tipo_Trabajo implements Retorno_Consulta {
    Context context;
    D_Tipo_Trabajo d_tipo_trabajo;
    Retorno_Consulta ret_get_id;
    Retorno_Consulta ret_get_todos;
    String nombre_a_insertar="";
    Boolean insertando=false;


    public N_Tipo_Trabajo(Context context) {
        this.context = context;
        d_tipo_trabajo=new D_Tipo_Trabajo(context,this);
    }

    public void Registrar(String nombre){
        this.nombre_a_insertar=nombre;
        this.insertando=true;
        //verificando que no exista
        get_id(nombre,null);
    }

    public void get_todos(Retorno_Consulta ret){
        this.ret_get_todos=ret;
        d_tipo_trabajo.get_todos();
    }


    public void get_id(String nombre,Retorno_Consulta ret){
        ret_get_id=ret;
        d_tipo_trabajo.get_id(nombre);
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Tipo_Trabajo){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                mensaje("Insertardo correctamente",true);
            }else{
                mensaje("No se Pudo Insertar",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Tipo_Trabajo &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_tipo_trabajo){
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                if(respuesta!=null&&!respuesta.isNull(0)) {
                    try {
                        String id = respuesta.getJSONObject(0).getString("id");
                        if(insertando){
                            mensaje("El Tipo de Trabajo ya existe",true);
                        }else{
                            ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                        }

                    } catch (JSONException e) {
                        mensaje("Error: " + e.toString(), false);
                    }
                }else{
                    //no encontrado
                    if(insertando){
                       d_tipo_trabajo.insertar(this.nombre_a_insertar);
                    }else{
                        ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                    }
                }
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Tipo_Tabajo 84",true);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Tipo_Trabajo &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todos_tipos_trabajo) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_todos.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Empresa 93",true);
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
