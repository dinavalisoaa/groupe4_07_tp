package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
public class Connexion {
    public static Connection getConn() throws Exception {
         Connection connectionSQL=null;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");
            connectionSQL = DriverManager.getConnection("jdbc:postgresql://postgresql-miantsa.alwaysdata.net:5432/miantsa_avion", "miantsa", "Korgpa50");
        } catch (Exception e) {
//            System.out.println("Nisy probelm connection");
            e.printStackTrace();
        }
        return connectionSQL;
    }

}
