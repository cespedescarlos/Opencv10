package com.example.cespe.opencv10.Presentacion.P_Ver_todos_tipo_trabajos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.Datos.Tipo_Trabajo_Objecto;
import com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc.ItemClickListener_Empresas_Ins_Doc;
import com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc.P_Ver_todas_Empresas_Ins_doc;
import com.example.cespe.opencv10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cespe on 25/06/2017.
 */

class RecyclerViewHolderTipoTrabajo extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView id,nombre;
    private ItemClickListener_Tipo_Trabajos itemClickListener_tipo_trabajos;

    public RecyclerViewHolderTipoTrabajo(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.card_tv_id_valor_info_tipo_trabajo);
        nombre= (TextView) itemView.findViewById(R.id.card_tv_nombre_valor_info_tipo_trabajo);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener_tipo_trabajos(ItemClickListener_Tipo_Trabajos itemClickListener_tipo_trabajos) {
        this.itemClickListener_tipo_trabajos = itemClickListener_tipo_trabajos;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener_tipo_trabajos.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener_tipo_trabajos.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class RecyclerAdapterTipoTrabajo extends RecyclerView.Adapter<RecyclerViewHolderTipoTrabajo>{

    private List<Tipo_Trabajo_Objecto> listData=new ArrayList<>();
    private Context context;
    P_Ver_todos_tipo_trabajos p_ver_todos_tipo_trabajos;

    public RecyclerAdapterTipoTrabajo(List<Tipo_Trabajo_Objecto> listData, Context context,
                                      P_Ver_todos_tipo_trabajos p_ver_todos_tipo_trabajos) {
        this.listData = listData;
        this.context = context;
        this.p_ver_todos_tipo_trabajos = p_ver_todos_tipo_trabajos;
    }

    @Override
    public RecyclerViewHolderTipoTrabajo onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.layout_item_recycler_view_tipo_trabajo_ins_doc,parent,false);

        return new RecyclerViewHolderTipoTrabajo(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderTipoTrabajo holder, int position) {
        Tipo_Trabajo_Objecto tipo_trabajo_objecto=listData.get(position);
       // Toast.makeText(context,"pos: "+position+" size: "+listData.size(),Toast.LENGTH_SHORT).show();
        holder.id.setText(tipo_trabajo_objecto.getId());
        holder.nombre.setText(tipo_trabajo_objecto.getNombre());


        holder.setItemClickListener_tipo_trabajos(new ItemClickListener_Tipo_Trabajos() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Tipo_Trabajo_Objecto tipo_oj=listData.get(position);
                //Toast.makeText(context,"Long click: "+empresa_ob.toString(),Toast.LENGTH_SHORT).show();
                    p_ver_todos_tipo_trabajos.volviendo_recycler(tipo_oj);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
