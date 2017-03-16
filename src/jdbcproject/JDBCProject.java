/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcproject;

import java.sql.*;
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
            
            for(int i = 0; i < numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 0; i < numcols; i++){
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
            
            for(int i = 0; i < numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 0; i < numcols; i++){
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

            for (int i = 0; i < numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 0; i < numcols; i++) {
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
            pstmt.setString(0, publishername);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numcols = rsmd.getColumnCount();

            for (int i = 0; i < numcols; i++) {
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }

            do {
                for (int i = 0; i < numcols; i++) {
                    System.out.format("%20s", rs.getString(i));
                }

            } while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    private static void viewBookTitles(){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT title FROM books;";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numcols = rsmd.getColumnCount();
            
            for(int i = 0; i < numcols; i++){
                System.out.format("%20s", rsmd.getColumnLabel(i));
            }
            
            do{
                for(int i = 0; i < numcols; i++){
                    System.out.format("%20s", rs.getString(i));
                }
                
            }while(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}