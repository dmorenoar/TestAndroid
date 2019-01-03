package com.example.dmorenoar.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnCamera;
    private ImageButton imgBtnWeb;

    private final int PHONE_CALL_CODE = 100; //Lo creo para pasarselo al permiso del teléfono
    private final int PICTURE_FROM_CAMERA = 110; //Lo creamos apra saber cuando el codigo se ha realizado de forma asincrona


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);


        //Boton para la llamada
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();

                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    //Comprobar version actual de android que estamos utilizando
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //Nos deja hacerlo porque sabe que estamos comprobando la versión pero en un metodo fuera no dejaria

                        //COMPROBAR SI HA ACEPTADO, NO HA ACEPTADO O NUNCA SE LE HA PREGUNTADO

                        if(checkPermission(Manifest.permission.CALL_PHONE)){
                            //Ha aceptado.
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            startActivity(i);

                        }else{
                            //0 o no ha aceptado, o es la primera vez que se le pregunta
                           if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                               //No se le ha preguntado aun
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                           }else{
                                //Ha denegado
                               Toast.makeText(ThirdActivity.this, "Por favor, permita el uso del tel", Toast.LENGTH_LONG);
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS); //Creamos un settings
                                i.addCategory(Intent.CATEGORY_DEFAULT); //Le asignamos el tipo de categoria
                                i.setData(Uri.parse("package:" + getPackageName())); // Le pasamos la infoirmacion de nuestro programa con el getPackage
                                //Creamos unas BANDERAS
                               /*Nos sirven para saber donde estamos, si doy a una pagina voy hacia adelanta y vuelvo atras sabe volver hacia atras
                               * Al hacer login entro en la pagina principal, y al volver atras la aplicacion hace lo que le diga, si vuelvo atras
                               * cierro la aplicacion, no tiene sentido logear il al home y darle atras y volver al login. No deberia hacerse
                               * para el flujo de navegacion. Los flags nos sacan del cache de navegacion para evitar errores
                               * */
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                           }

                        }



                        //requestPermissions(new String[]{ Manifest.permission.CALL_PHONE }, PHONE_CALL_CODE );
                    }else{
                        olderVersions(phoneNumber);
                    }
                }
            }

            private void olderVersions(String phoneNumber){
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

                if(checkPermission(Manifest.permission.CALL_PHONE)){
                    //sI TENEMOS ESTE PERMISO DECLARADO LANZAMOS LA ACTIVIDO
                    startActivity(intentCall);
                }else{
                    Toast.makeText(ThirdActivity.this,"No has declarado el acceso", Toast.LENGTH_LONG).show();
                }

            }


        });


        //boton para el navegador web y email
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();

                if(url != null && !url.isEmpty()){

                    //Para abrir un navegador web
                   //Opcion 1
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                    //Opcion 2
                    /*Intent intentWeb2 = new Intent();
                    intentWeb2.setAction(Intent.ACTION_VIEW);
                    intentWeb2.setData(Uri.parse("http://" + url));
                    */

                    //Para abrir contactos
                    Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));

                    //Para abrir email rapido
                    String email = "dmorenoar@gmail.com";
                    Intent emailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mail" + email));

                    //Para email completo
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                    //Solucion al error de la activity to handle
                    //intentMail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mails title"); //Titulo del mail
                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hola este es el mensaje"); //Cuerpo del mensaje
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[] {"fernando@gmail.com", "aa@gmail.com"}); //CC del correo


                    //Opcion para elegir que opcion enviar en lugar de que sea con gmail y que el elija que tipo quiere
                    //intentMail.setType("message/rfc822");
                    startActivity(Intent.createChooser(intentMail, "Elige cliente de email"));



                    //Telefono 2
                    Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:615226214"));

                    //startActivity(intentPhone);
                }



            }
        });


        imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(Intent.ACTION_CAMERA_BUTTON);
                startActivityForResult(intentCamera, PICTURE_FROM_CAMERA); //He de pasar el intent y el testigo al activityForResutl
                //El startActivity por Result espera un resultado porque no sabemos cuando va a acbar la camara
                //se ejecuta de forma asincrona
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){

            case 110:
                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(ThirdActivity.this, "Todo Ok:" + result, Toast.LENGTH_LONG).show();
                }else{
                    System.out.println("ALGO HA PASADO");
                    Toast.makeText(ThirdActivity.this, "errorr", Toast.LENGTH_LONG).show();
                }


                break;

                default:
                    System.out.println("Sin caso");
                    super.onActivityResult(requestCode, resultCode, data);
        }


    }

    /*Se llamará al pedir una petición de servicio*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //En el caso del telefono
        switch (requestCode){
            case PHONE_CALL_CODE:
                    //Revisamos si ha estado permitido o no el permiso
                    String permission = permissions[0]; //Llega solo uno en el array que hemos enviado en el requestPermision
                    int result = grantResults[0];

                    //rEVISAMOS QUE EL PERMISO SEA EL DEL TELEFONO
                    if (permission.equals(Manifest.permission.CALL_PHONE)){

                        //Comprobar si el usuario ha aceptado o denegado la peticion
                        if (result == PackageManager.PERMISSION_GRANTED){
                            //Concedio su permiso
                            String phoneNumber = editTextPhone.getText().toString();
                            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            startActivity(intentCall);
                        }else{
                            //No concedio su servicio
                            Toast.makeText(ThirdActivity.this, "No acces granted", Toast.LENGTH_LONG).show();
                        }

                    }

                break;
                default:
                    System.out.println("Sin codigo");
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    /*Calculo el permiso para ver si esta activado o no*/
    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);

        return result == PackageManager.PERMISSION_GRANTED ? true : false;

    }

}
