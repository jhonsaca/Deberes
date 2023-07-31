package ec.edu.tecnologicoloja.rom_listas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.ListAdapter;
import database.Persona;
import database.PersonaLab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button btn_nuevo, btn_borrar;
    protected ListView list;
    private PersonaLab mpersonalab;
    private Persona mpersona = null;
    private ListAdapter listItemAdapter;
    private ArrayList<Persona> personaList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_nuevo = (Button) findViewById(R.id.btn_new);
        btn_borrar = (Button) findViewById(R.id.btn_clear);
        list = (ListView) findViewById(R.id.lista);
        personaList.add(mpersona);
        getAllPersonas();
        listItemAdapter = new ListAdapter(this, personaList);
        list.setAdapter(listItemAdapter);
        enableMyCamera();
        btn_borrar.setOnClickListener(this);
        btn_nuevo.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v==btn_nuevo){
            Intent i = new Intent(this, activityregistro.class);
            startActivity(i);
            listItemAdapter.notifyDataSetChanged();
        }
        if (v==btn_borrar){
            mpersonalab.deleteAllPersona();
            personaList.clear();
            listItemAdapter.notifyDataSetChanged();
            getAllPersonas();
        }
    }

    public void getAllPersonas(){
        personaList.clear();
        personaList.addAll(mpersonalab.getPersonas());
    }
    private void enableMyCamera(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,},1000);

        }

    }

}