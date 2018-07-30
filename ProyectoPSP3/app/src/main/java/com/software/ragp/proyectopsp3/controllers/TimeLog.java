package com.software.ragp.proyectopsp3.controllers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.software.ragp.proyectopsp3.models.CDefectLog;
import com.software.ragp.proyectopsp3.models.CTimeLog;
import com.software.ragp.proyectopsp3.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLog extends AppCompatActivity implements View.OnClickListener{
    TextView txtStart, txtInterrupcion, txtStop, txtDelta, txtComments;
    Button btnStart, btnStop;
    Spinner spPhase;
    int modo=1;
    List<CTimeLog> cTimeLogs = new ArrayList<>();
    Date dateStart, dateStop;
    int delta =0;
    int interrupcion=0;
    private TextView mTextMessage;
    ConstraintLayout contenedor;
    CTimeLog cTimeLogV=new CTimeLog();
    List<String> phaseList =new ArrayList<>();
    FloatingActionButton btnIngresar;
    int valor=1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.atrasT:
                    //Funcion boton atras
                    if (modo>1){
                        retroceder();
                    }
                    return true;
                case R.id.GuardarT:
                    //Funcion boton Guardar

                    switch (modo){
                        case 1:

                            inputData(contenedor);
                            selectTimeLog();
                            valor=0;
                            break;

                        case 2:
                            updateData(contenedor);
                            selectTimeLog();
                            valor=0;
                            break;

                        case 3:
                            deleteData(contenedor);
                            valor=0;
                            break;
                    }

                    return true;
                case R.id.siguineteT:

                    //Funcion boton siguinete
                    if (modo>1){
                        avanzar();
                    }
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationT);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        modo=1;
        delta =0;
        valor=0;
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
            modo=2;
        }

        if (id == R.id.action_EliminarT) {

            //Aquí se pone lo que hacen los botones de eliminar en TImerLog
            modo=3;

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
        cleanV();
    }

    public void cleanV(){
        txtStart.setText("");
        txtStop.setText("");
        txtInterrupcion.setText("");
        txtDelta.setText("");
        txtComments.setText("");
    }

    private void inputPhase () {
        phaseList = new ArrayList<>();
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
        spPhase = findViewById(R.id.spPhase);
        contenedor= findViewById(R.id.container);
        btnIngresar= findViewById(R.id.btnIngresar);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        btnIngresar.setOnClickListener(this);


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

            case R.id.btnIngresar:
                modo=1;
                cleanV();
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

    }
    private void updateData (View view){
        if (cTimeLogs.size() > 0) {
            CTimeLog ctimeLog = new CTimeLog();
            try {
                ctimeLog.setId(cTimeLogV.getId());
                ctimeLog.setPhase(spPhase.getSelectedItem().toString());
                ctimeLog.setStart(txtStart.getText().toString());
                ctimeLog.setStop(txtStop.getText().toString());
                ctimeLog.setInterrupcion(txtInterrupcion.getText().toString());
                ctimeLog.setDelta(txtDelta.getText().toString());
                ctimeLog.setComments(txtComments.getText().toString());
                ctimeLog.setProyecto(MenuP.cProyecto.getId());
                ManagerDB managerDB = new ManagerDB(this);
                managerDB.updateTimeLog(ctimeLog, view);
                selectTimeLog();


            } catch (Exception e) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No hay TimeLogs para actualizar", Toast.LENGTH_SHORT).show();
        }
    }



    private void deleteData(final View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("Desea elimnar el DefectLog");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ManagerDB managerDB = new ManagerDB(TimeLog.this);
                managerDB.deleteTimeLog(cTimeLogV,v);
                selectTimeLog();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }


    public void avanzar(){
        if (cTimeLogs.size() > valor) {
            valor++;
            inputValues();
        }


        if (cTimeLogs.size()==1){
            valor=1;
            inputValues();
        }

    }

    public void retroceder(){
        if (valor>1) {
            if (cTimeLogs.size() >= valor && valor != 0) {
                valor--;
                inputValues();
            }
        }else {
            valor=1;
            inputValues();
        }

        if (valor==0){
            valor=1;
            inputValues();
        }

        if (cTimeLogs.size()==1){
            valor=1;
            inputValues();
        }
    }

    public void inputValues(){
        cTimeLogV= cTimeLogs.get(valor-1);
        txtStart.setText(cTimeLogV.getStart());
        txtInterrupcion.setText(cTimeLogV.getInterrupcion());
        txtStop.setText(cTimeLogV.getStop());
        txtDelta.setText(cTimeLogV.getDelta());
        txtComments.setText(cTimeLogV.getComments());

        for (int i=0; i<phaseList.size(); i++) {
            try {
                if (cTimeLogV.getPhase().equals(phaseList.get(i))) {
                    spPhase.setSelection(i);
                }
            }catch (Exception e){

            }
        }

    }


}
