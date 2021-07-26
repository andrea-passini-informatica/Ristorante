package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerificaDiposnibilitàAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private Integer[] ingDaRichiedere;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String idPiatto = params[0];

        //Populating request parameters
        httpParams.put("INTRIC_id", idPiatto);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "verificaDiposnibilità.php", "POST", httpParams);

        try {

            success = jsonObject.getInt("success");

            if( success == 0){
                JSONArray jsonArray = jsonObject.getJSONArray("array");

                //Leggi ogni elemento dell'ARRAY
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonCont = jsonArray.getJSONObject(i);
                    Integer idIng = jsonCont.getInt("ING_id");

                    ingDaRichiedere[i] = idIng;
                }
            }
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

    public Integer[] getIngDaRichiedere() {
        return ingDaRichiedere;
    }
}

