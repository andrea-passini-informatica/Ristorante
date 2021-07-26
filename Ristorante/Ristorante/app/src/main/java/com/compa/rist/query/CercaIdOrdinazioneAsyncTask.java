package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CercaIdOrdinazioneAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private Integer idOrdinazione;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String data = params[0];

        //Populating request parameters
        httpParams.put("data", data);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "cercaIdOrdinazione.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            idOrdinazione = jsonObject.getInt("id");

            return success.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final String result) {
        if (result.equals("1")) {

        } else {
            idOrdinazione = null;
        }
    }

    public Integer getSuccess() {
        return success;
    }

    public Integer getIdOrdinazione() {
        return idOrdinazione;
    }
}

