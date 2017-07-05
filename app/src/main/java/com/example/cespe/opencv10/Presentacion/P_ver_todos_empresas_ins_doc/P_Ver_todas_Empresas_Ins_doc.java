package com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Momentos_Hu_Serial;
import com.example.cespe.opencv10.Negocio.N_Empresa;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos.Data;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class P_Ver_todas_Empresas_Ins_doc extends AppCompatActivity implements Retorno_Consulta {
    private RecyclerView.LayoutManager layoutManager;
    private List<Empresa_Objeto> list=new ArrayList<>();
    private Empresa_Objeto empresa_objeto_devuelto_recycler =new Empresa_Objeto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__ver_todas__empresas__ins_doc);
        rellenar();




    }
    private void rellenar(){
        /*Empresa_Objeto e=new Empresa_Objeto();
        e.setNombre("cotel");
        e.setTelefono("1547");
        e.setNombre_contacto("cotel_perno");
        e.setTelefono_contacto("15963");

        list.add(e);list.add(e);

        e=new Empresa_Objeto();
        e.setNombre("enfe");
        e.setTelefono("47");
        e.setNombre_contacto("daigor");
        e.setTelefono_contacto("13");
        list.add(e);list.add(e);

        e=new Empresa_Objeto();
        e.setNombre("digi");
        e.setTelefono("45657");
        e.setNombre_contacto("erno");
        e.setTelefono_contacto("15866963");

        list.add(e);list.add(e);list.add(e);*/
        N_Empresa n_empresa=new N_Empresa(P_Ver_todas_Empresas_Ins_doc.this);
        n_empresa.get_empresas_todas(this);
    }

    private void continuar_relleno(){
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recyclerView_empresa_ins_doc);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapterEmpresa_Ins_Doc adapterEmpresa_ins_doc = new RecyclerAdapterEmpresa_Ins_Doc(list,this,this);
        recyclerView.setAdapter(adapterEmpresa_ins_doc);
    }

    public void volviendo_recycler(Empresa_Objeto empresa_objeto){
        this.empresa_objeto_devuelto_recycler=empresa_objeto;
        volver_activity_padre(RESULT_OK);
    }

    public void volver_activity_padre(int result){
        //Toast.makeText(getApplicationContext(),this.empresa_objeto_devuelto_recycler.toString(),Toast.LENGTH_SHORT).show();
        Intent i =new Intent();
        i.putExtra("empresa",this.empresa_objeto_devuelto_recycler);
        setResult(result,i);
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
                    String telefono=object.getString("telefono");
                    String nombre_contacto=object.getString("nombre_contacto");
                    String telefono_contacto=object.getString("telefono_contacto");

                    Empresa_Objeto empresa_objeto=new Empresa_Objeto();
                    empresa_objeto.setId(id);
                    empresa_objeto.setNombre(nombre);
                    empresa_objeto.setTelefono(telefono);
                    empresa_objeto.setNombre_contacto(nombre_contacto);
                    empresa_objeto.setTelefono_contacto(telefono_contacto);
                    list.add(empresa_objeto);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            continuar_relleno();
        }
    }

}
