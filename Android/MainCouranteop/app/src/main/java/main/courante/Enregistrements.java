package main.courante;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe de gestion de DPS
 * @author Florian Duchaine
 * @version 2.0
 */

public class Enregistrements {

    //Activité 2
    List<String> Manifestation;
    int NumeroPoste;

    //Activité 3
    List<String> Heures;

    //Activité 4
    List<String> ChefPoste;

    //Activité 5
    List<ArrayList<String>> Personnel = new ArrayList<ArrayList<String>>();
    ArrayList<String> Personne = new ArrayList<String>();

    //Activité 7
    String Immat;
    List<String> Lots;

    public int tour = 0;
    public int id = 0;


    public Enregistrements(List<String> manifestation, int numeroPoste, List<String> heures, List<String> chefPoste, List personnel, String immat, List<String> lots) {
        Manifestation = manifestation;
        NumeroPoste = numeroPoste;
        Heures = heures;
        ChefPoste = chefPoste;
        Personnel = personnel;
        Immat = immat;
        Lots = lots;
    }
    public Enregistrements(){
        Manifestation = getManifestation();
        NumeroPoste = getNumeroPoste();
        Heures = getHeures();
        ChefPoste = getChefPoste();
        Personnel = getPersonnel();
        Immat = getImmat();
        Lots = getLots();
        tour = getTour();
        id = getId();

    }

    public void setTour(){
        tour+=1;
    }

    public int getTour(){
        tour +=1;
        return tour;
    }

    public void setId(){
        id+=1;
    }

    public int getId(){
        return id;
    }

    /**
     * Informations sur le poste
     * @param Nature Nature du poste
     * @param Lieu Lieu physique du poste
     * @param Adresse Adresse du poste
     * @param Date Date du poste
     * @param Type Type de poste
     * @param Numero Numéro de poste
     * @return void
     */
    public void setManifestation(String Nature, String Lieu, String Adresse, String Date, String Type, String Numero){
        Manifestation = Arrays.asList(Nature, Lieu, Adresse, Date, Type, Numero);
    }

    /**
     * Ajout des heures
     * @param HeureDeb Heure de début du poste
     * @param HeureFin Heure de fin du poste
     * @param HeureDep Heure de départ du local
     * @param HeureRet Heure de retour au local
     * @return void
     */
    public void setHeures(String HeureDeb, String HeureFin, String HeureDep, String HeureRet){
        Heures = Arrays.asList(HeureDeb, HeureFin, HeureDep, HeureRet);
    }

    /**
     * Ajout du / des chef(s)
     * @param Dispo Nature du poste
     * @param Sect Lieu physique du poste
     * @param Equi Adresse du poste
     * @param Resp Date du poste
     * @return void
     */
    public void setChefPoste(String Dispo, String Sect, String Equi, String Resp){
        ChefPoste = Arrays.asList(Dispo,Sect, Equi, Resp);
    }

    /**
     * Ajout du personnel
     * @param pers Nature du poste
     * @return void
     */
    public void setPersonnel(String pers){
        Personne.add(pers);
        Personnel.add(Personne);
    }

    public void setLots(String lotA, String lotB, String lotC){
        Lots = Arrays.asList(lotA,lotB,lotC);
    }

    public void setImmat(String immat){
        Immat = immat;
    }

    public List<String> getLots(){
        return Lots;
    }

    public String getImmat(){
        return Immat;
    }

    /**
     * Récupération des informations du DPS
     * @return List<String>
     */
    public List<String> getManifestation() {
        return Manifestation;
    }

    /**
     *Récupération du numéro de DPS
     * @return int
     */
    public int getNumeroPoste() {
        return NumeroPoste;
    }

    /**
     * Récupération des heures
     * @return List<String>
     */
    public List<String> getHeures() {
        return Heures;
    }

    /**
     * Récupération du / des chef(s) du DPS
     * @return List<String>
     */
    public List<String> getChefPoste() {
        return ChefPoste;
    }

    /**
     * Récupération du personnel
     * @return List<ArrayList<String>>
     */
    public List<ArrayList<String>> getPersonnel() {
        return Personnel;
    }


}
