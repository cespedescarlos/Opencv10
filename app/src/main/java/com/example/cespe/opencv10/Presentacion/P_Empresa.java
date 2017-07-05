package com.example.cespe.opencv10.Presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Negocio.N_Empresa;
import com.example.cespe.opencv10.R;

public class P_Empresa extends AppCompatActivity {

    AutoCompleteTextView nombre,nombre_contacto;
    EditText telefono,telefono_contacto;
    Button bt;
    N_Empresa n_empresa;

    private String id_usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__empresa);
        n_empresa=new N_Empresa(P_Empresa.this);

        nombre= (AutoCompleteTextView) findViewById(R.id.acv_nombre_empresa_reg_valor);
        nombre_contacto= (AutoCompleteTextView) findViewById(R.id.acv_nombre_contacto_empresa_reg_valor);
        telefono= (EditText) findViewById(R.id.ed_telefono_empresa_reg_valor);
        telefono_contacto= (EditText) findViewById(R.id.ed_telefono_contacto_empresa_reg_valor);
        bt= (Button) findViewById(R.id.bt_reg_empresa);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empresa_Objeto empresa_objeto=new Empresa_Objeto();
                empresa_objeto.setNombre(nombre.getText().toString());
                empresa_objeto.setNombre_contacto(nombre_contacto.getText().toString());
                empresa_objeto.setTelefono(telefono.getText().toString());
                empresa_objeto.setTelefono_contacto(telefono_contacto.getText().toString());
                n_empresa.Registrar(empresa_objeto);
            }
        });

    }

    private void get_extras(){
        if(getIntent().getExtras()!=null){
            id_usuario=getIntent().getExtras().getString("usuario");
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
