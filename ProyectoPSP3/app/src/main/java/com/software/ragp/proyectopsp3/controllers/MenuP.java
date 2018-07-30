package com.software.ragp.proyectopsp3.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.software.ragp.proyectopsp3.R;

public class MenuP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);
    }

    public void go(View view) {

        Intent intent = new Intent(MenuP.this, TimeLog.class);
        startActivity(intent);
    }

    public void ir(View view) {
        Intent intent = new Intent(MenuP.this, DefectLog.class);
        startActivity(intent);
    }
}
