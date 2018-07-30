package com.software.ragp.proyectopsp3.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.software.ragp.proyectopsp3.R;

public class DefectLog extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.atrasD:
                    //fincion boton atras
                    return true;
                case R.id.GuardarD:
                    //Funcion boton guardar
                    return true;
                case R.id.SiguinteD:
                    //Funcion boton siguinte
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_log);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // llamamos el menu del toolbar
        getMenuInflater().inflate(R.menu.menu_defect, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_editarD){

            //Aquí se pone lo que hacen los botones de editar en DefectLog
        }

        if (id == R.id.action_EliminaD){

            //Aquí se pone lo que hacen los botones de eliminar en DefecLog

        }
        return super.onOptionsItemSelected(item);
    }

}
