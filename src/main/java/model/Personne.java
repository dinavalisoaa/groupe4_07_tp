/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.*;

import DAO.InfoDAO;
import java.util.ArrayList;

/**
 *
 * @author dina
 */
@InfoDAO(table = "Personne")
public class Personne extends ObjectBDD {
int id=-1;
String nom;
String prenom;
int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static Personne[] Personnes() throws Exception {
        ArrayList lis = new Personne().selectBySQL("select *from personne", null);
        Personne[] oo = new Personne[lis.size()];
        for (int i = 0; i < oo.length; i++) {
            oo[i] = (Personne) lis.get(i);
        }
        return oo;
    }

}
