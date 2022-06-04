/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import com.codename1.io.Preferences;

/**
 *
 * @author RoKy-Dev
 */
public class SessionManager {
    
    public static Preferences pref ; 
    
    private static float id ; 
    private static String email_u;
    private static String nom_u;
    private static String prenom_u;
    private static String mot_de_passe;
    
    public static Preferences getPref(){
        return pref;
    }
    public static void setPref(Preferences pref){
        SessionManager.pref = pref ; 
        
    }

    public static float getId() {
        return pref.get("id",id);
    }

    public static void setId(float id) {
        pref.set("id", id);
    }

 

    public static String getEmail() {
         return pref.get("email",email_u);
    }

    public static void setEmail(String email_u) {
      pref.set("email", email_u);
    }

    public static String getPassword() {
         return pref.get("password",mot_de_passe);
    }

    public static void setPassword(String mot_de_passe) {
           pref.set("password", mot_de_passe);
    }

    public static String getNom_u() {
       return pref.get("nom",nom_u);
    }

    public static void setNom_u(String nom_u) {
        pref.set("nom", nom_u);
    }

    public static String getPrenom_u() {
       return pref.get("prenom",prenom_u);
    }

    public static void setPrenom_u(String prenom_u) {
        pref.set("prenom", prenom_u);
    }
    
   
    
}