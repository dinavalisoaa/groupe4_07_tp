package tp;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import model.Assurance;
import model.Tokens;
import model.Vehicule;
 
public class GFG {
   
    public static void main(String args[]) throws Exception
    {
 
  // 2021-03-24 16:48:05.591
  Timestamp timestamp = new Timestamp(System.currentTimeMillis());

  // 2021-03-24 16:48:05.591
  Date date = new Date();
  Timestamp timestamp2 = new Timestamp(date.getTime());  // convert Instant to Timestamp
  Timestamp ts = Timestamp.from(Instant.now());
Timestamp t=Timestamp.valueOf("2022-12-11 14:17:10");
//        t.setMinutes(t.getMinutes()+5);
 String g = Tokens.addMinute("2022-12-11 14:17:10", 5);
// ArrayList<Vehicule>als=new Assurance().getVehiculesExp3mois();
// HashMap<String,Object>llo=new Hello().vehiculesAssurance1();
//        System.err.println("--->"+llo);
//        for (int i = 0; i < als.size(); i++) {
//            Vehicule get = als.get(i);
//            System.out.println(get.getJour_restant());
//        }
//  // convert Timestamp to Instant
//  Instant instant = ts.toInstant();
//        System.out.println("?>>>>>>: "+t.toString());
//                System.out.println("?>>>>>>: "+t.getHours());
//                                System.out.println("?>>>>>>: "+t.getMinutes());
//                                    t.setMinutes(t.getMinutes()+5);
// System.1out.println("?>>>>>>: "+Tokens.isDepasse("2022-12-11 14:17:10"));
//  System.out.println("?>>>>>>: "+Tokens.isDepasse("2022-12-11 14:50:10"));
 
//        String s1 = "GeeksForGeeks";
//        System.out.println("\n" + s1 + " : " + encryptThisString(s1));
 
//        String s2 = "hello world";
//        System.out.println("\n" + s2 + " : " + encryptThisString(s2));
    }
}