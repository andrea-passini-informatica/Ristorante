package com.compa.rist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DirigenteResocontoActivity extends Activity {

    private TextView tvPiattiServiti, tvGuadagnoTotale;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    private String use;

    private Integer success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirigente_resoconto);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "dirigente";

        tvPiattiServiti = (TextView) findViewById(R.id.textViewPiattiServiti);
        tvGuadagnoTotale = (TextView) findViewById(R.id.textViewGuadagnoTotale);

        //resocontoPiattiServiti()
        resocontoPiattiServiti();

        //guadagnoTotale()
        guadagnoTotale();

    }

    private void resocontoPiattiServiti() {
        new ResocontoPiattiServitiAsyncTask().execute();
    }

    private void guadagnoTotale() {
        new GuadagnoTotaleAsyncTask().execute();
    }


    /**
     * AsyncTask for ResocontoPiattiServiti
     */
    private class ResocontoPiattiServitiAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "resocontoPiattiServiti.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                use = jsonObject.getString("resoconto");
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
                        Toast.makeText(DirigenteResocontoActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    /**
     * AsyncTask for GuadagnoTotale
     */
    private class GuadagnoTotaleAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    "http://andreapa98.com/" + "guadagnoTotale.php", "POST", httpParams);

            try {
                success = jsonObject.getInt("success");
                use = jsonObject.getString("guadagno");
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
                        Toast.makeText(DirigenteResocontoActivity.this,
                                "Errore", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
