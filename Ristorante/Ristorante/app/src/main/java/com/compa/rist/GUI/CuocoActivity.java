package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.Cuoco;
import com.compa.rist.R;
import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.query.PopolaOrdinazioneAsyncTask;
import com.compa.rist.query.PopolaTutteAsyncTask;

public class CuocoActivity extends Activity {

    private Cuoco cuoco;

    private TextView txtOrdinazioni;

    private EditText edNumeroOrdinazione;

    private Button btnLeggiOrdinazione, btnAggiorna;

    //ATTRIBUTI CuocoOrdinazione

    private Button btnCucinaPiatto, btnAggiorna2;

    private EditText txtPiatto;

    private TextView txtvOrdinaizione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoco);

        cuoco = Cuoco.getInstance();

        txtOrdinazioni = (TextView) findViewById(R.id.textOrdinazioni);

        edNumeroOrdinazione = (EditText) findViewById(R.id.editNumeroOrdinazione);

        btnLeggiOrdinazione = (Button) findViewById(R.id.buttonLeggiOrdinazione);

        btnAggiorna = (Button) findViewById(R.id.buttonAgg);

        btnLeggiOrdinazione.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione web sai possibile
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    leggiOrdinazione(Integer.parseInt(edNumeroOrdinazione.getText().toString()));

                } else {
                    Toast.makeText(CuocoActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //Meglio integrarlo con un bottone di aggiornamento
        //ElencoOrdinazioni();
        btnAggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla che la connesione web sai possibile
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    txtOrdinazioni.setText(cuoco.ElencoOrdinazioni());

                } else {
                    Toast.makeText(CuocoActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    //Leggi tutti i piatti ordinati nell'ordinazione
    public void leggiOrdinazione(final Integer idOrdinazione) {
        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idOrdinazione.toString())) {
            setContentView(R.layout.activity_cuoco_ordinazionme);

            btnAggiorna2 = (Button) findViewById(R.id.buttonAgg2);
            btnCucinaPiatto = (Button) findViewById(R.id.buttonCucinaPiatto);

            txtvOrdinaizione = (TextView) findViewById(R.id.textViewOrdinazione);
            txtPiatto = (EditText) findViewById(R.id.editTextPiatto);

            //Ogni volta, cancello l'elemento dall'ordinazione
            btnCucinaPiatto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Controlla che la connesione web sai possibile e poi procede con ADD
                    if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                        String idPiatto = txtPiatto.getText().toString();

                        if (txtvOrdinaizione.getText().toString().contains(idPiatto)) {

                            cuoco.PiattoServito(Integer.parseInt(idPiatto), idOrdinazione);

                            txtvOrdinaizione.setText(cuoco.popolaOrdinazione(idOrdinazione));
                        }
                    } else {
                        Toast.makeText(CuocoActivity.this,
                                "Impossibile connettersi ad internet",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            //Meglio inserire un bottone
            btnAggiorna2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Meglio mettere un bottone
                    txtvOrdinaizione.setText(cuoco.popolaOrdinazione(idOrdinazione));
                }
            });
        } else {
            //Non è possibile procedere nel caso in cui manchi l'ID
            Toast.makeText(CuocoActivity.this,
                    "Completa i campi",
                    Toast.LENGTH_LONG).show();
        }
    }



}