package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Conexion.Volley_Objecto;
import com.example.cespe.opencv10.Momentos_Hu_Serial;
import com.example.cespe.opencv10.Negocio.N_Documento;
import com.example.cespe.opencv10.Presentacion.P_Visualizar;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_Ver_Todos_Doc extends AppCompatActivity implements Retorno_Consulta {

    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Data> listData=new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView;
    String [] valores;
    ImageButton button;
    Context context;
    N_Documento n_documento;
    P_Ver_Todos_Doc p_ver_todos_doc=this;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver__todos__doc);
        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.act_ver_todos_doc);
        n_documento=new N_Documento(P_Ver_Todos_Doc.this);
        n_documento.get_todos_documento(P_Ver_Todos_Doc.this);
        context=this;
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView_main);

        button= (ImageButton) findViewById(R.id.bt_ver_todos_buscar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=autoCompleteTextView.getText().toString();
                n_documento.get_doc_segun_nombre(nombre,p_ver_todos_doc);
            }
        });

    }

    private void rellenar(JSONArray respuesta){

        /*listData.add(new Data("1","2017/05/29","carlos","F:/tigo564654.pdf","Volante","Tigo"));
        listData.add(new Data("2","2017/05/29","carlos","F:/tigo564654.pdf","Diptico","Tigo"));
        listData.add(new Data("3","2017/05/29","carlos","F:/tigo564654.pdf","Triptico","Entel"));
        listData.add(new Data("4","2017/05/29","carlos","F:/tigo564654.pdf","Afiche","Viva"));
        listData.add(new Data("5","2017/05/29","carlos","F:/tigo564654.pdf","PLegable","Tigo"));*/

        //n_documento.get_todos_documento(P_Ver_Todos_Doc.this);

        if(respuesta!=null) {
            valores=new String[respuesta.length()];
            listData=null;
            listData=new ArrayList<>();
            for (int i = 0; i < respuesta.length(); i++) {
                try {
                    JSONObject object = respuesta.getJSONObject(i);
                    String id = object.getString("id");
                    String nombre = object.getString("nombre");
                    String fecha = object.getString("fecha");
                    String ruta = object.getString("ruta_archivos");
                    String empresa = object.getString("nombre_empresa");
                    String tipo = object.getString("nombre_tipo_trabajo");

                    Momentos_Hu_Serial hu_serial1;
                    Momentos_Hu_Serial hu_serial2;
                    String nombre_imagen, direccion;
                    float tamaño;
                    int filas, columnas;

                    hu_serial1 = (Momentos_Hu_Serial) getIntent().getSerializableExtra("momento1");
                    hu_serial2 = (Momentos_Hu_Serial) getIntent().getSerializableExtra("momento2");

                    nombre_imagen = getIntent().getExtras().getString("nombre_imagen");
                    tamaño = getIntent().getExtras().getFloat("tamaño", 0);
                    direccion = getIntent().getExtras().getString("direccion");
                    filas = getIntent().getExtras().getInt("filas", 0);
                    columnas = getIntent().getExtras().getInt("columnas", 0);

                    Data dat = new Data(id, fecha, nombre, ruta, tipo, empresa, hu_serial1, hu_serial2, nombre_imagen, direccion,
                            tamaño, filas, columnas);
                    listData.add(dat);
                    valores[i] = nombre;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Toast.makeText(context,"Json de respuesta es nulo, p_ver_todos_doc linea 114",Toast.LENGTH_LONG).show();
        }
        continuar_relleno();
    }



    private void continuar_relleno(){
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter =new RecyclerAdapter(listData,this);
        recyclerView.setAdapter(adapter);

        ArrayAdapter adapter_valores =new ArrayAdapter(this,android.R.layout.select_dialog_item,valores);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter_valores);

    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        //Toast.makeText(getApplicationContext(),"size: "+respuesta.length(),Toast.LENGTH_SHORT).show();
        if(respuesta!=null && operacion_especificacion== Retorno_Consulta.Operacion_Especifica_todos_Documento){
           // valores=new String[respuesta.length()];
            rellenar(respuesta);
            /*for (int i=0; i<respuesta.length();i++){
                try {
                    JSONObject object=respuesta.getJSONObject(i);
                    String id=object.getString("id");
                    String nombre=object.getString("nombre");
                    String fecha=object.getString("fecha");
                    String ruta=object.getString("ruta_archivos");
                    String empresa=object.getString("nombre_empresa");
                    String tipo=object.getString("nombre_tipo_trabajo");

                    Momentos_Hu_Serial hu_serial1;
                    Momentos_Hu_Serial hu_serial2;
                    String nombre_imagen,direccion;
                    float tamaño;
                    int filas,columnas;

                    hu_serial1= (Momentos_Hu_Serial) getIntent().getSerializableExtra("momento1");
                    hu_serial2= (Momentos_Hu_Serial) getIntent().getSerializableExtra("momento2");

                    nombre_imagen=getIntent().getExtras().getString("nombre_imagen");
                    tamaño=getIntent().getExtras().getFloat("tamaño",0);
                    direccion=getIntent().getExtras().getString("direccion");
                    filas=getIntent().getExtras().getInt("filas",0);
                    columnas=getIntent().getExtras().getInt("columnas",0);

                    Data dat=new Data(id,fecha,nombre,ruta,tipo,empresa,hu_serial1,hu_serial2,nombre_imagen,direccion,
                            tamaño,filas,columnas);
                    listData.add(dat);
                    valores[i]=nombre;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
            //continuar_relleno_inicial();
        }
        if(respuesta!=null && operacion_especificacion== Retorno_Consulta.Operacion_Especifica_get_documento_segun_nombre){
            //Toast.makeText(context,"tamaño: "+respuesta.length(),Toast.LENGTH_SHORT).show();
            rellenar(respuesta);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            volver_activity_padre(RESULT_CANCELED);
        }
        if(keyCode==event.KEYCODE_T){
            Toast.makeText(getApplicationContext(),"preciono la T",Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void volver_activity_padre(int result){
        Intent i =new Intent();
        setResult(result,i);
        finish();
    }

}
