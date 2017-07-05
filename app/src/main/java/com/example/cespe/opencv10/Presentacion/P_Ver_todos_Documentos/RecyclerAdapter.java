package com.example.cespe.opencv10.Presentacion.P_Ver_todos_Documentos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cespe.opencv10.R;

import java.util.ArrayList;

/**
 * Created by cespe on 24/06/2017.
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

   // TextView txt_descripcion;
   TextView id,nombre,fecha,ruta,tipo,empresa;
    ItemClickListener_Documentos itemClickListenerDocumentos;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    //    txt_descripcion= (TextView) itemView.findViewById(R.id.txtDescripcion_item);
      //  id= (TextView) itemView.findViewById(R.id.card_tv_id_valor);
        fecha=(TextView) itemView.findViewById(R.id.card_tv_fecha_valor);
        nombre=(TextView) itemView.findViewById(R.id.card_tv_nombre_valor);
        ruta=(TextView) itemView.findViewById(R.id.card_tv_ruta_valor);
        empresa=(TextView) itemView.findViewById(R.id.card_tv_empresa_valor);
        tipo=(TextView) itemView.findViewById(R.id.card_tv_tipo_trabajo_valor);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListenerDocumentos(ItemClickListener_Documentos itemClickListenerDocumentos){
        this.itemClickListenerDocumentos = itemClickListenerDocumentos;
    }


    @Override
    public boolean onLongClick(View v) {
        itemClickListenerDocumentos.onClick(v,getAdapterPosition(),true);
        return true;
    }

    @Override
    public void onClick(View v) {
        itemClickListenerDocumentos.onClick(v,getAdapterPosition(),true);
    }
}

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    //List<String> listData=new ArrayList<>();
    Context context;
    ArrayList<Data> listData;
    Data data;

    public RecyclerAdapter(ArrayList<Data> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.layout_item_recycler_view,parent,false);

        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
     //   holder.txt_descripcion.setText(listData.get(position).id);

        data=listData.get(position);
        //holder.id.setText(data.getId());
        holder.nombre.setText(data.getNombre());
        holder.ruta.setText(data.getRuta());
        holder.fecha.setText(data.getFecha());
        holder.empresa.setText(data.getEmpresa());
        holder.tipo.setText(data.getTipo());

        holder.setItemClickListenerDocumentos(new ItemClickListener_Documentos() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Data da=listData.get(position);
                         //   Toast.makeText(context,"Long Click: "+da.toString(),Toast.LENGTH_LONG).show();

                Intent intent=new Intent(context,Info_Activity.class);
                intent.putExtra("key",da);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
