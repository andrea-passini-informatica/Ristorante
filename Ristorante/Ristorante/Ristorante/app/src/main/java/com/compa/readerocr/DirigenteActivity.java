package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.compa.rist.helper.CheckNetworkStatus;

public class DirigenteActivity extends Activity {

    private Button btnGestioneDipendenti;
    private Button btnResoconto;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirigente);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "dirigente";

        btnGestioneDipendenti = (Button) findViewById(R.id.buttonGesioneD);
        btnResoconto = (Button) findViewById(R.id.buttonResoconto);

        btnGestioneDipendenti.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Cambia ACTIVITY -> GESTIONE DIPENDENTI
                    Intent intentGestioneDipendenti = new Intent(DirigenteActivity.this, DirigenteDipendentiActivity.class);
                    Bundle b = new Bundle();

                    b.putString("nome", nome);
                    b.putString("id", id);
                    b.putString("password", password);

                    intentGestioneDipendenti.putExtras(b);
                    startActivity(intentGestioneDipendenti);

                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnResoconto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)   //Per gestire output query
            @Override
            public void onClick(View arg0) {
                //Controlla che la connesione web sai possibile e poi procede con ADD
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    //Cambia ACTIVITY -> RESOCONTO
                    Intent intentResoconto = new Intent(DirigenteActivity.this, DirigenteResocontoActivity.class);
                    Bundle b = new Bundle();

                    b.putString("nome", nome);
                    b.putString("id", id);
                    b.putString("password", password);

                    intentResoconto.putExtras(b);
                    startActivity(intentResoconto);

                } else {
                    Toast.makeText(DirigenteActivity.this,
                            "Impossibile connettersi ad internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
