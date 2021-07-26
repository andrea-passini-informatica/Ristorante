package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPiattoOrdinazioneAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String idPiatto = params[0];
        String idOrdinazione = params[1];
        String quantitàPiatto = params[2];
        String numeroTavolo = params[3];

        //Populating request parameters
        httpParams.put("INTRIC_id", idPiatto);
        httpParams.put("ORD_id", idOrdinazione);
        httpParams.put("quantita", quantitàPiatto);
        httpParams.put("TV_id", numeroTavolo);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addPiattoOrdinazione.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String result) {

    }

    public Integer getSuccess() {
        return success;
    }
}
