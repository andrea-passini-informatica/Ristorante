package com.compa.rist.query;

import android.os.AsyncTask;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GuadagnoTotaleAsyncTask extends AsyncTask<String, String, String> {

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
                "http://andreapa98.com/" + "guadagnoTotale.php", "POST", httpParams);

        try {
            success = jsonObject.getInt("success");
            use = jsonObject.getString("guadagno");
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
