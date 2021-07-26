package com.compa.rist;

import android.content.Intent;
import android.util.Log;

import com.compa.rist.GUI.ControllerActivity;
import com.compa.rist.GUI.MagazziniereActivity;
import com.compa.rist.query.AddRisorseAsyncTask;
import com.compa.rist.query.CercaIdIngredienteAsyncTask;
import com.compa.rist.query.CercaQuantitaIngredienteAsyncTask;
import com.compa.rist.query.ElimRichiestaFornitoreAsyncTask;

public class Magazziniere extends Dipendente{

    private static Magazziniere istance;

    private Magazziniere() {

        istance = Magazziniere.this;

        Intent intentMagazziniere = new Intent(ControllerActivity.getInstance(), MagazziniereActivity.class);

        ControllerActivity.getInstance().startActivity(intentMagazziniere);

    }

    public boolean AddRisorse(Integer quantità, Integer idIngrediente)  {

        if(quantità != null && idIngrediente != null){

            CercaIdIngredienteAsyncTask cercaIdIngredienteAsyncTask = new CercaIdIngredienteAsyncTask();

            try {
                cercaIdIngredienteAsyncTask.execute(idIngrediente.toString());
            } catch (Exception e) {
                Log.e("cercaIdIngrediente", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (cercaIdIngredienteAsyncTask.getSuccess() == 1) {

                CercaQuantitaIngredienteAsyncTask cercaQuantitaIngredienteAsyncTask = new CercaQuantitaIngredienteAsyncTask();

                try {
                    cercaQuantitaIngredienteAsyncTask.execute(idIngrediente.toString());
                } catch (Exception e) {
                    Log.e("cercaQuantitaIngredient", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }

                if (cercaQuantitaIngredienteAsyncTask.getSuccess() == 1){

                    AddRisorseAsyncTask addRisorseAsyncTask = new AddRisorseAsyncTask();

                    try {
                        addRisorseAsyncTask.execute(quantità.toString(),
                                idIngrediente.toString(),
                                cercaQuantitaIngredienteAsyncTask.getQuantitaIngrediente().toString());
                    } catch (Exception e) {
                        Log.e("AddRisorse", e.toString());
                    }

                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                        Log.e("sleep", e.toString());
                    }

                    if(addRisorseAsyncTask.getSuccess() == 1){
                        //elimina ing dalle richieste fornitori

                        ElimRichiestaFornitoreAsyncTask elimRichiestaFornitoreAsyncTask = new ElimRichiestaFornitoreAsyncTask();

                        try {
                            elimRichiestaFornitoreAsyncTask.execute(
                                    idIngrediente.toString());
                        } catch (Exception e) {
                            Log.e("AddRisorse", e.toString());
                        }

                        try {
                            Thread.sleep(250);
                        } catch (Exception e) {
                            Log.e("sleep", e.toString());
                        }

                        if(elimRichiestaFornitoreAsyncTask.getSuccess() == 1){
                            return true;
                        }

                    }

                }
            }
        }
        return false;
    }

    public static Magazziniere getInstance(){
        if(istance == null){
            istance = new Magazziniere();
        }
        return istance;
    }
}
