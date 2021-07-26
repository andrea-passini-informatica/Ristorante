package com.compa.rist;

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

public class ChefIngredientiActivity extends Activity {

    private EditText etIdIndrediente, etNomeIngrediente, etUnita, etIdFornitore;

    private Button btnAggiungiIngrediente, btnModificaIngrediente, btnEliminaIngrediente;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ingredienti);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "chef";

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
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    AddIngrediente(Integer.parseInt(etIdIndrediente.getText().toString()), 
                                    etNomeIngrediente.getText().toString(),
                                    etUnita.getText().toString(),
                                    Integer.parseInt(etIdFornitore.getText().toString()));

                } else {
                    Toast.makeText(ChefIngredientiActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnModificaIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //ModIngrediente( in IDingredienteORIGINALE: integer, in nome: string, in UDM: string, in IDfornitore: integer)
                    ModIngrediente(Integer.parseInt(etIdIndrediente.getText().toString()),
                            etNomeIngrediente.getText().toString(),
                            etUnita.getText().toString(),
                            Integer.parseInt(etIdFornitore.getText().toString()));

                } else {
                    Toast.makeText(ChefIngredientiActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEliminaIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    // ElimIngrediente( in IDingrediente: integer)
                    ElimIngrediente(Integer.parseInt(etIdIndrediente.getText().toString()));

                } else {
                    Toast.makeText(ChefIngredientiActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void AddIngrediente(Integer IdIngrediente, String nomeIngrediente, String UDM, Integer IdFornitore) {
        String STRING_EMPTY = "";
        if(!STRING_EMPTY.equals(IdIngrediente) &&
                !STRING_EMPTY.equals(nomeIngrediente) &&
                !STRING_EMPTY.equals(UDM) &&
                !STRING_EMPTY.equals(IdFornitore)){

            new AddIngredienteAsyncTask().execute(IdIngrediente.toString(), nomeIngrediente, UDM, IdFornitore.toString());

        } else {
            Toast.makeText(ChefIngredientiActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }

    private void ModIngrediente(Integer IDingredienteORIGINALE, String nomeIngrediente, String UDM, Integer IdFornitore) {
        String STRING_EMPTY = "";
        if(!STRING_EMPTY.equals(IDingredienteORIGINALE) &&
                !STRING_EMPTY.equals(nomeIngrediente) &&
                !STRING_EMPTY.equals(UDM) &&
                !STRING_EMPTY.equals(IdFornitore)){

            new ModIngredienteAsyncTask().execute(IDingredienteORIGINALE.toString(), nomeIngrediente, UDM, IdFornitore.toString());

        } else {
            Toast.makeText(ChefIngredientiActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }

    private void ElimIngrediente(Integer IDingrediente) {
        String STRING_EMPTY = "";
        if(!STRING_EMPTY.equals(IDingrediente)){

            new ElimIngredienteAsyncTask().execute(IDingrediente.toString());

        } else {
            Toast.makeText(ChefIngredientiActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * AsyncTask for AddIngrediente
     */
    private class AddIngredienteAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String IdIngrediente = params[0];
            String nomeIngrediente = params[1];
            String UDM = params[2];
            String IdFornitore = params[3];

            //Populating request parameters
            httpParams.put("IdIngrediente", IdIngrediente);
            httpParams.put("nomeIngrediente", nomeIngrediente);
            httpParams.put("UDM", UDM);
            httpParams.put("IdFornitore", IdFornitore);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "AddIngrediente.php", "POST", httpParams);

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
                        Toast.makeText(ChefIngredientiActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ModIngrediente
     */
    private class ModIngredienteAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String IDingredienteORIGINALE = params[0];
            String nomeIngrediente = params[1];
            String UDM = params[2];
            String IdFornitore = params[3];

            //Populating request parameters
            httpParams.put("IdIngrediente", IDingredienteORIGINALE);
            httpParams.put("nomeIngrediente", nomeIngrediente);
            httpParams.put("UDM", UDM);
            httpParams.put("IdFornitore", IdFornitore);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "modIngrediente.php", "POST", httpParams);

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
                        Toast.makeText(ChefIngredientiActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ElimIngrediente
     */
    private class ElimIngredienteAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String IDingredienteORIGINALE = params[0];

            //Populating request parameters
            httpParams.put("IdIngrediente", IDingredienteORIGINALE);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "elimIngrediente.php", "POST", httpParams);

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
                        Toast.makeText(ChefIngredientiActivity.this,
                                "Errore nella ricerca", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
