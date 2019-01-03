package com.example.dmorenoar.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Poner icono para la aplicacion o para el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myiconapp);




        Activity activity = this;
        Context context = this;

        Test t = new Test();

        t.myToast(context);

        SuperActivityToast.create(this, new Style(), Style.TYPE_BUTTON)
                .setButtonText("UNDO")
                .setOnButtonClickListener("good_tag_name", null, null)
                .setProgressBarColor(Color.WHITE)
                .setText("Email deleted")
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                .setAnimations(Style.ANIMATIONS_POP).show();


        //Buscamos en la view el elemento por su id casteamos a botón
        Button btn = (Button) findViewById(R.id.buttonMain);

        //Le añadimos el onclick
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miMetodo(v);

                /*INTENT EXPLICITO*/

                //Creamos el activity primero y el segundo
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //Le ponemos el identificador y que le enviamos estos extras permanecen en el Bundle
                intent.putExtra("greeter", new String("Hello all"));
                //Arrancamos la activity y la presentamos
                startActivity(intent);
                /*INTENT EXPLICITO*/

                
            }
        });


        //Para acceder de un activity al segundo y mandarle algo
        //Hemos de informar de donde estamos y a donde queremos ir.




    }

    public void miMetodo(View v){

        System.out.println("me clickan");
        //Llamar desde un metodo al activity que quiero.
        Toast.makeText(MainActivity.this,"META-INF", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("se pausa");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Se incia");
    }
}
