package com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Negocio.N_Rol;
import com.example.cespe.opencv10.R;

import org.json.JSONArray;
import org.json.JSONException;

public class Info_Activity_Rol_Permiso extends AppCompatActivity implements Retorno_Consulta {

    Rol_Permiso_Objeto rol_permiso_objeto;
    TextView id_rol,nombre_rol,id_permiso,nombre_permiso;
    Button asignar;
    Context context=this;

    Info_Activity_Rol_Permiso info_activity_rol_permiso=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info___rol__permiso);

        rol_permiso_objeto= (Rol_Permiso_Objeto) getIntent().getSerializableExtra("key");

        id_rol= (TextView) findViewById(R.id.tv_id_rol_info_rol_permiso_valor);
        nombre_rol= (TextView) findViewById(R.id.tv_nombre_rol_info_rol_permiso_valor);
        id_permiso= (TextView) findViewById(R.id.tv_id_permiso_info_rol_permiso_valor);
        nombre_permiso= (TextView) findViewById(R.id.tv_nombre_permiso_info_rol_permiso_valor);

        id_rol.setText(rol_permiso_objeto.getRol_objeto().getId());
        nombre_rol.setText(rol_permiso_objeto.getRol_objeto().getNombre());

        id_permiso.setText(rol_permiso_objeto.getPermiso_objeto().getId());
        nombre_permiso.setText(rol_permiso_objeto.getPermiso_objeto().getNombre());

        asignar= (Button) findViewById(R.id.bt_asignar_info_rol_permiso);
        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                N_Rol n_rol=new N_Rol(context);
                n_rol.Asignar_rol_permiso(rol_permiso_objeto);
            }
        });
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {

    }
}
