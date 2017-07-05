package com.example.cespe.opencv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Presentacion.P_Visualizar;

import org.json.JSONArray;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity implements Retorno_Consulta {
    Button comenzar;

    //private static final String REGISTER_URL = "http://cespedessoliz.esy.es/volleyRegister.php";

    private static final int id_Ins_doc =1;
    private static final int id_Visualizar =2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comenzar = (Button) findViewById(R.id.bt_visualizar_main);
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this, P_Visualizar.class);
                i.putExtra("usuario","1");
                startActivityForResult(i, id_Visualizar);
            }
        });


        comprobar_opencv();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

   /* private void getInfoDevice() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;
        mensaje("Version release: "+ versionRelease+" codigo: "+version+" manufact: "+manufacturer,false);

    }*/

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        }
    }

    public void comprobar_opencv(){
        if(!OpenCVLoader.initDebug()){
            mensaje("fallo la cargar de opencv",true);
        }
    }



    @Override
    public void finalizo(JSONArray respuesta,int volley_operacion,String mensaje,String mensaje_extra,String Clase_origen
            ,String operacion_especificacion) {

    }

}
