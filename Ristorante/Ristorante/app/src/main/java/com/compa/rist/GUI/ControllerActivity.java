package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.compa.rist.Controller;
import com.compa.rist.R;
import com.compa.rist.helper.CheckNetworkStatus;

public class ControllerActivity extends Activity {

    private static ControllerActivity istance;

    /**
     * ATTRIBUTI
     */
    private EditText txtUsername;
    private EditText txtPassword;

    private Button btnLogin;
    private Button btnCancella;

    private Spinner spnRole;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        istance = ControllerActivity.this;

        controller = new Controller(ControllerActivity.this);

        txtUsername = (EditText) findViewById(R.id.editUsername);
        txtPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnCancella = (Button) findViewById(R.id.buttonCancella);

        spnRole = (Spinner) findViewById(R.id.spinnerRole);

        //LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    Integer id = Integer.parseInt(txtUsername.getText().toString());
                    String password = txtPassword.getText().toString();
                    String titolo = spnRole.getSelectedItem().toString();

                    controller.Accedi(id, password, titolo);

                } else {
                    Toast.makeText(ControllerActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //CANCELLA CAMPI
        btnCancella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg1) {
                txtUsername.setText("");
                txtPassword.setText("");
            }
        });
    }

    public static ControllerActivity getInstance(){
        if(istance == null){
            istance = new ControllerActivity();
        }
        return istance;
    }

}
