package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Momentos_Hu_Serial;
import com.example.cespe.opencv10.Negocio.N_Documento;
import com.example.cespe.opencv10.Presentacion.P_Ins_Documento;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;

public class Info_Activity extends AppCompatActivity implements Retorno_Consulta{
    TextView id,nombre,fecha,ruta,tipo,empresa;
    Momentos_Hu_Serial hu_serial1;
    Momentos_Hu_Serial hu_serial2;
    String nombre_imagen,direccion;
    float tamaño;
    int filas,columnas;
    Button aceptar;

    Info_Activity info_activity;
    boolean ban=false;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_);
        info_activity=this;



        id= (TextView) findViewById(R.id.card_tv_id_valor_info);
        fecha=(TextView) findViewById(R.id.card_tv_fecha_valor_info);
        nombre=(TextView) findViewById(R.id.card_tv_nombre_valor_info);
        ruta=(TextView) findViewById(R.id.card_tv_ruta_valor_info);
        empresa=(TextView) findViewById(R.id.card_tv_empresa_valor_info);
        tipo=(TextView) findViewById(R.id.card_tv_tipo_trabajo_valor_info);

        Data data=new Data();
        data= (Data) getIntent().getSerializableExtra("key");

        id.setText(data.getId());
        nombre.setText(data.getNombre());
        fecha.setText(data.getFecha());
        ruta.setText(data.getRuta());
        tipo.setText(data.getTipo());
        empresa.setText(data.getEmpresa());

        hu_serial1=data.getHu_serial1();
        hu_serial2=data.getHu_serial2();
        nombre_imagen=data.getNombre_imagen();
        tamaño=data.getTamaño();
        direccion=data.getDireccion();
        filas=data.getFilas();
        columnas=data.getColumnas();

        aceptar= (Button) findViewById(R.id.bt_info);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(Info_Activity.this,"Asignando Documento",
                        "Aplicando Procesamiento y Registrando en la Base de Datos, Espere…",false,false);
                Async_asignar asignar=new Async_asignar();
                asignar.execute();
            }
        });
    }

    public void asignar_imagen(){

        if(!ban) {
            N_Documento n_documento = new N_Documento(Info_Activity.this);

            Momentos_Hu momentos_hu = new Momentos_Hu();
            momentos_hu.setPosicion_momento(hu_serial1.getPosicion_momento());
            momentos_hu.setM0(hu_serial1.getM0());
            momentos_hu.setM1(hu_serial1.getM1());
            momentos_hu.setM2(hu_serial1.getM2());
            momentos_hu.setM3(hu_serial1.getM3());
            momentos_hu.setM4(hu_serial1.getM4());
            momentos_hu.setM5(hu_serial1.getM5());
            momentos_hu.setM6(hu_serial1.getM6());
            momentos_hu.setPromedio(hu_serial1.getPromedio());

            n_documento.setMomentos_hu(momentos_hu);

            momentos_hu = new Momentos_Hu();
            momentos_hu.setPosicion_momento(hu_serial2.getPosicion_momento());
            momentos_hu.setM0(hu_serial2.getM0());
            momentos_hu.setM1(hu_serial2.getM1());
            momentos_hu.setM2(hu_serial2.getM2());
            momentos_hu.setM3(hu_serial2.getM3());
            momentos_hu.setM4(hu_serial2.getM4());
            momentos_hu.setM5(hu_serial2.getM5());
            momentos_hu.setM6(hu_serial2.getM6());
            momentos_hu.setPromedio(hu_serial2.getPromedio());

            n_documento.setMomentos_hu2(momentos_hu);



            n_documento.Registrar_Imagen(nombre_imagen, tamaño, direccion, filas, columnas,
                    Integer.valueOf(id.getText().toString()), info_activity);
        }else{
            mensaje("La Imagen ya Fue Asignada",true);
        }
    }

    public void mensaje_insercion(String mensaje){
        mensaje(mensaje,false);
        if(mensaje.equals("Imagen: "+ Volley_Objecto.mensaje_Insert_ok)){
            ban=true;
        }
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

    }

    private class Async_asignar extends AsyncTask<Void, Integer, Boolean> {
       // final ProgressDialog loading = ProgressDialog.show(Info_Activity.this,"Asignando Documento",
         //       "Aplicando Procesamiento y Registrando en la Base de Datos, Espere…",false,false);
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            asignar_imagen();

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                loading.dismiss();
            }

        }

        @Override
        protected void onCancelled() {
            loading.dismiss();
            Toast.makeText(Info_Activity.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
