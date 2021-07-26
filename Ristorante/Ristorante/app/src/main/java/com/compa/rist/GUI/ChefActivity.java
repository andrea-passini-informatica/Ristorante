package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.Chef;
import com.compa.rist.R;
import com.compa.rist.helper.CheckNetworkStatus;

public class ChefActivity extends Activity {

    private Chef chef;

    private Button btnIngredienti, btnRicette, btnMenù;


    //ATTRIBUTI INGREDIENTI
    private EditText etIdIndrediente, etNomeIngrediente, etUnita, etIdFornitore;

    private Button btnAggiungiIngrediente, btnModificaIngrediente, btnEliminaIngrediente;


    //ATTRIBUTI MENU
    private TextView tvMenù, tvRicette;

    private EditText edIdRicetta;

    private Button btnAddRicettaMenù, btnElimRicettaMenù, btnAggiornaMenuRicette;


    //ATTRIBUTI RICETTE
    private EditText etIdRicetta, etNomeRicetta, etPreprazioneRicetta, etPrezzoRicetta, etPortata;

    private TextView tvElencoRicette;

    private Button btnAddRicetta, btnModRicetta, btnElimRicetta, btnAggiornaRicette;


    //ATTRIBUTI COMPOSIZIONE RICETTA
    private TextView tvIdRicetta, tvIngredienti;

    private EditText edIdIngrediente, edQuantitàIngrediente;

    private Button btnAddIngredienteRicetta, btnAggiornaComposizioneRicetta;

    private Integer idRicetta;


    //ATTRIBUTI ModificaComposizioneRicetta
    private TextView tvRicetta, tvIngredientiRicetta;

    private EditText edIdIng, edQuantitàIng;

    private Button btnAddIngredienteRic, btnEliminaIngredienteRic, btnAggiornaModificaComposizioneRicetta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        chef = Chef.getInstance();

        btnIngredienti = (Button) findViewById(R.id.buttonIngredienti);
        btnRicette = (Button) findViewById(R.id.buttonRicette);
        btnMenù = (Button) findViewById(R.id.buttonMenù);

        btnIngredienti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredienti();
            }
        });

        btnRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ricette();
            }
        });

        btnMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menù();
            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //OK

    /**
     * METODI INGREDIENTI
     */

    public void Ingredienti() {

        //Cambio LAYOUT
        setContentView(R.layout.activity_chef_ingredienti);

        etIdIndrediente = (EditText) findViewById(R.id.editTextIdIngrediente);
        etNomeIngrediente = (EditText) findViewById(R.id.editTextNome);
        etUnita = (EditText) findViewById(R.id.editTextUDM);
        etIdFornitore = (EditText) findViewById(R.id.editTextIdFornitore);

        btnAggiungiIngrediente = (Button) findViewById(R.id.buttonAddIngrediente);
        btnModificaIngrediente = (Button) findViewById(R.id.buttonModificaIngrediente);
        btnEliminaIngrediente = (Button) findViewById(R.id.buttonEliminaIngrediente);

        //AddIngrediente( in IDingrediente: integer, in nome: string, in UDM: string, in IDfornitore: integer)
        btnAggiungiIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    if (chef.AddIngrediente(Integer.parseInt(etIdIndrediente.getText().toString()),
                            etNomeIngrediente.getText().toString(),
                            etUnita.getText().toString(),
                            Integer.parseInt(etIdFornitore.getText().toString()))) {

                        Toast.makeText(ChefActivity.this,
                                "AddIngrediente",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: AddIngrediente",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnModificaIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare nel diagramma CLASSI (tornare boolean)
                    if (chef.ModIngrediente(
                            Integer.parseInt(etIdIndrediente.getText().toString()),
                            etNomeIngrediente.getText().toString(),
                            etUnita.getText().toString(),
                            Integer.parseInt(etIdFornitore.getText().toString()))) {

                        Toast.makeText(ChefActivity.this,
                                "ModIngrediente",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: ModIngrediente",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEliminaIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare nel diagramma CLASSI (tornare boolean)
                    //Sistemare nel diagramma CLASSI (tornare boolean)
                    if (chef.ElimIngrediente(Integer.parseInt(etIdIndrediente.getText().toString()))) {

                        Toast.makeText(ChefActivity.this,
                                "ModIngrediente",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: ModIngrediente",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //OK

    /**
     * METODI MENU'
     */

    public void Menù() {

        //Cambio LAYOUT
        setContentView(R.layout.activity_chef_menu);

        tvMenù = (TextView) findViewById(R.id.textViewMenù);
        tvRicette = (TextView) findViewById(R.id.textViewRicette);

        edIdRicetta = (EditText) findViewById(R.id.editTextIdRicette);

        btnAddRicettaMenù = (Button) findViewById(R.id.buttonAddRicettaMenù);
        btnElimRicettaMenù = (Button) findViewById(R.id.buttonElimRIcettaMenù);
        btnAggiornaMenuRicette = (Button) findViewById(R.id.buttonAggiornaMenuRicette);

        btnAddRicettaMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare nel diagramma classi (tornare boolean)
                    if (chef.AddRicettaMenù(Integer.parseInt(edIdRicetta.getText().toString()))) {
                        Toast.makeText(ChefActivity.this,
                                "AddRicettaMenù",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: AddRicettaMenù",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnElimRicettaMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare nel diagramma classi (tornare boolean)
                    if (chef.ElimRicettaMenù(Integer.parseInt(edIdRicetta.getText().toString()))) {
                        Toast.makeText(ChefActivity.this,
                                "AddRicettaMenù",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: AddRicettaMenù",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiornaMenuRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //MEGLIO METTERE in un BUTTON
                    //Popola Menu
                    tvMenù.setText(chef.PopolaMenu());

                    //Popola Ricette ( tutte le ricette che sono state registrate
                    tvRicette.setText(chef.PopolaIdRicette());

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //OK

    /**
     * METODI RICETTE
     */

    public void Ricette() {

        //Cambio LAYOUT
        setContentView(R.layout.activity_chef_ricette);

        etIdRicetta = (EditText) findViewById(R.id.editTextIdRicetta);
        etNomeRicetta = (EditText) findViewById(R.id.editTextNomeRicetta);
        etPreprazioneRicetta = (EditText) findViewById(R.id.editTextPreparazioneRicetta);
        etPrezzoRicetta = (EditText) findViewById(R.id.editTextPrezzoRicetta);
        etPortata = (EditText) findViewById(R.id.editTextPortata);

        tvElencoRicette = (TextView) findViewById(R.id.textViewElencoRicette);

        btnAggiornaRicette = (Button) findViewById(R.id.buttonAggiornaRicette);

        btnAddRicetta = (Button) findViewById(R.id.buttonAddRicetta);
        btnModRicetta = (Button) findViewById(R.id.buttonModRicetta);
        btnElimRicetta = (Button) findViewById(R.id.buttonElimRicetta);

        //Aggiungi Ricetta e poi vai alla composizione di essa
        btnAddRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    if (chef.AddRicetta(Integer.parseInt(etIdRicetta.getText().toString()),
                            etNomeRicetta.getText().toString(),
                            etPreprazioneRicetta.getText().toString(),
                            Integer.parseInt(etPrezzoRicetta.getText().toString()),
                            Integer.parseInt(etPortata.getText().toString()))) {

                        //cambio layout ->> Composizione ricetta
                        cambioLayoutComposizioneRicetta();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: AddRicetta",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnModRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    idRicetta = Integer.parseInt(etIdRicetta.getText().toString());

                    if(chef.ModRicetta(idRicetta,
                            etNomeRicetta.getText().toString(),
                            etPreprazioneRicetta.getText().toString(),
                            Integer.parseInt(etPrezzoRicetta.getText().toString()),
                            Integer.parseInt(etPortata.getText().toString()))){

                        //CAMBIO LAYOUT ChefModificaComposizioneRicetta
                        cambioLayoutModificaComposizioneRicetta();

                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: ModRicetta",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnElimRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare nel Diagramma Classi (tornare boolean)
                    if(chef.ElimRicetta(Integer.parseInt(etIdRicetta.getText().toString()))){
                        Toast.makeText(ChefActivity.this,
                                "ElimRicetta",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: ElimRicetta",
                                Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiornaRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    tvElencoRicette.setText(chef.PopolaIdRicette());

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //OK

    /**
     * ChefComposizioneRicetta
     */

    //COMPOSIZIONE RICETTA quando la sto componendo per la prima volta
    public void cambioLayoutComposizioneRicetta() {
        //CAMBIO LAYOUT
        setContentView(R.layout.activity_chef_composizione_ricetta);

        //LAYOUT
        tvIdRicetta = (TextView) findViewById(R.id.textViewIdRicetta);
        tvIngredienti = (TextView) findViewById(R.id.textViewIdIngredienti);

        edIdIngrediente = (EditText) findViewById(R.id.editTextIdIng);
        edQuantitàIngrediente = (EditText) findViewById(R.id.editTextQuantitàIng);

        btnAddIngredienteRicetta = (Button) findViewById(R.id.buttonAddIngredienteRicetta);
        btnAggiornaComposizioneRicetta = (Button) findViewById(R.id.buttonAggiornaComposizioneRicetta);


        //LAYOUT: ID Ricetta
        tvIdRicetta.setText("ID RICETTA: " + idRicetta);

        //ADD INGREDIENTE ALLA RICETTA
        btnAddIngredienteRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Sistemare in Diagrama Classi tornare boolean
                    if(chef.AddIngredienteRicetta(idRicetta,
                            Integer.parseInt(edIdIngrediente.getText().toString()),
                            Integer.parseInt(edQuantitàIngrediente.getText().toString()))) {

                        Toast.makeText(ChefActivity.this,
                                "AddIngredienteRicetta",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Error: AddIngredienteRicetta",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiornaComposizioneRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LAYOUT: Tutti gli Ingredienti
                //MEGLIO METTERE BOTTONE
                tvIngredienti.setText(chef.Ingredienti());
            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ChefModificaComposizioneRicetta
     */

    public void cambioLayoutModificaComposizioneRicetta(){
        //CAMBIO LAYOUT ChefModificaComposizioneRicetta
        setContentView(R.layout.activity_chef_modifica_composizione_ricetta);

        tvRicetta = (TextView) findViewById(R.id.textViewIdRicetta);
        tvIngredientiRicetta = (TextView) findViewById(R.id.textViewIngredienti);

        edIdIng = (EditText) findViewById(R.id.editTextIdIng);
        edQuantitàIng = (EditText) findViewById(R.id.editTextQuantitàIng);

        btnAddIngredienteRic = (Button) findViewById(R.id.buttonAddIngredienteRic);
        btnEliminaIngredienteRic = (Button) findViewById(R.id.buttonEliminaIngredienteRic);

        btnAggiornaModificaComposizioneRicetta = (Button) findViewById(R.id.buttonAggiornaChefModificaComposizioneRicetta);

        //BUOTTONE ADD ed ELIM
        btnAddIngredienteRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione internet
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String STRING_EMPTY = "";

                    if (!STRING_EMPTY.equals(edIdIng.getText().toString()) &&
                            !STRING_EMPTY.equals(edQuantitàIng.getText().toString())) {
                        //Sistemare in Diagrama Classi tornare boolean
                        if(chef.AddIngredienteRicetta(idRicetta,
                                Integer.parseInt(edIdIng.getText().toString()),
                                Integer.parseInt(edQuantitàIngrediente.getText().toString()))) {

                            Toast.makeText(ChefActivity.this,
                                    "AddIngredienteRicetta",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ChefActivity.this,
                                    "Error: AddIngredienteRicetta",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Completa i campi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAddIngredienteRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String STRING_EMPTY = "";

                    if (!STRING_EMPTY.equals(edIdIng.getText().toString())) {
                        if(chef.ElimIngredientiRicetta(idRicetta,
                                Integer.parseInt(edIdIng.toString()))){

                            Toast.makeText(ChefActivity.this,
                                    "ElimIngredientiRicetta", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ChefActivity.this,
                                    "Error: ElimIngredientiRicetta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChefActivity.this,
                                "Completa i campi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiornaModificaComposizioneRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controlla che la connesione web
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Mostra ingredienti della ricetta
                    //MEGLIO METTERE BOTTONE
                    tvIngredientiRicetta.setText(chef.popolaIngredientiRicetta(idRicetta));

                } else {
                    Toast.makeText(ChefActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
