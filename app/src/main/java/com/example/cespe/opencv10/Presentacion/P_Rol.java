package com.example.cespe.opencv10.Presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Rol_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Negocio.N_Rol;
import com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol.P_Ver__Permisos_Rol;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos.P_Ver_todos_permisos;
import com.example.cespe.opencv10.R;
import com.example.cespe.opencv10.navi;

public class P_Rol extends AppCompatActivity {

    AutoCompleteTextView nombre;
    Button bt;
    N_Rol n_rol;
    private String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__rol);
        n_rol=new N_Rol(this,this);

        if(getIntent().getExtras()!=null){
            id_usuario=getIntent().getExtras().getString("usuario");
        }


        nombre= (AutoCompleteTextView) findViewById(R.id.acv_rol_reg_valor);
        bt= (Button) findViewById(R.id.bt_reg_rol);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_rol.Registrar(nombre.getText().toString());
            }
        });
    }

    public void insertado_ok(String id_rol){
        //Toast.makeText(getApplicationContext(),"El id insertado es: "+id_rol,Toast.LENGTH_SHORT).show();
        Intent i=new Intent(P_Rol.this, P_Ver__Permisos_Rol.class);
        i.putExtra("usuario",id_usuario);
        i.putExtra("rol_objeto",new Rol_Objeto(id_rol,nombre.getText().toString()));
        i.putExtra("clase_origen", Retorno_Consulta.P_Rol);
        startActivityForResult(i, 0);
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
