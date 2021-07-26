package com.compa.rist;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MagazziniereActivity extends Activity {

    private EditText txtNomeIngrediente, txtQuantità;

    private Button btnAggiungi;

    private Integer quantità, quantitaIngrediente, idIngrediente, success;


    private String nome;
    private String password;
    private String titolo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazziniere);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "magazziniere";

        txtNomeIngrediente = (EditText) findViewById(R.id.textIngrediente);
        txtQuantità = (EditText) findViewById(R.id.textQuantità);

        btnAggiungi = (Button) findViewById(R.id.buttonAggiungiRisorsa);

        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                idIngrediente = Integer.parseInt(txtNomeIngrediente.getText().toString());

                quantità = Integer.parseInt(txtQuantità.getText().toString());

                AddRisorse(quantità, idIngrediente);
            }
        });
    }

    private void AddRisorse(Integer quantità, Integer idIngrediente) {

        if(quantità != null &&
                idIngrediente != null){

            new CercaIdIngrediente().execute();

        }

    }


    /**
     * AsyncTask for CercaIdIngrediente
     */
    private class CercaIdIngrediente extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters
            httpParams.put("id", idIngrediente.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapassini5l.altervista.org/" + "cercaIdIngrediente.php", "POST", httpParams);

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
                        new CercaQuantitaIngrediente().execute();
                    } else {
                        Toast.makeText(MagazziniereActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for CercaQuantitaIngrediente
     */
    private class CercaQuantitaIngrediente extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters
            httpParams.put("id", idIngrediente.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaQuantità.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                quantitaIngrediente = jsonObject.getInt("quantitaIngrediente");
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
                        new AddRisorseAsyncTask().execute();
                    } else {
                        Toast.makeText(MagazziniereActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for AddRisorse
     */
    private class AddRisorseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters
            httpParams.put("ING_id", quantità.toString());
            httpParams.put("quantita", idIngrediente.toString());
            httpParams.put("quantitaIngrediente", quantitaIngrediente.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addRisorse.php", "POST", httpParams);

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
                        SvuotaCampi();
                        Toast.makeText(MagazziniereActivity.this,
                                "Risorse aggiunte correttamente ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MagazziniereActivity.this,
                                "Errore nella ricerca della cedola ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void SvuotaCampi() {
        txtNomeIngrediente.setText("");
        txtQuantità.setText("");
    }


}
