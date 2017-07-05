package com.example.cespe.opencv10.Presentacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Cuadro_Dialogo;
import com.example.cespe.opencv10.Mat_Basicos;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Momentos_Hu_Serial;
import com.example.cespe.opencv10.Negocio.N_Visualizar;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos.P_Ver_Todos_Doc;
import com.example.cespe.opencv10.R;

import org.opencv.core.Mat;

import java.io.IOException;
import java.util.List;

public class P_Visualizar extends AppCompatActivity implements Cuadro_Dialogo.OnSimpleDialogListener{

    private String usuario;
    private ImageView imageView;
    private Button seleccionar,procesar;
    private Uri direccion;
    private float tamaño_imagen;
    private static final int codigo_intent_foto=1;
    private N_Visualizar n_visualizar;
    private P_Visualizar p_visualizar;
    private static final int id_Ins_doc =1;
    private static final int id_Ver_doc =2;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__visualizar);
        this.p_visualizar=this;
        usuario=null;
        direccion=null;
     //   lista_momentos=null;
        n_visualizar=new N_Visualizar(p_visualizar);

        if(getIntent().getExtras()!=null){
            usuario=getIntent().getExtras().getString("usuario");
        }


        seleccionar= (Button) findViewById(R.id.bt_visualizar_selecionar);
        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccion=null;
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,codigo_intent_foto);
            }
        });

        imageView= (ImageView) findViewById(R.id.iv_visualizar);
        procesar= (Button) findViewById(R.id.bt_visualizar_procesar);
        procesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (direccion != null) {
                    //final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Subiendo Imágen…", "Espere…", false, false);

                           // n_visualizar.comparar(direccion,p_visualizar);
                    Async_procesar simpleTask = new Async_procesar();
                     simpleTask.execute();

                }
                else{
                    mensaje("Seleccione una Imagen",true);
                }
                //loading.dismiss();
            }
        });
    }



    private class Async_procesar extends AsyncTask<Void, Integer, Boolean> {
        final ProgressDialog loading = ProgressDialog.show(P_Visualizar.this,"Procesando Imagen",
                "Aplicando Procesamiento y Comparando con la Base de Datos, Espere…",false,false);
        @Override
        protected Boolean doInBackground(Void... params) {

                n_visualizar.comparar(direccion,p_visualizar);

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            //pbarProgreso.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
           // pbarProgreso.setMax(100);
           // pbarProgreso.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                loading.dismiss();
               // Toast.makeText(P_Visualizar.this, "Tarea finalizada! ",
               //         Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {
            loading.dismiss();
            Toast.makeText(P_Visualizar.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void resultado_comparar(boolean existe,String id_imagen){
        if(!existe){
            new Cuadro_Dialogo().show(getSupportFragmentManager(), "dialogo");
          //  mensaje("no existe",true);
        }else{
            mensaje("La Imagen Existe",true);
            Intent i = new Intent(P_Visualizar.this, P_Ver_Documento.class);
            i.putExtra("usuario", usuario);
            i.putExtra("id_imagen", id_imagen);
            startActivityForResult(i, id_Ver_doc);
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri filePath = data.getData();
        if (requestCode == codigo_intent_foto && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri m_direccion_imagen = data.getData();
            this.direccion=m_direccion_imagen;
            String dir= m_direccion_imagen.getEncodedPath();

            Bitmap mBitmap = null;
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), m_direccion_imagen);
                imageView.setImageBitmap(mBitmap);



                tamaño_imagen=(float)mBitmap.getByteCount()/(1024*1024);

            } catch (IOException e) {
                mensaje("Error al Cargar Imagen "+data.getData().toString(),true);
            }

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

    @Override
    public void onPossitiveButtonClick() {

        Async_Ver_Doc async_ver_doc=new Async_Ver_Doc();
        async_ver_doc.execute();

    }

    @Override
    public void onNegativeButtonClick() {
        Intent i = new Intent(P_Visualizar.this, P_Ver_Todos_Doc.class);
        List<Momentos_Hu> list=n_visualizar.getLista_momentos();
        Momentos_Hu hu=list.get(0);
        Momentos_Hu_Serial hu_serial=new Momentos_Hu_Serial();
        hu_serial.setPosicion_momento(hu.getPosicion_momento());
        hu_serial.setM0(hu.getM0());
        hu_serial.setM1(hu.getM1());
        hu_serial.setM2(hu.getM2());
        hu_serial.setM3(hu.getM3());
        hu_serial.setM4(hu.getM4());
        hu_serial.setM5(hu.getM5());
        hu_serial.setM6(hu.getM6());
        hu_serial.setPromedio(hu.getPromedio());
        i.putExtra("momento1",hu_serial);

        hu=list.get(1);
        hu_serial=new Momentos_Hu_Serial();
        hu_serial.setPosicion_momento(hu.getPosicion_momento());
        hu_serial.setM0(hu.getM0());
        hu_serial.setM1(hu.getM1());
        hu_serial.setM2(hu.getM2());
        hu_serial.setM3(hu.getM3());
        hu_serial.setM4(hu.getM4());
        hu_serial.setM5(hu.getM5());
        hu_serial.setM6(hu.getM6());
        hu_serial.setPromedio(hu.getPromedio());
        i.putExtra("momento2",hu_serial);

        //String nombre, float size, String direccion,int filas, int columnas, int id_doc){
        i.putExtra("nombre_imagen",direccion.getEncodedPath());
        i.putExtra("tamaño",tamaño_imagen);
        i.putExtra("direccion",direccion.getPath());

        Mat_Basicos mat_basicos=new Mat_Basicos(P_Visualizar.this);
        i.putExtra("filas",mat_basicos.get_filas(mat_basicos.cargar(direccion,0)));
        i.putExtra("columnas",mat_basicos.get_columnas(mat_basicos.cargar(direccion,0)));


        startActivityForResult(i, 999);

    }

    public void llamar_ver_documento(){
       // n_visualizar.guardar_fotos_angulos(this.direccion);

        List<Momentos_Hu> lista= n_visualizar.procesar(direccion);

        if(lista!=null && lista.size()==2) {

            Intent i = new Intent(P_Visualizar.this, P_Ins_Documento.class);
            i.putExtra("usuario", usuario);

            double[] vec = new double[9];
            for (int j = 0; j < vec.length; j++) {
                vec[j] = lista.get(0).get(j);
            }
            i.putExtra("momento1", vec);

            vec = null;
            vec = new double[9];

            for (int j = 0; j < vec.length; j++) {
                vec[j] = lista.get(1).get(j);
            }
            i.putExtra("momento2", vec);
            String nombre = direccion.toString();
            Mat_Basicos mat_basicos = new Mat_Basicos(P_Visualizar.this);
            int filas = mat_basicos.get_filas(mat_basicos.cargar(direccion, 0));
            int columnas = mat_basicos.get_columnas(mat_basicos.cargar(direccion, 0));
            i.putExtra("nombre", nombre);
            i.putExtra("tamaño", tamaño_imagen);
            i.putExtra("filas", filas);
            i.putExtra("columnas", columnas);
            i.putExtra("ruta", direccion.getPath());

            startActivityForResult(i, id_Ins_doc);
        }
    }

    private class Async_Ver_Doc extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog loading;
        @Override
        protected Boolean doInBackground(Void... params) {

           llamar_ver_documento();

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            //pbarProgreso.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(P_Visualizar.this,"Creando Documento",
                    "Creando parametros para un Nuevo Documento, Espere...",false,false);
            // pbarProgreso.setMax(100);
            // pbarProgreso.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                loading.dismiss();
                //Toast.makeText(P_Visualizar.this, "Tarea finalizada! ",
                 //       Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {
            loading.dismiss();
            Toast.makeText(P_Visualizar.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }



    /*
    List<Momentos_Hu> lista= n_visualizar.procesar(direccion);

               if(lista!=null && lista.size()==2) {

                   Intent i = new Intent(P_Visualizar.this, P_Ins_Documento.class);
                   i.putExtra("usuario", "1");

                   double[] vec = new double[9];
                   for (int j = 0; j < vec.length; j++) {
                       vec[j] = lista.get(0).get(j);
                   }
                   i.putExtra("momento1", vec);

                   vec = null;
                   vec = new double[9];

                   for (int j = 0; j < vec.length; j++) {
                       vec[j] = lista.get(1).get(j);
                   }
                   i.putExtra("momento2", vec);
                   String nombre = direccion.toString();
                   Mat_Basicos mat_basicos = new Mat_Basicos(P_Visualizar.this);
                   int filas = mat_basicos.get_filas(mat_basicos.cargar(direccion, 0));
                   int columnas = mat_basicos.get_columnas(mat_basicos.cargar(direccion, 0));
                   i.putExtra("nombre", nombre);
                   i.putExtra("tamaño", tamaño_imagen);
                   i.putExtra("filas", filas);
                   i.putExtra("columnas", columnas);
                   i.putExtra("ruta", direccion.getPath());

                   startActivityForResult(i, id_Ins_doc);
     */
}
