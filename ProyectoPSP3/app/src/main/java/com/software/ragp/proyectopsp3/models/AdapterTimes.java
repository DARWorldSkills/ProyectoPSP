package com.software.ragp.proyectopsp3.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.software.ragp.proyectopsp3.R;

import java.util.List;

public class AdapterTimes extends RecyclerView.Adapter<AdapterTimes.Holder> {
    private List<CConsultTimes> timesList;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void itemClick(int position);

    }

    public AdapterTimes(List<CConsultTimes> timesList) {
        this.timesList = timesList;
    }


    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
