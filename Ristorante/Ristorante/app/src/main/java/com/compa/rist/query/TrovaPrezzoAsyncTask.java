package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * AsyncTask for FindPrezzo
 */
public class TrovaPrezzoAsyncTask extends AsyncTask<String, String, String> {

    private Integer  prezzo, success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        Integer idPiatto = Integer.parseInt(params[0]);

        httpParams.put("idPiatto", idPiatto.toString());

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "trovaPrezzo.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            prezzo = jsonObject.getInt("prezzo");

            return success.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final String result) {
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public Integer getSuccess() {
        return success;
    }
}
