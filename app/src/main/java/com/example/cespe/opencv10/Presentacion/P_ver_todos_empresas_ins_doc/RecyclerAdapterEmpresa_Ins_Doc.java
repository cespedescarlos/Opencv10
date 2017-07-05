package com.example.cespe.opencv10.Presentacion.P_ver_todos_empresas_ins_doc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cespe.opencv10.Datos.Empresa_Objeto;
import com.example.cespe.opencv10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cespe on 25/06/2017.
 */

class RecyclerViewHolderEmpresaInsDoc extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public TextView id,nombre,telefono,nombre_contacto,telefono_contacto;
    private ItemClickListener_Empresas_Ins_Doc itemClickListener_empresas_ins_doc;

    public RecyclerViewHolderEmpresaInsDoc(View itemView) {
        super(itemView);
        id= (TextView) itemView.findViewById(R.id.card_tv_id_valor_info_empresas);
        nombre= (TextView) itemView.findViewById(R.id.card_tv_nombre_valor_info_empresas);
        telefono= (TextView) itemView.findViewById(R.id.card_tv_telefono_valor_info_empresas);
        nombre_contacto= (TextView) itemView.findViewById(R.id.card_tv_Nombre_contacto_valor_info_empresas);
        telefono_contacto=(TextView) itemView.findViewById(R.id.card_tv_telefono_contacto_valor_info_empresas);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener_empresas_ins_doc(ItemClickListener_Empresas_Ins_Doc itemClickListener_empresas_ins_doc) {
        this.itemClickListener_empresas_ins_doc = itemClickListener_empresas_ins_doc;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener_empresas_ins_doc.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener_empresas_ins_doc.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class RecyclerAdapterEmpresa_Ins_Doc extends RecyclerView.Adapter<RecyclerViewHolderEmpresaInsDoc>{

    private List<Empresa_Objeto> listData=new ArrayList<>();
    private Context context;
    P_Ver_todas_Empresas_Ins_doc p_ver_todas_empresas_ins_doc;

    public RecyclerAdapterEmpresa_Ins_Doc(List<Empresa_Objeto> listData, Context context,
                                          P_Ver_todas_Empresas_Ins_doc p_ver_todas_empresas_ins_doc) {
        this.listData = listData;
        this.context = context;
        this.p_ver_todas_empresas_ins_doc=p_ver_todas_empresas_ins_doc;
    }

    @Override
    public RecyclerViewHolderEmpresaInsDoc onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.layout_item_recycler_view_empresas_ins_doc,parent,false);

        return new RecyclerViewHolderEmpresaInsDoc(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderEmpresaInsDoc holder, int position) {
        Empresa_Objeto empresa_objeto=listData.get(position);
       // Toast.makeText(context,"pos: "+position+" size: "+listData.size(),Toast.LENGTH_SHORT).show();
        holder.id.setText(empresa_objeto.getId());
        holder.nombre.setText(empresa_objeto.getNombre());
        holder.nombre_contacto.setText(empresa_objeto.getNombre_contacto());
        holder.telefono_contacto.setText(empresa_objeto.getTelefono_contacto());
        holder.telefono.setText(empresa_objeto.getTelefono());


        holder.setItemClickListener_empresas_ins_doc(new ItemClickListener_Empresas_Ins_Doc() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Empresa_Objeto empresa_ob=listData.get(position);
                //Toast.makeText(context,"Long click: "+empresa_ob.toString(),Toast.LENGTH_SHORT).show();
                    p_ver_todas_empresas_ins_doc.volviendo_recycler(empresa_ob);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
