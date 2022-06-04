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
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private String email;
    private String adresse;
    private int numTel;
    private Role role;
    private Date date_naissance;
    private boolean status;

    public User() {
    }

    // ID because ID est auto-increment
    public User(String nom, String prenom, String username, String password, String email, String adresse, int numTel, Role role, Date date_naissance,boolean status) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.role = role;
        this.date_naissance = date_naissance;
        this.status=status;
    }

    public User(String nom, String prenom, String username, String password, String email, String adresse, int numTel, Role role, Date date_naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.role = role;
        this.date_naissance = date_naissance;
    }
    
    public User(int id, String nom, String prenom, String username, String password, String email, String adresse, int numTel, Role role, Date date_naissance,boolean status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.role = role;
        this.date_naissance = date_naissance;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



   
    /*@Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", username=" + username + ", password=" + password + ", email=" + email + ", adresse=" + adresse + ", numTel=" + numTel + ", role=" + role + ", date_naissance=" + date_naissance + '}'+"\n";
    }*/
    @Override
    public String toString() {
        return "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", username=" + username + ", password=" + password + ", email=" + email + ", adresse=" + adresse + ", numTel=" + numTel + ", role=" + role + ", date_naissance=" + date_naissance + ", status=" + status + '}'+"\n"+"\n";
    }
    
    
}
