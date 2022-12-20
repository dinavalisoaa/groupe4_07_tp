/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import DAO.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Times;
import java.util.Date;
import java.time.LocalDate;

import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

    /**
     * @param args the command line arguments
     */
  
  
/**
 *
 * @author dina
 */
@InfoDAO(table = "Tokens")

public class Tokens extends ObjectBDD{
    int utilisateurId=-1;
    String token;
    String dateExp;

    public int getUtilisateurId() {
        return utilisateurId;
    }
  public static String addMinute(String date,int adding){
        Timestamp t=Timestamp.valueOf(date);
        t.setMinutes(t.getMinutes()+5);
      Timestamp time=t;
      String daty=time.toLocalDateTime().toString().split("T")[0];
              
      String lera=time.toLocalDateTime().toString().split("T")[1];
//        System.out.println();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return daty+" "+lera;    
    }
    
    public static boolean isDepasse(String date){
        String s=getCurrentTimestamp().split("\\.")[0];
        Timestamp t=Timestamp.valueOf(date);
//        t.
        Timestamp t1=Timestamp.valueOf(s);
        System.out.println("Compare__+"+t+"   "+t.before(t1)+" <?> current"+t1);
        return t.before(t1);
    }
    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
  
     public static String getCurrentTimestamp() {
        Connection connect = null;
        Statement stmt = null;
           ResultSet res = null;
           String date="";
        ResultSetMetaData resultMeta = null;
        PreparedStatement pst = null;
        try {
            connect=Connexion.getConn();
              pst=connect.prepareStatement("Select current_timestamp as date");
             res=pst.executeQuery();
            resultMeta = res.getMetaData();
            int b = 0;
            while (res.next()) {
             date=res.getString("date");
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
                    Logger.getLogger(Tokens.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return date;
    }
 
 public static String sha1(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getDateExp() {
        return dateExp;
    }

    public void setDateExp(String dateExp) {
        this.dateExp = dateExp;
    }
    
}
