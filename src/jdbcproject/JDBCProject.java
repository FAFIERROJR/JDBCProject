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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/JDBC"); 
            //run create DB only once
            //createDB(conn);
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
        
        
    }
    
    
    private static void viewTable(Connection conn, String table){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM " + table + ";";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%20s", rs.getString(i));
                }
                
            }while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    private static void viewBook(Connection conn, String title, String publisher, String groupname){
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BOOKS "
                    + "NATURAL JOIN writinggroups NATURAL JOIN publishers "
                    + "WHERE title = ? AND groupname = ?" );
            pstmt.setString(1, title);
            pstmt.setString(2, groupname);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%20s", rs.getString(i));
                }
                
            }while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void viewGroup(Connection conn, String groupname) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GROUPS WHERE groupname = ?");
            pstmt.setString(1, groupname);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    private static void viewPublisher(Connection conn, String publishername) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GROUPS WHERE publishername = ?");
            pstmt.setString(1, publishername);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    private static void viewBookTitles(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT title FROM books;";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 1; i <= numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 1; i <= numcols; i++){
                    System.out.format("%20s", rs.getString(i));
                }
                
            }while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private static boolean insertBook(Connection conn, String title, String gName, String pName, int year, int numberPages){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books "
                    + "values (?,?,?,?,?);");
            pstmt.setString(1, title);
            pstmt.setString(2, gName);
            pstmt.setString(3, pName);
            pstmt.setInt(4, year);
            pstmt.setInt(5, numberPages);
            
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private static boolean insertPub(Connection conn, ArrayList<String> newPub, String oldPubName){
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE publishers "
                    + "SET publishername = ?, "
                    + "publisheraddress = ?, "
                    + "publisherphone = ?, "
                    + "publisheremail = ? "
                    + "WHERE publishername = ?;");
            pstmt.setString(1, newPub.get(0));
            pstmt.setString(2, newPub.get(1));
            pstmt.setString(3, newPub.get(2));
            pstmt.setString(4, newPub.get(3));
            pstmt.setString(5, oldPubName);
            
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    public static boolean removeBook(Connection conn, String title, String gName, String pName){
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books "
                    + "WHERE booktitle = ?, "
                    + "groupname = ?, "
                    + "publishername = ?;");
            pstmt.setString(1, title);
            pstmt.setString(2, gName);
            pstmt.setString(3, pName);
            
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 1; i <= numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 1; i <= numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
}