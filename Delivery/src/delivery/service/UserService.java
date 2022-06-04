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
            String query="INSERT INTO `user`(`adresse`, `date_naissance`,"
                    + " `email`, `nom`, `num_tel`,"
                    + " `password`, `prenom`, `role`,"
                    + " `username`, `status`) "
                    + "VALUES ('"+t.getAdresse()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail()+"','"+t.getNom()+"',"
                    + "'"+t.getNumTel()+"','"+t.getPassword()+"',"
                    + "'"+t.getPrenom()+"','"+t.getRole()+"',"
                    + "'"+t.getUsername()+"',"+t.isStatus()+")";
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
            String query="INSERT INTO `user`(`adresse`, `date_naissance`,"
                    + " `email`, `nom`, `num_tel`,"
                    + " `password`, `prenom`, `role`,"
                    + " `username`, `status`) "
                    + "VALUES ('"+t.getAdresse()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail()+"','"+t.getNom()+"',"
                    + "'"+t.getNumTel()+"','"+t.getPassword()+"',"
                    + "'"+t.getPrenom()+"','"+t.getRole()+"',"
                    + "'"+t.getUsername()+"',"+0+")";
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
            String query="UPDATE `user` SET "
                    + "`adresse`=?,"
                    + "`date_naissance`=?,"
                    + "`email`=?,"
                    + "`nom`=?,"
                    + "`num_tel`=?,"
                    + "`password`=?,"
                    + "`prenom`=?,"
                    + "`role`=?,"
                    + "`username`=?,"
                    + "`status`=? WHERE id=?";
            PreparedStatement st=cnx.prepareStatement(query);
            st.setString(1,t.getAdresse());
            st.setDate(2,new java.sql.Date(t.getDate_naissance().getTime()));
            st.setString(3,t.getEmail());
            st.setString(4,t.getNom());
            st.setInt(5,t.getNumTel());
            st.setString(6,t.getPassword());
            st.setString(7,t.getPrenom());
            st.setString(8,t.getRole().toString());
            st.setString(9,t.getUsername());
            st.setBoolean(10,t.isStatus());
            st.setInt(11, id_amodifier);
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
            String query="DELETE FROM `user` WHERE id="+id;
            st.executeUpdate(query);
            System.out.println("supprimé avec succes");
            String query_addarchive="INSERT INTO `user_archive`(`adresse`, `date_naissance`,"
                    + " `email`, `nom`, `num_tel`,"
                    + " `password`, `prenom`, `role`,"
                    + " `username`, `status`) "
                    + "VALUES ('"+t.getAdresse()+"','"+t.getDate_naissance()+"',"
                    + "'"+t.getEmail()+"','"+t.getNom()+"',"
                    + "'"+t.getNumTel()+"','"+t.getPassword()+"',"
                    + "'"+t.getPrenom()+"','"+t.getRole()+"',"
                    + "'"+t.getUsername()+"',"+t.isStatus()+")";
            st.executeUpdate(query_addarchive);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<User> afficher() {
        List<User> lu=new ArrayList<>();
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user`";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setAdresse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setNumTel(rs.getInt("num_tel"));
                u.setPassword(rs.getString("password"));
                u.setUsername(rs.getString("username"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setStatus(rs.getBoolean("status"));
                lu.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lu;
    }
 
    public boolean approvedlogin(String username ,String password){
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` WHERE `username`='"+username+"' AND `password`='"+password+"' AND status="+1+";";
            ResultSet rs=st.executeQuery(query);
            return rs.next(); 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean checklogin(String username ,String password){
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` WHERE `username`='"+username+"' AND `password`='"+password+"';";
            ResultSet rs=st.executeQuery(query);
            return rs.next(); 
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public User findByUsername(String username){
        User u=new User();
        try {
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` WHERE `username`='"+username+"'";
            ResultSet rs=st.executeQuery(query);
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setAdresse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setNumTel(rs.getInt("num_tel"));
                u.setPassword(rs.getString("password"));
                u.setUsername(rs.getString("username"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setStatus(rs.getBoolean("status"));
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
            String query="SELECT * FROM `user` WHERE `id`="+id;
            ResultSet rs=st.executeQuery(query);
            while(rs.next()){
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setAdresse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setNumTel(rs.getInt("num_tel"));
                u.setPassword(rs.getString("password"));
                u.setUsername(rs.getString("username"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setDate_naissance(rs.getDate("date_naissance"));
               u.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(u.getId()==0){
            return null;
        }
        return u;
    }
    public User findByEmailORUsername(String str){
        User u=new User();
        try {
            
            Statement st=cnx.createStatement();
            String query="SELECT * FROM `user` WHERE username='"+str+"' OR email='"+str+"';";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setAdresse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setNumTel(rs.getInt("num_tel"));
                u.setPassword(rs.getString("password"));
                u.setUsername(rs.getString("username"));
                u.setRole(Role.valueOf(rs.getString("role")));
                u.setDate_naissance(rs.getDate("date_naissance"));
                u.setStatus(rs.getBoolean("status"));
                
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
        List<User> resultat=users.stream().filter(user->user.getNom().equals(name)).collect(Collectors.toList());
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
