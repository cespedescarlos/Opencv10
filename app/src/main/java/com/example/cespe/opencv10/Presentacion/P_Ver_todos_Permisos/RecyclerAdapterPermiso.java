package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Permisos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cespe.opencv10.Datos.Permiso_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos.ItemClickListener_Tipo_Trabajos;
import com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos.P_Ver_todos_tipo_trabajos;
import com.example.cespe.opencv10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cespe on 25/06/2017.
 */

class RecyclerViewHolderPermiso extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView id,nombre;
    private ItemClickListener_Permiso itemClickListener_permiso;

    public RecyclerViewHolderPermiso(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.card_tv_id_valor_info_permiso);
        nombre= (TextView) itemView.findViewById(R.id.card_tv_nombre_valor_info_permiso);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener_permiso(ItemClickListener_Permiso itemClickListener_permiso) {
        this.itemClickListener_permiso = itemClickListener_permiso;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener_permiso.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener_permiso.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class RecyclerAdapterPermiso extends RecyclerView.Adapter<RecyclerViewHolderPermiso>{

    private List<Permiso_Objeto> listData=new ArrayList<>();
    private Context context;
    P_Ver_todos_permisos p_ver_todos_permisos;

    public RecyclerAdapterPermiso(List<Permiso_Objeto> listData, Context context,
                                  P_Ver_todos_permisos p_ver_todos_permisos) {
        this.listData = listData;
        this.context = context;
        this.p_ver_todos_permisos = p_ver_todos_permisos;
    }

    @Override
    public RecyclerViewHolderPermiso onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.layout_item_recycler_view_permisos,parent,false);

        return new RecyclerViewHolderPermiso(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderPermiso holder, int position) {
        Permiso_Objeto permiso_objeto=listData.get(position);
       // Toast.makeText(context,"pos: "+position+" size: "+listData.size(),Toast.LENGTH_SHORT).show();
        holder.id.setText(permiso_objeto.getId());
        holder.nombre.setText(permiso_objeto.getNombre());


        holder.setItemClickListener_permiso(new ItemClickListener_Permiso() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Permiso_Objeto permiso_oj=listData.get(position);
                //Toast.makeText(context,"Long click: "+empresa_ob.toString(),Toast.LENGTH_SHORT).show();
                    p_ver_todos_permisos.volviendo_recycler(permiso_oj);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
