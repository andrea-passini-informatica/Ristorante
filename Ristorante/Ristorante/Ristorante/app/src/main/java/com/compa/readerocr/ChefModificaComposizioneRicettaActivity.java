package com.compa.rist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChefModificaComposizioneRicettaActivity extends Activity {

    private TextView tvRicetta, tvIngredienti;

    private EditText edIdIng, edQuantitàIng;

    private Button btnAddIngredienteRic, btnEliminaIngredienteRic;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private String ingredienti;

    private Integer idRicetta;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_modifica_composizione_ricetta);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "chef";
        idRicetta = b.getInt("idRicetta");

        tvRicetta = (TextView) findViewById(R.id.textViewIdRicetta);
        tvIngredienti = (TextView) findViewById(R.id.textViewIngredienti);
        edIdIng = (EditText) findViewById(R.id.editTextIdIng);
        edQuantitàIng = (EditText) findViewById(R.id.editTextQuantitàIng);
        btnAddIngredienteRic = (Button) findViewById(R.id.buttonAddIngredienteRic);
        btnEliminaIngredienteRic = (Button) findViewById(R.id.buttonEliminaIngredienteRic);

        //Mostra ingredienti della ricetta
        popolaIngredientiRicetta();

        //BUOTTONE ADD ed ELIM
        btnAddIngredienteRic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String STRING_EMPTY = "";

                    if(!STRING_EMPTY.equals(edIdIng.getText().toString()) &&
                            !STRING_EMPTY.equals(edQuantitàIng.getText().toString())){

                        new AddIngredientiRicettaAsyncTask().execute(idRicetta.toString(), edIdIng.toString(), edQuantitàIng.toString());

                    } else {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Completa i campi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
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

                    if(!STRING_EMPTY.equals(edIdIng.getText().toString())){

                        new ElimIngredientiRicettaAsyncTask().execute(idRicetta.toString(), edIdIng.toString());

                    } else {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Completa i campi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void popolaIngredientiRicetta() {
        //Controlla che la connesione web
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            new ElencoIngredientiRicettaAsyncTask().execute(idRicetta.toString());

        } else {
            Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                    "Impossibile connettersi ad internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * AsyncTask for ElencoIngredientiRicetta
     */
    private class ElencoIngredientiRicettaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elencoIngredientiRicetta.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                ingredienti = jsonObject.getString("ingredienti");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        tvIngredienti.setText(ingredienti);
                    } else {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Errore ELENCO", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for AddIngredientiRicetta
     */
    private class AddIngredientiRicettaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];
            String idIng = params[1];
            String quantitàIng = params[2];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("idIng", idIng);
            httpParams.put("quantitàIng", quantitàIng);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addIngredientiRicetta.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Ingrediente AGGIUNTO alla RICETTA", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Errore ADD", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ElimIngredientiRicetta
     */
    private class ElimIngredientiRicettaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Recupera parametri da quelli passati
            String idRicetta = params[0];
            String idIng = params[1];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("idIng", idIng);


            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elimIngredientiRicetta.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Ingrediente ELIMINATO dalla RICETTA", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefModificaComposizioneRicettaActivity.this,
                                "Errore ELIM", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
