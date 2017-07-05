package com.example.cespe.opencv10.Presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Negocio.N_Documento;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;

public class P_Ver_Documento extends AppCompatActivity implements Retorno_Consulta{

    private String usuario;
    private String id_imagen;
    EditText id,nombre,fecha,cantidad,cotizacion,precio,ruta;
    CheckBox placas;
    Button aceptar;
    P_Ver_Documento p_ver_documento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver__documento);

        p_ver_documento=this;

        id= (EditText) findViewById(R.id.ed_ver_doc_id);
        nombre= (EditText) findViewById(R.id.ed_ver_doc_nombre);
        fecha= (EditText) findViewById(R.id.ed_ver_doc_fecha);
        cantidad= (EditText) findViewById(R.id.ed_ver_doc_cantidad);
        cotizacion= (EditText) findViewById(R.id.ed_ver_doc_cotizacion);
        precio= (EditText) findViewById(R.id.ed_ver_doc_precio);
        ruta= (EditText) findViewById(R.id.ed_ver_doc_ruta);
        placas= (CheckBox) findViewById(R.id.ch_ver_doc);

        aceptar= (Button) findViewById(R.id.bt_ver_doc_aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver_activity_padre(RESULT_CANCELED);
            }
        });


        if(getIntent().getExtras()!=null){
            usuario=getIntent().getExtras().getString("usuario");
            id_imagen=getIntent().getExtras().getString("id_imagen");
            N_Documento n_documento=new N_Documento(P_Ver_Documento.this);

            n_documento.get_documento_id_imagen(p_ver_documento,id_imagen);
            n_documento.get_todos_documento(p_ver_documento);

            //MiTareaAsincrona2 simpleTask = new MiTareaAsincrona2();
            //simpleTask.execute();

        }else{
            mensaje("Error al Cargar el Documento",true);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            volver_activity_padre(RESULT_CANCELED);
        }
        return super.onKeyDown(keyCode, event);
    }
    public void volver_activity_padre(int result){
        Intent i =new Intent();
        setResult(result,i);
        finish();
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(Volley_Objecto.Operacion_Select==volley_operacion &&  Clase_origen==Retorno_Consulta.N_Documento &&
                operacion_especificacion==Retorno_Consulta.Operacion_Especifica_get_segun_id_imagen_Documento) {

            try {
                String id_txt=respuesta.getJSONObject(0).getString("id");
                String nombre_txt=respuesta.getJSONObject(0).getString("nombre");
                String fecha_txt=respuesta.getJSONObject(0).getString("fecha");
                String cotizacion_txt=respuesta.getJSONObject(0).getString("cotizacion");
                String precio_txt=respuesta.getJSONObject(0).getString("precio");
                String cantidad_txt=respuesta.getJSONObject(0).getString("cantidad");
                String ruta_txt=respuesta.getJSONObject(0).getString("ruta_archivos");
                String placas_txt=respuesta.getJSONObject(0).getString("estado_placas");

                this.id.setText(id_txt);
                this.nombre.setText(nombre_txt);
                this.fecha.setText(fecha_txt);
                this.cotizacion.setText(cotizacion_txt);
                this.precio.setText(precio_txt);
                this.cantidad.setText(cantidad_txt);
                this.ruta.setText(ruta_txt);
                this.placas.setSelected(false);
                if(placas_txt.equals("F")){
                    this.placas.setChecked(false);
                }



            } catch (JSONException e) {
                mensaje("Error al tratar el JSON, Ver Documento 79: "+e.toString(),false);
            }
        }
    }
}
