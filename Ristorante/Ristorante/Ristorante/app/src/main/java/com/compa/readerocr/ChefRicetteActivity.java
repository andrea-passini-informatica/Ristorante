package com.compa.rist;

import android.content.Intent;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChefRicetteActivity extends Activity {

    private EditText etIdRicetta, etNomeRicetta, etPreprazioneRicetta, etPrezzoRicetta, etPortata;

    private Button btnAddRicetta, btnModRicetta, btnElimRicetta;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ricette);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "chef";

        etIdRicetta = (EditText) findViewById(R.id.editTextIdRicetta);
        etNomeRicetta = (EditText) findViewById(R.id.editTextNomeRicetta);
        etPreprazioneRicetta = (EditText) findViewById(R.id.editTextPreparazioneRicetta);
        etPrezzoRicetta = (EditText) findViewById(R.id.editTextPrezzoRicetta);
        etPortata = (EditText) findViewById(R.id.editTextPortata);

        btnAddRicetta = (Button) findViewById(R.id.buttonAddRicetta);
        btnModRicetta = (Button) findViewById(R.id.buttonModRicetta);
        btnElimRicetta = (Button) findViewById(R.id.buttonElimRicetta);

        //Aggiungi Ricetta e poi vai alla composizione di essa
        btnAddRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    AddRicetta(Integer.parseInt(etIdRicetta.getText().toString()),
                                etNomeRicetta.getText().toString(),
                                etPreprazioneRicetta.getText().toString(),
                                Integer.parseInt(etPrezzoRicetta.getText().toString()),
                                Integer.parseInt(etPortata.getText().toString()));

                } else {
                    Toast.makeText(ChefRicetteActivity.this,
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

                    ModRicetta(Integer.parseInt(etIdRicetta.getText().toString()),
                            etNomeRicetta.getText().toString(),
                            etPreprazioneRicetta.getText().toString(),
                            Integer.parseInt(etPrezzoRicetta.getText().toString()),
                            Integer.parseInt(etPortata.getText().toString()));

                } else {
                    Toast.makeText(ChefRicetteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnElimRicetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())){

                    ElimRicetta(Integer.parseInt(etIdRicetta.getText().toString()));

                }
                else {
                    Toast.makeText(ChefRicetteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void AddRicetta(Integer idRicetta, String nomeRicetta, String preparazioneRicetta, Integer prezzoRicetta, Integer portata) {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(idRicetta) &&
                !STRING_EMPTY.equals(nomeRicetta) &&
                !STRING_EMPTY.equals(preparazioneRicetta) &&
                !STRING_EMPTY.equals(prezzoRicetta) &&
                !STRING_EMPTY.equals(portata)){

            new AddRicettaAsyncTask().execute(idRicetta.toString(), nomeRicetta, preparazioneRicetta, prezzoRicetta.toString(), portata.toString());

        } else {
            Toast.makeText(ChefRicetteActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }

    }

    private void ModRicetta(Integer idRicetta, String nomeRicetta, String preparazioneRicetta, Integer prezzoRicetta, Integer portata) {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(idRicetta) &&
                !STRING_EMPTY.equals(nomeRicetta) &&
                !STRING_EMPTY.equals(preparazioneRicetta) &&
                !STRING_EMPTY.equals(prezzoRicetta) &&
                !STRING_EMPTY.equals(portata)){

            new ModRicettaAsyncTask().execute(idRicetta.toString(), nomeRicetta, preparazioneRicetta, prezzoRicetta.toString(), portata.toString());


        } else if(!STRING_EMPTY.equals(idRicetta) &&
                STRING_EMPTY.equals(nomeRicetta) &&
                STRING_EMPTY.equals(preparazioneRicetta) &&
                STRING_EMPTY.equals(prezzoRicetta) &&
                STRING_EMPTY.equals(portata)){

            //SE METTI SOLO ID, vai diretto a modifica ingredienti
            //Procedi verso la MODIFICA COMPOSIZIONE RICETTA
            Intent intentChefComposizionRicetta = new Intent(ChefRicetteActivity.this, ChefModificaComposizioneRicettaActivity.class);
            Bundle b = new Bundle();

            b.putString("idRicetta", etIdRicetta.getText().toString());
            b.putString("nome", nome);
            b.putString("password", password);
            b.putString("id", id);

            intentChefComposizionRicetta.putExtras(b);
            startActivity(intentChefComposizionRicetta);

        } else {
            Toast.makeText(ChefRicetteActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }

    private void ElimRicetta(Integer idRicetta) {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(idRicetta)){

            new ElimRicettaAsyncTask().execute(idRicetta.toString());

        } else {
            Toast.makeText(ChefRicetteActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * QUERY
     */

    /**
     * AsyncTask for AddRicetta
     */
    private class AddRicettaAsyncTask extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];
            String nomeRicetta = params[1];
            String preparazioneRicetta = params[2];
            String prezzoRicetta = params[3];
            String portata = params[4];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("nomeRicetta", nomeRicetta);
            httpParams.put("preparazioneRicetta", preparazioneRicetta);
            httpParams.put("prezzoRicetta", prezzoRicetta);
            httpParams.put("portata", portata);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addRicetta.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                return success;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final Integer result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result == 1) {
                        //Procedi verso la COMPOSIZIONE RICETTA
                        Intent intentChefComposizionRicetta = new Intent(ChefRicetteActivity.this, ChefComposizioneRicettaActivity.class);
                        Bundle b = new Bundle();

                        b.putString("nome", nome);
                        b.putString("id", id);
                        b.putString("password", password);
                        b.putString("idRicetta", etIdRicetta.getText().toString());

                        intentChefComposizionRicetta.putExtras(b);
                        startActivity(intentChefComposizionRicetta);
                    } else {
                        Toast.makeText(ChefRicetteActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ModRicetta
     */
    private class ModRicettaAsyncTask extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];
            String nomeRicetta = params[1];
            String preparazioneRicetta = params[2];
            String prezzoRicetta = params[3];
            String portata = params[4];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);
            httpParams.put("nomeRicetta", nomeRicetta);
            httpParams.put("preparazioneRicetta", preparazioneRicetta);
            httpParams.put("prezzoRicetta", prezzoRicetta);
            httpParams.put("portata", portata);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addRicetta.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                return success;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final Integer result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result == 1) {
                        Intent intentChefComposizionRicetta = new Intent(ChefRicetteActivity.this, ChefModificaComposizioneRicettaActivity.class);
                        Bundle b = new Bundle();

                        b.putString("idRicetta", etIdRicetta.getText().toString());
                        b.putString("nome", nome);
                        b.putString("password", password);
                        b.putString("id", id);

                        intentChefComposizionRicetta.putExtras(b);
                        startActivity(intentChefComposizionRicetta);
                    } else {
                        Toast.makeText(ChefRicetteActivity.this,
                                "Errore: QUERY", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ElimRicetta
     */
    private class ElimRicettaAsyncTask extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idRicetta = params[0];

            //Populating request parameters
            httpParams.put("idRicetta", idRicetta);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elimRicetta.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                return success;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final Integer result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result == 1) {
                        Toast.makeText(ChefRicetteActivity.this,
                                "Ricetta eliminata con successo", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefRicetteActivity.this,
                                "Errore: QUERY", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
