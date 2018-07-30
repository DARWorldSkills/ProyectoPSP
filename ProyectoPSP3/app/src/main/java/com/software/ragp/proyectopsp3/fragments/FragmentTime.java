package com.software.ragp.proyectopsp3.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.software.ragp.proyectopsp3.R;
import com.software.ragp.proyectopsp3.controllers.MenuP;
import com.software.ragp.proyectopsp3.controllers.MenuProyecto;
import com.software.ragp.proyectopsp3.models.AdapterTimes;
import com.software.ragp.proyectopsp3.models.CConsultTimes;
import com.software.ragp.proyectopsp3.models.GestorDB;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTime extends Fragment {


    public FragmentTime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_time, container, false);
        final TextView txtHoras = view.findViewById(R.id.txtTiempoP);
        Button btnSave = view.findViewById(R.id.btnGuardarTime);
        GestorDB gestorDB = new GestorDB(getActivity());
        final SQLiteDatabase db = gestorDB.getWritableDatabase();
        final int proyecto = MenuP.cProyecto.getId();
        final Cursor cursor = db.rawQuery("SELECT * FROM PPS WHERE PROYECTO ="+proyecto+" ;",null);
        if (cursor.moveToFirst()){
            do {
                txtHoras.setText(Integer.toString(cursor.getInt(1)));

            }while (cursor.moveToNext());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int horas = Integer.parseInt(txtHoras.getText().toString());

                    if (cursor.moveToFirst()){


                        ContentValues values = new ContentValues();
                        values.put("HORAS",horas);
                        String [] paramenters = {Integer.toString(proyecto)};
                        db.update("PPS",values,"IDPPS=?",paramenters);
                        Snackbar.make(v,"Se ha actualizado las horas del proyecto", Snackbar.LENGTH_SHORT).show();

                    }else {
                        ContentValues values = new ContentValues();
                        values.put("HORAS" ,horas);
                        values.put("PROYECTO",proyecto);
                        db.insert("PPS",null,values);
                        Snackbar.make(v,"Se han ingresado las horas del proyecto", Snackbar.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Snackbar.make(v,"Campo vacio o con caracteres no numéricos", Snackbar.LENGTH_SHORT).show();
                }

                cursor.close();

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerTime);
        List<CConsultTimes> timesList = new ArrayList<>();
        AdapterTimes adapterTimes = new AdapterTimes(timesList);
        recyclerView.setAdapter(adapterTimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);


        return view;
    }

}
