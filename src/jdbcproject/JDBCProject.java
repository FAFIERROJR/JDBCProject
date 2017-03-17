/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Vargas
 * @author Francisco Fierro
 */
public class JDBCProject {
    private static int padding;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        padding = 40;
         try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/JDBC"); 
            
            //write menu stuff in here
            
            //this stuff is just for testing
            viewTable(conn, "writinggroups");
            viewGroup(conn, "Happy Gilmore");
            viewTable(conn, "publishers");
            viewPublisher(conn, "Pearson");
            viewBookTitles(conn);
            viewBook(conn, "Harry Potter and the Chamber of Secrets", "Rowling et al", "Pearson");
            //removeBook(conn, "Harry Potter and the Prisoner of Azkaban", "Rowling et al", "Pearson");
            insertBook(conn, "Harry Potter and the Prisoner of Azkaban", "Rowling et al", "Pearson", 2003, 500);
            viewBookTitles(conn);
            ArrayList<String> newPub = new ArrayList<String>();
            newPub.add("McGraw Hill");
            newPub.add("666 Oligopoly Rd");
            newPub.add("800-123-4567");
            newPub.add("McGrawHelp@McGraw.com");
            insertPub(conn, newPub, "Penguin Random House" );
            viewTable(conn, "publishers");
            
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
        
        
    }
    
    
    public static void viewTable(Connection conn, String table){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM " + table + "";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%" + padding +  "s", rsmd.getColumnLabel(i));
            }
            System.out.print("\n");
            while(rs.next()){
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%" + padding +  "s", rs.getString(i));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public static void viewBook(Connection conn, String title, String groupname, String publisher){
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BOOKS "
                    + "NATURAL JOIN writinggroups NATURAL JOIN publishers "
                    + "WHERE booktitle = ? AND groupname = ? AND publishername = ?" );
            pstmt.setString(1, title);
            pstmt.setString(2, groupname);
            pstmt.setString(3, publisher);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%" + padding +  "s", rsmd.getColumnLabel(i));
            }
            System.out.print("\n");
            while(rs.next()){
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%" + padding +  "s", rs.getString(i));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void viewGroup(Connection conn, String groupname) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM WRITINGGROUPS WHERE groupname = ?");
            pstmt.setString(1, groupname);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%" + padding +  "s", rsmd.getColumnLabel(i));
            }
            System.out.print("\n");
            while(rs.next()){
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%" + padding +  "s", rs.getString(i));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public static void viewPublisher(Connection conn, String publishername) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM publishers WHERE publishername = ?");
            pstmt.setString(1, publishername);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%" + padding +  "s", rsmd.getColumnLabel(i));
            }
            System.out.print("\n");
            while(rs.next()){
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%" + padding +  "s", rs.getString(i));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public static void viewBookTitles(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT booktitle FROM books";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%" + padding +  "s", rsmd.getColumnLabel(i));
            }
            System.out.print("\n");
            
            while(rs.next()){
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%" + padding +  "s", rs.getString(i));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static boolean insertBook(Connection conn, String title, String gName, String pName, int year, int numberPages){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books "
                    + "values (?,?,?,?,?)");
            pstmt.setString(1, title);
            pstmt.setString(2, gName);
            pstmt.setString(3, pName);
            pstmt.setInt(4, year);
            pstmt.setInt(5, numberPages);
            pstmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean insertPub(Connection conn, ArrayList<String> newPub, String oldPubName){
        try {
            PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO publishers "
                    + "VALUES (?,?,?,?)");
            PreparedStatement pstmt2 = conn.prepareStatement("UPDATE books "
                    + "SET publishername = ? "
                    + "WHERE publishername = ?");
            PreparedStatement pstmt3 = conn.prepareStatement("DELETE FROM publishers "
                    + "WHERE publishername = ?");
                    
            pstmt1.setString(1, newPub.get(0));
            pstmt1.setString(2, newPub.get(1));
            pstmt1.setString(3, newPub.get(2));
            pstmt1.setString(4, newPub.get(3));
            pstmt2.setString(1, newPub.get(0));
            pstmt2.setString(2, oldPubName);
            pstmt3.setString(1, oldPubName);
            
            pstmt1.execute();
            pstmt2.execute();
            pstmt3.execute();

         
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            return false;
        }
    }
    
    public static boolean removeBook(Connection conn, String title, String gName, String pName){
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books "
                    + "WHERE booktitle = ? AND "
                    + "groupname = ? AND "
                    + "publishername = ?");
            pstmt.setString(1, title);
            pstmt.setString(2, gName);
            pstmt.setString(3, pName);
            pstmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
}