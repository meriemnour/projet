/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.service;


import delivery.entity.BonPlan;
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
 * @author Mortadha
 */
public class BonPlanService {
    
    Connection cnx;

    public BonPlanService() {
        cnx = Myconnexion.getInstance().getCnx();
    }
    
    public void ajouter(BonPlan b){
                Statement st;
        try {
            st = cnx.createStatement();
            String query ="INSERT INTO `bonplan`(`ref_compte`, `refcategorie`,`idrestaurant`,`status`, `libelle`,`description`, `image`,`prix`,`ouverture`, `fermuture`, `nbrreact`) VALUES ('"+b.getRef_compte()+"','"+b.getRefcategorie()+"','"+b.getIdrestaurant()+"','"+b.getStatus()+"','"+b.getLibelle()+"','"+b.getDescription()+"','"+b.getImage()+"','"+b.getPrix()+"','"+LocalDateTime.now()+"','"+LocalDateTime.now()+"','"+b.getNbrreact()+"')";
            st.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
        
    public List<BonPlan> afficher() {
        List<BonPlan> lp = new ArrayList<>();
        try {
            
            
            
            
            Statement stm = cnx.createStatement();
            
            
            String query = "SELECT * FROM bonplan";
            ResultSet rs= stm.executeQuery(query);
            while (rs.next()){
                BonPlan bp = new BonPlan();
                bp.setId_bonplan(rs.getInt("id_bonplan"));
                bp.setRef_compte(rs.getInt("ref_compte"));
                bp.setRefcategorie(rs.getInt("refcategorie"));
                bp.setIdrestaurant(rs.getInt("idrestaurant"));
                bp.setStatus(rs.getInt("status"));
                bp.setLibelle(rs.getString("libelle"));
                bp.setDescription(rs.getString("description"));
                bp.setImage(rs.getString("image"));
                bp.setPrix(rs.getFloat("prix"));
                bp.setOuverture(rs.getTimestamp(10).toLocalDateTime());
                bp.setFermuture(rs.getTimestamp(11).toLocalDateTime());
                bp.setNbrreact(rs.getInt("nbrreact"));
                lp.add(bp);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lp;
    }
    
    public void supprimer(int id_bonplan)  {
        try {
            Statement stm = cnx.createStatement();
            String query = "delete from bonplan where id_bonplan="+id_bonplan;
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        public int nbBonplan(){
        int nbBonplan = 0;
        try {
            ResultSet set = Myconnexion.getInstance().
                    getCnx().prepareStatement("SELECT COUNT(id_bonplan) FROM bonplan")
                    .executeQuery();
            if (set.next()) {
                nbBonplan = set.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nbBonplan;
    }
        
            public BonPlan SearchById(int id_bonplan) {
                BonPlan bp =new BonPlan();
        try {
            Statement stm = cnx.createStatement();
            
            String query = "select * from bonplan where id_bonplan="+id_bonplan;
            ResultSet rs= stm.executeQuery(query);
            while (rs.next()){
                bp.setId_bonplan(rs.getInt("id_bonplan"));
                bp.setRef_compte(rs.getInt("ref_compte"));
                bp.setRefcategorie(rs.getInt("refcategorie"));
                bp.setIdrestaurant(rs.getInt("idrestaurant"));
                bp.setStatus(rs.getInt("status"));
                bp.setLibelle(rs.getString("libelle"));
                bp.setDescription(rs.getString("description"));
                bp.setImage(rs.getString("image"));
                bp.setPrix(rs.getFloat("prix"));
                bp.setOuverture(rs.getTimestamp(10).toLocalDateTime());
                bp.setFermuture(rs.getTimestamp(11).toLocalDateTime());
                bp.setNbrreact(rs.getInt("nbrreact"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bp;
    }
            
        public BonPlan SearchBylibelle(String libelle) throws SQLException{
        Statement stm = cnx.createStatement();
        BonPlan bp =new BonPlan();
        String query ="SELECT * FROM from where libelle='"+libelle+"'";
        ResultSet rs= stm.executeQuery(query);
        while (rs.next()){
            bp.setId_bonplan(rs.getInt("id_bonplan"));
            bp.setRef_compte(rs.getInt("ref_compte"));
            bp.setRefcategorie(rs.getInt("refcategorie"));
            bp.setIdrestaurant(rs.getInt("idrestaurant"));
            bp.setStatus(rs.getInt("status"));
            bp.setLibelle(rs.getString("libelle"));
            bp.setDescription(rs.getString("description"));
            bp.setImage(rs.getString("image"));
            bp.setPrix(rs.getFloat("prix"));
            bp.setOuverture(rs.getTimestamp(10).toLocalDateTime());
            bp.setFermuture(rs.getTimestamp(11).toLocalDateTime());
            bp.setNbrreact(rs.getInt("nbrreact"));
        }
        return bp;
    }
        
      public void modifier(int id_bonplan,BonPlan bp)  {
        try {
            Statement stm = cnx.createStatement();
            BonPlan b =SearchById(id_bonplan);
            String query = "UPDATE `bonplan` SET `ref_compte`='"+bp.getRef_compte()+"',"
                    + "`refcategorie`='"+bp.getRefcategorie()+"',"
                    + "`idrestaurant`='"+bp.getIdrestaurant()+"',"
                    + "`status`='"+bp.getStatus()+"',"
                    + "`libelle`='"+bp.getLibelle()+"',"
                    + "`description`='"+bp.getDescription()+"',"
                    + "`image`='"+bp.getImage()+"',"
                    + "`prix`='"+bp.getPrix()+"',"
                    + "`ouverture`='"+bp.getOuverture()+"',"
                    + "`fermuture`='"+bp.getFermuture()+"',"
                    + "`nbrreact`='"+bp.getNbrreact()+"' where id_bonplan="+id_bonplan;
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    
}
