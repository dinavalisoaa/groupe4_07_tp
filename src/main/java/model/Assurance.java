/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author dina
 */
@InfoDAO(table = "Assurance")
public class Assurance extends ObjectBDD {

    int id = -1;
    String datePaie;
    String dateExp;
    double montant = -1.0;
    int vehiculeId = -1;
    int etat = -1;
   
    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void lanydate() throws Exception {
        try {
            Assurance vao = new Assurance();
            vao.setId(this.id);
            vao.setEtat(0);
            if (vao.getDurerRestant() <= 0) {
                vao.update("Id", null);
            }
        } catch (Exception s) {
            throw s;
        }

    }

    public Assurance getAssurance() throws Exception {
        Assurance su = new Assurance();
             lanydate();
        su.setId(this.id);
        su.setEtat(1);
        ArrayList<Assurance> li = su.select(null);
        return li.get(0);
    }

    public Vehicule getVehicule() throws Exception {
        Vehicule su = new Vehicule();
        su.setId(this.vehiculeId);
        ArrayList<Vehicule> li = su.select(null);
        return li.get(0);
    }

    public ArrayList<Vehicule> getVehiculesExp3mois() throws Exception {
        ArrayList<Vehicule> rance = new Vehicule().selectBySQL("select * from assurance_vehicule where jour_restant<90 ", null);
        System.err.println("==/???" + rance.size());
        ArrayList<Vehicule> vaovao = new ArrayList();
        for (int i = 0; i < rance.size(); i++) {
            Vehicule get = rance.get(i);
            get.setJour_restant(get.getAssurance().get(0).getDurerRestant());
            get.setPhoto("photo");
            vaovao.add(get);
        }
        System.err.println(vaovao.size());
        return vaovao;
    }
//    select *from assurance_vehicule where  jour_restant>=30 and jour_restant<=40;
//    select *from assurance_vehicule where  jour_restant>=90 and jour_restant<=70;
    public ArrayList<Vehicule> getVehiculesExpmois() throws Exception {
        ArrayList<Vehicule> rance = new Vehicule().selectBySQL("select * from assurance_vehicule where jour_restant<30 ", null);
        ArrayList<Vehicule> vaovao = new ArrayList();
        for (int i = 0; i < rance.size(); i++) {
            Vehicule get = rance.get(i);
            get.setJour_restant(get.getAssurance().get(0).getDurerRestant());
            get.setPhoto("photo");
            vaovao.add(get);
        }
        return vaovao;
    }

    public int getDurerRestant() {
        Connection connect = null;
        Statement stmt = null;
        ResultSet res = null;
        int date = 0;
        ResultSetMetaData resultMeta = null;
        PreparedStatement pst = null;
        try {
            connect = DAO.Connexion.getConn();
            pst = connect.prepareStatement("Select *  from assurance_vehicule where id=" + this.getId());
            res = pst.executeQuery();
            resultMeta = res.getMetaData();
            int b = 0;
            while (res.next()) {
                date = res.getInt("jour_restant");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                    pst.close();
                    connect.close();
                } catch (SQLException ex) {
                }
            }
        }

        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatePaie() {
        return datePaie;
    }

    public void setDatePaie(String datePaie) {
        this.datePaie = datePaie;
    }

    public String getDateExp() {
        return dateExp;
    }

    public void setDateExp(String dateExp) {
        this.dateExp = dateExp;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

}
