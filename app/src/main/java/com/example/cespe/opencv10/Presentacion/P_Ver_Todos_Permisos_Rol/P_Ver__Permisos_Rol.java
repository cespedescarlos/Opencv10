package com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Permiso_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Negocio.N_Permiso;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos.RecyclerAdapterPermiso;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_Ver__Permisos_Rol extends AppCompatActivity implements Retorno_Consulta {
    private RecyclerView.LayoutManager layoutManager;
    private List<Rol_Permiso_Objeto> list=new ArrayList<>();
    private String id_usuario;
    Rol_Objeto rol_objeto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver___permisos__rol);

        if(getIntent().getExtras()!=null){
            id_usuario=getIntent().getExtras().getString("usuario");
            rol_objeto= (Rol_Objeto) getIntent().getSerializableExtra("rol_objeto");
        }

        rellenar();
    }
    private void rellenar(){

        N_Permiso n_permiso=new N_Permiso(this);
        n_permiso.get_todos(this);

    }

    private void continuar_relleno(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView_rol_permisos);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterRolPermiso recyclerAdapterRolPermiso = new RecyclerAdapterRolPermiso(list,this,this);
        recyclerView.setAdapter(recyclerAdapterRolPermiso);
    }


    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(respuesta!=null){
            for (int i=0; i<respuesta.length();i++){
                try {
                    JSONObject object=respuesta.getJSONObject(i);
                    String id_permiso=object.getString("id");
                    String nombre_permiso=object.getString("nombre");


                    Rol_Permiso_Objeto rol_permiso_objeto=new Rol_Permiso_Objeto();
                    rol_permiso_objeto.setPermiso_objeto(new Permiso_Objeto(id_permiso,nombre_permiso));
                    rol_permiso_objeto.setRol_objeto(this.rol_objeto);
                    list.add(rol_permiso_objeto);

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
