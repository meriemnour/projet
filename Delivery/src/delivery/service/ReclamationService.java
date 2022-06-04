/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.service;


import delivery.entity.Reclamation;
import delivery.utils.Myconnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mahdi
 */
public class ReclamationService {
        Connection cnx;

    public ReclamationService() {
        cnx = Myconnexion.getInstance().getCnx();
    }
    
        public void ajouter(Reclamation r){
                Statement st;
        try {
            st = cnx.createStatement();
            String query ="INSERT INTO `reclamation`(`id_reclamation`, `id_author`,`id_bonplan`,`date_creation`,`description`, `objet`,`status`) VALUES ('"+r.getId_reclamation()+"','"+r.getId_author()+"','"+r.getId_bonplan()+"','"+r.getDate_creation()+"','"+r.getDescription()+"','"+r.getObjet()+"','"+r.getStatus()+"')";
            st.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
    }
            public List<Reclamation> afficher() {
                List<Reclamation> lp = new ArrayList<>();
            try {
                
                Statement stm = cnx.createStatement();
                String query = "SELECT * FROM reclamation";
                ResultSet rs= stm.executeQuery(query);
                while (rs.next()){
                    Reclamation r = new Reclamation();
                    r .setId_reclamation(rs.getInt("id_reclamation"));
                    r .setId_author(rs.getInt("id_author"));
                    r .setId_bonplan(rs.getInt("id_bonplan"));
                    r .setDate_creation(rs.getTimestamp(4).toLocalDateTime());
//                    r .setDate_traitement(rs.getTimestamp(5).toLocalDateTime());
                    r .setDescription(rs.getString("description"));
                    r .setObjet(rs.getString("objet"));
                    r.setStatus(rs.getInt("status"));
                    lp.add(r );
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return lp;
    }
            
      public void supprimer(int id_reclamation){
            try {
                Statement stm = cnx.createStatement();
                String query = "delete from reclamation where id_reclamation="+id_reclamation;
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
      
      public int nbReservation(){
        int nbReservation = 0;
        try {
            ResultSet set = Myconnexion.getInstance().
                    getCnx().prepareStatement("SELECT COUNT(id_reclamation) FROM reclamation")
                    .executeQuery();
            if (set.next()) {
                nbReservation = set.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nbReservation;
    }
                  public Reclamation SearchById(int id_reclamation) {
                      Reclamation r =new Reclamation();
            try {
                Statement stm = cnx.createStatement();
                
                String query = "select * from reclamation where id_reclamation="+id_reclamation;
                ResultSet rs= stm.executeQuery(query);
                while (rs.next()){
                    r .setId_reclamation(rs.getInt("id_reclamation"));
                    r .setId_author(rs.getInt("id_author"));
                    r .setId_bonplan(rs.getInt("id_bonplan"));
                    r .setDate_creation(rs.getTimestamp(4).toLocalDateTime());
//                    r .setDate_traitement(rs.getTimestamp(5).toLocalDateTime());
                    r .setDescription(rs.getString("description"));
                    r .setObjet(rs.getString("objet"));
                    r.setStatus(rs.getInt("status"));
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return r;
    }
                  
     public Reclamation SearchByObjet(String objet){
         Reclamation r =new Reclamation();
            try {
                Statement stm = cnx.createStatement();
                
                String query ="SELECT * FROM reclamation where objet='"+objet+"'";
                ResultSet rs= stm.executeQuery(query);
                while (rs.next()){
                    r .setId_reclamation(rs.getInt("id_reclamation"));
                    r .setId_author(rs.getInt("id_author"));
                    r .setId_bonplan(rs.getInt("id_bonplan"));
                    r .setDate_creation(rs.getTimestamp(4).toLocalDateTime());
                    r .setDate_traitement(rs.getTimestamp(5).toLocalDateTime());
                    r .setDescription(rs.getString("description"));
                    r .setObjet(rs.getString("objet"));
                    r.setStatus(rs.getInt("status"));
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return r;
    }
      public void modifier(int id_reclamation,Reclamation r)  {
            try {
                Statement stm = cnx.createStatement();
                Reclamation rs =SearchById(id_reclamation);
                String query = "UPDATE `reclamation` SET `id_author`='"+r.getId_author()+"',`id_bonplan`='"+r.getId_bonplan()+"',`date_creation`='"+LocalDateTime.now()+"',`date_traitement`='"+LocalDateTime.now()+"',`description`='"+r.getDescription()+"',`objet`='"+r.getObjet()+"',`status`='"+r.getStatus()+"' where id_reclamation="+r.getId_reclamation();
                stm.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
