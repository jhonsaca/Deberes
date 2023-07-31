package ec.edu.tecnologicoloja.rom_listas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.ListAdapter;
import database.Persona;
import database.PersonaLab;

public class activityregistro extends AppCompatActivity implements View.OnClickListener {
    protected TextView txtname, txtape;
    protected ImageView img;
    protected Button btn_g, btn_c, btn_camm;
    private PersonaLab mpersonalab;
    public static int REQUEST_IMAGE_CAPTURE=1;
    Bundle extras;
    private Persona mpersona;
    private ListAdapter listItemAdapter;
    private ArrayList<Persona> personaList = new ArrayList<>();
    private ListView list;
    private MainActivity u = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityregistro);
        txtname = (TextView) findViewById(R.id.editTextPersona);
        txtape = (TextView) findViewById(R.id.editTextApellido);
        img = (ImageView) findViewById(R.id.img_contact);
        btn_c = (Button) findViewById(R.id.btn_cancel);
        listItemAdapter = new ListAdapter(this, personaList);
        btn_c.setOnClickListener(this);
        btn_g = (Button) findViewById(R.id.btn_save);
        btn_camm = (Button) findViewById(R.id.btn_cam);
        mpersonalab = new PersonaLab(this);
    }


    public void insertPersona(){
        mpersona = new Persona();
        mpersona.setNombre(txtname.getText().toString());
        mpersona.setApellido(txtape.getText().toString());
        mpersonalab.addPersona(mpersona);
    }

    @Override
    public void onClick(View v) {
        if (v==btn_camm){
            dispatchTakePictureIntent();
        } else if (v==btn_g) {
            insertPersona();
            listItemAdapter.notifyDataSetChanged();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            getAllPersonas();
        } else if (v==btn_c) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            getAllPersonas();
        }
    }
    public void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            extras= data.getExtras();
            Bitmap imgbt = (Bitmap) extras.get("data");
            img.setImageBitmap(imgbt);
            //control = true;
        }
    }

    public void getAllPersonas(){
        personaList.clear();
        personaList.addAll(mpersonalab.getPersonas());
    }

}