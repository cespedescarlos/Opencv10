package com.example.cespe.opencv10.Presentacion;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Datos.Usuario_Objeto;
import com.example.cespe.opencv10.Negocio.N_Usuario;
import com.example.cespe.opencv10.Presentacion.P_Ver_Roles_Usuario_Reg.P_ver_roles_usuario_reg;
import com.example.cespe.opencv10.R;

public class P_Usuario_Reg extends AppCompatActivity {

    AutoCompleteTextView correo,nombre;
    Button registrar;
    ImageButton buscar_rol;
    TextView rol_selecc;
    Rol_Objeto rol_objeto_seleccionado;
    private String id_usuario;
    int CODE_P_VER_ROLES=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__usuario__reg);
        if(getIntent().getExtras()!=null){
            id_usuario=getIntent().getExtras().getString("usuario");
        }

        correo= (AutoCompleteTextView) findViewById(R.id.act_correo_usuario_reg);
        nombre= (AutoCompleteTextView) findViewById(R.id.act_nombre_usuario_reg);
        rol_selecc= (TextView) findViewById(R.id.tv_rol_usuario_reg_valor);

        rol_objeto_seleccionado=new Rol_Objeto("1",rol_selecc.getText().toString());

        registrar= (Button) findViewById(R.id.bt_registrar_usuario_reg);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        buscar_rol= (ImageButton) findViewById(R.id.bt_buscar_rol_usuario_reg);
        buscar_rol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(P_Usuario_Reg.this, P_ver_roles_usuario_reg.class);
                startActivityForResult(i, CODE_P_VER_ROLES);
            }
        });

    }

    public void registrar(){
        if(rol_objeto_seleccionado!=null){
            N_Usuario n_usuario=new N_Usuario(this);
            Usuario_Objeto usuario_objeto=new Usuario_Objeto();
            usuario_objeto.setCorreo(correo.getText().toString());
            usuario_objeto.setPassword("123321");
            usuario_objeto.setNombre(nombre.getText().toString());
            usuario_objeto.setId_rol(rol_objeto_seleccionado.getId());
            n_usuario.Registrar(usuario_objeto);
        }else{
            Toast.makeText(getApplicationContext(),"Error Rol No Seleccionado",Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_P_VER_ROLES && resultCode == RESULT_OK && data != null) {
           this.rol_objeto_seleccionado= (Rol_Objeto) data.getSerializableExtra("rol");
           rol_selecc.setText(rol_objeto_seleccionado.getNombre());
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
