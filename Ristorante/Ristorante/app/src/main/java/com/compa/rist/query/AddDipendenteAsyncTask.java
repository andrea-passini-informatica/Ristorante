package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDipendenteAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String iDdipendente = params[0];
        String nome = params[1];
        String password = params[2];
        String titolo = params[3];

        //Populating request parameters
        httpParams.put("iDdipendente", iDdipendente);
        httpParams.put("nome", nome);
        httpParams.put("password", password);
        httpParams.put("titolo", titolo);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addDipendente.php", "POST", httpParams);

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

