package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ElencoFornitoriAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private String elencoIngredienteFornitore;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "elencoFornitori.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");

            JSONArray jsonArray = jsonObject.getJSONArray("array");

            //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonCont = jsonArray.getJSONObject(i);

                Integer idFornitore = jsonCont.getInt("idFornitore");

                String nomeFornitore = jsonCont.getString("nomeFornitore");

                buffer.append( idFornitore + " " + nomeFornitore + "\n");
            }

            elencoIngredienteFornitore = "Fornitori: " + buffer.toString();

            return success.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final String result) {
    }

    public Integer getSuccess() {
        return success;
    }

    public String getElencoIngredienteFornitore() {
        return elencoIngredienteFornitore;
    }

}
