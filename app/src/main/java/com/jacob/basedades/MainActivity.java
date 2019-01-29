package com.jacob.basedades;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListAdapter adapter;
    public ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // En fer onClick a un element de la llista cridam l'activity d'edició i passam la clau primària
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String s = ((TextView) view.findViewById(R.id.id)).getText().toString();
                Intent in=new Intent(getApplicationContext(),EditActivity.class);
                in.putExtra("ID", s);
                startActivity(in);
            }
        });
        Button btNou=(Button) findViewById(R.id.nouBtn);
        btNou.setOnClickListener(
                // Cridam l'activity d'edició indicant que es un insert (clau primària en blanc per exemple)
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getApplicationContext(),EditActivity.class);
                        in.putExtra("ID", "0");
                        startActivity(in);
                    }
                }
        );
        mostraVins(); // Carrega la llista
    }

    @Override
    public void onResume() {
        super.onResume();
        mostraVins();
    }

    public void mostraVins() {
        // Obrim la base de dades
        DataSourceVi bd;
        bd = new DataSourceVi(this);
        bd.open();
        // Obtenim tots els vins
        List<Vi> llistaVins = bd.getAllVi();
        ArrayList<HashMap<String, String>> llista = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < llistaVins.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            Vi vi = llistaVins.get(i);
            map.put("id", String.valueOf(vi.getId()));
            map.put("nomVi", vi.getNomVi());
            map.put("anada", vi.getAnada());
            map.put("data", vi.getData());
            map.put("tipus", vi.getTipus());
            llista.add(map);
        }
        //Tanquem la BD
        bd.close();
        //Assignar a la listview
        adapter = new SimpleAdapter(this, llista,R.layout.llistavins,
                new String[]{"id","nomVi", "anada", "data", "tipus"},
                new int[]{R.id.id,R.id.nomVi, R.id.anada, R.id.data, R.id.tipus});
        lv.setAdapter(adapter);
    }
}
