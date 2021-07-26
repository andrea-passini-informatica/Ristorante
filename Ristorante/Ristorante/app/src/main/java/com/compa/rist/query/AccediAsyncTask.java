package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * AsyncTask for ACCEDI
 *
 */
public class AccediAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;
    private String nome;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String id = params[0];
        String password = params[1];
        String titolo = params[2];

        //Populating request parameters
        httpParams.put("id", id);
        httpParams.put("password", password);
        httpParams.put("titolo", titolo);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "cercaDipendente.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            nome = jsonObject.getString("nome");

            return success.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final String result) {
    }

    public String getNome() {
        return nome;
    }

    public Integer getSuccess() {
        return success;
    }

}

