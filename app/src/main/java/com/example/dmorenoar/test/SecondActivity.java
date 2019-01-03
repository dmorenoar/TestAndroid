package com.example.dmorenoar.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //Activar flecha ir atrás a la pantalla anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*INTENT EXPLICITO*/
        //Rescatar el activity que nos envia y el valor que recibimos y ponerlo en el textView

        //Enlazamos con el objeto visual y casteamos al tipo(nos devuelve un tipo view)
        TextView txtView = (TextView) findViewById(R.id.textViewMain);

        //Tomamos los datos del intent
        Bundle bundle = getIntent().getExtras(); //Bundle es una caja donde estan los datos que hemos mandado al aire

        if(bundle != null){
            String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_LONG).show();
            txtView.setText(greeter); //Asignamos el texto al textview

        }else{
            Toast.makeText(SecondActivity.this,"Esta vacio",Toast.LENGTH_LONG).show();
        }
        /*INTENT EXPLICITO*/




    }
}
