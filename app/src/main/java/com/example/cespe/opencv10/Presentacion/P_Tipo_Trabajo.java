package com.example.cespe.opencv10.Presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.cespe.opencv10.Negocio.N_Tipo_Trabajo;
import com.example.cespe.opencv10.R;

public class P_Tipo_Trabajo extends AppCompatActivity {

    AutoCompleteTextView nombre;
    Button bt;
    N_Tipo_Trabajo n_tipo_trabajo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__tipo__trabajo);

        n_tipo_trabajo=new N_Tipo_Trabajo(P_Tipo_Trabajo.this);

        nombre= (AutoCompleteTextView) findViewById(R.id.acv_tipo_trab_reg_valor);
        bt= (Button) findViewById(R.id.bt_reg_tipo_trab);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    n_tipo_trabajo.Registrar(nombre.getText().toString());
            }
        });



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
