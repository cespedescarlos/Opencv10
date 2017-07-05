package com.example.cespe.opencv10.Presentacion.P_Ver_Roles_Usuario_Reg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Permiso_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Negocio.N_Permiso;
import com.example.cespe.opencv10.Negocio.N_Rol;
import com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol.RecyclerAdapterRolPermiso;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_ver_roles_usuario_reg extends AppCompatActivity implements Retorno_Consulta {
    private RecyclerView.LayoutManager layoutManager;
    private List<Rol_Objeto> list=new ArrayList<>();
    Rol_Objeto rol_objeto_seleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_ver_roles_usuario_reg);
        rol_objeto_seleccionado=new Rol_Objeto("1","Administrador");
        rellenar();
    }

    private void rellenar(){

        N_Rol n_rol=new N_Rol(this);
        n_rol.get_todos(this);

    }

    private void continuar_relleno(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView_rol_usuario_reg);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterRolUsuarioReg recyclerAdapterRolUsuarioReg = new RecyclerAdapterRolUsuarioReg(list,this,this);
        recyclerView.setAdapter(recyclerAdapterRolUsuarioReg);
    }
    public void volviendo_recycler(Rol_Objeto rol_objeto){
        this.rol_objeto_seleccionado=rol_objeto;
        volver_activity_padre(RESULT_OK);
    }
    public void volver_activity_padre(int result){
        //Toast.makeText(getApplicationContext(),this.empresa_objeto_devuelto_recycler.toString(),Toast.LENGTH_SHORT).show();
        Intent i =new Intent();
        i.putExtra("rol",this.rol_objeto_seleccionado);
        setResult(result,i);
        finish();
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(respuesta!=null){
            for (int i=0; i<respuesta.length();i++){
                try {
                    JSONObject object=respuesta.getJSONObject(i);
                    String id_rol=object.getString("id");
                    String nombre_rol=object.getString("nombre");


                    Rol_Objeto rol_objeto=new Rol_Objeto();
                    rol_objeto.setId(id_rol);
                    rol_objeto.setNombre(nombre_rol);
                    list.add(rol_objeto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            continuar_relleno();
        }
    }
}
