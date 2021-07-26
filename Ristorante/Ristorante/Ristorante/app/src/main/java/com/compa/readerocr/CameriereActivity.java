package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;
import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CameriereActivity extends Activity {

    private EditText txtNumTavolo;

    private Button btnNuovaOrdinazione;

    private Integer success, numeroTavolo, idOrdinazione;

    private String data;
    private String nome;
    private String password;
    private String titolo;
    private String id;

    private Boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameriere);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "Cameriere";

        txtNumTavolo = (EditText) findViewById(R.id.textNumeroTavolo);

        btnNuovaOrdinazione = (Button) findViewById(R.id.buttonNuovaOrdinazione);

        btnNuovaOrdinazione.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    success = CreaNuovaOrdinazione();
                } else {
                    Toast.makeText(CameriereActivity.this,
                            "Impossibile connettersi ad internet", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Crea ordinazione e PiattiOrdinati c
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Integer CreaNuovaOrdinazione() {
        String STRING_EMPTY ="";

        if(!STRING_EMPTY.equals(txtNumTavolo.getText().toString())){
            numeroTavolo = Integer.parseInt(txtNumTavolo.getText().toString());

            data = getFormattedDate();

            new ControlloTavoloAsyncTask().execute();
        }
        return null;
    }


    //METODI PER QUERY

    /**
     * AsyncTask for ControlloTavolo
     */
    private class ControlloTavoloAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters
            httpParams.put("numero", numeroTavolo.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaTavolo.php", "POST", httpParams);

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
                        new CreaOrdinazioneAsyncTask().execute();
                    } else {
                        Toast.makeText(CameriereActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for CreaOrdinazione
     */
    private class CreaOrdinazioneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters
            httpParams.put("data", data);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "addOrdinazione.php", "POST", httpParams);

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
                        new CercaIdOrdinazioneAsyncTask().execute();
                    } else {
                        idOrdinazione = null;
                        Toast.makeText(CameriereActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for cercaIdOrdinazione
     */
    private class CercaIdOrdinazioneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            //Populating request parameters

            httpParams.put("data", data);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "cercaIdOrdinazione.php", "POST", httpParams);

            try {
                Integer success = jsonObject.getInt("success");
                idOrdinazione = jsonObject.getInt("id");
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
                        //Adesso che ho la nuova ordinazione cambio activity

                        Intent intentOrdinazione = new Intent(CameriereActivity.this, CameriereOrdinazioneActivity.class);

                        // Pulisci Bundle
                        Bundle b = new Bundle();

                        //Inserisci dati da passare nel bundle
                        b.putString("idOrdinazione", idOrdinazione.toString());
                        b.putString("numeroTavolo", numeroTavolo.toString());
                        intentOrdinazione.putExtras(b);
                        intentOrdinazione.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        //Passo all'altra activity
                        startActivity(intentOrdinazione);
                    } else {
                        idOrdinazione = null;
                        Toast.makeText(CameriereActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


    //DATA
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getFormattedDate() {
        //SimpleDateFormat called without pattern
        return new SimpleDateFormat().format(Calendar.getInstance().getTime());
    }

}
