package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModIngredienteAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String IDingredienteORIGINALE = params[0];
        String nomeIngrediente = params[1];
        String UDM = params[2];
        String IdFornitore = params[3];

        //Populating request parameters
        httpParams.put("IdIngrediente", IDingredienteORIGINALE);
        httpParams.put("nomeIngrediente", nomeIngrediente);
        httpParams.put("UDM", UDM);
        httpParams.put("IdFornitore", IdFornitore);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "modIngrediente.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
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
}

