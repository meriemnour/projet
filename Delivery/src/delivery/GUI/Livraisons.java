/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import java.util.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public class Livraisons {
static void setItems(ObservableList<Livraisons> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private int id_livraison;
    private String nom_livraison;
    private String date	;
    private String destination;

    public Livraisons(int id_livraison, String nom_livraison, String date, String destination) {
        this.id_livraison = id_livraison;
        this.nom_livraison = nom_livraison;
        this.date = date;
        this.destination = destination;
    }

    public int getId_livraison() {
        return id_livraison;
    }

    public String getNom_livraison() {
        return nom_livraison;
    }

    public String getDate() {
        return date;
    }

    public String getDestination() {
        return destination;
    }
    
}    

