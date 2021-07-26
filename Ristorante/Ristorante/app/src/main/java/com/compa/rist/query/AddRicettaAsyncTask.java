package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddRicettaAsyncTask extends AsyncTask<String, String, Integer> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String idRicetta = params[0];
        String nomeRicetta = params[1];
        String preparazioneRicetta = params[2];
        String prezzoRicetta = params[3];
        String portata = params[4];

        //Populating request parameters
        httpParams.put("idRicetta", idRicetta);
        httpParams.put("nomeRicetta", nomeRicetta);
        httpParams.put("preparazioneRicetta", preparazioneRicetta);
        httpParams.put("prezzoRicetta", prezzoRicetta);
        httpParams.put("portata", portata);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addRicetta.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final Integer result) {
    }

    public Integer getSuccess() {
        return success;
    }
}
