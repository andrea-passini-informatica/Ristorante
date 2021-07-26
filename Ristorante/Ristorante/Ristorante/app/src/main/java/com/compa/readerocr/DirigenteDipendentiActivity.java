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

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DirigenteDipendentiActivity extends Activity {

    private TextView txtElencoDipendenti;

    private EditText edId;

    private Button btnAddDipendente, btnElimDipendente, btnModDipendente;

    private String nome;
    private String password;
    private String titolo;
    private String id;
    private String elencoDipendenti;

    private Integer success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirigente_dipendenti);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "dirigente";

        txtElencoDipendenti = (TextView) findViewById(R.id.textViewElencoDipendenti);
        edId = (EditText) findViewById(R.id.editTextIdDipendente);
        btnAddDipendente = (Button) findViewById(R.id.buttonAggiungiDipendente);
        btnModDipendente = (Button) findViewById(R.id.buttonModificaDipendente);
        btnElimDipendente = (Button) findViewById(R.id.buttonElimDipendente);

        popolaElencoDipendenti();

        //Modo brutto per cercare di sincronizzare i due processi
        int i, j = 0;
        for (i = 0; i < 50000000; i++) {
            j++;
        }

        btnAddDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AGGIUNGI DIPENDENTE -> Vai a Nuova ACTIVITY
                String STRING_EMPTY = "";

                if(!STRING_EMPTY.equals(edId.getText().toString())){
                    Intent intentChef = new Intent(DirigenteDipendentiActivity.this, DirigenteDipendentiAddActivity.class);
                    Bundle b = new Bundle();

                    b.putString("nome", nome);
                    b.putString("password", password);
                    b.putString("id", id);
                    b.putString("idDipendente", edId.getText().toString());

                    intentChef.putExtras(b);
                    startActivity(intentChef);
                }
            }
        });

        btnElimDipendente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ELIMINA DIPENDENTE
                ElimDipendente( Integer.parseInt(edId.getText().toString()) );
            }
        });

    }

    private void popolaElencoDipendenti() {

        new ElencoDipendentiAsyncTask().execute();

    }

    private void ElimDipendente(Integer IDdipendente) {
        new ElimDipendentiAsyncTask().execute(IDdipendente.toString());
    }


    /**
     * AsyncTask for ElencoDipendenti
     */
    private class ElencoDipendentiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idDipendente = params[0];

            //Populating request parameters
            httpParams.put("idDipendente", idDipendente);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elimDipendenti.php", "POST", httpParams);

            try {
                String elencoDipendenti = jsonObject.getString("array");
                return elencoDipendenti;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (result != null) {
                        txtElencoDipendenti.setText(result);
                    } else {
                        Toast.makeText(DirigenteDipendentiActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ElencoDipendenti
     */
    private class ElimDipendentiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elencoDipendenti.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                elencoDipendenti = jsonObject.getString("array");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        txtElencoDipendenti.setText(elencoDipendenti);
                    } else {
                        Toast.makeText(DirigenteDipendentiActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
