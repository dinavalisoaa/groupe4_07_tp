/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dina
 */
public class UFunction {
    
    public static int dateDiff(String sfixer, String soriginal) throws Exception {
        Date fixer = Date.valueOf(sfixer);
//        Date original=Date.valueOf(original);
        Date original = Date.valueOf(soriginal);

        try {
            long diff = fixer.getTime() - original.getTime();
            System.out.println("utils.UFunction.dateDiff()"+diff);
            TimeUnit time = TimeUnit.DAYS;
            long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
            
            
            return (int) diffrence;
        } catch (Exception e) {
            throw new Exception("Date invalid");

        }
    }
    public static int timeDiff(String sfixer, String soriginal) throws Exception {
        Timestamp  fixer = Timestamp.valueOf(sfixer);
//        Date original=Date.valueOf(original);
        Timestamp original = Timestamp.valueOf(soriginal);

        try {
            long diff = fixer.getTime()- original.getTime();
            System.out.println("utils.UFunction.dateDiff()"+diff);
            TimeUnit time = TimeUnit.HOURS;
            long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
            
            
            return (int) diffrence;
        } catch (Exception e) {
            throw new Exception("Date invalid");

        }
    }
    
     public static String getCurrentTimestamp() {
        Connection connect = null;
        Statement stmt = null;
           ResultSet res = null;
           String date="";
        ResultSetMetaData resultMeta = null;
        PreparedStatement pst = null;
        try {
              connect=DAO.Connexion.getConn();
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
                }
            }
        }

        return date;
    }
}
