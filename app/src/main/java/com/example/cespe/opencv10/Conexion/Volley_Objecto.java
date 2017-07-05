package com.example.cespe.opencv10.Conexion;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cespe on 09/06/2017.
 */

public class Volley_Objecto {

    public static int Operacion_Insert=0;
    public static int Operacion_Update=1;
    public static int Operacion_Delete=2;
    public static int Operacion_Select=3;

    public static String mensaje_Insert_ok="Registro Exitoso";
    public static String mensaje_Update_ok="Modificaion Exitosa";
    public static String mensaje_Delete_ok="Eliminacion Exitosa";
    public static String mensaje_Select_ok="Consulta Exitosa";

    public static String mensaje_Insert_error="Error al Registrar_Documento";
    public static String mensaje_Update_error="Error al Modificar";
    public static String mensaje_Delete_error="Error al Eliminar";
    public static String mensaje_Select_error="Error al Consultar";

    public static String mensaje_Select_vacia="Resultado de Consulta Vacio";

    private String url="http://";
    private String servidor="192.168.1.8";

    //private String servidor="arteycolor.esy.es";

    private String pagina="";
    //private static final String REGISTER_URL = "http://cespedessoliz.esy.es/volleyRegister.php";


    public Volley_Objecto() {
        actualizar_url();
    }

    private void actualizar_url(){
        this.url="http://"+this.servidor+"/"+this.pagina;
        this.url = this.url.replace(" ","%20");
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
        actualizar_url();
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
        actualizar_url();
    }

    public String getUrl() {
        return url;
    }


    public void consulta_get(final Context context, final Retorno_Consulta activity, final String operacion_especificacion){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //if(response!=null&&!response.isEmpty()) {
                    try {
                        activity.finalizo(new JSONArray(response), Volley_Objecto.Operacion_Select, Volley_Objecto.mensaje_Select_ok,
                                "", "", operacion_especificacion);
                    } catch (JSONException e) {
                        //Toast.makeText(context, "error al formato json re:"+response+" ; "+response.length()+" : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        activity.finalizo(null, Volley_Objecto.Operacion_Select, Volley_Objecto.mensaje_Select_ok,
                                Volley_Objecto.mensaje_Select_vacia, "", operacion_especificacion);
                    }
                //}else{

                //}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.finalizo(null,Volley_Objecto.Operacion_Select,Volley_Objecto.mensaje_Select_error," ; "+error.getMessage(),
                        "",operacion_especificacion);
            }
        });
        queue.add(stringRequest);
    }

    public void insert_get(final Context context, final Retorno_Consulta activity, final String operacion_especificacion){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                activity.finalizo(null,Volley_Objecto.Operacion_Insert,Volley_Objecto.mensaje_Insert_ok,"","",operacion_especificacion);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.finalizo(null,Volley_Objecto.Operacion_Insert,Volley_Objecto.mensaje_Insert_error," ; "+error.getMessage(),
                        "",operacion_especificacion);
            }
        });
        queue.add(stringRequest);
    }

}
