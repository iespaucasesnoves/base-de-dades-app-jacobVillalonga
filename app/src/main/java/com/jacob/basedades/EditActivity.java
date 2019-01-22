package com.jacob.basedades;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
        Button button2 = (Button) findViewById(R.id.button2);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("ID");
        {
            TextView idVi = findViewById(R.id.idVi);
            if (id != ""){
                idVi.setText(id);
                bd.open();
                Vi vi = bd.getVi(Integer.parseInt(id));
                bd.close();
                emplenaDades(vi);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = ((TextView)findViewById(R.id.idVi)).getText().toString();
                if (id != ""){
                    updateVi();
                } else {
                    insertVi();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = ((TextView)findViewById(R.id.idVi)).getText().toString();
                if (id != "") {
                    deleteVi();
                }
            }
        });
    }

    public void emplenaDades(Vi vi){
        ((EditText) findViewById(R.id.editText)).setText(vi.getNomVi());
        ((EditText) findViewById(R.id.editText2)).setText(vi.getAnada());
        ((EditText) findViewById(R.id.editText3)).setText(vi.getLloc());
        ((EditText) findViewById(R.id.editText4)).setText(vi.getData());
        ((EditText) findViewById(R.id.editText5)).setText(vi.getGraduacio());
        ((EditText) findViewById(R.id.editText6)).setText(Double.toString(vi.getPreu()));
        ((EditText) findViewById(R.id.editText7)).setText(vi.getComentari());
        String tipus = "Tinto";
        //String tipus = ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString();
        long idBodega = 1;
        long idDenominacio = 1;
        String valOlfativa = "Olor";
        String valGustativa = "Sabor";
        String valVisual = "Color";
        int nota = 1;
        String foto = "noFoto";
    }

    public void insertVi(){
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
        //agafa el codi del nou vi insertat
        long codiVi = nouVi.getId();
    }

    public void updateVi(){
        //agafam les dades de l'editActivity
        long id = Integer.parseInt(((TextView)findViewById(R.id.idVi)).getText().toString());
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
        nouVi.setId(id);
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
        bd.updateVi(nouVi);
        bd.close();
    }

    public void deleteVi(){
        //agafam les dades de l'editActivity
        long id = Integer.parseInt(((TextView)findViewById(R.id.idVi)).getText().toString());

        //cream un objecte Vi amb la id
        Vi nouVi = new Vi();
        nouVi.setId(id);

        bd.open();
        bd.deleteVi(nouVi);
        bd.close();
    }
}
