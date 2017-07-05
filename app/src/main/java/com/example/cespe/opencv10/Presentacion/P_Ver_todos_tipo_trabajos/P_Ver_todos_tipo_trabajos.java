package com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Negocio.N_Tipo_Trabajo;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_Ver_todos_tipo_trabajos extends AppCompatActivity implements Retorno_Consulta{

    private RecyclerView.LayoutManager layoutManager;
    private List<Tipo_Trabajo_Objecto> list=new ArrayList<>();
    private Tipo_Trabajo_Objecto tipo_trabajo_objecto_devuelto_recycler =new Tipo_Trabajo_Objecto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver_todos_tipo_trabajos);
        rellenar();
    }

    private void rellenar(){
        /*Tipo_Trabajo_Objecto  tipo_trabajo_objecto=new Tipo_Trabajo_Objecto();
        tipo_trabajo_objecto.setId("5");
        tipo_trabajo_objecto.setNombre("trece");
        list.add(tipo_trabajo_objecto);

        tipo_trabajo_objecto=new Tipo_Trabajo_Objecto();
        tipo_trabajo_objecto.setId("48");
        tipo_trabajo_objecto.setNombre("cuarenta");
        list.add(tipo_trabajo_objecto);

        tipo_trabajo_objecto=new Tipo_Trabajo_Objecto();
        tipo_trabajo_objecto.setId("78");
        tipo_trabajo_objecto.setNombre("setenta");
        list.add(tipo_trabajo_objecto);*/

        //continuar_relleno();
        N_Tipo_Trabajo n_tipo_trabajo=new N_Tipo_Trabajo(this);
        n_tipo_trabajo.get_todos(this);

    }

    private void continuar_relleno(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView_tipo_trabajo);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterTipoTrabajo adapterTipoTrabajo = new RecyclerAdapterTipoTrabajo(list,this,this);
        recyclerView.setAdapter(adapterTipoTrabajo);
    }

    public void volviendo_recycler(Tipo_Trabajo_Objecto tipo_trabajo_objecto){
        this.tipo_trabajo_objecto_devuelto_recycler =tipo_trabajo_objecto;
        volver_activity_padre(RESULT_OK);
    }

    public void volver_activity_padre(int result){
        //Toast.makeText(getApplicationContext(),this.empresa_objeto_devuelto_recycler.toString(),Toast.LENGTH_SHORT).show();
        Intent i =new Intent();
        i.putExtra("tipo_trabajo",this.tipo_trabajo_objecto_devuelto_recycler);
        setResult(result,i);
        //setResult(RESULT_CANCELED,i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            volver_activity_padre(RESULT_CANCELED);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(respuesta!=null){
            for (int i=0; i<respuesta.length();i++){
                try {
                    JSONObject object=respuesta.getJSONObject(i);
                    String id=object.getString("id");
                    String nombre=object.getString("nombre");


                    Tipo_Trabajo_Objecto tipo_trabajo_objecto=new Tipo_Trabajo_Objecto();
                    tipo_trabajo_objecto.setId(id);
                    tipo_trabajo_objecto.setNombre(nombre);
                    list.add(tipo_trabajo_objecto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            continuar_relleno();
        }
    }

}
