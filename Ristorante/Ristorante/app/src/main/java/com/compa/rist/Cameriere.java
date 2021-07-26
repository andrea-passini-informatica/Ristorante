package com.compa.rist;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.util.Log;

import com.compa.rist.GUI.CameriereActivity;
import com.compa.rist.GUI.ControllerActivity;
import com.compa.rist.query.AddPiattoOrdinazioneAsyncTask;
import com.compa.rist.query.CercaIdOrdinazioneAsyncTask;
import com.compa.rist.query.ControlloTavoloAsyncTask;
import com.compa.rist.query.CreaOrdinazioneAsyncTask;
import com.compa.rist.query.MenuAsyncTask;

import java.util.Calendar;

public class Cameriere extends Dipendente{

    private static Cameriere istance;

    private String menù;

    private Cameriere() {

        istance = Cameriere.this;

        Intent intentCameriere = new Intent(ControllerActivity.getInstance(), CameriereActivity.class);

        ControllerActivity.getInstance().startActivity(intentCameriere);
    }

    public String ElencoMenù() {
        MenuAsyncTask menùAsyncTask = new MenuAsyncTask();

        try {
            menùAsyncTask.execute();
        } catch (Exception e) {
            Log.e("menùAsyncTask", e.toString());
        }

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if(menùAsyncTask.getSuccess() == 1){
            menù = "MENU: \n" + menùAsyncTask.getRicetteNelMenù();
        } else {
            menù = "Error: menùAsyncTask";
        }

        return menù;
    }

    //Crea ordinazione e PiattiOrdinati c
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer CreaNuovaOrdinazione(Integer numeroTavolo) {
        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(numeroTavolo.toString())) {
            String data = getFormattedDate();

            ControlloTavoloAsyncTask controlloTavoloAsyncTask = new ControlloTavoloAsyncTask();

            try {
                controlloTavoloAsyncTask.execute(numeroTavolo.toString());
            } catch (Exception e) {
                Log.e("controlloTavolo", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (controlloTavoloAsyncTask.getSuccess() == 1) {
                CreaOrdinazioneAsyncTask creaOrdinazioneAsyncTask = new CreaOrdinazioneAsyncTask();

                try {
                    creaOrdinazioneAsyncTask.execute(data);
                } catch (Exception e) {
                    Log.e("CreaOrdinazione", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }

                if (creaOrdinazioneAsyncTask.getSuccess() == 1) {
                    CercaIdOrdinazioneAsyncTask cercaIdOrdinazioneAsyncTask = new CercaIdOrdinazioneAsyncTask();

                    try {
                        cercaIdOrdinazioneAsyncTask.execute(data);
                    } catch (Exception e) {
                        Log.e("CercaIdOrdinazione", e.toString());
                    }

                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                        Log.e("sleep", e.toString());
                    }

                    if (cercaIdOrdinazioneAsyncTask.getSuccess() == 1) {
                        return cercaIdOrdinazioneAsyncTask.getIdOrdinazione();
                    }
                }
            }
        }
        return null;
    }

    public boolean AddPiattoOrdinazione(Integer idPiatto, Integer quantitàPiatto, Integer idOrdinazione, Integer numeroTavolo) {

        if (idPiatto != null &&
                quantitàPiatto != null &&
                idOrdinazione != null) {

            //CERCA SE IL PIATTO ERA VERAMENTE NEL MENU
            if (menù.contains(idPiatto.toString())) {
                //VERIFICA DISPONIBILITA'
                Controller controller = new Controller();
                Boolean verificaDisponibilità = controller.verificaDisponibilità(idPiatto);

                if (verificaDisponibilità) {
                    AddPiattoOrdinazioneAsyncTask addPiattoOrdinazioneAsyncTask = new AddPiattoOrdinazioneAsyncTask();

                    try {
                        addPiattoOrdinazioneAsyncTask.execute(idPiatto.toString(),
                                quantitàPiatto.toString(),
                                idOrdinazione.toString(),
                                numeroTavolo.toString());
                    } catch (Exception e) {
                        Log.e("addPiattoOrdinazione", e.toString());
                    }

                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                        Log.e("sleep", e.toString());
                    }

                    if(addPiattoOrdinazioneAsyncTask.getSuccess() == 1 &&
                        controller.usoRisorse(idPiatto)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //DATA
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getFormattedDate() {
        //SimpleDateFormat called without pattern
        return new SimpleDateFormat().format(Calendar.getInstance().getTime());
    }

    public static Cameriere getInstance(){
        if(istance == null){
            istance = new Cameriere();
        }
        return istance;
    }

}
