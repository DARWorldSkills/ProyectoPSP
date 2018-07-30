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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.software.ragp.proyectopsp3.R;
import com.software.ragp.proyectopsp3.models.CDefectLog;
import com.software.ragp.proyectopsp3.models.GestorDB;
import com.software.ragp.proyectopsp3.models.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefectLog extends AppCompatActivity implements View.OnClickListener{

    TextView txtDate, txtFixTime, txtComments;
    ImageButton btnPlay, btnPause, btnStop;
    Spinner spType, spPhaseI, spPhaseR;
    Button btnDate;
    FloatingActionButton btnIngresar;
    Thread thread;
    boolean bandera = true;
    boolean bandera1 = false;
    List<CDefectLog> cDefectLogs = new ArrayList<>();
    int []tiempo = {0,0};
    int modo=1;
    ConstraintLayout contenedor;
    private TextView mTextMessage;
    List<String> typeList;
    List<String> phaseList;
    int valor=0;
    CDefectLog cDefectLogV = new CDefectLog();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                case R.id.atrasD:
                    //Funcion boton atras
                    if (modo>1){
                        retroceder();
                    }
                    return true;
                case R.id.GuardarD:
                    //Funcion boton guardar

                    switch (modo){
                        case 1:
                            inputData(contenedor);
                            selectDefectLog();
                            valor=0;
                            break;

                        case 2:
                            stop_chronometer();
                            updateData(contenedor);
                            selectDefectLog();
                            valor=0;
                            break;

                        case 3:
                            stop_chronometer();
                            deleteData(contenedor);
                            valor=0;
                            break;
                    }
                    return true;
                case R.id.SiguinteD:
                    //Funcion boton siguinte
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
        setContentView(R.layout.activity_defect_log);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bandera = true;
        tiempo = new int []{0,0};
        modo=1;
        bandera1 = false;
        inizialite();
        onClickThis();
        disableT();
        inputList();
        chronometer();
        selectDefectLog();
        txtFixTime.setText("0" + tiempo[1] + ":" + "0" + tiempo[0]);

    }



    private void inizialite() {
        txtDate = findViewById(R.id.txtDateD);
        txtFixTime = findViewById(R.id.txtFixTimeD);
        txtComments = findViewById(R.id.txtCommentsD);

        btnPlay = findViewById(R.id.imgPlayD);
        btnPause = findViewById(R.id.imgPauseD);
        btnStop = findViewById(R.id.imgStopD);

        spType = findViewById(R.id.spTypeD);
        spPhaseI = findViewById(R.id.spPhaseI);
        spPhaseR = findViewById(R.id.spPhaseR);

        btnDate = findViewById(R.id.btnDateD);
        contenedor = findViewById(R.id.container);
        btnIngresar = findViewById(R.id.btnIngresar);
    }



    private void onClickThis() {
        btnDate.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
    }


    private void inputList() {
        phaseList = new ArrayList<>();
        phaseList.add("Planning");
        phaseList.add("Design");
        phaseList.add("Code");
        phaseList.add("Compile");
        phaseList.add("UT");
        phaseList.add("PM");

        typeList = new ArrayList<>();
        typeList.add("Documentation");
        typeList.add("Syntax");
        typeList.add("Build");
        typeList.add("Package");
        typeList.add("Assigment");
        typeList.add("Interface");
        typeList.add("Checking");
        typeList.add("Data");
        typeList.add("Fuction");
        typeList.add("System");
        typeList.add("Environment");

        ArrayAdapter<String> adapterPhase= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,phaseList);
        ArrayAdapter<String> adapterType= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,typeList);

        spType.setAdapter(adapterType);
        spPhaseI.setAdapter(adapterPhase);
        spPhaseR.setAdapter(adapterPhase);

    }

    private void disableT() {
        txtDate.setEnabled(false);
        txtFixTime.setEnabled(false);

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
            modo=2;
        }

        if (id == R.id.action_EliminaD){

            //Aquí se pone lo que hacen los botones de eliminar en DefecLog
            modo=3;

        }
        return super.onOptionsItemSelected(item);
    }


    private void obtenerHora() {
        Date date = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fecha = sformat.format(date);
        txtDate.setText(fecha);
    }

    private void selectDefectLog() {
        ManagerDB managerDB = new ManagerDB(this);
        cDefectLogs = managerDB.selectDefectLog(MenuP.cProyecto.getId());
        cleanV();

    }

    private void inputData(View view) {
        ManagerDB managerDB = new ManagerDB(this);
        CDefectLog cDefectLog = new CDefectLog();
        cDefectLog.setDate(txtDate.getText().toString());
        cDefectLog.setType(spType.getSelectedItem().toString());
        cDefectLog.setFixtime(txtFixTime.getText().toString());
        cDefectLog.setComments(txtComments.getText().toString());
        cDefectLog.setPhaseI(spPhaseI.getSelectedItem().toString());
        cDefectLog.setPhaseR(spPhaseR.getSelectedItem().toString());
        cDefectLog.setProyecto(MenuP.cProyecto.getId());
        managerDB.inputDefectLog(cDefectLog,view);
    }

    private void updateData(View v) {
        ManagerDB managerDB = new ManagerDB(this);
        CDefectLog cDefectLog = new CDefectLog();
        cDefectLog.setId(cDefectLogV.getId());
        cDefectLog.setDate(txtDate.getText().toString());
        cDefectLog.setType(spType.getSelectedItem().toString());
        cDefectLog.setFixtime(txtFixTime.getText().toString());
        cDefectLog.setComments(txtComments.getText().toString());
        cDefectLog.setPhaseI(spPhaseI.getSelectedItem().toString());
        cDefectLog.setPhaseR(spPhaseR.getSelectedItem().toString());
        managerDB.updateDefectLog(cDefectLog,v);
    }

    private void deleteData(final View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("Desea elimnar el DefectLog");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ManagerDB managerDB = new ManagerDB(DefectLog.this);
                CDefectLog cDefectLog = new CDefectLog();
                cDefectLog.setId(cDefectLogV.getId());
                managerDB.deleteDefectLog(cDefectLog,v);
                selectDefectLog();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }


    private void chronometer() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera){
                    try {
                        {
                            Thread.sleep(1000);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (bandera1) {
                                    tiempo[0]++;
                                    if (tiempo[0] == 60) {
                                        tiempo[1]++;
                                        tiempo[0] = 0;
                                    }


                                    if (tiempo[0] >= 0 && tiempo[0] < 10) {
                                        if (tiempo[0] >= 0 && tiempo[0] < 10) {
                                            txtFixTime.setText("0" + tiempo[1] + ":" + "0" + tiempo[0]);

                                        } else {
                                            txtFixTime.setText(tiempo[1] + ":" + "0" + tiempo[0]);
                                        }

                                    }

                                    if (tiempo[0] >= 10 && tiempo[0] < 60) {
                                        if (tiempo[0] >= 0 && tiempo[0] < 10) {
                                            txtFixTime.setText("0" + tiempo[1] + ":" + tiempo[0]);

                                        } else {
                                            txtFixTime.setText(tiempo[1] + ":" + tiempo[0]);
                                        }
                                    }

                                }


                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bandera=false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnDateD:
                obtenerHora();
                break;

            case R.id.imgPlayD:
                go_chronometer();
                Snackbar.make(v,"Start",Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.imgPauseD:
                pause_chronometer();
                Snackbar.make(v,"Pause",Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.imgStopD:
                stop_chronometer();
                Snackbar.make(v,"Removed",Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.btnIngresar:
                modo=1;
                cleanV();
                break;

        }

    }

    private void cleanV() {
        txtDate.setText("");
        txtComments.setText("");
        txtFixTime.setText("00:00");
        stop_chronometer();
        valor=0;
    }

    public void go_chronometer(){
        bandera1=true;
    }

    public void pause_chronometer(){
        bandera1=false;
    }

    public void stop_chronometer(){
        bandera1=false;
        tiempo[0]=0;
        tiempo[1]=0;
        txtFixTime.setText("0" + tiempo[1] + ":" + "0" + tiempo[0]);
    }

    public void avanzar(){
        if (cDefectLogs.size() > valor) {
                valor++;
                inputValues();
            }


        if (cDefectLogs.size()==1){
            valor=1;
            inputValues();
        }

    }

    public void retroceder(){
        if (valor>1) {
            if (cDefectLogs.size() >= valor && valor != 0) {
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

        if (cDefectLogs.size()==1){
            valor=1;
            inputValues();
        }
    }

    public void inputValues(){

        cDefectLogV= cDefectLogs.get(valor-1);
        txtDate.setText(cDefectLogV.getDate());
        txtFixTime.setText(cDefectLogV.getFixtime());
        txtComments.setText(cDefectLogV.getComments());
        for (int i=0; i<typeList.size(); i++) {
            try {
                if (cDefectLogV.getType().equals(typeList.get(i))) {
                    spType.setSelection(i);
                }
            }catch (Exception e){

            }

        }

        for (int i=0; i<phaseList.size(); i++) {
            try {
                if (cDefectLogV.getPhaseI().equals(phaseList.get(i))) {
                    spPhaseI.setSelection(i);
                }
            }catch (Exception e){

            }
        }

        for (int i=0; i<phaseList.size(); i++) {
            try {
                if (cDefectLogV.getPhaseI().equals(phaseList.get(i))) {
                    spPhaseR.setSelection(i);
                }
            }catch (Exception e){

            }
        }

    }


}
