/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.InfoDAO;
import DAO.ObjectBDD;
import java.util.ArrayList;

/**
 *
 * @author dina
 */

@InfoDAO(table = "Kilometrage")
public class Kilometrage extends ObjectBDD {
    int id=-1;
    int vehiculeId=-1;
    String daty;
    double debut=-1.0;   
    double fin=-1.0;
    public Vehicule getVehicules() throws Exception{
        Vehicule ray=new Vehicule();
        ray.setId(this.vehiculeId);
        ArrayList rays=ray.select(null);
        return ((Vehicule)rays.get(0));
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public String getDaty() {
        return daty;
    }

    public void setDaty(String daty) {
        this.daty = daty;
    }

    public double getDebut() {
        return debut;
    }

    public void setDebut(double debut) {
        this.debut = debut;
    }

    public double getFin() {
        return fin;
    }

    public void setFin(double fin) {
        this.fin = fin;
    }

    
}
