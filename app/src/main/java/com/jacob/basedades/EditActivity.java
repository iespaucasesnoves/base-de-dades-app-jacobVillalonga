package com.jacob.basedades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

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
            if (!id.equals("0")){
                idVi.setText(id);
                bd.open();
                Vi vi = bd.getVi(Integer.parseInt(id));
                emplenaDades(vi);
                bd.close();
            }else{
                button2.setVisibility(View.INVISIBLE);
                idVi.setText(id);
                spinnerTipus("");
                montaAutocompleta("","");
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = ((TextView)findViewById(R.id.idVi)).getText().toString();
                if (!id.equals("0")){
                    updateVi();
                } else {
                    insertVi();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id = ((TextView)findViewById(R.id.idVi)).getText().toString();
                if (!id.equals("0")) {
                    deleteVi();
                }
            }
        });
    }

    private void spinnerTipus(String t) {
        Spinner spinnerTipus = (Spinner) findViewById(R.id.spinner3);
        DataSourceVi bd;
        bd = new DataSourceVi(this);
        bd.open();
        List<String> llista;
        llista=bd.getAllTipus();
        // Crear adapter
        ArrayAdapter<String> dataAdapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, llista);
        // Drop down estil – llista amb radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // assignar adapter
        spinnerTipus.setAdapter(dataAdapter);
        if (t!=null && !t.equals("")) {
            selectValue(spinnerTipus,t); // Si hi ha un valor assignat posicionar-se
        }
        bd.close();
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void emplenaDades(Vi vi){
        ((EditText) findViewById(R.id.editText)).setText(vi.getNomVi());
        ((EditText) findViewById(R.id.editText2)).setText(vi.getAnada());
        ((EditText) findViewById(R.id.editText3)).setText(vi.getLloc());
        ((EditText) findViewById(R.id.editText4)).setText(vi.getData());
        ((EditText) findViewById(R.id.editText5)).setText(vi.getGraduacio());
        ((EditText) findViewById(R.id.editText6)).setText(Double.toString(vi.getPreu()));
        ((EditText) findViewById(R.id.editText7)).setText(vi.getComentari());
        spinnerTipus(vi.getTipus());
        bd.open();
        montaAutocompleta(bd.getNomBodega(vi.getIdBodega()),bd.getNomDenominacio(vi.getIdDenominacio()));
        ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat(vi.getValGustativa()));
        ((RatingBar) findViewById(R.id.ratingBar2)).setRating(Float.parseFloat(vi.getValOlfativa()));
        ((RatingBar) findViewById(R.id.ratingBar3)).setRating(Float.parseFloat(vi.getValVisual()));
        int nota = 1;
        String foto = "noFoto";
    }
    private void montaAutocompleta(String b,String d){
        List<String> llistaB;
        List<String> llistaD;
        DataSourceVi bd;
        bd = new DataSourceVi(this);
        bd.open();

        llistaB=bd.getLlistaBodegues();
        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, llistaB);
        AutoCompleteTextView bodega = (AutoCompleteTextView) findViewById(R.id.acBodega);
        bodega.setThreshold(0);
        bodega.setAdapter(adapter);
        if (b!=null && !b.equals("")) {
            bodega.setText(b,true);
        }
        llistaD=bd.getLlistaDenominacions();
        ArrayAdapter<String> adapter2 = new
                ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, llistaD);
        AutoCompleteTextView denominacio = (AutoCompleteTextView) findViewById(R.id.acDenominacio);
        denominacio.setThreshold(0);
        denominacio.setAdapter(adapter2);
        if (d!=null && !d.equals("")) {
            denominacio.setText(d,true);
        }
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
        String tipus = ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString();
        long idDenominacio = 1;
        String valOlfativa = Float.toString(((RatingBar) findViewById(R.id.ratingBar)).getRating());
        String valGustativa = Float.toString(((RatingBar) findViewById(R.id.ratingBar2)).getRating());
        String valVisual = Float.toString(((RatingBar) findViewById(R.id.ratingBar3)).getRating());
        int nota = 1;
        String foto = "noFoto";

        //cream un objecte Vi amb les dades
        bd.open();
        AutoCompleteTextView bodega = (AutoCompleteTextView) findViewById(R.id.acBodega);
        AutoCompleteTextView denominacio = (AutoCompleteTextView) findViewById(R.id.acDenominacio);
        Vi nouVi = new Vi();
        nouVi.setAnada(anada);
        nouVi.setNomVi(nomVi);
        nouVi.setLloc(lloc);
        nouVi.setData(data);
        nouVi.setGraduacio(graduacio);
        nouVi.setPreu(preu);
        nouVi.setComentari(comentari);
        nouVi.setTipus(tipus);
        nouVi.setIdBodega(bd.findInsertBodegaPerNom(bodega.getText().toString()));
        nouVi.setIdDenominacio(bd.findInsertDenominacioPerNom(denominacio.getText().toString()));
        nouVi.setValGustativa(valGustativa);
        nouVi.setValOlfativa(valOlfativa);
        nouVi.setValVisual(valVisual);
        nouVi.setNota(nota);
        nouVi.setFoto(foto);

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
        String tipus = ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString();
        long idDenominacio = 1;
        String valOlfativa = Float.toString(((RatingBar) findViewById(R.id.ratingBar)).getRating());
        String valGustativa = Float.toString(((RatingBar) findViewById(R.id.ratingBar2)).getRating());
        String valVisual = Float.toString(((RatingBar) findViewById(R.id.ratingBar3)).getRating());
        int nota = 1;
        String foto = "noFoto";

        //cream un objecte Vi amb les dades
        Vi nouVi = new Vi();
        bd.open();
        AutoCompleteTextView bodega = (AutoCompleteTextView) findViewById(R.id.acBodega);
        AutoCompleteTextView denominacio = (AutoCompleteTextView) findViewById(R.id.acDenominacio);
        nouVi.setId(id);
        nouVi.setAnada(anada);
        nouVi.setNomVi(nomVi);
        nouVi.setLloc(lloc);
        nouVi.setData(data);
        nouVi.setGraduacio(graduacio);
        nouVi.setPreu(preu);
        nouVi.setComentari(comentari);
        nouVi.setTipus(tipus);
        nouVi.setIdBodega(bd.findInsertBodegaPerNom(bodega.getText().toString()));
        nouVi.setIdDenominacio(bd.findInsertDenominacioPerNom(denominacio.getText().toString()));
        nouVi.setValGustativa(valGustativa);
        nouVi.setValOlfativa(valOlfativa);
        nouVi.setValVisual(valVisual);
        nouVi.setNota(nota);
        nouVi.setFoto(foto);
        //...

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
