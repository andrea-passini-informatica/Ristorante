package com.compa.rist;

public class Dipendente {

    private String nome;
    private Integer id;
    private String password;

    public void Accedi(String password, Integer id, String nome, String titolo) {

        this.nome = nome;
        this.id = id;
        this.password = password;

        String STRINGEMPTY = "";
        if (!password.equals(STRINGEMPTY) &&
                !id.toString().equals(STRINGEMPTY) &&
                !titolo.equals(STRINGEMPTY)) {

            switch (titolo) {
                case "magazziniere":
                    Magazziniere.getInstance();
                    break;
                case "cameriere":
                    Cameriere.getInstance();
                    break;
                case "cuoco":
                    Cuoco.getInstance();
                    break;
                case "chef":
                    Chef.getInstance();
                    break;
                case "dirigente":
                    Dirigente.getInstance();
                    break;
            }
        }
    }

}
