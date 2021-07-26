package com.compa.rist;

import android.content.Intent;

import android.util.Log;

import com.compa.rist.GUI.ControllerActivity;
import com.compa.rist.GUI.DirigenteActivity;
import com.compa.rist.query.AddDipendenteAsyncTask;
import com.compa.rist.query.AddFornitoreAsyncTask;
import com.compa.rist.query.ElencoDipendentiAsyncTask;
import com.compa.rist.query.ElencoFornitoriAsyncTask;
import com.compa.rist.query.ElimDipendentiAsyncTask;
import com.compa.rist.query.ElimFornitoreAsyncTask;
import com.compa.rist.query.GuadagnoTotaleAsyncTask;
import com.compa.rist.query.ResocontoPiattiServitiAsyncTask;

public class Dirigente extends Dipendente{

    private static Dirigente istance;

    private Dirigente() {

        istance = Dirigente.this;

        Intent intentDirigente = new Intent(ControllerActivity.getInstance(), DirigenteActivity.class);

        ControllerActivity.getInstance().startActivity(intentDirigente);
    }

    //ELENCO DIPENDENTI
    public boolean ElimDipendente(Integer IdDipendente) {

        //new ElimDipendentiAsyncTask().execute(IDdipendente.toString());

        ElimDipendentiAsyncTask elimDipendentiAsyncTask = new ElimDipendentiAsyncTask();

        try {
            elimDipendentiAsyncTask.execute(IdDipendente.toString());
        } catch (Exception e) {
            Log.e("cercaIdIngrediente", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (elimDipendentiAsyncTask.getSuccess() == 1) {
            // new ElencoDipendentiAsyncTask().execute();

        }

        return false;
    }

    public boolean AddDipendente(Integer iDdipendente, String nome, String password, String titolo)         {

        AddDipendenteAsyncTask addDipendenteAsyncTask = new AddDipendenteAsyncTask();

        try {
            addDipendenteAsyncTask.execute(iDdipendente.toString(), nome, password, titolo);
        } catch (Exception e) {
            Log.e("addDipendenteAsyncTask", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (addDipendenteAsyncTask.getSuccess() == 1) {
            return true;
        }

        return false;
    }

    //GESTIONE FORNITORI
    public String aggiornaFornitore(){

        ElencoFornitoriAsyncTask elencoFornitoriAsyncTask = new ElencoFornitoriAsyncTask();

        try {
            elencoFornitoriAsyncTask.execute();
        } catch (Exception e) {
            Log.e("elencoIngredienteForn", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (elencoFornitoriAsyncTask.getSuccess() == 1) {

            return elencoFornitoriAsyncTask.getElencoIngredienteFornitore();
        }

        return "ERROR: aggiornaIngredienteFornitore";
    }

    public boolean AddFornitore(Integer idFornitore, String nomeFornitore){

        AddFornitoreAsyncTask addFornitoreAsyncTask = new AddFornitoreAsyncTask();

        try {
            addFornitoreAsyncTask.execute(idFornitore.toString(), nomeFornitore);
        } catch (Exception e) {
            Log.e("elencoIngredienteForn", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (addFornitoreAsyncTask.getSuccess() == 1) {

            return true;
        }

        return false;
    }

    public boolean ElimFornitore(Integer idFornitore){

        ElimFornitoreAsyncTask elimFornitoreAsyncTask = new ElimFornitoreAsyncTask();

        try {
            elimFornitoreAsyncTask.execute(idFornitore.toString());
        } catch (Exception e) {
            Log.e("elencoIngredienteForn", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (elimFornitoreAsyncTask.getSuccess() == 1) {

            return true;
        }

        return false;
    }

    public String popolaElencoDipendenti() {

        ElencoDipendentiAsyncTask elencoDipendentiAsyncTask = new ElencoDipendentiAsyncTask();

        try {
            elencoDipendentiAsyncTask.execute();
        } catch (Exception e) {
            Log.e("elencoDipendenti", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (elencoDipendentiAsyncTask.getSuccess() == 1) {
            return "DIPENDENTI: " + elencoDipendentiAsyncTask.getElencoDipendenti();
        }
        return "ERROR: elencoDipendentiAsyncTask";
    }

    public String resocontoPiattiServiti() {

        ResocontoPiattiServitiAsyncTask resocontoPiattiServitiAsyncTask = new ResocontoPiattiServitiAsyncTask();

        try {
            resocontoPiattiServitiAsyncTask.execute();
        } catch (Exception e) {
            Log.e("resocontoPiattiServiti", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (resocontoPiattiServitiAsyncTask.getSuccess() == 1) {
            return "PIATTI SERVITI: " + resocontoPiattiServitiAsyncTask.getUse();
        }
        return "ERROR: resocontoPiattiServitiAsyncTask";
    }

    public String guadagnoTotale() {

        GuadagnoTotaleAsyncTask guadagnoTotaleAsyncTask = new GuadagnoTotaleAsyncTask();

        try {
            guadagnoTotaleAsyncTask.execute();
        } catch (Exception e) {
            Log.e("guadagnoTotale", e.toString());
        }

        try {
            Thread.sleep(250);
        } catch (Exception e) {
            Log.e("sleep", e.toString());
        }

        if (guadagnoTotaleAsyncTask.getSuccess() == 1) {
            return "GUADAGNO TOTALE :" + guadagnoTotaleAsyncTask.getUse();
        }
        return "ERROR: guadagnoTotaleAsyncTask";
    }



    public static Dirigente getInstance(){
        if(istance == null){
            istance = new Dirigente();
        }
        return istance;
    }

}
