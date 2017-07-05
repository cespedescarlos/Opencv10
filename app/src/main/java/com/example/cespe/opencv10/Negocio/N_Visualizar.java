package com.example.cespe.opencv10.Negocio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.MainActivity;
import com.example.cespe.opencv10.Mat_Basicos;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Presentacion.P_Ins_Documento;
import com.example.cespe.opencv10.Presentacion.P_Visualizar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by cespe on 11/06/2017.
 */

public class N_Visualizar implements Retorno_Consulta {
    private List<Momentos_Hu> lista_momentos;
    private Context context;
    private N_Documento n_documento;
    private P_Visualizar p_visualizar;
    ProgressDialog loading=null;

    public N_Visualizar(Context context) {
        this.context=context;
        this.n_documento=new N_Documento(context);
    }

    public List<Momentos_Hu> getLista_momentos() {
        return lista_momentos;
    }

    public List<Momentos_Hu> procesar(Uri direccion){

        this.lista_momentos=null;
        Mat_Basicos mat_basicos=new Mat_Basicos(context);
        List<Momentos_Hu> lista=mat_basicos.procesar_HU_solo(direccion,0);
        if(lista!=null&&!lista.isEmpty()){
            this.lista_momentos=lista;
            return lista;
        }else{
            mensaje("Error al Procesar la Imagen, porfavor intentelo con una Imagen en mejor Calidad",false);
        }
        return null;
    }

    public void guardar_fotos_angulos(Uri ruta){
        Mat_Basicos mat_basicos=new Mat_Basicos(context);
        mat_basicos.guardar_fotos_angulos(ruta);
    }

    public void comparar(Uri direccion, P_Visualizar visualizar){
        this.p_visualizar=visualizar;
        procesar(direccion);
        if(lista_momentos!=null) {
            n_documento.get_id_imagen_segun_momento(this, this.lista_momentos.get(0));
        }
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra,
                         String Clase_origen,String operacion_especificacion) {
        /*if(Volley_Objecto.Operacion_Select==volley_operacion && Clase_origen==Retorno_Consulta.N_Documento
                && operacion_especificacion=="max_id"){
            if(respuesta!=null && Volley_Objecto.mensaje_Select_vacia!=mensaje_extra) {
                try {
                    String id_max = respuesta.getJSONObject(0).getString("MAX(id)");
                } catch (JSONException e) {
                    mensaje("Error: " + e.toString(), false);
                }
            }else{
                mensaje("Error al Obtener el Max Id, "+mensaje_extra,false);
            }
        }*/
        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.N_Documento &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Id_segun_momento_Imagen) {

            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                if(respuesta!=null&&!respuesta.isNull(0)) {
                    try {
                        String id_imagen = respuesta.getJSONObject(0).getString("id_imagen");
                        //mensaje("Imagen Encontrada: " + id_imagen, true);
                        this.p_visualizar.resultado_comparar(true,id_imagen);

                    } catch (JSONException e) {
                        mensaje("Error: " + e.toString(), false);
                    }
                }else{
                   // mensaje("Imagen no Encontrada",true);
                    this.p_visualizar.resultado_comparar(false,"");
                }
            }else{
                mensaje("Error Fallo en Conexion Con el Servidor; N_Visualizar 106",true);
            }
        }
    }
}
