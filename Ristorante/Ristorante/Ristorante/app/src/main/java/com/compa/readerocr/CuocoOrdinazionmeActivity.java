package com.compa.rist;

import android.os.AsyncTask;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CuocoOrdinazionmeActivity extends Activity {

    private String nome, password, titolo, id, idPiatto, ordinazione;

    private Boolean a;

    private Integer numeroOrdinazione;

    private Button btnCucinaPiatto;

    private EditText txtPiatto;

    private TextView txtvOrdinaizione;

    private Integer success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoco_ordinazionme);

        //popola
        popola();

        btnCucinaPiatto = (Button) findViewById(R.id.buttonCucinaPiatto);
        txtvOrdinaizione = (TextView) findViewById(R.id.textViewOrdinazione);
        txtPiatto = (EditText) findViewById(R.id.editTextPiatto);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "cuoco";
        numeroOrdinazione = Integer.parseInt(b.getString("numeroOrdinazione"));

        //Ogni volta, cancello l'elemento dall'ordinazione
        // rifaccio la query per aggiornare i piatti rimanenti

        btnCucinaPiatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    idPiatto = txtPiatto.getText().toString();

                    new ControllaPiattoAsyncTask().execute();

                } else {
                    Toast.makeText(CuocoOrdinazionmeActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void popola() {

        new PopolaAsyncTask().execute();

    }

    //QUERY
    /**
     * AsyncTask for POPOLA
     */
    private class PopolaAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

                httpParams.put("idOrdinazione", numeroOrdinazione.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "piattiOrdinazione.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");

                JSONArray jsonArray = jsonObject.getJSONArray("array");

                //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonCont = jsonArray.getJSONObject(i);
                    String id = jsonCont.getString("id");

                    buffer.append(" Id piatto = " + id+ "\n");
                }

                return buffer.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        if(!result.equals("")){
                            txtvOrdinaizione.setText(result);
                        } else {
                            Toast.makeText(CuocoOrdinazionmeActivity.this,
                                    "ERRORE: ID Ordinazione", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CuocoOrdinazionmeActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ControllaPiatto
     */
    private class ControllaPiattoAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            httpParams.put("idPiatto", idPiatto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "piattoInOrdinazione.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                return success.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result.equals("1")) {
                        new PiattoCucinatoOrdinazioneAsyncTask().execute();
                    } else {
                        Toast.makeText(CuocoOrdinazionmeActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for PiattoCucinatoOrdinazione
     */
    private class PiattoCucinatoOrdinazioneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            httpParams.put("idPiatto", idPiatto);
            httpParams.put("numeroOrdinazione", numeroOrdinazione.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "piattoCucinatoOrdinazione.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                return success.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result.equals("1")) {
                        popola();
                    } else {
                        Toast.makeText(CuocoOrdinazionmeActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
