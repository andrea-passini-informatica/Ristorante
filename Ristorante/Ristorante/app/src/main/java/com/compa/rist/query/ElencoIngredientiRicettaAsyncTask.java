package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ElencoIngredientiRicettaAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private String elencoIngredientiRicetta;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String idRicetta = params[0];

        //Populating request parameters
        httpParams.put("INTRIC_id", idRicetta);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "elencoIngredientiRicetta.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");

            JSONArray jsonArray = jsonObject.getJSONArray("array");

            //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonCont = jsonArray.getJSONObject(i);

                Integer idIngrediente = jsonCont.getInt("ING_id");
                Integer quantitaIngrediente = jsonCont.getInt("quantita");

                buffer.append(idIngrediente + " " + quantitaIngrediente + "\n");
            }

            elencoIngredientiRicetta = buffer.toString();

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

    public String getElencoIngredientiRicetta() {
        return elencoIngredientiRicetta;
    }
}
