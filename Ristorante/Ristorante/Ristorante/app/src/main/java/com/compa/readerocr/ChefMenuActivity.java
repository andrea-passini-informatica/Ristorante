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

public class ChefMenuActivity extends Activity {

    private TextView tvMenù, tvIdRicette, tvNomeRicette;

    private EditText edIdRicetta;

    private Button btnAddRicettaMenù, btnElimRicettaMenù;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private String nomeRicette;

    private Integer idRicette, menu;

    private int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "chef";

        tvMenù = (TextView) findViewById(R.id.textViewMenù);
        tvIdRicette = (TextView) findViewById(R.id.textViewIdRicette);
        tvNomeRicette = (TextView) findViewById(R.id.tvNomeRicette);
        edIdRicetta = (EditText) findViewById(R.id.editTextIdRicette);
        btnAddRicettaMenù = (Button) findViewById(R.id.buttonAddRicettaMenù);
        btnElimRicettaMenù = (Button) findViewById(R.id.buttonElimRIcettaMenù);

        //Popola Menu
        PopolaMenu();

        //Popola Ricette
        PopolaIdRicette();

        btnAddRicettaMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    AddRicettaMenù(Integer.parseInt(edIdRicetta.getText().toString()));

                } else {
                    Toast.makeText(ChefMenuActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnElimRicettaMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Controlla che la connesione web
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    ElimRicettaMenù(Integer.parseInt(edIdRicetta.getText().toString()));

                } else {
                    Toast.makeText(ChefMenuActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void PopolaMenu() {
        //Controlla che la connesione web
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            new MenuAsyncTask().execute();

        } else {
            Toast.makeText(ChefMenuActivity.this,
                    "Impossibile connettersi ad internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void PopolaIdRicette() {
        //Controlla che la connesione web
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            new IdRicetteAsyncTask().execute();

        } else {
            Toast.makeText(ChefMenuActivity.this,
                    "Impossibile connettersi ad internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void AddRicettaMenù(Integer IdRicetta) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IdRicetta.toString())) {

            success = 0;
            //Controlla che la ricetta esista
            new CercaIdRicettaAsyncTask().execute(edIdRicetta.toString());

            //Modo brutto per cercare di sincronizzare i due processi
            int i, j = 0;
            for (i = 0; i < 50000000; i++) {
                j++;
            }

            if (success == 1) {
                success = 0;
                //Controlla che la ricetta non sia nel MENU
                new CercaIdRicettaMenuAsyncTask().execute(IdRicetta.toString());

                //Modo brutto per cercare di sincronizzare i due processi
                i = 0;
                for (i = 0; i < 50000000; i++) {
                    j++;
                }

                if (success == 1) {
                    new AddRicettaMenuAsyncTask().execute(IdRicetta.toString());
                }

            }
        } else {
        Toast.makeText(ChefMenuActivity.this,
                "Completa i campi", Toast.LENGTH_LONG).show();
    }
    }

    private void ElimRicettaMenù(Integer IdRicetta) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IdRicetta.toString())) {

            success = 0;
            //Controlla che la ricetta non sia nel MENU
            new CercaIdRicettaMenuAsyncTask().execute(IdRicetta.toString());

            //Modo brutto per cercare di sincronizzare i due processi
            int i = 0;
            int j = 0;
            for (i = 0; i < 50000000; i++) {
                j++;
            }

            if (success == 1) {
                new ElimRicettaMenuAsyncTask().execute(IdRicetta.toString());
            }

        } else {
            Toast.makeText(ChefMenuActivity.this,
                    "Completa i campi", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * AsyncTask for Menu
     */
    private class MenuAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "menu.php", "POST", httpParams);
            try {
                success = jsonObject.getInt("success");
                menu = jsonObject.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        tvMenù.setText(menu);
                    } else {
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore MENU", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for IdRicette
     */
    private class IdRicetteAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "idRicette.php", "POST", httpParams);
            try {
                success = jsonObject.getInt("success");
                idRicette = jsonObject.getInt("id");
                nomeRicette = jsonObject.getString("nome");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        tvIdRicette.setText(idRicette);
                        tvNomeRicette.setText(nomeRicette);
                    } else {
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore ID RICETTA", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for AddRicettaMenu
     */
    private class AddRicettaMenuAsyncTask extends AsyncTask<String, String, String> {
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
                    "http://andreapa98.com/" + "addRicettaMenu.php", "POST", httpParams);
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
                        Toast.makeText(ChefMenuActivity.this,
                                "Ricetta AGGIUNTA", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for CercaIdRicetta
     */
    private class CercaIdRicettaAsyncTask extends AsyncTask<String, String, String> {
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
                    "http://andreapa98.com/" + "cercaIdRicetta.php", "POST", httpParams);
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
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for CercaIdRicettaMenu
     */
    private class CercaIdRicettaMenuAsyncTask extends AsyncTask<String, String, String> {
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
                    "http://andreapa98.com/" + "cercaIdRicettaMenu.php", "POST", httpParams);
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
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore Impossibile aggiungere Ricetta", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for ElimRicettaMenu
     */
    private class ElimRicettaMenuAsyncTask extends AsyncTask<String, String, String> {
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
                    "http://andreapa98.com/" + "elimRicettaMenu.php", "POST", httpParams);
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
                        Toast.makeText(ChefMenuActivity.this,
                                "Ricetta Eliminata", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChefMenuActivity.this,
                                "Errore: impossibile eliminare RICETTA", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
