package com.software.ragp.proyectopsp3.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ragp.proyectopsp3.R;
import com.software.ragp.proyectopsp3.models.CTimeLog;
import com.software.ragp.proyectopsp3.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLog extends AppCompatActivity implements View.OnClickListener{
    TextView txtStart, txtInterrupcion, txtStop, txtDelta, txtComments;
    Button btnStart, btnStop, btnInsert, btnUpdate, btnDelete, btnGuardar, btnAtras, btnAdelante;
    Spinner spPhase;
    int modo=1;
    List<CTimeLog> cTimeLogs = new ArrayList<>();
    Date dateStart, dateStop;
    int delta =0;
    int interrupcion=0;
    private TextView mTextMessage;
    ConstraintLayout contenedor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.atrasT:
                    //Funcion boton atras
                    return true;
                case R.id.GuardarT:
                    //Funcion boton Guardar

                    switch (modo){
                        case 1:
                            inputData(contenedor);
                            break;

                        case 2:
                            updateData(contenedor);
                            break;

                        case 3:
                            deleteData();
                            break;
                    }

                    return true;
                case R.id.siguineteT:
                    //Funcion boton siguinete
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setContentView(R.layout.activity_time_log);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        modo=1;
        delta =0;
        interrupcion=0;
        inizialite();
        disable();
        inputPhase();
        selectTimeLog();
        btnStop.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // llamamos el menu del toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_editarT) {

            //Aquí se pone lo que hacen los botones de editar en TImerLog
        }

        if (id == R.id.action_EliminarT) {

            //Aquí se pone lo que hacen los botones de eliminar en TImerLog


        }

        return super.onOptionsItemSelected(item);
    }


    private void disable () {
        txtStart.setEnabled(false);
        txtStop.setEnabled(false);
        txtDelta.setEnabled(false);
    }

    private void selectTimeLog () {
        ManagerDB managerDB = new ManagerDB(this);
        cTimeLogs = managerDB.selectTimeLog(MenuP.cProyecto.getId());

    }

    private void inputPhase () {
        List<String> phaseList = new ArrayList<>();
        phaseList.add("Planning");
        phaseList.add("Design");
        phaseList.add("Code");
        phaseList.add("Compile");
        phaseList.add("UT");
        phaseList.add("PM");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phaseList);
        spPhase.setAdapter(adapter);

    }

    private void inizialite () {
        txtStart = findViewById(R.id.txtStart);
        txtInterrupcion = findViewById(R.id.txtInterrupcion);
        txtStop = findViewById(R.id.txtStop);
        txtDelta = findViewById(R.id.txtDelta);
        txtComments = findViewById(R.id.txtCommentsT);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);



        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        btnGuardar.setEnabled(true);

        spPhase = findViewById(R.id.spPhase);
        contenedor= findViewById(R.id.container);
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {

            

            case R.id.btnStart:
                inputDateStart();
                btnStop.setEnabled(true);
                break;

            case R.id.btnStop:
                inputDateStop();
                getDiferencia();
                Snackbar.make(v, "Se ha calculado Delta ", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void inputDateStart () {
        dateStart = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(dateStart);
        txtStart.setText(fecha);
    }

    private void obtenerInterrupcion () {
        try {
            String tmp1 = txtInterrupcion.getText().toString();
            int tmp2 = Integer.parseInt(tmp1);
            interrupcion = tmp2;

        } catch (Exception e) {
            interrupcion = 0;
        }

    }

    private void inputDateStop () {
        dateStop = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(dateStop);
        txtStop.setText(fecha);
    }


    public void getDiferencia () {
        long diferencia = dateStop.getTime() - dateStart.getTime();

        //long miliSeg = 1000;
        //long minsSeg = miliSeg*60;
        //long horasSeg = minsSeg*60;
        //long diasMil = horasSeg*60;

        float tmp = (int) (((diferencia / 60) / 60) - interrupcion);
        obtenerInterrupcion();

        delta = (int) tmp;
        txtDelta.setText(Integer.toString(delta));


    }

    private void inputData (View v){
        CTimeLog ctimeLog = new CTimeLog();
        try {
            ctimeLog.setPhase(spPhase.getSelectedItem().toString());
            ctimeLog.setStart(txtStart.getText().toString());
            ctimeLog.setStop(txtStop.getText().toString());
            ctimeLog.setInterrupcion(txtInterrupcion.getText().toString());
            ctimeLog.setDelta(txtDelta.getText().toString());
            ctimeLog.setComments(txtComments.getText().toString());
            ctimeLog.setProyecto(MenuP.cProyecto.getId());
            ManagerDB managerDB = new ManagerDB(this);
            managerDB.inputTimeLog(ctimeLog, v);
            selectTimeLog();


        } catch (Exception e) {
            Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData (View view){
        if (cTimeLogs.size() > 0) {
            CTimeLog ctimeLog = new CTimeLog();
            try {
                ctimeLog.setPhase(spPhase.getSelectedItem().toString());
                ctimeLog.setStart(txtStart.getText().toString());
                ctimeLog.setStop(txtStop.getText().toString());
                ctimeLog.setInterrupcion(txtInterrupcion.getText().toString());
                ctimeLog.setDelta(txtDelta.getText().toString());
                ctimeLog.setComments(txtComments.getText().toString());
                ctimeLog.setProyecto(MenuP.cProyecto.getId());
                ManagerDB managerDB = new ManagerDB(this);
                managerDB.inputTimeLog(ctimeLog, view);
                selectTimeLog();


            } catch (Exception e) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay TimeLogs para actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData () {
        if (cTimeLogs.size() > 0) {
            ManagerDB managerDB = new ManagerDB(this);


        } else {
            Toast.makeText(this, "No hay TimeLogs para eliminar", Toast.LENGTH_SHORT).show();
        }
    }



}
