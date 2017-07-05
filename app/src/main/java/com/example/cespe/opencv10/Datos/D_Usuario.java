package com.example.cespe.opencv10.Datos;

import android.content.Context;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 26/06/2017.
 */

public class D_Usuario implements Retorno_Consulta {
    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Usuario d_usuario;
    private Retorno_Consulta retorno_consulta;

    public D_Usuario(Context context, Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.retorno_consulta = retorno_consulta;
        this.d_usuario =this;
        this.volley_objecto=new Volley_Objecto();
    }

    public void get_id_rol_validar(Usuario_Objeto usuario_objeto){
        String url="FotoScan/Validar_Usuario.php?correo="+usuario_objeto.getCorreo()+
                "&password="+usuario_objeto.getPassword();

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_usuario,Retorno_Consulta.Operacion_Especifica_get_id_validar_usuario);

    }

    public void get_id(String correo){
        String url="FotoScan/Consultar_correo_usuario.php?correo="+correo;

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_usuario,Retorno_Consulta.Operacion_Especifica_get_id_usuario);
    }

    public void insertar(Usuario_Objeto usuario_objeto){
        String url="FotoScan/Insertar_Usuario.php?nombre="+usuario_objeto.getNombre()+
                "&correo="+usuario_objeto.getCorreo()+"&password="+usuario_objeto.getPassword()+
                "&id_rol="+usuario_objeto.getId_rol();

        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context, d_usuario,Retorno_Consulta.Operacion_Especifica_Insertar_Usuario);
    }

    public void get_todos(){
        String url="FotoScan/Consulta_todos_usuario.php";

        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context, d_usuario,Retorno_Consulta.Operacion_Especifica_todos_usuario);
    }



    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                mensaje_extra,Retorno_Consulta.D_Usuario,operacion_especificacion);
    }
}
