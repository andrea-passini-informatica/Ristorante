package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
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

public class ChefComposizioneRicettaActivity extends Activity {

    private TextView tvIdRicetta, tvIdIngredienti, tvNomeIngredienti;

    private EditText edIdIngrediente;

    private Button btnAddIngredienteRicetta;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private Integer ingredienti;
    private String nomeIngredienti;

    private Integer idRicetta;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_composizione_ricetta);

        //LAYOUT: Tutti gli Ingredienti
        Ingredienti();

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        idRicetta = Integer.parseInt(b.getString("idRicetta")); // Uso ancora getString perchè prima il dato era INTEGER e non INT(dato che esiste solo getInt e non Integer)
        titolo = "chef";

        //LAYOUT
        tvIdRicetta = (TextView) findViewById(R.id.textViewIdRicetta);
        tvIdIngredienti= (TextView) findViewById(R.id.textViewIdIngredientiTutti);
        tvNomeIngredienti = (TextView) findViewById(R.id.textViewIdIngredientiTutti2);
        edIdIngrediente = (EditText) findViewById(R.id.editTextIdIngrediente2);
        btnAddIngredienteRicetta = (Button) findViewById(R.id.buttonAddIngredienteRicetta);

        //LAYOUT: ID Ricetta
        tvIdRicetta.setText("ID RICETTA :" + idRicetta);

        //ADD INGREDIENTE ALLA RICETTA
        btnAddIngredienteRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    AddIngredienteRicetta(idRicetta, Integer.parseInt(edIdIngrediente.getText().toString()));

                } else {
                    Toast.makeText(ChefComposizioneRicettaActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Carica a schermo tutti gli ingredienti in elenco (Id, Nome)
    private void Ingredienti() {
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            new ElencoIngredientiAsyncTask().execute();

        } else {
            Toast.makeText(ChefComposizioneRicettaActivity.this,
                    "Impossibile connettersi ad internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void AddIngredienteRicetta(Integer idRicetta, Integer idIngrediente) {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(idRicetta.toString()) &&
                !STRING_EMPTY.equals(idIngrediente.toString())){

            //Controlla che l'ingrediente non sia già inserito
            new IngredienteInRicettaAsyncTask().execute(idRicetta.toString(), idIngrediente.toString());

            //Modo brutto per cercare di sincronizzare i due processi
            int i, j = 0;
            for (i = 0; i < 50000000; i++) {
                j++;
            }

            if(success == 1){
                //Se non è presente nella ricetta lo aggiunge
                new AddIngredienteRicettaAsyncTask().execute(idRicetta.toString(), idIngrediente.toString());
            }

        } else {
            Toast.makeText(ChefComposizioneRicettaActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * AsyncTask for ElencoIngredienti
     */
    private class ElencoIngredientiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elencoIngredienti.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                ingredienti = jsonObject.getInt("ingredienti");
                nomeIngredienti = jsonObject.getString("nome");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        tvIdIngredienti.setText(ingredienti.toString());
                        tvNomeIngredienti.setText(nomeIngredienti);
                    } else {
                        Toast.makeText(ChefComposizioneRicettaActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for IngredienteInRicetta
     */
    private class IngredienteInRicettaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];
            String idIngrediente = params[1];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("idIngrediente", idIngrediente);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "ingredienteInRicetta.php", "POST", httpParams);

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

                        Toast.makeText(ChefComposizioneRicettaActivity.this,
                                "Ingrediente AGGIUNTO alla RICETTA", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefComposizioneRicettaActivity.this,
                                "Errore: Ingrediente già AGGIUNTO", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for AddIngredienteRicetta
     */
    private class AddIngredienteRicettaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];
            String idIngrediente = params[1];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("idIngrediente", idIngrediente);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addIngredienteRicetta.php", "POST", httpParams);

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

                    } else {
                        Toast.makeText(ChefComposizioneRicettaActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
