/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Yudho
 */
public abstract class IDaoServer {
    public static Connection CON;
    public static final String url = "jdbc:ucanaccess://";
    public static final String path = "X:\\dbAdmin.mdb";
    
    public void makeConnection(){
        System.out.println("Opening Database .....");
        
        try{
            CON = DriverManager.getConnection(url+path);
            System.out.print("Success ..\n");
        }
        catch(SQLException e){
            System.out.print("Error Opening Database ..");
            System.out.println(e);
        }
    }
    
    public void closeConnection() {
        System.out.println("Closing Database .....");
        
        try{
            CON.close();
            System.out.print("Success ..\n");
        }catch(SQLException e){
            System.out.print("Error Closing Database ..");
            System.out.println(e);
        }
    }
}
