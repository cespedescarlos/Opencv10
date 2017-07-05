package com.example.cespe.opencv10.Negocio;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Rol;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Presentacion.P_Rol;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 25/06/2017.
 */

public class N_Rol implements Retorno_Consulta {
    Context context;
    D_Rol d_rol;
    Retorno_Consulta ret_get_id;
    Retorno_Consulta ret_get_max_id;
    Retorno_Consulta ret_get_todos;
    //Retorno_Consulta ret_verificar_rol_permiso;
   // Retorno_Consulta ret_asignar_rol_permiso;
    Rol_Permiso_Objeto rol_permiso_asignar;
    P_Rol p_rol;
    String nombre_a_insertar="";
    Boolean insertando=false;


    public N_Rol(Context context,P_Rol p_rol) {
        this.context = context;
        this.p_rol=p_rol;
        d_rol =new D_Rol(context,this);
    }

    public N_Rol(Context context) {
        this.context = context;
        this.p_rol=null;
        d_rol =new D_Rol(context,this);
    }

    public void Registrar(String nombre){
        this.nombre_a_insertar=nombre;
        this.insertando=true;
        //verificando que no exista
        get_id(nombre,null);
    }

    public void get_todos(Retorno_Consulta ret){
        this.ret_get_todos=ret;
        d_rol.get_todos();
    }


    public void get_id(String nombre,Retorno_Consulta ret){
        ret_get_id=ret;
        d_rol.get_id(nombre);
    }

    private void verificar_permiso_rol(String id_permiso, String id_rol){
        d_rol.verificar_permiso_rol(id_permiso,id_rol);
    }

    public void get_max_id(Retorno_Consulta ret){
        this.ret_get_max_id=ret;
        d_rol.get_max_id();
    }

    public void Asignar_rol_permiso(Rol_Permiso_Objeto rol_permiso_objeto){
        rol_permiso_asignar=rol_permiso_objeto;
        d_rol.verificar_permiso_rol(rol_permiso_objeto.getPermiso_objeto().getId(),
                rol_permiso_objeto.getRol_objeto().getId());
        //d_rol.asignar_rol_permiso(id_permiso,id_rol);
    }

    private void continuar_asignando(){
        if(this.rol_permiso_asignar!=null){
            d_rol.asignar_rol_permiso(rol_permiso_asignar.getPermiso_objeto().getId(),
                    rol_permiso_asignar.getRol_objeto().getId());
        }
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                if(p_rol!=null){
                    get_max_id(this);
                }else {
                    mensaje("Registrado Correctamente", true);
                }
            }else{
                mensaje("No se Pudo Registar",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_rol){
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                if(respuesta!=null&&!respuesta.isNull(0)) {
                    try {
                        String id = respuesta.getJSONObject(0).getString("id");
                        if(insertando){
                            mensaje("El Rol ya existe",true);
                        }else{
                            ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                        }

                    } catch (JSONException e) {
                        mensaje("Error: " + e.toString(), false);
                    }
                }else{
                    //no encontrado
                    if(insertando){
                       d_rol.insertar(this.nombre_a_insertar);
                    }else{
                        ret_get_id.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                    }
                }
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Rol 84",true);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todos_rol) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_todos.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Rol 93",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_verificar_permiso_rol) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
               // this.ret_verificar_rol_permiso.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);

                try {
                    int c=respuesta.getJSONObject(0).getInt("cantidad");
                    if(c==0){
                        continuar_asignando();
                    }else{
                        mensaje("El Rol ya Cuenta con este Permiso",false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Rol 108",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Max_Id_Rol) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                try {
                    String res=respuesta.getJSONObject(0).getString("id");
                    if(p_rol!=null){
                        p_rol.insertado_ok(res);
                    }else{
                        mensaje("max id rol: "+res,true);
                    }

                } catch (JSONException e) {
                    mensaje("Error: " + e.toString(), false);
                }

            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Rol 131",true);
            }
        }

        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Rol &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_asignar_permiso_rol) {
            if (Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                //mensaje("Permiso Asignado Correctamente",true);
            }else{
                mensaje("Error al Asignar el Permiso, Vuelva a Intentarlo",false);
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
