package com.compa.rist;

import android.content.Intent;
import android.util.Log;

import com.compa.rist.GUI.ControllerActivity;
import com.compa.rist.GUI.CuocoActivity;

import com.compa.rist.query.AddResocontoAsyncTask;
import com.compa.rist.query.PiattoCucinatoOrdinazioneAsyncTask;
import com.compa.rist.query.PopolaOrdinazioneAsyncTask;
import com.compa.rist.query.PopolaTutteAsyncTask;
import com.compa.rist.query.TrovaPrezzoAsyncTask;


public class Cuoco extends Dipendente{

    private static Cuoco istance;

    private Cuoco() {

        istance = Cuoco.this;

        Intent intentCuoco = new Intent(ControllerActivity.getInstance(), CuocoActivity.class);

        ControllerActivity.getInstance().startActivity(intentCuoco);
    }

    //+ PiattoServito( in IDricetta: integer, in IDordinazione: integer)
    public void PiattoServito(Integer idRicetta, Integer idOrdinazione) {

        PiattoCucinatoOrdinazioneAsyncTask piattoCucinatoOrdinazioneAsyncTask = new PiattoCucinatoOrdinazioneAsyncTask();

        try {
            piattoCucinatoOrdinazioneAsyncTask.execute(idRicetta.toString(), idOrdinazione.toString());
        } catch (Exception e) {
            Log.e("piattoCucinatoOrdinaz", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (piattoCucinatoOrdinazioneAsyncTask.getSuccess() == 1) {

            TrovaPrezzoAsyncTask trovaPrezzoAsyncTask = new TrovaPrezzoAsyncTask();

            try {
                trovaPrezzoAsyncTask.execute(idRicetta.toString());
            } catch (Exception e) {
                Log.e("trovaPrezzo", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (trovaPrezzoAsyncTask.getSuccess() == 1) {

                Integer prezzo = trovaPrezzoAsyncTask.getPrezzo();

                AddResocontoAsyncTask addResocontoAsyncTask = new AddResocontoAsyncTask();

                try {
                    addResocontoAsyncTask.execute(idRicetta.toString(), idOrdinazione.toString(), prezzo.toString());
                } catch (Exception e) {
                    Log.e("addResoconto", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }
            }
        }
    }

    public String ElencoOrdinazioni() {

            new PopolaTutteAsyncTask().execute();

            PopolaTutteAsyncTask popolaTutteAsyncTask = new PopolaTutteAsyncTask();

            try {
                popolaTutteAsyncTask.execute();
            } catch (Exception e) {
                Log.e("popolaOrdinazione", e.toString());
            }

            try {
                Thread.sleep(500);
            } catch (Exception e) {
                Log.e("popolaOrdinazione", e.toString());
            }

            if (popolaTutteAsyncTask.getSuccess() == 1) {
                return "ORDINAZIONI :\n" + popolaTutteAsyncTask.getPopolaIdOrdinazione();
            }
            return "ERROR: ElencoOrdinazioni";
    }

    public String popolaOrdinazione(Integer idOrdinazione) {

        PopolaOrdinazioneAsyncTask popolaOrdinazioneAsyncTask = new PopolaOrdinazioneAsyncTask();

        try {
            popolaOrdinazioneAsyncTask.execute(idOrdinazione.toString());
        } catch (Exception e) {
            Log.e("popolaOrdinazione", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (popolaOrdinazioneAsyncTask.getSuccess() == 1) {
            return "ID Oridinazione :\n" + popolaOrdinazioneAsyncTask.getRicetteOrdinazione();
        } else {
            return "ERROR: popolaOrdinazioneAsyncTask";
        }
    }


    public static Cuoco getInstance(){
        if(istance == null){
            istance = new Cuoco();
        }
        return istance;
    }

}