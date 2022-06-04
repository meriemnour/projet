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
public class Reclamation {
    private int id_reclamation;
    private int id_author;
    private int id_bonplan;
    private LocalDateTime date_creation;
    private LocalDateTime date_traitement;
    private String description;
    private String objet;
    private int status;

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public int getId_bonplan() {
        return id_bonplan;
    }

    public void setId_bonplan(int id_bonplan) {
        this.id_bonplan = id_bonplan;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDateTime date_creation) {
        this.date_creation = date_creation;
    }

    public LocalDateTime getDate_traitement() {
        return date_traitement;
    }

    public void setDate_traitement(LocalDateTime date_traitement) {
        this.date_traitement = date_traitement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Reclamation() {
    }

    public Reclamation(int id_author, int id_bonplan, LocalDateTime date_creation, String description, String objet, int status) {
        this.id_author = id_author;
        this.id_bonplan = id_bonplan;
        this.date_creation = date_creation;
        this.description = description;
        this.objet = objet;
        this.status = status;
    }

    public Reclamation(int id_author, int id_bonplan, LocalDateTime date_creation, LocalDateTime date_traitement, String description, String objet, int status) {
        this.id_author = id_author;
        this.id_bonplan = id_bonplan;
        this.date_creation = date_creation;
        this.date_traitement = date_traitement;
        this.description = description;
        this.objet = objet;
        this.status = status;
    }

    public Reclamation(int id_reclamation, int id_author, int id_bonplan, LocalDateTime date_creation, LocalDateTime date_traitement, String description, String objet, int status) {
        this.id_reclamation = id_reclamation;
        this.id_author = id_author;
        this.id_bonplan = id_bonplan;
        this.date_creation = date_creation;
        this.date_traitement = date_traitement;
        this.description = description;
        this.objet = objet;
        this.status = status;
    }

    

    @Override
    public String toString() {
        return "Reclamation{" + "id_reclamation=" + id_reclamation + ", id_author=" + id_author + ", id_bonplan=" + id_bonplan + ", date_creation=" + date_creation + ", date_traitement=" + date_traitement + ", description=" + description + ", objet=" + objet + ", status=" + status + '}';
    }




    
}
