package ec.edu.tecnologicoloja.rom_listas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import database.Persona;
import database.PersonaLab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button btn1, btn2;
    protected TextView txt1;
    PersonaLab mpersonalab;
    Persona per;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btnsave);
        btn2 = (Button) findViewById(R.id.btnclear);
        txt1 = (TextView) findViewById(R.id.editTextPersona);
        btn2.setOnClickListener(this::onClick);
        btn1.setOnClickListener(this);
        mpersonalab = new PersonaLab(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btn1){
            per = new Persona();
            per.setNombre(txt1.toString());
            nombre=txt1.toString();
            mpersonalab.addPersona(per);
            Log.i(null, "onClick: click guardar");
            List<Persona> personaList = new ArrayList<>();
            personaList = mpersonalab.getPersonas();
            for (int i =0; i< personaList.size();i++){
                Toast.makeText(this, "Nombre:"+mpersonalab.getPersona(nombre), Toast.LENGTH_SHORT).show();
                System.out.println(personaList.get(i).toString());
            }
        }
        if (v==btn2){
            mpersonalab.deleteAllPersona();
            Log.i(null, "onClick: click borrar");
        }
    }
}