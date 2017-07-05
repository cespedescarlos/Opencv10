package com.example.cespe.opencv10.Presentacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Momentos_Hu;
import com.example.cespe.opencv10.Negocio.N_Documento;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos.P_Ver_todos_tipo_trabajos;
import com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc.P_Ver_todas_Empresas_Ins_doc;
import com.example.cespe.opencv10.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class P_Ins_Documento extends AppCompatActivity {
    private Empresa_Objeto empresa_objeto_seleccionada;
    private Tipo_Trabajo_Objecto tipo_trabajo_objecto_seleccionado;

    private Uri direccion_uri;
    private String mensaje_insercion=null;
    private String usuario;
    private static final int codigo_intent_foto=1;
    private static final int codigo_intent_empresa=2;
    private static final int codigo_intent_tipo_trabajo=3;
    Button registrar;
    ImageButton seleccionar_empresa,seleccionar_tipo_trabajo;
    EditText ed_nombre,ed_cotizacion,ed_precio,ed_cantidad,ed_ruta;
    AutoCompleteTextView aut_tipo_trabajo,aut_empresa;
    CheckBox placas;
    List<Momentos_Hu> lista_moment;
    String nombre_imagen;
    String  ruta_imagen;
    float tamaño;
    int filas,columnas;

    // empresa



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ins__documento);
        empresa_objeto_seleccionada=new Empresa_Objeto();
        empresa_objeto_seleccionada.setId("1");
        empresa_objeto_seleccionada.setNombre("Tigo");
        empresa_objeto_seleccionada.setTelefono("4564");
        empresa_objeto_seleccionada.setNombre_contacto("Erika Cespedes");
        empresa_objeto_seleccionada.setTelefono_contacto("456454");

        tipo_trabajo_objecto_seleccionado=new Tipo_Trabajo_Objecto("1","Volante");

        usuario=null;
        direccion_uri =null;
        ed_nombre= (EditText) findViewById(R.id.ed_ins_doc_nombre);
        ed_cotizacion= (EditText) findViewById(R.id.ed_ins_doc_cotizacion);
        ed_precio= (EditText) findViewById(R.id.ed_ins_doc_precio);
        ed_cantidad= (EditText) findViewById(R.id.ed_ins_doc_cantidad);
        ed_ruta= (EditText) findViewById(R.id.ed_ins_doc_ruta);
        ed_ruta= (EditText) findViewById(R.id.ed_ins_doc_ruta);
        aut_empresa= (AutoCompleteTextView) findViewById(R.id.act_ins_doc_empresa_valor);
        aut_tipo_trabajo = (AutoCompleteTextView) findViewById(R.id.act_ins_doc_tipo_trabajo_valor);
        placas= (CheckBox) findViewById(R.id.ch_ins_doc_placas);

        getExtras();

        registrar= (Button) findViewById(R.id.bt_ins_doc_registrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(usuario!=null){
                        Async_registrar async_registrar=new Async_registrar();
                        async_registrar.execute();
                        //registrar();
                    }else{
                        mensaje("Error Usuario no Definido",true);
                    }
            }
        });

        seleccionar_empresa= (ImageButton) findViewById(R.id.bt_empresas_ins_doc);
        seleccionar_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(P_Ins_Documento.this, P_Ver_todas_Empresas_Ins_doc.class);
                startActivityForResult(i,codigo_intent_empresa);

            }
        });

        seleccionar_tipo_trabajo= (ImageButton) findViewById(R.id.bt_tipo_trabajo_ins_doc);
        seleccionar_tipo_trabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(P_Ins_Documento.this, P_Ver_todos_tipo_trabajos.class);
                startActivityForResult(i,codigo_intent_tipo_trabajo);
            }
        });
    }



    private void getExtras(){
        if(getIntent().getExtras()!=null){
            this.lista_moment=new ArrayList<>();
            usuario=getIntent().getExtras().getString("usuario");
            Momentos_Hu momentos_hu=new Momentos_Hu();

            double vec[]=getIntent().getDoubleArrayExtra("momento1");

            for (int j=0; j<vec.length;j++){
                momentos_hu.set(j,vec[j]);
            }

            Momentos_Hu momentos_hu2=new Momentos_Hu();
            vec=getIntent().getDoubleArrayExtra("momento2");

            for (int j=0; j<vec.length;j++){
                momentos_hu2.set(j,vec[j]);
            }

            lista_moment.add(momentos_hu);
            lista_moment.add(momentos_hu2);

            this.nombre_imagen=getIntent().getStringExtra("nombre");
            this.ruta_imagen=getIntent().getStringExtra("ruta");
            this.tamaño=getIntent().getFloatExtra("tamaño",0);
            this.filas=getIntent().getIntExtra("filas",0);
            this.columnas=getIntent().getIntExtra("columnas",0);


        }
    }

    private class Async_registrar extends AsyncTask<Void, Integer, Boolean> {
        final ProgressDialog loading = ProgressDialog.show(P_Ins_Documento.this,"Creando Documento",
                "Aplicando Procesamiento y Registrando en la Base de Datos, Espere…",false,false);
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
              //  e.printStackTrace();
            }

            registrar();

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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
               // mensaje(mensaje_insercion,false);
                // Toast.makeText(P_Visualizar.this, "Tarea finalizada! ",
                //         Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() {
            loading.dismiss();
            Toast.makeText(P_Ins_Documento.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void registrar(){
        String nombre=ed_nombre.getText().toString();
        String fecha=getDateTime();
        try {
            double precio=Double.valueOf(ed_precio.getText().toString());
            double cotizacion=Double.valueOf(ed_cotizacion.getText().toString());
            String ruta=ed_ruta.getText().toString();
            int cantidad=Integer.valueOf(ed_cantidad.getText().toString());
            String estado_placas="F";
            if(placas.isChecked()){
                estado_placas="V";
            }

            String trabajo= tipo_trabajo_objecto_seleccionado.getId();
            String empresa=empresa_objeto_seleccionada.getId();

            N_Documento n_documento=new N_Documento(P_Ins_Documento.this);

                n_documento.set_datos_imagen(this.nombre_imagen,this.ruta_imagen,this.tamaño,this.filas,this.columnas);
                n_documento.Registrar_Documento(nombre,fecha,cotizacion,precio,ruta,cantidad,
                        estado_placas,trabajo,empresa,usuario,lista_moment.get(0),lista_moment.get(1),this,this.usuario);
        }catch (Exception e){
            mensaje("Error al Converir a Numero",true);
        }

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(); return dateFormat.format(date); }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri filePath = data.getData();
        if (requestCode == codigo_intent_foto && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri m_direccion_imagen = data.getData();
            this.direccion_uri =m_direccion_imagen;
            String dir= m_direccion_imagen.getEncodedPath();

            //mensaje(dir,false);
        }
        if (requestCode == codigo_intent_empresa && resultCode == RESULT_OK && data != null) {
            Empresa_Objeto empresa_objeto= (Empresa_Objeto) data.getSerializableExtra("empresa");
            this.empresa_objeto_seleccionada=empresa_objeto;
            this.aut_empresa.setText(empresa_objeto_seleccionada.getNombre());

        }
        if (requestCode == codigo_intent_tipo_trabajo && resultCode == RESULT_OK && data != null) {
            Tipo_Trabajo_Objecto tipo_trabajo_objecto=
                    (Tipo_Trabajo_Objecto) data.getSerializableExtra("tipo_trabajo");
            this.tipo_trabajo_objecto_seleccionado=tipo_trabajo_objecto;
            this.aut_tipo_trabajo.setText(tipo_trabajo_objecto_seleccionado.getNombre());

        }
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        }
    }


    public void mensaje_insercion(String mensaje){
        this.mensaje_insercion=mensaje;
        mensaje(mensaje,false);
        volver_activity_padre(RESULT_CANCELED);
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
}
