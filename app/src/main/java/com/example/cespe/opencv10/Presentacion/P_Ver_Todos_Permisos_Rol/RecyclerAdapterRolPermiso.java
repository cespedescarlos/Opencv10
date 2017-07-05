package com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cespe.opencv10.Datos.Permiso_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos.Info_Activity;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos.ItemClickListener_Permiso;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos.P_Ver_todos_permisos;
import com.example.cespe.opencv10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cespe on 25/06/2017.
 */

class RecyclerViewHolderRolPermiso extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView id,nombre;
    private ItemClickListener_Rol_Permiso itemClickListener_rol_permiso;

    public RecyclerViewHolderRolPermiso(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.card_tv_id_valor_info_rol_permiso);
        nombre= (TextView) itemView.findViewById(R.id.card_tv_nombre_valor_info_rol_permiso);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener_rol_permiso(ItemClickListener_Rol_Permiso itemClickListener_rol_permiso) {
        this.itemClickListener_rol_permiso = itemClickListener_rol_permiso;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener_rol_permiso.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener_rol_permiso.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class RecyclerAdapterRolPermiso extends RecyclerView.Adapter<RecyclerViewHolderRolPermiso>{

    private List<Rol_Permiso_Objeto> listData=new ArrayList<>();
    private Context context;
    P_Ver__Permisos_Rol p_ver__permisos_rol;

    public RecyclerAdapterRolPermiso(List<Rol_Permiso_Objeto> listData, Context context,
                                     P_Ver__Permisos_Rol p_ver__permisos_rol) {
        this.listData = listData;
        this.context = context;
        this.p_ver__permisos_rol = p_ver__permisos_rol;
    }

    @Override
    public RecyclerViewHolderRolPermiso onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.layout_item_recycler_view_rol_permisos,parent,false);

        return new RecyclerViewHolderRolPermiso(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderRolPermiso holder, int position) {
        Rol_Permiso_Objeto rol_permiso_objeto=listData.get(position);
       // Toast.makeText(context,"pos: "+position+" size: "+listData.size(),Toast.LENGTH_SHORT).show();
        holder.id.setText(rol_permiso_objeto.getPermiso_objeto().getId());
        holder.nombre.setText(rol_permiso_objeto.getPermiso_objeto().getNombre());


        holder.setItemClickListener_rol_permiso(new ItemClickListener_Rol_Permiso() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Rol_Permiso_Objeto rol_permi_obj1=listData.get(position);
                //Toast.makeText(context,"Long click: "+empresa_ob.toString(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,Info_Activity_Rol_Permiso.class);
                intent.putExtra("key",rol_permi_obj1);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
