package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.Cameriere;
import com.compa.rist.R;
import com.compa.rist.helper.CheckNetworkStatus;

public class CameriereActivity extends Activity {

    private Cameriere cameriere;

    /**
     * ATTRIBUTI ACT.CAMERIERE
     */
    private EditText txtNumTavolo;

    private Button btnNuovaOrdinazione;

    private Integer numeroTavolo, idOrdinazione;


    /**
     * ATTRIBUTI ACT.ORDINAZIONE
     */
    private TextView txtMenù;

    private EditText txtPiatto, txtQuantitàPiatto;

    private Button btnAddPiatto, btnAggiorna;

    private Integer idPiatto, quantitàPiatto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameriere);

        cameriere = Cameriere.getInstance();

        txtNumTavolo = (EditText) findViewById(R.id.textNumeroTavolo);

        btnNuovaOrdinazione = (Button) findViewById(R.id.buttonNuovaOrdinazione);

        btnNuovaOrdinazione.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    idOrdinazione = cameriere.CreaNuovaOrdinazione(Integer.parseInt(txtNumTavolo.getText().toString()));

                    if (idPiatto != null) {
                        //USARE IL CAMBIO LAYOUT
                        setContentView(R.layout.activity_cameriere_ordinazione);


                        txtPiatto = (EditText) findViewById(R.id.textPiatto);
                        txtQuantitàPiatto = (EditText) findViewById(R.id.textQuantitàPiatto);

                        btnAddPiatto = (Button) findViewById(R.id.buttonAddPiatto);

                        txtMenù = (TextView) findViewById(R.id.textMenù);

                        btnAggiorna = (Button) findViewById(R.id.buttonAggiorna);

                        //AGGIUNTA PIATTO
                        btnAddPiatto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                idPiatto = Integer.parseInt(txtPiatto.getText().toString());
                                quantitàPiatto = Integer.parseInt(txtQuantitàPiatto.getText().toString());
                                numeroTavolo = Integer.parseInt(txtNumTavolo.getText().toString());

                                //aggiungi piatto ordinazione
                                if(cameriere.AddPiattoOrdinazione(idPiatto, quantitàPiatto, idOrdinazione, numeroTavolo)){
                                    Toast.makeText(CameriereActivity.this,
                                            "OK: AddPiattoOrdinazione", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(CameriereActivity.this,
                                            "Error: AddPiattoOrdinazione", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        btnAggiorna.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Visualizza menù
                                txtMenù.setText(cameriere.ElencoMenù());
                            }
                        });
                    }
                } else {
                    Toast.makeText(CameriereActivity.this,
                            "Impossibile connettersi ad internet", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
