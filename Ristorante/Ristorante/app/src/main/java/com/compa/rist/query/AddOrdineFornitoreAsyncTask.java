package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddOrdineFornitoreAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String idFornitore = params[0];
        String idIngrediente = params[1];

        //Populating request parameters
        httpParams.put("FR_id", idFornitore);
        httpParams.put("ING_id", idIngrediente);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "AddOrdineFornitore.php", "POST", httpParams);

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
