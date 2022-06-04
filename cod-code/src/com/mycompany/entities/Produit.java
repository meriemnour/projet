/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author RoKy-Dev
 */
public class Produit {

    private int id_vehicule;
    private String type_vehicule;
    private String num_chassis;
    private String num_immatriculation;

    public Produit() {
    }

    public Produit(String type_vehicule, String num_chassis, String num_immatriculation) {
        this.num_chassis = num_chassis;
        this.type_vehicule = type_vehicule;
        this.num_immatriculation = num_immatriculation;
    }

    public Produit(int id_vehicule, String type_vehicule, String num_chassis, String num_immatriculation) {
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

    public void setId_vehicule(int id_vehicule) {
        this.id_vehicule = id_vehicule;
    }

    public void setType_vehicule(String type_vehicule) {
        this.type_vehicule = type_vehicule;
    }

    public void setNum_chassis(String num_chassis) {
        this.num_chassis = num_chassis;
    }

    public void setNum_immatriculation(String num_immatriculation) {
        this.num_immatriculation = num_immatriculation;
    }

    public String getNum_immatriculation() {
        return num_immatriculation;
    }

    @Override
    public String toString() {
        return "Produit{" + "id_vehicule=" + id_vehicule + ", type_vehicule=" + type_vehicule + ", num_chassis=" + num_chassis + ", num_immatriculation=" + num_immatriculation + '}';
    }

}
