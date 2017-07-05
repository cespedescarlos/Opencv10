package com.example.cespe.opencv10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.cespe.opencv10.Conexion.Retorno_Consulta;
import com.example.cespe.opencv10.Presentacion.P_Empresa;
import com.example.cespe.opencv10.Presentacion.P_Login;
import com.example.cespe.opencv10.Presentacion.P_Rol;
import com.example.cespe.opencv10.Presentacion.P_Tipo_Trabajo;
import com.example.cespe.opencv10.Presentacion.P_Usuario_Reg;
import com.example.cespe.opencv10.Presentacion.P_Ver_Documento;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos.P_Ver_Todos_Doc;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos.P_Ver_todos_permisos;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos.P_Ver_todos_tipo_trabajos;
import com.example.cespe.opencv10.Presentacion.P_Visualizar;
import com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc.P_Ver_todas_Empresas_Ins_doc;

import org.opencv.android.OpenCVLoader;

public class navi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button comenzar;

    //private static final String REGISTER_URL = "http://cespedessoliz.esy.es/volleyRegister.php";

    private static final int id_Ins_doc =1;
    private static final int id_Visualizar =2;

    private String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitleTextColor(Color.alpha(R.color.amarillo1));
        setSupportActionBar(toolbar);

        if(getIntent().getExtras()!=null){
            id_usuario=getIntent().getExtras().getString("usuario");
        }

        comenzar = (Button) findViewById(R.id.bt_visualizar_main);
        comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(navi.this, P_Visualizar.class);
                i.putExtra("usuario",id_usuario);
                startActivityForResult(i, id_Visualizar);
            }
        });


        comprobar_opencv();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle("Cerrar");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void mensaje(String mensaje, boolean duracion_corta){
        if(duracion_corta){
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
        }
    }

    public void comprobar_opencv(){
        if(!OpenCVLoader.initDebug()){
            mensaje("fallo la cargar de opencv",true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tipo_trabajo_reg_navi) {
            //Toast.makeText(getApplicationContext(),"tipo trabajo",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(navi.this, P_Tipo_Trabajo.class);
            i.putExtra("usuario",id_usuario);
            startActivityForResult(i, 0);
        } else if (id == R.id.empresa_reg_navi) {
            //Toast.makeText(getApplicationContext(),"empresa",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(navi.this, P_Empresa.class);
            i.putExtra("usuario",id_usuario);
            startActivityForResult(i, 0);

        }else if (id == R.id.roles_reg_navi) {
            //Toast.makeText(getApplicationContext(),"roles",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(navi.this, P_Rol.class);
            i.putExtra("usuario",id_usuario);
            startActivityForResult(i, 0);

        }else if (id == R.id.usuario_reg_navi) {
            //Toast.makeText(getApplicationContext(),"roles",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(navi.this, P_Usuario_Reg.class);
            i.putExtra("usuario",id_usuario);
            startActivityForResult(i, 0);

        }
        //////////////////// ver todos//////////////////////
        else if (id == R.id.ver_tipo_trab_todos_navi) {
            Intent i = new Intent(navi.this, P_Ver_todos_tipo_trabajos.class);
            i.putExtra("usuario", id_usuario);
            startActivityForResult(i, 0);

        }else if (id == R.id.ver_empresa_todos_navi) {
            Intent i = new Intent(navi.this, P_Ver_todas_Empresas_Ins_doc.class);
            i.putExtra("usuario", id_usuario);
            startActivityForResult(i, 0);

        }
        else if (id == R.id.permiso_todos_navi) {
                Intent i=new Intent(navi.this, P_Ver_todos_permisos.class);
                i.putExtra("usuario",id_usuario);
                i.putExtra("clase_origen", Retorno_Consulta.Navi);
                startActivityForResult(i, 0);

        }
        else if (id == R.id.cerrar_sesion_navi) {
            Toast.makeText(getApplicationContext(),"cerrar sesion",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(navi.this, P_Login.class);
            startActivityForResult(i, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
