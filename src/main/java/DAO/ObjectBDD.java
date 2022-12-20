/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
//import model.Ecriture;

/**
 *
 * @author dina
 */
public class ObjectBDD {

    String nomDeTable;

    public String getNomDeTable() {
        return nomDeTable;
    }
    ObjectBDD ObjetValidate;

    public ObjectBDD getObjetValidate() {
        return ObjetValidate;
    }

    public void setObjetValidate(ObjectBDD ObjetValidate) {
        this.ObjetValidate = ObjetValidate;
    }

    public void setNomDeTable(String nomDeTable) {
        this.nomDeTable = nomDeTable;
    }

    public boolean IN(String[] lstring, String token) {
        boolean re = false;
        for (String o : lstring) {
            if (o.toUpperCase().equals(token.toUpperCase())) {
                re = true;
            }

            ///balance  ====>  (totalDeb,Totalcredit,totalSoldeCred,totalSoldDeb,journalID)
            // detail balance ==> (cred,deb,soldCred,soldeDebit,BalanceID,COMPTEID)
        }
        return re;
    }

    public static String getTables(Object[] all, String ignore) throws Exception {
        Object objet2 = all[0].getClass().newInstance();
        String table = "<table border='1'>";
        String[] att = getAtr(objet2);
        String th = "<tr>";
        int cpt = 0;
        String td = "<tr>";
        for (Object objet : all) {
            String t = "";
            System.out.println("OBJECT__" + cpt);
            String trr = "<tr>";
            Method[] method = getMethodByName("get", objet);
            String[] attri = getAtr(objet);
            String exp = new String();
            int indice = 0;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class);
            Method[] methods = objet.getClass().getDeclaredMethods();
            String ret = new String();
            boolean misyWhere = false;
            boolean misyfiltre = false;
            for (int i = 0; i < attri.length; i++) {
                if (cpt == 0) {
                    th += "<th>" + attri[i] + "<th>";
                }
                String str = attri[i];
                String[] vol = str.split(Character.toString(str.toCharArray()[0]));
                String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
                String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
                Field atts = objet.getClass().getDeclaredField(h);
                Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class);
//            if (att_f == null) 
                if (attri[i].toLowerCase().equals("Id".toLowerCase())) {
                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false) {
                        t += "<td>" + objet.getClass().getSimpleName().substring(0, 4) + "_" + method[i].invoke(objet) + "<td>";
//                    System.out.println(t);
                    }

                } else {
                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false) {
                        {
                            t += "<td>" + method[i].invoke(objet) + "<td>";
//                    System.out.println(t);
                        }
                    }

                }

            }
            cpt++;
            td += t + "</tr>";
        }
        th += "</tr>";

        return table + th + td + "<table>";
    }

    public String update(String primary) {
        if (primary.equals("") == true) {
            primary = "id";
        }
        Object objet = this;
        String st = new String();
        try {
            Method[] method = getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            int indice = 0;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
            );
//            String[] tab = addString(annontation.ignore());
            for (int i = 0; i < attri.length; i++) {
                String str = attri[i];
                String[] vol = str.split(Character.toString(str.toCharArray()[0]));
                String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
                String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
//                System.out.println("DAObject.ObjectBDD.select()"+h);
                Field atts = this.getClass().getDeclaredField(h);
                Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                );
                UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                );

//                
                if (att_f == null && ats == null) {
                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false) {
                        if (attri[i].toUpperCase().equals(primary.toUpperCase())) {
                            //	exp=exp+attri[i]+"='"+method[i].invoke(objet)+"',";
                            indice = i;
                            // System.out.println("");
                        } else if (method[i].invoke(objet).toString().contains("%")) {
                            // misyWhere=true;
                            exp = exp + attri[i] + " like '" + method[i].invoke(objet) + "'and ";

                        } else {
//                        if (attri[i].equals(primary) == false) 
                            {
//                            if (method[i].getReturnType().getSimpleName().equals("Double")) {
//                                exp = exp + attri[i] + "=" + "" + method[i].invoke(objet) + ",";
//                            }
                                if (method[i].getReturnType().getSimpleName().equals("Date")) {
                                    exp = exp + attri[i] + "=DATE" + "'" + method[i].invoke(objet) + "',";

                                } else {
//                                (method[i].getReturnType().getSimpleName().equals("String")) {
                                    exp = exp + attri[i] + "=" + "'" + method[i].invoke(objet) + "',";

                                }
                            }
                        }
                    }
                }
            }
            char[] ch = exp.toCharArray();
//            System.out.println("DAO.DBTable.update()==>" + exp);
            String ret = (String) exp.subSequence(0, ch.length - 1);
//            System.out.println(toChamp(attri));
//            System.out.println("DAO.DBTable.update()==>" + exp);
            Class ccl = objet.getClass();

            InfoDAO anontation = (InfoDAO) ccl.getAnnotation(InfoDAO.class
            );

//          String tableName=annontation.table();
            String nameTable = annontation.table();//nameTable;
            st = "update " + nameTable + " set " + ret + " where " + primary + "='" + method[indice].invoke(objet) + "'";
            System.out.println("SQL:=>" + exp);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("===?" + st);
        return st;
    }

    public static void run(String st) throws Exception {
        int c = 0;
        System.out.println("SQL:=>" + st);
        Connection connect = null;
        Statement stmt = null;
        try {
            connect = Connexion.getConn();
            stmt = connect.createStatement();
            stmt.executeUpdate(st);
        } catch (Exception ex) {
            throw ex;
        } finally {
            stmt.close();
            connect.close();
        }

    }

    public void delete(String primary, Connection connect) throws Exception {
        String st = delete(primary);
        int c = 0;
        System.out.println("SQL:=>" + st);
        if (connect == null) {
            c++;
            connect = Connexion.getConn();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(st);
        } else {
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(st);

        }
        if (c > 0) {
            connect.close();
        }
    }

    public ArrayList select(Connection connect) throws Exception {
        Vector vect = new Vector();
        Object objet = this;
        int cpt = 0;
        if (connect == null) {
            cpt++;
            connect = Connexion.getConn();
        }
        Class ccl = this.getClass();
        InfoDAO anontation = (InfoDAO) ccl.getAnnotation(InfoDAO.class
        );
        String nameTable = anontation.table();
//        System.out.println("<<<<<<<<<>>>>>><<<<<<" + anontation);
        if (this.getNomDeTable() != null) {
            nameTable = this.getNomDeTable();
        }
        try {
            Method[] method = getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            int indice = 0;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
            );
//            String[] tab = addString(annontation.ignore());
//            System.out.println("main.M"+tab.length);
            Method[] methods = objet.getClass().getDeclaredMethods();
            String ret = new String();
            boolean misyWhere = false;
            boolean misyfiltre = false;
            for (int i = 0; i < attri.length; i++) {
                String str = attri[i];
                String[] vol = str.split(Character.toString(str.toCharArray()[0]));
                String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
                String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
//                System.out.println("DAObject.ObjectBDD.select()"+h);
                Field atts = this.getClass().getDeclaredField(h);
                Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                );
                UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                );

//                
                if (att_f == null && ats == null) {
                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false)//&&method[i].invoke(objet)=)
                    {
                        misyWhere = true;
                    }
                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false) {
                        if (method[i].invoke(objet).toString().contains("%")) {
//                             misyWhere=true;
                            exp = exp + attri[i] + " like '" + method[i].invoke(objet) + "' and ";
                        } //                    if (method[i].getReturnType().getSimpleName().equals("Date")) {
                        //                        if (method[i].invoke(objet).toString().contains(":")) {
                        //                            exp = exp + attri[i] + "=" + "'" + method[i].invoke(objet) + "' and ";
                        //
                        //                        } 
                        else {
//
                            exp = exp + attri[i] + "=" + "'" + method[i].invoke(objet) + "' and ";
                        }
                        char[] ch = exp.toCharArray();
                        int taille = ch.length;
                        ret = (String) exp.subSequence(0, taille - 4);

                    }
                }
            }
//            System.out.println("DAO<>>>>>>>>>><()" + nameTable);
            String st = new String();
            {
                // if(select.equals("*")==false)
                {
                    if (misyWhere == true) //                    if (getAutre() != null) 
                    {
                        st = "select *from  " + nameTable + " where " + "1=1 and " + ret;
//                        st = "select *from  " + nameTable + " where " + ret + "and " + getAutre();
                    } else {
                        st = "select *from  " + nameTable + " where " + "1=1";

                    }
                    //+"  "+primary+"="+method[indice].invoke(objet);
                }

            }
//             connect=Connexion.getConn();
//            System.out.println("SQL:=>" + st);
            Field[] fiel = objet.getClass().getDeclaredFields();
            Method[] methody = objet.getClass().getDeclaredMethods();
            Statement stmt = connect.createStatement();
            ResultSet res = stmt.executeQuery(st);
            ResultSetMetaData resultMeta = res.getMetaData();
            int b = 0;
            String st2 = "";
            Object nouveau = new Object();
            while (res.next()) {
                nouveau = objet.getClass().newInstance();
                for (int u = 0; u < fiel.length; u++) {
                    String str = fiel[u].getName();
                    String[] vols = str.split(Character.toString(str.toCharArray()[0]));
                    String primes = Character.toString(str.toCharArray()[0]).toUpperCase();
                    String h = primes.toLowerCase() + str.substring(1, str.toCharArray().length);

                    Field atts = this.getClass().getDeclaredField(h);
                    Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                    );
                    UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                    );

//                
                    if (att_f == null && ats == null) {
                        {
                            String[] vol = fiel[u].getName().split(Character.toString(fiel[u].getName().toCharArray()[0]));
                            String prime = Character.toString(fiel[u].getName().toCharArray()[0]).toUpperCase();
//                System.out.println("DBtable.Sql.getAllFields()");
                            st2 = prime + fiel[u].getName().substring(1, fiel[u].getName().toCharArray().length);
                            Class[] types = new Class[1];
                            types[0] = fiel[0].getType();

//                    for (int o = 0; o < resultMeta.getColumnCount(); o++) {
//
//                        {
                            if (fiel[u].getType().getSimpleName().equals("String")) {
                                Object ob = res.getString(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            } else if (fiel[u].getType().getSimpleName().equals("int")) {
                                Object ob = res.getInt(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, types);
                                miset.invoke(nouveau, ob);
                            } //                            
                            else if (fiel[u].getType().getSimpleName().equals("Integer")) {
                                Object ob = res.getInt(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            } else if (fiel[u].getType().getSimpleName().equals("double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            }
                            if (fiel[u].getType().getSimpleName().equals("Double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            }
//                            if(fiel[u].getType().getSimpleName().equals("int"))
////                            else {
//                                {
//                                    Object ob = res.getObject(fiel[u].getName());
//                                    System.out.println(st2+"??"+fiel[u].getName()+"????"+fiel[u].getType());
//                                    // System.out.println(fiel[u].getName());
//                                    //objet.getClass().getMethod(attri[u]));
//                                       
//                                    Method miset = objet.getClass().getMethod("setIdSexe", fiel[u].getType());
//                                    miset.invoke(nouveau, ob);
//                                }
////                            }
//                        }
//
                        }

                    }

                }
                vect.add(nouveau);
                b++;
            }

            // System.out.println;
            res.close();
            stmt.close();
            if (cpt > 0) {
                connect.close();

            }
        } //            connect.close();
        catch (Exception d) {
            d.printStackTrace();
        }
        ArrayList valiny = new ArrayList();; //        ArrayList valiny = new ArrayList();;
        for (int i = 0; i < vect.size(); i++) {

            valiny.add(vect.get(i));
        }
        return valiny;
    }

//    public boolean dejaValidate(ObjectBDD validation, Connection con) throws Exception {
//        boolean bool = false;
//        Class c = this.getClass();
//        ArrayList object = this.select(con);
//        
//        InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class);
//        if (annontation.validable() == true) {
//            this.setNomDeTable(toinsert.getClass().getSimpleName());
//            this.insert(con);
//        }
//        return bool;
//    }
//    public ObjectBDD gettoInsert(){
//        return 
//    }
    public void validate(Connection con) throws Exception {
        Class c = this.getClass();
        InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
        );
        if (annontation.validable() == true) {
            this.setNomDeTable(this.getObjetValidate().getClass().getSimpleName());
            this.insert(con);
        } else {
            throw new Exception("Class is not validable");
        }
    }

    public int getLastID() throws Exception {
        ArrayList li = selectBySQL(" select *  from " + this.getClass().getSimpleName() + " order by id desc limit 1", null);
        Object obj = li.get(0);
        Method m = li.get(0).getClass().getMethod("getId");
        int g = (int) m.invoke(obj);
        return g;
    }

    public ObjectBDD getLastObject() throws Exception {
        ArrayList li = selectBySQL(" select *  from " + this.getClass().getSimpleName() + " order by id desc limit 1", null);
        Object obj = li.get(0);
        Method m = li.get(0).getClass().getMethod("getId");
        int g = (int) m.invoke(obj);
        return (ObjectBDD) obj;
    }

    public ArrayList selectBySQL(String SQL, Connection connect) throws Exception {
        String st = SQL;
        Vector vect = new Vector();
        Object objet = this;
        int cpt = 0;
        if (connect == null) {
            cpt++;
            connect = Connexion.getConn();
        }
        Class c = objet.getClass();

        InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
        );

//          String tableName=annontation.table();
        String nameTable = annontation.table();//nameTable;
        if (this.getNomDeTable() != null) {
            nameTable = this.getNomDeTable();
        }
        try {
            Method[] method = getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            int indice = 0;

            Method[] methods = objet.getClass().getDeclaredMethods();
            String ret = new String();
            boolean misyWhere = false;
            boolean misyfiltre = false;

//             connect=Connexion.getConn();
            System.out.println("SQL:=>" + st);
            Field[] fiel = objet.getClass().getDeclaredFields();
            Method[] methody = objet.getClass().getDeclaredMethods();
            Statement stmt = connect.createStatement();
            ResultSet res = stmt.executeQuery(st);
            ResultSetMetaData resultMeta = res.getMetaData();
            int b = 0;
            String st2 = "";
            Object nouveau = new Object();
            while (res.next()) {
                nouveau = objet.getClass().newInstance();
                for (int u = 0; u < fiel.length; u++) {
                    String str = fiel[u].getName();
                    String[] vols = str.split(Character.toString(str.toCharArray()[0]));
                    String primes = Character.toString(str.toCharArray()[0]).toUpperCase();
                    String h = primes.toLowerCase() + str.substring(1, str.toCharArray().length);

                    Field atts = this.getClass().getDeclaredField(h);
                    Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                    );
                    UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                    );

//                
                    if (att_f == null && ats == null) {
                        {
                            String[] vol = fiel[u].getName().split(Character.toString(fiel[u].getName().toCharArray()[0]));
                            String prime = Character.toString(fiel[u].getName().toCharArray()[0]).toUpperCase();
//                System.out.println("DBtable.Sql.getAllFields()");
                            st2 = prime + fiel[u].getName().substring(1, fiel[u].getName().toCharArray().length);
                            Class[] types = new Class[1];
                            types[0] = fiel[0].getType();

//                    for (int o = 0; o < resultMeta.getColumnCount(); o++) {
//
//                        {
                            if (fiel[u].getType().getSimpleName().equals("String")) {
                                Object ob = res.getString(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            } else if (fiel[u].getType().getSimpleName().equals("int")) {
                                Object ob = res.getInt(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, types);
                                miset.invoke(nouveau, ob);
                            } //                            
                            else if (fiel[u].getType().getSimpleName().equals("Integer")) {
                                Object ob = res.getInt(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            } else if (fiel[u].getType().getSimpleName().equals("double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            }
                            if (fiel[u].getType().getSimpleName().equals("Double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            }
//                            if(fiel[u].getType().getSimpleName().equals("int"))
////                            else {
//                                {
//                                    Object ob = res.getObject(fiel[u].getName());
//                                    System.out.println(st2+"??"+fiel[u].getName()+"????"+fiel[u].getType());
//                                    // System.out.println(fiel[u].getName());
//                                    //objet.getClass().getMethod(attri[u]));
//                                       
//                                    Method miset = objet.getClass().getMethod("setIdSexe", fiel[u].getType());
//                                    miset.invoke(nouveau, ob);
//                                }
////                            }
//                        }
//
                        }

                    }

                }
                vect.add(nouveau);
                b++;
            }
            // System.out.println;
            res.close();
            stmt.close();
            if (cpt > 0) {
                connect.close();
            }
        } catch (Exception d) {
            d.printStackTrace();
        }
        ArrayList valiny = new ArrayList();; //        ArrayList valiny = new ArrayList();;
        for (int i = 0; i < vect.size(); i++) {
            valiny.add(vect.get(i));
        }
        return valiny;
    }

    public ArrayList selectAgregatBySQL(String SQL, Connection connect) throws Exception {
        String st = SQL;
        Vector vect = new Vector();
        Object objet = this;
        int cpt = 0;
        if (connect == null) {
            cpt++;
            connect = Connexion.getConn();
        }
        Class c = objet.getClass();

        InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
        );

//          String tableName=annontation.table();
        String nameTable = annontation.table();//nameTable;
        if (this.getNomDeTable() != null) {
            nameTable = this.getNomDeTable();
        }
        try {
            Method[] method = getMethodByNameAgregat("get");
            String[] attri = getAllFields();
            String exp = new String();
            int indice = 0;

            Method[] methods = objet.getClass().getDeclaredMethods();
            String ret = new String();
            boolean misyWhere = false;
            boolean misyfiltre = false;

//             connect=Connexion.getConn();
            System.out.println("SQL:=>" + st);
            Field[] fiel = objet.getClass().getDeclaredFields();
            Method[] methody = objet.getClass().getDeclaredMethods();
            Statement stmt = connect.createStatement();
            ResultSet res = stmt.executeQuery(st);
            ResultSetMetaData resultMeta = res.getMetaData();
            int b = 0;
            String st2 = "";
            Object nouveau = new Object();
            while (res.next()) {
                nouveau = objet.getClass().newInstance();
                for (int u = 0; u < fiel.length; u++) {
                    String str = fiel[u].getName();
                    String[] vols = str.split(Character.toString(str.toCharArray()[0]));
                    String primes = Character.toString(str.toCharArray()[0]).toUpperCase();
                    String h = primes.toLowerCase() + str.substring(1, str.toCharArray().length);

                    Field atts = this.getClass().getDeclaredField(h);
                    UnColumn att_f = (UnColumn) atts.getAnnotation(UnColumn.class
                    );
//                
                    if (att_f != null) {
                        {
                            String[] vol = fiel[u].getName().split(Character.toString(fiel[u].getName().toCharArray()[0]));
                            String prime = Character.toString(fiel[u].getName().toCharArray()[0]).toUpperCase();
//                System.out.println("DBtable.Sql.getAllFields()");
                            st2 = prime + fiel[u].getName().substring(1, fiel[u].getName().toCharArray().length);
                            Class[] types = new Class[1];
                            types[0] = fiel[0].getType();

//                    for (int o = 0; o < resultMeta.getColumnCount(); o++) {
//
//                        {
                            if (fiel[u].getType().getSimpleName().equals("String")) {
                                Object ob = res.getString(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            } else if (fiel[u].getType().getSimpleName().equals("int")) {
                                Object ob = res.getInt(fiel[u].getName());
//                            System.out.println(fiel[u].getName());
                                Method miset = objet.getClass().getMethod("set" + st2, types);
                                miset.invoke(nouveau, ob);
                            } //                            
                            else if (fiel[u].getType().getSimpleName().equals("Integer")) {
                                Object ob = res.getInt(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            } else if (fiel[u].getType().getSimpleName().equals("double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                //System.out.println("set"+fiel[u].getName());
                                miset.invoke(nouveau, ob);

                            }
                            if (fiel[u].getType().getSimpleName().equals("Double")) {
                                Object ob = res.getDouble(fiel[u].getName());//objet.getClass().getMethod(attri[u]));
                                Method miset = objet.getClass().getMethod("set" + st2, fiel[u].getType());
                                miset.invoke(nouveau, ob);
                            }
//                            if(fiel[u].getType().getSimpleName().equals("int"))
////                            else {
//                                {
//                                    Object ob = res.getObject(fiel[u].getName());
//                                    System.out.println(st2+"??"+fiel[u].getName()+"????"+fiel[u].getType());
//                                    // System.out.println(fiel[u].getName());
//                                    //objet.getClass().getMethod(attri[u]));
//                                       
//                                    Method miset = objet.getClass().getMethod("setIdSexe", fiel[u].getType());
//                                    miset.invoke(nouveau, ob);
//                                }
////                            }
//                        }
//
                        }

                    }

                }
                vect.add(nouveau);
                b++;
            }
            // System.out.println;
            res.close();
            stmt.close();
            if (cpt > 0) {
                connect.close();
            }
        } catch (Exception d) {
            d.printStackTrace();
        }
        ArrayList valiny = new ArrayList();; //        ArrayList valiny = new ArrayList();;
        for (int i = 0; i < vect.size(); i++) {
            valiny.add(vect.get(i));
        }
        return valiny;
    }

    public void update(String primary, Connection connect) throws Exception {
        String st = update(primary);
        System.out.println(st);
        int c = 0;
        if (connect == null) {
            c++;
            connect = Connexion.getConn();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(st);
        } else {
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(st);

        }
        if (c > 0) {
            connect.close();
        }
    }

    public void execBySQL(String SQL, Connection connect) throws Exception {
//        String st = update(primary);
//        System.out.println(st);
        int c = 0;
        if (connect == null) {
            c++;
            connect = Connexion.getConn();
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(SQL);
        } else {
            Statement stmt = connect.createStatement();
            stmt.executeUpdate(SQL);

        }
        if (c > 0) {
            connect.close();
        }
    }

    public boolean alreadyHere(Connection connect) throws Exception {
        boolean retour = false;
        ArrayList vaovao = select(connect);
        if (vaovao.size() > 0) {
            return true;
        }
        return false;
    }

    public void insert(Connection connect) throws Exception {
        String op = insertion();
        System.out.println("sd");
        System.out.println(op);
        int c = 0;
        if (connect == null) {
            c++;
            connect = Connexion.getConn();
        }
        try {
            PreparedStatement stmt = connect.prepareStatement(op);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        System.out.println("insertion succesfully");
        if (c > 0) {
            connect.close();
        }
    }

//    public ArrayList executeSQL(String requet, Connection connect) throws Exception {
//        Object a = this;
//        PreparedStatement state = null;
//        ResultSet rs = null;
//        int ks = 0;
//        if (connect == null) {
//            ks++;
//            connect = Connexion.getConn();
//        }
//        String[] atr = this.getAtr();
//        String[] nom = new String[atr.length];
//        Method[] mg = getMethodes();
//        Method[] ms = setMethodes();
//        int k = 0;
//        ArrayList<Object> table = new ArrayList();
//        try {
//            state = state = connect.prepareStatement(requet);
//            rs = state.executeQuery();
//            while (rs.next()) {
//                Object objet = a.getClass().newInstance();
//                int i = 0;
//                for (String p : atr) {
//                    if (mg[i].getGenericReturnType().toString().contains("int")) {
//                        ms[i].invoke(objet, rs.getInt(i + 1));
//                        nom[k] = ms[i].getName().split("set")[1];
//                    } else if (mg[i].getGenericReturnType().toString().contains("double")) {
//                        ms[i].invoke(objet, rs.getDouble(i + 1));
//                        // nom[k]=ms[i].getName().split("set")[1];
//
//                    } else if (mg[i].getGenericReturnType().toString().contains("String")) {
//                        ms[i].invoke(objet, rs.getString(i + 1));
//                        nom[k] = ms[i].getName().split("set")[1];
//
//                    } else {
//                        ms[i].invoke(objet, rs.getDate(i + 1));
//                        nom[k] = ms[i].getName().split("set")[1];
//                    }
//                    i++;
//                    // k++;
//                }
//                table.add(objet);
//            }
//            rs.close();
//            state.close();
//
//        } catch (Exception e) {
//        }
//        for (int o = 0; o < nom.length; o++) {
//            System.out.println(nom[o]);
//
//        }
//        ArrayList valiny = new ArrayList();
//        for (int i = 0; i < table.size(); i++) {
//
//            valiny.add(table.get(i));
//        }
//        if (ks > 0) {
//            connect.close();
//        }
//        return valiny;
//    }
    public String delete(String primary) {
        if (primary.equals("") == false) {
            primary = "Id";
        }
        String st = new String();
        Object objet = this;
        try {
            Method[] method = getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            int indice = 0;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
            );
//            String[] tab = addString(annontation.ignore());
            for (int i = 0; i < attri.length; i++) {
                Field atts = this.getClass().getDeclaredField(attri[i].toLowerCase());
                Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                );
                UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                );

//                
                if (att_f == null && ats == null) {

                    if (method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false)//&&method[i].invoke(objet)=)
                    {
                        if (attri[i].equals(primary)) {
                            exp = exp + attri[i] + "=" + method[i].invoke(objet) + " and ";
                            indice = i;
                        }
                        if (method[i].invoke(objet).toString().contains("%")) {
                            exp = exp + attri[i] + " like '" + method[i].invoke(objet) + "'and ";

                        } else if (method[i].getReturnType().getSimpleName().equals("Integer")) {
                            exp = exp + attri[i] + "=" + method[i].invoke(objet) + " and ";
                        } else {
                            exp = exp + attri[i] + "=" + "'" + method[i].invoke(objet) + "' and ";
                        }
                    }
                }
            }
            char[] ch = exp.toCharArray();
            String ret = (String) exp.subSequence(0, ch.length - 4);
//            System.out.println(toChamp(attri));
            Class ccl = objet.getClass();
            InfoDAO anontation = (InfoDAO) ccl.getAnnotation(InfoDAO.class
            );
            String nameTable = annontation.table();
            st = "delete  from " + nameTable + " where " + ret;
            System.out.println("===?" + st);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return st;
    }

    public Method[] getMethodes() throws Exception {
        String[] field = getAtr();
        Method[] m = new Method[field.length];
        Class classe = this.getClass();
        for (int i = 0; i < field.length; i++) {
            m[i] = classe.getMethod("get" + field[i]);
        }
        return m;
    }

    public String[] getAtr() {
        // Object object = (Object)this;
        Class classe = this.getClass();
        Field[] fields = classe.getDeclaredFields();
        String[] ls = new String[fields.length];
        String[] str2 = new String[fields.length];
        int i = 0;
        for (Field f : fields) {
            ls[i] = f.getName();
            String[] vol = ls[i].split(Character.toString(ls[i].toCharArray()[0]));
            String prime = Character.toString(ls[i].toCharArray()[0]).toUpperCase();
//                System.out.println("DBtable.Sql.getAllFields()");
            str2[i] = prime + ls[i].substring(1, ls[i].toCharArray().length);

            i++;
        }
        return str2;
    }

    public static String[] getAtr(Object ob) {
        // Object object = (Object)this;
        Class classe = ob.getClass();
        Field[] fields = classe.getDeclaredFields();
        String[] ls = new String[fields.length];
        String[] str2 = new String[fields.length];
        int i = 0;
        for (Field f : fields) {
            ls[i] = f.getName();
            String[] vol = ls[i].split(Character.toString(ls[i].toCharArray()[0]));
            String prime = Character.toString(ls[i].toCharArray()[0]).toUpperCase();
//                System.out.println("DBtable.Sql.getAllFields()");
            str2[i] = prime + ls[i].substring(1, ls[i].toCharArray().length);

            i++;
        }
        return str2;
    }

    public Method[] setMethodes() throws Exception {
        String[] field = getAtr();
        Method[] m = new Method[field.length];
        Class classe = this.getClass();
        for (int i = 0; i < field.length; i++) {
            Method getmethod = getMethodes()[i];
            m[i] = classe.getMethod("set" + field[i], getmethod.getReturnType());
        }
        return m;
    }

    public String tostr(String[] a) {
        String ret = new String();
        for (int i = a.length - 1; i >= 0; i--) {
            ret = a[i] + "<th>" + ret;

        }
        return ret;

    }

    public String insertion() {
        String st = new String();
        String primary = "";

        try {
            Object objet = this;
//            System.out.println(nameTable);
            Method[] method = this.getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            String eo = new String();
            String exp_p = new String();
            boolean isPG = false;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
            );
            Method[] methods = objet.getClass().getDeclaredMethods();

            for (int i = 0; i < attri.length; i++) {
                String str = attri[i];
                String[] vol = str.split(Character.toString(str.toCharArray()[0]));
                String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
                String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
                Field atts = this.getClass().getDeclaredField(h);
                Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
                );

                UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
                );

//                
                if (att_f == null && ats == null) {

                    if (attri[i].toUpperCase().equals("Id".toUpperCase())) {
                    }
                    if ((attri[i].equals(primary) == false) && method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false)//&&method[i].invoke(objet)=)
                    {
//                    if (method[i].getReturnType().getSimpleName().equals("String")) {
                        exp = exp + "'" + method[i].invoke(objet) + "',";
                        char[] lr = method[i].getName().toCharArray();
                        eo = eo + attri[i] + ",";

//                    }
//                    if (method[i].getReturnType().getSimpleName().equals("Date")) {
//                        exp = exp + "DATE'" + method[i].invoke(objet) + "',";
//                        char[] lr = method[i].getName().toCharArray();
//                        eo = eo + attri[i] + ",";
//
//                    }
////                    if (method[i].getReturnType().getSimpleName().equals("Double") || method[i].getReturnType().getSimpleName().equals("Integer"))
//                    {
//                        exp = method[i].invoke(objet) + "," + exp;
//                        eo = attri[i] + "," + eo;
//                        char[] lr = method[i].getName().toCharArray();
//                        String name = (String) method[i].getName().subSequence(3, lr.length);
//
//                    }
                    }
                }
            }
            char[] ch = exp.toCharArray();
            char[] che = eo.toCharArray();
            String valu = new String();
            System.out.println("expression:" + eo);
            if (primary.equals("") == false) {
                valu = "(" + primary + "," + (String) eo.subSequence(0, che.length - 1) + ")";
            } else {
                valu = "(" + (String) eo.subSequence(0, che.length - 1) + ")";

            }
            String tableName = annontation.table();
            String ret = "(" + exp_p + (String) exp.subSequence(0, ch.length - 1) + ")";
            if (this.getNomDeTable() != null) {
                st = "insert into " + this.getNomDeTable() + valu + " values" + ret;
            } else {
                st = "insert into " + tableName + valu + " values" + ret;

            }

            System.out.println("SQL:=>" + st);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            // throw new Exception("Insertion tsy mety");
        }
        return st;
    }

    public Method[] getMethodByName(String n) throws Exception {
        String[] attr = this.getAllFields();
        Method[] met = new Method[attr.length];
        Method[] methodses = this.getClass().getMethods();

        for (int i = 0; i < attr.length; i++) {
            String str = attr[i];
            String[] vol = str.split(Character.toString(str.toCharArray()[0]));
            String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
            String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
            Field atts = this.getClass().getDeclaredField(h);
            Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
            );
            UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
            );

//                
            if (att_f == null && ats == null) {
                met[i] = this.getClass().getMethod(n + str);
            }
        }
        return met;
    }

    public Method[] getMethodByNameAgregat(String n) throws Exception {
        String[] attr = this.getAllFields();
        Method[] met = new Method[attr.length];
        Method[] methodses = this.getClass().getMethods();

        for (int i = 0; i < attr.length; i++) {
            String str = attr[i];
            String[] vol = str.split(Character.toString(str.toCharArray()[0]));
            String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
            String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
            Field atts = this.getClass().getDeclaredField(h);
            UnColumn att_f = (UnColumn) atts.getAnnotation(UnColumn.class
            );
            if (att_f != null) {
                met[i] = this.getClass().getMethod(n + str);
            }
        }
        return met;
    }

    public static Method[] getMethodByName(String n, Object ob) throws NoSuchMethodException, NoSuchFieldException {
        String[] attr = getAtr(ob);
        Method[] met = new Method[attr.length];
        Method[] methodses = ob.getClass().getMethods();

        for (int i = 0; i < attr.length; i++) {

            String str = attr[i];
            String[] vol = str.split(Character.toString(str.toCharArray()[0]));
            String prime = Character.toString(str.toCharArray()[0]).toUpperCase();
            String h = prime.toLowerCase() + str.substring(1, str.toCharArray().length);
            Field atts = ob.getClass().getDeclaredField(h);
            Ignore att_f = (Ignore) atts.getAnnotation(Ignore.class
            );
            UnColumn ats = (UnColumn) atts.getAnnotation(UnColumn.class
            );

//                
            if (att_f == null && ats == null) {
                met[i] = ob.getClass().getMethod(n + str);
            }

        }
        return met;
    }

    public String[] getAllFields() {

        Field[] fd = this.getClass().getDeclaredFields();
        String[] str = new String[fd.length];
        String[] str2 = new String[fd.length];
        int ii = 0;
        for (int i = 0; i < fd.length; i++) {
            {
                str[ii] = fd[i].getName();
                String[] vol = str[ii].split(Character.toString(str[ii].toCharArray()[0]));
                String prime = Character.toString(str[ii].toCharArray()[0]).toUpperCase();
                str2[ii] = prime + str[ii].substring(1, str[ii].toCharArray().length);
                ii++;
            }
        }
        return str2;
    }

    /**
     * *********************************************************************************************************************************************
     */
    public void insertNeedNameTable(String nametable, Connection connect) throws Exception {
        String op = insertionNeedNameTable(nametable);
        System.out.println("sd");
        System.out.println(op);
        int c = 0;
        if (connect == null) {
            c++;
            connect = Connexion.getConn();
        }
        try {
            PreparedStatement stmt = connect.prepareStatement(op);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        System.out.println("insertion succesfully");
        if (c > 0) {
            connect.close();
        }
    }

    public String insertionNeedNameTable(String nameTa) {
        String st = new String();
        String primary = "";

        try {
            Object objet = this;
//            System.out.println(nameTable);
            Method[] method = this.getMethodByName("get");
            String[] attri = getAllFields();
            String exp = new String();
            String eo = new String();
            String exp_p = new String();
            boolean isPG = false;
            Class c = objet.getClass();
            InfoDAO annontation = (InfoDAO) c.getAnnotation(InfoDAO.class
            );
            Method[] methods = objet.getClass().getDeclaredMethods();
            for (int i = 0; i < attri.length; i++) {
//                if (true == IN(tab, attri[i])) {
//                    continue;
//                }
//                else
                {
                    if (attri[i].toUpperCase().equals("Id".toUpperCase())) {
                    }
                    if ((attri[i].equals(primary) == false) && method[i].invoke(objet) != null && method[i].invoke(objet).equals(-1) == false && method[i].invoke(objet).equals(-1.0) == false)//&&method[i].invoke(objet)=)
                    {
//                    if (method[i].getReturnType().getSimpleName().equals("String")) {
                        exp = exp + "'" + method[i].invoke(objet) + "',";
                        char[] lr = method[i].getName().toCharArray();
                        eo = eo + attri[i] + ",";

//                    }
//                    if (method[i].getReturnType().getSimpleName().equals("Date")) {
//                        exp = exp + "DATE'" + method[i].invoke(objet) + "',";
//                        char[] lr = method[i].getName().toCharArray();
//                        eo = eo + attri[i] + ",";
//
//                    }
////                    if (method[i].getReturnType().getSimpleName().equals("Double") || method[i].getReturnType().getSimpleName().equals("Integer"))
//                    {
//                        exp = method[i].invoke(objet) + "," + exp;
//                        eo = attri[i] + "," + eo;
//                        char[] lr = method[i].getName().toCharArray();
//                        String name = (String) method[i].getName().subSequence(3, lr.length);
//
//                    }
                    }
                }
            }
            char[] ch = exp.toCharArray();
            char[] che = eo.toCharArray();
            String valu = new String();
            System.out.println("expression:" + eo);
            if (primary.equals("") == false) {
                valu = "(" + primary + "," + (String) eo.subSequence(0, che.length - 1) + ")";
            } else {
                valu = "(" + (String) eo.subSequence(0, che.length - 1) + ")";

            }
            String tableName = nameTa;
            String ret = "(" + exp_p + (String) exp.subSequence(0, ch.length - 1) + ")";
            st = "insert into " + tableName + valu + " values" + ret;
            System.out.println("SQL:=>" + st);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            // throw new Exception("Insertion tsy mety");
        }
        return st;
    }

}
