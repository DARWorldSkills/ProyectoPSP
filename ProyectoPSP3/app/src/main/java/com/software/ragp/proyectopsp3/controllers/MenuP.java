package com.software.ragp.proyectopsp3.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.software.ragp.proyectopsp3.R;
import com.software.ragp.proyectopsp3.models.AdapterP;
import com.software.ragp.proyectopsp3.models.ManagerDB;
import com.software.ragp.proyectopsp3.models.Proyecto;

import java.util.List;

public class MenuP extends AppCompatActivity {
    public static Proyecto cProyecto = new Proyecto();
    RecyclerView recyclerView;
    FloatingActionButton btnAgregarP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);
        recyclerView = findViewById(R.id.recyclerView);
        btnAgregarP = findViewById(R.id.btnAgregarP);
        cProyecto = new Proyecto();
        inputAdapter();

        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuP.this);
                builder.setTitle("Agregar Proyecto");
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.item_proyectoa,null);
                final EditText txtProyecto = view.findViewById(R.id.txtNombreP);
                builder.setView(view);
                builder.setPositiveButton("Agreagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ManagerDB managerDB = new ManagerDB(MenuP.this);
                            Proyecto proyecto = new Proyecto();
                            proyecto.setProyecto(txtProyecto.getText().toString());
                            if (proyecto.getProyecto().length()>1) {
                                managerDB.inputProyecto(proyecto);
                                dialog.cancel();
                                inputAdapter();
                                Snackbar.make(v,"Se ha agregado el proyecto "+proyecto.getProyecto(),Snackbar.LENGTH_SHORT).show();
                            }else {
                                Snackbar.make(v,"Para poder agreagar un proyecto por favor no deje el campo vacio",Snackbar.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Snackbar.make(v,"Por favor ingrese un nombre",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });
    }

    private void inputAdapter() {
        ManagerDB managerDB = new ManagerDB(this);
        final List<Proyecto> proyectoList = managerDB.selectProject();
        AdapterP adapterP = new AdapterP(proyectoList);
        recyclerView.setAdapter(adapterP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapterP.setMlistener(new AdapterP.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                cProyecto = proyectoList.get(position);
                Intent intent = new Intent(MenuP.this,MenuProyecto.class);
                startActivity(intent);
            }
        });
    }


}
