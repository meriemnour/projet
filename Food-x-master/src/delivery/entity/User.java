/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.entity;

import java.util.Date;

/**
 *
 * @author asus
 */
public class User {
    private int id;
    private int cin_u;
    private String nom_u;
    private String prenom_u;
    private String mot_de_passe;
    private String email_u;
    private int num_tel;
    private Role role;
    private Date date_naissance;
    //private boolean status;

    public User() {
    }

    public User(int id, int cin_u, String nom_u, String prenom_u, String mot_de_passe, String email_u, int num_tel, Role role, Date date_naissance) {
        this.id = id;
        this.cin_u = cin_u;
        this.nom_u = nom_u;
        this.prenom_u = prenom_u;
        this.mot_de_passe = mot_de_passe;
        this.email_u = email_u;
        this.num_tel = num_tel;
        this.role = role;
        this.date_naissance = date_naissance;
    }

    public User(int cin_u, String nom_u, String prenom_u, String mot_de_passe, String email_u, int num_tel, Role role, Date date_naissance) {
        this.cin_u = cin_u;
        this.nom_u = nom_u;
        this.prenom_u = prenom_u;
        this.mot_de_passe = mot_de_passe;
        this.email_u = email_u;
        this.num_tel = num_tel;
        this.role = role;
        this.date_naissance = date_naissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin_u() {
        return cin_u;
    }

    public void setCin_u(int cin_u) {
        this.cin_u = cin_u;
    }

    public String getNom_u() {
        return nom_u;
    }

    public void setNom_u(String nom_u) {
        this.nom_u = nom_u;
    }

    public String getPrenom_u() {
        return prenom_u;
    }

    public void setPrenom_u(String prenom_u) {
        this.prenom_u = prenom_u;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail_u() {
        return email_u;
    }

    public void setEmail_u(String email_u) {
        this.email_u = email_u;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", cin_u=" + cin_u + ", nom_u=" + nom_u + ", prenom_u=" + prenom_u + ", mot_de_passe=" + mot_de_passe + ", email_u=" + email_u + ", num_tel=" + num_tel + ", role=" + role + ", date_naissance=" + date_naissance + '}';
    }
    
    

}