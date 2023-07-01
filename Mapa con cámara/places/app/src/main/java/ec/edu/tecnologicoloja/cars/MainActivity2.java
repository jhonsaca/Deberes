package ec.edu.tecnologicoloja.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private Button btn4, btn5, btn6, btn7;
    private ImageView img;
    Bundle extras;
    public static int REQUEST_IMAGE_CAPTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        img = (ImageView) findViewById(R.id.imagen1);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
    }

    public void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==btn4){
            dispatchTakePictureIntent();
        }
        if (v==btn5){
            Intent i = new Intent(MainActivity2.this, MapsActivity.class);
            startActivity(i);
        }
        if (v==btn6){

        }
        if (v==btn7){
            img.setImageResource(R.drawable.fondo);
        }
    }
    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            extras= data.getExtras();
            Bitmap imgbt = (Bitmap) extras.get("data");
            img.setImageBitmap(imgbt);
        }
    }
}