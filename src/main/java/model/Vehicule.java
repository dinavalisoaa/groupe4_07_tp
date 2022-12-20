/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.*;
import java.util.ArrayList;

/**
 *
 * @author dina
 */
@InfoDAO(table = "Vehicule")
public class Vehicule extends ObjectBDD {

    int id = -1;
    String nomChauffeur;
    String matricule;
    
@UnColumn
    String photo;
    @UnColumn
    double jour_restant;

    public int getId() {
        return id;
    }

    public ArrayList<Assurance> getAssurance() throws Exception {
        Assurance su = new Assurance();
        su.setVehiculeId(this.id);
        su.setEtat(1);
        ArrayList<Assurance> li = su.select(null);
        return li;
    }
//    public /

    public double getJour_restant() {
        return jour_restant;
    }

    public void setJour_restant(double jour_restant) {
        this.jour_restant = jour_restant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomChauffeur() {
        return nomChauffeur;
    }

    public void setPhoto(String nomChauffeur) {
        this.photo = nomChauffeur;
    }
  public String getPhoto() {
        return photo;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

}
