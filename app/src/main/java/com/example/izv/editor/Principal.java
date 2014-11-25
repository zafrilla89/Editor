package com.example.izv.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Principal extends Activity {

    private String nombre;
    private EditText ettexto;
    private String ruta;
    private File f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        ettexto=(EditText)findViewById(R.id.ettexto);
        Uri data = getIntent().getData();
        if(data!=null){
            nombre = data.toString();
            ruta = nombre.substring(7,nombre.length());
            ruta=ruta.replaceAll("%20"," ");
            f = new File(ruta);
            leerarchivo();

        }
    }



    public void leerarchivo(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            StringBuilder texto = new StringBuilder("");
            while ((linea = br.readLine()) != null) {
                texto.append(linea);
                texto.append('\n');
            }
            br.close();
            ettexto.setText(texto);
        }catch (IOException e){
            Log.v("ARCHIVO", "exepcion" + e.toString());

        }
    }

    public void guardar (View view){
        String texto= ettexto.getText().toString();
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(texto);
            fw.flush();
            fw.close();
            Toast.makeText(this,"Guardados los cambios del archivo", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrar (View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Estas seguro que desea eliminar el archivo?");
        alertDialog.setTitle("Eliminar archivo");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                f.delete();
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        alertDialog.show();
    }

}
