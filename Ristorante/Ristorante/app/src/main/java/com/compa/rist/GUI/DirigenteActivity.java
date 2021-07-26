package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.Dirigente;
import com.compa.rist.R;
import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.query.ElencoDipendentiAsyncTask;
import com.compa.rist.query.GuadagnoTotaleAsyncTask;
import com.compa.rist.query.ResocontoPiattiServitiAsyncTask;

public class DirigenteActivity extends Activity {

    private Dirigente dirigente;

    private Button btnGestioneDipendenti;
    private Button btnResoconto;
    private Button btnFornitori;

    //ATTRIBUTI DirigenteDipendenti
    private TextView txtElencoDipendenti;

    private EditText edId;

    private Button btnAddDipendente, btnElimDipendente, btnAggiornaElencoDipendenti;

    //ATTRIBUTI DirigenteDipendentiAdd
    private EditText edIdDipendete, edNomeDipendete, edPasswordDipendete;

    private Spinner spnRoleTitolo;

    private Button btnAddDipendete, btnSvuotaCampiDipendete;

    private Integer idDipendente;

    //ATTRIBUTI DirigenteResoconto
    private TextView tvPiattiServiti, tvGuadagnoTotale;

    private Button btnAggiornaResoconto;

    //ATTRIBUTI DirigenteFornitori

    private TextView tvElencoFornitori;

    private EditText edNomeFornitore;
    private EditText edFornitori;

    private Button btnAddIngredienteFornitore;
    private Button btnElimIngredienteFornitore;
    private Button btnAggiornaIngredienteFornitori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirigente);

        dirigente = Dirigente.getInstance();

        btnGestioneDipendenti = (Button) findViewById(R.id.buttonGesioneD);
        btnResoconto = (Button) findViewById(R.id.buttonResoconto);
        btnFornitori = (Button) findViewById(R.id.buttonFornitori);

        btnGestioneDipendenti.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    cambioLayoutDipendenti();
                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnResoconto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    cambioLayoutResoconto();
                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnFornitori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    cambioLayoutDirigenteFornitori();
                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  METODI DirigenteDipendenti
     */

    public void cambioLayoutDipendenti() {

        //CAMBIA LAYOUT => DirigenteDipendenti
        cambioLayoutDipendenti();
        setContentView(R.layout.activity_dirigente_dipendenti);

        txtElencoDipendenti = (TextView) findViewById(R.id.textViewElencoDipendenti);
        edId = (EditText) findViewById(R.id.editTextIdDipendente);
        btnAddDipendente = (Button) findViewById(R.id.buttonAggiungiDipendente);
        btnElimDipendente = (Button) findViewById(R.id.buttonElimDipendente);
        btnAggiornaElencoDipendenti = (Button) findViewById(R.id.buttonAggiornaElencoDipendenti);

        btnAddDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AGGIUNGI DIPENDENTE
                cambioLayoutDipendentiAdd();
            }
        });

        btnElimDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ELIMINA DIPENDENTE

                if(dirigente.ElimDipendente(Integer.parseInt(edId.getText().toString()))){
                    Toast.makeText(DirigenteActivity.this,
                            "ElimDipendente", Toast.LENGTH_LONG).show();
                    txtElencoDipendenti.setText(dirigente.popolaElencoDipendenti());
                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Error: ElimDipendente", Toast.LENGTH_LONG).show();
                }

            }
        });


        //Meglio mettere un bottone
        //popolaElencoDipendenti();
        btnAggiornaElencoDipendenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtElencoDipendenti.setText(dirigente.popolaElencoDipendenti());
            }
        });


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  METODI: DirigenteDipendentiAddActivity
     */

    public void cambioLayoutDipendentiAdd() {
        setContentView(R.layout.activity_dirigente_dipendenti_add);

        idDipendente = Integer.parseInt(edId.getText().toString());

        edIdDipendete = (EditText) findViewById(R.id.editTextIdDipendete1);
        edNomeDipendete = (EditText) findViewById(R.id.editTextNomeDipendete);
        edPasswordDipendete = (EditText) findViewById(R.id.editTextPasswordDipendete);

        spnRoleTitolo = (Spinner) findViewById(R.id.spinnerRoleTitolo);

        btnAddDipendete = (Button) findViewById(R.id.buttonAddDipendete);
        btnSvuotaCampiDipendete = (Button) findViewById(R.id.buttonSvuotaCampiDipendete);

        edIdDipendete.setText(idDipendente);

        btnAddDipendete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AddDipendente( in IDdipendente: integer, in nome: string, in password: string, in titolo: string)
                if(dirigente.AddDipendente(Integer.parseInt(edIdDipendete.getText().toString()),
                        edNomeDipendete.getText().toString(),
                        edPasswordDipendete.getText().toString(),
                        spnRoleTitolo.getSelectedItem().toString())){

                }
            }
        });

        btnSvuotaCampiDipendete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String STRING_EMPTY = "";

                edIdDipendete.setText(STRING_EMPTY);
                edNomeDipendete.setText(STRING_EMPTY);
                edPasswordDipendete.setText(STRING_EMPTY);
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  METODI: DirigenteResocontoActivity
     */

    public void cambioLayoutResoconto() {
        setContentView(R.layout.activity_dirigente_resoconto);

        tvPiattiServiti = (TextView) findViewById(R.id.textViewPiattiServiti);
        tvGuadagnoTotale = (TextView) findViewById(R.id.textViewGuadagnoTotale);

        btnAggiornaResoconto = (Button) findViewById(R.id.buttonAggiornaResoconto);

        btnAggiornaResoconto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resocontoPiattiServiti()
                tvPiattiServiti.setText(dirigente.resocontoPiattiServiti());

                //guadagnoTotale()
                tvGuadagnoTotale.setText(dirigente.guadagnoTotale());

            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  METODI: DirigenteFornitori
     */

    public void  cambioLayoutDirigenteFornitori(){
        setContentView(R.layout.activity_dirigente_fornitori);

        tvElencoFornitori = (TextView) findViewById(R.id.textViewElencoFornitori);

        edFornitori = (EditText) findViewById(R.id.editTextFornitore);
        edNomeFornitore = (EditText) findViewById(R.id.editTextNomeFornitore);

        btnAddIngredienteFornitore = (Button) findViewById(R.id.buttonAddFornitore);
        btnElimIngredienteFornitore = (Button) findViewById(R.id.buttonElimFornitore);
        btnAggiornaIngredienteFornitori = (Button) findViewById(R.id.buttonAggiornaIngtredientiFornitore);

        btnAddIngredienteFornitore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    Integer idFornitore = Integer.parseInt(edFornitori.getText().toString());
                    String nomeFornitore = edNomeFornitore.getText().toString();

                    if(dirigente.AddFornitore(idFornitore, nomeFornitore)){
                        Toast.makeText(DirigenteActivity.this,
                                "OK: AddFornitore",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DirigenteActivity.this,
                                "ERROR: AddFornitore",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnElimIngredienteFornitore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    Integer idFornitore = Integer.parseInt(edFornitori.getText().toString());

                    if(dirigente.ElimFornitore(idFornitore)){
                        Toast.makeText(DirigenteActivity.this,
                                "OK: AddFornitore",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DirigenteActivity.this,
                                "ERROR: AddFornitore",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiornaIngredienteFornitori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    tvElencoFornitori.setText(dirigente.aggiornaFornitore());

                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
