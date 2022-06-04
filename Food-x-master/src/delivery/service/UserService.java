/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery.service;

import delivery.entity.Role;
import delivery.entity.User;
import delivery.utils.Myconnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author asus
 */
public class UserService implements IService<User>{
    Connection cnx;
    public UserService(){
        cnx=Myconnexion.getInstance().getCnx();
    }
    @Override
    public void ajouter(User t) {
        try {
            Statement st=cnx.createStatement();
            //id menghir khater auto-increment
            String query="INSERT INTO `utilisateur`( `cin_u`, `nom_u`, `prenom_u`, `date_naissance`, "
                    + "`email_u`, `num_tel`, `role`, `mot_de_passe`) "
                    + "VALUES ('"+t.getCin_u()+"','"+t.getNom_u()+"','"+t.getPrenom_u()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail_u()+"','"+t.getNum_tel()+"','"+t.getRole()+"','"+t.getMot_de_passe()+"')";
            st.executeUpdate(query);
            System.out.println("ajout avec succes");
            //st.executeQuery(sql);
            //"+t.getAdresse()+"
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void signin(User t) {
        try {
            Statement st=cnx.createStatement();
            //id menghir khater auto-increment
            String query="INSERT INTO `utilisateur`( `cin_u`, `nom_u`, `prenom_u`, `date_naissance`, "
                    + "`email_u`, `num_tel`, `role`, `mot_de_passe`) "
                    + "VALUES ('"+t.getCin_u()+"','"+t.getNom_u()+"','"+t.getPrenom_u()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail_u()+"','"+t.getNum_tel()+"','"+t.getRole()+"','"+t.getMot_de_passe()+"')";
            st.executeUpdate(query);
            System.out.println("ajout avec succes");
            //st.executeQuery(sql);
            //"+t.getAdresse()+"
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(int id_amodifier, User t) {
        try {
            String query="UPDATE `utilisateur` SET "
                    + "`cin_u`=? ,"
                    + "`nom_u`=?,"
                    + "`prenom_u`=?,"
                    + "`date_naissance`=?,"
                    + "`email_u`=?,"
                    + "`num_tel`=?,"
                    + "`role`=?,"
                    + "`mot_de_passe`=? "
                    + "WHERE  id=?";
                    
            PreparedStatement st=cnx.prepareStatement(query);
            
            st.setInt(1,t.getCin_u());
            st.setString(2,t.getNom_u());
            st.setString(3,t.getPrenom_u());
            st.setDate(4,new java.sql.Date(t.getDate_naissance().getTime()));
            st.setString(5,t.getEmail_u());
            st.setInt(6,t.getNum_tel());
            st.setString(7,t.getRole().toString());
            st.setString(8,t.getMot_de_passe());
           
          
           // st.setString(9,t.getUsername());
           // st.setBoolean(10,t.isStatus());
            st.setInt(9, id_amodifier);
            st.executeUpdate();
            System.out.println("modifié avec succes");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            User t=findById(id);
            Statement st=cnx.createStatement();
            String query="DELETE FROM `utilisateur` WHERE id="+id;
           st.executeUpdate(query);
            System.out.println("supprimé avec succes");
           /* String query_addarchive="INSERT INTO `user_archive`(`adresse`, `date_naissance`,"
                    + " `email`, `nom`, `num_tel`,"
                    + " `password`, `prenom`, `role`,"
                    + " `username`, `status`) "
                    + "VALUES ('"+t.getAdresse()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail()+"','"+t.getNom()+"',"
                    + "'"+t.getNumTel()+"','"+t.getPassword()+"',"
                    + "'"+t.getPrenom()+"','"+t.getRole()+"',"
                    + "'"+t.getUsername()+"',"+t.isStatus()+")";
            st.executeUpdate(query_addarchive);*/
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<User> afficher() {
        List<User> lu=new ArrayList<>();
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur`";
            ResultSet rs = st.executeQuery(query);
            
            
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setCin_u(rs.getInt("cin_u"));
                u.setNom_u(rs.getString("nom_u"));
                u.setPrenom_u(rs.getString("prenom_u"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setEmail_u(rs.getString("email_u"));
                u.setNum_tel(rs.getInt("num_tel"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
                //u.setUsername(rs.getString("username"));
                //u.setStatus(rs.getBoolean("status"));
                lu.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lu;
           
    }
 
    public boolean approvedlogin(String email){
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur` WHERE `email_u`='"+email+"'  ;";
            ResultSet rs=st.executeQuery(query);
            return rs.next(); 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean checklogin(String email){
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur` WHERE `email_u`='"+email+"' ;";
            ResultSet rs=st.executeQuery(query);
            return rs.next(); 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public User findByEmail(String email){
        User u=new User();
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur` WHERE `email_u`='"+email+"'";
            ResultSet rs=st.executeQuery(query);
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setCin_u(rs.getInt("cin_u"));
                u.setNom_u(rs.getString("nom_u"));
                u.setPrenom_u(rs.getString("prenom_u"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setEmail_u(rs.getString("email_u"));
                u.setNum_tel(rs.getInt("num_tel"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
                //u.setUsername(rs.getString("username"));
                //u.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    /*
      public statis User checklogin(String username ,String password){
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` WHERE `username`='"+username+"' AND `password`='"+password+"';";
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                 user =new User();
                 user.setStatus(rs.getString("status"));
                   }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    */
    
        public User findById(int id){
        User u=new User();
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur` WHERE `id`="+id;
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                u.setId(rs.getInt("id"));
                u.setCin_u(rs.getInt("cin_u"));
                u.setNom_u(rs.getString("nom_u"));
                u.setPrenom_u(rs.getString("prenom_u"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setEmail_u(rs.getString("email_u"));
                u.setNum_tel(rs.getInt("num_tel"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
               // u.setUsername(rs.getString("username"));
               //u.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(u.getId()==0){
            return null;
        }
        return u;
    }
    public User findByEmailORCin(String str){
        User u=new User();
        try {
            
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `utilisateur` WHERE cin_u='"+str+"' OR email_u='"+str+"';";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setCin_u(rs.getInt("cin_u"));
                u.setNom_u(rs.getString("nom_u"));
                u.setPrenom_u(rs.getString("prenom_u"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setEmail_u(rs.getString("email_u"));
                u.setNum_tel(rs.getInt("num_tel"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setMot_de_passe(rs.getString("mot_de_passe"));
                //u.setUsername(rs.getString("username"));
                //u.setStatus(rs.getBoolean("status"));
                
            }
            else{
                u=null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
        
    public List<User> findByName(String name){
        List<User> users=afficher();
        List<User> resultat=users.stream().filter(user->user.getNom_u().equals(name)).collect(Collectors.toList());
        return resultat;
    }
    
    public List<User> sortedByDate(){
        List<User> users=afficher();
        List<User> resultat=users.stream().sorted(Comparator.comparing(User::getDate_naissance)).collect(Collectors.toList());
        return resultat;
        /*
        Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` ORDER BY date_naissance";
          kifma affichage
        */
    }
    public int nbUser(){
        
        return afficher().size();
        
    }
    
    /*public static User getSecurity(String email){
        User user=null;
        try {
            
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` email='"+email+"'";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
    }
            //return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
}
