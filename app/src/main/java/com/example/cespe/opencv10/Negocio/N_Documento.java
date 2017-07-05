package com.example.cespe.opencv10.Negocio;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Datos.D_Documento;
import com.example.cespe.opencv10.Datos.D_Imagen;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Presentacion.P_Ins_Documento;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos.Info_Activity;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 10/06/2017.
 */

public class N_Documento implements Retorno_Consulta{
    private Context context;
    private D_Documento d_documento;
    private D_Imagen d_imagen;
    ProgressDialog loading=null;
    private Retorno_Consulta retorno_consulta_id_imagen_momento=null;
    private Retorno_Consulta retorno_consulta_get_doc_id_imagen=null;
    private Retorno_Consulta retorno_consulta_todos_doc=null;
    private Retorno_Consulta retorno_consulta_get_doc_segun_nombre=null;
    private Momentos_Hu momentos_hu;
    private Momentos_Hu momentos_hu2;
    String nombre_imagen="sin definir";
   // Uri ruta_uri;
    String  ruta_imagen="sin definir";
    float tamaño=-1;
    int filas,columnas=-1;

    P_Ins_Documento p_ins_documento=null;
    Info_Activity info_activity=null;

    public N_Documento(Context context) {
        this.context=context;
        d_documento=new D_Documento(this.context,this);
        d_imagen=new D_Imagen(this.context,this);
    }

    public void set_datos_imagen(String nombre_imagen,String ruta, float tamaño, int filas, int columnas){
        this.nombre_imagen=nombre_imagen;
        this.ruta_imagen=ruta;
        this.tamaño=tamaño;
        this.filas=filas;
        this.columnas=columnas;
    }

    public void setMomentos_hu(Momentos_Hu momentos_hu) {
        this.momentos_hu = momentos_hu;
    }

    public void setMomentos_hu2(Momentos_Hu momentos_hu2) {
        this.momentos_hu2 = momentos_hu2;
    }

    public void Registrar_Documento(String nombre, String fecha, double cotizacion, double precio, String ruta,
                                    int cantidad, String estado_placas, String id_tipo_trabajo, String id_empresa, String usuario,
                                    Momentos_Hu momentos, Momentos_Hu momentos2, P_Ins_Documento p_ins_documento,
                                    String id_usuario){
      //  this.ruta_uri=ruta_uri;
        this.p_ins_documento=p_ins_documento;
        this.momentos_hu=momentos;
        this.momentos_hu2=momentos2;
        d_documento.insertar(nombre,fecha,cotizacion,precio,ruta,cantidad,estado_placas,
                Integer.valueOf(id_tipo_trabajo),Integer.valueOf(id_empresa),Integer.valueOf(id_usuario));
    }

    public void Registrar_Imagen(String nombre, float size, String direccion,int filas, int columnas, int id_doc){
        d_imagen.insertar_imagen(nombre,size,direccion,filas,columnas,id_doc,momentos_hu,momentos_hu2);
    }

    public void Registrar_Imagen(String nombre, float size, String direccion,int filas, int columnas, int id_doc,
                                 Info_Activity info_activity){
        this.info_activity=info_activity;
        d_imagen.insertar_imagen(nombre,size,direccion,filas,columnas,id_doc,momentos_hu,momentos_hu2);
    }

    public void get_max_id(Retorno_Consulta ret){
      //  retorno_consulta=ret;
        d_documento.get_max_id();
    }

    public void get_id_imagen_segun_momento(Retorno_Consulta ret,Momentos_Hu momento){
        this.retorno_consulta_id_imagen_momento=ret;
        d_imagen.get_id_imagen_segun_momento(momento);
    }

    public void get_documento_id_imagen(Retorno_Consulta ret,String id_imagen){
        this.retorno_consulta_get_doc_id_imagen=ret;
        d_documento.get_documento_id_imagen(id_imagen);
    }

    public void get_todos_documento(Retorno_Consulta ret){
        this.retorno_consulta_todos_doc=ret;
        d_documento.get_todos();
    }

    public void get_doc_segun_nombre(String nombre,Retorno_Consulta ret){
        this.retorno_consulta_get_doc_segun_nombre=ret;
        d_documento.get_segun_nombre(nombre);
    }


    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje,
                         String mensaje_extra,String Clase_origen,String operacion_especificacion) {

        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Documento){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                get_max_id(this);
            }else{
            mensaje("Error Conexion Inestable, porfavor Vuelva a Intentarlo: "+mensaje+mensaje_extra,false);
            }
        }
        if(Volley_Objecto.Operacion_Insert==volley_operacion && Clase_origen==Retorno_Consulta.D_Imagen){
            if(Volley_Objecto.mensaje_Insert_ok.equals(mensaje)) {
                if(p_ins_documento!=null){
                    p_ins_documento.mensaje_insercion("Documento e Imagen: "+mensaje);
                }else {
                    if (info_activity != null) {
                        info_activity.mensaje_insercion("Imagen: "+mensaje);
                    }
                }
               // mensaje("Documento e Imagen: "+mensaje,true);
            }else{
                if(p_ins_documento!=null) {
                    p_ins_documento.mensaje_insercion("Error Conexion Inestable, porfavor Vuelva a Intentarlo: " + mensaje + mensaje_extra);
                }else {
                    if (info_activity != null) {
                        info_activity.mensaje_insercion("Error Conexion Inestable, porfavor Vuelva a Intentarlo: " + mensaje + mensaje_extra);
                    }
                }
                //mensaje("Error Conexion Inestable, porfavor Vuelva a Intentarlo: "+mensaje+mensaje_extra,false);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.D_Documento
                && operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Max_Id_Documento){
            if(Volley_Objecto.mensaje_Select_ok.equals(mensaje)&&respuesta!=null &&
                    Volley_Objecto.mensaje_Select_vacia!=mensaje_extra) {
                try {
                    String id_max=respuesta.getJSONObject(0).getString("MAX(id)");
                    this.Registrar_Imagen(this.nombre_imagen,this.tamaño,this.ruta_imagen,this.filas,
                            this.columnas,Integer.valueOf(id_max));
                } catch (JSONException e) {
                    mensaje("Error: "+e.toString(),false);
                }
            }else{
                mensaje("Error Conexion Inestable, porfavor Vuelva a Intentarlo: Documento: "+mensaje+mensaje_extra,false);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.D_Imagen &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Id_segun_momento_Imagen) {

            //if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                this.retorno_consulta_id_imagen_momento.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                        Retorno_Consulta.N_Documento,operacion_especificacion);
            //}
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.D_Documento &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_segun_id_imagen_Documento) {

            this.retorno_consulta_get_doc_id_imagen.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                    Retorno_Consulta.N_Documento,operacion_especificacion);
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.D_Documento &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_todos_Documento) {
            //    mensaje("longi: "+respuesta.length(),true);
            this.retorno_consulta_todos_doc.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                    Retorno_Consulta.N_Documento,operacion_especificacion);
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.D_Documento &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_documento_segun_nombre) {
            //    mensaje("longi: "+respuesta.length(),true);
           // mensaje("sale",true);
            this.retorno_consulta_get_doc_segun_nombre.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                    Retorno_Consulta.N_Documento,operacion_especificacion);
        }
    }


    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }
    }
}
