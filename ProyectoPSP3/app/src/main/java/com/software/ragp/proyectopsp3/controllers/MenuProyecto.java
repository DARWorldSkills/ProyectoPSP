package com.software.ragp.proyectopsp3.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.software.ragp.proyectopsp3.R;

public class MenuProyecto extends AppCompatActivity implements View.OnClickListener{
    Button btnTime, btnDefect, btnPPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proyecto);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnTime = findViewById(R.id.btnTime);
        btnDefect = findViewById(R.id.btnDefect);
        btnPPS = findViewById(R.id.btnPPS);

        btnTime.setOnClickListener(this);
        btnDefect.setOnClickListener(this);
        btnPPS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnTime:
                intent = new Intent(MenuProyecto.this, TimeLog.class);
                startActivity(intent);
                break;

            case R.id.btnDefect:
                intent = new Intent(MenuProyecto.this, DefectLog.class);
                startActivity(intent);
                break;

            case R.id.btnPPS:
                intent = new Intent(MenuProyecto.this, ProjectPlanSummary.class);
                startActivity(intent);
                break;



        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
