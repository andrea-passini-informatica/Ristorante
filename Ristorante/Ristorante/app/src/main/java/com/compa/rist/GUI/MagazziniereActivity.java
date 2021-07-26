package com.compa.rist.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compa.rist.Magazziniere;
import com.compa.rist.R;

public class MagazziniereActivity extends Activity {

    private EditText txtNomeIngrediente, txtQuantità;

    private Button btnAggiungi;

    private Magazziniere magazziniere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazziniere);

        magazziniere = Magazziniere.getInstance();

        txtNomeIngrediente = (EditText) findViewById(R.id.textIngrediente);
        txtQuantità = (EditText) findViewById(R.id.textQuantità);

        btnAggiungi = (Button) findViewById(R.id.buttonAggiungiRisorsa);

        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer idIngrediente = Integer.parseInt(txtNomeIngrediente.getText().toString());

                Integer quantità = Integer.parseInt(txtQuantità.getText().toString());

                if(magazziniere.AddRisorse(quantità, idIngrediente)){
                    SvuotaCampi();
                    Toast.makeText(MagazziniereActivity.this,
                            "AddRisorse", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MagazziniereActivity.this,
                            "Error: AddRisorse", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SvuotaCampi() {
        txtNomeIngrediente.setText("");
        txtQuantità.setText("");
    }

}
