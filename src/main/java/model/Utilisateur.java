/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.*;

/**
 *
 * @author dina
 */
@InfoDAO(table = "Utilisateur")

public class Utilisateur extends ObjectBDD{
 int id=-1;
 String nom;
 String pwd;
 String login;
public boolean login() throws Exception{
    this.setNom(nom);
    this.setLogin(login);
    if(this.select(null).size()>0){
        return true;
    }
    return false;
}

public int getLoginId() throws Exception{
    this.setNom(nom);
    this.setLogin(login);
    if(this.select(null).size()>0){
        return ((Utilisateur)this.select(null).get(0)).getId();
    }
    return -1;
}
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
 
}
