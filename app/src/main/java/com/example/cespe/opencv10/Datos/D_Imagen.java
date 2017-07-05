package com.example.cespe.opencv10.Datos;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Mat_Basicos;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Negocio.N_Visualizar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.List;

/**
 * Created by cespe on 11/06/2017.
 */

public class D_Imagen implements Retorno_Consulta {
    private Volley_Objecto volley_objecto;
    private Context context;
    private Retorno_Consulta retorno_consulta;
    private D_Imagen d_imagen;
    //Uri ruta_uri;
    private Momentos_Hu momentos_hu;
    private Momentos_Hu momentos_hu2;
    private int contador_momentos;

    public D_Imagen(Context context,Retorno_Consulta retorno_consulta) {
        this.context = context;
        this.volley_objecto=new Volley_Objecto();
        this.retorno_consulta=retorno_consulta;
        d_imagen=this;
        contador_momentos=1;
    }

    public void get_max_id(){
        String url="FotoScan/Consulta_max_id_imagen.php";
        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Max_Id_Imagen);
    }

    public void get_id_imagen_segun_momento(Momentos_Hu momento){
        String url="FotoScan/Consulta_momento.php?m0="+momento.getM0()+"&m1="+momento.getM1()+
                "&m2="+momento.getM2()+"&m3="+momento.getM3()+"&m4="+momento.getM4()+"&m5="+momento.getM5()
                +"&m6="+momento.getM6();
        volley_objecto.setPagina(url);
        volley_objecto.consulta_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Id_segun_momento_Imagen);
    }


    public void insertar_imagen(String nombre, float size, String direccion, int filas, int columnas,
                                int id_doc,Momentos_Hu momentos,Momentos_Hu momentos2){
        this.momentos_hu=momentos;
        //this.ruta_uri=ruta_uri;
        this.momentos_hu2=momentos2;
        String url="FotoScan/Insertar_Imagen.php?nombre="+nombre+"&size="+size+
            "&direccion="+direccion+"&filas="+filas+"&columnas="+columnas+"&id_doc="+id_doc;
        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Insertar_Imagen);
    }

    public void insertar_momentos(int id_imagen){

        //insertando momentos procesados con angulos


        /*Mat_Basicos mat_basicos=new Mat_Basicos(context);
        List<Momentos_Hu> lista=mat_basicos.procesar_HU_angulos(0);
        if(lista!=null&&!lista.isEmpty()){
           for (int i=0;i<lista.size();i++){
               Momentos_Hu moment=lista.get(i);
               String url="FotoScan/Insertar_MomentosHu.php?posicion_momento="+moment.getPosicion_momento()+
                       "&m0="+moment.getM0()+"&m1="+moment.getM1()+"&m2="+moment.getM2()+
                       "&m3="+moment.getM3()+"&m4="+moment.getM4()+"&m5="+moment.getM5()+
                       "&m6="+moment.getM6()+"&promedio="+moment.getPromedio()+"&id_imagen="+id_imagen;
               volley_objecto.setPagina(url);
               volley_objecto.insert_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Insertar_MomentosHu);
           }

        }*/

        // insertando momentos originales

        String url="FotoScan/Insertar_MomentosHu.php?posicion_momento="+momentos_hu.getPosicion_momento()+
                "&m0="+momentos_hu.getM0()+"&m1="+momentos_hu.getM1()+"&m2="+momentos_hu.getM2()+
                "&m3="+momentos_hu.getM3()+"&m4="+momentos_hu.getM4()+"&m5="+momentos_hu.getM5()+
                "&m6="+momentos_hu.getM6()+"&promedio="+momentos_hu.getPromedio()+"&id_imagen="+id_imagen;
        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Insertar_MomentosHu);

        url="FotoScan/Insertar_MomentosHu.php?posicion_momento="+momentos_hu2.getPosicion_momento()+
                "&m0="+momentos_hu2.getM0()+"&m1="+momentos_hu2.getM1()+"&m2="+momentos_hu2.getM2()+
                "&m3="+momentos_hu2.getM3()+"&m4="+momentos_hu2.getM4()+"&m5="+momentos_hu2.getM5()+
                "&m6="+momentos_hu2.getM6()+"&promedio="+momentos_hu2.getPromedio()+"&id_imagen="+id_imagen;
        volley_objecto.setPagina(url);
        volley_objecto.insert_get(context,d_imagen,Retorno_Consulta.Operacion_Especifica_Insertar_MomentosHu);


    }



    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra,
                         String Clase_origen,String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Insert==volley_operacion &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Insertar_Imagen){

           get_max_id();
        }

        if(Volley_Objecto.Operacion_Insert==volley_operacion &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Insertar_MomentosHu){

            if (Volley_Objecto.mensaje_Insert_ok.equals(mensaje)){
                if(contador_momentos==2){  // 2 por lados
                    this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                            Retorno_Consulta.D_Imagen,operacion_especificacion);
                    contador_momentos=1;
                }else{
                    contador_momentos++;
                }

            }else{
                mensaje("Error momentos no insertados; d_imagen 79",true);
            }
        }

        if(Volley_Objecto.Operacion_Select==volley_operacion &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Max_Id_Imagen) {

            if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {
                try {
                    String id_max=respuesta.getJSONObject(0).getString("MAX(id)");
                   /* Momentos_Hu mo=new Momentos_Hu();
                    mo.setPosicion_momento(0);
                    mo.setM0(-10);
                    mo.setM1(-10);
                    mo.setM2(-10);
                    mo.setM3(-10);
                    mo.setM4(-10);
                    mo.setM5(-10);
                    mo.setM6(-10);
                    mo.setPromedio(-100);*/
                    insertar_momentos(Integer.valueOf(id_max));

                } catch (JSONException e) {
                    mensaje("Error: "+e.toString(),false);
                }

            }else{
                mensaje("Error al Obtener el Max Id Imagen; d_imagen 98",false);
            }
        }
        if(Volley_Objecto.Operacion_Select==volley_operacion &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_Id_segun_momento_Imagen) {

            //try {
            //    mensaje("consulta: "+respuesta.getString(0),true);
            //} catch (JSONException e) {
              //  mensaje("Error: "+e.toString(),false);
            //}

            //if (Volley_Objecto.mensaje_Select_ok.equals(mensaje)) {

                this.retorno_consulta.finalizo(respuesta,volley_operacion,mensaje,mensaje_extra,
                        Retorno_Consulta.D_Imagen,operacion_especificacion);
            //}
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
