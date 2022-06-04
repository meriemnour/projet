/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.GUI;

import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public class Vehicules {
    static void setItems(ObservableList<Vehicules> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     private int id_vehicule;
    private String type_vehicule;
    private String num_chassis;
    private String num_immatriculation;

    public Vehicules(int id_vehicule, String type_vehicule, String num_chassis, String num_immatriculation) {
        this.id_vehicule = id_vehicule;
        this.type_vehicule = type_vehicule;
        this.num_chassis = num_chassis;
        this.num_immatriculation = num_immatriculation;
    }

    public int getId_vehicule() {
        return id_vehicule;
    }

    public String getType_vehicule() {
        return type_vehicule;
    }

    public String getNum_chassis() {
        return num_chassis;
    }

    public String getNum_immatriculation() {
        return num_immatriculation;
    }
    
}
