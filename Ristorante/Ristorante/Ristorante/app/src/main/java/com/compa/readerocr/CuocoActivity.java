package com.compa.rist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuocoActivity extends Activity {

    private TextView txtOrdinazioni;

    private EditText edNumeroOrdinazione;

    private Button btnLeggiOrdinazione;

    private Integer numeroOrdinazione, success;

    private  ArrayList<String> list;

    private String[] ordinazione;
    private String nome;
    private String password;
    private String titolo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuoco);

        popolaOrdinazione();

        list = new ArrayList();

        txtOrdinazioni = (TextView) findViewById(R.id.textOrdinazioni);

        edNumeroOrdinazione = (EditText) findViewById(R.id.editNumeroOrdinazione);

        btnLeggiOrdinazione = (Button) findViewById(R.id.buttonLeggiOrdinazione);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "cuoco";

        btnLeggiOrdinazione.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    leggiOrdinazione();

                } else {
                    Toast.makeText(CuocoActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void popolaOrdinazione() {
        //Controlla che la connesione web sai possibile e poi procede con ADD
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            new PopolaAsyncTask().execute();

        } else {
            Toast.makeText(CuocoActivity.this,
                    "Impossibile connettersi ad internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void leggiOrdinazione() {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(numeroOrdinazione)){
            numeroOrdinazione = Integer.parseInt(edNumeroOrdinazione.getText().toString());

            Intent intentCuocoOrdinazione = new Intent(CuocoActivity.this, CuocoOrdinazionmeActivity.class);
            Bundle b = new Bundle();

            b.putString("nome", nome);
            b.putString("id", id);
            b.putString("password", password);
            b.putString("numeroOrdinazione", numeroOrdinazione.toString());

            intentCuocoOrdinazione.putExtras(b);
            startActivity(intentCuocoOrdinazione);
        } else {
            //Non è possibile procedere nel caso in cui manchi l'ID
            Toast.makeText(CuocoActivity.this,
                    "Completa i campi",
                    Toast.LENGTH_LONG).show();
        }

    }

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

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "idOrdinazione.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");

                JSONArray jsonArray = jsonObject.getJSONArray("array");

                //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonCont = jsonArray.getJSONObject(i);
                    String id1 = jsonCont.getString("id");

                    buffer.append(" Id Ordinazione = " + id1 + "\n");
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
                            txtOrdinazioni.setText(result);
                        } else {
                            Toast.makeText(CuocoActivity.this,
                                    "ERRORE: ID Ordinazione", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CuocoActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}