package com.compa.rist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DirigenteDipendentiAddActivity extends Activity {

    private EditText edIdDipendete, edNomeDipendete, edPasswordDipendete;

    private Spinner spnRoleTitolo;

    private Button btnAddDipendete, btnSvuotaCampiDipendete;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private Integer idDipendente;

    private Integer success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirigente_dipendenti_add);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "dirigente";
        idDipendente = Integer.parseInt(b.getString("idDipendente"));

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
                AddDipendente(Integer.parseInt(edIdDipendete.getText().toString()),
                        edNomeDipendete.getText().toString(),
                        edPasswordDipendete.getText().toString(),
                        spnRoleTitolo.getSelectedItem().toString()
                                );

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

    //AddDipendente( in IDdipendente: integer, in nome: string, in password: string, in titolo: string)
    private void AddDipendente(Integer iDdipendente, String nome, String password, String titolo) {

        new AddDipendenteAsyncTask().execute(iDdipendente.toString(), nome, password, titolo);

    }

    /**
     * AsyncTask for AddDipendente
     */
    private class AddDipendenteAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String iDdipendente = params[0];
            String nome = params[1];
            String password = params[2];
            String titolo = params[3];

            //Populating request parameters
            httpParams.put("iDdipendente", iDdipendente);
            httpParams.put("nome", nome);
            httpParams.put("password", password);
            httpParams.put("titolo", titolo);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addDipendente.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                if (success == 1) {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        Toast.makeText(DirigenteDipendentiAddActivity.this,
                                "AGGIUNTA COMPLETATA", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DirigenteDipendentiAddActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
