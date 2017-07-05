package com.example.cespe.opencv10.Negocio;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Usuario;
import com.example.cespe.opencv10.Datos.Usuario_Objeto;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 25/06/2017.
 */

public class N_Usuario implements Retorno_Consulta {
    Context context;
    D_Usuario d_usuario;
    Retorno_Consulta ret_get_validar;
    Retorno_Consulta ret_get_todos;
    Retorno_Consulta ret_get_id_validar;
    Usuario_Objeto usuario_a_insertar =new Usuario_Objeto();
    Boolean insertando=false;


    public N_Usuario(Context context) {
        this.context = context;
        d_usuario =new D_Usuario(context,this);
    }

    public void Registrar(Usuario_Objeto usuario_objeto){
        this.usuario_a_insertar =usuario_objeto;
        this.insertando=true;
        //verificando que no exista
        get_id(usuario_objeto.getCorreo(),null);
    }

    public void get_todos(Retorno_Consulta ret){
        this.ret_get_todos=ret;
        d_usuario.get_todos();
    }


    public void get_id(String correo,Retorno_Consulta ret){
        ret_get_validar =ret;
        d_usuario.get_id(correo);
    }

    public void get_id_validar(Usuario_Objeto usuario_objeto,Retorno_Consulta ret){
        this.ret_get_id_validar =ret;
        d_usuario.get_id_rol_validar(usuario_objeto);
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Usuario &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Insertar_Usuario){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                mensaje("Registrado Correctamente",true);
            }else{
                mensaje("No se Pudo Registrar",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Usuario &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_usuario){
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                if(respuesta!=null&&!respuesta.isNull(0)) {
                    try {
                        String id = respuesta.getJSONObject(0).getString("id");
                        if(insertando){
                            mensaje("El Correo ya existe",true);
                        }else{
                            ret_get_validar.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                        }

                    } catch (JSONException e) {
                        mensaje("Error: " + e.toString(), false);
                    }
                }else{
                    //no encontrado
                    if(insertando){
                       d_usuario.insertar(this.usuario_a_insertar);
                    }else{
                        ret_get_validar.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
                    }
                }
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Usuario 90",true);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Usuario &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todos_usuario) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_todos.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Usuario 99",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Usuario &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_id_validar_usuario) {
            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.ret_get_id_validar.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,Clase_origen,operacion_especificacion);
            }
            else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Usuario 109",true);
            }
        }

        inicializar_var_globales();

    }

    private void inicializar_var_globales(){
        this.insertando=false;
        this.usuario_a_insertar =new Usuario_Objeto();
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }
    }
}
