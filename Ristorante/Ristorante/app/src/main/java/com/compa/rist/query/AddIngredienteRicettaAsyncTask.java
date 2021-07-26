package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddIngredienteRicettaAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        Integer idRicetta = Integer.parseInt(params[0]);
        Integer idIngrediente = Integer.parseInt(params[1]);
        Integer quantita = Integer.parseInt(params[2]);

        //Populating request parameters
        httpParams.put("idRicetta", idRicetta.toString());
        httpParams.put("idIngrediente", idIngrediente.toString());
        httpParams.put("quantita", quantita.toString());

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addIngredienteRicetta.php", "POST", httpParams);

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

