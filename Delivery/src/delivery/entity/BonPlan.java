/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Mortadha
 */
public class BonPlan {
    private int id_bonplan;
    private int ref_compte;
    private int refcategorie;
    private int idrestaurant;
    private int status;
    private String libelle;
    private String description;
    private String image;
    private float prix;
    private LocalDateTime ouverture;
    private LocalDateTime fermuture;
    private int nbrreact;

    public BonPlan() {
    }

    public BonPlan(int ref_compte, int refcategorie, int idrestaurant, int status, String libelle, String description, String image, float prix, LocalDateTime ouverture, LocalDateTime fermuture, int nbrreact) {
        this.ref_compte = ref_compte;
        this.refcategorie = refcategorie;
        this.idrestaurant = idrestaurant;
        this.status = status;
        this.libelle = libelle;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.ouverture = ouverture;
        this.fermuture = fermuture;
        this.nbrreact = nbrreact;
    }

    public BonPlan(int id_bonplan, int ref_compte, int refcategorie, int idrestaurant, int status, String libelle, String description, String image, float prix, LocalDateTime ouverture, LocalDateTime fermuture, int nbrreact) {
        this.id_bonplan = id_bonplan;
        this.ref_compte = ref_compte;
        this.refcategorie = refcategorie;
        this.idrestaurant = idrestaurant;
        this.status = status;
        this.libelle = libelle;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.ouverture = ouverture;
        this.fermuture = fermuture;
        this.nbrreact = nbrreact;
    }

    public int getId_bonplan() {
        return id_bonplan;
    }

    public void setId_bonplan(int id_bonplan) {
        this.id_bonplan = id_bonplan;
    }

    public int getRef_compte() {
        return ref_compte;
    }

    public void setRef_compte(int ref_compte) {
        this.ref_compte = ref_compte;
    }

    public int getRefcategorie() {
        return refcategorie;
    }

    public void setRefcategorie(int refcategorie) {
        this.refcategorie = refcategorie;
    }

    public int getIdrestaurant() {
        return idrestaurant;
    }

    public void setIdrestaurant(int idrestaurant) {
        this.idrestaurant = idrestaurant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

        
    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public LocalDateTime getOuverture() {
        return ouverture;
    }

    public void setOuverture(LocalDateTime ouverture) {
        this.ouverture = ouverture;
    }

    public LocalDateTime getFermuture() {
        return fermuture;
    }

    public void setFermuture(LocalDateTime fermuture) {
        this.fermuture = fermuture;
    }

    public int getNbrreact() {
        return nbrreact;
    }

    public void setNbrreact(int nbrreact) {
        this.nbrreact = nbrreact;
    }

    @Override
    public String toString() {
        return "BonPlan{" + "id_bonplan=" + id_bonplan + ", ref_compte=" + ref_compte + ", refcategorie=" + refcategorie + ", idrestaurant=" + idrestaurant + ", status=" + status + ", libelle=" + libelle + ", description=" + description + ", image=" + image + ", prix=" + prix + ", ouverture=" + ouverture + ", fermuture=" + fermuture + ", nbrreact=" + nbrreact + '}';
    }

    
    

}
