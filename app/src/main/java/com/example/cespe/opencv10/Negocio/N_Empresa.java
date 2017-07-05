package com.example.cespe.opencv10.Negocio;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Empresa;
import com.example.cespe.opencv10.Datos.D_Tipo_Trabajo;
import com.example.cespe.opencv10.Datos.Empresa_Objeto;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 25/06/2017.
 */

public class N_Empresa implements Retorno_Consulta {
    Context context;
    D_Empresa d_empresa;
    Retorno_Consulta ret_get_id;
    Retorno_Consulta ret_get_todas;
    Empresa_Objeto empresa_objeto;
    Boolean insertando=false;


    public N_Empresa(Context context) {
        this.context = context;
        d_empresa =new D_Empresa(context,this);
    }

    public void Registrar(Empresa_Objeto empresa_objeto){
        this.empresa_objeto=empresa_objeto;
        this.insertando=true;
        //verificando que no exista
        get_id(empresa_objeto.getNombre(),null);
    }


    public void get_id(String nombre,Retorno_Consulta ret){
        ret_get_id=ret;
        d_empresa.get_id(nombre);
    }

    public void get_empresas_todas(Retorno_Consulta ret){
        this.ret_get_todas=ret;
        d_empresa.get_empresas_todas();
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Empresa){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                mensaje("Insertardo correctamente",true);
            }else{
                mensaje("No se Pudo Insertar",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Empresa &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_empresa){
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                if(respuesta!=null&&!respuesta.isNull(0)) {
                    try {
                        String id = respuesta.getJSONObject(0).getString("id");
                        if(insertando){
                            mensaje("La Empresa ya existe",true);
                        }else{
                            ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                        }

                    } catch (JSONException e) {
                        mensaje("Error: " + e.toString(), false);
                    }
                }else{
                    //no encontrado
                    if(insertando){
                       d_empresa.insertar(this.empresa_objeto);
                    }else{
                        ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                    }
                }
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Empresa 79",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Empresa &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todas_empresas) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_todas.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Empresa 95",true);
            }
        }

        inicializar_var_globales();

    }

    private void inicializar_var_globales(){
        this.insertando=false;
        this.empresa_objeto=new Empresa_Objeto();
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }
    }
}
