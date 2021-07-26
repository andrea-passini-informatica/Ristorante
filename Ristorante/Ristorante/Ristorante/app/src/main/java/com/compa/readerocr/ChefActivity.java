package com.compa.rist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChefActivity extends Activity {

    private Button btnIngredienti, btnRicette, btnMenù;

    private String nome;
    private String password;
    private String titolo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        Bundle b = getIntent().getExtras();

        nome = b.getString("nome");
        id = b.getString("id");
        password = b.getString("password");
        titolo = "chef";

        btnIngredienti = (Button) findViewById(R.id.buttonIngredienti);
        btnRicette = (Button) findViewById(R.id.buttonRicette);
        btnMenù = (Button) findViewById(R.id.buttonMenù);

        btnIngredienti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredienti(nome, password, id);
            }
        });

        btnRicette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ricette(nome, password, id);
            }
        });

        btnMenù.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menù(nome, password, id);
            }
        });

    }

    public void Ingredienti(String nome, String password, String id){
        Intent intentIngredienti = new Intent(ChefActivity.this, ChefIngredientiActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("id", id);
        b.putString("password", password);

        intentIngredienti.putExtras(b);
        startActivity(intentIngredienti);
    }

    public void Ricette(String nome, String password, String id){
        Intent intentRicette = new Intent(ChefActivity.this, ChefRicetteActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("id", id);
        b.putString("password", password);

        intentRicette.putExtras(b);
        startActivity(intentRicette);
    }

    public void Menù(String nome, String password, String id){
        Intent intentMenù = new Intent(ChefActivity.this, ChefMenuActivity.class);
        Bundle b = new Bundle();

        b.putString("nome", nome);
        b.putString("id", id);
        b.putString("password", password);

        intentMenù.putExtras(b);
        startActivity(intentMenù);
    }

}
