/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author asus
 */
public class Myconnexion {
    final String URL="jdbc:mysql://localhost:3306/Bank?zeroDateTimeBehavior=convertToNull";
    final String USER="root";
    final String PWD="";
    private static Connection cnx;
    private static Myconnexion instance;
    public Myconnexion(){
        try {
            cnx=DriverManager.getConnection(URL, USER, PWD);
            System.out.println("connexion établie");

        } catch (SQLException ex) {
            Logger.getLogger(Myconnexion.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    //hedhi bech ma trod l connexion tsir mara wahda kahaw sinoon rani deja connectee exemple 
    //Mycoonexion m= new Myconnexion ; w Myconnection m2 = new Myconnexion; 
    //lezem tsir mara barka
    public static Myconnexion getInstance(){
        if(instance==null){
            instance= new Myconnexion();
        }else
        {
            System.out.println("déja connecté");
        }
        return instance;
    }

    public static Connection getCnx() {
        return cnx;
    }
    
}
