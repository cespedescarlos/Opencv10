package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Permiso_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Negocio.N_Permiso;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_Ver_todos_permisos extends AppCompatActivity implements Retorno_Consulta {

    private RecyclerView.LayoutManager layoutManager;
    private List<Permiso_Objeto> list=new ArrayList<>();
    private Permiso_Objeto permiso_objecto_devuelto_recycler =new Permiso_Objeto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver_todos_permisos);
        rellenar();
    }

    private void rellenar(){

        N_Permiso n_permiso=new N_Permiso(this);
        n_permiso.get_todos(this);

    }

    private void continuar_relleno(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView_permisos);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterPermiso recyclerAdapterPermiso = new RecyclerAdapterPermiso(list,this,this);
        recyclerView.setAdapter(recyclerAdapterPermiso);
    }

    public void volviendo_recycler(Permiso_Objeto permiso_objeto){
        this.permiso_objecto_devuelto_recycler =permiso_objeto;
        volver_activity_padre();
    }

    public void volver_activity_padre(){
        //Toast.makeText(getApplicationContext(),this.empresa_objeto_devuelto_recycler.toString(),Toast.LENGTH_SHORT).show();
        Intent i =new Intent();
        i.putExtra("permiso",this.permiso_objecto_devuelto_recycler);
        setResult(RESULT_OK,i);
        finish();
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(respuesta!=null){
            for (int i=0; i<respuesta.length();i++){
                try {
                    JSONObject object=respuesta.getJSONObject(i);
                    String id=object.getString("id");
                    String nombre=object.getString("nombre");


                    Permiso_Objeto permiso_objeto=new Permiso_Objeto();
                    permiso_objeto.setId(id);
                    permiso_objeto.setNombre(nombre);
                    list.add(permiso_objeto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            continuar_relleno();
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

}
