package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CercaQuantitaIngredienteAsyncTask extends AsyncTask<String, String, String> {

        private Integer success;
    private Integer quantitaIngrediente;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        Integer idIngrediente = Integer.parseInt(params[0]);

        //Populating request parameter
        httpParams.put("id", idIngrediente.toString());

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "cercaQuantità.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            quantitaIngrediente = jsonObject.getInt("quantitaIngrediente");
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

    public Integer getQuantitaIngrediente() {
        return quantitaIngrediente;
    }
}
