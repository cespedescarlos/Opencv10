package com.example.cespe.opencv10.Presentacion.P_Ver_Roles_Usuario_Reg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cespe.opencv10.Datos.Rol_Objeto;
import com.example.cespe.opencv10.Datos.Rol_Permiso_Objeto;
import com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol.Info_Activity_Rol_Permiso;
import com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol.ItemClickListener_Rol_Permiso;
import com.example.cespe.opencv10.Presentacion.P_Ver_Todos_Permisos_Rol.P_Ver__Permisos_Rol;
import com.example.cespe.opencv10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cespe on 25/06/2017.
 */

class RecyclerViewHolderRolUsuario_reg extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView id,nombre;
    private ItemClickListener_Usuario_reg itemClickListener_usuario_reg;

    public RecyclerViewHolderRolUsuario_reg(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.card_tv_id_valor_info_usuario_reg);
        nombre= (TextView) itemView.findViewById(R.id.card_tv_nombre_valor_info_usuario_reg);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener_usuario_reg(ItemClickListener_Usuario_reg itemClickListener_usuario_reg) {
        this.itemClickListener_usuario_reg = itemClickListener_usuario_reg;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener_usuario_reg.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener_usuario_reg.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class RecyclerAdapterRolUsuarioReg extends RecyclerView.Adapter<RecyclerViewHolderRolUsuario_reg>{

    private List<Rol_Objeto> listData=new ArrayList<>();
    private Context context;
    P_ver_roles_usuario_reg p_ver_roles_usuario_reg;
    //P_Ver__Permisos_Rol p_ver__permisos_rol;

    public RecyclerAdapterRolUsuarioReg(List<Rol_Objeto> listData, Context context,P_ver_roles_usuario_reg p_ver_roles_usuario_reg) {
        this.listData = listData;
        this.context = context;
        this.p_ver_roles_usuario_reg=p_ver_roles_usuario_reg;
    }

    @Override
    public RecyclerViewHolderRolUsuario_reg onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.layout_item_recycler_view_rol_usuario,parent,false);

        return new RecyclerViewHolderRolUsuario_reg(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderRolUsuario_reg holder, int position) {
        final Rol_Objeto rol_objeto=listData.get(position);
       // Toast.makeText(context,"pos: "+position+" size: "+listData.size(),Toast.LENGTH_SHORT).show();
        holder.id.setText(rol_objeto.getId());
        holder.nombre.setText(rol_objeto.getNombre());


        holder.setItemClickListener_usuario_reg(new ItemClickListener_Usuario_reg() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Rol_Objeto rol_objeto_selec=listData.get(position);
                //Toast.makeText(context,"rol: "+rol_objeto1.getId()+"; "+rol_objeto1.getNombre(),Toast.LENGTH_SHORT).show();
                p_ver_roles_usuario_reg.volviendo_recycler(rol_objeto_selec);
                //Intent intent=new Intent(context,Info_Activity_Rol_Permiso.class);
                //intent.putExtra("key",rol_objeto1);
               // context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
