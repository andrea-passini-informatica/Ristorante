package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResocontoPiattiServitiAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private String use;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "resocontoPiattiServiti.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");

            //GESTIONE ARRAY
            JSONArray jsonArray = jsonObject.getJSONArray("array");

            //Leggi ogni elemento dell'ARRAY e crea una stringa unica appendendo un elemento dopo l'altro
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonCont = jsonArray.getJSONObject(i);
                String nome = jsonCont.getString("nome");
                Integer INTRIC_id = jsonCont.getInt("INTRIC_id");

                buffer.append(nome + " " + INTRIC_id + "\n");
            }

            use = buffer.toString();
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

    public String getUse() {
        return use;
    }
}

