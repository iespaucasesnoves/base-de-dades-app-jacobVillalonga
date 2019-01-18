package com.jacob.basedades;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    DataSourceVi bd = new DataSourceVi(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button button = (Button) findViewById(R.id.button);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("ID");
        {
            TextView idVi = findViewById(R.id.idVi);
            if (id != ""){
                idVi.setText(id);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //agafam les dades de l'editActivity
                String nomVi = ((EditText) findViewById(R.id.editText)).getText().toString();
                String anada = ((EditText) findViewById(R.id.editText2)).getText().toString();
                String lloc = ((EditText) findViewById(R.id.editText3)).getText().toString();
                String data = ((EditText) findViewById(R.id.editText4)).getText().toString();
                String graduacio = ((EditText) findViewById(R.id.editText5)).getText().toString();
                double preu = Double.parseDouble(((EditText) findViewById(R.id.editText6)).getText().toString());
                String comentari = ((EditText) findViewById(R.id.editText7)).getText().toString();
                String tipus = "Tinto";
                //String tipus = ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString();
                long idBodega = 1;
                long idDenominacio = 1;
                String valOlfativa = "Olor";
                String valGustativa = "Sabor";
                String valVisual = "Color";
                int nota = 1;
                String foto = "noFoto";

                //cream un objecte Vi amb les dades
                Vi nouVi = new Vi();
                nouVi.setAnada(anada);
                nouVi.setNomVi(nomVi);
                nouVi.setLloc(lloc);
                nouVi.setData(data);
                nouVi.setGraduacio(graduacio);
                nouVi.setPreu(preu);
                nouVi.setComentari(comentari);
                nouVi.setTipus(tipus);
                nouVi.setIdBodega(idBodega);
                nouVi.setIdDenominacio(idDenominacio);
                nouVi.setValGustativa(valGustativa);
                nouVi.setValOlfativa(valOlfativa);
                nouVi.setValVisual(valVisual);
                nouVi.setNota(nota);
                nouVi.setFoto(foto);
                //...

                bd.open();
                bd.createVi(nouVi);
                bd.close();
            }
        });
    }

}
