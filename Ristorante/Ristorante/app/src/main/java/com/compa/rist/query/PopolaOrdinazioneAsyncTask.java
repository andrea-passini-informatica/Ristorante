package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PopolaOrdinazioneAsyncTask extends AsyncTask<String, String, String> {

    Integer success;
    String ricetteOrdinazione;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        Integer numeroOrdinazione = Integer.parseInt(params[0]);

        httpParams.put("idOrdinazione", numeroOrdinazione.toString());

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "piattiOrdinazione.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");

            JSONArray jsonArray = jsonObject.getJSONArray("array");

            //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonCont = jsonArray.getJSONObject(i);
                String id = jsonCont.getString("id");

                buffer.append(" Id piatto = " + id + "\n");
            }

            ricetteOrdinazione = buffer.toString();

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

    public String getRicetteOrdinazione() {
        return ricetteOrdinazione;
    }
}

