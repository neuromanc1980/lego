package com.example.xavib.lego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button search;
    protected EditText codi_input;
    protected ListView llista;
    protected TextView llista2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llista = (ListView) findViewById(R.id.llista);

        //final String codi = findViewById(R.id.codi).toString();

        search = (Button) findViewById(R.id.buscar);
        search.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {

            //antiga urlrw          ex codi    60128-1
            //String url = "https://rebrickable.com/api/get_set_parts?key=Drx9VlnnCV&format=tsv&set="+codi;

                codi_input = (EditText)  findViewById(R.id.codi);

                String codi = codi_input.getText().toString();

            String url = "http://stucom.flx.cat/lego/get_set_parts.php?key=Drx9VlnnCV&set="+codi;


                //Log.d("xavi", "el codi: "+codi);

            PartDownloader test = new PartDownloader(MainActivity.this, llista);
            test.execute(url);
                Log.d("xavi", url);

                EditText caixeta = (EditText)findViewById(R.id.codi);
                caixeta.getText().clear();
            }

        });

        //TextView codi = (TextView) findViewById(R.id.codi);
        //la llistaaaa de la muette

        ListView llista5 = (ListView) this.findViewById(R.id.llista);
    }

    @Override
    public void onClick(View v) {
    }
    //exemple format "https://rebrickable.com/api/get_set_parts?key=Drx9VlnnCV&format=tsv&set=60128-1";


}
