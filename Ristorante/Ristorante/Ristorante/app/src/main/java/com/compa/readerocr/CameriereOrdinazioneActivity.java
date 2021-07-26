package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CameriereOrdinazioneActivity extends Activity {

    /**
     * ATTRIBUTI
     */
    private TextView txtMenù;

    private EditText txtPiatto, txtQuantitàPiatto;

    private Button btnAddPiatto;

    private Integer success, idPiatto, quantitàPiatto;
    private Integer numeroTavolo, idOrdinazione;//DA PASSARE CON BUNDLE

    private String menu;

    private Boolean a;


    /**
     * METODI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameriere_ordinazione);

        txtPiatto = (EditText) findViewById(R.id.textPiatto);
        txtQuantitàPiatto = (EditText) findViewById(R.id.textQuantitàPiatto);

        btnAddPiatto = (Button) findViewById(R.id.buttonAddPiatto);

        txtMenù = (TextView) findViewById(R.id.textMenù);

        //Visualizza menù
        new MenùAsyncTask().execute();

        //OTTENERE L'ID ORDINAZIONE CON BUNDLE
        Bundle b = getIntent().getExtras();
        idOrdinazione = Integer.parseInt(b.getString("idOrdinazione"));
        numeroTavolo = Integer.parseInt(b.getString("numeroTavolo"));

        //AGGIUNTA PIATTO
        btnAddPiatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idPiatto = Integer.parseInt(txtPiatto.getText().toString());
                quantitàPiatto = Integer.parseInt(txtQuantitàPiatto.getText().toString());

                AddPiattoOrdinazione(idPiatto, quantitàPiatto, idOrdinazione, numeroTavolo);
            }
        });
    }

    public void AddPiattoOrdinazione(Integer idPiatto, Integer quantitàPiatto, Integer idOrdinazione, Integer numeroTavolo) {

        if (idPiatto != null &&
                quantitàPiatto != null &&
                idOrdinazione != null) {

            //CERCA SE IL PIATTO ERA VERAMENTE NEL MENU
            new CercaPiattoNelMenùAsyncTask().execute(idPiatto.toString());

            if (a) {
                //VERIFICA DISPONIBILITA'
                ControllerActivity controllerActivity = new ControllerActivity();
                controllerActivity.verificaDisponibilità(idPiatto);

                if (a) {
                    //AGGIUGI PIATTO ALL'ORDINAZIONE
                    new AddPiattoOrdinazioneAsyncTask().execute(idPiatto.toString(),
                                                                quantitàPiatto.toString(),
                                                                idOrdinazione.toString(),
                                                                numeroTavolo.toString());
                }
            } else {
                //NEL CASO IN CUI IL PIATTO NON SIA NEL MENU'
                Toast.makeText(CameriereOrdinazioneActivity.this,
                        "Piatto fuori MENU'", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * QUERY
     */

    /**
     * AsyncTask for MenùAsyncTask
     */
    private class MenùAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaMenu.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                JSONArray jsonArray = jsonObject.getJSONArray("array");

                //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonCont = jsonArray.getJSONObject(i);
                    String id = jsonCont.getString("id");

                    buffer.append(" Id Ricetta = " + id + "\n");
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
                    if (success == 1 && !result.equals("")) {
                            txtMenù.setText(result);
                    } else {
                        Toast.makeText(CameriereOrdinazioneActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    /**
     * AsyncTask for CercaPiattoNelMenùAsyncTask
     */
    private class CercaPiattoNelMenùAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idPiatto = params[0];

            httpParams.put("INTRIC_id", idPiatto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaPiattoNelMenu.php", "POST", httpParams);

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
                        a = true;
                    } else {
                        a = false;
                        Toast.makeText(CameriereOrdinazioneActivity.this,
                                "Errore nella ricerca del piatto nel MENU' ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    /**
     * AsyncTask for AddPiattoOrdinazione
     */
    private class AddPiattoOrdinazioneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idPiatto = params[0];
            String idOrdinazione = params[1];
            String quantitàPiatto = params[2];
            String numeroTavolo = params[3];

            //Populating request parameters
            httpParams.put("INTRIC_id", idPiatto);
            httpParams.put("ORD_id", idOrdinazione);
            httpParams.put("quantita", quantitàPiatto);
            httpParams.put("TV_id", numeroTavolo);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addPiattoOrdinazione.php", "POST", httpParams);

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
                        Toast.makeText(CameriereOrdinazioneActivity.this,
                                "Piatto: " + idPiatto + " ordinato.", Toast.LENGTH_LONG).show();
                        CancellaCampi();
                    } else {
                        Toast.makeText(CameriereOrdinazioneActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void CancellaCampi() {
        txtPiatto.setText("");
        txtQuantitàPiatto.setText("");
    }

}
