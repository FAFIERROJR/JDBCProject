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
            Scanner in = new Scanner(System.in);
            int menuChoice;
            

            do{
                displayMenu();
                menuChoice = in.nextInt();
                int menuChoices = 10;
                
                String groupname, pubname, booktitle;
                int yearpublished, numpages;
                String pubaddress, pubphone, pubemail, oldpubname;
                switch(menuChoice){
                case 1:
                    viewTable(conn, "groupname", "writinggroups");
                    break;
                case 2:
                    in.nextLine();
                    do{
                        System.out.println("Please enter the group name");
                        groupname = in.nextLine();
                    }while(!validate(conn, "groupname", groupname, "writinggroups"));
                    viewGroup(conn, groupname);
                    break;
                case 3:
                    viewTable(conn, "publishername", "publishers");
                    break;
                case 4:
                    in.nextLine();
                    do{
                        System.out.println("Please enter the publisher name");
                        pubname = in.nextLine();
                    }while(!validate(conn, "publishername", pubname, "publishers"));
                    viewPublisher(conn, pubname);
                    break;
                case 5:
                    viewBookTitles(conn);
                    break;
                case 6:
                    in.nextLine();
                    do{
                        System.out.println("Please enter the book title");
                        booktitle = in.nextLine();
                    }while(!validate(conn, "booktitle", booktitle, "books"));
                    do{
                        System.out.println("Please enter the group name");
                        groupname = in.nextLine();
                    }while(!validateBookGroup(conn, booktitle, groupname));
                    do{
                        System.out.println("Please enter the publisher name");
                        pubname = in.nextLine();
                    }while(!validateBookGroupPub(conn, booktitle, groupname, pubname));
                    viewBook(conn, booktitle, groupname, pubname);
                    break;
                case 7:
                    in.nextLine();
                    System.out.println("Please enter the book title");
                    booktitle = in.nextLine();
                    do{
                        System.out.println("Please enter the group name");
                        groupname = in.nextLine();
                    }while(!validate(conn, "groupname", groupname, "writinggroups"));
                    do{
                        System.out.println("Please enter the publisher name");
                        pubname = in.nextLine();
                    }while(!validate(conn, "publishername", pubname, "publishers"));
                    System.out.println("Please enter the year published (4-digits)");
                    do{
                        yearpublished = in.nextInt();
                        if(yearpublished <0 ){
                            System.out.println("Please enter a positive number");
                        }
                    }while(yearpublished <= 0);
                    System.out.println("Please enter the number of pages");
                    do{
                        numpages = in.nextInt();
                        System.out.println("Please enter a positive number");
                    }while(numpages < 0);
                    insertBook(conn, booktitle, groupname, pubname, yearpublished, numpages);
                    break;
                case 8:
                    in.nextLine();
                    System.out.println("Please enter the new publisher name");
                    pubname = in.nextLine();
                    System.out.println("Please enter the new publisher address");
                    pubaddress = in.nextLine();
                    System.out.println("Please enter the new publisher phone");
                    pubphone = in.nextLine();
                    System.out.println("Please enter the new publisher email");
                    pubemail = in.nextLine();
                    do{
                        System.out.println("Please enter the old publisher name");
                        oldpubname = in.nextLine();
                    }while(!validate(conn, "publishername", oldpubname, "publishers"));
                    
                    ArrayList<String> newpub = new ArrayList<String>();
                    newpub.add(pubname);
                    newpub.add(pubaddress);
                    newpub.add(pubphone);
                    newpub.add(pubemail);
                    
                    insertPub(conn, newpub, oldpubname);
                    
                    break;
                case 9:
                    in.nextLine();
                    do{
                        System.out.println("Please enter the book title");
                        booktitle = in.nextLine();
                    }while(!validate(conn, "booktitle", booktitle, "books"));
                    do{
                        System.out.println("Please enter the group name");
                        groupname = in.nextLine();
                    }while(!validateBookGroup(conn, booktitle, groupname));
                    do{
                        System.out.println("Please enter the publisher name");
                        pubname = in.nextLine();
                    }while(!validateBookGroupPub(conn, booktitle, groupname, pubname));
                    removeBook(conn, booktitle, groupname, pubname);
                    break;
                case 10:
                    System.out.println("Thanks for using this app! Goodbye!");
                    conn.close();
                    System.exit(0);
                default:
                    System.out.println("Please enter an integer between 1 and " + menuChoices);
                    break;
                }
            }while(true);

//            //this stuff is just for testing
//            //viewtable just shows the one table. So we can use it for writing groups and publishers
//            viewTable(conn, "writinggroups");
//            viewGroup(conn, "Happy Gilmore");
//            viewTable(conn, "publishers");
//            viewPublisher(conn, "Pearson");
//            //this is a special function for viewing just book titles
//            viewBookTitles(conn);
//            viewBook(conn, "Harry Potter and the Chamber of Secrets", "Rowling et al", "Pearson");
//            //removeBook(conn, "Harry Potter and the Prisoner of Azkaban", "Rowling et al", "Pearson");
//            insertBook(conn, "Harry Potter and the Prisoner of Azkaban", "Rowling et al", "Pearson", 2003, 500);
//            viewBookTitles(conn);
//            ArrayList<String> newPub = new ArrayList<String>();
//            newPub.add("McGraw Hill");
//            newPub.add("666 Oligopoly Rd");
//            newPub.add("800-123-4567");
//            newPub.add("McGrawHelp@McGraw.com");
//            insertPub(conn, newPub, "Penguin Random House" );
//            viewTable(conn, "publishers");
            
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
        
        
    }
    
    public static void displayMenu(){
        String menu = "1. List all writing groups"
                + "\n2. Find a writing group "
                + "\n3. List all publishers"
                + "\n4. Find a publisher"
                + "\n5. List all book titles"
                + "\n6. Find a book"
                + "\n7. Add a book"
                + "\n8. Publisher buy-out"
                + "\n9. Remove book"
                + "\n10. Exit Application";
        System.out.println(menu);
    }
          
    public static void viewTable(Connection conn, String attribute, String table){
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT " + attribute + " FROM " + table + "";
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
            
            rs.close();
            stmt.close();
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
            
            rs.close();
            pstmt.close();
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
            
            rs.close();
            pstmt.close();
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
            
            rs.close();
            pstmt.close();
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

       ;
            pstmt1.close();
            pstmt2.close();
            pstmt3.close();
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
            pstmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    public static boolean validate(Connection conn, String attribute, String value, String table){
        try {
            String query = "SELECT 1 FROM " + table + " WHERE " + attribute + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, value);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count > 0){
                return true;
            }
            System.out.println(value + " is not an existing " + attribute + ". Please try again.");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean validateBookGroup(Connection conn, String booktitle, String groupname){
        try {
            String query = "SELECT 1 FROM books WHERE booktitle = ? AND groupname = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, booktitle);
            pstmt.setString(2, groupname);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count > 0){
                return true;
            }
            System.out.println("There are no entries with that book title by that writing group. Please try again.");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean validateBookGroupPub(Connection conn, String booktitle, String groupname, String publishername){
        try {
            String query = "SELECT 1 FROM books WHERE booktitle = ? AND groupname = ? AND publishername = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, booktitle);
            pstmt.setString(2, groupname);
            pstmt.setString(3, publishername);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count > 0){
                return true;
            }
            System.out.println(publishername + " does produce any matches with the book and writing group given. Please try again.");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(JDBCProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}