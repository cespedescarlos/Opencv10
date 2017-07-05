package com.example.cespe.opencv10.Datos;

import android.content.Context;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;

import org.json.JSONArray;

/**
 * Created by cespe on 10/06/2017.
 */

public class D_Documento implements Retorno_Consulta {

    private Volley_Objecto volley_objecto;
    private Context context;
    private D_Documento d_documento;
    private Retorno_Consulta retorno_consulta;

    public D_Documento(Context context,Retorno_Consulta retorno_consulta) {
        this.volley_objecto=new Volley_Objecto();
        this.retorno_consulta=retorno_consulta;
        this.context=context;
        this.d_documento=this;
    }

    public void insertar(String nombre,String fecha, double cotizacion, double precio, String ruta,
                         int cantidad, String estado_placas, int id_trabajo, int id_empresa, int id_usuario){
        String url="FotoScan/Insertar_Documento.php?nombre="+nombre+"&fecha="+fecha+"&" +
                "cotizacion="+cotizacion+"&precio="+precio+"&ruta_archivos="+ruta+"&cantidad="+cantidad+
                "&" + "estado_placas="+estado_placas+"&id_trabajo="+id_trabajo+"&id_empresa="+id_empresa+
                "&id_usuario="+id_usuario;
       // Toast.makeText(context,url,Toast.LENGTH_LONG).show();
        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_documento,Retorno_Consulta.Operacion_Especifica_Insertar_Documento);
    }

    public void get_max_id(){
        String url="FotoScan/Consulta_max_id_doc.php";
        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_documento,Retorno_Consulta.Operacion_Especifica_Max_Id_Documento);
    }

    public void get_todos(){
        String url="FotoScan/Consulta_todos_documentos.php";
        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_documento,Retorno_Consulta.Operacion_Especifica_todos_Documento);
    }

    public void get_segun_nombre(String nombre){
        String url="FotoScan/Consulta_documentos_segun_nombre.php?nombre="+nombre;
        volley_objecto.setPagina(url);
       // Toast.makeText(context,"url: "+volley_objecto.getUrl(),Toast.LENGTH_LONG).show();
        volley_objecto.consulta_get(context,d_documento,Retorno_Consulta.Operacion_Especifica_get_documento_segun_nombre);
    }

    public void get_documento_id_imagen(String id_imagen){
        String url="FotoScan/Consulta_doc_id_imagen.php?id="+id_imagen;
        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_documento,Retorno_Consulta.Operacion_Especifica_get_segun_id_imagen_Documento);
    }

    @Override
    public void finalizo(JSONArray respuesta,int volley_operacion,String mensaje,String mensaje_extra,
                         String Clase_origen,String operacion_especificacion) {

            this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,
                    mensaje_extra,Retorno_Consulta.D_Documento,operacion_especificacion);
    }

}
