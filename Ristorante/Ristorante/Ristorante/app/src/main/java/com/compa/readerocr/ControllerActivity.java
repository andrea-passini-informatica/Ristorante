package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.helper.HttpJsonParser;
//import com.compa.readerocr.query.AccediAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ControllerActivity extends Activity {

    /**
     * ATTRIBUTI
     */
    private EditText txtUsername;
    private EditText txtPassword;

    private Button btnLogin;
    private Button btnCancella;

    private Spinner spnRole;

    //DA SISTEMARE NEL DIAGRAMMA CALSSI
    private String nome;
    private String password;
    private String titolo;
    private Integer id;
    private Integer success, idPiatto, quantitàPiatto;

    private boolean a;


    /**
     * METODI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        txtUsername = (EditText) findViewById(R.id.editUsername);
        txtPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnCancella = (Button) findViewById(R.id.buttonCancella);

        spnRole = (Spinner) findViewById(R.id.spinnerRole);

        //LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    id = Integer.parseInt(txtUsername.getText().toString());
                    password = txtPassword.getText().toString();
                    titolo = spnRole.getSelectedItem().toString();

                    Accedi(id, password, titolo);

                } else {
                    Toast.makeText(ControllerActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //CANCELLA CAMPI
        btnCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg1) {
                txtUsername.setText("");
                txtPassword.setText("");
            }
        });
    }

    //Controllo campi e chiamata metodo Controller.Accedi(passando parametri)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Accedi(Integer id, String password, String titolo) {
        String STRING_EMPTY = "";

        //Controlla che i campi siano stati riempiti
        if (!STRING_EMPTY.equals(id.toString()) &&
                !STRING_EMPTY.equals(password) &&
                !STRING_EMPTY.equals(titolo)) {

            new AccediAsyncTask().execute(id.toString(), password, titolo);
            //new AccediAsyncTask(ControllerActivity.this).execute(id.toString(), password, titolo);


            //Modo brutto per cercare di sincronizzare i due processi

            int i, j = 0;
            for (i = 0; i < 50000000; i++) {
                j++;
            }


            //Gestire dipendente
            if (a) {
                if (titolo.equals("magazziniere")) {
                    Magazziniere(nome, id, password);

                } else if(titolo.equals("Cameriere")){
                    Cameriere(nome, id, password);

                } else if(titolo.equals("cuoco")){
                    Cuoco(nome, id, password);

                } else if(titolo.equals("chef")){
                    Chef(nome, id, password);

                } else if(titolo.equals("dirigente")){
                    Dirigente(nome, id, password);
                }
            }else {
                Toast.makeText(ControllerActivity.this,
                        "Impossibile accedere", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ControllerActivity.this,
                    "Completa correttamente i campi", Toast.LENGTH_LONG).show();
        }
    }

    //DIPENDENTI
    //+ Magazziniere( in Nome: string, in ID: integer, in Password: string)
    public void Magazziniere(String nome, Integer id, String password){
        Intent intentMagazziniere = new Intent(ControllerActivity.this, MagazziniereActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("id", id.toString());
        b.putString("password", password);

        intentMagazziniere.putExtras(b);
        startActivity(intentMagazziniere);
    }

    //+ Cameriere( in Nome: string, in ID: integer, in Password: string)
    public void Cameriere(String nome, Integer id, String password){
        Intent intentCameriere = new Intent(ControllerActivity.this, CameriereActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("password", password);
        b.putString("id", id.toString());

        intentCameriere.putExtras(b);
        startActivity(intentCameriere);
    }

    //+ Cuoco( in Nome: string, in ID: integer, in Password: string)
    public void Cuoco(String nome, Integer id, String password){
        Intent intentCuoco = new Intent(ControllerActivity.this, CuocoActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("password", password);
        b.putString("id", id.toString());

        intentCuoco.putExtras(b);
        startActivity(intentCuoco);
    }

    //+ Chef( in Nome: string, in ID: integer, in Password: string)
    public void Chef(String nome, Integer id, String password){
        Intent intentChef = new Intent(ControllerActivity.this, ChefActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("password", password);
        b.putString("id", id.toString());

        intentChef.putExtras(b);
        startActivity(intentChef);
    }

    //+ Dirigente( in nome: string, in ID: integer, in Password: string)
    public void Dirigente(String nome, Integer id, String password){
        Intent intentDirigente = new Intent(ControllerActivity.this, DirigenteActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("password", password);
        b.putString("id", id.toString());

        intentDirigente.putExtras(b);
        startActivity(intentDirigente);
    }


    public Boolean verificaDisponibilità(Integer piatto) {
        //CERCA NELLAS RICETTA GLI INGREDIENTI ED IL LORO DOSAGGIO
        //CERCANE LA DISPONIBILITA' NELLE PROVVIGIONI
        new VerificaDiposnibilitàAsyncTask().execute(piatto.toString());

        int i,j = 0;
        for (i = 0; i < 50000000; i++) {
                j++;
        }

        if(a){
            return true;
        }
        return false;
    }

    public Boolean usoRisorse(Integer idPiatto){

        new UsoRisorseAsyncTask().execute(idPiatto.toString());

        int i,j = 0;
        for (i = 0; i < 50000000; i++) {
            j++;
        }

        if(a){
            return true;
        }
        return false;
    }


    public void setQueryResult(Integer success, String nome){
        this.nome = nome;
        if(success == 1){
            this.a = true;
        }
        else {
            this.a = false;
        }
    }


    //METODI PER QUERY

    /**
     * AsyncTask for ACCEDI
     */
    private class AccediAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String id = params[0];
            String password = params[1];
            String titolo = params[2];

            //Populating request parameters
            httpParams.put("id", id);
            httpParams.put("password", password);
            httpParams.put("titolo", titolo);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaDipendente.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                nome = jsonObject.getString("nome");
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
                        Toast.makeText(ControllerActivity.this,
                                "Errore QUERY", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }



    /**
     * AsyncTask for VERIFICA DISPONIBILITA'
     */
    public class VerificaDiposnibilitàAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idPiatto2 = idPiatto.toString();

            //Populating request parameters
            httpParams.put("INTRIC_id", idPiatto2);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "verificaDiposnibilità.php", "POST", httpParams);

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
                    if (!result.equals("1")) {
                        a = true;
                    } else {
                        Toast.makeText(ControllerActivity.this,
                                "Errore QUERY", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    /**
     * AsyncTask for USO RISORSE'
     */
    private class UsoRisorseAsyncTask extends AsyncTask<String, String, String>     {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            String idPiatto = params[0];

            //Populating request parameters
            httpParams.put("INTRIC_id", idPiatto);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "usoRisorse.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                if (success == 1) {
                    a = true;
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

                    } else {
                        Toast.makeText(ControllerActivity.this,
                                "Errore QUERY", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
