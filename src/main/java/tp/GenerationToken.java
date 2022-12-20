package tp;

import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerationToken {
    public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    public  String GenerateToken(String id,int minuteExp){
        Date now=new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
       calendar.add(Calendar.MINUTE, minuteExp);
       Date exp=calendar.getTime();
        return Jwts.builder()
        .setSubject(id)
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(now)
        .setExpiration(exp)
        .compact();
    }
    public  String GenerateBearerToken(String requete,String token) throws NoSuchAlgorithmException{
        return sha1(requete+token);
    }

}
