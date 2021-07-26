package com.compa.rist;

import android.content.Intent;
import android.util.Log;

import com.compa.rist.GUI.ChefActivity;
import com.compa.rist.GUI.ControllerActivity;

import com.compa.rist.query.AddIngredienteAsyncTask;
import com.compa.rist.query.AddIngredienteRicettaAsyncTask;
import com.compa.rist.query.AddRicettaAsyncTask;
import com.compa.rist.query.AddRicettaMenuAsyncTask;
import com.compa.rist.query.CercaIdRicettaAsyncTask;
import com.compa.rist.query.ElencoIngredientiAsyncTask;
import com.compa.rist.query.ElencoIngredientiRicettaAsyncTask;
import com.compa.rist.query.ElimIngredienteAsyncTask;
import com.compa.rist.query.ElimIngredientiRicettaAsyncTask;
import com.compa.rist.query.ElimRicettaAsyncTask;
import com.compa.rist.query.ElimRicettaMenuAsyncTask;
import com.compa.rist.query.IdRicetteAsyncTask;
import com.compa.rist.query.IngredienteInRicettaAsyncTask;
import com.compa.rist.query.MenuAsyncTask;
import com.compa.rist.query.ModIngredienteAsyncTask;
import com.compa.rist.query.ModRicettaAsyncTask;


public class Chef extends Dipendente{

    private static Chef istance;

    private String menu;

    private Chef() {

        istance = Chef.this;

        Intent intentChef = new Intent(ControllerActivity.getInstance(), ChefActivity.class);

        ControllerActivity.getInstance().startActivity(intentChef);
    }

    //INGREDIENTI

    public boolean AddIngrediente(Integer IdIngrediente, String nomeIngrediente, String UDM, Integer IdFornitore) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IdIngrediente) &&
                !STRING_EMPTY.equals(nomeIngrediente) &&
                !STRING_EMPTY.equals(UDM) &&
                !STRING_EMPTY.equals(IdFornitore)) {

            AddIngredienteAsyncTask addIngredienteAsyncTask = new AddIngredienteAsyncTask();

            try {
                addIngredienteAsyncTask.execute(IdIngrediente.toString(), nomeIngrediente, UDM, IdFornitore.toString());
            } catch (Exception e) {
                Log.e("addIngrediente", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (addIngredienteAsyncTask.getSuccess() == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean ModIngrediente(Integer IDingredienteORIGINALE, String nomeIngrediente, String UDM, Integer IdFornitore) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IDingredienteORIGINALE) &&
                !STRING_EMPTY.equals(nomeIngrediente) &&
                !STRING_EMPTY.equals(UDM) &&
                !STRING_EMPTY.equals(IdFornitore)) {

            ModIngredienteAsyncTask modIngredienteAsyncTask = new ModIngredienteAsyncTask();

            try {
                modIngredienteAsyncTask.execute(IDingredienteORIGINALE.toString(), nomeIngrediente, UDM, IdFornitore.toString());
            } catch (Exception e) {
                Log.e("modIngrediente", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (modIngredienteAsyncTask.getSuccess() == 1) {
                return true;
            }

        }
        return false;
    }

    public boolean ElimIngrediente(Integer IDingrediente) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IDingrediente)) {

            ElimIngredienteAsyncTask elimIngredienteAsyncTask = new ElimIngredienteAsyncTask();

            try {
                elimIngredienteAsyncTask.execute(IDingrediente.toString());
            } catch (Exception e) {
                Log.e("elimIngrediente", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (elimIngredienteAsyncTask.getSuccess() == 1) {
                return true;
            }
        }
        return false;
    }


    //MENU

    //Trova id delle ricette presenti nel menu
    public String PopolaMenu() {

            MenuAsyncTask menuAsyncTask = new MenuAsyncTask();

            try {
                menuAsyncTask.execute();
            } catch (Exception e) {
                Log.e("menuAsyncTask", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (menuAsyncTask.getSuccess() == 1) {
                menu = menuAsyncTask.getRicetteNelMenù();
                return  "MENU': \n" + menuAsyncTask.getRicetteNelMenù();
            } else {
                return "ERROR: PopolaMenu";
            }
    }

    //Trova tutte le ricette in elenco
    public String PopolaIdRicette() {

            IdRicetteAsyncTask idRicetteAsyncTask = new IdRicetteAsyncTask();

            try {
                idRicetteAsyncTask.execute();
            } catch (Exception e) {
                Log.e("idRicette", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (idRicetteAsyncTask.getSuccess() == 1) {
                return "RICETTE : \n" + idRicetteAsyncTask.getElencoIdRicette();
            } else {
                return "ERROR: PopolaIdRicette";
            }
    }

    public boolean AddRicettaMenù(Integer IdRicetta) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IdRicetta.toString())) {

            //Controlla che la ricetta esista
            CercaIdRicettaAsyncTask cercaIdRicettaAsyncTask = new CercaIdRicettaAsyncTask();

            try {
                cercaIdRicettaAsyncTask.execute(IdRicetta.toString());
            } catch (Exception e) {
                Log.e("cercaIdRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (cercaIdRicettaAsyncTask.getSuccess() == 1) {

                //Controlla che la ricetta non sia già presente nel Menù
                if (menu.contains(IdRicetta.toString())) {

                    Controller controller = new Controller();

                    Boolean verificaDisponibilità = controller.verificaDisponibilità(IdRicetta);

                    if(verificaDisponibilità) {
                        //AGGIUNGI ricetta al menù
                        AddRicettaMenuAsyncTask addRicettaMenuAsyncTask = new AddRicettaMenuAsyncTask();

                        try {
                            addRicettaMenuAsyncTask.execute(IdRicetta.toString());
                        } catch (Exception e) {
                            Log.e("addRicettaMenu", e.toString());
                        }

                        try {
                            Thread.sleep(250);
                        } catch (Exception e) {
                            Log.e("sleep", e.toString());
                        }

                        if (addRicettaMenuAsyncTask.getSuccess() == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean ElimRicettaMenù(Integer IdRicetta) {
        String STRING_EMPTY = "";
        if (!STRING_EMPTY.equals(IdRicetta.toString())) {

            //Controlla che la ricetta non sia nel MENU

            if (!menu.contains(IdRicetta.toString())) {

                // new ChefActivity.ElimRicettaMenuAsyncTask().execute(IdRicetta.toString());
                ElimRicettaMenuAsyncTask elimRicettaMenuAsyncTask = new ElimRicettaMenuAsyncTask();

                try {
                    elimRicettaMenuAsyncTask.execute(IdRicetta.toString());
                } catch (Exception e) {
                    Log.e("cercaIdRicettaMenu", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }

                if (elimRicettaMenuAsyncTask.getSuccess() == 1) {
                    return true;
                }
            }
        }

        return false;
    }


    //RICETTE

    public boolean AddRicetta(Integer idRicetta,
                              String nomeRicetta,
                              String preparazioneRicetta,
                              Integer prezzoRicetta,
                              Integer portata) {

        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idRicetta) &&
                !STRING_EMPTY.equals(nomeRicetta) &&
                !STRING_EMPTY.equals(preparazioneRicetta) &&
                !STRING_EMPTY.equals(prezzoRicetta) &&
                !STRING_EMPTY.equals(portata)) {

            AddRicettaAsyncTask addRicettaAsyncTask = new AddRicettaAsyncTask();

            try {
                addRicettaAsyncTask.execute(idRicetta.toString(),
                        nomeRicetta,
                        preparazioneRicetta,
                        prezzoRicetta.toString(),
                        portata.toString());
            } catch (Exception e) {
                Log.e("addRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (addRicettaAsyncTask.getSuccess() == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean ModRicetta(Integer idRicetta,
                            String nomeRicetta,
                            String preparazioneRicetta,
                            Integer prezzoRicetta,
                            Integer portata) {

        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idRicetta) &&
                !STRING_EMPTY.equals(nomeRicetta) &&
                !STRING_EMPTY.equals(preparazioneRicetta) &&
                !STRING_EMPTY.equals(prezzoRicetta) &&
                !STRING_EMPTY.equals(portata)) {

            //Modifico l'header della ricetta
            ModRicettaAsyncTask modRicettaAsyncTask = new ModRicettaAsyncTask();

            try {
                modRicettaAsyncTask.execute(idRicetta.toString(),
                                            nomeRicetta,
                                            preparazioneRicetta,
                                            prezzoRicetta.toString(),
                                            portata.toString());
            } catch (Exception e) {
                Log.e("modRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(modRicettaAsyncTask.getSuccess() == 1){
                return true;
            }


        } else if (!STRING_EMPTY.equals(idRicetta) &&
                STRING_EMPTY.equals(nomeRicetta) &&
                STRING_EMPTY.equals(preparazioneRicetta) &&
                STRING_EMPTY.equals(prezzoRicetta) &&
                STRING_EMPTY.equals(portata)) {

            //Con solo ID, vai diretto a modifica ingredienti
            //senza modifcare l'header

            return true;

        }

        return false;
    }

    public boolean ElimRicetta(Integer idRicetta) {
        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idRicetta)) {

            new ElimRicettaAsyncTask().execute(idRicetta.toString());

            ElimRicettaAsyncTask elimRicettaAsyncTask = new ElimRicettaAsyncTask();

            try {
                elimRicettaAsyncTask.execute(idRicetta.toString());
            } catch (Exception e) {
                Log.e("modRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(elimRicettaAsyncTask.getSuccess() == 1){
                return true;
            }
        }

        return false;
    }

    //Carica a schermo tutti gli ingredienti in elenco (Id, Nome)
    public String Ingredienti() {

            ElencoIngredientiAsyncTask elencoIngredientiAsyncTask = new ElencoIngredientiAsyncTask();

            try {
                elencoIngredientiAsyncTask.execute();
            } catch (Exception e) {
                Log.e("modRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(elencoIngredientiAsyncTask.getSuccess() == 1){
                return "INGREDIENTI: \n" + elencoIngredientiAsyncTask.getElencoIngredienti();
            } else {
                return "ERROR: elencoIngredientiAsyncTask";
            }
    }


    public boolean AddIngredienteRicetta(Integer idRicetta, Integer idIngrediente, Integer quantita) {
        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idRicetta.toString()) &&
                !STRING_EMPTY.equals(idIngrediente.toString())) {

            //Controlla che l'ingrediente non sia già inserito
            IngredienteInRicettaAsyncTask ingredienteInRicettaAsyncTask = new IngredienteInRicettaAsyncTask();

            try {
                ingredienteInRicettaAsyncTask.execute(idRicetta.toString(), idIngrediente.toString());
            } catch (Exception e) {
                Log.e("ingredienteInRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(ingredienteInRicettaAsyncTask.getSuccess() == 1){
                //Se non è presente nella ricetta lo aggiunge
                //new AddIngredienteRicettaAsyncTask().execute(idRicetta.toString(), idIngrediente.toString());

                AddIngredienteRicettaAsyncTask addIngredienteRicettaAsyncTask = new AddIngredienteRicettaAsyncTask();

                try {
                    addIngredienteRicettaAsyncTask.execute(idRicetta.toString(), idIngrediente.toString(), quantita.toString());
                } catch (Exception e) {
                    Log.e("addIngredienteRicetta", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }

                if(addIngredienteRicettaAsyncTask.getSuccess() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ElimIngredientiRicetta(Integer idRicetta, Integer idIngrediente) {

        String STRING_EMPTY = "";

        if (!STRING_EMPTY.equals(idRicetta.toString()) &&
                !STRING_EMPTY.equals(idIngrediente.toString())) {

            //Controlla che l'ingrediente non sia già inserito
            IngredienteInRicettaAsyncTask ingredienteInRicettaAsyncTask = new IngredienteInRicettaAsyncTask();

            try {
                ingredienteInRicettaAsyncTask.execute(idRicetta.toString(), idIngrediente.toString());
            } catch (Exception e) {
                Log.e("ingredienteInRicetta", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if(ingredienteInRicettaAsyncTask.getSuccess() == 0){
                //Se è presente nella ricetta lo ELIMINA

                ElimIngredientiRicettaAsyncTask elimIngredientiRicettaAsyncTask = new ElimIngredientiRicettaAsyncTask();

                try {
                    elimIngredientiRicettaAsyncTask.execute(idRicetta.toString(), idIngrediente.toString());
                } catch (Exception e) {
                    Log.e("elimIngredientiRicetta", e.toString());
                }

                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }

                if(elimIngredientiRicettaAsyncTask.getSuccess() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    //Trova gli ingredienti nella ricetta
    public String popolaIngredientiRicetta(Integer idRicetta) {
            ElencoIngredientiRicettaAsyncTask elencoIngredientiRicettaAsyncTask = new ElencoIngredientiRicettaAsyncTask();

            try {
                elencoIngredientiRicettaAsyncTask.execute(idRicetta.toString());
            } catch (Exception e) {
                Log.e("elencoIngredientiR", e.toString());
            }

            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

            if (elencoIngredientiRicettaAsyncTask.getSuccess() == 1) {
                return "INGREDIENTI RICETTA: \n" + elencoIngredientiRicettaAsyncTask.getElencoIngredientiRicetta();
            } else {
                return "ERROR: elencoIngredientiRicettaAsyncTask";
            }
    }

    public static Chef getInstance(){
        if(istance == null){
            istance = new Chef();
        }
        return istance;
    }

}
