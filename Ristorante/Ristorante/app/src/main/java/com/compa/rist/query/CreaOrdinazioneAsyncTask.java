package com.compa.rist.query;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.compa.rist.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreaOrdinazioneAsyncTask extends AsyncTask<String, String, String> {

    private Integer success;

    @Override
    protected void onPreExecute() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();

        String data = params[0];

        //Populating request parameters
        httpParams.put("data", data);

        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "http://andreapa98.com/" + "addOrdinazione.php", "POST", httpParams);

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

