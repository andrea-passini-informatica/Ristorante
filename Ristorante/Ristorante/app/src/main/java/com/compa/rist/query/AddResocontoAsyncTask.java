package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddResocontoAsyncTask extends AsyncTask<String, String, String> {

    Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        Integer idPiatto = Integer.parseInt(params[0]);
        Integer idOrdinazione = Integer.parseInt(params[1]);
        Integer ricavo = Integer.parseInt(params[2]);

        httpParams.put("INTRIC_id", idPiatto.toString());
        httpParams.put("ORD_id", idOrdinazione.toString());
        httpParams.put("Ricavo", ricavo.toString());

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addResoconto.php", "POST", httpParams);

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
