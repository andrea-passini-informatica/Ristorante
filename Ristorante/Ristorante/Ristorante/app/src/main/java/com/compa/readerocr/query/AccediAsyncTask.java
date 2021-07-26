package com.compa.rist.query;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.compa.rist.ControllerActivity;
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
    private ProgressDialog progressDialog = null;
    private Context context = null;
    private ControllerActivity controllerActivity;

    public AccediAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(this.context);

        progressDialog.setTitle("AsyncTask");
        progressDialog.setMessage("query");
        progressDialog.setCancelable(false);

        progressDialog.show();
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
            Integer success = jsonObject.getInt("success");
            String nome = jsonObject.getString("nome");
            controllerActivity.setQueryResult(success, nome);
            if (success == 1){
                progressDialog.setMessage("!1111111111111111111111111111111111111111111");
            }
            return success.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(final String result) {
        progressDialog.dismiss();
    }

}
