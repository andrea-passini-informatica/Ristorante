package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ElimRicettaMenuAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

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
            return success.toString();
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
