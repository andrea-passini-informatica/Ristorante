package com.compa.rist;

import android.util.Log;
import android.widget.Toast;

import com.compa.rist.GUI.ControllerActivity;
import com.compa.rist.query.AccediAsyncTask;
import com.compa.rist.query.AddOrdineFornitoreAsyncTask;
import com.compa.rist.query.ElencoRichiesteIngAsyncTask;
import com.compa.rist.query.UsoRisorseAsyncTask;
import com.compa.rist.query.VerificaDiposnibilitàAsyncTask;

public class Controller {

    private Dipendente dipendente;

    private ControllerActivity controllerActivity;

    public Controller(ControllerActivity c){
        controllerActivity = c;
    }


    public Controller(){

    }

    public void Accedi(Integer id, String password, String titolo) {
        String STRING_EMPTY = "";

        if(!STRING_EMPTY.equals(id.toString()) &&
            !STRING_EMPTY.equals(password)  &&
            !STRING_EMPTY.equals(titolo))   {

            AccediAsyncTask accediAsyncTask = new AccediAsyncTask();

            //Esecuzione della QUERY
            try {
                accediAsyncTask.execute(id.toString(), password, titolo);
            } catch (Exception e) {
                Toast.makeText(controllerActivity,
                        e.toString(), Toast.LENGTH_LONG).show();
            }

            //Metto in pausa il Thread principale aspettando che la QUERY si concluda
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Toast.makeText(controllerActivity,
                        e.toString(), Toast.LENGTH_LONG).show();
            }

            //Ottengo i risultati della QUERY
            if (accediAsyncTask.getSuccess() == 1) {

                //Con i dati ottenuti chiamo il metodo Accedi() di dipendente
                dipendente = new Dipendente();
                dipendente.Accedi(password, id, accediAsyncTask.getNome(), titolo);

            } else {
                Toast.makeText(controllerActivity,
                        "Impossibile accedere", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Boolean verificaDisponibilità(Integer IDricetta) {

        //CERCA NELLA RICETTA GLI INGREDIENTI ED IL LORO DOSAGGIO
        //CERCANE LA DISPONIBILITA' NELLE PROVVIGIONI
        VerificaDiposnibilitàAsyncTask verificaDiposnibilitàAsyncTask = new VerificaDiposnibilitàAsyncTask();

        try{
            verificaDiposnibilitàAsyncTask.execute(IDricetta.toString());
        } catch(Exception e){
            Log.e("VerificaDisponibilità", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (verificaDiposnibilitàAsyncTask.getSuccess() == 1) {
            return true;
        } else {
            //Fa richiesta al fornitore
            String elencoRichiesteIng = null;
            ElencoRichiesteIngAsyncTask elencoRichiesteIngAsyncTask = new ElencoRichiesteIngAsyncTask();

            try{
                elencoRichiesteIngAsyncTask.execute();
            } catch(Exception e){
                Log.e("elencoRichiesteIng", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(elencoRichiesteIngAsyncTask.getSuccess() == 1){
                elencoRichiesteIng = elencoRichiesteIngAsyncTask.getelencoRichiesteIng();
            }

            Integer[] ingDaRichiedere = verificaDiposnibilitàAsyncTask.getIngDaRichiedere();
            Integer i;
            for (i=0; i<ingDaRichiedere.length; i++){

                if(elencoRichiesteIng.contains(ingDaRichiedere[i].toString())){
                    //Se la richiesta dello stesso ingrediente non è già stata inviata
                    AddOrdineFornitoreAsyncTask addOrdineFornitoreAsyncTask = new AddOrdineFornitoreAsyncTask();

                    try{
                        addOrdineFornitoreAsyncTask.execute(ingDaRichiedere[i].toString());
                    } catch(Exception e){
                        Log.e("addOrdineFornitore", e.toString());
                    }

                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                        Log.e("sleep", e.toString());
                    }

                    if(addOrdineFornitoreAsyncTask.getSuccess() == 1){

                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public Boolean usoRisorse(Integer idPiatto) {

        new UsoRisorseAsyncTask().execute(idPiatto.toString());

        UsoRisorseAsyncTask usoRisorseAsyncTask = new UsoRisorseAsyncTask();

        try{
            usoRisorseAsyncTask.execute(idPiatto.toString());
        } catch(Exception e){
            Log.e("usoRisorse", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (usoRisorseAsyncTask.getSuccess() == 1) {
            return true;
        }
        return false;
    }

}
