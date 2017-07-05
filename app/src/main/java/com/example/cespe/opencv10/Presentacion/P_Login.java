package com.example.cespe.opencv10.Presentacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Datos.Usuario_Objeto;
import com.example.cespe.opencv10.Negocio.N_Usuario;
import com.example.cespe.opencv10.R;
import com.example.cespe.opencv10.navi;

import org.json.JSONArray;
import org.json.JSONException;

public class P_Login extends AppCompatActivity implements Retorno_Consulta{

    EditText correo,password;
    Button inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p__login);

        correo= (EditText) findViewById(R.id.ed_correo_valor_login);
        password= (EditText) findViewById(R.id.ed_contraseña_valor_login);

        inicio= (Button) findViewById(R.id.bt_Ininicar_sesion);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async_login async_login=new Async_login();
                async_login.execute();
            }
        });

    }

    private void validar(){
        Usuario_Objeto usuario_objeto=new Usuario_Objeto();
        usuario_objeto.setCorreo(correo.getText().toString());
        usuario_objeto.setPassword(password.getText().toString());

        N_Usuario n_usuario=new N_Usuario(P_Login.this);
        n_usuario.get_id_validar(usuario_objeto,P_Login.this);
    }

    private class Async_login extends AsyncTask<Void, Integer, Boolean> {
        final ProgressDialog loading = ProgressDialog.show(P_Login.this,"Inicio de Sesion",
                "Autentificando Usuario, Espere…",false,false);
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //  e.printStackTrace();
            }

            validar();

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            //pbarProgreso.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            // pbarProgreso.setMax(100);
            // pbarProgreso.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                //try {
                    //Thread.sleep(2000);
              //  } catch (InterruptedException e) {
               //     e.printStackTrace();
              //  }
                loading.dismiss();
            }

        }

        @Override
        protected void onCancelled() {
            loading.dismiss();
            Toast.makeText(P_Login.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finalizo(JSONArray respuesta, int volley_operacion, String mensaje, String mensaje_extra, String Clase_origen, String operacion_especificacion) {
        if(respuesta!=null&&!respuesta.isNull(0)) {
            try {
                String id = respuesta.getJSONObject(0).getString("id");

                mensaje("Inicio de Sesion Exitoso",true);

                Intent i=new Intent(P_Login.this, navi.class);
                i.putExtra("usuario",id);
                startActivityForResult(i, 0);

            } catch (JSONException e) {
                mensaje("Error: " + e.toString(), false);
            }
        }else{
            mensaje("Datos Incorrectos",true);
           // mensaje("Error, resuesta null, P_Login 58",true);
        }
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        password.setText("");
    }
}
