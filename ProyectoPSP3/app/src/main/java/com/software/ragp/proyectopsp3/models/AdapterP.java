package com.software.ragp.proyectopsp3.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.software.ragp.proyectopsp3.R;

import java.util.List;

public class AdapterP extends RecyclerView.Adapter<AdapterP.Holder> {
    List<Proyecto> proyectoList;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterP(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto,parent,false);
        Holder holder = new Holder(view, mlistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connectData(proyectoList.get(position));
    }

    @Override
    public int getItemCount() {
        return proyectoList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtProyecto = itemView.findViewById(R.id.txtProyectoI);
        public Holder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.itemClick(position);
                        }
                    }
                }
            });
        }

        public void connectData (Proyecto proyecto){
            txtProyecto.setText(proyecto.getProyecto());
        }
    }
}
